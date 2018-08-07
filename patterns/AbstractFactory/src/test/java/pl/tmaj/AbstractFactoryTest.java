package pl.tmaj;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AbstractFactoryTest {

    @ParameterizedTest
    @MethodSource("factoryProvider")
    void shouldPrintLayout(GUIFactory factory) {
        System.out.println("printing layout for " + factory.getClass());
        factory.createButton().paint();
        factory.createCheckbox().paint();
    }

    public static Stream<Arguments> factoryProvider() {
        return Stream.of(
                Arguments.of(new WindowsFactory()),
                Arguments.of(new MacOsFactory())
        );
    }
}