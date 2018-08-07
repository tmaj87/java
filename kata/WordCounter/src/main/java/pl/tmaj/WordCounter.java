package pl.tmaj;

public class WordCounter {

    private static final String WORD_SEPARATOR = " ";
    private final String sentence;
    private String[] words;

    public WordCounter(String sentence) {
        this.sentence = sentence;
        parseSentence();
    }

    private void parseSentence() {
        this.words = sentence.split(WORD_SEPARATOR);
    }

    public String getSentence() {
        return sentence;
    }

    public int getCount(String word) {
        int count = 0;
        for (String string : words) {
            if (string.equals(word)) {
                count++;
            }
        }
        return count;
    }
}
