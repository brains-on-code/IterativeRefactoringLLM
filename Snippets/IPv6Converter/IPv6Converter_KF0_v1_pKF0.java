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
    private static final int IPV6_MAPPED_FF_POSITION_1 = 10;
    private static final int IPV6_MAPPED_FF_POSITION_2 = 11;
    private static final String IPV6_MAPPED_PREFIX_STRING = "::ffff:";

    private IPv6Converter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an IPv4 address (e.g., "192.0.2.128") to an IPv6-mapped IPv6 address.
     * Example: IPv4 "192.0.2.128" -> IPv6 "::ffff:192.0.2.128"
     *
     * @param ipv4Address The IPv4 address in string format.
     * @return The corresponding IPv6-mapped IPv6 address.
     * @throws UnknownHostException If the IPv4 address is invalid.
     */
    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        validateIpv4Input(ipv4Address);

        InetAddress ipv4 = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = ipv4.getAddress();

        byte[] ipv6Bytes = createIpv6MappedAddress(ipv4Bytes);
        return formatIpv6MappedAddress(ipv6Bytes);
    }

    /**
     * Extracts the IPv4 address from an IPv6-mapped IPv6 address.
     * Example: IPv6 "::ffff:192.0.2.128" -> IPv4 "192.0.2.128"
     *
     * @param ipv6Address The IPv6 address in string format.
     * @return The extracted IPv4 address.
     * @throws UnknownHostException If the IPv6 address is invalid.
     * @throws IllegalArgumentException If the IPv6 address is not a mapped IPv4 address.
     */
    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6 = InetAddress.getByName(ipv6Address);
        byte[] ipv6Bytes = ipv6.getAddress();

        if (!isValidIpv6MappedIpv4(ipv6Bytes)) {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }

        byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, IPV6_MAPPED_PREFIX_LENGTH, IPV6_BYTE_LENGTH);
        InetAddress ipv4 = InetAddress.getByAddress(ipv4Bytes);
        return ipv4.getHostAddress();
    }

    /**
     * Helper function to check if the given byte array represents
     * an IPv6-mapped IPv4 address (prefix 0:0:0:0:0:ffff).
     *
     * @param ipv6Bytes Byte array representation of the IPv6 address.
     * @return True if the address is IPv6-mapped IPv4, otherwise false.
     */
    private static boolean isValidIpv6MappedIpv4(byte[] ipv6Bytes) {
        if (ipv6Bytes.length != IPV6_BYTE_LENGTH) {
            return false;
        }

        for (int i = 0; i < IPV6_MAPPED_FF_POSITION_1; i++) {
            if (ipv6Bytes[i] != 0) {
                return false;
            }
        }

        return ipv6Bytes[IPV6_MAPPED_FF_POSITION_1] == (byte) 0xff
            && ipv6Bytes[IPV6_MAPPED_FF_POSITION_2] == (byte) 0xff;
    }

    private static void validateIpv4Input(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }
    }

    private static byte[] createIpv6MappedAddress(byte[] ipv4Bytes) {
        byte[] ipv6Bytes = new byte[IPV6_BYTE_LENGTH];
        ipv6Bytes[IPV6_MAPPED_FF_POSITION_1] = (byte) 0xff;
        ipv6Bytes[IPV6_MAPPED_FF_POSITION_2] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, IPV6_MAPPED_PREFIX_LENGTH, IPV4_BYTE_LENGTH);
        return ipv6Bytes;
    }

    private static String formatIpv6MappedAddress(byte[] ipv6Bytes) {
        StringBuilder ipv6String = new StringBuilder(IPV6_MAPPED_PREFIX_STRING);
        for (int i = IPV6_MAPPED_PREFIX_LENGTH; i < IPV6_BYTE_LENGTH; i++) {
            ipv6String.append(ipv6Bytes[i] & 0xFF);
            if (i < IPV6_BYTE_LENGTH - 1) {
                ipv6String.append('.');
            }
        }
        return ipv6String.toString();
    }
}