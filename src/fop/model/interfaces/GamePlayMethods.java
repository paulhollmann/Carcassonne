package fop.model.interfaces;

import java.util.List;

import fop.model.player.Player;
import fop.model.tile.Position;
import fop.model.tile.Tile;

public interface GamePlayMethods {

	/**
	 * picks up a tile and returns it
	 * 
	 * @param gc
	 * @return Tile
	 */

	public Tile pickUpTile();

	/**
	 * creates a new Tile
	 * 
	 * @param t
	 * @param x
	 * @param y
	 * @param gc
	 */
	public void newTile(Tile t, int x, int y);

	/**
	 * places a meeple at position
	 * 
	 * @param position
	 * @param gc
	 */
	public void placeMeeple(Position position);

	/**
	 * checks if the current tile is allowed
	 * 
	 * @param x
	 * @param y
	 * @param gc
	 * @return
	 */
	public boolean isTopTileAllowed(int x, int y);

	/**
	 * Rotates the currentTile
	 * 
	 * @param gc
	 */
	public void rotateTopTile();

	/**
	 * goes to the next round or sets the state to game Over
	 * 
	 * @param gc
	 */
	public void nextRound();

	/**
	 * inits a the Gameboard
	 * 
	 * @param gc
	 */
	public void initGameboard();

	/**
	 * setups the Observers
	 * 
	 * @param gc
	 */
	public void setupObservers();

	/**
	 * setups the window listeners
	 * 
	 * @param gc
	 */
	public void setupListeners();
	
	/**
	 * Returns the player whose turn it is.
	 * 
	 * @return the player whose turn it is.
	 */
	public  Player currentPlayer();

	/**
	 * inits a game
	 */
	public void initGame(); 


	/**
	 * the mode were the player sets a tile 
	 */
	public void placing_Tile_Mode();
	
	/**
	 * the mode were the player sets a Meeple
	 */
	public void placing_Meeple_Mode();

	/**
	 * returns the winners from the game
	 * @param players
	 * @return
	 */
	public List<Player> getWinners(List<Player>players);
}
