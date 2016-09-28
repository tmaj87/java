package pl.tmaj;

import java.util.HashMap;
import java.util.Map;

public class CommandListener {

    private final Map<String, Integer> map = new HashMap<>();

    public String execute(Command command) {
        recordCommand(command);
        return command.execute();
    }

    private void recordCommand(Command command) {
        String name = getCommandName(command);
        Integer value = map.get(name);
        record(name, value);
    }

    private void record(String name, Integer value) {
        if (value != null) {
            map.put(name, value++);
        } else {
            map.put(name, 1);
        }
    }

    private String getCommandName(Command command) {
        return command.getClass().getName();
    }
}