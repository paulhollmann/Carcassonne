package fop.model.interfaces;

import java.awt.Color;

import fop.view.components.GameBoardPanel;
import fop.view.components.ToolbarPanel;
import fop.view.components.tile.TileStackPanel;

public interface GameViewMethods {
	/**
	 * Returns the GameBoardPanel 
	 * @return
	 */
	public GameBoardPanel getGameBoardPanel();
	/**
	 * Returns the TileStackPanel
	 * @return
	 */
	public TileStackPanel getTileStackPanel();
	/**
	 * Returns the ToolbarPanel
	 * @return
	 */
	public ToolbarPanel getToolbarPanel();
	/**
	 * writes the given text with the given color into the StatusbarPanel 
	 * @param status
	 * @param color
	 */
	public void setStatusbarPanel(String status, Color color);
}
