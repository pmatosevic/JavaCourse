package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Program that demonstates solving puzzle of numbers.
 * Takes a list of numbers as a command-line parameter and solves it.
 * 
 * @author Patrik
 *
 */
public class SlagalicaMain {

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Configuration is needed.");
			return;
		}
		if (args[0].length() != 9) {
			System.out.println("Invalid configuration.");
			return;
		}
		
		Set<Integer> used = new HashSet<>();
		int[] conf = new int[9];
		for (int i = 0; i < 9; i++) {
			int num = args[0].charAt(i) - '0';
			if (!Character.isDigit(args[0].charAt(i)) || !used.add(num)) {
				System.out.println("Invalid configuration.");
				return;
			}
			conf[i] = num;
		}
		
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(conf));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
	}
	
}
