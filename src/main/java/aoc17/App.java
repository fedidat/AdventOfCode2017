package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class App {

    public static void main(String[] args) throws IOException {
        adventOfCode7_1();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode7_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc7.txt"));

        //reuse part 1 to first find the ancestor
        Map<String, List<String>> programs = new HashMap<>();
        Map<String, Integer> weights = new HashMap<>();
        for(String program: inputStr.split("\n")) {
            String[] programAndKids = program.split(" \\([\\d]+\\)( -> )?");
            //only store mappings of dads to kids
            if(programAndKids.length > 1)
                programs.put(programAndKids[0], Arrays.asList(programAndKids[1].split(", ")));
            else
                weights.put(programAndKids[0], value);
        }
        System.out.println(programs);
        System.out.println(weights);

        String currentProgram = programs.keySet().stream().findFirst().get();
        boolean found = true;
        while(found) {
            found = false;
            for(Entry<String, List<String>> dadAndKids : programs.entrySet()) {
                if(dadAndKids.getValue().contains(currentProgram)) {
                    currentProgram = dadAndKids.getKey();
                    found = true;
                }
            }
        }

        //while i dislike recursive functions it's the best fit here
        System.out.println(checkWeight(programs, weights, currentProgram));
    }

    private static String checkWeight(Map<String, List<String>> programs, Map<String, Integer> weights, String currentDad){
        if(programs.get(currentDad).size() == 0) //leaf
            return null;
        
        int kidsSum = 0;
        for(String currentKid : programs.get(currentDad)) {
            if(!weights.containsKey(currentDad))
            if(checkWeight(programs, currentKid) != null) {

            }
        }
    }

    @SuppressWarnings("unused")
    private static void adventOfCode7_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc7.txt"));

        //first recreate the structure
        Map<String, List<String>> programs = new HashMap<>();
        for(String program: inputStr.split("\n")) {
            String[] programAndKids = program.split(" \\([\\d]+\\)( -> )?");
            //only store mappings of dads to kids
            if(programAndKids.length > 1)
                programs.put(programAndKids[0], Arrays.asList(programAndKids[1].split(", ")));
        }

        //Now let's start from the first element and find our way back
        String currentProgram = programs.keySet().stream().findFirst().get();
        boolean found = true;
        while(found) {
            found = false;
            for(Entry<String, List<String>> dadAndKids : programs.entrySet()) {
                if(dadAndKids.getValue().contains(currentProgram)) {
                    currentProgram = dadAndKids.getKey();
                    found = true;
                }
            }
        }
        System.out.println("Ancestor is " + currentProgram);

    }


}
