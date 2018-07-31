package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;


public class Day14 {

    public static void main(String[] args) throws IOException {
        adventOfCode14_2();
    }


    @SuppressWarnings("unused")
    private static void adventOfCode14_2() throws IOException {
        final String inputStr = FileUtils.readFileToString(new File("inputs/aoc14.txt"));

        //First, load everything to a real matrix
        final int[] countOfOnes = { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4 };
        int[][] data = new int[128][];
        for(int i=0; i<128; i++){
            int[] hashChars = Day10.knotHash(inputStr + "-" + String.valueOf(i)).chars().toArray();
            data[i] = new int[hashChars.length * 4];
            for(int j=0; j<hashChars.length; j++) {
                int hex = Character.digit(hashChars[j], 16);
                for(int hexBit=3; hex > 0; hexBit--){
                    data[i][j*4 + hexBit] = hex%2;
                    hex >>= 1;
                }
            }
        }

        //Now we'll do the real thing. Every time we meet a marked bit, we
        //go over its neighbors, and then their neighbors, etc, iteratively
        //using a Queue.
        class Point{ public Point(int x, int y){this.x=x;this.y=y;} public int x,y; }
        int clusterID = 10;
        for(int i=0; i<data.length; i++){
            for(int j=0; j<data[i].length; j++){
                if(data[i][j] == 1) {
                    Queue<Point> adjBits = new LinkedList<Point>(Arrays.asList(new Point(i,j)));

                    while(!adjBits.isEmpty()){
                        Point p=adjBits.remove();
                        data[p.x][p.y] = clusterID;
                        Point[] adj = { new Point(p.x+1, p.y), new Point(p.x-1, p.y), new Point(p.x, p.y+1), new Point(p.x, p.y-1) };
                        for(Point pAdj : adj)
                            if(pAdj.x >= 0 && pAdj.x < 128 && pAdj.y >= 0 && pAdj.y < 128 && data[pAdj.x][pAdj.y] == 1)
                                adjBits.add(pAdj);
                    }
                    clusterID++;
                }
            }
        }

        //We started at cluster ID 10
        System.out.println(clusterID-10);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode14_1() throws IOException {
        final String inputStr = FileUtils.readFileToString(new File("inputs/aoc14.txt"));
        final int[] countOfOnes = { 0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4 };
        System.out.println(
            IntStream.range(0, 128).map(i -> //for 0 to 127
                    Day10.knotHash(inputStr + "-" + String.valueOf(i)) //concatenate the round
                    .chars().mapToObj(c -> Character.digit(c, 16)) //map to hex int
                    .mapToInt(c -> countOfOnes[c]) //map to count of ones in the hex
                    .sum() //sum the counts of ones
        ).sum()); //sum the rounds and print
    }
}
