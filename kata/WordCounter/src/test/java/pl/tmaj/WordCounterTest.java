package pl.tmaj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    private static final String SENTENCE = "Little fox is running in in the forest";
    private static final String SINGLE_OCCURRENCE_WORD = "fox";
    private static final String NON_EXISTING_WORD = "weather";
    private static final String DOUBLE_OCCURRENCE_WORD = "in";

    private WordCounter wordCounter = new WordCounter(SENTENCE);

    @Test
    void shouldConsumeSentence() {
        assertEquals(SENTENCE, wordCounter.getSentence());
    }

    @Test
    void shouldReturnOneForSingleOccurrence() {
        assertEquals(wordCounter.getCount(SINGLE_OCCURRENCE_WORD), 1);
    }

    @Test
    void shouldReturnZeroForNonExistingWord() {
        assertEquals(wordCounter.getCount(NON_EXISTING_WORD), 0);
    }

    @Test
    void shouldReturnTwoForDoubleOccurrence() {
        assertEquals(wordCounter.getCount(DOUBLE_OCCURRENCE_WORD), 2);
    }
}
