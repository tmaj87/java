package it.justDo.chat.server;

import it.justDo.chat.common.Log4j;
import it.justDo.chat.common.Shared;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AServer {

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final Log4j log4j = new Log4j(this);

    private AServer(int port) {
        pool.execute(MessageCleaner::new);
        pool.execute(UsersNotifier::new);
        listenOn(port);
    }

    private void listenOn(int port) {
        try (ServerSocket listener = new ServerSocket(port)) {
            while (true) {
                final Socket client = listener.accept();
                pool.execute(() -> new ClientHandler(client));
            }
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = Shared.PORT;
        if (args.length > 0) {
            int intPort = Integer.parseInt(args[0]);
            if (Shared.isInPortRange(intPort)) {
                port = intPort;
            }
        }
        new AServer(port);
    }
}
