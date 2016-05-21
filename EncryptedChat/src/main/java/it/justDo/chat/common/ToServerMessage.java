package it.justDo.chat.common;

import java.io.Serializable;

public class ToServerMessage implements Serializable {
    public final byte[] message;
    public final byte[] vector;

    public ToServerMessage(byte[] message, byte[] vector) {
        this.message = message;
        this.vector = vector;
    }
}
