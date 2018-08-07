package pl.tmaj;

import org.junit.Test;

import static java.util.Arrays.*;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static pl.tmaj.PrimeFactors.factorOf;

public class PrimeFactorsTest {

    @Test
    public void of_1isNone() {
        assertEquals(factorOf(1), emptyList());
    }

    @Test
    public void of_2is2() throws Exception {
        assertEquals(factorOf(2), asList(2));
    }

    @Test
    public void of_3is3() throws Exception {
        assertEquals(factorOf(3), asList(3));
    }

    @Test
    public void of_4is2_2() throws Exception {
        assertEquals(factorOf(4), asList(2, 2));
    }

    @Test
    public void of_6is2_3() throws Exception {
        assertEquals(factorOf(6), asList(2, 3));
    }

    @Test
    public void of_6is2_2_2() throws Exception {
        assertEquals(factorOf(8), asList(2, 2, 2));
    }

    @Test
    public void of_9is3_3() throws Exception {
        assertEquals(factorOf(9), asList(3, 3));
    }

    @Test
    public void of_acceptanceTest() throws Exception {
        assertEquals(factorOf(2*2*2*2*3*3*3*7*11), asList(2, 2, 2, 2, 3, 3, 3, 7, 11));
    }
}
