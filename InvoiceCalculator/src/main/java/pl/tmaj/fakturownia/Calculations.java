package pl.tmaj.fakturownia;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculations {

    private static final BigDecimal OVER_HOUR_FACTOR = BigDecimal.valueOf(1.5);
    private static final BigDecimal VAT_23 = BigDecimal.valueOf(.23);
    private static final BigDecimal LINEAR_WORK_TAX = BigDecimal.valueOf(.19);

    private final BigDecimal workedHours;
    private final BigDecimal regularPay;
    private final BigDecimal regularPayTax;
    private final BigDecimal overHoursPay;
    private final BigDecimal overHoursPayTax;
    private final BigDecimal taxation;

    public Calculations(double hours, double wage) {
        this(BigDecimal.valueOf(hours), BigDecimal.valueOf(wage), BigDecimal.ZERO);
    }

    public Calculations(double hours, double wage, double overHours) {
        this(BigDecimal.valueOf(hours), BigDecimal.valueOf(wage), BigDecimal.valueOf(overHours));
    }

    public Calculations(BigDecimal hours, BigDecimal wage, BigDecimal overHours) {
        workedHours = hours.add(overHours.multiply(OVER_HOUR_FACTOR));
        regularPay = hours.multiply(wage);
        regularPayTax = regularPay.multiply(LINEAR_WORK_TAX);
        overHoursPay = overHours.multiply(OVER_HOUR_FACTOR).multiply(wage);
        overHoursPayTax = overHoursPay.multiply(LINEAR_WORK_TAX);
        taxation = regularPay.add(overHoursPay).multiply(VAT_23);
    }

    public double getWorkedHours() {
        return roundAndConvert(workedHours);
    }

    public double getRegularPay() {
        return roundAndConvert(regularPay);
    }

    public double getRegularPayAfterWorkTax() {
        return roundAndConvert(regularPay.subtract(regularPayTax));
    }

    public double getOverHoursPay() {
        return roundAndConvert(overHoursPay);
    }

    public double getOverHoursPayAfterWorkTax() {
        return roundAndConvert(overHoursPay.subtract(overHoursPayTax));
    }

    public double getVatTax() {
        return roundAndConvert(taxation);
    }

    public double getNettSalary() {
        return roundAndConvert(regularPay.add(overHoursPay));
    }

    public double getWithWorkTax() {
        return roundAndConvert(taxation.add(regularPay).add(overHoursPay));
    }

    private double roundAndConvert(BigDecimal value) {
        return value.setScale(2, RoundingMode.CEILING).doubleValue();
    }
}