package pl.tmaj;

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
}