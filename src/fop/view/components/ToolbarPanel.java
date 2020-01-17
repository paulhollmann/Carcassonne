package fop.view.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import fop.model.interfaces.Observer;
import fop.model.player.Player;

/**
 * creates a ToolbarPanel which displays Playername und updates the score.
 *
 */
public class ToolbarPanel extends JPanel implements Observer<List<Player>> {

	JButton menuButton;
	JButton skipButton;
	JLabel[] playerLabels;

	/**
	 * creates boxes for the players which get the MeepleColor as the border color.
	 * Displays the name and the current score
	 * 
	 * @param players
	 */
	public ToolbarPanel(List<Player> players) {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		playerLabels = new JLabel[players.size()];
		for (int i = 0; i < players.size(); i++) {
			playerLabels[i] = new JLabel();
			playerLabels[i].setBorder(
					BorderFactory.createTitledBorder(null, players.get(i).getName(), TitledBorder.DEFAULT_JUSTIFICATION,
							TitledBorder.DEFAULT_POSITION, null, players.get(i).getColor().getMeepleColor()));
			playerLabels[i].setPreferredSize(new Dimension(80, 54));
			add(playerLabels[i]);
		}

		menuButton = new JButton("Main menu");
		add(menuButton);

		skipButton = new JButton("Skip");
		add(skipButton);
	}

	/**
	 * Adds an action listener to the menu and skip buttons.
	 * 
	 * @param l The action listener
	 */
	public void addToolbarActionListener(ActionListener l) {
		menuButton.addActionListener(l);
		skipButton.addActionListener(l);
	}

	/**
	 * if true => display will show up if false => display will hide
	 * 
	 * @param visible
	 */
	public void showSkipButton(boolean visible) {
		skipButton.setVisible(visible);
	}

	/**
	 * Updates the current score and meeple count for each player.
	 * 
	 * @param players (an array of the players)
	 */
	private void updatePlayers(List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			playerLabels[i].setText("<html>Score:  " + players.get(i).getScore() + "<br>Meeples: "
					+ players.get(i).getMeepleAmount() + "</html>");
		}
	}

	/**
	 * updates the current score on the Board
	 */
	@Override
	public void update(List<Player> players) {
		updatePlayers(players);
	}

}
