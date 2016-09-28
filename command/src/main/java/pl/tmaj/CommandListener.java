package pl.tmaj;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CommandListener {

    private final Map<String, Integer> map = new HashMap<>();

    public String execute(Command command) {
        recordCommand(command);
        return command.execute();
    }

    public String count() {
        StringBuilder sBuilder = new StringBuilder();
        for (Entry<String, Integer> entry : map.entrySet()) {
            sBuilder.append(entry.getKey() + "-" + entry.getValue());
        }
        return sBuilder.toString();
    }

    private void recordCommand(Command command) {
        String name = getCommandName(command);
        Integer value = map.get(name);
        record(name, value);
    }

    private void record(String name, Integer value) {
        if (value != null) {
            map.put(name, value + 1);
        } else {
            map.put(name, 1);
        }
    }

    private String getCommandName(Command command) {
        return command.getClass().getSimpleName();
    }
}