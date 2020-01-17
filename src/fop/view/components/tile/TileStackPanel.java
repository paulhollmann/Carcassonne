package fop.view.components.tile;

import static fop.model.tile.TileType.FLIPSIDE;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fop.model.interfaces.Observer;
import fop.model.tile.TileStack;

public class TileStackPanel extends JPanel implements Observer<TileStack> {
	private TilePanel topTile;
	private JLabel tileCounter;

	public TileStackPanel() {
		setBorder(BorderFactory.createTitledBorder("Stack"));
		setPreferredSize(new Dimension(120, 400));

		topTile = new TilePanel(FLIPSIDE, 100);
		add(topTile);

		tileCounter = new JLabel();
		add(tileCounter);
	}

	/**
	 * Updates the type and rotation of the tile stacks top tile and the amount of
	 * tiles left on the stack.
	 */
	@Override
	public void update(TileStack tilestack) {
		tileCounter.setText(Integer.toString(tilestack.remainingTiles()) + " tiles left");

		if (tilestack.remainingTiles() > 0) {
			topTile.setType(tilestack.peekTile().getType());
			topTile.setRotation(tilestack.peekTile().getRotation());
			topTile.repaint(); // !
		}
	}

	/**
	 * Hides the top tile and shows its FLIPSIDE instead.
	 */
	public void hideTopTile() {
		topTile.setType(FLIPSIDE);
		topTile.setRotation(0);
		topTile.repaint();
	}

}
