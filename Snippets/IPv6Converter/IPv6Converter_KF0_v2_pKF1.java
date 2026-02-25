package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * A utility class for converting between IPv6 and IPv4 addresses.
 *
 * - Converts IPv4 to IPv6-mapped IPv6 address.
 * - Extracts IPv4 address from IPv6-mapped IPv6.
 * - Handles exceptions for invalid inputs.
 *
 * @author Hardvan
 */
public final class IPv6Converter {

    private static final int IPV6_BYTE_LENGTH = 16;
    private static final int IPV4_BYTE_LENGTH = 4;
    private static final int IPV6_MAPPED_PREFIX_LENGTH = 12;
    private static final int IPV6_MAPPED_FF_BYTE_INDEX_1 = 10;
    private static final int IPV6_MAPPED_FF_BYTE_INDEX_2 = 11;

    private IPv6Converter() {
    }

    /**
     * Converts an IPv4 address (e.g., "192.0.2.128") to an IPv6-mapped IPv6 address.
     * Example: IPv4 "192.0.2.128" -> IPv6 "::ffff:192.0.2.128"
     *
     * @param ipv4Address The IPv4 address in string format.
     * @return The corresponding IPv6-mapped IPv6 address.
     * @throws UnknownHostException If the IPv4 address is invalid.
     * @throws IllegalArgumentException If the IPv6 address is not a mapped IPv4 address.
     */
    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress ipv4InetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = ipv4InetAddress.getAddress();

        byte[] ipv6Bytes = new byte[IPV6_BYTE_LENGTH];
        ipv6Bytes[IPV6_MAPPED_FF_BYTE_INDEX_1] = (byte) 0xff;
        ipv6Bytes[IPV6_MAPPED_FF_BYTE_INDEX_2] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, IPV6_MAPPED_PREFIX_LENGTH, IPV4_BYTE_LENGTH);

        StringBuilder ipv6StringBuilder = new StringBuilder("::ffff:");
        for (int byteIndex = IPV6_MAPPED_PREFIX_LENGTH; byteIndex < IPV6_BYTE_LENGTH; byteIndex++) {
            ipv6StringBuilder.append(ipv6Bytes[byteIndex] & 0xFF);
            if (byteIndex < IPV6_BYTE_LENGTH - 1) {
                ipv6StringBuilder.append('.');
            }
        }
        return ipv6StringBuilder.toString();
    }

    /**
     * Extracts the IPv4 address from an IPv6-mapped IPv6 address.
     * Example: IPv6 "::ffff:192.0.2.128" -> IPv4 "192.0.2.128"
     *
     * @param ipv6Address The IPv6 address in string format.
     * @return The extracted IPv4 address.
     * @throws UnknownHostException If the IPv6 address is invalid or not a mapped IPv4 address.
     */
    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6InetAddress = InetAddress.getByName(ipv6Address);
        byte[] ipv6Bytes = ipv6InetAddress.getAddress();

        if (isIpv6MappedIpv4Address(ipv6Bytes)) {
            byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, IPV6_MAPPED_PREFIX_LENGTH, IPV6_BYTE_LENGTH);
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4Bytes);
            return ipv4InetAddress.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
    }

    /**
     * Helper function to check if the given byte array represents
     * an IPv6-mapped IPv4 address (prefix 0:0:0:0:0:ffff).
     *
     * @param ipv6Bytes Byte array representation of the IPv6 address.
     * @return True if the address is IPv6-mapped IPv4, otherwise false.
     */
    private static boolean isIpv6MappedIpv4Address(byte[] ipv6Bytes) {
        if (ipv6Bytes.length != IPV6_BYTE_LENGTH) {
            return false;
        }

        for (int byteIndex = 0; byteIndex < IPV6_MAPPED_PREFIX_LENGTH - 2; byteIndex++) {
            if (ipv6Bytes[byteIndex] != 0) {
                return false;
            }
        }

        return ipv6Bytes[IPV6_MAPPED_FF_BYTE_INDEX_1] == (byte) 0xff
            && ipv6Bytes[IPV6_MAPPED_FF_BYTE_INDEX_2] == (byte) 0xff;
    }
}