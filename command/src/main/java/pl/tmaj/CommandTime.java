package pl.tmaj;

import org.joda.time.DateTime;


public class CommandTime implements Command {

    private static final String FORMAT = "YYYY/MM/dd HH:mm:ss";

    @Override
    public String execute() {
        return new DateTime().toString(FORMAT);
    }
}
