package pl.tmaj;

public class CommandGetTime implements Command {

    @Override
    public String execute() {
        return "time";
    }

    @Override
    public String execute(String... args) {
        return "";
    }
}
