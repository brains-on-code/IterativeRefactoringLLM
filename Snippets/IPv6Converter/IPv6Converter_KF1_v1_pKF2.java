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
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Converts an IPv4 address string (e.g. {@code "192.0.2.128"}) to its
     * IPv6-mapped IPv4 representation (e.g. {@code "::ffff:192.0.2.128"}).
     *
     * @param ipv4Address the IPv4 address string
     * @return the IPv6-mapped IPv4 address string
     * @throws UnknownHostException if the input is null, empty, or not a valid IPv4 address
     */
    public static String method1(String ipv4Address) throws UnknownHostException {
        if (ipv4Address == null || ipv4Address.isEmpty()) {
            throw new UnknownHostException("IPv4 address is empty.");
        }

        InetAddress inetAddress = InetAddress.getByName(ipv4Address);
        byte[] ipv4Bytes = inetAddress.getAddress();

        // Build a 16-byte IPv6 address with the IPv4-mapped prefix ::ffff:0:0/96
        byte[] ipv6Bytes = new byte[16];
        ipv6Bytes[10] = (byte) 0xff;
        ipv6Bytes[11] = (byte) 0xff;
        System.arraycopy(ipv4Bytes, 0, ipv6Bytes, 12, 4);

        // Construct the textual representation ::ffff:w.x.y.z
        StringBuilder result = new StringBuilder("::ffff:");
        for (int i = 12; i < 16; i++) {
            result.append(ipv6Bytes[i] & 0xFF);
            if (i < 15) {
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
    public static String method2(String ipv6MappedAddress) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipv6MappedAddress);
        byte[] ipv6Bytes = inetAddress.getAddress();

        if (isIpv4MappedIpv6(ipv6Bytes)) {
            byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, 12, 16);
            InetAddress ipv4InetAddress = InetAddress.getByAddress(ipv4Bytes);
            return ipv4InetAddress.getHostAddress();
        } else {
            throw new IllegalArgumentException("Not a valid IPv6-mapped IPv4 address.");
        }
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
        if (addressBytes.length != 16) {
            return false;
        }

        // First 10 bytes must be 0
        for (int i = 0; i < 10; i++) {
            if (addressBytes[i] != 0) {
                return false;
            }
        }

        // Next two bytes must be 0xff 0xff
        return addressBytes[10] == (byte) 0xff && addressBytes[11] == (byte) 0xff;
    }
}