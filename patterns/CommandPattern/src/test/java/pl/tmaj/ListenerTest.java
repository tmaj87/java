package pl.tmaj;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ListenerTest {

    @Test
    public void shouldCountCommands() {
        Listener listener = new Listener();
        Map<String, Integer> map = new HashMap<>();
        map.put("CommandNull", 2);

        listener.execute(new CommandNull());
        listener.execute(new CommandNull());

        assertEquals(map, listener.count());
    }

    @Test
    public void shouldExecuteCommand() {
        Listener listener = new Listener();

        String command = listener.execute(new CommandNull());

        Assert.assertEquals("", command);
    }
}