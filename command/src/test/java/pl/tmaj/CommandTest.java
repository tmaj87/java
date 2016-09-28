package pl.tmaj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandTest {

    public static final String IP = "IP";

    @Test
    public void shouldGetTime() {
        String time = new CommandGetTime().execute();

        assertEquals("time", time);
    }

    @Test
    public void shouldGetPing() {
        String ping = new CommandPing().execute();

        assertEquals("ping", ping);
    }

    @Test
    public void shouldGetPingWithIp() {
        String ping = new CommandPing().execute(IP);

        assertEquals("pinging " + IP, ping);
    }
}
