package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker that calculates the sum of numbers a and b from parameters and shows that in a html table.
 * @author Patrik
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a;
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException | NullPointerException e) {
			a = 1;
		}
		int b;
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException | NullPointerException e) {
			b = 2;
		}
		
		int sum = a + b;
		
		context.setTemporaryParameter("varA", "" + a);
		context.setTemporaryParameter("varB", "" + b);
		context.setTemporaryParameter("zbroj", "" + sum);
		context.setTemporaryParameter("imgName", sum%2==0 ? "image1.jpg" : "image2.jpg");
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
