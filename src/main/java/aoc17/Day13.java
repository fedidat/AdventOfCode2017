package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;


public class Day13 {

    public static void main(String[] args) throws IOException {
        adventOfCode13_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode13_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc13.txt"));
        int NUMBER_OF_DELAYS = 10000000;
        Map<Integer, Integer> layers = Arrays.stream(inputStr.split("\n"))
                .map(e -> Arrays.stream(e.split(": ")).mapToInt(Integer::parseInt).toArray())
                .map(e -> new AbstractMap.SimpleEntry<Integer, Integer>(e[0], e[1]))
                .collect(Collectors.toMap(e -> e.getKey(), e  -> e.getValue()));

        for(int delay = 0 ; delay < NUMBER_OF_DELAYS ; delay++) {
            //Optimization of part 1: eliminate D-1 in the modulus, and since we're only
            //interested with 0, also remove the subtraction and Math.abs.
        	//Also, streams have no break so a loop will be more efficient. 
        	boolean detected = false;
	        for(Entry<Integer, Integer> entry : layers.entrySet()) {
	            if((entry.getKey()+delay) % ((entry.getValue()-1)*2) == 0) {
	            	detected = true;
	            	break;
	            }
	        }
            if(!detected) {
                System.out.println("Not detected at delay " + delay);
                return;
            }
        }
        System.out.println("Detected for all delays up to " + NUMBER_OF_DELAYS);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode13_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc13.txt"));

        //I love streams
        Map<Integer, Integer> layers = Arrays.stream(inputStr.split("\n")) //for each line
                .map(e -> Arrays.stream(e.split(": ")).mapToInt(Integer::parseInt).toArray()) //extract two values
                .map(e -> new AbstractMap.SimpleEntry<Integer, Integer>(e[0], e[1])) //make an entry
                .collect(Collectors.toMap(e -> e.getKey(), e  -> e.getValue())); //collect into a map

        //Let's be smart!
        //We will try to calculate the position of the bot at depth t with range D at time t.
        //The position goes up then down linearly, following a triangular function.
        //We go from f1(t)=t, the simplest function,
        //f2(t)=f1(t)%((D-1)*2) gives us little increasing lines of the right cycle size,
        //f3(t)=f2(t)-(D-1) lowers these lines half under 0,
        //f4(t)=abs(f3(t)) raises turns the first half around the x axis, like so: \/,
        //f4(t+D-1) gives offsets by half, giving us /\,
        //so f(t)=abs((t+D-1)%((D-1)*2)-(D-1)).
        //so for example for D=4, f(t)=abs((t+3)%6-3)
        int severity = layers.entrySet().stream().map(e -> {
            int t = e.getKey(), D = e.getValue();
            return Math.abs((t+D-1)%((D-1)*2)-(D-1)) == 0 ? t * D : 0;
            //from part 2: return t % ((D-1)*2) == 0 ? t * D : 0;
        }).mapToInt(Integer::intValue).sum();
        System.out.println(severity);
    }
}
