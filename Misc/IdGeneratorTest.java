package pl.tmaj;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdGeneratorTest {

    private IdGenerator generator = new IdGenerator();

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
}
