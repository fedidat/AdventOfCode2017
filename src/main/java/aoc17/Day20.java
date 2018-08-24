package aoc17;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class App {
    public static void main(String[] args) throws IOException {
        adventOfCode20_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode20_2() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc20.txt"));
        class Point{ public Point(int x, int y, int z){this.x=x;this.y=y;this.z=z;} public int x,y,z; 
                void add(Point b) {this.x += b.x; this.y += b.y; this.z += b.z;}
                boolean compare(Point b) {return this.x == b.x && this.y == b.y && this.z == b.z;}}
        List<List<Point>> particles = new ArrayList<>();
        String patternStr = "^p=< ?(-?\\d*), ?(-?\\d*), ?(-?\\d*)>, v=< ?(-?\\d*), ?(-?\\d*), ?(-?\\d*)>, a=< ?(-?\\d*), ?(-?\\d*), ?(-?\\d*)>$";
        Pattern pattern = Pattern.compile(patternStr);

        for(String particleStr: inputStr.split("\n")) {
            Matcher m = pattern.matcher(particleStr);
            if(m.matches()) {
                List<Point> particlePoint = new ArrayList<>();
                for(int i=0; i<3; i++)
                    particlePoint.add(new Point(Integer.valueOf(m.group(i*3+1)), Integer.valueOf(m.group(i*3+2)), Integer.valueOf(m.group(i*3+3))));
                particles.add(particlePoint);
            }
        }

        //One way to solve this part is to calculate quadratic equations on each pair to check whether they will collide.
        //But IMO this is overkill, so I simulate for a decent number of steps. Why write lot code when few lines do trick?
        int SIM_STEPS = 500;
        for(int step=0; step<SIM_STEPS; step++){
            List<List<Point>> forDeletion = new ArrayList<>();
            for(List<Point> particle : particles) {
                particle.get(1).add(particle.get(2));
                particle.get(0).add(particle.get(1));
            }
            for(List<Point> particleA : particles)
                for(List<Point> particleB : particles)
                    if(particleA != particleB && particleA.get(0).compare(particleB.get(0)))
                        forDeletion.addAll(List.of(particleA, particleB));
        }
        System.out.println("Final number of particles: " + particles.size());
    }

    @SuppressWarnings("unused")
    private static void adventOfCode20_1() throws IOException {
        String inputStr = FileUtils.readFileToString(new File("inputs/aoc20.txt"));
        class Point{ public Point(int x, int y, int z){this.x=x;this.y=y;this.z=z;} public int x,y,z; 
                int absSum() { return Math.abs(x) + Math.abs(y) + Math.abs(z); }}
        List<List<Point>> particles = new ArrayList<>();
        String patternStr = "^p=< ?(-?\\d*), ?(-?\\d*), ?(-?\\d*)>, v=< ?(-?\\d*), ?(-?\\d*), ?(-?\\d*)>, a=< ?(-?\\d*), ?(-?\\d*), ?(-?\\d*)>$";
        Pattern pattern = Pattern.compile(patternStr);

        for(String particleStr: inputStr.split("\n")) {
            Matcher m = pattern.matcher(particleStr);
            if(m.matches()) {
                List<Point> particlePoint = new ArrayList<>();
                for(int i=0; i<3; i++)
                    particlePoint.add(new Point(Integer.valueOf(m.group(i*3+1)), Integer.valueOf(m.group(i*3+2)), Integer.valueOf(m.group(i*3+3))));
                particles.add(particlePoint);
            }
        }

        int closestIndex = 0;
        List<Point> closestPoint = particles.get(0);
        for(int i=1; i<particles.size(); i++) {
            List<Point> otherPoint = particles.get(i);
            //Slight approximation here, the always-correct solution is even more complex
            if(otherPoint.get(2).absSum() < closestPoint.get(2).absSum() // 1. lowest acceleration or ...
               || (otherPoint.get(2).absSum() == closestPoint.get(2).absSum() // ... 2. equal acceleration, with either ...
                    && (otherPoint.get(1).absSum() < closestPoint.get(1).absSum() // ... 2.1. lowest velocity or ...
                        || (otherPoint.get(1).absSum() == closestPoint.get(1).absSum() //... 2.2.1. equal velocity ...
                            && otherPoint.get(0).absSum() < closestPoint.get(0).absSum())))) { ///... 2.2.2. but lowest starting coordinates
                closestIndex = i;
                closestPoint = otherPoint;
            }
        }
        System.out.println("Closest point is " + closestIndex + ": " + closestPoint.stream().map(p -> p.x + " " + p.y + " " + p.z).collect(Collectors.joining(", ")));
    }
}
