package it.justDo.chat.server;

import it.justDo.chat.common.Log4j;
import it.justDo.chat.common.Shared;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

class AServer {

    private static final ExecutorService POOL = Executors.newCachedThreadPool();
    private final Logger log4j = Log4j.getInstance(this);
    private Settings settings = new Settings();

    private AServer(int port) {
        POOL.execute(() -> new MessageCleaner(settings));
        POOL.execute(() -> new UsersNotifier(settings));

        try (ServerSocket listener = new ServerSocket(port)) {
            while (true) {
                final Socket client = listener.accept();
                POOL.execute(() -> new ClientHandler(client, settings));
            }
        } catch (IOException e) {
            log4j.log(WARNING, e.getMessage());
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
