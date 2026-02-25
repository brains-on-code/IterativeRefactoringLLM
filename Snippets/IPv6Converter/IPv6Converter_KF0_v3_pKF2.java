package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Utility methods for converting between IPv4 and IPv6-mapped IPv4 addresses.
 *
 * <p>Supported operations:
 * <ul>
 *   <li>Convert IPv4 to IPv6-mapped IPv6 (e.g., "192.0.2.128" → "::ffff:192.0.2.128")</li>
 *   <li>Extract IPv4 from IPv6-mapped IPv6 (e.g., "::ffff:192.0.2.128" → "192.0.2.128")</li>
 * </ul>
 */
public final class IPv6Converter {

    private static final int IPV6_LENGTH = 16;
    private static final int IPV4_LENGTH = 4;
    private static final int IPV4_OFFSET_IN_IPV6 = 12;

    /**
     * Index of the first 0xff byte in an IPv6-mapped IPv4 address:
     * 0:0:0:0:0:ffff:w.x.y.z
     */
    private static final int IPV6_MAPPED_PREFIX_FF_INDEX_1 = 10;

    /**
     * Index of the second 0xff byte in an IPv6-mapped IPv4 address:
     * 0:0:0:0:0:ffff:w.x.y.z
     */
    private static final int IPV6_MAPPED_PREFIX_FF_INDEX_2 = 11;

    private IPv6Converter() {
        // Utility class; prevent instantiation.
    }

    /**
     * Converts an IPv4 address (e.g., "192.0.2.128") to an IPv6-mapped IPv6 address.
     * Example: "192.0.2.128" → "::ffff:192.0.2.128"
     *
     * @param ipv4Address IPv4 address in string format
     * @return IPv6-mapped IPv6 address
     * @throws UnknownHostException if the IPv4 address is invalid or empty
     */
    public static String ipv4ToIpv6(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress ipv4 = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = ipv4.getAddress();

        byte[] ipv6Bytes = new byte[IPV6_LENGTH];
        ipv6Bytes[IPV6_MAPPED_PREFIX_FF_INDEX_1] = (byte) 0xff;
        ipv6Bytes[IPV6_MAPPED_PREFIX_FF_INDEX_2] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, IPV4_OFFSET_IN_IPV6, IPV4_LENGTH);

        StringBuilder ipv6String = new StringBuilder("::ffff:");
        for (int i = IPV4_OFFSET_IN_IPV6; i < IPV6_LENGTH; i++) {
            ipv6String.append(ipv6Bytes[i] & 0xFF);
            if (i < IPV6_LENGTH - 1) {
                ipv6String.append('.');
            }
        }
        return ipv6String.toString();
    }

    /**
     * Extracts the IPv4 address from an IPv6-mapped IPv6 address.
     * Example: "::ffff:192.0.2.128" → "192.0.2.128"
     *
     * @param ipv6Address IPv6 address in string format
     * @return extracted IPv4 address
     * @throws UnknownHostException     if the IPv6 address is invalid
     * @throws IllegalArgumentException if the address is not IPv6-mapped IPv4
     */
    public static String ipv6ToIpv4(String ipv6Address) throws UnknownHostException {
        InetAddress ipv6 = InetAddress.getByName(ipv6Address);
        byte[] ipv6Bytes = ipv6.getAddress();

        if (!isValidIpv6MappedIpv4(ipv6Bytes)) {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }

        byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, IPV4_OFFSET_IN_IPV6, IPV6_LENGTH);
        InetAddress ipv4 = InetAddress.getByAddress(ipv4Bytes);
        return ipv4.getHostAddress();
    }

    /**
     * Checks whether the given byte array represents an IPv6-mapped IPv4 address.
     * An IPv6-mapped IPv4 address has the form 0:0:0:0:0:ffff:w.x.y.z.
     *
     * @param ipv6Bytes byte array representation of the IPv6 address
     * @return {@code true} if the address is IPv6-mapped IPv4; {@code false} otherwise
     */
    private static boolean isValidIpv6MappedIpv4(byte[] ipv6Bytes) {
        if (ipv6Bytes.length != IPV6_LENGTH) {
            return false;
        }

        // First 10 bytes must be zero.
        for (int i = 0; i < IPV6_MAPPED_PREFIX_FF_INDEX_1; i++) {
            if (ipv6Bytes[i] != 0) {
                return false;
            }
        }

        // Bytes 10 and 11 must be 0xff.
        return ipv6Bytes[IPV6_MAPPED_PREFIX_FF_INDEX_1] == (byte) 0xff
            && ipv6Bytes[IPV6_MAPPED_PREFIX_FF_INDEX_2] == (byte) 0xff;
    }
}