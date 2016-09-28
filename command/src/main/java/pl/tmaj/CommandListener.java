package pl.tmaj;

import java.util.HashMap;
import java.util.Map;

public class CommandListener {

    private final Map<String, Integer> map = new HashMap<>();

    public String execute(Command command) {
        return command.execute();
    }
}