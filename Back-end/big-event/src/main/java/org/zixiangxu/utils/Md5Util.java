package org.zixiangxu.utils;/*
 * @author Zixiang Xu
 * @date 2024/1/3 12:24
 */


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for MD5 cryptographic hashing.
 * Provides methods to generate MD5 hash of strings and to check if a given string's MD5 hash matches a known hash.
 */
public class Md5Util {
    /**
     * Default password string combination used to convert bytes to hexadecimal characters.
     * <p>
     * This default combination is used by apache to verify the correctness of downloaded files.
     */
    protected static char[] hexDigits =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * MessageDigest instance for MD5 hashing.
     */
    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException md5Exception) {
            System.err.println(
                Md5Util.class.getName() + "Initialization Failed, "
                    + "MessageDigest does not support MD5Util.");
            md5Exception.printStackTrace(System.err);
        }
    }

    /**
     * Generates an MD5 hash for a given string.
     *
     * @param str The string to hash.
     * @return A string representing the hexadecimal MD5 hash.
     */
    public static String getMD5String(String str) {
        return getMD5String(str.getBytes());
    }

    /**
     * Checks if a given string's MD5 hash matches a known MD5 hash.
     *
     * @param password  The string to hash and check.
     * @param md5PwdStr The known MD5 hash to compare against.
     * @return True if the generated hash matches the known hash, false otherwise.
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String str = getMD5String(password);
        return str.equals(md5PwdStr);
    }

    /**
     * Generates an MD5 hash for a byte array.
     *
     * @param bytes The byte array to hash.
     * @return A string representing the hexadecimal MD5 hash.
     */
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    // Converts a byte array to a hexadecimal string.
    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, bytes.length);
    }

    // Converts a byte array to a hexadecimal string,
    // specifying a range in the array.
    private static String bufferToHex(byte[] bytes, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        for (int l = 0; l < n; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    // Appends a byte as a pair of hexadecimal characters to a StringBuffer.
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        // Take the upper 4 bits of the byte and convert them.
        // ">>" is the logical right shift,
        //      -- which shifts the sign bits to the right together.
        // Note that no difference between the two symbols is found here.
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        // Digitally converts the lower 4 bits of a byte
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}
