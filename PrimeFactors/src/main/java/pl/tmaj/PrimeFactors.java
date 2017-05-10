package pl.tmaj;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactors {

    public static List<Integer> factorOf(Integer number) {
        List<Integer> factors = new ArrayList<>();
        for (int divisor = 2; number > 1; divisor++) {
            for (; number % divisor == 0; number /= divisor) {
                factors.add(divisor);
            }
        }
        return factors;
    }
}
