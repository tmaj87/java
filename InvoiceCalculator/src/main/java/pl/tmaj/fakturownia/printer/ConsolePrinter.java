package pl.tmaj.fakturownia.printer;

import pl.tmaj.fakturownia.Calculations;

public class ConsolePrinter extends Printer {

    public ConsolePrinter(Calculations calculations) {
        super(calculations);
    }

    @Override
    public void print() {
        System.out.printf("hours worked = %f%n", calculations.getWorkedHours());
        System.out.printf("regular salary = %f (ex. tax %f)%n", calculations.getRegularPay(), calculations.getRegularPayAfterWorkTax());
        System.out.printf("over hours salary = %f (ex. tax %f)%n", calculations.getOverHoursPay(), calculations.getOverHoursPayAfterWorkTax());
        System.out.printf("nett salary = %f%n", calculations.getNettSalary());
        System.out.printf("VAT = %f%n", calculations.getVatTax());
        System.out.printf("summary = %f%n", calculations.getWithWorkTax());
    }
}
