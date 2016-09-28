package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    @Test
    public void shouldGetTime() {
        String time = CommandGetTime.execute();

        assertEquals("time", time);
    }

    @Test
    public void shouldGetPing() {
        String ping = CommandPing.execute();

        assertEquals("ping", ping);
    }
}
