package ru.dmitryobukhoff.numbergenerator.util;

import ru.dmitryobukhoff.numbergenerator.exception.SHA256AlgorithmException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class NumberGenerator {

    private static final int LENGTH = 5;

    public static String generateNumber(UUID uuid){
        try {

            byte[] data = uuid.toString().concat(Long.toString(System.nanoTime())).getBytes(StandardCharsets.UTF_8);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(data);

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return getRandomSubstring(hexString.toString());

        } catch (NoSuchAlgorithmException exception) {
            throw new SHA256AlgorithmException("SHA-256 not found");
        }
    }

    public static String getRandomSubstring(String input) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }
        return result.toString();
    }
}
