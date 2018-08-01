package aoc17;

import java.io.IOException;

public class Day15 {

    public static void main(String[] args) throws IOException {
        adventOfCode15_2();
    }

    @SuppressWarnings("unused")
    private static void adventOfCode15_2() throws IOException {
        int aSeed = 516, bSeed = 190;
        int aFactor = 16807, bFactor = 48271, ROUNDS = 5_000_000, MODULUS = Integer.MAX_VALUE, matches = 0,
                JUDGE_BITS = 16, JUDGE_CMP = 1 << JUDGE_BITS;
        double aPrev = aSeed, bPrev = bSeed;
        for(int i=0; i<ROUNDS; i++) {
            do {
                aPrev = (aPrev * aFactor) % MODULUS;
            } while(aPrev % 4 != 0);
            do {
                bPrev = (bPrev * bFactor) % MODULUS;
            } while(bPrev % 8 != 0);
            if(aPrev % JUDGE_CMP == bPrev % JUDGE_CMP)
                matches++;
        }
        System.out.println(matches);
    }

    @SuppressWarnings("unused")
    private static void adventOfCode15_1() throws IOException {
        int aSeed = 516, bSeed = 190;
        int aFactor = 16807, bFactor = 48271, ROUNDS = 40_000_000, MODULUS = Integer.MAX_VALUE, matches = 0,
                JUDGE_BITS = 16, JUDGE_CMP = 1 << JUDGE_BITS;
        double aPrev = aSeed, bPrev = bSeed;
        for(int i=0; i<ROUNDS; i++) {
            aPrev = (aPrev * aFactor) % MODULUS;
            bPrev = (bPrev * bFactor) % MODULUS;
            if(aPrev % JUDGE_CMP == bPrev % JUDGE_CMP)
                matches++;
        }
        System.out.println(matches);
    }
}
