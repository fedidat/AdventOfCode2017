package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

public class Day19 {

    public static void main(String[] args) throws IOException {
        adventOfCode19();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode19() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc19.txt"));

        char[][] input = Arrays.stream(inputStr.split("\n")).map(s -> s.toCharArray()).toArray(char[][]::new);
        int steps = 0, x = 0, y = IntStream.range(0, input[0].length).filter(c -> input[0][c] != ' ').findFirst().getAsInt();
        boolean vertical = true, rightDown = true;
        List<Character> letters = new ArrayList<>();
        while(x >= 0 && x < input.length && y >= 0 && y < input[0].length) {
            System.out.println(input[x][y]);
            if(input[x][y] == '+') {
                if(vertical && input[x][y-1] == '-') {
                    y--;
                    rightDown = false;
                }
                else if(vertical && input[x][y+1] == '-') {
                    y++;
                    rightDown = true;
                }
                else if(!vertical && input[x-1][y] == '|') {
                    x--;
                    rightDown = false;
                }
                else if(!vertical && input[x+1][y] == '|') {
                    x++;
                    rightDown = true;
                }
                else
                    break;
                vertical = !vertical;
                System.out.println("Turning to " + (vertical ? (rightDown ? "S" : "N") : (rightDown ? "E" : "W")));
                steps++;
                continue;
            }
            if(Character.isLetter(input[x][y])) {
                System.out.println("Adding " + input[x][y] + " to " + letters);
                letters.add(input[x][y]);
            }
            if(Character.isLetter(input[x][y]) || input[x][y] == '-' || input[x][y] == '|') {
                if(vertical){
                    if(rightDown)
                        x++;
                    else
                        x--;
                }
                else { //!vertical
                    if(rightDown)
                        y++;
                    else
                        y--;
                }
            }
            else {
                System.out.println("Illegal character!");
                break;
            }
            steps++;
        }
        System.out.println("Finished in " + steps + " steps, letters: " + letters.stream().map(e -> e.toString()).collect(Collectors.joining()));
    }
}
