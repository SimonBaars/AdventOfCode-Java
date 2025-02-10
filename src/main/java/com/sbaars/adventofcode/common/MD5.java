package com.sbaars.adventofcode.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final MessageDigest md;
    
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
    
    public static byte[] hash(String input) {
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public static boolean hasLeadingZeros(byte[] hash, int numZeros) {
        int fullBytes = numZeros / 2;
        int remainingNibbles = numZeros % 2;
        
        // Check full bytes
        for (int i = 0; i < fullBytes; i++) {
            if (hash[i] != 0) return false;
        }
        
        // Check remaining nibble if needed
        return remainingNibbles == 0 || (hash[fullBytes] & 0xF0) == 0;
    }
    
    public static char toHexChar(int nibble) {
        return (char) (nibble < 10 ? ('0' + nibble) : ('a' + (nibble - 10)));
    }
} 