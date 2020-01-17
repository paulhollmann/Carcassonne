package fop.view.components.tile;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fop.model.interfaces.GameConstants;
import fop.model.tile.TileType;


/**
 * This class creates a Tile
 *
 */
public class TilePanel extends JPanel implements GameConstants{

	private BufferedImage tileImage;
	private TileType type;
	private int rotation; // in degrees

	public TilePanel(TileType type, int size) {
		super(true); // enables double buffering
		this.rotation = 0;
		this.setPreferredSize(new Dimension(size, size));
		setType(type);
	}
/**
 * sets the Type and the picture of the Tile
 * @param type
 */
	public void setType(TileType type) {
		this.type = type;
		try {
			tileImage = ImageIO.read(getClass().getResource(TILES_FOLDER + type + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Returns the typ of the Tile
 * @return
 */
	public TileType getType() {
		return type;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(Math.toRadians(rotation), getWidth() / 2, getHeight() / 2);
		g2d.drawImage(tileImage, 0, 0, getWidth(), getHeight(), null);
	}
	
/**
 * rotates the tile
 * @param rotation
 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

}
