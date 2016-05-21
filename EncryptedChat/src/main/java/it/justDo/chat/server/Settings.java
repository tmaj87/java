package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

class Settings {

    final Phaser coordinator = new Phaser();
    final Queue<FromServerMessage> messages = new ConcurrentLinkedQueue<>();
    final Set<String> users = new HashSet<>();
    CountDownLatch gate;

    {
        resetGate();
    }

    private void resetGate() {
        gate = new CountDownLatch(1);
    }

    void openGate() {
        gate.countDown();
        resetGate();
    }

}
