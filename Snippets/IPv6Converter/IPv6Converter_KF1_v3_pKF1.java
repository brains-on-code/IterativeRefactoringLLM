package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Utility class for IPv4/IPv6 mapped address conversions.
 */
public final class IPv4IPv6Mapper {

    private static final int IPV6_ADDRESS_LENGTH_BYTES = 16;
    private static final int IPV4_ADDRESS_LENGTH_BYTES = 4;
    private static final int IPV6_MAPPED_PREFIX_LENGTH_BYTES = 12;
    private static final int IPV6_MAPPED_FF_BYTE_INDEX_1 = 10;
    private static final int IPV6_MAPPED_FF_BYTE_INDEX_2 = 11;

    private IPv4IPv6Mapper() {
    }

    /**
     * Converts an IPv4 address string (e.g., "192.0.2.128") to an IPv6-mapped IPv4
     * address string (e.g., "::ffff:192.0.2.128").
     *
     * @param ipv4Address IPv4 address in string form
     * @return IPv6-mapped IPv4 address string
     * @throws UnknownHostException if the IPv4 address is invalid or empty
     */
    public static String toIPv6MappedIPv4(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress ipv4InetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4AddressBytes = ipv4InetAddress.getAddress();

        byte[] ipv6AddressBytes = new byte[IPV6_ADDRESS_LENGTH_BYTES];
        ipv6AddressBytes[IPV6_MAPPED_FF_BYTE_INDEX_1] = (byte) 0xff;
        ipv6AddressBytes[IPV6_MAPPED_FF_BYTE_INDEX_2] = (byte) 0xff;
        System.arraycopy(
            ipv4AddressBytes,
            0,
            ipv6AddressBytes,
            IPV6_MAPPED_PREFIX_LENGTH_BYTES,
            IPV4_ADDRESS_LENGTH_BYTES
        );

        StringBuilder ipv6MappedAddressBuilder = new StringBuilder("::ffff:");
        for (int byteIndex = IPV6_MAPPED_PREFIX_LENGTH_BYTES; byteIndex < IPV6_ADDRESS_LENGTH_BYTES; byteIndex++) {
            ipv6MappedAddressBuilder.append(ipv6AddressBytes[byteIndex] & 0xFF);
            if (byteIndex < IPV6_ADDRESS_LENGTH_BYTES - 1) {
                ipv6MappedAddressBuilder.append('.');
            }
        }
        return ipv6MappedAddressBuilder.toString();
    }

    /**
     * Converts an IPv6-mapped IPv4 address string (e.g., "::ffff:192.0.2.128")
     * back to its IPv4 address string (e.g., "192.0.2.128").
     *
     * @param ipv6MappedAddress IPv6-mapped IPv4 address in string form
     * @return IPv4 address string
     * @throws UnknownHostException if the address is invalid
     * @throws IllegalArgumentException if the address is not an IPv6-mapped IPv4 address
     */
    public static String fromIPv6MappedIPv4(String ipv6MappedAddress) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipv6MappedAddress);
        byte[] fullAddressBytes = inetAddress.getAddress();

        if (isIPv6MappedIPv4(fullAddressBytes)) {
            byte[] ipv4AddressBytes =
                Arrays.copyOfRange(fullAddressBytes, IPV6_MAPPED_PREFIX_LENGTH_BYTES, IPV6_ADDRESS_LENGTH_BYTES);
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4AddressBytes);
            return ipv4InetAddress.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
    }

    /**
     * Checks whether the given 16-byte array represents an IPv6-mapped IPv4 address.
     *
     * @param addressBytes 16-byte IPv6 address
     * @return true if the address is IPv6-mapped IPv4, false otherwise
     */
    private static boolean isIPv6MappedIPv4(byte[] addressBytes) {
        if (addressBytes.length != IPV6_ADDRESS_LENGTH_BYTES) {
            return false;
        }

        for (int byteIndex = 0; byteIndex < IPV6_MAPPED_PREFIX_LENGTH_BYTES - 2; byteIndex++) {
            if (addressBytes[byteIndex] != 0) {
                return false;
            }
        }

        return addressBytes[IPV6_MAPPED_FF_BYTE_INDEX_1] == (byte) 0xff
            && addressBytes[IPV6_MAPPED_FF_BYTE_INDEX_2] == (byte) 0xff;
    }
}