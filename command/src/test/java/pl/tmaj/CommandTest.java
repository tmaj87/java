package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    public static final String IP = "IP";

    @Test
    public void shouldExecuteTimeCommand() {
        String time = new CommandGetTime().execute();

        assertEquals("time", time);
    }

    @Test
    public void shouldExecutePingCommand() {
        String ping = new CommandPing().execute();

        assertEquals("ping", ping);
    }

    @Test
    public void shouldExecutePingWithIp() {
        String ping = new CommandPing().execute(IP);

        assertEquals("pinging " + IP, ping);
    }

    @Test
    public void shouldExecuteCountCommand() {
        String count = new CommandCount().execute();

        assertEquals("count-1", count);
    }
}
