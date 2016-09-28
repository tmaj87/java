package pl.tmaj;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class CommandTest {

    private static final String IP = "IP";
    private static final String TIME = "1970/01/01 00:00:01";
    private final Listener listener = new Listener();

    @Mock DateTime dateTime;
    @InjectMocks CommandGetTime timeCommand;

    @Test
    public void shouldGetCurrentTime() {
        given(dateTime.toString(anyString())).willReturn(TIME);

        String time = listener.execute(timeCommand);

        assertEquals(TIME, time);
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
