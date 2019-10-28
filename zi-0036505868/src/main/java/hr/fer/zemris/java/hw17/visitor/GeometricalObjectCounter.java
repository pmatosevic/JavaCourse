package hr.fer.zemris.java.hw17.visitor;

import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.Line;
import hr.fer.zemris.java.hw17.objects.Triangle;

public class GeometricalObjectCounter implements GeometricalObjectVisitor {

	public int lineCnt = 0;
	public int circleCnt = 0;
	public int fillCircleCnt = 0;
	public int triangleCnt = 0;
	
	@Override
	public void visit(Line line) {
		lineCnt++;
	}

	@Override
	public void visit(Circle circle) {
		circleCnt++;
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		fillCircleCnt++;
	}

	@Override
	public void visit(Triangle triangle) {
		triangleCnt++;
	}

	/**
	 * @return the lineCnt
	 */
	public int getLineCnt() {
		return lineCnt;
	}

	/**
	 * @return the circleCnt
	 */
	public int getCircleCnt() {
		return circleCnt;
	}

	/**
	 * @return the fillCircleCnt
	 */
	public int getFillCircleCnt() {
		return fillCircleCnt;
	}

	/**
	 * @return the triangleCnt
	 */
	public int getTriangleCnt() {
		return triangleCnt;
	}
	
	

}
