/*
 *  =============================================================
 *  A2.java : Extends JApplet and contains a panel where
 *  shapes move around on the screen. Also contains start and stop
 *  buttons that starts animation and stops animation respectively.
 *  ==============================================================
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class A2 extends JApplet {
	AnimationPanel panel; // panel for bouncing area
	JButton startButton, stopButton; // buttons to
										// start and
										// stop the
										// animation
	JButton strokeButton, c1Button, c2Button;
	JPanel toolPanel, drawPanel;

	Color sColor, c1, c2;

	BasicStroke[] strokes;

	Point mousePoint;

	Shape circleShape;

	/**
	 * main method for A1
	 */
	public static void main(String[] args) {
		A2 applet = new A2();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(applet, BorderLayout.CENTER);
		frame.setTitle("Bouncing Application, by edso681");
		applet.init();
		applet.start();
		frame.setSize(800, 500);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		frame.setLocation((d.width - frameSize.width) / 2,
				(d.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/**
	 * init method to initialise components
	 */
	@Override
	public void init() {
		panel = new AnimationPanel();
		add(panel, BorderLayout.CENTER);
		add(setUpToolsPanel(), BorderLayout.NORTH);
		add(setUpButtons(), BorderLayout.SOUTH);
		addComponentListener(new ComponentAdapter() { // resize the frame and
														// reset all margins for
														// all shapes
			@Override
			public void componentResized(ComponentEvent componentEvent) {
				panel.resetMarginSize();
			}
		});

		/*
		 * strokeButton.setText("Stroke Colour");
		 * strokeButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { Color
		 * tempColor = JColorChooser.showDialog(c1Button, "Pick Colour 1", c1);
		 * if (tempColor != null) { sColor = tempColor; } } });
		 * 
		 * c1Button.setText("Colour 1"); c1Button.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { Color
		 * tempColor = JColorChooser.showDialog(c1Button, "Pick Colour 1", c1);
		 * if (tempColor != null) { c1 = tempColor; }
		 * 
		 * } });
		 * 
		 * c2Button.setText("Colour 2"); c2Button.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { Color
		 * tempColor = JColorChooser.showDialog(c2Button, "Pick Colour 2", c2);
		 * if (tempColor != null) { c2 = tempColor; }
		 * 
		 * } });
		 * 
		 * toolPanel.add(strokeButton); toolPanel.add(c1Button);
		 * toolPanel.add(c2Button);
		 * 
		 * drawPanel.addMouseListener((MouseListener) this);
		 * 
		 * this.add(toolPanel, BorderLayout.NORTH); this.add(drawPanel,
		 * BorderLayout.CENTER);
		 */

	}

	/**
	 * Set up the tools panel
	 * 
	 * @return toolsPanel the Panel
	 */
	public JPanel setUpToolsPanel() {
		// Set up the shape combo box
		ImageIcon squareButtonIcon = createImageIcon("square.gif");
		ImageIcon rectangleButtonIcon = createImageIcon("rectangle.gif");
		ImageIcon rightAngleButtonIcon = createImageIcon("rightAngle.gif");
		JComboBox<ImageIcon> shapesComboBox = new JComboBox<ImageIcon>(
				new ImageIcon[] { rectangleButtonIcon, squareButtonIcon,
						rightAngleButtonIcon, });
		shapesComboBox.setToolTipText("Set shape");
		shapesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				// set the Current shape type based on the selection: 0 for
				// Circle, 1 for Rectangle etc
				panel.setCurrentShapeType(cb.getSelectedIndex());
			}
		});
		// Set up the path combo box
		ImageIcon boundaryButtonIcon = createImageIcon("boundary.gif");
		ImageIcon fallingButtonIcon = createImageIcon("falling.gif");
		JComboBox<ImageIcon> pathComboBox = new JComboBox<ImageIcon>(
				new ImageIcon[] { boundaryButtonIcon, fallingButtonIcon });
		pathComboBox.setToolTipText("Set Path");
		pathComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				// set the Current path type based on the selection from combo
				// box: 0 for Boundary Path, 1 for bouncing Path
				panel.setCurrentPathType(cb.getSelectedIndex());
			}
		});
		// Set up the height TextField
		JTextField heightTxt = new JTextField("20");
		heightTxt.setToolTipText("Set Height");
		heightTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0) // if the value is valid, then change the
										// current height
						panel.setCurrentHeight(newValue);
					else
						tf.setText(panel.getCurrentHeight() + "");
				} catch (Exception ex) {
					System.err.println("Exception: " + ex.getMessage());

					tf.setText(panel.getCurrentHeight() + ""); // if the number
																// entered is
																// invalid,
																// reset it
				}
			}
		});
		// Set up the width TextField
		JTextField widthTxt = new JTextField("50");
		widthTxt.setToolTipText("Set Width");
		widthTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
						panel.setCurrentWidth(newValue);
					else
						tf.setText(panel.getCurrentWidth() + "");
				} catch (Exception ex) {
					System.err.println("Exception: " + ex.getMessage());

					tf.setText(panel.getCurrentWidth() + "");
				}
			}
		});
		// added
		JTextField dashedLineLength = new JTextField("3.0f");
		dashedLineLength.setToolTipText("Set dished line Length");
		dashedLineLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();

				try {
					float newValue = Float.parseFloat(tf.getText());
					if (newValue > 0)
						panel.setLength(newValue);
					else
						tf.setText(panel.getLength() + "");
				} catch (Exception ex) {

					System.err.println("Exception: " + ex.getMessage());

					tf.setText(panel.getLength() + "");
				}

			}
		});
		// added
		JTextField seperation = new JTextField("3.0f");
		seperation.setToolTipText("Set Seperation");
		seperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField) e.getSource();
				try {
					float newValue = Float.parseFloat(tf.getText());
					if (newValue > 0)
						panel.setSeperation(newValue);
					else
						tf.setText(panel.getSeperation() + "");
				} catch (Exception ex) {
					System.err.println("Exception: " + ex.getMessage());
					tf.setText(panel.getSeperation() + "");
				}
			}
		});
		// added
		/*
		 * JTextField radius = new JTextField("3.0f");
		 * radius.setToolTipText("Set Seperation"); radius.addActionListener(new
		 * ActionListener() { public void actionPerformed(ActionEvent e) {
		 * JTextField tf = (JTextField) e.getSource(); try { int newValue =
		 * Integer.parseInt(tf.getText()); if (newValue > 0)
		 * panel.setSeperation(newValue); else tf.setText(panel.getSeperation()
		 * + ""); } catch (Exception ex) { System.err.println("Exception: " +
		 * ex.getMessage());
		 * 
		 * tf.setText(panel.getSeperation() + ""); } } });
		 */

		JPanel toolsPanel = new JPanel();
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
		toolsPanel.add(shapesComboBox);
		toolsPanel.add(new JLabel(" Path: ", JLabel.RIGHT));
		toolsPanel.add(pathComboBox);
		toolsPanel.add(new JLabel(" Height: ", JLabel.RIGHT));
		toolsPanel.add(heightTxt);
		toolsPanel.add(new JLabel(" Width: ", JLabel.RIGHT));
		toolsPanel.add(widthTxt);
		//
		toolsPanel.add(new JLabel(" Length ", JLabel.RIGHT));
		toolsPanel.add(dashedLineLength);
		//
		toolsPanel.add(new JLabel(" Seperation ", JLabel.RIGHT));
		toolsPanel.add(seperation);

		return toolsPanel;
	}

	/**
	 * Set up the buttons panel
	 * 
	 * @return buttonPanel the Panel
	 */
	public JPanel setUpButtons() {
		JPanel buttonPanel = new JPanel(new FlowLayout());

		// Set up the start button
		panel.setCurrentLeftColor(new JButton("leftButton"));
		panel.getCurrentLeftColor().setToolTipText("leftButton Animation");
		panel.getCurrentLeftColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(panel, "Fill Color",
						Color.blue);
				panel.setBackground(newColor);
				panel.getCurrentLeftColor().setForeground(newColor);

				panel.start(); // start the animation
			}
		});
		// Set up the start button
		panel.setCurrentRightColor(new JButton("RightButton"));
		panel.getCurrentRightColor().setToolTipText("rightButton Animation");
		panel.getCurrentRightColor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(panel, "Fill Color",
						Color.blue);
				panel.setBackground(newColor);

				panel.getCurrentRightColor().setForeground(newColor);
				panel.start(); // start the animation
			}
		});

		// Set up the start button
		startButton = new JButton("Start");
		startButton.setToolTipText("Start Animation");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				panel.start(); // start the animation
			}
		});
		// Set up the stop button
		stopButton = new JButton("Stop");
		stopButton.setToolTipText("Stop Animation");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				startButton.setEnabled(true); // stop the animation
				panel.stop();
			}
		});

		// Slider to adjust the speed of the animation
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 30);
		slider.setToolTipText("Adjust Speed");
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int value = (source.getValue()); // get the value from
														// slider
					TitledBorder tb = (TitledBorder) source.getBorder();
					tb.setTitle("Anim delay = " + String.valueOf(value) + " ms");
					/*
					 * // adjust // the // tilted // border // to // indicate //
					 * the // speed // of // the // animation
					 */panel.adjustSpeed(value); // set the speed
					source.repaint();
				}
			}
		});
		TitledBorder title = BorderFactory
				.createTitledBorder("Anim delay = 30 ms");
		slider.setBorder(title);
		// Add buttons and slider control

		panel.getCurrentLeftColor().setForeground(Color.orange);
		panel.getCurrentRightColor().setForeground(Color.orange);
		// buttonPanel.add(colorButton);
		buttonPanel.add(panel.getCurrentLeftColor());
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(slider);

		buttonPanel.add(panel.getCurrentRightColor());
		return buttonPanel;
	}

	/**
	 * create the imageIcon
	 * 
	 * @param filename
	 *            the filename of the image
	 * @return ImageIcon the imageIcon
	 */
	protected static ImageIcon createImageIcon(String filename) {
		java.net.URL imgURL = A2.class.getResource(filename);
		return new ImageIcon(imgURL);
	}
}
