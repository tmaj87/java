package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;

class UsersNotifier {

    private final Settings settings;

    public UsersNotifier(Settings settings) {
        this.settings = settings;
        init();
    }

    private void init() {
        try {
            while (true) {
                if (settings.users.size() > 0) {
                    final FromServerMessage usersMessage = new FromServerMessage(settings.users.toArray(new String[settings.users.size()]));
                    settings.messages.add(usersMessage);
                }
                Thread.sleep(900); // let's not spam too much
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
