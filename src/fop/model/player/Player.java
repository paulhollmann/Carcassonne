package fop.model.player;

import java.util.Random;
import java.util.ArrayList;

import fop.controller.GameController;
import fop.model.gameplay.GamePlay;
import fop.model.gameplay.Gameboard;
import fop.model.interfaces.GameConstants;
import fop.model.interfaces.PlayerMethods;
import fop.model.tile.Position;
import fop.model.tile.Tile;

public class Player implements PlayerMethods {

	private MeepleColor color;
	private String name;
	private int score;
	private int meeples; // the amount of meeples

	public Player(String name, MeepleColor color) {
		this.color = color;
		this.name = name;
		this.score = 0;
		this.meeples = GameConstants.NUMBER_OF_MEEPLES;
	}

	@Override
	public MeepleColor getColor() {
		return color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getMeepleAmount() {
		return meeples;
	}

	@Override
	public void removeMeeple() {
		meeples--;
	}

	@Override
	public void returnMeeple() {
		meeples++;
	}

	/**
	 * This method iterates over the gameboard and places a tile at the first
	 * possible place
	 * 
	 * @param gp the gameplay object
	 * @param tile a tile drawn from the stack
	 */

	public void draw(GamePlay gp, Tile tile) {
		GameController gc = gp.getGameController();

		Tile[][] board = gc.getGameBoard().getBoard();
		boolean positionAvailable;

		// x direction
		outter: for (int x = 1; x < board.length - 1; x++) {
			// y direction
			for (int y = 1; y < board.length - 1; y++) {
				positionAvailable = true;
				inner: for (int i = 0; i < gc.getGameBoard().getTiles().size(); i++) {
					int xOfExistingTile = gc.getGameBoard().getTiles().get(i).x;
					int yOfExistingTile = gc.getGameBoard().getTiles().get(i).y;

					if (x == xOfExistingTile && y == yOfExistingTile) {
						positionAvailable = false;
						break inner;
					}
				}
				if (positionAvailable) {
					if (gc.getGameBoard().isTileAllowed(tile, x, y)) {
						gc.getGameBoard().newTile(tile, x, y);
						break outter;
					} else {
						tile.rotateRight();
						if (gc.getGameBoard().isTileAllowed(tile, x, y)) {
							gc.getGameBoard().newTile(tile, x, y);
							break outter;
						} else {
							tile.rotateRight();
							if (gc.getGameBoard().isTileAllowed(tile, x, y)) {
								gc.getGameBoard().newTile(tile, x, y);
								break outter;
							} else {
								tile.rotateRight();
								if (gc.getGameBoard().isTileAllowed(tile, x, y)) {
									gc.getGameBoard().newTile(tile, x, y);
									break outter;
								}
							}
						}
					}
				}
			}
		}
	}


	/**
	 * This method places if possible a Meeple on the newest tile on the gameboard
	 * 
	 * @param gp the gameplay object
	 */

	public void placeMeeple(GamePlay gp) {
		GameController gc = gp.getGameController();
		// if no position is allowed, you have to call nextRound() by yourself.
		if (gc.getGameBoard().getMeepleSpots() == null) {
			gp.nextRound();
		} else {
			ArrayList<Integer> trueIndexes = new ArrayList<>();

			for (int i = 0; i < gc.getGameBoard().getMeepleSpots().length; i++) {
				if (gc.getGameBoard().getMeepleSpots()[i]) {
					trueIndexes.add(i);
				}
			}

			Random random = new Random();
			int spot = random.nextInt(trueIndexes.size());
			int positionIndex = trueIndexes.get(spot);

			if (positionIndex == 0) {
				gp.placeMeeple(Position.TOPLEFT);
			} else if (positionIndex == 1) {
				gp.placeMeeple(Position.TOP);
			} else if (positionIndex == 2) {
				gp.placeMeeple(Position.TOPRIGHT);
			} else if (positionIndex == 3) {
				gp.placeMeeple(Position.LEFT);
			} else if (positionIndex == 4) {
				gp.placeMeeple(Position.CENTER);
			} else if (positionIndex == 5) {
				gp.placeMeeple(Position.RIGHT);
			} else if (positionIndex == 6) {
				gp.placeMeeple(Position.BOTTOMLEFT);
			} else if (positionIndex == 7) {
				gp.placeMeeple(Position.BOTTOM);
			} else {
				gp.placeMeeple(Position.BOTTOMRIGHT);
			}
		}
		// to place a meeple, call gp.placeMeeple(...).
	}

}
