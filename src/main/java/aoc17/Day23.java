package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;

public class Day23 {
    public static void main(String[] args) throws IOException {
        adventOfCode23_2();
    }

	@SuppressWarnings("unused")
    private static void adventOfCode23_2() {
        // Starting for the pseudo-assembly, we quickly translate it to:
        // 1 for(b=105700; b <= 122700; b+=17)
        // 2    for(d=2; d <= b; d++)
        // 3        for(e=2; e <= b; e++)
        // 4            if(d*e == b)
        // 5                h++
        // As can be seen, register h counts the non-prime numbers between 105700 and 122700 (inclusive).
        // The most simple way (without memoization) of doing that more efficiently is to try dividing the
        // value by each number up to its square root, stopping eahc time when finding a divider.
        System.out.println("Non-primes: " + IntStream.iterate(105700, n -> n <= 122700, n -> n+17)
            .mapToObj(n -> 
                IntStream.range(2, (int)Math.sqrt(n))
                .anyMatch(i -> n % i == 0))
            .filter(r -> r == true)
            .count());
    }

    @SuppressWarnings("unused")
    private static void adventOfCode23_1() throws IOException {
		String inputStr = FileUtils.readFileToString(new File("inputs/aoc23.txt"));
        String[] instructions = inputStr.split("\n");

        Map<String, Long> registers = new HashMap<>();
        int ITERATIONS = 10000000, iteration, currentPc = 0;
        String currentInstruction;
        Integer mulCounter = 0;

        for(iteration = 0; iteration < ITERATIONS && currentPc >= 0 && currentPc < instructions.length; iteration++) {
            currentInstruction = instructions[currentPc];

            //System.out.println("I" + iteration + ":" + currentPc + ":" + currentInstruction + ", " + registers);

            String[] args = currentInstruction.split(" ");
            String opcode = args[0], 
                    register = opcode.equals("snd") ? "" : args[1], 
                    valueArg = opcode.equals("snd") || opcode.equals("rcv") ? args[1] : args[2];
            Long value = valueArg.matches("[+-]?\\d+") ? Long.parseLong(valueArg) : registers.getOrDefault(valueArg, 0l);

            switch(opcode) {
                case "set":
                    registers.put(register, value);
                    break;
                case "sub":
                    registers.put(register, registers.getOrDefault(register, 0l) - value);
                    break;
                case "mul":
                    mulCounter++;
                    registers.put(register, registers.getOrDefault(register, 0l) * value);
                    break;
                case "jnz":
                    if((register.matches("[+-]?\\d+") ? Long.parseLong(register) : registers.getOrDefault(register, 0l)) != 0)
                        currentPc += value - 1;
                    break;
                default:
                    System.out.println("Unrecognized opcode " + opcode);
                    System.exit(1);
            }
            currentPc++;
        }

        System.out.println("Iterations: " + iteration + ", multiply counter = " + mulCounter);
    }

}
