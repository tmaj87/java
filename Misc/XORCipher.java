package it.justDo.misc;

public class XORCipher {

    private char[] key;

    public XORCipher(String key) {
        setKey(key);
    }

    public void setKey(String key) {
        this.key = key.toCharArray();
    }

    public String cipher(String message) throws Exception {
        if (!validateKey()) {
            throw new Exception("Invalid key");
        }
        char[] messageArray = message.toCharArray();
        int messageLength = messageArray.length;
        int keyLength = key.length;
        char[] newMessage = new char[messageLength];
        for (int i = 0; i < messageLength; i++) {
            newMessage[i] = (char) (messageArray[i] ^ key[i % keyLength]);
        }
        return new String(newMessage);
    }

    private boolean validateKey() {
        return key.length > 0;
    }
}
