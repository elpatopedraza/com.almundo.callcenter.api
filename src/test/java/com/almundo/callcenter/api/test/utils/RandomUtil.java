package com.almundo.callcenter.api.test.utils;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that implements a group of useful methods for tests.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class RandomUtil
{
    /**
     * Generates an array of random words.
     *
     * @param numberOfWords the number of words to be generated.
     * @param lowestSize the lowest size possible of a word.
     * @param largestSize the largest size possible of a word.
     * @return an array of random words according to parameters.
     */
    public static String[] generateRandomWords(int numberOfWords, int lowestSize, int largestSize)
    {
        char startingChar = 'a';
        short maxAlphabetSize = 26;
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();

        for(int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(largestSize - lowestSize + 1)+ lowestSize];

            for(int j = 0; j < word.length; j++) {
                word[j] = (char)(startingChar + random.nextInt(maxAlphabetSize));
            }

            randomStrings[i] = new String(word);
        }

        return randomStrings;
    }

    /**
     * Gets a random enum value given its class.
     *
     * @param enumClass the class where enum value will be extracted.
     * @param <E> the enum class.
     * @return a random enum value given its class.
     */
    public static <E extends Enum<E>> E getRandomEnum(Class<E> enumClass)
    {
        List<E> enumValues = EnumUtils.getEnumList(enumClass);

        Random random = new Random();
        int randomPosition = random.nextInt(enumValues.size());

        return enumValues.get(randomPosition);
    }

    /**
     * Gets a random boolean.
     *
     * @return a random boolean.
     */
    public static boolean getRandomBoolean()
    {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Gets a random int from a range.
     *
     * @param min the range minimum value.
     * @param max the range maximum value.
     * @return a random int from the specified range.
     */
    public static int getRandomInt(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
