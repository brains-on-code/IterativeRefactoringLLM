package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Utility class for converting between IPv4 and IPv6-mapped IPv4 addresses.
 */
public final class Ipv4Ipv6Mapper {

    private static final int IPV6_BYTE_LENGTH = 16;
    private static final int IPV4_BYTE_LENGTH = 4;
    private static final int IPV4_START_INDEX_IN_IPV6 = 12;
    private static final byte IPV6_MAPPED_PREFIX_BYTE = (byte) 0xff;
    private static final String IPV6_MAPPED_PREFIX = "::ffff:";

    private Ipv4Ipv6Mapper() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an IPv4 address string (e.g., "192.0.2.128") to its IPv6-mapped
     * IPv4 representation (e.g., "::ffff:192.0.2.128").
     *
     * @param ipv4Address IPv4 address in string form
     * @return IPv6-mapped IPv4 address string
     * @throws UnknownHostException if the IPv4 address is null, empty, or invalid
     */
    public static String toIpv6MappedIpv4(String ipv4Address) throws UnknownHostException {
        validateIpv4Input(ipv4Address);

        InetAddress inetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = inetAddress.getAddress();

        byte[] ipv6Bytes = createIpv6MappedBytes(ipv4Bytes);

        return buildIpv6MappedString(ipv6Bytes);
    }

    /**
     * Converts an IPv6-mapped IPv4 address string (e.g., "::ffff:192.0.2.128")
     * back to its IPv4 representation (e.g., "192.0.2.128").
     *
     * @param ipv6MappedAddress IPv6-mapped IPv4 address in string form
     * @return IPv4 address string
     * @throws UnknownHostException if the address is invalid
     * @throws IllegalArgumentException if the address is not an IPv6-mapped IPv4 address
     */
    public static String fromIpv6MappedIpv4(String ipv6MappedAddress) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipv6MappedAddress);
        byte[] addressBytes = inetAddress.getAddress();

        if (!isIpv6MappedIpv4(addressBytes)) {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }

        byte[] ipv4Bytes = Arrays.copyOfRange(addressBytes, IPV4_START_INDEX_IN_IPV6, IPV6_BYTE_LENGTH);
        InetAddress ipv4Address = InetAddress.getByAddress(ipv4Bytes);
        return ipv4Address.getHostAddress();
    }

    /**
     * Checks whether the given 16-byte array represents an IPv6-mapped IPv4 address.
     *
     * @param addressBytes 16-byte IPv6 address
     * @return true if the address is IPv6-mapped IPv4, false otherwise
     */
    private static boolean isIpv6MappedIpv4(byte[] addressBytes) {
        if (addressBytes.length != IPV6_BYTE_LENGTH) {
            return false;
        }

        for (int i = 0; i < IPV4_START_INDEX_IN_IPV6 - 2; i++) {
            if (addressBytes[i] != 0) {
                return false;
            }
        }

        return addressBytes[10] == IPV6_MAPPED_PREFIX_BYTE && addressBytes[11] == IPV6_MAPPED_PREFIX_BYTE;
    }

    private static void validateIpv4Input(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }
    }

    private static byte[] createIpv6MappedBytes(byte[] ipv4Bytes) {
        byte[] ipv6Bytes = new byte[IPV6_BYTE_LENGTH];
        ipv6Bytes[10] = IPV6_MAPPED_PREFIX_BYTE;
        ipv6Bytes[11] = IPV6_MAPPED_PREFIX_BYTE;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, IPV4_START_INDEX_IN_IPV6, IPV4_BYTE_LENGTH);
        return ipv6Bytes;
    }

    private static String buildIpv6MappedString(byte[] ipv6Bytes) {
        StringBuilder result = new StringBuilder(IPV6_MAPPED_PREFIX);
        for (int i = IPV4_START_INDEX_IN_IPV6; i < IPV6_BYTE_LENGTH; i++) {
            result.append(ipv6Bytes[i] & 0xFF);
            if (i < IPV6_BYTE_LENGTH - 1) {
                result.append('.');
            }
        }
        return result.toString();
    }
}