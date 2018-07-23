package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;


public class Day12 {

    public static void main(String[] args) throws IOException {
        adventOfCode12_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode12_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc12.txt"));
        Map<Integer, Set<Integer>> input = new HashMap<>();
        
        for(String line : inputStr.split("\n")) {
            String[] nodeAndAdj = line.split(" <-> ");
            int program = Integer.valueOf(nodeAndAdj[0]);
            Set<Integer> adj = Arrays.stream(nodeAndAdj[1].split(", ")).map(Integer::valueOf).collect(Collectors.toSet());
            input.put(program, adj);
        }

        //usual incremental approach: repeat the first program until no unconnected value can be found
        Set<Integer> connected = new HashSet<>();
        int groups = 0;
        while(true) {
            //try to find an unconnected departure point
            Integer depart = null;
            for(Integer candidate : input.keySet()) {
                if(!connected.contains(candidate)) {
                    depart = candidate;
                    break;
                }
            }
            //if no point was found, stop looking, otherwise increment and visit the group
            if(depart == null)
                break;
            groups++;

            Set<Integer> newlyConnected = new HashSet<>(Arrays.asList(depart));
            while(newlyConnected.size() > 0) {
                Set<Integer> toVisit = newlyConnected.stream().filter(e -> !connected.contains(e)).collect(Collectors.toSet());
                newlyConnected.clear();
                for(Integer program : toVisit) {
                    connected.add(program);
                    for(Integer adj : input.get(program))
                        newlyConnected.add(adj);
                }
            }
        }
        System.out.println(groups);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode12_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc12.txt"));
        Map<Integer, Set<Integer>> input = new HashMap<>();
        
        //parse the entire thing, better than going back and forth, particularly since it is sparse
        for(String line : inputStr.split("\n")) {
            String[] nodeAndAdj = line.split(" <-> ");
            int program = Integer.valueOf(nodeAndAdj[0]);
            Set<Integer> adj = Arrays.stream(nodeAndAdj[1].split(", ")).map(Integer::valueOf).collect(Collectors.toSet());
            input.put(program, adj);
        }

        //starting with 0, iterate over the neighbors of new programs, until there are no new programs
        Set<Integer> connected = new HashSet<>(), newlyConnected = new HashSet<>(Arrays.asList(0));
        while(newlyConnected.size() > 0) {
            Set<Integer> toVisit = newlyConnected.stream().filter(e -> !connected.contains(e)).collect(Collectors.toSet());
            newlyConnected.clear();
            for(Integer program : toVisit) {
                connected.add(program);
                for(Integer adj : input.get(program))
                    newlyConnected.add(adj);
            }
        }
        System.out.println(connected.size());

    }
}
