package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;
import it.justDo.chat.common.Shared;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

class AServer {

    Settings settings = new Settings();

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

    public AServer(int port) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket listener = new ServerSocket(port)) {
            pool.execute(() -> new MessageCleaner(settings));
            pool.execute(() -> new UsersNotifier(settings));
            while (true) {
                final Socket client = listener.accept();
                pool.execute(() -> new ClientHandler(client, settings));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
