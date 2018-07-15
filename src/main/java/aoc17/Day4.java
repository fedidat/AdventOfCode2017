package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Day4 {

    @SuppressWarnings("unused")
    public static void adventOfCode4_2() throws IOException {
        String inputWithLines = FileUtils.readFileToString(new File("inputs/aoc4.txt"));
        String[] input = inputWithLines.split("\n");
        int valid = 0;
        for(String line : input) {
            Set<String> set = new HashSet<>();
            boolean unique = true;
            String[] words = line.split(" ");
            for(int i=0; i < words.length && unique; i++) {
                String word = words[i];

                //calculate anagram
                for(String anagram : allAnagrams(word)) {
                    if(set.contains(anagram)) {
                        unique = false;
                        break;
                    }    
                }
                if(unique)
                    set.add(word);
            }
            if(unique)
                valid++;
        }
        System.out.println(valid + " valid passphrases");
    }

    public static List<String> allAnagrams(String s) {
        List<String> permutations = new ArrayList<>();
        //n
        //yn, ny
        //oyn, yon, yno, ony, noy, nyo
        permutations.add(String.valueOf(s.charAt(0)));
        for(char c : s.substring(1).toCharArray()) {
            List<String> longerPerms = new ArrayList<>();
            for(String p : permutations) {
                for(int i=0; i<=p.length(); i++)
                    longerPerms.add(p.substring(0, i) + c + p.substring(i));
            }
            permutations = longerPerms;
        }
        return permutations;
    }

    @SuppressWarnings("unused")
    public static void adventOfCode4_1() throws IOException {
        String inputWithLines = FileUtils.readFileToString(new File("inputs/aoc4.txt"));
        String[] input = inputWithLines.split("\n");
        int valid = 0;
        for(String line : input) {
            Set<String> set = new HashSet<>();
            boolean unique = true;
            for(String word : line.split(" ")) {
                if(set.contains(word)) {
                    unique = false;
                    break;
                }
                set.add(word);
            }
            if(unique)
                valid++;
        }
        System.out.println(valid + " valid passphrases");
    }

}