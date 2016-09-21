package it.justDo.chat.common;

import java.io.Serializable;

public class ToServerMessage implements Serializable {
    public final byte[] content;
    public final byte[] vector;

    public ToServerMessage(byte[] content, byte[] vector) {
        this.content = content;
        this.vector = vector;
    }
}
