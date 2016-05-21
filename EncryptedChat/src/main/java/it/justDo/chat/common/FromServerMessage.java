package it.justDo.chat.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FromServerMessage implements Serializable {
    public final int type;
    public final String who;
    public final String[] users;
    public final byte[] content; // TODO File - type 3
    public final byte[] vector;
    public final String date = getNow();

    private FromServerMessage(int type, String who, String[] users, byte[] content, byte[] vector) {
        this.type = type;
        this.who = who;
        this.users = users;
        this.content = content;
        this.vector = vector;
    }

    public FromServerMessage(String who, byte[] content, byte[] vector) {
        this(1, who, null, content, vector);
    }

    public FromServerMessage(String[] users) {
        this(2, null, users, null, null);
    }

    private String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
