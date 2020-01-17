package fop.model.player;

import java.awt.Color;

/**
 * This "enum" class contains the available MeepleColor Also it translate the
 * MeepleColor to the equivalent java.awt Color
 * 
 */
public enum MeepleColor {

	YELLOW, GREEN, GREY, RED, BLUE, BLACK;

	/**
	 * This function returns the equivalent java.awt.Color to the MeepleColor
	 * 
	 * @return Color
	 */
	public Color getMeepleColor() {
		switch (this) {
		case YELLOW:
			return Color.YELLOW;
		case BLACK:
			return Color.BLACK;
		case BLUE:
			return Color.BLUE;
		case GREEN:
			return Color.GREEN;
		case GREY:
			return Color.GRAY;
		case RED:
			return Color.RED;
		default:
			return null;
		}
	}

	public static Color[] availableMeepleColors = { Color.YELLOW, Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY, Color.RED};
	
	/**
	 * Converts a java.awt.Color to a MeepleColor 
	 * @param color
	 * @return MeepleColor
	 */
	public static MeepleColor toMeepleColor(Color color) {
		if(color .equals(Color.YELLOW)) {
			return YELLOW;
		}else if(color .equals(Color.BLACK)) {
			return BLACK;
		}else if(color .equals(Color.BLUE)) {
			return BLUE;
		}else if(color .equals(Color.GREEN)) {
			return GREEN;
		}else if(color .equals(Color.GRAY)) {
			return GREY;
		}else if(color .equals(Color.RED)) {
			return RED;
		}
		
		return null; 
	}

}
