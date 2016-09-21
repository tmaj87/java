package it.justDo.chat.server;

class MessageCleaner {

    private final Coordinator coordinator = Coordinator.getInstance();

    MessageCleaner() {
        coordinator.phaser.register();
        init();
    }

    private void init() {
        while (true) {
            if (coordinator.isNewMessagePresent()) {
                cleanMessage();
            }
        }
    }

    private void cleanMessage() {
        coordinator.phaser.arriveAndAwaitAdvance();
        coordinator.messages.poll();
        coordinator.openGate();
    }
}
