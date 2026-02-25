package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public final class IPv6Converter {

    private static final int IPV6_BYTE_LENGTH = 16;
    private static final int IPV4_BYTE_LENGTH = 4;
    private static final int IPV6_MAPPED_PREFIX_LENGTH = 12;
    private static final int IPV6_MAPPED_FF_INDEX_1 = 10;
    private static final int IPV6_MAPPED_FF_INDEX_2 = 11;

    private IPv6Converter() {
    }

    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress ipv4InetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4AddressBytes = ipv4InetAddress.getAddress();

        byte[] ipv6AddressBytes = new byte[IPV6_BYTE_LENGTH];
        ipv6AddressBytes[IPV6_MAPPED_FF_INDEX_1] = (byte) 0xff;
        ipv6AddressBytes[IPV6_MAPPED_FF_INDEX_2] = (byte) 0xff;
        System.arraycopy(ipv4AddressBytes, 0, ipv6AddressBytes, IPV6_MAPPED_PREFIX_LENGTH, IPV4_BYTE_LENGTH);

        StringBuilder ipv6StringBuilder = new StringBuilder("::ffff:");
        for (int byteIndex = IPV6_MAPPED_PREFIX_LENGTH; byteIndex < IPV6_BYTE_LENGTH; byteIndex++) {
            ipv6StringBuilder.append(ipv6AddressBytes[byteIndex] & 0xFF);
            if (byteIndex < IPV6_BYTE_LENGTH - 1) {
                ipv6StringBuilder.append('.');
            }
        }
        return ipv6StringBuilder.toString();
    }

    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6InetAddress = InetAddress.getByName(ipv6Address);
        byte[] ipv6AddressBytes = ipv6InetAddress.getAddress();

        if (isIpv6MappedIpv4Address(ipv6AddressBytes)) {
            byte[] ipv4AddressBytes = Arrays.copyOfRange(ipv6AddressBytes, IPV6_MAPPED_PREFIX_LENGTH, IPV6_BYTE_LENGTH);
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4AddressBytes);
            return ipv4InetAddress.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
    }

    private static boolean isIpv6MappedIpv4Address(byte[] ipv6AddressBytes) {
        if (ipv6AddressBytes.length != IPV6_BYTE_LENGTH) {
            return false;
        }

        for (int byteIndex = 0; byteIndex < IPV6_MAPPED_PREFIX_LENGTH - 2; byteIndex++) {
            if (ipv6AddressBytes[byteIndex] != 0) {
                return false;
            }
        }

        return ipv6AddressBytes[IPV6_MAPPED_FF_INDEX_1] == (byte) 0xff
            && ipv6AddressBytes[IPV6_MAPPED_FF_INDEX_2] == (byte) 0xff;
    }
}