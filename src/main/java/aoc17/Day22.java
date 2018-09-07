package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

public class Day22 {
    public static void main(String[] args) throws IOException {
        adventOfCode22_2();
    }

	@SuppressWarnings("unused")
    private static void adventOfCode22_2() throws IOException {
		String inputStr = FileUtils.readFileToString(new File("inputs/aoc22.txt"));
        
        int BURSTS = 10000000, infections = 0;
        Direction direction = Direction.NORTH;
        List<List<Character>> grid = new ArrayList<>(
            Arrays.stream(inputStr.split("\n"))
                    .map(line -> line.chars()
                        .mapToObj(c -> (char)c)
                        .collect(Collectors.toList()))
                    .collect(Collectors.toList()));
        Point coords = new Point(grid.size() / 2, grid.get(0).size() / 2);

        for(int burst = 0; burst < BURSTS; burst++) {
            //change dimensions if needed
            borderCheckChar(grid, coords);
            //change infection state and direction
            if(grid.get(coords.x).get(coords.y) == '.') {
                direction = Direction.leftOf(direction);
                grid.get(coords.x).set(coords.y, 'W');
            }
            else if(grid.get(coords.x).get(coords.y) == 'W') {
                grid.get(coords.x).set(coords.y, '#');
            }
            else if(grid.get(coords.x).get(coords.y) == '#') {
                direction = Direction.rightOf(direction);
                grid.get(coords.x).set(coords.y, 'F');
            }
            else { //if(grid.get(coords.x).get(coords.y) == 'F') {
                direction = Direction.inverse(direction);
                grid.get(coords.x).set(coords.y, '.');
            }
            //count infections
            if(grid.get(coords.x).get(coords.y) == '#')
                infections++;
            //move
            coords.go(direction);
        }
        
        // printGridChar(grid);
        System.out.println("Final coordinates (" + coords.x + ", " + coords.y + "), facing " + direction.toString().toLowerCase() 
                + ", grid size " + grid.size() + "x" + grid.get(0).size());
        System.out.println("There have been " + infections + " infections");
    }

    @SuppressWarnings("unused")
    private static void adventOfCode22_1() throws IOException {
		String inputStr = FileUtils.readFileToString(new File("inputs/aoc22.txt"));
        
        int BURSTS = 70, infections = 0;
        Direction direction = Direction.NORTH;
        List<List<Boolean>> grid = new ArrayList<>(
            Arrays.stream(inputStr.split("\n"))
                    .map(line -> line.chars()
                        .boxed().map(c -> {return c == '#';})
                        .collect(Collectors.toList()))
                    .collect(Collectors.toList()));
        Point coords = new Point(grid.size() / 2, grid.get(0).size() / 2);

        for(int burst = 0; burst < BURSTS; burst++) {
            //change dimensions if needed
            borderCheck(grid, coords);
            System.out.println("Iteration " + burst + ", coords (" + coords.x + ", " + coords.y + "), grid " + grid.size() + "x" + grid.get(0).size() + " facing " + direction);
            printGrid(grid);
            //change infection state and direction
            direction = grid.get(coords.x).get(coords.y) ? Direction.rightOf(direction) : Direction.leftOf(direction);
            grid.get(coords.x).set(coords.y, !grid.get(coords.x).get(coords.y));
            //count infections
            if(grid.get(coords.x).get(coords.y))
                infections++;
            //move
            coords.go(direction);
        }
        
        printGrid(grid);
        System.out.println("Final coordinates (" + coords.x + ", " + coords.y + "), facing " + direction.toString().toLowerCase() 
                + ", grid size " + grid.size() + "x" + grid.get(0).size());
        System.out.println("There have been " + infections + " infections");
    }

    static enum Direction { 
        NORTH(0), EAST(1), SOUTH(2), WEST(3); 
        private final int value;
        private Direction(int value) { this.value = value; }
        public int getValue() { return value; }
        public static Direction leftOf(Direction direction) { return Direction.values()[(direction.getValue() - 1 + 4) % 4]; }
        public static Direction rightOf(Direction direction) { return Direction.values()[(direction.getValue() + 1) % 4]; }
        public static Direction inverse(Direction direction) { return Direction.values()[(direction.getValue() + 2) % 4]; }
    }
    
    static class Point{ 
        public int x,y;
        public Point(int x, int y){this.x=x;this.y=y;}  
        public void go(Direction direction){
            if(direction == Direction.NORTH) x--;
            else if(direction == Direction.EAST) y++;
            else if(direction == Direction.SOUTH) x++;
            else y--;
        }
    }

    private static void borderCheckChar(List<List<Character>> grid, Point coords) {
        if(coords.x == 0) {
            coords.x = 1;
            grid.add(0, IntStream.range(0, grid.get(0).size()).mapToObj(i -> '.').collect(Collectors.toList()));
        }
        else if(coords.x == grid.size() - 1) {
            grid.add(IntStream.range(0, grid.get(0).size()).mapToObj(i -> '.').collect(Collectors.toList()));
        }
        if(coords.y == 0) {
            coords.y = 1;
            for(List<Character> rowAdding : grid)
                rowAdding.add(0, '.');
        }
        else if(coords.y == grid.get(0).size() - 1) {
            for(List<Character> rowAdding : grid)
                rowAdding.add('.');
        }
    }
    private static void borderCheck(List<List<Boolean>> grid, Point coords) {
        if(coords.x == 0) {
            coords.x = 1;
            grid.add(0, IntStream.range(0, grid.get(0).size()).mapToObj(i -> false).collect(Collectors.toList()));
        }
        else if(coords.x == grid.size() - 1) {
            grid.add(IntStream.range(0, grid.get(0).size()).mapToObj(i -> false).collect(Collectors.toList()));
        }
        if(coords.y == 0) {
            coords.y = 1;
            for(List<Boolean> rowAdding : grid)
                rowAdding.add(0, false);
        }
        else if(coords.y == grid.get(0).size() - 1) {
            for(List<Boolean> rowAdding : grid)
                rowAdding.add(false);
        }
    }
    
    private static void printGridChar(List<List<Character>> grid) {
        grid.stream().forEach(l -> {
            l.stream().forEach(System.out::print);;
            System.out.println();
        });
    }
    private static void printGrid(List<List<Boolean>> grid) {
        grid.stream().forEach(l -> {
            l.stream().map(e -> e ? '#' : '.').forEach(System.out::print);;
            System.out.println();
        });
    }

}
