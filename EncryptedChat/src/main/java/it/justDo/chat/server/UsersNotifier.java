package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;
import it.justDo.chat.common.Log4j;

class UsersNotifier {

    private final Coordinator coordinator = Coordinator.getInstance();
    private final Log4j log4j = new Log4j(this);

    UsersNotifier() {
        init();
    }

    private void init() {
        try {
            while (true) {
                if (coordinator.users.size() > 0) {
                    final FromServerMessage usersMessage = newMessage();
                    coordinator.postNewMessage(usersMessage);
                }
                Thread.sleep(900); // let's not spam too much
            }
        } catch (InterruptedException e) {
            log4j.WARN(e.getMessage());
        }
    }

    private FromServerMessage newMessage() {
        return new FromServerMessage(coordinator.users.toArray(new String[coordinator.users.size()]));
    }
}
