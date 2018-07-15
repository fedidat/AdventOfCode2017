package aoc17;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Day3 {

    @SuppressWarnings("unused")
    private static void adventOfCode3_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc3.txt"));
        int input = Integer.parseInt(inputStr);

        //All smart algorithms I could come up with require the previous number,
        //so instead I'll go for a straight up simulation which will be at least
        //as fast and require almost the same amount of memory as the more complex implementations.
        //The only way to really avoid memory constraints is without any memoization, 
        //which would give horrendous performance.
        //Besides, growth is exponential so runtime should be roughly O(n).

        //initialize
        int size = 20;
        int[][] values = new int[size][size];
        int currentValue = values[size/2][size/2] = 1;

        //For each layer until the end of the board
        for(int layer = 1; layer < size / 2; layer++) {

            //start at [originX-layer+1; originY+layer]
            int x = size / 2 + layer, y = size / 2 + layer;

            //For each of the four sides
            for(int side = 0; side < 4; side++){

                //For each of the layer * 2 values on the side
                for(int value = 0; value < layer * 2; value++) {
                    //go on
                    switch(side) {
                        case 0: x--; break;
                        case 1: y--;  break;
                        case 2: x++; break;
                        case 3: y++; break;
                    }

                    //Set value
                    currentValue = values[x][y] = values[x-1][y-1] + values[x-1][y] + values[x-1][y+1]
                            + values[x][y-1] + values[x][y+1] + values[x+1][y-1] + values[x+1][y] + values[x+1][y+1];

                    //return at success
                    if(currentValue > input) {
                        System.out.println("Reached " + currentValue + " at [" + x + "," + y + "]");
                        return;
                    }
                }
            }
        }
        System.out.println("Reached end of the board with " + currentValue);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode3_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc3.txt"));
        int input = Integer.parseInt(inputStr);

        //First find out which layer we are.
        //On each layer >0, there are n*2*4 values. 
        //We'll simply add that up until we reach the input number.
        int layer = 0, sum = 1;
        for(; sum + layer * 8 < input; layer++){
            sum += layer * 8;
        }
        System.out.println(layer); 

        //Now let's find where we are on the current square.
        //First, the side number, counter-clockwise, fixed by subtracting one modulus number of values.
        int numOnSquare = input - sum - 1;
        int side = (numOnSquare)/(layer*2);

        //Now let's computer how far from the central cross we are
        //For each side, half is above and half is beneath
        int howManyOnSide = layer * 2;
        int indexOnSide = numOnSquare % (layer * 2);
        int crossDist = Math.abs(indexOnSide - howManyOnSide / 2 + 1);

        //Put it together: Manhattan distance = layer number + distance from cross
        int distance = layer + crossDist;
        System.out.println(distance);
    }
    
}