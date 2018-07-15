package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class Day7 {

    public static void main(String[] args) throws IOException {
        adventOfCode7_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode7_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc7.txt"));

        //this time, parse all the info
        //a full-fledged MVC would have been overkill so I'll stick to maps
        Map<String, List<String>> programs = new HashMap<>();
        Map<String, Integer> weights = new HashMap<>();
        for(String program: inputStr.split("\n")) {
            String pattern = "(.*) \\(([\\d]+)\\)(?: -> (.*))?";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(program);
            if(m.matches()) {
                String dad = m.group(1);
                Integer weight = Integer.parseInt(m.group(2));
                weights.put(dad, weight);
                String kids = m.group(3);
                if(kids != null) {
                    programs.put(dad, Arrays.asList(kids.split(", ")));
                }
            }
        }

        //go to ancestor rather than loop pointlessly
        String currentProgram = programs.keySet().stream().findFirst().get();
        boolean foundADad = true;
        while(foundADad) {
            foundADad = false;
            for(Entry<String, List<String>> dadAndKids : programs.entrySet()) {
                if(dadAndKids.getValue().contains(currentProgram)) {
                    currentProgram = dadAndKids.getKey();
                    foundADad = true;
                    break;
                }
            }
        }

        //while i dislike recursive functions it's the best fit here
        Map<String, Integer> treeWeights = new HashMap<>();
        checkWeight(programs, weights, treeWeights, currentProgram);
    }

    private static void checkWeight(Map<String, List<String>> programs, Map<String, Integer> weights, 
            Map<String, Integer> treeWeights, String currentDad){
        if(!programs.containsKey(currentDad)) { //leaf, set self then OK
            treeWeights.put(currentDad, weights.get(currentDad));
            return;
        }
        
        //use recursion over kids to get tree weights
        Map<String, Integer> kidsToWeights = new HashMap<>();
        for(String currentKid : programs.get(currentDad)) {
            if(!treeWeights.containsKey(currentKid)) //if kid not set, compute its weight
                checkWeight(programs, weights, treeWeights, currentKid);
            kidsToWeights.put(currentKid, treeWeights.get(currentKid)); //store kid value
        }

        //if not all kids equal, output problem and return
        if(new HashSet<Integer>(kidsToWeights.values()).size() != 1)
            System.out.println("Dad " + currentDad + " has wrong kids " + kidsToWeights + " with own weights " + kidsToWeights.keySet().stream().collect(Collectors.toMap(e -> e, e -> weights.get(e))));
        treeWeights.put(currentDad, weights.get(currentDad) + kidsToWeights.values().stream().mapToInt(i->i).sum()); //set self
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
