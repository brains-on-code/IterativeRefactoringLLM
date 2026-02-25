package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Utility class for converting between IPv4 and IPv6-mapped IPv4 addresses.
 *
 * <p>IPv6-mapped IPv4 addresses have the form {@code ::ffff:w.x.y.z}, where
 * {@code w.x.y.z} is a standard IPv4 address.</p>
 */
public final class Ipv4Ipv6Mapper {

    private static final int IPV6_BYTE_LENGTH = 16;
    private static final int IPV4_BYTE_LENGTH = 4;
    private static final int IPV4_START_INDEX_IN_IPV6 = 12;
    private static final int IPV4_MAPPED_PREFIX_FF_INDEX_1 = 10;
    private static final int IPV4_MAPPED_PREFIX_FF_INDEX_2 = 11;

    private Ipv4Ipv6Mapper() {
        // Prevent instantiation of utility class.
    }

    /**
     * Converts an IPv4 address string (e.g. {@code "192.0.2.128"}) to its
     * IPv6-mapped IPv4 representation (e.g. {@code "::ffff:192.0.2.128"}).
     *
     * @param ipv4Address the IPv4 address string
     * @return the IPv6-mapped IPv4 address string
     * @throws UnknownHostException if the input is null, empty, or not a valid IPv4 address
     */
    public static String toIpv6MappedIpv4(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress inetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = inetAddress.getAddress();

        byte[] ipv6Bytes = new byte[IPV6_BYTE_LENGTH];
        ipv6Bytes[IPV4_MAPPED_PREFIX_FF_INDEX_1] = (byte) 0xff;
        ipv6Bytes[IPV4_MAPPED_PREFIX_FF_INDEX_2] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, IPV4_START_INDEX_IN_IPV6, IPV4_BYTE_LENGTH);

        StringBuilder result = new StringBuilder("::ffff:");
        for (int i = IPV4_START_INDEX_IN_IPV6; i < IPV6_BYTE_LENGTH; i++) {
            result.append(ipv6Bytes[i] & 0xFF);
            if (i < IPV6_BYTE_LENGTH - 1) {
                result.append('.');
            }
        }
        return result.toString();
    }

    /**
     * Extracts the IPv4 address from an IPv6-mapped IPv4 address string
     * (e.g. {@code "::ffff:192.0.2.128"} -> {@code "192.0.2.128"}).
     *
     * @param ipv6MappedAddress the IPv6-mapped IPv4 address string
     * @return the extracted IPv4 address string
     * @throws UnknownHostException if the input is not a valid IP address
     * @throws IllegalArgumentException if the address is not an IPv6-mapped IPv4 address
     */
    public static String fromIpv6MappedIpv4(String ipv6MappedAddress) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipv6MappedAddress);
        byte[] ipv6Bytes = inetAddress.getAddress();

        if (!isIpv4MappedIpv6(ipv6Bytes)) {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }

        byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, IPV4_START_INDEX_IN_IPV6, IPV6_BYTE_LENGTH);
        InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4Bytes);
        return ipv4InetAddress.getHostAddress();
    }

    /**
     * Checks whether the given 16-byte array represents an IPv6-mapped IPv4 address.
     *
     * <p>Format: {@code 0:0:0:0:0:ffff:w.x.y.z}</p>
     *
     * @param addressBytes a 16-byte IPv6 address
     * @return {@code true} if the address is IPv6-mapped IPv4, {@code false} otherwise
     */
    private static boolean isIpv4MappedIpv6(byte[] addressBytes) {
        if (addressBytes.length != IPV6_BYTE_LENGTH) {
            return false;
        }

        for (int i = 0; i < IPV4_MAPPED_PREFIX_FF_INDEX_1; i++) {
            if (addressBytes[i] != 0) {
                return false;
            }
        }

        return addressBytes[IPV4_MAPPED_PREFIX_FF_INDEX_1] == (byte) 0xff
                && addressBytes[IPV4_MAPPED_PREFIX_FF_INDEX_2] == (byte) 0xff;
    }
}