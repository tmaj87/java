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
    private final Coordinator coordinator = Coordinator.getInstance();
    private final Socket connection;
    private boolean isConnected = true;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String clientIp = "NO_IP";
    private String nick = "NO_NICK";

    ClientHandler(Socket client) {
        connection = client;
        setClientInfo();
        register();
        initCommunication();
        log4j.INFO(clientIp + " connected");
    }

    private void setClientInfo() {
        String hostName = connection.getInetAddress().getHostName();
        clientIp = connection.getInetAddress().getHostAddress();
        nick = Shared.getSHA256(hostName + Thread.currentThread().getName()).substring(0, 8);
    }

    private void register() {
        coordinator.register(nick);

    }

    private void initCommunication() {
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        }
        communicate();
    }

    private void communicate() {
        Executor pool = Executors.newFixedThreadPool(2);
        pool.execute(this::handleRead);
        pool.execute(this::handleWrite);
    }

    private void handleRead() {
        ToServerMessage toServerMessage;
        try {
            while ((toServerMessage = (ToServerMessage) input.readObject()) != null) {
                log4j.INFO(clientIp + " wrote");
                final FromServerMessage message = newMessage(toServerMessage);
                coordinator.postNewMessage(message);
            }
        } catch (Exception e) {
            closeConnection();
        }
    }

    private FromServerMessage newMessage(ToServerMessage message) {
        return new FromServerMessage(nick, message.content, message.vector);
    }

    private void handleWrite() {
        try {
            while (isConnected) {
                if (coordinator.isNewMessagePresent()) {
                    propagateMessage();
                }
            }
        } catch (IOException e) {
            closeConnection();
        } catch (InterruptedException e) {
            log4j.INFO(e.getMessage());
        }
    }

    private void propagateMessage() throws IOException, InterruptedException {
        output.writeObject(coordinator.messages.peek());
        coordinator.phaser.arriveAndAwaitAdvance();
        coordinator.gate.await();
    }

    private void closeConnection() {
        isConnected = false;
        deregister();
        disconnect();
    }

    private void deregister() {
        coordinator.deregister(nick);
    }

    private void disconnect() {
        try {
            connection.close();
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        } finally {
            log4j.INFO(clientIp + " disconnected");
        }
    }
}
