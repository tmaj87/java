package it.justDo.chat.client;

import it.justDo.chat.common.HTMLJTextPane;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class ChatWindow extends JFrame {

    private static final Integer WIN_WIDTH = 600;
    private static final Integer WIN_HEIGHT = 328;
    private static final String WIN_TITLE = "Anonymous Chat";

    private final AClient caller;
    private final HTMLJTextPane displayField = new HTMLJTextPane();
    private final JScrollPane displayScroll = new JScrollPane(displayField);
    private final HTMLJTextPane usersList = new HTMLJTextPane();
    private final JScrollPane usersScroll = new JScrollPane(usersList);
    private final JTextArea messageInput = new JTextArea();
    private final JScrollPane messageScroll = new JScrollPane(messageInput);
    private final JButton sendButton = new JButton("Send");
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton passwordButton = new JButton("set key");

    ChatWindow(AClient aClient) {
        caller = aClient;
        setupWindow();
        setupFields();
        addFields();
    }

    private void setupWindow() {
        setTitle(WIN_TITLE);
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocationRelativeTo(null); // center window
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
    }

    private void setupFields() {
        passwordField.setBounds(65, 5, 200, 23);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setKeyAndPassFocus();
                }
            }
        });
        passwordButton.setBounds(270, 5, 130, 22);
        passwordButton.addActionListener(e -> setKeyAndPassFocus());
        displayField.setEditable(false);
        displayScroll.setBounds(5, 30, 420, 200);
        usersList.setEditable(false);
        usersScroll.setBounds(430, 30, 160, 200);
        messageInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    writeToServer();
                }
            }
        });
        messageScroll.setBounds(5, 235, 490, 60);
        sendButton.setBounds(500, 235, 90, 60);
        sendButton.addActionListener(e -> writeToServer());
    }

    private void addFields() {
        add(passwordField);
        add(passwordButton);
        add(displayScroll);
        add(usersScroll);
        add(messageScroll);
        add(sendButton);
    }

    private void writeToServer() {
        String filtered = filterInput();
        if (filtered.length() > 0) {
            caller.putMessage(filtered);
        }
        messageInput.setText("");
    }

    private void setKeyAndPassFocus() {
        String password = new String(passwordField.getPassword());
        caller.setEncryptionKey(password);
        passwordButton.setText("change key");
        passwordField.setText("");
        messageInput.grabFocus();
    }

    private String filterInput() {
        return messageInput.getText().trim();
    }

    void writeToDisplay(String string) {
        displayField.append(string);
        scrollDisplayToBottom();
    }

    private void scrollDisplayToBottom() {
        SwingUtilities.invokeLater(() -> { // waits for all Swing actions
            JScrollBar jScrollBar = displayScroll.getVerticalScrollBar();
            jScrollBar.setValue(jScrollBar.getMaximum());
        });
    }

    void writeToUsersList(String string) {
        usersList.setText(string);
    }

    void makeVisible() {
        setVisible(true);
        passwordField.grabFocus();
    }
}
