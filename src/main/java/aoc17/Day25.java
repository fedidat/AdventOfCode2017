package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Day25 {
    public static void main(String[] args) throws IOException {
        //Input
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc25.txt"));
        String[] lines = inputStr.split("\n");
        Map<String, State> states = new HashMap<>();
        for(int line = 3; line < lines.length; line += 10){
            states.put(String.valueOf(lines[line].charAt(9)), 
                    new State(String.valueOf(lines[line].charAt(9)), 
                        lines[line+2].charAt(22) == '1', lines[line+2+4].charAt(22) == '1',
                        lines[line+3].charAt(27) == 'l', lines[line+3+4].charAt(27) == 'l',
                        String.valueOf(lines[line+4].charAt(26)), String.valueOf(lines[line+4+4].charAt(26))));
        }
        State currentState = states.get("A");
        int STEPS = 12794428;

        //Simulate the Turing machine
        List<Boolean> tape = new ArrayList<>();
        int zeroIndex = 0, currentIndex=0;
        for(int step = 0; step < STEPS; step++) {
            //Border check
            int realIndex = zeroIndex - currentIndex;
            if(realIndex < 0) {
                tape.add(0, false);
                zeroIndex++;
                realIndex++;
            }
            else if(realIndex > tape.size() - 1)
                tape.add(false);

            //Tape step
            Boolean bitHere = tape.get(realIndex);
            tape.set(realIndex, currentState.valueToWrite.get(bitHere));
            currentIndex = currentIndex + (currentState.moveToLeft.get(bitHere) ? -1 : 1);
            currentState = states.get(currentState.nextState.get(bitHere));
        }

        System.out.println("Number of ones: " + tape.stream().filter(b -> b == true).count());
    }

    public static class State {
        public State(String name, Boolean writeOn0, Boolean writeOn1, Boolean leftOn0, Boolean leftOn1, String stateOn0, String stateOn1) {
            this.name = name;
            this.valueToWrite = Map.of(false, writeOn0, true, writeOn1);
            this.moveToLeft = Map.of(false, leftOn0, true, leftOn1);
            this.nextState = Map.of(false, stateOn0, true, stateOn1);
        }
        public String toString() { return "{" + name 
            + ": 0->{" + (valueToWrite.get(false) ? "1" : "0") + "," + (moveToLeft.get(false) ? "L" : "R") + "," + nextState.get(false) + "}"
            + ", 1->{" + (valueToWrite.get(true) ? "1" : "0") + "," + (moveToLeft.get(true) ? "L" : "R") + "," + nextState.get(true) + "}}";
        }
        public String name;
        public Map<Boolean, Boolean> valueToWrite;
        public Map<Boolean, Boolean> moveToLeft;
        public Map<Boolean, String> nextState;
    }
}
