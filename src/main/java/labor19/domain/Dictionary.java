package labor19.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Dictionary {
	private Map<Integer, Set<String>> wordsGroupedByLength;
	private Map<Integer, Set<String>> wordsGroupedByDistinctCharCount;
	
	public Dictionary (String filename) {
		try {
			wordsGroupedByLength = new HashMap<>();
			wordsGroupedByDistinctCharCount = new HashMap<>();
			
			Files.lines(Paths.get(filename)).skip(1).filter(s -> !s.contains(" ") && !s.contains("\t"))
					.forEach(s -> {
						s = s.split("/")[0];
						if (s.contains("ungs") && s.indexOf("ungs") == s.length() - 4)
							s = s.replace("ungs", "ung");
						addLineToMaps(s, (int) s.toUpperCase().chars().distinct().count());
					});
		} catch (IOException e) {
			System.err.println(e.toString());
		}
	}
	
	public Set<String> getPermutations (String word) {
		if (word.isBlank() || !wordsGroupedByLength.containsKey(word.length()))
			return new HashSet<>();
		
		Set<String> permutations = new TreeSet<>(wordsGroupedByLength.get(word.length()));
		permutations.removeIf(str -> !sameLettersWatchCount(word.toUpperCase().chars().toArray(), str.toUpperCase().chars().toArray()));
		
		return permutations;
	}
	
	private boolean sameLettersWatchCount (int[] word, int[] check) {
		if (word.length != check.length)
			return false;
		
		for (int ascii : word) {
			if (getOccurrences(ascii, word) != getOccurrences(ascii, check))
				return false;
		}
		return true;
	}
	
	private int getOccurrences (int occurrence, int[] check) {
		int count = 0;
		for (int ascii : check) {
			if (ascii == occurrence)
				count++;
		}
		return count;
	}
	
	public Set<String> getWordsWithSameLetters (String word) {
		if (word.isBlank() || !wordsGroupedByDistinctCharCount.containsKey((int) word.toUpperCase().chars().distinct().count()))
			return new HashSet<>();
		
		int[] arrayDistinctChars = word.toUpperCase().chars().distinct().toArray();
		
		Set<String> ret = new TreeSet<>(wordsGroupedByDistinctCharCount.get((int) word.toUpperCase().chars().distinct().count()));
		ret.removeIf(str -> !sameLettersIgnoreCount(arrayDistinctChars, str.toUpperCase().chars().distinct().toArray()));
		
		return ret;
	}
	
	private boolean sameLettersIgnoreCount (int[] word, int[] check) {
		for (int ch : check) {
			if (!Arrays.toString(word).contains(ch + ""))
				return false;
		}
		for (int ch : word) {
			if (!Arrays.toString(check).contains(ch + ""))
				return false;
		}
		return true;
	}
	
	private void addLineToMaps (String line, int charCount) {
		if (!wordsGroupedByLength.containsKey(line.length())) {
			Set<String> strings = new HashSet<>();
			strings.add(line);
			wordsGroupedByLength.put(line.length(), strings);
		} else
			wordsGroupedByLength.get(line.length()).add(line);
		
		if (!wordsGroupedByDistinctCharCount.containsKey(charCount)) {
			Set<String> strings = new HashSet<>();
			strings.add(line);
			wordsGroupedByDistinctCharCount.put(charCount, strings);
		} else
			wordsGroupedByDistinctCharCount.get(charCount).add(line);
	}
	
	public static void main (String[] args) {
		Dictionary dictionary = new Dictionary("src/main/resources/assets/de-DE1.dic");
		
		System.out.println(dictionary.getPermutations("esi"));
		System.out.println(dictionary.getWordsWithSameLetters("esi"));
		System.out.println(dictionary.wordsGroupedByLength.get(8).contains("Änderung"));
	}
}
