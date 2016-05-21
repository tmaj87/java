import org.junit.Ignore;
import org.junit.Test;
import pl.tmaj.Infinite;
import pl.tmaj.Message;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void shouldEquals() {
        int math = 2*2;

        assertEquals(4, math);
    }

    @Test
    public void shouldNotNull() {
        Message isNotNull = new Message();

        assertNotNull(isNotNull);
    }

    @Test
    public void shouldNull() {
        Object isNull = null;

        assertNull(isNull);
    }

    @Test(timeout = 1000)
    public void shouldTimeout() {
        Infinite loop = new Infinite();

        assertSame(new Infinite(), loop);
    }

    @Ignore
    @Test
    public void ignoranceTest() {
    }
}
