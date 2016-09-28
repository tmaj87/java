package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    public static final String IP = "IP";
    private final Listener listener = new Listener();

    @Test
    public void shouldGetCurrentTime() {
        String time = listener.execute(new CommandGetTime());

        assertEquals("time", time);
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
}
