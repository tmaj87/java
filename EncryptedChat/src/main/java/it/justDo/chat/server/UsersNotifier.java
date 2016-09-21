package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;
import it.justDo.chat.common.Log4j;

class UsersNotifier {

    private final Settings settings;
    private final Log4j log4j = new Log4j(this);

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
            log4j.WARN(e.getMessage());
        }
    }
}
