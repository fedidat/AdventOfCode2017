package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

public class Day10 {

    public static void main(String[] args) throws IOException {
        adventOfCode10_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode10_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc10.txt"));
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
        String hash = IntStream.of(denseSpan).mapToObj(i-> String.format("%02x", i)).collect(Collectors.joining(""));
        System.out.println(hash);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode10_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc10.txt"));
        int[] input = Arrays.stream(inputStr.split(",")).mapToInt(Integer::parseInt).toArray();
        final int SPAN_SIZE = 256;
        int[] span = IntStream.range(0, SPAN_SIZE).toArray();
        int currentPosition = 0, currentSkip = 0;
        for(int length : input) {
            //reverse
            for(int j=0; j<length / 2; j++) {
                int temp = span[(currentPosition + j) % SPAN_SIZE];
                span[(currentPosition + j) % SPAN_SIZE] = span[(currentPosition + length - j - 1) % SPAN_SIZE];
                span[(currentPosition + length - j - 1) % SPAN_SIZE] = temp;
            }

            //reposition
            currentPosition = (currentPosition + length + currentSkip) % SPAN_SIZE;

            //update skip
            currentSkip++;
        }
        System.out.println(span[0] * span[1]);
    }

}
