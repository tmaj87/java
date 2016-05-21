package pl.tmaj;

import java.util.Optional;
import java.util.Random;

public class Message {
    private double ID;
    private String message;

    public Message()
    { // c++
        setKey();
    }

    public Message(String text) { // java
        setKey();
        message = text;
    }

    @Deprecated
    private double getKey() {
        return new Random().nextDouble();
    }

    private void setKey() {
        ID = new Random().nextDouble();
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }
}
