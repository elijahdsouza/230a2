/*
 *	===============================================================================
 *	MovingRectangle.java : A shape that is a rectangle.
 *	A rectangle has 4 handles shown when it is selected (by clicking on it).
 *	===============================================================================
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class MovingRectangle extends MovingShape {
	/**
	 * constuctor to create a rectangle with default values
	 */
	public MovingRectangle() {
		super();
	}

	/**
	 * constuctor to create a rectangle shape
	 */
	public MovingRectangle(int x, int y, int w, int h, int mw, int mh,
			int pathType) {
		super(x, y, w, h, mw, mh, pathType);
	}

	/**
	 * draw the rectangle with the fill colour If it is selected, draw the
	 * handles
	 * 
	 * @param g
	 *            the Graphics control
	 */

	@Override
	// protected void paint(Graphics g){
	// Graphics2D g2 = (Graphics2D)g.create();
	//
	// Paint p2 = new GradientPaint(
	// 0, 0, new Color(0x7D838F),
	// getWidth(), 0, new Color(0x272B39)
	// );
	//
	// g2.setPaint(p2);
	// g2.fillRect(0, 0, getWidth(), getHeight());
	//
	// g2.dispose();
	// }
	public void draw(Graphics g) {
		// g.setColor(Color.blue);
		// g.fillRect(p.x, p.y, width, height);
		// g.drawRect(p.x, p.y, width, height);
		// BasicStrokes({"lineWidth","endCap","lineJoin","miterLimit","dashArray","dashPhase"})

		// new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
		// BasicStroke.JOIN_ROUND, 80.0f, dash[length], 0.0f)

		Graphics2D g2 = (Graphics2D) g.create();
		float dash[] = { length };
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_ROUND, 20.0f, dash, 0.0f));
		Rectangle rect = new Rectangle(p.x, p.y, width, height);
		// g2.setColor(Color.blue);
		GradientPaint gp = new GradientPaint(rect.getBounds().x,
				rect.getBounds().y, Color.blue,
				(rect.getBounds().x + rect.getBounds().width),
				(rect.getBounds().x + rect.getBounds().height), Color.green);//

		g2.setPaint(gp);//
		g2.fillRect(p.x, p.y, width, height);

		g2.setColor(Color.black);
		g2.drawRect(p.x, p.y, width, height);

		g2.dispose();//

		// g2.draw(r);
		drawHandles(g);
	}

	// public GradientsOutlines() {
	// super();
	// toolPanel = new JPanel();
	// drawPanel = new JPanel();
	// toolPanel.setBackground(Color.BLUE);
	// strokeButton = new JButton();
	// c1Button = new JButton();
	// c2Button = new JButton();
	// c1 = c2 = Color.RED;
	// sColor = Color.BLACK;
	//
	// mousePoint = new Point(0, 0);
	// circleShape = new Ellipse2D.Double(0, 0, 0, 0);
	//
	// setUpStrokes();
	// }

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