package com.example.kakaopay.utils;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    private static RandomUtils instance = new RandomUtils();

    public static RandomUtils getInstance() {
        return instance;
    }

    public int getRandom(int limit) {
        return random.nextInt(limit);
    }

    public String generateToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
