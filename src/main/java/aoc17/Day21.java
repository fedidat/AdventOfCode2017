package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class Day21 {
    public static void main(String[] args) throws IOException {
        adventOfCode21_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode21_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc21.txt"));
        String[] rulesStr = inputStr.split("\n");
        final Map<List<String>, List<String>> rules = Arrays.stream(rulesStr)
                .map(rule -> rule.split(" => "))
                .map(strArr -> List.of(List.of(strArr[0].split("/")), List.of(strArr[1].split("/"))))
                .collect(Collectors.toMap(r -> r.get(0), r -> r.get(1), (a,b)->a, LinkedHashMap::new));

        List<String> currentArt = new ArrayList<>(List.of(".#.", "..#", "###"));
        int ITERATIONS = 18;
        for(int iteration=0; iteration<ITERATIONS; iteration++){
			currentArt = mergeSegments(
					splitIntoSegments(currentArt).stream()
					.map(s -> new ArrayList<>(matchAnyRule(s, rules))).collect(Collectors.toList()));
		}
		
		System.out.println("Pixels on: " + currentArt.stream().mapToInt(s -> s.length() - s.replace("#", "").length()).sum());
    }

    @SuppressWarnings("unused")
    private static void adventOfCode21_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc21.txt"));
        String[] rulesStr = inputStr.split("\n");
        final Map<List<String>, List<String>> rules = Arrays.stream(rulesStr)
                .map(rule -> rule.split(" => "))
                .map(strArr -> List.of(List.of(strArr[0].split("/")), List.of(strArr[1].split("/"))))
                .collect(Collectors.toMap(r -> r.get(0), r -> r.get(1), (a,b)->a, LinkedHashMap::new));

        List<String> currentArt = new ArrayList<>(List.of(".#.", "..#", "###"));
        int ITERATIONS = 5;
        for(int iteration=0; iteration<ITERATIONS; iteration++){
			currentArt = mergeSegments(
					splitIntoSegments(currentArt).stream()
					.map(s -> new ArrayList<>(matchAnyRule(s, rules))).collect(Collectors.toList()));
		}
		
		System.out.println("Pixels on: " + currentArt.stream().mapToInt(s -> s.length() - s.replace("#", "").length()).sum());
	}

	static List<List<String>> splitIntoSegments(List<String> input) {
		List<List<String>> segments = new ArrayList<>();
		int segmentSize = input.size()%2 == 0 ? 2 : 3;
		for(int rowNum=0; rowNum<input.size(); rowNum+=segmentSize) {
			for(int colNum=0; colNum < input.size(); colNum+=segmentSize) {
				List<String> segment = new ArrayList<>();
				for(int segRowNum=0; segRowNum<segmentSize; segRowNum++) {
						segment.add(input.get(rowNum+segRowNum).substring(colNum, colNum+segmentSize));
				}
				segments.add(segment);
			}
		}
		return segments;
	}

	static List<String> mergeSegments(List<List<String>> segments) {
		List<String> merged = new ArrayList<>();
		int artDimension = (int) Math.sqrt(segments.size());
		for(int segmentNum = 0; segmentNum < segments.size(); segmentNum++) {
			List<String> currentSegment = segments.get(segmentNum);
			int baseRow = segmentNum / artDimension * segments.get(0).size();
			for(int segRowNum = 0; segRowNum < currentSegment.size(); segRowNum++) {
				if(merged.size() <= baseRow + segRowNum)
					merged.add("");
				String artCol = currentSegment.get(segRowNum);
				merged.set(baseRow + segRowNum, merged.get(baseRow + segRowNum) + artCol);
			}
		}
		return merged;

	}

	static List<String> matchAnyRule(List<String> segment, Map<List<String>, List<String>> rules) {            	
		for(Entry<List<String>, List<String>> rule : rules.entrySet()) {
			if(ruleMatch(segment, rule.getKey())) {
				//System.out.println("Segment " + segment + " matched rule " + rule);
				return rule.getValue();
			}
		}
		return null;
	}

	static boolean ruleMatch(List<String> segment, List<String> ruleKey){
		if(ruleKey.size() != segment.size())
			return false;
			
		//compare
		if(compareRule(segment, ruleKey)) {
			return true;
		}
		
		for(int flipTranspose = 0; flipTranspose < 4; flipTranspose++) {
			
			//flip
			List<String> flippedSegment = new ArrayList<>();
			for(int row=0; row<segment.size(); row++) {
				flippedSegment.add(0, segment.get(row));
			}
			segment = flippedSegment;
			
			//compare
			if(compareRule(segment, ruleKey)) {
				return true;
			}
			
			//transpose
			List<String> transposedSegment = new ArrayList<>();
			for(int row=0; row<segment.size(); row++) {
				transposedSegment.add("");
				for(int col=0; col<segment.size(); col++) {
					transposedSegment.set(row, transposedSegment.get(row) + segment.get(col).charAt(row));
				}
			}
			segment = transposedSegment;
			
			//compare
			if(compareRule(segment, ruleKey)) {
				return true;
			}
		}
		return false;
	}
	
	static boolean compareRule(List<String> segment, List<String> rule) {
		for(int row=0; row<segment.size(); row++) {
			if(!segment.get(row).equals(rule.get(row))){
				return false;
			}
		}
		return true;
	}
}
