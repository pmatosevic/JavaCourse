package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

/**
 * Program that demonstrates coloring.
 * 
 * @author Patrik
 *
 */
public class Bojanje2 {

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs, dfs, bfsv)); // ili FillApp.ROSE
	}
	
	/**
	 * BFS fill algorithm
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
//			if (picture.getPixelColor(x, y) == color) {
//				return;
//			}
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}
	};
	
	/**
	 * DFS fill algorithm
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
//			if (picture.getPixelColor(x, y) == color) {
//				return;
//			}
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}
	};
	
	/**
	 * BFS (with visited set) fill algorithm
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs bolji!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
//			if (picture.getPixelColor(x, y) == color) {
//				return;
//			}
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);
		}
	};
	
}
