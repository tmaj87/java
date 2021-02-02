package pl.tmaj.bankkata;

public class Account {

    private Balance balance = Balance.zero();

    public Balance deposit(Balance deposit) {
        balance = balance.add(deposit);
        return balance;
    }

    public Balance withdraw(Balance withdraw) {
        balance = balance.subtract(withdraw);
        return balance;
    }
}
