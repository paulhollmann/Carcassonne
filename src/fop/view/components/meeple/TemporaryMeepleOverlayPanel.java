package fop.view.components.meeple;

import fop.model.player.Player;
import fop.model.tile.Position;

/**
 * This class creates a TempoaryMeepleOverlayPanel, were all the possible Meeples are shown
 *
 */
public class TemporaryMeepleOverlayPanel extends MeepleOverlayPanel {

	public TemporaryMeepleOverlayPanel(boolean[] meepleSpots, int size, Player player) {
		super(size);

		for (int i = 0; i < meepleSpots.length; i++) {
			if (meepleSpots[i])
				add(new TemporaryMeeplePanel(Position.values()[i], player));
			else
				add(new TemporaryMeeplePanel());
		}
	}

}
