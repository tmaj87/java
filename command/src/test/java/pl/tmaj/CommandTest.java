package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    public static final String IP = "IP";
    private final CommandListener listener = new CommandListener();

    @Test
    public void shouldExecuteTimeCommand() {
        String time = listener.execute(new CommandGetTime());

        assertEquals("time", time);
    }

    @Test
    public void shouldExecutePingCommand() {
        String ping = listener.execute(new CommandPing());

        assertEquals(null, ping);
    }

    @Test
    public void shouldExecutePingWithIp() {
        String ping = listener.execute(new CommandPing(IP));

        assertEquals(IP, ping);
    }

    @Test
    public void shouldExecuteCountCommand() {
        String count = listener.execute(new CommandCount());

        assertEquals("count-1", count);
    }
}
