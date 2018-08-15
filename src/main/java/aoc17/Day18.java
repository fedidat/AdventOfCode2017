package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.io.FileUtils;

public class Day18 {

    public static void main(String[] args) throws IOException {
        adventOfCode18_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode18_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc18.txt"));
        String[] instructions = inputStr.split("\n");

        Map<Integer, Map<String, Long>> processRegisters = Map.of(0, new HashMap<>(), 1, new HashMap<>());
        processRegisters.get(1).put("p", 1l);

        Map<Integer, Queue<Long>> buffers = Map.of(0, new LinkedList<>(), 1, new LinkedList<>());

        Map<Integer, Integer> programCounters = new HashMap<>();
        programCounters.put(0, 0);
        programCounters.put(1, 0);

        boolean deadlock = false, rcvBlocked = false, program0finished = false, program1finished = false;
        int iterations = 0, currentPid = 0, currentPc;
        String currentInstruction;
        Queue<Long> currentBuffer;
        Integer pid1SendCounter = 0;

        while(!deadlock && (!program0finished || !program1finished)) {
            Map<String, Long> currentRegisters = processRegisters.get(currentPid);
            currentPc = programCounters.get(currentPid);
            currentInstruction = instructions[currentPc];
            currentBuffer = buffers.get(currentPid);

            String[] args = currentInstruction.split(" ");
            String opcode = args[0], 
                    register = opcode.equals("snd") ? "" : args[1], 
                    valueArg = opcode.equals("snd") || opcode.equals("rcv") ? args[1] : args[2];
            Long value = valueArg.matches("[+-]?\\d+") ? Long.parseLong(valueArg) : currentRegisters.getOrDefault(valueArg, 0l);

            switch(opcode) {
                case "snd":
                    buffers.get((currentPid + 1) % 2).add(value);
                    if(currentPid == 1)
                        pid1SendCounter++;
                    break;
                case "set":
                    currentRegisters.put(register, value);
                    break;
                case "add":
                    currentRegisters.put(register, currentRegisters.getOrDefault(register, 0l) + value);
                    break;
                case "mul":
                    currentRegisters.put(register, currentRegisters.getOrDefault(register, 0l) * value);
                    break;
                case "mod":
                    currentRegisters.put(register, currentRegisters.getOrDefault(register, 0l) % value);
                    break;
                case "rcv":
                    if(currentBuffer.isEmpty()) {
                        //Context switch
                        currentPid = (currentPid + 1) % 2;
                        if(rcvBlocked)
                            deadlock = true;
                        else
                            rcvBlocked = true;
                        continue;
                    }
                    else
                        currentRegisters.put(register, currentBuffer.remove());
                    break;
                case "jgz":
                    if((register.matches("[+-]?\\d+") ? Long.parseLong(register) : currentRegisters.getOrDefault(register, 0l)) > 0)
                        currentPc += value - 1;
                    break;
                default:
                    System.out.println("Unrecognized opcode " + opcode);
                    System.exit(1);
            }
            rcvBlocked = false;
            programCounters.put(currentPid, currentPc + 1);
        }
        System.out.println("PID 1 send counter = " + pid1SendCounter);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode18_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc18.txt"));
        String[] instructions = inputStr.split("\n");
        Map<String, Long> registers = new HashMap<>();
        Long lastSound = 0l;

        for(int nextInstruction = 0; nextInstruction >= 0 && nextInstruction < instructions.length; nextInstruction++) {
            String[] args = instructions[nextInstruction].split(" ");
            String opcode = args[0], 
                    register = opcode.equals("snd") ? "" : args[1], 
                    valueArg = opcode.equals("snd") || opcode.equals("rcv") ? args[1] : args[2];
            Long value = valueArg.matches("[+-]?\\d+") ? Long.parseLong(valueArg) : registers.getOrDefault(valueArg, 0l);

            switch(opcode) {
                case "snd":
                    lastSound = value;
                    break;
                case "set":
                    registers.put(register, value);
                    break;
                case "add":
                    registers.put(register, registers.getOrDefault(register, 0l) + value);
                    break;
                case "mul":
                    registers.put(register, registers.getOrDefault(register, 0l) * value);
                    break;
                case "mod":
                    registers.put(register, registers.getOrDefault(register, 0l) % value);
                    break;
                case "rcv":
                    if(registers.getOrDefault(register, 0l) != 0 && lastSound != 0) {
                        System.out.println("Recovered frequency " + lastSound);
                        System.exit(0);
                    }
                    break;
                case "jgz":
                    if(registers.getOrDefault(register, 0l) > 0)
                        nextInstruction += value - 1;
                    break;
                default:
                    System.out.println("Unrecognized opcode " + opcode);
                    System.exit(1);
            }
        }
    }
}
