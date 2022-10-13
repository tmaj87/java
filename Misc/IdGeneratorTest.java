package pl.tmaj;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IdGeneratorTest {

    private IdGenerator generator = new IdGenerator();

    private final String toEncrypt = "The quick brown fox jumps over the lazy dog";

    @Test
    void shouldGetIds() {
        String[] params = new String[]{
                "abcdefghjklmnpqvwxy",
                "abcdefghjklmnpqvwxy",
                "-/",
                "0123"
        };
        List<String> result = generator.getAllIds(params, 12);
        assertEquals(List.of("aa-0", "aa-1", "aa-2", "aa-3", "aa/0",
                "aa/1", "aa/2", "aa/3", "ab-0", "ab-1", "ab-2", "ab-3"), result);
    }

    @Test
    void shouldEncrypt() {
        Map<String, String> dictionary = prepareDictionary();
        String encrypted = Stream.of(toEncrypt.split(""))
                .map(dictionary::get)
                .collect(joining());

        assertEquals("ime!eoESedexeJe1e6ESeFeheqeTeZESeGeqeEESevexe_eYeiESeqeHeoehESeme!eoESe7eze.eDESeOeqeA", encrypted);
    }

    @Test
    void shouldDecrypt() {
        String encrypted = "ime!eoESedexeJe1e6ESeFeheqeTeZESeGeqeEESevexe_eYeiESeqeHeoehESeme!eoESe7eze.eDESeOeqeA";
        Map<String, String> reversedDictionary = prepareDictionary().entrySet().stream()
                .collect(toMap(Entry::getValue, Entry::getKey));
        String decrypted = Pattern.compile(".{2}")
                .matcher(encrypted)
                .results()
                .map(MatchResult::group)
                .map(reversedDictionary::get)
                .collect(joining());

        assertEquals(toEncrypt, decrypted);
    }

    private Map<String, String> prepareDictionary() {
        String[] params = new String[]{
                "LEieOoA(fFHV(_.cn0AkpE!U_(jw3vyQ",
                "SzF1OoGA!Jv67_ZqYdhimxHTED.tw2su"
        };
        List<String> result = generator.getAllIds(params, 127);
        return IntStream.range(32, 127)
                .boxed()
                .collect(toMap(i -> String.valueOf((char) i.byteValue()), result::get));
    }
}
