package fop.view.components.meeple;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fop.model.interfaces.GameConstants;
import fop.model.player.MeepleColor;
import fop.model.player.Player;
import fop.model.tile.Position;

/**
 * A MeeplePanel is one of nine Panels inside a MeepleOverlayPanel. It is either
 * a possible spot to place a Meeple on or not, in which case it is still part
 * of the MeepleOverlayPanel to get the spacing right.
 * 
 *
 */
public class MeeplePanel extends JPanel implements GameConstants {

	protected BufferedImage meepleImage;
	protected Position position; // the meeple spots position inside the tile its on
	protected MeepleColor colour;

	/**
	 * A meeple panel without a position is considered not to be a spot to place a
	 * meeple on. It's just an invisible panel using up space.
	 */
	public MeeplePanel() {
		this.setOpaque(false);
	}

	public MeeplePanel(Position pos, Player player) {
		this();
		this.position = pos;
		this.colour = player.getColor();

		try {
			meepleImage = ImageIO
					.read(getClass().getResource(MEEPLE_FOLDER + colour.toString().toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns the Position of the meeple
	 * 
	 * @return
	 */
	public Position getPosition() {
		return position;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; // nsin
		g2d.drawImage(meepleImage, 0, 0, getWidth(), getHeight(), null);
		revalidate(); // nsin
	}

}
