/*
 *	===============================================================================
 *	MovingSquare.java : A shape that is a square.
 *	A square has 4 handles shown when it is selected (by clicking on it).
 *	===============================================================================
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class MovingSquare extends MovingRectangle {

	/**
	 * constuctor to create a square with default values
	 */
	public MovingSquare() {
		super();
	}

	/**
	 * constuctor to create a square shape
	 * 
	 * @param h
	 */
	public MovingSquare(int x, int y, int w, int h, int mw, int mh, int pathType) {
		super(x, y, w, h, mw, mh, pathType);
	}

	/**
	 * draw the square with the fill colour If it is selected, draw the handles
	 * 
	 * @param g
	 *            the Graphics control
	 */
	@Override
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create();
		float dash[] = { length };
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND, 20.0f, dash, 0.0f));
		g2.setColor(Color.blue);
		g2.fillRect(p.x, p.y, width, height);
		g.setColor(Color.black);
		g.drawRect(p.x, p.y, width, height);
		drawHandles(g);
	}

	/**
	 * Returns whether the point is in the rectangle or not
	 * 
	 * @return true if and only if the point is in the rectangle, false
	 *         otherwise.
	 */
	@Override
	public boolean contains(Point mousePt) {
		return (p.x <= mousePt.x && mousePt.x <= (p.x + width + 1)
				&& p.y <= mousePt.y && mousePt.y <= (p.y + height + 1));
	}
}