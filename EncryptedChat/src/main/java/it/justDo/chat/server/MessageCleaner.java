package it.justDo.chat.server;

class MessageCleaner {

    private final Settings settings = Settings.getInstance();

    public MessageCleaner() {
        settings.coordinator.register();
        init();
    }

    private void init() {
        while (true) {
            if (settings.messages.size() > 0) {
                settings.coordinator.arriveAndAwaitAdvance();
                settings.messages.poll();
                settings.openGate();
            }
        }
    }
}
