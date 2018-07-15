package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class Day6 {

    @SuppressWarnings("unused")
    private static void adventOfCode6_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc6.txt"));
        int[] input = Stream.of(inputStr.split("\t")).mapToInt(Integer::parseInt).toArray();

        //ugly but works approach: copy pasta with loop stuff

        //simulate it, storing the configuration after each round
        int banks = input.length;
        Set<List<Integer>> configurations = new HashSet<>();

        for(int i=0; i<10000; i++) {
            if(configurations.contains(Arrays.stream(input).boxed().collect(Collectors.toList()))) {
                System.out.println("Loop found after " + i + " redistributions");

                //loop stuff
                List<Integer> loopingCombo = Arrays.stream(input).boxed().collect(Collectors.toList());
                for(int loopI=0; loopI<i; loopI++) {
                    if(loopI != 0 && loopingCombo.equals(Arrays.stream(input).boxed().collect(Collectors.toList()))) {
                        System.out.println("Loop size is " + loopI + " redistributions");
                        return;
                    }
                    
                    //Find largest bank
                    int maxBankIndex = 0;
                    for(int j=0; j < input.length; j++) {
                        maxBankIndex = input[j] > input[maxBankIndex] ? j : maxBankIndex;
                    }
        
                    //redistirbute
                    int redistributable = Math.min(input[maxBankIndex], banks);
                    input[maxBankIndex] -= redistributable;
                    for(int j=1; j <= redistributable; j++)
                        input[(j + maxBankIndex) % banks]++;

                }
            }
            configurations.add(Arrays.stream(Arrays.copyOf(input, input.length)).boxed().collect(Collectors.toList()));

            //Find largest bank
            int maxBankIndex = 0;
            for(int j=0; j < input.length; j++) {
                maxBankIndex = input[j] > input[maxBankIndex] ? j : maxBankIndex;
            }

            //redistirbute
            int redistributable = Math.min(input[maxBankIndex], banks);
            input[maxBankIndex] -= redistributable;
            for(int j=1; j <= redistributable; j++)
                input[(j + maxBankIndex) % banks]++;
        }
        System.out.println("exceeded redistribution limit, loop unlikely");
    }

    @SuppressWarnings("unused")
    private static void adventOfCode6_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc6.txt"));
        int[] input = Stream.of(inputStr.split("\t")).mapToInt(Integer::parseInt).toArray();

        //simulate it, storing the configuration after each round
        int banks = input.length;
        Set<List<Integer>> configurations = new HashSet<>();

        for(int i=0; i<10000; i++) {
            //System.out.println(Arrays.toString(input));
            //System.out.println(Arrays.toString(configurations.toArray()))
            if(configurations.contains(Arrays.stream(input).boxed().collect(Collectors.toList()))) {
                System.out.println("Loop found after " + i + " redistributions");
                return;
            }
            configurations.add(Arrays.stream(Arrays.copyOf(input, input.length)).boxed().collect(Collectors.toList()));

            //Find largest bank
            int maxBankIndex = 0;
            for(int j=0; j < input.length; j++) {
                maxBankIndex = input[j] > input[maxBankIndex] ? j : maxBankIndex;
            }

            //redistirbute
            int redistributable = Math.min(input[maxBankIndex], banks);
            input[maxBankIndex] -= redistributable;
            for(int j=1; j <= redistributable; j++)
                input[(j + maxBankIndex) % banks]++;
        }
        System.out.println("exceeded redistribution limit, loop unlikely");
    }


}
