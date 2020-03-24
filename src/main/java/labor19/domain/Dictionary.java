package labor19.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Dictionary {
	private Map<Integer, Set<String>> wordsGroupedByLength;
	private Map<Integer, Set<String>> wordsGroupedByDistinctCharCount;
	int lines;
	
	public Dictionary (String filename) {
		try {
			lines = 0;
			wordsGroupedByLength = new HashMap<>();
			wordsGroupedByDistinctCharCount = new HashMap<>();
			
			Files.lines(Paths.get(filename)).skip(1).filter(s -> !s.contains(" ") && !s.contains("\t"))
					.forEach(s -> {
						lines++;
						s = s.split("/")[0];
						addLineToMaps(s, (int) s.toUpperCase().chars().distinct().count());
					});
		} catch (IOException e) {
			System.err.println(e.toString());
		}
	}
	
	public Set<String> getPermutations (String word) {
		int[] chars = word.toUpperCase().chars().toArray();
		
		Set<String> ret = new TreeSet<>(wordsGroupedByLength.get(word.length()));
		ret.removeIf(str -> !equalContent(chars, str.toUpperCase().chars().toArray()));
		
		return ret;
	}
	
	private boolean equalContent (int[] word, int[] check) {
		if (word.length != check.length)
			return false;
		
		for (int i = 0; i < word.length; i++) {
			System.out.println(getOccurrences(word[i], word));
			if (getOccurrences(word[i], word) != getOccurrences(word[i], check))
				return false;
		}
		return true;
	}
	
	private int getOccurrences (int i, int[] arr) {
		int count = 0;
		for (int j = 0; j < arr.length; j++) {
			if (arr[j] == i)
				count++;
		}
		return count;
	}
	
	public Set<String> getWordsWithSameLetters (String word) {
		int[] arrayDistinctChars = word.toUpperCase().chars().distinct().toArray();
		
		Set<String> ret = new TreeSet<>(wordsGroupedByDistinctCharCount.get((int) word.toUpperCase().chars().distinct().count()));
		ret.removeIf(str -> !equalLetters(arrayDistinctChars, str.toUpperCase().chars().distinct().toArray()));
		
		return ret;
	}
	
	private boolean equalLetters (int[] word, int[] check) {
		for (int i : check) {
			if (!Arrays.toString(word).contains(i + ""))
				return false;
		}
		for (int i : word) {
			if (!Arrays.toString(check).contains(i + ""))
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
		
		System.out.println(dictionary.lines);
		System.out.println(dictionary.getPermutations("esi"));
		System.out.println(dictionary.getWordsWithSameLetters("esi"));
	}
}
