package pl.tmaj.bankkata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pl.tmaj.bankkata.Balance.zero;

class BalanceTest {

    @Test
    void shouldAdd() {
        assertEquals(Balance.of(10), zero().add(Balance.of(10)));
    }

    @Test
    void shouldSubtract() {
        assertEquals(Balance.of(-10), zero().subtract(Balance.of(10)));
    }
}