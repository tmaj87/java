package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

class Settings {

    private static Settings instance = null;

    final Phaser coordinator = new Phaser();
    final Queue<FromServerMessage> messages = new ConcurrentLinkedQueue<>();
    final Set<String> users = new HashSet<>();
    CountDownLatch gate;

    private Settings() {
        resetGate();
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private void resetGate() {
        gate = new CountDownLatch(1);
    }

    void openGate() {
        gate.countDown();
        resetGate();
    }

}
