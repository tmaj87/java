package pl.tmaj.fakturownia;

import lombok.extern.log4j.Log4j2;
import pl.tmaj.fakturownia.printer.ConsolePrinter;
import pl.tmaj.fakturownia.printer.PdfPrinter;

public @Log4j2
class InvoiceMachine {

    private final Calculations calculations;

    public InvoiceMachine(double hours, double wage) {
        calculations = new Calculations(hours, wage);
    }

    public InvoiceMachine(double hours, double wage, double overHours) {
        calculations = new Calculations(hours, wage, overHours);
    }

    public void toPdf() {
        new PdfPrinter(calculations).print();
    }

    public void toConsole() {
        new ConsolePrinter(calculations).print();
    }
}