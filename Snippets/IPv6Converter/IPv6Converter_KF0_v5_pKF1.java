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

    private static final int IPV6_ADDRESS_BYTE_LENGTH = 16;
    private static final int IPV4_ADDRESS_BYTE_LENGTH = 4;
    private static final int IPV6_MAPPED_IPV4_PREFIX_BYTE_LENGTH = 12;
    private static final int IPV6_MAPPED_IPV4_FF_FIRST_BYTE_INDEX = 10;
    private static final int IPV6_MAPPED_IPV4_FF_SECOND_BYTE_INDEX = 11;

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
        byte[] ipv4AddressBytes = ipv4InetAddress.getAddress();

        byte[] ipv6AddressBytes = new byte[IPV6_ADDRESS_BYTE_LENGTH];
        ipv6AddressBytes[IPV6_MAPPED_IPV4_FF_FIRST_BYTE_INDEX] = (byte) 0xff;
        ipv6AddressBytes[IPV6_MAPPED_IPV4_FF_SECOND_BYTE_INDEX] = (byte) 0xff;
        System.arraycopy(
            ipv4AddressBytes,
            0,
            ipv6AddressBytes,
            IPV6_MAPPED_IPV4_PREFIX_BYTE_LENGTH,
            IPV4_ADDRESS_BYTE_LENGTH
        );

        StringBuilder ipv6AddressBuilder = new StringBuilder("::ffff:");
        for (int byteIndex = IPV6_MAPPED_IPV4_PREFIX_BYTE_LENGTH; byteIndex < IPV6_ADDRESS_BYTE_LENGTH; byteIndex++) {
            ipv6AddressBuilder.append(ipv6AddressBytes[byteIndex] & 0xFF);
            if (byteIndex < IPV6_ADDRESS_BYTE_LENGTH - 1) {
                ipv6AddressBuilder.append('.');
            }
        }
        return ipv6AddressBuilder.toString();
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
        byte[] ipv6AddressBytes = ipv6InetAddress.getAddress();

        if (isIpv6MappedIpv4Address(ipv6AddressBytes)) {
            byte[] ipv4AddressBytes =
                Arrays.copyOfRange(
                    ipv6AddressBytes,
                    IPV6_MAPPED_IPV4_PREFIX_BYTE_LENGTH,
                    IPV6_ADDRESS_BYTE_LENGTH
                );
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4AddressBytes);
            return ipv4InetAddress.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
    }

    /**
     * Helper function to check if the given byte array represents
     * an IPv6-mapped IPv4 address (prefix 0:0:0:0:0:ffff).
     *
     * @param ipv6AddressBytes Byte array representation of the IPv6 address.
     * @return True if the address is IPv6-mapped IPv4, otherwise false.
     */
    private static boolean isIpv6MappedIpv4Address(byte[] ipv6AddressBytes) {
        if (ipv6AddressBytes.length != IPV6_ADDRESS_BYTE_LENGTH) {
            return false;
        }

        for (int byteIndex = 0; byteIndex < IPV6_MAPPED_IPV4_PREFIX_BYTE_LENGTH - 2; byteIndex++) {
            if (ipv6AddressBytes[byteIndex] != 0) {
                return false;
            }
        }

        return ipv6AddressBytes[IPV6_MAPPED_IPV4_FF_FIRST_BYTE_INDEX] == (byte) 0xff
            && ipv6AddressBytes[IPV6_MAPPED_IPV4_FF_SECOND_BYTE_INDEX] == (byte) 0xff;
    }
}