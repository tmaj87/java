package it.justDo.chat.server;

import it.justDo.chat.common.FromServerMessage;
import it.justDo.chat.common.Log4j;
import it.justDo.chat.common.Shared;
import it.justDo.chat.common.ToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class ClientHandler {

    private final Log4j log4j = new Log4j(this);
    private final Settings settings;
    private final Socket connection;
    private boolean isConnected = true;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String hostIp = "NO_IP";
    private String nick = "NO_NICK";

    ClientHandler(Socket client, Settings settings) {
        this.settings = settings;
        connection = client;
        setClientInfo();
        settings.coordinator.register();
        settings.users.add(nick);
        initCommunication();
        log4j.INFO(hostIp + " connected");
    }

    private void setClientInfo() {
        String hostName = connection.getInetAddress().getHostName();
        hostIp = connection.getInetAddress().getHostAddress();
        nick = Shared.getSHA256(hostName + Thread.currentThread().getName()).substring(0, 8);
    }

    private void initCommunication() {
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Executor pool = Executors.newFixedThreadPool(2);
        pool.execute(this::handleRead);
        pool.execute(this::handleWrite);
    }

    private void handleRead() {
        ToServerMessage oneObject;
        try {
            while ((oneObject = (ToServerMessage) input.readObject()) != null) {
                log4j.INFO(hostIp + " wrote");
                if (!settings.messages.offer(new FromServerMessage(nick, oneObject.message, oneObject.vector))) {
                    log4j.WARN("Queue full.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            closeConnection();
        }
    }

    private void handleWrite() {
        try {
            while (isConnected) {
                if (isMessagePresent()) {
                    output.writeObject(settings.messages.peek());
                    settings.coordinator.arriveAndAwaitAdvance();
                    settings.gate.await();
                }
            }
        } catch (IOException e) {
            closeConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isMessagePresent() {
        return settings.messages.size() > 0;
    }

    private void closeConnection() {
        isConnected = false;
        settings.coordinator.arriveAndDeregister();
        settings.users.remove(nick);
        try {
            connection.close();
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        } finally {
            log4j.INFO(hostIp + " disconnected");
        }
    }
}
