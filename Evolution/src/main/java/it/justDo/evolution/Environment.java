package it.justDo.evolution;

import java.util.*;

public class Environment {

    private final Random random = new Random();
    private List<Unit> pool = new ArrayList<>();
    private int desiredPoolSize;
    private int dnaSize;

    public Environment(int desiredPoolSize, int dnaSize) {
        this.desiredPoolSize = desiredPoolSize;
        this.dnaSize = dnaSize;
        for (int i = 0; i < desiredPoolSize; i++) {
            pool.add(new Unit(dnaSize));
        }
    }

    public void cross(int crossFactor, Set<Integer> swapLines) {
        int crosses = 0;
        Map<Integer, Integer> crossedUnits = new HashMap<>();
        do {
            final int male = random.nextInt(pool.size());
            final int female = random.nextInt(pool.size());
            if (male == female) {
                continue;
            }
            if ((crossedUnits.get(male) == null || crossedUnits.get(female) == null) || (crossedUnits.get(male) != female || crossedUnits.get(female) != male)) {
                crossingAction(pool.get(male), pool.get(female), swapLines);
                crossedUnits.put(male, female);
                crossedUnits.put(female, male);
                crosses++;
            }
        } while (crosses < crossFactor);
    }

    private void crossingAction(Unit male, Unit female, Set<Integer> swapLines) {
        BitSet maleDNA = male.getDna();
        BitSet femaleDNA = female.getDna();
        BitSet boy = new BitSet();
        BitSet girl = new BitSet();
        boolean swapped = false;
        for (int i = 0; i < dnaSize; i++) {
            if (swapLines.contains(i)) {
                swapped = !swapped;
            }
            if (swapped) {
                boy.set(i, femaleDNA.get(i));
                girl.set(i, maleDNA.get(i));
            } else {
                boy.set(i, maleDNA.get(i));
                girl.set(i, femaleDNA.get(i));
            }
        }
        pool.add(new Unit(dnaSize, boy));
        pool.add(new Unit(dnaSize, girl));
    }

    public void mutate(int mutationFactor, int times) {
        int mutations = 0;
        Set<Integer> mutatedUnits = new HashSet<>();
        do {
            final int unit = random.nextInt(pool.size());
            if (!mutatedUnits.contains(unit)) {
                pool.get(unit).mutate(times);
                mutatedUnits.add(unit);
                mutations++;
            }
        } while (mutations < mutationFactor);
    }

    private void sortPool() { // reverse order
        Collections.sort(pool, new EvolutionComparator());
    }

    public int evaluate(Unit unit) {
        BitSet dna = unit.getDna();
        int value = 0;
        for (int i = 0; i < dnaSize; i++) {
            if (dna.get(i)) {
                value++;
            }
        }
        return value;
    }

    public void selectViaRussianRoulette() {
        List<Unit> newPool = new ArrayList<>(desiredPoolSize);
        List<Integer> roulette = new LinkedList<>();
        int totalValue = 0;
        for (int i = 0; i < pool.size(); i++) {
            final int singleValue = evaluate(pool.get(i));
            for (int j = totalValue; j < totalValue + singleValue; j++) {
                roulette.add(j, i);
            }
            totalValue += singleValue;
        }
        int tryNumber = 0;
        while (tryNumber < desiredPoolSize) {
            final int drawnNumber = roulette.get(random.nextInt(totalValue));
            newPool.add(tryNumber, pool.get(drawnNumber));
            tryNumber++;
        }
        pool = newPool;
    }

    public void throwWeakestAway(int count) throws Exception {
        if (count < 1 || count > desiredPoolSize) {
            throw new Exception("Invalid throw count.");
        }
        sortPool();
        for (int i = pool.size() - count; i < pool.size(); i++) {
            pool.remove(i);
        }
        fixPopulationSize();
    }

    private void repopulateWithBest(int count) {
        sortPool();
        for (int i = 0; i < count; i++) {
            pool.add(pool.get(i));
        }
    }

    private void fixPopulationSize() throws Exception {
        int poolDiff = desiredPoolSize - pool.size();
        if (poolDiff > 0) {
            repopulateWithBest(poolDiff);
        } else if (poolDiff < 0) {
            throwWeakestAway(Math.abs(poolDiff));
        }
    }

    public List<Unit> getPool() {
        return pool;
    }

    private class EvolutionComparator implements Comparator<Unit> {
        public int compare(Unit obj1, Unit obj2) {
            return evaluate(obj2) - evaluate(obj1);
        }
    }
}
