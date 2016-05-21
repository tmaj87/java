package it.justDo.evolution;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Unit {

    private final Random random = new Random();
    private final BitSet dna; // new boolean[size];
    private int size;

    public Unit(int size) {
        this.size = size;
        dna = new BitSet(size);
        for (int i = 0; i < size; i++) {
            dna.set(i, random.nextBoolean());
        }
    }

    public Unit(int size, BitSet dna) {
        this.size = size;
        this.dna = dna;
    }

    public void mutate(int times) {
        int cycles = 0;
        Set<Integer> mutatedGenes = new HashSet<>();
        do {
            final int gene = random.nextInt(size);
            if (!mutatedGenes.contains(gene)) {
                dna.flip(gene);
                mutatedGenes.add(gene);
                cycles++;
            }
        } while (cycles < times);
    }

    public BitSet getDna() {
        return dna;
    }
}
