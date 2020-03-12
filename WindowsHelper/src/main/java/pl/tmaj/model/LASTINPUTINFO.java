package pl.tmaj.model;

import com.sun.jna.Structure;

import java.util.List;

import static java.util.Arrays.asList;

public class LASTINPUTINFO extends Structure {

    public int cbSize = 8;
    public int dwTime;

    @SuppressWarnings("rawtypes")
    @Override
    protected List getFieldOrder() {
        return asList("cbSize", "dwTime");
    }
}