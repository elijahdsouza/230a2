/*
 *	======================================================================
 *	AnimationPanel.java : Moves shapes around on the screen according to different paths.
 *	It is the main drawing area where shapes are added and manipulated.
 *	It also contains a popup menu to clear all shapes.
 *	======================================================================
 */

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class AnimationPanel extends JComponent implements Runnable {
	private JButton currentLeftColor, currentRightColor;
	private Thread animationThread = null; // the thread for animation
	private final ArrayList<MovingShape> shapes; // the arraylist to store all

	// shapes
	private int currentShapeType, // the current shape type
			currentPath, // the current path type
			currentWidth = 50, // the current width of a shape
			currentHeight = 20; // the current height of a shape
	private float currentSeperation = 3.0f, currentLength = 3.0f;
	// JButton = leftColorButton, rightButton;

	private int delay = 30; // the current animation speed
	JPopupMenu popup; // popup menu

	/**
	 * Constructor of the AnimationPanel
	 */
	public AnimationPanel() {
		shapes = new ArrayList<MovingShape>(); // create the vector to store
												// shapes
		popup = new JPopupMenu(); // create the popup menu
		makePopupMenu();

		// add the mouse event to handle popup menu and create new shape
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (animationThread != null) { // if the animation has started,
												// then
					boolean found = false;
					MovingShape currentShape = null;
					for (int i = 0; i < shapes.size(); i++) {
						currentShape = shapes.get(i);

						/*
						 * // if the mousepoint is within a shape, then set the
						 * shape to be selected/deselected
						 */
						if (currentShape.contains(e.getPoint())) {

							found = true;
							currentShape
									.setSelected(!currentShape.isSelected());
							System.out.println(currentShape);
						}
					}
					if (!found)
						createNewShape(e.getX(), e.getY()); // if the mousepoint
															// is not within a
															// shape, then
															// create a new one
															// according to the
															// mouse position
				}
			}
		});
	}

	/**
	 * create a new shape
	 * 
	 * @param x
	 *            the x-coordinate of the mouse position
	 * @param y
	 *            the y-coordinate of the mouse position
	 */
	protected void createNewShape(int x, int y) {
		// get the margin of the frame
		Insets insets = getInsets();
		int marginWidth = getWidth() - insets.left - insets.right;
		int marginHeight = getHeight() - insets.top - insets.bottom;

		float seperation = getSeperation();
		float length = getLength();
		// create a new shape dependent on all current properties and the mouse
		// position
		switch (currentShapeType) {
		case 0: { // rectnage

			MovingShape movingRectangle = new MovingRectangle(x, y,
					currentWidth, currentHeight, marginWidth, marginHeight,
					currentPath);

			movingRectangle.setSeperation(seperation);
			movingRectangle.setLength(length);
			shapes.add(movingRectangle);
			break;
		}
		case 1: { // square
			int currentSize = Math.min(currentWidth, currentHeight);
			MovingShape movingSquare = new MovingSquare(x, y, currentSize,
					currentSize, marginWidth, marginHeight, currentPath);
			movingSquare.setLength(length);
			movingSquare.setSeperation(seperation);
			shapes.add(movingSquare);
			break;
		}
		case 2: { // triangle
			MovingShape movingRightAngleTriangle = new MovingRightAngleTriangle(
					x, y, currentWidth, currentHeight, marginWidth,
					marginHeight, currentPath);

			movingRightAngleTriangle.setLength(length);
			movingRightAngleTriangle.setSeperation(seperation);
			shapes.add(movingRightAngleTriangle);
			break;
		}
		}
	}

	float getLength() {
		// TODO Auto-generated method stub
		return currentLength;
	}

	float getSeperation() {
		// TODO Auto-generated method stub
		return currentSeperation;
	}

	/**
	 * set the current shape type
	 * 
	 * @param s
	 *            the new shape type
	 */
	public void setCurrentShapeType(int s) {
		currentShapeType = s;
	}

	/**
	 * set the current path type and the path type for all currently selected
	 * shapes
	 * 
	 * @param t
	 *            the new path type
	 */
	public void setCurrentPathType(int t) {
		currentPath = t;
		MovingShape currentShape = null;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setPath(currentPath);
			}
		}
	}

	/**
	 * set the current width and the width for all currently selected shapes
	 * 
	 * @param w
	 *            the new width value
	 */
	public void setCurrentWidth(int w) {
		// why not: currentWidth = w; ?
		MovingShape currentShape = null;
		currentWidth = w;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setWidth(currentWidth);
			}
		}
	}

	/**
	 * set the current width and the width for all currently selected shapes
	 * 
	 * @param seperation
	 * 
	 *            the new separation
	 */
	public void setSeperation(float seperation) {
		MovingShape currentShape = null;
		currentSeperation = seperation;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setSeperation(seperation);
			}
		}
	}

	/**
	 * set the current length and the width for all currently selected shapes
	 * 
	 * @param length
	 * 
	 *            the new length
	 */
	public void setLength(float length) {
		MovingShape currentShape = null;
		currentLength = length;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setLength(currentLength);
			}
		}
	}

	/**
	 * set the current height and the height for all currently selected shapes
	 * 
	 * @param h
	 *            the new height value
	 */
	public void setCurrentHeight(int h) {
		MovingShape currentShape = null;
		this.currentHeight = h;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setHeight(currentHeight);
			}
		}
	}

	/**
	 * @return the currentSeperation
	 */
	public float getCurrentSeperation() {
		return currentSeperation;
	}

	/**
	 * @param currentSeperation
	 *            the currentSeperation to set
	 */
	public void setCurrentSeperation(float currentSeperation) {
		MovingShape currentShape = null;
		this.currentSeperation = currentSeperation;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setSeperation(currentSeperation);
			}
		}
	}

	/**
	 * @return the currentLength
	 */
	public float getCurrentLength() {
		return currentLength;
	}

	/**
	 * @param currentLength
	 *            the currentLength to set
	 */
	public void setCurrentLength(int currentLength) {
		this.currentLength = currentLength;
	}

	/**
	 * get the current width
	 * 
	 * @return currentWidth - the width value
	 */
	public int getCurrentWidth() {
		return currentWidth;
	}

	/**
	 * get the current height
	 * 
	 * @return currentHeight - the height value
	 */
	public int getCurrentHeight() {
		return currentHeight;
	}

	/**
	 * remove all shapes from our vector
	 */
	public void clearAllShapes() {
		shapes.clear();
	}

	// you don't need to make any changes after this line ______________

	/**
	 * create the popup menu for our animation program
	 */
	protected void makePopupMenu() {
		JMenuItem menuItem;
		// clear all
		menuItem = new JMenuItem("Clear All");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllShapes();
			}
		});
		popup.add(menuItem);
	}

	/**
	 * reset the margin size of all shapes from our vector
	 */
	public void resetMarginSize() {
		Insets insets = getInsets();
		int marginWidth = getWidth() - insets.left - insets.right;
		int marginHeight = getHeight() - insets.top - insets.bottom;
		for (int i = 0; i < shapes.size(); i++)
			(shapes.get(i)).setMarginSize(marginWidth, marginHeight);
	}

	/**
	 * update the painting area
	 * 
	 * @param g
	 *            the graphics control
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * move and paint all shapes within the animation area
	 * 
	 * @param g
	 *            the Graphics control
	 */
	@Override
	public void paintComponent(Graphics g) {
		MovingShape currentShape;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			currentShape.move();
			currentShape.draw(g);
		}
	}

	/**
	 * change the speed of the animation
	 * 
	 * @param newValue
	 *            the speed of the animation in ms
	 */
	public void adjustSpeed(int newValue) {
		if (animationThread != null) {
			stop();
			delay = newValue;
			start();
		}
	}

	/**
	 * When the "start" button is pressed, start the thread
	 */
	public void start() {
		animationThread = new Thread(this);
		animationThread.start();
	}

	/**
	 * When the "stop" button is pressed, stop the thread
	 */
	public void stop() {
		if (animationThread != null) {
			animationThread = null;
		}
	}

	/**
	 * run the animation
	 */
	public void run() {
		Thread myThread = Thread.currentThread();
		while (animationThread == myThread) {
			repaint();
			pause(delay);
		}
	}

	/**
	 * Sleep for the specified amount of time
	 */
	private void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ie) {
		}
	}

	/**
	 * @return the leftColorButton
	 */
	public JButton getCurrentLeftColor() {
		return currentLeftColor;
	}

	/**
	 * @param leftColorButton
	 *            the leftColorButton to set
	 */
	public void setCurrentLeftColor(JButton leftColorButton) {
		this.currentLeftColor = leftColorButton;
	}

	/**
	 * @return the rightButton
	 */
	public JButton getCurrentRightColor() {
		return currentRightColor;
	}

	/**
	 * @param rightButton
	 *            the rightButton to set
	 */
	public void setCurrentRightColor(JButton rightButton) {
		this.currentRightColor = rightButton;
	}

}
