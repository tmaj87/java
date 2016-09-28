package pl.tmaj;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListenerTest {

    @Test
    public void shouldCountCommands() {
        Listener listener = new Listener();

        listener.execute(new CommandNull());
        listener.execute(new CommandNull());

        assertEquals("CommandNull-2", listener.count());
    }

    @Test
    public void shouldExecuteCommand() {
        Listener listener = new Listener();

        String command = listener.execute(new CommandNull());

        Assert.assertEquals("", command);
    }
}