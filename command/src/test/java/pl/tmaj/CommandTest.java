package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandTest {

    private static final String IP = "IP";
    private static final String TIME_PATTERN = "[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
    private final Listener listener = new Listener();

    @Test
    public void shouldReturnTime() {
        String time = listener.execute(new CommandTime());

        assertTrue(time.matches(TIME_PATTERN));
    }

    @Test
    public void shouldExecutePingWithNoArgs() {
        String ping = listener.execute(new CommandPing());

        assertEquals("", ping);
    }

    @Test
    public void shouldExecutePingWithIp() {
        String ping = listener.execute(new CommandPing(IP));

        assertEquals(IP, ping);
    }

    @Test
    public void shouldReturnNothing() {
        String empty = listener.execute(new CommandNull());

        assertEquals("", empty);
    }
}
