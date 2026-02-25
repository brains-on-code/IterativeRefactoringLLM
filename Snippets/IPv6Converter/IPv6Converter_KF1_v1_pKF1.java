package com.thealgorithms.conversions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Utility class for IPv4/IPv6 mapped address conversions.
 */
public final class IPv4IPv6Mapper {

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
        byte[] ipv4Bytes = ipv4InetAddress.getAddress();

        byte[] ipv6Bytes = new byte[16];
        ipv6Bytes[10] = (byte) 0xff;
        ipv6Bytes[11] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, 12, 4);

        StringBuilder mappedAddressBuilder = new StringBuilder("::ffff:");
        for (int i = 12; i < 16; i++) {
            mappedAddressBuilder.append(ipv6Bytes[i] & 0xFF);
            if (i < 15) {
                mappedAddressBuilder.append('.');
            }
        }
        return mappedAddressBuilder.toString();
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
        byte[] addressBytes = inetAddress.getAddress();

        if (isIPv6MappedIPv4(addressBytes)) {
            byte[] ipv4Bytes = Arrays.copyOfRange(addressBytes, 12, 16);
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4Bytes);
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
        if (addressBytes.length != 16) {
            return false;
        }

        for (int i = 0; i < 10; i++) {
            if (addressBytes[i] != 0) {
                return false;
            }
        }

        return addressBytes[10] == (byte) 0xff && addressBytes[11] == (byte) 0xff;
    }
}