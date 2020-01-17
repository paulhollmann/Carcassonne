package fop.model.tile;

/**
 * This "enum" class contains the different positions from a tile
 *
 */
public enum Position {

	TOPLEFT, TOP, TOPRIGHT, LEFT, CENTER, RIGHT, BOTTOMLEFT, BOTTOM, BOTTOMRIGHT;

	/**
	 * returns an array of the straight positions
	 * @return
	 */
	public static Position[] getStraightPositions() {
		return new Position[] { TOP, LEFT, RIGHT, BOTTOM };
	}
	/**
	 * returns an array of the diagonal positions
	 * @return
	 */
	public static Position[] getDiagonalPositions() {
		return new Position[] { TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT };
	}
	
	public static Position[] getAllPosition() {
		return new Position[] {TOPLEFT, TOP, TOPRIGHT, LEFT, CENTER, RIGHT, BOTTOMLEFT, BOTTOM, BOTTOMRIGHT};
	}

}
