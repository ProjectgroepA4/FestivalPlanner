package Applicatie;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PropertiesPanel extends JPanel {

	DrawObject selectedObject = null;
	Panel p = null;

	JTextField locationXField;
	JTextField locationYField;
	JTextField scaleField;
	JTextField rotationField;

	PropertiesPanel() {
		super();
		BoxLayout box = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(box);

		setPreferredSize(new Dimension(200, 0));
		setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
		setBackground(new Color(220, 220, 220));

		// NAME "PROPERTIES"
		JPanel propPanel = new JPanel();
		JLabel propLabel = new JLabel("Properties");
		propLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		propPanel.setBackground(new Color(220, 220, 220));
		propPanel.setMaximumSize(new Dimension(200, 60));
		propPanel.add(propLabel);
		add(propPanel);

		// LOCATION
		JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel locationLabel = new JLabel("Location");
		locationLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
		locationPanel.setPreferredSize(new Dimension(200, 150));
		locationPanel.setMaximumSize(new Dimension(200, 150));
		locationPanel.add(locationLabel);
		add(locationPanel);
		{
			JPanel locationXPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			locationXPanel.setPreferredSize(new Dimension(200, 50));
			locationPanel.add(locationXPanel);

			JLabel locationXLabel = new JLabel("X");
			locationXPanel.add(locationXLabel);
			locationXField = new JTextField();
			locationXField.setPreferredSize(new Dimension(150, 25));
			{
				locationXField.addKeyListener(new KeyListener() {

					public void keyTyped(KeyEvent e) {

						try {
							locationXField.setBackground(Color.WHITE);
							double xpos = Double.parseDouble(locationXField
									.getText());
							selectedObject.setPosition(new Point2D.Double(xpos,
									selectedObject.getPosition().getY()));
							p.update();
						} catch (NumberFormatException nfe) {
							locationXField.setBackground(Color.RED);
						}

					}

					public void keyReleased(KeyEvent e) {
					}

					public void keyPressed(KeyEvent e) {
					}
				});
			}
			locationXPanel.add(locationXField);
		}
		{
			JPanel locationYPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			locationYPanel.setPreferredSize(new Dimension(200, 50));
			locationPanel.add(locationYPanel);

			JLabel locationYLabel = new JLabel("Y");
			locationYPanel.add(locationYLabel);
			locationYField = new JTextField();
			locationYField.setPreferredSize(new Dimension(150, 25));
			{
				locationYField.addKeyListener(new KeyListener() {

					public void keyTyped(KeyEvent e) {
						try {
							locationYField.setBackground(Color.WHITE);
							double ypos = Double.parseDouble(locationYField
									.getText());
							selectedObject.setPosition(new Point2D.Double(
									selectedObject.getPosition().getX(), ypos));
							;
							p.update();
						} catch (NumberFormatException nfe) {

							locationYField.setBackground(Color.RED);

						}
					}

					public void keyReleased(KeyEvent e) {
					}

					public void keyPressed(KeyEvent e) {
					}
				});
			}
			locationYPanel.add(locationYField);
		}

		// SCALE
		JPanel scalePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel scaleLabel = new JLabel("Scale");
		scaleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
		scalePanel.setPreferredSize(new Dimension(200, 100));
		scalePanel.setMaximumSize(new Dimension(200, 100));
		scalePanel.add(scaleLabel);
		add(scalePanel);
		{
			JPanel scalePanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			scalePanel2.setPreferredSize(new Dimension(200, 50));
			scalePanel.add(scalePanel2);

			JLabel scale2Label = new JLabel(" ");
			scalePanel2.add(scale2Label);
			scaleField = new JTextField();
			scaleField.setPreferredSize(new Dimension(150, 25));
			{
				scaleField.addKeyListener(new KeyListener() {

					public void keyTyped(KeyEvent e) {

						try {
							scaleField.setBackground(Color.WHITE);
							double scale = Double.parseDouble(scaleField
									.getText());
							selectedObject.setScale(scale);
							p.update();
						} catch (NumberFormatException nfe) {
							scaleField.setBackground(Color.RED);
						}

					}

					public void keyReleased(KeyEvent e) {
					}

					public void keyPressed(KeyEvent e) {
					}
				});
			}
			scalePanel2.add(scaleField);
		}

		// ROTATION
		JPanel rotationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel rotationLabel = new JLabel("Rotation");
		rotationLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
		rotationPanel.setPreferredSize(new Dimension(200, 100));
		rotationPanel.setMaximumSize(new Dimension(200, 100));
		rotationPanel.add(rotationLabel);
		add(rotationPanel);
		{
			JPanel rotationPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			rotationPanel2.setPreferredSize(new Dimension(200, 50));
			rotationPanel.add(rotationPanel2);

			JLabel rotation2Label = new JLabel(" ");
			rotationPanel2.add(rotation2Label);
			rotationField = new JTextField();
			rotationField.setPreferredSize(new Dimension(150, 25));
			{
				rotationField.addKeyListener(new KeyListener() {

					public void keyTyped(KeyEvent e) {

						try {
							rotationField.setBackground(Color.WHITE);
							int rotation = Integer.parseInt(rotationField
									.getText()) % 360;
							rotationField.setText(rotation + "");
							selectedObject.setRotation(rotation);
							p.update();
						} catch (NumberFormatException nfe) {
							rotationField.setBackground(Color.RED);
						}

					}

					public void keyReleased(KeyEvent e) {
					}

					public void keyPressed(KeyEvent e) {
					}
				});
			}
			rotationPanel2.add(rotationField);
		}

		// SPACER
		JPanel spacerPanel = new JPanel();
		spacerPanel.setPreferredSize(new Dimension(200, 50));
		spacerPanel.setMaximumSize(new Dimension(200, 50));
		spacerPanel.setBackground(new Color(220, 220, 220));
		add(spacerPanel);

		// EXAMPLE
		JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel exampleLabel = new JLabel("Example");
		exampleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
		examplePanel.setPreferredSize(new Dimension(200, 100));
		examplePanel.setMaximumSize(new Dimension(200, 100));
		examplePanel.add(exampleLabel);
		add(examplePanel);

		enableComponents(this, false);
	}

	public void setSelected(DrawObject drOb) {
		selectedObject = drOb;
		fillFields();
		enableComponents(this, true);
	}

	public void clearSelected() {
		enableComponents(this, false);
		clearFields(this);
		selectedObject = null;
	}

	public void update() {
		fillFields();
	}

	private void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}

	private void clearFields(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				((JTextField) component).setText("");
			}
			if (component instanceof Container) {
				clearFields((Container) component);
			}
		}
	}

	private void fillFields() {
		if (selectedObject != null) {
			locationXField.setText(Math.round(selectedObject.getPosition()
					.getX()) + "");
			locationYField.setText(Math.round(selectedObject.getPosition()
					.getY()) + "");
			scaleField
					.setText((double) Math.round(selectedObject.getScale() * 10)
							/ 10 + "");
			rotationField
					.setText(Math.round(selectedObject.getRotation() % 360)
							+ "");
		}

	}

	public void setPanel(Panel p) {
		this.p = p;
	}
}
