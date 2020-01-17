package fop.model.interfaces;

import java.util.List;

import javax.swing.JPanel;

import fop.model.gameplay.GamePlay;
import fop.model.gameplay.Gameboard;
import fop.model.gameplay.State;
import fop.model.player.Player;
import fop.model.tile.TileStack;
import fop.view.components.GameBoardPanel;
import fop.view.components.gui.GameView;
import fop.view.components.tile.TileStackPanel;

public interface GameControllerMethods {
	
	/**
	 * sets up the Window
	 */
	public void setupWindow();
	/**
	 * sets up the view 
	 * @param view
	 */
	public void setView(JPanel view);
	/**
	 * sets the current state from the game
	 * @param state
	 */
	public void setState(State state);
	/**
	 * returns the current state
	 * @return State
	 */
	public State getState();
	/**
	 * returns a list of the current players
	 * @return
	 */
	public List<Player> getPlayers();
	/**
	 * returns the current TileStack 
	 * @return
	 */
	public TileStack getTileStack();
	/**
	 * returns the current GameBoard 
	 * @return
	 */
	public Gameboard getGameBoard();
	/**
	 * returns the current round as an integer
	 * @return
	 */
	public int getCurrentRound();
	/**
	 * returns the current GameBoardPanel 
	 * @return
	 */
	public GameBoardPanel getGameBoardPanel();
	/**
	 * increments the current Round 
	 */
	public void incrementRound();
	/**
	 * returns the current GameView
	 * @return
	 */
	public GameView getGameView();
	/**
	 * returns the current TileStackPanel 
	 * @return
	 */
	public TileStackPanel getTileStackPanel();
	/**
	 * returns the current GamePlay 
	 * @return
	 */
	public GamePlay getGamePlay();
	/**
	 * sets the current Player
	 * @param players
	 */
	public void setPlayers(List<Player> players);
	/**
	 * sets the GameBoard
	 * @param board
	 */
	public void setBoard(Gameboard board);
	/**
	 * sets the TileStack
	 * @param stack
	 */
	public void setTileStack(TileStack stack);
	/**
	 * sets the GameView
	 * @param view
	 */
	public void setGameView(GameView view);
	/**
	 * sets the GameBoardPanel
	 * @param gameBoardPanel
	 */
	public void setGameBoardPanel(GameBoardPanel gameBoardPanel);
	/**
	 * sets the TileStackPanel
	 * @param tileStackPanel
	 */
	public void setTileStackPanel(TileStackPanel tileStackPanel);
	
	
	
}
