package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;
import it.justDo.chat.common.Log4j;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

class Coordinator {

    private static Coordinator instance = null;
    final Phaser phaser = new Phaser();
    final Queue<FromServerMessage> messages = new ConcurrentLinkedQueue<>();
    final Set<String> users = new HashSet<>();
    private final Log4j log4j = new Log4j(this);
    CountDownLatch gate;

    private Coordinator() {
        resetGate();
    }

    static Coordinator getInstance() {
        if (instance == null) {
            instance = new Coordinator();
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

    boolean isNewMessagePresent() {
        return messages.size() > 0;
    }

    void postNewMessage(FromServerMessage message) {
        if (!messages.offer(message)) {
            log4j.WARN("Queue full.");
        }
    }

    void register(String name) {
        phaser.register();
        users.add(name);
    }

    void deregister(String name) {
        phaser.arriveAndDeregister();
        users.remove(name);
    }
}
