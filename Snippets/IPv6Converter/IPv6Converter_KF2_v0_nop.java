package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;


public final class IPv6Converter {
    private IPv6Converter() {
    }


    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress ipv4 = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = ipv4.getAddress();

        byte[] ipv6Bytes = new byte[16];
        ipv6Bytes[10] = (byte) 0xff;
        ipv6Bytes[11] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, 12, 4);

        StringBuilder ipv6String = new StringBuilder("::ffff:");
        for (int i = 12; i < 16; i++) {
            ipv6String.append(ipv6Bytes[i] & 0xFF);
            if (i < 15) {
                ipv6String.append('.');
            }
        }
        return ipv6String.toString();
    }


    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6 = InetAddress.getByName(ipv6Address);
        byte[] ipv6Bytes = ipv6.getAddress();

        if (isValidIpv6MappedIpv4(ipv6Bytes)) {
            byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, 12, 16);
            InetAddress ipv4 = InetAddress.getByAddress(ipv4Bytes);
            return ipv4.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
    }


    private static boolean isValidIpv6MappedIpv4(byte[] ipv6Bytes) {
        if (ipv6Bytes.length != 16) {
            return false;
        }

        for (int i = 0; i < 10; i++) {
            if (ipv6Bytes[i] != 0) {
                return false;
            }
        }

        return ipv6Bytes[10] == (byte) 0xff && ipv6Bytes[11] == (byte) 0xff;
    }
}
