package fop.model.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import fop.model.tile.TileType;

/**
 * This interface contains all the important values for the game
 * 
 */
public interface GameConstants {

	public static final int MAX_PLAYERS = 4;
	public static final int MIN_PLAYERS = 2;
	public static final String TILETYPES_FILE_LOCATION = "/TileTypes.xml";
	public static final TileType START_TILE_TYPE = TileType.D;
	public static final int NUM_CASTLES = 6;
	public static final File HIGHSCORE_FILE = new File("highscores.txt");
	public static final int NUMBER_OF_MEEPLES =7;
	public static final Dimension BUTTON_SIZE = new Dimension(125, 40);
	public static final Color BUTTON_BACKGROUND_Color = Color.GREEN;
	public static final Color BUTTON_FOREGROUND_Color = Color.BLUE;
	public static final String MEEPLE_FOLDER = "/meeple/";
	public static final String TILES_FOLDER = "/tiles/";
	public static final Color WINNING_MESSAGE_COLOR = Color.ORANGE;


}
