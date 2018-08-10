package pl.tmaj;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RemoteTest {

    private static final int VOLUME_IN_RANGE = 43;
    private static final int VOLUME_BELOW_ZERO = -17;
    private static final int VOLUME_ABOVE_100 = 112;

    @ParameterizedTest
    @MethodSource("deviceSource")
    public void shouldRemotelyTurnOffDevice(Device device) {
        Remote remote = new Remote(device);

        remote.togglePower();

        assertEquals(false, device.isEnabled());
    }

    @ParameterizedTest
    @MethodSource("deviceSource")
    public void shouldRemotelyChangeVolume(Device device) {
        Remote remote = new Remote(device);

        remote.setVolume(VOLUME_IN_RANGE);

        assertEquals(false, device.isEnabled());
    }

    @ParameterizedTest
    @MethodSource("deviceSource")
    public void shouldSetVolumeOnlyInRange1To100(Device device) {
        Remote remote = new Remote(device);

        remote.setVolume(VOLUME_IN_RANGE);

        assertEquals(VOLUME_IN_RANGE, device.getVolume());
    }

    @ParameterizedTest
    @MethodSource("deviceSource")
    public void shouldNotBeAbleToSetTooLowVolume(Device device) {
        Remote remote = new Remote(device);

        remote.setVolume(VOLUME_BELOW_ZERO);

        assertNotEquals(VOLUME_BELOW_ZERO, device.getVolume());
    }

    @ParameterizedTest
    @MethodSource("deviceSource")
    public void shouldNotBeAbleToSetTooHighVolume(Device device) {
        Remote remote = new Remote(device);

        remote.setVolume(VOLUME_ABOVE_100);

        assertNotEquals(VOLUME_ABOVE_100, device.getVolume());
    }

    public static Stream<Arguments> deviceSource() {
        return Stream.of(
                Arguments.of(new Tv()),
                Arguments.of(new Radio())
        );
    }
}
