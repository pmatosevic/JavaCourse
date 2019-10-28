package hr.fer.zemris.java.hw17.trazilica.model;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class with static utility methods that can be used to create new {@link DocumentCollection}.
 * 
 * @author Patrik
 *
 */
public class DocumentCollectionBuilder {

	/**
	 * Creates a new {@link DocumentCollection} by loading and adding files from given directory.
	 * 
	 * @param stopwordsPath path to the file with stop words
	 * @param docDir path to the directory of document
	 * @return collection of documents
	 * @throws IOException in case of an error
	 */
	public static DocumentCollection loadIntoDocumentCollection(Path stopwordsPath, Path docDir) throws IOException {
		Set<String> stopwords = Files.lines(stopwordsPath).collect(Collectors.toSet());
		Map<String, Integer> vocabularyFreq = new HashMap<>();
		Map<String, Integer> vocabularyMapping = new HashMap<>();
		int[] counter = { 0 };
		int[] docCounter = { 0 };
		
		Files.walkFileTree(docDir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Set<String> words = new HashSet<>(parseWords(Files.readString(file)));
				docCounter[0]++;
				
				for (String word : words) {
					if (stopwords.contains(word)) continue;
					
					vocabularyFreq.merge(word, 1, Integer::sum);
					if (!vocabularyMapping.containsKey(word)) {
						vocabularyMapping.put(word, counter[0]);
						counter[0]++;
					}
				}
				
				return FileVisitResult.CONTINUE;
			}
		});
		
		Vector idfVector = new Vector(vocabularyFreq.size());
		for (Map.Entry<String, Integer> ent : vocabularyFreq.entrySet()) {
			idfVector.setComponent(vocabularyMapping.get(ent.getKey()), Math.log((double) docCounter[0] / ent.getValue()));
		}
		
		DocumentCollection docCollection = new DocumentCollection(vocabularyMapping, idfVector);
		
		Files.walkFileTree(docDir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				docCollection.addDocument(docCollection.createDocument(file));
				return FileVisitResult.CONTINUE;
			}
		});
		
		return docCollection;
	}
	
	/**
	 * Parses the given string and returns the list of words.
	 * @param text string
	 * @return the list of all words
	 */
	public static List<String> parseWords(String text) {
		int pos = 0;
		char[] data = text.toCharArray();
		List<String> words = new ArrayList<>();
		
		while (true) {
			while (pos < data.length && !Character.isAlphabetic(data[pos])) {
				pos++;
			}
			
			int start = pos;
			while (pos < data.length && Character.isAlphabetic(data[pos])) {
				pos++;
			}
			if (start == pos) break;
			words.add(new String(data, start, pos - start).toLowerCase());
		}
		
		return words;
	}
	
}
