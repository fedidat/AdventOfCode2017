package aoc17;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Day11 {

    public static void main(String[] args) throws IOException {
        adventOfCode11_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode11_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc11.txt"));
    	String[] input = inputStr.split(",");
        int x=0, y=0, maxDist = 0;
        
        //strategy: move N-E on the NW-SE axis 
        for(String direction : input) {
        	switch (direction) {
			case "n": //north up
				y--;
				break;
			case "nw": //north up, west left
				y--;
				x--;
				break;
			case "ne": //north no move, east right
				x++;
				break;
			case "s": //south down
				y++;
				break;
			case "sw": //south no move, west left
				x--;
				break;
			case "se": //south down, east right
				y++;
				x++;
				break;
			}
        	int xc = x, zc = y, yc = -xc + zc, dist = (Math.abs(xc) + Math.abs(yc) + Math.abs(zc)) / 2;
        	maxDist = Math.max(maxDist, dist);
        }
        
        //convert to cubic coordinates, see https://www.redblobgames.com/grids/hexagons/#conversions
        System.out.println("Max distance is " + maxDist);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode11_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc11.txt"));
    	String[] input = inputStr.split(",");
        int x=0, y=0;
        
        //strategy: move N-E on the NW-SE axis 
        for(String direction : input) {
        	switch (direction) {
			case "n": //north up
				y--;
				break;
			case "nw": //north up, west left
				y--;
				x--;
				break;
			case "ne": //north no move, east right
				x++;
				break;
			case "s": //south down
				y++;
				break;
			case "sw": //south no move, west left
				x--;
				break;
			case "se": //south down, east right
				y++;
				x++;
				break;
			}
        }
        
        //convert to cubic coordinates, see https://www.redblobgames.com/grids/hexagons/#conversions
        int xc = x, zc = y, yc = -xc + zc, dist = (Math.abs(xc) + Math.abs(yc) + Math.abs(zc)) / 2;
        System.out.println("Total distance is " + dist);
    }
}
