package pl.tmaj;

public class CommandPing implements Command {

    private final String ip;

    public CommandPing(String ip) {
        this.ip = ip;
    }

    @Override
    public String execute() {
        return isIpPresent() ? ip : "";
    }

    private boolean isIpPresent() {
        return ip != null;
    }

}
