package it.justDo.evolution;

import java.util.HashSet;
import java.util.Set;

public class App {

    public static final int DNA_SIZE = 12;
    public static final int POOL_SIZE = 16;
    public static final int CROSS_FACTOR = 2;
    public static final Set<Integer> CROSSING_LINES = new HashSet<>();
    {
        CROSSING_LINES.add(3);
        CROSSING_LINES.add(8);
    }
    public static final int MUTATION_FACTOR = 1;
    public static final int MUTATION_COUNT = 1;
    public static final int THROW_AWAY_FACTOR = 2;
    
    public static void main(String[] args) {
        Environment environment = new Environment(POOL_SIZE, DNA_SIZE);

        environment.selectViaRussianRoulette();
        for (int i = 0; i < 100; i++) {
            environment.mutate(MUTATION_FACTOR, MUTATION_COUNT);
            environment.cross(CROSS_FACTOR, CROSSING_LINES);

            try {
                environment.throwWeakestAway(THROW_AWAY_FACTOR);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Unit unit : environment.getPool()) {
            System.out.println(unit.getDna() + " = " + environment.evaluate(unit));
        }
    }
}
