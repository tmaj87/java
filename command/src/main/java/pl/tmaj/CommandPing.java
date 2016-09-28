package pl.tmaj;

public class CommandPing implements Command {

    @Override
    public String execute() {
        return "ping";
    }

    @Override
    public String execute(String... args) {
        return "pinging " + args[0];
    }
}
