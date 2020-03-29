package labor19.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class DictionaryTest {
    public static final String DICTIONARY_FILE = "src/main/resources/assets/de-DE1.dic";
    private Dictionary dic;

    @BeforeEach
    void initializeDictionary() throws IOException {
        dic = new Dictionary(DICTIONARY_FILE);
    }

    @Test
    void getPermutations_word_returnsAllPermutations() {
        Set<String> actual = dic.getPermutations("esi");

        assertThat(actual).containsExactly("Eis", "eis", "sei", "sie");
    }

    @Test
    void getPermutations_wordHadSlashInLine_returnsAllPermutations() {
        Set<String> actual = dic.getPermutations("Äbte");

        assertThat(actual).containsExactly("Äbte", "äbte");
    }

    @Test
    void getPermutations_wordEndsWithUng_returnsAllPermutations() {
        Set<String> actual = dic.getPermutations("Abschottung");

        assertThat(actual).containsExactly("Abschottung", "abschottung");
    }

    @Test
    void getPermutations_wordBlank_returnsEmptyList() {
        Set<String> actual = dic.getPermutations("");

        assertThat(actual).isEmpty();
    }

    @Test
    void getPermutations_noneFound_returnsEmptyList() {
        Set<String> actual = dic.getPermutations(
                "Code is clean if it can be understood easily – by everyone on the team. " +
                        "Clean code can be read and enhanced by a developer other than its original author. " +
                        "With understandability comes readability, changeability, extensibility and maintainability.");

        assertThat(actual).isEmpty();
    }

    @Test
    void getPermutations_unprintable_returnsEmptyList() {
        StringBuilder unprintable = IntStream.rangeClosed(0, 32)
                .mapToObj(i -> (char) i)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append);

        Set<String> actual = dic.getPermutations(unprintable.toString());

        assertThat(actual).isEmpty();
    }

    @Test
    void getWordsWithSameLetters_word_returnsAllWordsWithSameLetters() {
        Set<String> actual = dic.getWordsWithSameLetters("esi");

        assertThat(actual).containsExactly("Eies", "Eis", "eies", "eis", "sei", "sie");
    }

    @Test
    void getWordsWithSameLetters_noneFound_returnsEmptyList() {
        Set<String> actual = dic.getWordsWithSameLetters("Code is clean if it can be understood easily – by everyone on the team. " +
                "Clean code can be read and enhanced by a developer other than its original author. " +
                "With understandability comes readability, changeability, extensibility and maintainability.");

        assertThat(actual).isEmpty();
    }

    @Test
    void getWordsWithSameLetters_wordBlank_returnsEmptyList() {
        Set<String> actual = dic.getWordsWithSameLetters("");

        assertThat(actual).isEmpty();
    }
}