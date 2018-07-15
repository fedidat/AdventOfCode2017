package aoc17;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Day1 {

    @SuppressWarnings("unused")
    private static void adventOfCode1_2() throws IOException {
        String input = FileUtils.readFileToString(new File("inputs/aoc1.txt"));
        int sum = 0;
        for(int i=0; i<input.length(); i++){
            int indexToCompare = (i + input.length()/2) % input.length();
            if(Integer.valueOf(input.charAt(i)) == Integer.valueOf(input.charAt(indexToCompare)))
                sum += Character.getNumericValue(input.charAt(i));
        }
        System.out.println(sum);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode1_1() throws IOException {
        String input = FileUtils.readFileToString(new File("inputs/aoc1.txt"));
        int sum = 0;
        for(int i=0; i<input.length(); i++){
            if(Integer.valueOf(input.charAt(i)) == Integer.valueOf(input.charAt(((i+1)%(input.length())))))
                sum += Character.getNumericValue(input.charAt(i));
        }
        System.out.println(sum);
    }
    
}