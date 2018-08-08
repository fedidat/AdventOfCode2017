package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Day16 {

    public static void main(String[] args) throws IOException {
        adventOfCode16_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode16_2() throws IOException {
        //Same code, just added a few lines to detect a cycle
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc16.txt"));
        String[] instructions = inputStr.split(",");
        char[] state = "abcdefghijklmnop".toCharArray();
        int ROUNDS = 10_000;

        int inc = 0;
        long start = System.currentTimeMillis();
        List<String> uniques = new ArrayList<>();
        for(int r=0; r<ROUNDS; r++) {
            for(String instruction : instructions){
                char instructionType = instruction.charAt(0);
                String instructionBody = instruction.substring(1);
                switch(instructionType){
                    case 's': {
                        int offset = Integer.parseInt(instructionBody);
                        char[] newState = new char[state.length];
                        for(int i = state.length - offset; i < state.length; i++)
                            newState[i - (state.length - offset)] = state[i];
                        for(int i = 0; i < state.length - offset; i++)
                            newState[offset + i] = state[i];
                        state = newState;
                    }
                    break; 
                    case 'x': {
                        String[] places = instructionBody.split("/");
                        int placeA = Integer.parseInt(places[0]), placeB = Integer.parseInt(places[1]);
                        char temp = state[placeA];
                        state[placeA] = state[placeB];
                        state[placeB] = temp;
                    }
                        break;
                    case 'p': {
                        String[] places = instructionBody.split("/");
                        char elementA = places[0].charAt(0), elementB = places[1].charAt(0);
                        for(int i=0; i<state.length; i++) {
                            if(state[i] == elementA)
                                state[i] = elementB;
                            else if(state[i] == elementB)
                                state[i] = elementA;
                        }
                    }
                }
            }
            String stateStr = new String(state);
            if(uniques.contains(stateStr)) { //identify a cycle
                System.out.println("Found a cycle after at " + r + ": " + stateStr);
                System.out.println("Solution is " + uniques.get(1_000_000_000%36-1));
                System.exit(0);
            }
            else
                uniques.add(stateStr);        
            System.out.println("State at round " + r + ": " + new String(state));
        }
    }

    @SuppressWarnings("unused")
    private static void adventOfCode16_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc16.txt"));
        String[] instructions = inputStr.split(",");
        char[] state = "abcdefghijklmnop".toCharArray();

        for(String instruction : instructions){
            char instructionType = instruction.charAt(0);
            String instructionBody = instruction.substring(1);
            switch(instructionType){
                case 's': {
                    int offset = Integer.parseInt(instructionBody);
                    char[] newState = new char[state.length];
                    for(int i = state.length - offset; i < state.length; i++)
                        newState[i - (state.length - offset)] = state[i];
                    for(int i = 0; i < state.length - offset; i++)
                        newState[offset + i] = state[i];
                    state = newState;
                }
                break; 
                case 'x': {
                    String[] places = instructionBody.split("/");
                    int placeA = Integer.parseInt(places[0]), placeB = Integer.parseInt(places[1]);
                    char temp = state[placeA];
                    state[placeA] = state[placeB];
                    state[placeB] = temp;
                }
                    break;
                case 'p': {
                    String[] places = instructionBody.split("/");
                    char elementA = places[0].charAt(0), elementB = places[1].charAt(0);
                    for(int i=0; i<state.length; i++) {
                        if(state[i] == elementA)
                            state[i] = elementB;
                        else if(state[i] == elementB)
                            state[i] = elementA;
                    }
                }
            }
        }
        System.out.println("Final state: " + new String(state));
    }
}
