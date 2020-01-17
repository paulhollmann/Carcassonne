
package fop.controller;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import fop.model.gameplay.GamePlay;
import fop.model.gameplay.Gameboard;
import fop.model.gameplay.State;
import fop.model.interfaces.GameControllerMethods;
import fop.model.player.Player;
import fop.model.tile.TileStack;
import fop.view.components.GameBoardPanel;
import fop.view.components.gui.GameView;
import fop.view.components.gui.Resources;
import fop.view.components.tile.TileStackPanel;

/**
 * Game Controller controls the game play
 * 
 *
 */
public class GameController implements GameControllerMethods {

	// model
	private State state;
	private Gameboard board;
	private TileStack stack;
	private List<Player> players;
	private int currentRound;

	// view
	private JFrame window;
	private GameView view;
	private GameBoardPanel boardPanel;
	private TileStackPanel stackPanel;

	private Resources resources;

	private GamePlay gameplay;

	public GameController() {
		setupWindow();
		resources = Resources.getInstance();
		if (!resources.load())
			return;
		Runtime.getRuntime().addShutdownHook(new Thread(resources::save));

		currentRound = 0;
		board = new Gameboard();
		gameplay = new GamePlay(this);
		boardPanel = new GameBoardPanel(this);
		stackPanel = new TileStackPanel(); 

	}
	/**
	 * Sets up the Window
	 */
	public void setupWindow() {
		try {
			// set look and feel of swing components
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		window = new JFrame("Carcassonne - FOP Projekt WiSe 19/20");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(400, 300));
	}
	
	/**
	 * Sets up the View
	 * @param view 
	 */
	public void setView(JPanel view) {
		window.setContentPane(view);
		window.pack();
		window.revalidate();
		window.repaint();
		window.setVisible(true);
	}

	@Override
	public void setState(State state) {
		this.state = state;
		switch (state) {
		case GAME_START:
			gameplay.initGame();
			break;

		case PLACING_TILE:
			gameplay.placing_Tile_Mode();
			break;
		case PLACING_MEEPLE:
			gameplay.placing_Meeple_Mode();
			break;

		case GAME_OVER:
			gameplay.game_Over_Mode();
			break;
		default:
			break;
		}
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public TileStack getTileStack() {
		return stack;
	}

	@Override
	public Gameboard getGameBoard() {
		return board;
	}

	@Override
	public int getCurrentRound() {
		return currentRound;
	}

	@Override
	public GameBoardPanel getGameBoardPanel() {
		return boardPanel;
	}

	@Override
	public void incrementRound() {
		currentRound++;
	}

	@Override
	public GameView getGameView() {
		return view;
	}

	@Override
	public TileStackPanel getTileStackPanel() {
		return stackPanel;
	}

	@Override
	public GamePlay getGamePlay() {
		return gameplay;
	}

	@Override
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	@Override
	public void setBoard(Gameboard board) {
		this.board = board;
	}

	@Override
	public void setTileStack(TileStack stack) {
		this.stack = stack;
	}

	@Override
	public void setGameView(GameView view) {
		this.view = view;
	}

	@Override
	public void setGameBoardPanel(GameBoardPanel gameBoardPanel) {
		this.boardPanel = gameBoardPanel;
	}

	@Override
	public void setTileStackPanel(TileStackPanel tileStackPanel) {
		this.stackPanel = tileStackPanel;
	}

}