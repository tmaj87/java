package pl.tmaj;

import org.joda.time.DateTime;


public class CommandTime implements Command {

    private static final String FORMAT = "YYYY/MM/dd HH:mm:ss";
    private DateTime date = new DateTime();

    @Override
    public String execute() {
        return date.toString(FORMAT);
    }
}
