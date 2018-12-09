package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RandomStringGenerator {
    private static Random random = new Random();
    private static List<Character> chars;
    private static StringBuilder stringBuilder;

    static String getNextString(int length, boolean smallLetters, boolean bigLetters, boolean numbers) {
        initialize();
        populate(smallLetters, bigLetters, numbers);
        generate(length);

        return stringBuilder.toString();
    }

    private static void initialize() {
        chars = new ArrayList<>();
        stringBuilder = new StringBuilder();
    }

    private static void populate(boolean smallLetters, boolean bigLetters, boolean numbers) {
        if(numbers) {
            populateList(48, 57);
        }
        if(bigLetters) {
            populateList(65, 90);
        }
        if(smallLetters) {
            populateList(97, 122);
        }
    }

    private static void populateList(int leftLimit, int rightLimit) {
        for (; leftLimit <= rightLimit; leftLimit++)
        {
            chars.add((char) leftLimit);
        }
    }

    private static void generate(int length) {
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) random.nextInt(chars.size()));
        }
    }
}
