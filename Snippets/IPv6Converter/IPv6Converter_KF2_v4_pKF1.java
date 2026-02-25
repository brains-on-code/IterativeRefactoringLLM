package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public final class IPv6Converter {

    private static final int IPV6_BYTE_LENGTH = 16;
    private static final int IPV4_BYTE_LENGTH = 4;
    private static final int IPV6_MAPPED_IPV4_PREFIX_LENGTH = 12;
    private static final int IPV6_MAPPED_FF_INDEX_1 = 10;
    private static final int IPV6_MAPPED_FF_INDEX_2 = 11;

    private IPv6Converter() {
    }

    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress ipv4InetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = ipv4InetAddress.getAddress();

        byte[] ipv6Bytes = new byte[IPV6_BYTE_LENGTH];
        ipv6Bytes[IPV6_MAPPED_FF_INDEX_1] = (byte) 0xff;
        ipv6Bytes[IPV6_MAPPED_FF_INDEX_2] = (byte) 0xff;
        System.arraycopy(
            ipv4Bytes,
            0,
            ipv6Bytes,
            IPV6_MAPPED_IPV4_PREFIX_LENGTH,
            IPV4_BYTE_LENGTH
        );

        StringBuilder ipv6StringBuilder = new StringBuilder("::ffff:");
        for (int index = IPV6_MAPPED_IPV4_PREFIX_LENGTH; index < IPV6_BYTE_LENGTH; index++) {
            ipv6StringBuilder.append(ipv6Bytes[index] & 0xFF);
            if (index < IPV6_BYTE_LENGTH - 1) {
                ipv6StringBuilder.append('.');
            }
        }
        return ipv6StringBuilder.toString();
    }

    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6InetAddress = InetAddress.getByName(ipv6Address);
        byte[] ipv6Bytes = ipv6InetAddress.getAddress();

        if (isIpv6MappedIpv4Address(ipv6Bytes)) {
            byte[] ipv4Bytes =
                Arrays.copyOfRange(ipv6Bytes, IPV6_MAPPED_IPV4_PREFIX_LENGTH, IPV6_BYTE_LENGTH);
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4Bytes);
            return ipv4InetAddress.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
    }

    private static boolean isIpv6MappedIpv4Address(byte[] ipv6Bytes) {
        if (ipv6Bytes.length != IPV6_BYTE_LENGTH) {
            return false;
        }

        for (int index = 0; index < IPV6_MAPPED_IPV4_PREFIX_LENGTH - 2; index++) {
            if (ipv6Bytes[index] != 0) {
                return false;
            }
        }

        return ipv6Bytes[IPV6_MAPPED_FF_INDEX_1] == (byte) 0xff
            && ipv6Bytes[IPV6_MAPPED_FF_INDEX_2] == (byte) 0xff;
    }
}