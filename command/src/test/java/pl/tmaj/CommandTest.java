package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    public static final String IP = "IP";
    private final CommandListener listener = new CommandListener();

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

    @Test
    public void shouldCountCommands() {
        CommandListener listener = new CommandListener();

        listener.execute(new CommandCount());
        listener.execute(new CommandCount());

        assertEquals("CommandCount-2", listener.count());
    }
}
