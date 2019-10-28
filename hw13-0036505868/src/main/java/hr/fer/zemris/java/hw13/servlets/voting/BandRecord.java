package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a band with name and voting results.
 * 
 * @author Patrik
 *
 */
public class BandRecord {

	/**
	 * Path to the file with band definitions
	 */
	public static final String BANDS_FILENAME = "/WEB-INF/glasanje-definicija.txt";
	
	/**
	 * Path to the file with the results
	 */
	public static final String RESULTS_FILENAME = "/WEB-INF/glasanje-rezultati.txt";
	
	/**
	 * The ID
	 */
	private int id;
	
	/**
	 * The band name
	 */
	private String name;
	
	/**
	 * URL of a song
	 */
	private String exampleURL;
	
	/**
	 * Number of votes
	 */
	private int votes;

	/**
	 * Creates a new band record
	 * @param id id
	 * @param name band name
	 * @param exampleURL URL of a song
	 */
	public BandRecord(int id, String name, String exampleURL) {
		this.id = id;
		this.name = name;
		this.exampleURL = exampleURL;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * @param votes the votes to set
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	/**
	 * @return the exampleURL
	 */
	public String getExampleURL() {
		return exampleURL;
	}
	
	
	
	/**
	 * Loads the band from the file.
	 * 
	 * @param filename path to the file
	 * @return list of bands
	 * @throws IOException in case of an IO error
	 */
	public static List<BandRecord> loadBands(String filename) throws IOException {
		Path path = Paths.get(filename);
		List<BandRecord> result = new ArrayList<>();
		
		List<String> bandLines = Files.readAllLines(path);
		for (String line : bandLines) {
			String[] parts = line.split("\t");
			int id = Integer.parseInt(parts[0]); 
			result.add(new BandRecord(id, parts[1], parts[2]));
		}
		
		return result;
	}
	
	/**
	 * Loads the votes.
	 * @param filename path to the results file
	 * @return a map of ids and number of votes
	 * @throws IOException in case of an IO error
	 */
	public static Map<Integer, Integer> loadVotes(String filename) throws IOException {
		Path path = Paths.get(filename);
		if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
			return new HashMap<>();
		}
		
		List<String> lines = Files.readAllLines(path);
		Map<Integer, Integer> map = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split("\t");
			map.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		}
		return map;
	}
	
	/**
	 * Loads the bands with the votes
	 * @param fileBands path to the bands file
	 * @param fileResults path to the results file
	 * @return the list of bands
	 * @throws IOException in case of an IO error
	 */
	public static List<BandRecord> loadBandsWithVotes(String fileBands, String fileResults) throws IOException {
		List<BandRecord> bands = loadBands(fileBands);
		Map<Integer, Integer> votes = loadVotes(fileResults);
		for (BandRecord band : bands) {
			Integer cnt = votes.get(band.getId());
			if (cnt != null) {
				band.setVotes(cnt);
			}
		}
		return bands;
	}
	
	/**
	 * Saves the map of votes to a file.
	 * 
	 * @param votes map of ids and number of votes
	 * @param filename path to the file
	 * @throws IOException in case of an IO error
	 */
	public static void saveVotes(Map<Integer, Integer> votes, String filename) throws IOException {
		Path path = Paths.get(filename);
		try (BufferedWriter wr = Files.newBufferedWriter(path)) {
			for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
				wr.write("" + entry.getKey() + "\t" + entry.getValue());
				wr.newLine();
			}
		}
	}
	
	
}
