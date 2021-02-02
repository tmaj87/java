package pl.tmaj.bankkata;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@AllArgsConstructor
@EqualsAndHashCode
public class Balance {

    private final BigDecimal money;

    public static Balance of(double money) {
        return new Balance(BigDecimal.valueOf(money));
    }

    public static Balance of(BigDecimal money) {
        return new Balance(money);
    }

    public static Balance zero() {
        return of(BigDecimal.ZERO);
    }

    public Balance add(Balance balance) {
        return of(this.money.add(balance.money));
    }

    public Balance subtract(Balance balance) {
        return of(this.money.subtract(balance.money));
    }
}
