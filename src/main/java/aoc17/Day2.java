package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class Day2 {

    @SuppressWarnings("unused")
    private static void adventOfCode2_2() throws IOException {
        int checksum = 0;
        String input = FileUtils.readFileToString(new File("inputs/aoc2.txt"));
        String[] rows = input.split("\n");
        for(String str : rows) {
            int[] values = Arrays.stream(str.split("\t")).mapToInt(Integer::parseInt).toArray();
            for(int i=0; i<values.length-1; i++) {
                for(int j=i+1; j<values.length; j++) {
                    if(values[i]%values[j] == 0)
                        checksum += values[i]/values[j];
                    else if(values[j]%values[i] == 0)
                        checksum += values[j]/values[i];
                }
            }
        }
        System.out.println(checksum);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode2_1() throws IOException {
        int checksum = 0;
        String input = FileUtils.readFileToString(new File("inputs/aoc2.txt"));
        String[] rows = input.split("\n");
        for(String str : rows) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for(int i : Stream.of(str.split("\t")).mapToInt(Integer::parseInt).toArray()) {
                max = Math.max(max, i);
                min = Math.min(min, i);
            }
            checksum += max - min;
        }
        System.out.println(checksum);
    }

}