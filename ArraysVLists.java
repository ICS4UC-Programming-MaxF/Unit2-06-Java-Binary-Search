/*
 * This program reads lists of integers from text files and calculates
 * the mean, median and mode of each list.
 *
 * @author  Your Name
 * @version 1.0
 * @since   2026-11-03
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is the Statistics program.
 */
public final class ArraysVLists {

    /**
     * The names of the files to read.
     */
    private static final String[] FILE_NAMES = {
        "Unit2-06-Set1.txt",
        "Unit2-06-Set2.txt",
        "Unit2-06-Set3.txt"
    };

    /**
     * Prevents instantiation.
     * Throws exception if this ever is instantiated.
     *
     * @throws IllegalStateException if this ever is instantiated.
     */
    private ArraysVLists() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
     * Calculates the mean of an array of numbers.
     *
     * @param numbers the array of numbers
     * @return the mean
     */
    public static double calcMean(final int[] numbers) {
        double sum = 0;

        for (int value : numbers) {
            sum += value;
        }

        return sum / numbers.length;
    }

    /**
     * Calculates the median of a sorted array of numbers.
     *
     * @param numbers the sorted array of numbers
     * @return the median
     */
    public static double calcMedian(final int[] numbers) {
        final int middle = numbers.length / 2;

        if (numbers.length % 2 == 1) {
            return numbers[middle];
        }

        return (numbers[middle - 1] + numbers[middle]) / 2.0;
    }

    /**
     * Calculates the mode(s) of a sorted array of numbers.
     *
     * @param numbers the sorted array of numbers
     * @return a list of the modes, empty if no number repeats
     */
    public static ArrayList<Integer> calcMode(final int[] numbers) {
        final ArrayList<Integer> modes = new ArrayList<>();
        int maxCount = 0;
        int index = 0;

        while (index < numbers.length) {
            int count = 1;

            while (index + count < numbers.length
                    && numbers[index + count] == numbers[index]) {
                count++;
            }

            if (count > maxCount) {
                maxCount = count;
                modes.clear();
                modes.add(numbers[index]);
            } else if (count == maxCount) {
                modes.add(numbers[index]);
            }

            index += count;
        }

        if (maxCount == 1) {
            modes.clear();
        }

        return modes;
    }

    /**
     * The starting main() function.
     *
     * @param args No args will be used
     */
    public static void main(final String[] args) {
        for (String fileName : FILE_NAMES) {
            System.out.println("\nReading " + fileName);
            final ArrayList<Integer> list = new ArrayList<>();

            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNext()) {
                    if (scanner.hasNextInt()) {
                        list.add(scanner.nextInt());
                    } else {
                        System.out.println(
                                "Skipping invalid value: " + scanner.next());
                    }
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Error: could not find " + fileName);
                continue;
            }

            if (list.isEmpty()) {
                System.out.println("Error: " + fileName + " has no numbers");
                continue;
            }

            // Copy the list to a fixed-length array
            final int[] numbers = new int[list.size()];
            for (int index = 0; index < list.size(); index++) {
                numbers[index] = list.get(index);
            }

            System.out.print("Numbers:");
            for (int number : numbers) {
                System.out.print(" " + number);
            }
            System.out.println();

            Arrays.sort(numbers);

            // clone() passes a copy so the functions cannot change the original
            final double mean = calcMean(numbers.clone());
            final double median = calcMedian(numbers.clone());
            final ArrayList<Integer> modes = calcMode(numbers.clone());

            System.out.println("Mean: " + mean);
            System.out.println("Median: " + median);
            if (modes.isEmpty()) {
                System.out.println("Mode: none");
            } else {
                System.out.println("Mode: " + modes);
            }
        }

        System.out.println("\nDone.");
    }
}