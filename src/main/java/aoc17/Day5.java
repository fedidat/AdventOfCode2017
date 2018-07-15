package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class Day5 {

    @SuppressWarnings("unused")
    private static void adventOfCode5_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc5.txt"));
        int[] input = Stream.of(inputStr.split("\n")).mapToInt(Integer::parseInt).toArray();

        //Full simulation
        int step = 0;
        for(int i=0; i>=0 && i<input.length; step++)
            i += (input[i] >= 3 ? input[i]-- : input[i]++);
        System.out.println(step);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode5_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc5.txt"));
        int[] input = Stream.of(inputStr.split("\n")).mapToInt(Integer::parseInt).toArray();

        //Full simulation
        int step = 0;
        for(int i=0; i>=0 && i<input.length; step++)
            i += input[i]++;
        System.out.println(step);
    }

}
