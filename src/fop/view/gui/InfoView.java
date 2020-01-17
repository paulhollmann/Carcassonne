package fop.view.gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import fop.model.interfaces.GameMethods;
import fop.view.components.View;

/**
 * Info Area
 *
 */
public class InfoView extends View {

	private static final String ABOUT_TEXT = "~ Carcassone ~\nFOP-Projekt WiSe 19/20";

	private JButton btnBack;
	private JTextPane txtInfo;
	private JLabel lblTitle;
	GameWindow gw;

	public InfoView(GameWindow gameWindow) {
		super(gameWindow);
		this.gw = gameWindow;
	}

	@Override
	public void onResize() {

		int offsetY = 25;
		lblTitle.setLocation((getWidth() - lblTitle.getWidth()) / 2, offsetY);
		offsetY += lblTitle.getSize().height + 25;
		txtInfo.setLocation(25, offsetY);
		txtInfo.setSize(getWidth() - 50, getHeight() - 50 - BUTTON_SIZE.height - offsetY);

		btnBack.setLocation((getWidth() - BUTTON_SIZE.width) / 2, getHeight() - BUTTON_SIZE.height - 25);
	}

	@Override
	protected void onInit() {
		btnBack = createButton("Back");
		lblTitle = createLabel("About", 25, true);
		txtInfo = createTextPane();
		txtInfo.setText(ABOUT_TEXT);
		txtInfo.setBorder(null);
		txtInfo.setBackground(this.getBackground());
		add(txtInfo);

		StyledDocument doc = txtInfo.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		GameMethods.GoToMainMenu();
	}
}
