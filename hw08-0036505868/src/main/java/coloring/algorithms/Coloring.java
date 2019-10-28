package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Represents a coloring problem that provides necessary data for the solving algorithms.
 * 
 * @author Patrik
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel>  {

	/**
	 * Array of allowed moves
	 */
	private static final int[] DX = {1, -1, 0, 0};
	
	/**
	 * Array of allowed moves
	 */
	private static final int[] DY = {0, 0, 1, -1};
	
	/**
	 * Starting pixel
	 */
	private Pixel reference;
	
	/**
	 * The picture
	 */
	private Picture picture;
	
	/**
	 * The fill color
	 */
	private int fillColor;
	
	/**
	 * The reference color
	 */
	private int refColor;
	/**
	 * @param reference
	 * @param picture
	 * @param fillColor
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.getX(), reference.getY());
	}
	
	@Override
	public Pixel get() {
		return reference;
	}
	
	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.getX(), t.getY()) == refColor;
	}
	
	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> l = new ArrayList<>();
		for (int i=0; i<4; i++) {
			Pixel p = new Pixel(t.getX() + DX[i], t.getY() + DY[i]);
			if (p.getX() >= 0 && p.getX() < picture.getWidth() 
					&& p.getY() >= 0 && p.getY() < picture.getHeight()) {
				l.add(p);
			}
		}
		return l;
	}
	
	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.getX(), t.getY(), fillColor);
	}
	
	
	
}
