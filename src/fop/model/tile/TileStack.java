package fop.model.tile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fop.model.gameplay.Observable;
import fop.model.interfaces.Observer;

public class TileStack extends Observable<TileStack> {
	public List<Tile> cardstack;
	private TileGenerator generator;

	/**
	 * Constructs a tile stack with all available tiles shuffled and the start tile
	 * on top of the stack.
	 */
	public TileStack() {
		cardstack = new LinkedList<Tile>();
		generator = new TileGenerator();

		for (Tile tile : generator.getTiles()) {
			cardstack.add(0, tile);
		}

		Collections.shuffle(cardstack);

		cardstack.add(0, generator.getStartTile());
	}
	public TileStack(List<Tile> cardstack) {
		this.cardstack = new LinkedList<Tile>();
		this.generator = new TileGenerator();
		this.generator.setTiles(cardstack);
		
		for (Tile tile : this.generator.getTiles()) {
			this.cardstack.add(0, tile);
		}

		Collections.shuffle(cardstack);

		this.cardstack.add(0, generator.getStartTile());

	}

	/**
	 * Returns the top tile and removes it from the tile stack.
	 * 
	 * @return the top tile
	 */
	public Tile pickUpTile() {
		Tile topTile = cardstack.get(0);
		this.cardstack.remove(0);
		return topTile;
	}

	/**
	 * Returns the top tile.
	 * 
	 * @return the top tile.
	 */
	public Tile peekTile() {
		return cardstack.get(0);
	}

	/**
	 * Returns the number of remaining tiles.
	 * 
	 * @return the number of remaining tiles.
	 */
	public int remainingTiles() {
		return cardstack.size();
	}

	/**
	 * Rotates the top tile and updates the tile stack observers accordingly.
	 */
	public void rotateTopTile() {
		cardstack.get(0).rotateRight();
		for (Observer<TileStack> o : getOberserver())
			o.update(this);
	}

	/**
	 * Simply removes the top tile.
	 */
	public void discardTopTile() {
		cardstack.remove(0);
	}
	public TileGenerator getTileGenerator() {
		return this.generator;
	}
	
}