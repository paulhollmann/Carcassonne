package fop.model.interfaces;

import fop.model.gameplay.Gameboard;

public interface GameboardObserver extends Observer<Gameboard> {
	/**
	 * creates a new Tile
	 * @param id
	 * @param rotation
	 * @param x
	 * @param y
	 */
	public void newTile(String id, int rotation, int x, int y);

}
