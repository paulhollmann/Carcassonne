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

	/*
	 * This method iterates over the gameboard and places a tile at the first possible place
	 * 
	 * @param Gameplay
	 * @param Tile
	 */
	
	public void draw(GamePlay gp, Tile tile) {
		GameController gc = gp.getGameController();
		// TODO
		
		Tile[][] board = gc.getGameBoard().getBoard();
		boolean positionAvailable;
		
		//x direction
		outter:
		for(int x=1;x<board.length-1;x++) {
			//y direction
			for(int y=1;y<board.length-1;y++) {
				positionAvailable = true;
				inner:
				for(int i=0;i<gc.getGameBoard().getTiles().size();i++) {
					int xOfExistingTile = gc.getGameBoard().getTiles().get(i).x;
					int yOfExistingTile = gc.getGameBoard().getTiles().get(i).y;
					
					if(x == xOfExistingTile && y == yOfExistingTile) {
						positionAvailable = false;
						break inner;
					}
				}
				System.out.println("Testing Tile at x: " + x + " y: " + y);
				//System.out.println("PositionAvailable: " + positionAvailable);
				//System.out.println("Tile Allowed: " + gc.getGameBoard().isTileAllowed(tile, x, y));
				if(positionAvailable && gc.getGameBoard().isTileAllowed(tile, x, y)) {
					gc.getGameBoard().newTile(tile, x, y);
					System.out.println("AI placed a tile");
					break outter;
				}
			}
		}
	}

	/*public void draw(GamePlay gp, Tile tile) {
		GameController gc = gp.getGameController();
		
		Tile[][] board = gc.getGameBoard().getBoard();
		boolean positionAvailable = true;
		
		outer:
		for(int x=1;x<board.length-1;x++) {
			for(int y=1;y<board.length-1;y++) {
				inner:
				for(int i=0;i<gc.getGameBoard().getTiles().size();i++) {
					int x2 = gc.getGameBoard().getTiles().get(i).x;
					int y2 = gc.getGameBoard().getTiles().get(i).y;
					if(x == x2 && y == y2) {
						positionAvailable = false;
						break inner;
					}
				}
			System.out.println("Tile checked at x: "+x+" y: "+y);
				if(positionAvailable = true && gc.getGameBoard().isTileAllowed(tile, x, y)) {
					gc.getGameBoard().newTile(tile, x, y);
				}
			}
		}
	}*/
	
	/*
	 * This method places if possible a Meeple on the newest tile on the gameboard
	 * 
	 * @param GamePlay
	 */
	
	public void placeMeeple(GamePlay gp) {
		GameController gc = gp.getGameController();
		// TODO
		// if no position is allowed, you have to call nextRound() by yourself.
		if(gc.getGameBoard().getMeepleSpots() == null) {
			gp.nextRound();
		}
		else {
			//gc.getGameBoard().getMeepleSpots();
			boolean[] meepleSpots = gc.getGameBoard().getMeepleSpots().clone();
			ArrayList<Integer> trueIndexes = new ArrayList<>();
			
			for(int i =0;i<gc.getGameBoard().getMeepleSpots().length;i++) {
				if(meepleSpots[i]) {
					trueIndexes.add(i);
				}
				else {
					//Do nothing
				}
			}
			
			Random random = new Random();
			int spot = random.nextInt(trueIndexes.size());
			int positionIndex = trueIndexes.get(spot);
			
			if(positionIndex == 0) {
				gp.placeMeeple(Position.TOPLEFT);
			}
			else if(positionIndex == 1) {
				gp.placeMeeple(Position.TOP);
			}
			else if(positionIndex == 2) {
				gp.placeMeeple(Position.TOPRIGHT);
			}
			else if(positionIndex == 3) {
				gp.placeMeeple(Position.LEFT);
			}
			else if(positionIndex == 4) {
				gp.placeMeeple(Position.CENTER);
			}
			else if(positionIndex == 5) {
				gp.placeMeeple(Position.RIGHT);
			}
			else if(positionIndex == 6) {
				gp.placeMeeple(Position.BOTTOMLEFT);
			}
			else if(positionIndex == 7) {
				gp.placeMeeple(Position.BOTTOM);
			}
			else {
				gp.placeMeeple(Position.BOTTOMRIGHT);
			}						
		}
		// to place a meeple, call gp.placeMeeple(...).
	}

}
