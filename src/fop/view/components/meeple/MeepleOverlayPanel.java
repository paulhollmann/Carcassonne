package fop.view.components.meeple; 

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;

import fop.model.player.Player;
import fop.model.tile.Position;

public class MeepleOverlayPanel extends JLabel {

	/**
	 * Creates the MeepleOverlayPanel with given size
	 * @param size
	 */
	public MeepleOverlayPanel(int size) {
		this.setLayout(new GridLayout(3, 3));
		this.setPreferredSize(new Dimension(size, size));
	}

	/**
	 * returns the MeepleOverplay panel with meeples from the player
	 * @param meepleSpots
	 * @param size
	 * @param player
	 */
	public MeepleOverlayPanel(boolean[] meepleSpots, int size, Player player) {
		this(size);

		for (int i = 0; i < meepleSpots.length; i++) {
			if (meepleSpots[i])
				add(new MeeplePanel(Position.values()[i], player));
			else
				add(new MeeplePanel());
		}
	}
}
