import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.tmaj.Message;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {

    private static final String EXPECTED_VALUE = "foo is bar";

    @Mock private Message mockMessage;

    @Before
    public void beforeTests() {
        System.out.println("Beginning of test...");
    }

    @After
    public void afterTests() {
        System.out.println("Test executed.");
    }

    @Test
    public void shouldReturnSameMessage() {
        Message message = new Message(EXPECTED_VALUE);

        when(mockMessage.getMessage()).thenReturn(Optional.of(EXPECTED_VALUE));

        assertEquals(message.getMessage(), mockMessage.getMessage());
    }
}
