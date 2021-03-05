package pl.tmaj.fakturownia;

import org.junit.jupiter.api.Test;

class InvoiceMachineTest {

    private static final double HOURS = 160;
    private static final double WAGE = 200;
    private static final double OVER_HOURS = 0;

    private InvoiceMachine invoiceMachine;

    @Test
    void shouldPrintToPdf() {
        invoiceMachine = new InvoiceMachine(HOURS, WAGE);
        invoiceMachine.toPdf();
    }

    @Test
    void shouldPrintToConsole() {
        invoiceMachine = new InvoiceMachine(HOURS, WAGE, OVER_HOURS);
        invoiceMachine.toConsole();
    }
}