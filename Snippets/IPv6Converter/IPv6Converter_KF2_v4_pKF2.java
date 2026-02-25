package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public final class IPv6Converter {

    private static final int IPV6_LENGTH = 16;
    private static final int IPV4_LENGTH = 4;
    private static final int IPV4_START_INDEX_IN_IPV6 = 12;
    private static final int IPV6_MAPPED_PREFIX_LENGTH = 10;
    private static final byte IPV6_MAPPED_BYTE = (byte) 0xff;

    private IPv6Converter() {
        // Prevent instantiation
    }

    /**
     * Converts an IPv4 address string to its IPv6-mapped IPv4 representation.
     * Example: "192.168.0.1" -> "::ffff:192.168.0.1"
     *
     * @param ipv4Address IPv4 address in dotted-decimal notation
     * @return IPv6-mapped IPv4 address string
     * @throws UnknownHostException if the IPv4 address is null, empty, or invalid
     */
    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        validateIpv4Input(ipv4Address);

        InetAddress ipv4 = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = ipv4.getAddress();
        byte[] ipv6Bytes = buildIpv6MappedBytes(ipv4Bytes);

        return buildIpv6MappedString(ipv6Bytes);
    }

    /**
     * Converts an IPv6-mapped IPv4 address string to its IPv4 representation.
     * Example: "::ffff:192.168.0.1" -> "192.168.0.1"
     *
     * @param ipv6Address IPv6-mapped IPv4 address string
     * @return IPv4 address in dotted-decimal notation
     * @throws UnknownHostException     if the IPv6 address is invalid
     * @throws IllegalArgumentException if the address is not IPv6-mapped IPv4
     */
    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6 = InetAddress.getByName(ipv6Address);
        byte[] ipv6Bytes = ipv6.getAddress();

        if (!isValidIpv6MappedIpv4(ipv6Bytes)) {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }

        byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, IPV4_START_INDEX_IN_IPV6, IPV6_LENGTH);
        InetAddress ipv4 = InetAddress.getByAddress(ipv4Bytes);
        return ipv4.getHostAddress();
    }

    /**
     * Checks whether the given 16-byte array represents an IPv6-mapped IPv4 address.
     * Expected format (bytes): 0:0:0:0:0:0:0:0:0:0:ff:ff:w.x.y.z
     *
     * @param ipv6Bytes 16-byte IPv6 address
     * @return true if the address is IPv6-mapped IPv4, false otherwise
     */
    private static boolean isValidIpv6MappedIpv4(byte[] ipv6Bytes) {
        if (ipv6Bytes.length != IPV6_LENGTH) {
            return false;
        }

        for (int i = 0; i < IPV6_MAPPED_PREFIX_LENGTH; i++) {
            if (ipv6Bytes[i] != 0) {
                return false;
            }
        }

        return ipv6Bytes[10] == IPV6_MAPPED_BYTE && ipv6Bytes[11] == IPV6_MAPPED_BYTE;
    }

    private static void validateIpv4Input(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }
    }

    private static byte[] buildIpv6MappedBytes(byte[] ipv4Bytes) {
        byte[] ipv6Bytes = new byte[IPV6_LENGTH];
        ipv6Bytes[10] = IPV6_MAPPED_BYTE;
        ipv6Bytes[11] = IPV6_MAPPED_BYTE;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, IPV4_START_INDEX_IN_IPV6, IPV4_LENGTH);
        return ipv6Bytes;
    }

    private static String buildIpv6MappedString(byte[] ipv6Bytes) {
        StringBuilder ipv6String = new StringBuilder("::ffff:");
        for (int i = IPV4_START_INDEX_IN_IPV6; i < IPV6_LENGTH; i++) {
            ipv6String.append(ipv6Bytes[i] & 0xFF);
            if (i < IPV6_LENGTH - 1) {
                ipv6String.append('.');
            }
        }
        return ipv6String.toString();
    }
}