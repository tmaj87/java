package pl.tmaj.fakturownia.calculations;

import org.junit.jupiter.api.Test;
import pl.tmaj.fakturownia.Calculations;

import static org.junit.jupiter.api.Assertions.*;

public class CalculationsTest {

    private static final double HOURS = 160;
    private static final double WAGE = 100;
    private static final double OVER_HOURS = 4;

    private Calculations calculations = new Calculations(HOURS, WAGE, OVER_HOURS);

    @Test
    void shouldReturnBaseSalary() {
        assertEquals(16000, calculations.getRegularPay());
    }

    @Test
    void shouldReturnOvertimeBonus() {
        assertEquals(600, calculations.getOverHoursPay());
    }

    @Test
    void shouldReturnOverallWorkedHours() {
        assertEquals(166, calculations.getWorkedHours());
    }

    @Test
    void shouldReturnNettSalary() {
        assertEquals(16600, calculations.getNettSalary());
    }

    @Test
    void shouldReturnVat() {
        assertEquals(3818, calculations.getVatTax());
    }

    @Test
    void shouldReturnSummaryAmount() {
        assertEquals(20418, calculations.getWithWorkTax());
    }

    @Test
    void shouldReturnIncomeForRegularHours() {
        assertEquals(12960, calculations.getRegularPayAfterWorkTax());
    }

    @Test
    void shouldReturnIncomeForOverHours() {
        assertEquals(486, calculations.getOverHoursPayAfterWorkTax());
    }
}