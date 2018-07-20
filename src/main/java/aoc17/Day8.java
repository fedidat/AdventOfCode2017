package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Day8 {

    public static void main(String[] args) throws IOException {
        adventOfCode8_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode8_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc8.txt"));
        Map<String, Integer> registers = new HashMap<>();
        int highestSoFar = Integer.MIN_VALUE;

        for(String instruction : inputStr.split("\n")) {
            String[] tokens = instruction.split(" ");
            String register = tokens[0];
            boolean increase = tokens[1].equals("inc");
            int value = Integer.parseInt(tokens[2]);
            String ifRegister = tokens[4];
            String ifOperator = tokens[5];
            int ifValue = Integer.parseInt(tokens[6]);

            boolean execute = false;
            
            //more elegant this time
            int comparison = Integer.compare(value, ifValue);
            if(comparison == 1) 
                execute = Arrays.asList(">", ">=", "!=").contains(ifOperator);
            else if(comparison == -1)
                execute = Arrays.asList("<", "<=", "!=").contains(ifOperator);
            else
                execute = Arrays.asList("==", ">=", "<=").contains(ifOperator);

            if(execute)
                registers.put(register, (increase ? 1 : -1) * value + registers.getOrDefault(register, 0));
            highestSoFar = Math.max(highestSoFar, registers.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue());
        }
        System.out.println(highestSoFar);

    }

    @SuppressWarnings("unused")
    private static void adventOfCode8_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc8.txt"));
        Map<String, Integer> registers = new HashMap<>();

        for(String instruction : inputStr.split("\n")) {
            String[] tokens = instruction.split(" ");
            String register = tokens[0];
            boolean increase = tokens[1].equals("inc");
            int value = Integer.parseInt(tokens[2]);
            String ifRegister = tokens[4];
            String ifOperator = tokens[5];
            int ifValue = Integer.parseInt(tokens[6]);

            boolean execute = false;
            switch(ifOperator) {
                case ">":
                    execute = registers.getOrDefault(ifRegister, 0) > ifValue;
                    break;
                case ">=":
                    execute = registers.getOrDefault(ifRegister, 0) >= ifValue;
                    break;
                case "<":
                    execute = registers.getOrDefault(ifRegister, 0) < ifValue;
                    break;
                case "<=":
                    execute = registers.getOrDefault(ifRegister, 0) <= ifValue;
                    break;
                case "==":
                    execute = registers.getOrDefault(ifRegister, 0) == ifValue;
                    break;
                case "!=":
                    execute = registers.getOrDefault(ifRegister, 0) != ifValue;
            }

            if(execute)
                registers.put(register, (increase ? 1 : -1) * value + registers.getOrDefault(register, 0));
                //or registers.merge(register, (increase ? 1 : -1) * value, (a, b) -> a + b);
        }
        System.out.println(registers.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue());
    }

}
