package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class Day24 {
    public static void main(String[] args) throws IOException {
        adventOfCode24_2();
    }

	@SuppressWarnings("unused")
    private static void adventOfCode24_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc24.txt"));
        List<Link> links = Arrays.stream(inputStr.split("\n"))
            .map(ls -> ls.split("/"))
            .map(la -> new Link(Integer.parseInt(la[0]), Integer.parseInt(la[1])))
            .collect(Collectors.toList());
        Bridge maxBridge = longestBridge(links, 0, new Bridge(0, 0));
        System.out.println("Longest bridge with length " + maxBridge.length + " and strength " + maxBridge.strength);
    }

    public static Bridge longestBridge(List<Link> links, int queue, Bridge bridge) {
        Bridge maxBridge = new Bridge(bridge);
        for(Link link : links) {
            if(link.a != queue){
                if(link.b != queue)
                    continue;
                else
                    link.reverse();
            }
            List<Link> splicedLinks = new ArrayList<Link>(links);
            splicedLinks.remove(link);
            Bridge res = longestBridge(splicedLinks, link.b, 
                    new Bridge(bridge.length + 1, bridge.strength + link.a + link.b));
            if(res.length > maxBridge.length || (res.length == maxBridge.length && res.strength > maxBridge.strength))
                maxBridge = res;
        }
        return maxBridge;
    }

    @SuppressWarnings("unused")
    private static void adventOfCode24_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc24.txt"));
        List<Link> links = Arrays.stream(inputStr.split("\n"))
            .map(ls -> ls.split("/"))
            .map(la -> new Link(Integer.parseInt(la[0]), Integer.parseInt(la[1])))
            .collect(Collectors.toList());
        System.out.println("Strongest bridge with strength " + strongestBridge(links, 0, new Bridge(0, 0)).strength);
    }

    public static Bridge strongestBridge(List<Link> links, int queue, Bridge bridge) {
        Bridge maxBridge = new Bridge(bridge);
        for(Link link : links) {
            if(link.a != queue){
                if(link.b != queue)
                    continue;
                else
                    link.reverse();
            }
            List<Link> splicedLinks = new ArrayList<Link>(links);
            splicedLinks.remove(link);
            Bridge res = strongestBridge(splicedLinks, link.b, 
                    new Bridge(bridge.length + 1, bridge.strength + link.a + link.b));
            if(res.strength > maxBridge.strength)
                maxBridge = res;
        }
        return maxBridge;
    } 

    public static class Bridge {
        public int length, strength;
        public Bridge(int length, int strength){ this.length = length; this.strength = strength; }
        public Bridge(Bridge bridge) { length = bridge.length; strength = bridge.strength; }
    }

    public static class Link {
        public int a, b;
        public Link(int a, int b) { this.a = a; this.b = b; }
        public String toString() { return a + "/" + b; }
        public int value() { return a + b; }
        public void reverse() { int temp = a; a = b; b = temp; }
    }
}
