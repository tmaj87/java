package it.justDo.misc;

import java.util.*;

public class WaysToCreateCollections {
    private static Map<String, Integer> staticMap = new HashMap<>();
    static {
        staticMap.put("key1", 1);
        staticMap.put("key2", 2);
    }

    public WaysToCreateCollections() {
        anonymous();
        set();

        Collection<Double> collection = new LinkedList<>();
    }

    private void set() {
        Set<String> set = new HashSet<>();
        set.add("one");
        set.add("two");
    }

    private void anonymous() {
        List<Integer> anonymousList = new ArrayList<Integer>(){
            {
                add(1);
                add(2);
                add(3);
            }
        };
    }
}