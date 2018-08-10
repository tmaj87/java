package pl.tmaj;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvancedRemoteTest {

    @ParameterizedTest
    @MethodSource("deviceSource")
    public void shouldMuteDevice(Device device) {
        AdvancedRemote remote = new AdvancedRemote(device);

        remote.mute();

        assertEquals(0, device.getVolume());
    }

    public static Stream<Arguments> deviceSource() {
        return Stream.of(
                Arguments.of(new Tv()),
                Arguments.of(new Radio())
        );
    }
}
