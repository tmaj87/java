package pl.tmaj.fakturownia.printer;

import pl.tmaj.fakturownia.Calculations;

public abstract class Printer {

    protected Calculations calculations;

    protected Printer(Calculations calculations) {
        this.calculations = calculations;
    }

    abstract void print();
}
