package it.justDo.misc;

import java.util.ArrayList;
import java.util.List;

public class ArrayListCreator<T> {
    public List<T> getFor(T... args) {
        List<T> list = new ArrayList<>();
        for (T arg : args) {
            list.add(arg);
        }
        return list;
    }
}
