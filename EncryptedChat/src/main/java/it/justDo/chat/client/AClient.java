package it.justDo.chat.client;

import it.justDo.chat.common.FromServerMessage;
import it.justDo.chat.common.Shared;
import it.justDo.chat.common.ToServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class AClient {

    private final Stack<ToServerMessage> messages = new Stack<>();
    private final ChatWindow window = new ChatWindow(this);
    private final AesEncryption safety = new AesEncryption();
    private final Map<String, String> userColorMap = new HashMap<>();
    private boolean isConnected = true;

    public static void main(String[] args) {
        String ip = Shared.IP;
        if (args.length > 0 && Shared.isIP(args[0])) {
            ip = args[0];
        }
        int port = Shared.PORT;
        if (args.length > 1) {
            int intPort = Integer.parseInt(args[1]);
            if (Shared.isInPortRange(intPort)) {
                port = intPort;
            }
        }
        try {
            new AClient(new Socket(ip, port));
        } catch (IOException e) {
            System.out.println("Couldn't connect to this ip/port combination.");
        }
    }

    public AClient(Socket socket) {
        Executor pool = Executors.newFixedThreadPool(2);
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            pool.execute(() -> messageReader(inStream));
            pool.execute(() -> messageWriter(outStream));
            window.writeToDisplay("<i>Connected to " + socket.getInetAddress().getHostAddress() + "</i>");
            window.makeVisible();
        } catch (IOException e) {
            System.out.println("Encountered error while establishing connection.");
        }
    }

    private void messageReader(ObjectInputStream inStream) {
        FromServerMessage oneObject;
        try {
            while ((oneObject = (FromServerMessage) inStream.readObject()) != null) {
                switch (oneObject.type) {
                    case 1: // messages
                        window.writeToDisplay("<div><b>"
                                + translateUser(oneObject.who)
                                + "</b> <font color='rgb(127,127,127)'>("
                                + oneObject.date
                                + "):</font> "
                                + safety.decrypt(oneObject.content, oneObject.vector)
                                + "</div>");
                        window.toFront();
                        break;
                    case 2: // users
                        window.writeToUsersList(translateUsers(oneObject.users));
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            closeConnection();
        }
    }

    private void closeConnection() {
        isConnected = false;
        window.writeToDisplay("<i>Server closed connection</i>");
    }

    private void messageWriter(ObjectOutputStream outStream) {
        try {
            while (isConnected) {
                if (messages.size() > 0) {
                    outStream.writeObject(messages.pop());
                }
            }
        } catch (IOException e) {
            closeConnection();
        }
    }

    public void putMessage(String message) {
        messages.push(safety.encrypt(message));
    }

    public void setEncryptionKey(String string) {
        safety.setKey(string);
    }

    private String getRGBColor() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rgb(");
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(random.nextInt(216) + 20);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private String getColoredTag(String user) {
        return "<font color='"
                + getRGBColor()
                + "'>"
                + user
                + "</font>";
    }

    private String translateUser(String user) {
        if (userColorMap.get(user) == null) {
            userColorMap.put(user, getColoredTag(user));
        }
        return userColorMap.get(user);
    }

    private String translateUsers(String[] users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String user : users) {
            stringBuilder.append("<div style='margin: 0; text-align: left;'>")
                    .append(translateUser(user))
                    .append("</div>");
        }
        return stringBuilder.toString();
    }
}
