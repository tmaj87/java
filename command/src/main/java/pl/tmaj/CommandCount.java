package pl.tmaj;

public class CommandCount implements Command {

    @Override
    public String execute() {
        return "count-1";
    }

    @Override
    public String execute(String... args) {
        return "";
    }
}
