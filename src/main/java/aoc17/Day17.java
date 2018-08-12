package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Day17 {

    public static void main(String[] args) throws IOException {
        adventOfCode17_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode17_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc17.txt"));
        int forwardSteps = Integer.parseInt(inputStr), INSERTIONS = 50_000_000, currentPos = 0;
        List<Integer> buffer = new ArrayList<>(Arrays.asList(0));

        for(int currentInsertion = 1; currentInsertion <= INSERTIONS; currentInsertion++) {
            //Pretend we did insert a number, yet only consider insertions right after index 0
            currentPos = (currentPos + forwardSteps) % currentInsertion;
            if(currentPos == 0)
                buffer.add(currentPos + 1, currentInsertion);
            currentPos++;
        }
        System.out.println(buffer.get(1));
    }

    @SuppressWarnings("unused")
    private static void adventOfCode17_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc17.txt"));
        int forwardSteps = Integer.parseInt(inputStr), INSERTIONS = 2017, currentPos = 0;
        List<Integer> buffer = new ArrayList<>(Arrays.asList(0));

        for(int currentInsertion = 1; currentInsertion <= INSERTIONS; currentInsertion++) {
            currentPos = (currentPos + forwardSteps) % buffer.size();
            buffer.add(currentPos + 1, currentInsertion);
            currentPos++;
        }
        System.out.println(buffer.get(currentPos + 1));
    }
}
