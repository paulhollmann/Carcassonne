package fop.view.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import fop.model.interfaces.GameConstants;
import fop.view.components.gui.Resources;
import fop.view.gui.GameWindow;

/**
 * this abstract class defines our specific containers
 */
public abstract class View extends Container implements ActionListener, GameConstants {

	private GameWindow gameWindow;

	public View(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		this.onInit();
		this.setSize(gameWindow.getSize());
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent componentEvent) {
				onResize();
			}
		});
	}

	public abstract void onResize();

	protected abstract void onInit();

	/**
	 * returns the font with "Times New Roman", Font.PLAIN and given Size
	 * 
	 * @param Size
	 * @return
	 */
	public static Font createFont(int Size) {
		return new Font("Times New Roman", Font.PLAIN, Size);
	}

	/**
	 * creates a Celtic Font with given size an returns it
	 * 
	 * @param Size
	 * @return
	 */
	public static Font createCelticFont(float Size) {
		Resources resources = Resources.getInstance();
		return resources.getCelticFont().deriveFont(Size);
	}

	/**
	 * returns the current GameWindow
	 * 
	 * @return
	 */
	protected GameWindow getWindow() {
		return this.gameWindow;
	}

	/**
	 * creates a button with the constants form GameConstants and the given text
	 * 
	 * @param text
	 * @return
	 */
	protected JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setSize(BUTTON_SIZE);
		button.addActionListener(this);
		button.setForeground(BUTTON_FOREGROUND_Color);
		button.setBackground(BUTTON_BACKGROUND_Color);
		button.setFont(gameWindow.getResources().getCelticFont());
		this.add(button);
		return button;
	}
	/**
	 * calculates the text size and returns it 
	 * @param text
	 * @param font
	 * @return
	 */
	public static Dimension calculateTextSize(String text, Font font) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int width = (int) (font.getStringBounds(text, frc).getWidth());
		int height = (int) (font.getStringBounds(text, frc).getHeight());
		return new Dimension(width, height);
	}
	/**
	 * calculates the label size and returns it
	 * @param label
	 * @return
	 */
	private Dimension calculateLabelSize(JLabel label) {
		return calculateTextSize(label.getText(), label.getFont());
	}
	/**
	 * creates a label with the given parameters where the text is NOT underlined
	 * @param text
	 * @param fontSize
	 * @return
	 */
	protected JLabel createLabel(String text, int fontSize) {
		return createLabel(text, fontSize, false);
	}
	/**
	 * creates a label where the text can be underlined
	 * @param text
	 * @param fontSize
	 * @param underline
	 * @return
	 */
	protected JLabel createLabel(String text, int fontSize, boolean underline) {
		JLabel label = new JLabel(text);
		label.setFont(createFont(fontSize));
		label.setSize(calculateLabelSize(label));

		if (underline) {
			Font font = label.getFont();
			Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			label.setFont(font.deriveFont(attributes));
		}

		add(label);
		return label;
	}
	/**
	 * creates the Text Pane for the info View 
	 * @return
	 */
	protected JTextPane createTextPane() {
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(3, 3, 3, 3)));
		return textPane;
	}
	/**
	 * creats a error Message with given text and title 
	 * @param text
	 * @param title
	 */
	protected void showErrorMessage(String text, String title) {
		JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
	}

}
