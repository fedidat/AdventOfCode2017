package aoc17;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Day9 {

    public static void main(String[] args) throws IOException {
        adventOfCode9_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode9_2() throws IOException {
        String input = FileUtils.readFileToString(new File("inputs/aoc9.txt"));
        int garbageCount = 0;
        boolean inGarbage = false, ignoring = false;
        for(char c : input.toCharArray()) {
            if(ignoring)
                ignoring = false;
            else if(inGarbage) {
                if(c == '!')
                    ignoring = true;
                else if(c == '>')
                    inGarbage = false;
                else 
                    garbageCount++; //just adding this to count the garbage chars
            }
            else if(c == '<')
                inGarbage = true;
        }
        System.out.println(garbageCount);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode9_1() throws IOException {
        String input = FileUtils.readFileToString(new File("inputs/aoc9.txt"));
        int score = 0, currentLevel = 0;
        boolean inGarbage = false, ignoring = false;
        for(char c : input.toCharArray()) {
            if(ignoring)
                ignoring = false;
            else if(inGarbage) {
                if(c == '!')
                    ignoring = true;
                else if(c == '>')
                    inGarbage = false;
            }
            else {
                if(c == '{')
                    currentLevel++;
                else if(c == '}')
                    score += currentLevel--;
                else if(c == '<')
                    inGarbage = true;
            }
        }
        System.out.println(score);
    }

}
