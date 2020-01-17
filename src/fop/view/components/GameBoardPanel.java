package fop.view.components;

import static fop.model.tile.TileType.FLIPSIDE;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fop.controller.GameController;
import fop.model.gameplay.GamePlay;
import fop.model.gameplay.Gameboard;
import fop.model.gameplay.State;
import fop.model.interfaces.Observer;
import fop.model.player.Player;
import fop.model.tile.Position;
import fop.model.tile.Tile;
import fop.model.tile.TileStack;
import fop.model.tile.TileType;
import fop.view.components.meeple.MeepleOverlayPanel;
import fop.view.components.meeple.TemporaryMeepleOverlayPanel;
import fop.view.components.meeple.TemporaryMeeplePanel;
import fop.view.components.tile.TilePanel;

public class GameBoardPanel extends JPanel implements Observer<Gameboard> {

	/**
	 * This represents a tile that might be placed but was not yet placed, so it is
	 * only temporarily overlayed. Other than tiles that have already be placed, it
	 * might still change its rotation. It gets its type and rotation by listening
	 * to the TileStack (it is always the top tile on the tile stack).
	 * 
	 */
	private class TileOverlayPanel extends TilePanel implements Observer<TileStack> {

		TileOverlayPanel(TileType type, int size) {
			super(type, size);
		}

		@Override
		public void update(TileStack ts) {
			setType(ts.peekTile().getType());
			setRotation(ts.peekTile().getRotation());
			repaint();
		}
	}

	private GameController gc;

	private Point anchorPoint;
	private GridBagLayout gbl;
	private GridBagConstraints gbc;

	private int scale = 100; // in pixels of each tile
	private TilePanel tileInFocus;
	private TilePanel tileWithOverlay;
	private TileOverlayPanel tileOverlay;

	private TemporaryMeepleOverlayPanel tempMeepleOverlay;
	private GamePlay gameplay;

	public GameBoardPanel(GameController gc) {
		this.gc = gc;
		this.gameplay = gc.getGamePlay();
		gbl = new GridBagLayout();
		setLayout(gbl);
		gbc = new GridBagConstraints();

		tileOverlay = new TileOverlayPanel(FLIPSIDE, scale);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				switch (gc.getState()) {
				case PLACING_TILE:
					if (contains(tileOverlay)) {
						if (SwingUtilities.isLeftMouseButton(event)) {
							gameplay.newTile(gameplay.pickUpTile(), getGridX(tileOverlay), getGridY(tileOverlay));
							gc.setState(State.PLACING_MEEPLE);
							hideTileOverlay();
						} else if ((SwingUtilities.isRightMouseButton(event))) {
							rotateUntilAllowed();
						}
					}
					break;
				case PLACING_MEEPLE:
					if (SwingUtilities.isLeftMouseButton(event)) {
						Component c = event.getComponent();
						if (c instanceof TemporaryMeeplePanel) {
							Position p = ((TemporaryMeeplePanel) c).getPosition();
							if (p != null)
								gameplay.placeMeeple(p);
						}
					} else if (SwingUtilities.isRightMouseButton(event)) {
						gameplay.nextRound();
					}
					break;
				default:
					break;
				}
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent event) {
				int anchorX = anchorPoint.x;
				int anchorY = anchorPoint.y;

				Point parentOnScreen = getParent().getLocationOnScreen();
				Point mouseOnScreen = event.getLocationOnScreen();
				Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX,
						mouseOnScreen.y - parentOnScreen.y - anchorY);
				setLocation(position);
			}

			@Override
			public void mouseMoved(MouseEvent event) {
				Point p = event.getPoint();

				// save current cursor position, used by mouseDragged()
				anchorPoint = p;

				if (gc.getState() == State.PLACING_TILE)
					showTileOverlay(p);

			}
		});

		this.addMouseWheelListener(event -> {
			int notches = event.getWheelRotation();
			if (notches < 0) {
				setScale(6);
			} else {
				setScale(-6);

			}
		});
	}

	/**
	 * Adds TilePanels of type FLIPSIDE everywhere around a given position, where
	 * there is no TilePanel yet.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void addSurroundingFlipsides(int x, int y) {
		gbc.gridy = y;
		gbc.gridx = x - 1;
		if (!hasTileAt(gbc.gridx, gbc.gridy))
			add(new TilePanel(FLIPSIDE, scale), gbc);
		gbc.gridx = x + 1;
		if (!hasTileAt(gbc.gridx, gbc.gridy))
			add(new TilePanel(FLIPSIDE, scale), gbc);
		gbc.gridx = x;
		gbc.gridy = y + 1;
		if (!hasTileAt(gbc.gridx, gbc.gridy))
			add(new TilePanel(FLIPSIDE, scale), gbc);
		gbc.gridy = y - 1;
		if (!hasTileAt(gbc.gridx, gbc.gridy))
			add(new TilePanel(FLIPSIDE, scale), gbc);
	}

	/**
	 * Returns the x position of a TilePanel in the grid of the GridBagLayout.
	 * 
	 * @param t A TilePanel.
	 * @return The TilePanels x position.
	 */
	private int getGridX(TilePanel t) {
		return gbl.getConstraints(t).gridx;
	}

	/**
	 * Returns the y position of a TilePanel in the grid of the GridBagLayout.
	 * 
	 * @param t A TilePanel.
	 * @return The TilePanels y position.
	 */
	private int getGridY(TilePanel t) {
		return gbl.getConstraints(t).gridy;
	}

	/**
	 * Returns the TilePanel at a given Point, which is a pixel location relative to
	 * the GameBoardPanel.
	 * 
	 * @param p A pixel coordinate as a Point
	 * @return The TilePanel at given Point or null, if there is none.
	 */
	private TilePanel getTileAt(Point p) {
		Component c = getComponentAt(p);
		if (c instanceof TilePanel)
			return (TilePanel) c;
		else
			return null;
	}

	private TilePanel[] getTiles() {
		return Arrays.stream(getComponents()).filter(c -> c instanceof TilePanel).map(c -> (TilePanel) c)
				.toArray(TilePanel[]::new);
	}

	public TileOverlayPanel getTileOverlay() {
		return tileOverlay;
	}

	/**
	 * Checks if the gameboard has a tile at (x, y). Strictly speaking, it just
	 * checks if there is _any_ component at (x, y). As we are exclusively adding
	 * TilePanel components to the gameboard, we may assume the component is a tile.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean hasTileAt(int x, int y) {
		for (Component c : getComponents()) {
			if (gbl.getConstraints(c).gridx == x)
				if (gbl.getConstraints(c).gridy == y)
					return true;
		}
		return false;
	}

	/**
	 * Adds a tile to the gameboard panel at given position (x, y). Actually it just
	 * updates the type and rotation of the tile at the given position, as there
	 * must already be a TilePanel (of type FLIPSIDE) on the same position.
	 * 
	 * @param t
	 * @param x
	 * @param y
	 */
	private void newTile(TileType type, int rotation, int x, int y) {
		for (TilePanel t : getTiles()) {
			if (gbl.getConstraints(t).gridx == x)
				if (gbl.getConstraints(t).gridy == y) {
					t.setType(type);
					t.setRotation(rotation);
					addSurroundingFlipsides(x, y);
					return;
				}
		}

		/**
		 * This part is only reached if there was no TilePanel at given position, which
		 * should only be the case once at game initialization.
		 */
		TilePanel tile = new TilePanel(type, scale);
		tile.setRotation(rotation);
		gbc.gridx = x;
		gbc.gridy = y;
		add(tile, gbc);

		addSurroundingFlipsides(x, y);
	}

	private boolean rotateUntilAllowed() {
		boolean allowed = false;
		gameplay.rotateTopTile();

		for (int i = 0; i < 3; i++) {
			if (!gameplay.isTopTileAllowed(getGridX(tileOverlay), getGridY(tileOverlay))) {
				gameplay.rotateTopTile();
			} else {
				allowed = true;
			}
		}
		return allowed;
	}

	private void setScale(int pixels) {
		if (scale + pixels < 50 || scale + pixels > 150)
			return;
		else {
			for (Component c : getComponents()) {
				if (c instanceof MeepleOverlayPanel)
					c.setPreferredSize(new Dimension(scale + pixels, scale + pixels));
				else if (c instanceof TilePanel)
					c.setPreferredSize(new Dimension(scale + pixels, scale + pixels));
			}

			tileOverlay.setPreferredSize(new Dimension(scale + pixels, scale + pixels));
			scale += pixels;
			revalidate(); // !
		}
	}

	public MeepleOverlayPanel showTemporaryMeepleOverlay(boolean[] meepleSpots, int x, int y, Player player) {
		gbc.gridx = x;
		gbc.gridy = y;
		tempMeepleOverlay = new TemporaryMeepleOverlayPanel(meepleSpots, scale, player);
		add(tempMeepleOverlay, gbc, 0);
		return tempMeepleOverlay;
	}

	private MeepleOverlayPanel showMeepleOverlay(boolean[] meepleSpots, int x, int y, Player player) {
		gbc.gridx = x;
		gbc.gridy = y;
		add(new MeepleOverlayPanel(meepleSpots, scale, player), gbc, 0);
		return tempMeepleOverlay;
	}

	/**
	 * Removes the temporary meeple overlay (which shows possible meeple spots) that
	 * is displayed when entering PLACING_MEEPLE state.
	 */
	public void removeTempMeepleOverlay() {
		remove(tempMeepleOverlay);
		repaint(); // !
	}

	private void removeMeeples() {
		for (Component c : getComponents()) {
			if (c instanceof MeepleOverlayPanel)
				remove(c);
		}
	}

	private void showMeeples(List<Tile> tiles) {
		removeMeeples();
		for (Tile t : tiles) {
			if (t.hasMeeple()) {
				boolean[] positions = new boolean[9];

				for (Position p : Position.values()) {
					if (p.equals(t.getMeeplePosition()))
						positions[p.ordinal()] = true;
				}
				showMeepleOverlay(positions, t.x, t.y, t.getMeeple());
			}
		}
		repaint(); // nsin
	}

	/**
	 * Shows a temporary tile (the top tile of the tile stack) if it is allowed to
	 * place it on the mouses location, which is given as a Point.
	 * 
	 * @param p The mouses location as a Point.
	 */
	private void showTileOverlay(Point p) {
		TilePanel tile = getTileAt(p);
		if (tile != null && tile == tileInFocus)
			return;
		tileInFocus = tile;

		if (contains(tileOverlay) && !tileOverlay.contains(p)) {
			hideTileOverlay();
		}

		if (tile != null && !contains(tileOverlay) && tile.getType() == FLIPSIDE) {
			for (int i = 0; i < 4; i++) {
				if (gameplay.isTopTileAllowed(getGridX(tile), getGridY(tile))) {
					tileWithOverlay = tile;
					tileWithOverlay.setVisible(false);
					gbc.gridx = getGridX(tile);
					gbc.gridy = getGridY(tile);
					add(tileOverlay, gbc);
					repaint(); // nsin
					return;
				} else {
					gameplay.rotateTopTile();
					repaint();
				}
			}
		}
	}

	/**
	 * Hides the tile overlay and shows the underlying tile.
	 */
	private void hideTileOverlay() {
		remove(tileOverlay);
		tileWithOverlay.setVisible(true);
		tileWithOverlay = null;
		repaint();
	}

	/**
	 * Checks if a given component is part of this panel.
	 * 
	 * @param c The component
	 * @return True if the component is part of this panels components.
	 */
	private boolean contains(Component c) {
		for (Component component : getComponents())
			if (component.equals(c))
				return true;
		return false;
	}

	@Override
	public void update(Gameboard board) {
		switch (gc.getState()) {
		case GAME_START:
			// same behaviour as when PLACING_TILE, so we just omit the break statement
		case PLACING_TILE:
			Tile newTile = board.getNewestTile();
			newTile(newTile.getType(), newTile.getRotation(), newTile.x, newTile.y);
			break;
		case GAME_OVER:
			// same behaviour as when PLACING_MEEPLE, so we just omit the break statement
		case PLACING_MEEPLE:
			showMeeples(board.getTiles());
			repaint();
			break;
		default:
			break;
		}
	}
}
