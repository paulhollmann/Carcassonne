package fop.model.interfaces;

import fop.model.player.MeepleColor;

public interface PlayerMethods {
	/**
	 * Returns the meeple color of this player.
	 * 
	 * @return the meeple color of this player.
	 */
	public MeepleColor getColor();

	/**
	 * Returns the name of this player.
	 * 
	 * @return the name of this player.
	 */
	public String getName();
	/**
	 * Adds points to this players score.
	 * 
	 * @param score
	 *            The score to be added.
	 */
	public void addScore(int score);
	/**
	 * Return this players score.
	 * 
	 * @return the players score.
	 */
	public int getScore();
	
	/**
	 * Returns the amount of meeple this player has left.
	 * 
	 * @return the amount of meeple this player has left.
	 */
	public int getMeepleAmount();
	
	/**
	 * Removes one meeple from this players amount of meeple.
	 */
	public void removeMeeple();
	/**
	 * Adds one meeple to this players amount of meeple.
	 */
	public void returnMeeple();
	
}
