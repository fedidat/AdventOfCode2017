package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;


public class App {

    public static void main(String[] args) throws IOException {
        adventOfCode14_1();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode14_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc14.txt"));

    }

    @SuppressWarnings("unused")
    private static void adventOfCode14_1() throws IOException {
        final String inputStr = FileUtils.readFileToString(new File("inputs/aoc14.txt"));
        final int[] countOfOnes = { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4 };
        System.out.println(
            IntStream.range(0, 128).map(i -> //for 0 to 127
                    knotHash(inputStr + "-" + String.valueOf(i)) //concatenate the round
                    .chars().mapToObj(c -> Character.digit(c, 16)) //map to hex int
                    .mapToInt(c -> countOfOnes[c]) //map to count of ones in the hex
                    .sum() //sum the counts of ones
        ).sum()); //sum the rounds and print
    }

    //First helper function for this AoC (excluding one recursive function)
    //Almost a straight copy-paste of day 10 part 2
    @SuppressWarnings("unused")
    private static String knotHash(String inputStr) {
        final int SPAN_SIZE = 256, NUM_OF_ROUNDS = 64, BLOCK_SIZE = 16, BLOCKS = (int) Math.ceil(SPAN_SIZE / BLOCK_SIZE);
        int[] input = IntStream.concat(inputStr.chars(), IntStream.of(17, 31, 73, 47, 23)).toArray();
        int[] span = IntStream.range(0, SPAN_SIZE).toArray();
        int currentPosition = 0, currentSkip = 0;

        //64 hashing rounds
        for(int round = 0; round < NUM_OF_ROUNDS; round++) {
            for(int length : input) {
                //reverse from currentPosition to currentPosition + length
                for(int j=0; j<length / 2; j++) {
                    int temp = span[(currentPosition + j) % SPAN_SIZE];
                    span[(currentPosition + j) % SPAN_SIZE] = span[(currentPosition + length - j - 1) % SPAN_SIZE];
                    span[(currentPosition + length - j - 1) % SPAN_SIZE] = temp;
                }

                //reposition then update skip
                currentPosition = (currentPosition + length + currentSkip++) % SPAN_SIZE;
            }
        }

        //sparse to dense hash
        int[] denseSpan = new int[BLOCKS];
        for(int i=0; i < SPAN_SIZE; i++) {
            denseSpan[i / BLOCK_SIZE] ^= span[i];
        }

        //convert dense span to hexa then concat to hash
        return IntStream.of(denseSpan).mapToObj(i-> String.format("%02x", i)).collect(Collectors.joining(""));
    }
}
