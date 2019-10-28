package hr.fer.zemris.java.hw16.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An implementation of {@link ImageInfoDB} that loads the information from a description file.
 * 
 * @author Patrik
 *
 */
public class DefaultImageInfoDB implements ImageInfoDB {

	/**
	 * Map of all image informations by the filename
	 */
	private Map<String, ImageInfo> images = new HashMap<>();
	
	/**
	 * Creates a new object
	 * @param descFilename path to the description file
	 */
	public DefaultImageInfoDB(String descFilename) {
		Path path = Paths.get(descFilename);
		
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			while (true) {
				String filename = reader.readLine();
				if (filename == null) break;
				
				String title = reader.readLine();
				
				String[] tagsParts = reader.readLine().split(",");
				List<String> tags = new ArrayList<String>();
				for (String tag : tagsParts) {
					tags.add(tag.trim());
				}
				
				images.put(filename, new ImageInfo(filename, title, tags));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<String> getTags() {
		return images.values().stream().flatMap(img -> img.getTags().stream()).distinct()
				.collect(Collectors.toList());
	}
	
	@Override
	public List<String> getImagesByTag(String tag) {
		return images.values().stream().filter(img -> img.getTags().contains(tag)).map(img -> img.getFilename())
				.collect(Collectors.toList());
	}
	
	@Override
	public ImageInfo getImageByFilename(String filename) {
		return images.get(filename);
	}
	
	
}
