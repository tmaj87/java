package pl.tmaj.bankkata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    private Account account;

    @BeforeEach
    void beforeAll() {
        account = new Account();
    }

    @Test
    void shouldBeAbleToDeposit() {
        assertEquals(deposit(10), Balance.of(10));
    }

    @Test
    void shouldBeAbleToDepositMultipleTimes() {
        deposit(8);
        assertEquals(deposit(4), Balance.of(12));
    }

    @Test
    void shouldBeAbleToWithdraw() {
        deposit(11);
        assertEquals(withdraw(10), Balance.of(1));
    }

    private Balance withdraw(double amount) {
        return account.withdraw(Balance.of(amount));
    }

    private Balance deposit(double amount) {
        return account.deposit(Balance.of(amount));
    }
}