package fop.model.graph;

import fop.base.Node;
import fop.model.player.Player;
import fop.model.tile.FeatureType;

public class FeatureNode extends Node<FeatureType> {

	private boolean connectsTiles;
	private boolean meepleSpot;
	private Player meeple;

	/**
	 * Creates a new FeatureNode. It is assumed that the feature is present in both
	 * directions.
	 * 
	 * @param type     The features type.
	 * @param position Its position on a tile.
	 */
	public FeatureNode(FeatureType type) {
		super(type);
		this.connectsTiles = false;
	}
	/**
	 * sets the Meeple Spot to true 
	 */
	public void setMeepleSpot() {
		this.meepleSpot = true;
	}
	/**
	 * checks if it has a Meeple Spot 
	 * @return
	 */
	public boolean hasMeepleSpot() {
		return meepleSpot;
	}
	/**
	 * sets a Player
	 * @param p
	 */
	public void setPlayer(Player p) {
		meeple = p;
	}
	/**
	 * check if a meeple has been set 
	 * @return
	 */
	public boolean hasMeeple() {
		if (meeple != null)
			return true;
		else
			return false;
	}
	/**
	 * gets the player 
	 * @return
	 */
	public Player getPlayer() {
		return meeple;
	}
	/**
	 * returns the Feature Type
	 * @return
	 */
	public FeatureType getType() {
		return getValue();
	}
	/**
	 * sets the ConnectsTiles to true 
	 */
	protected void setConnectsTiles() {
		connectsTiles = true;
	}
	/**
	 * returns if it is conected Tiles 
	 * @return
	 */
	public boolean isConnectingTiles() {
		return connectsTiles;
	}

}
