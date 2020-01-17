package fop.model.player;

import fop.controller.GameController;
import fop.model.gameplay.GamePlay;
import fop.model.interfaces.GameConstants;
import fop.model.interfaces.PlayerMethods;
import fop.model.tile.Position;
import fop.model.tile.Tile;

public class Player implements PlayerMethods{

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
	
	public void draw(GamePlay gp, Tile tile) {
		GameController gc = gp.getGameController();
		// TODO
	}
	
	public void placeMeeple(GamePlay gp) {
		GameController gc = gp.getGameController();
		//TODO
		//if no position is allowed, you have to call nextRound() by yourself. 
		//to place a meeple, call gp.placeMeeple(...).
	}
	
}
