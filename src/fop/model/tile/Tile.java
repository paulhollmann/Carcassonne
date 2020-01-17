package fop.model.tile;

import static fop.model.tile.Position.BOTTOM;
import static fop.model.tile.Position.BOTTOMLEFT;
import static fop.model.tile.Position.BOTTOMRIGHT;
import static fop.model.tile.Position.CENTER;
import static fop.model.tile.Position.LEFT;
import static fop.model.tile.Position.RIGHT;
import static fop.model.tile.Position.TOP;
import static fop.model.tile.Position.TOPLEFT;
import static fop.model.tile.Position.TOPRIGHT;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import fop.base.Edge;
import fop.model.graph.FeatureNode;
import fop.model.player.Player;

/**
 * This class represents a tile 
 */
public class Tile {
	
	private TileType type;
	private SortedMap<Position, FeatureNode> nodes; 
	private List<Edge<FeatureType>> edges;
	private final boolean pennant;
	private int rotation;
	public int x;
	public int y;

	/**
	 * creates a tile via TileType
	 * @param type
	 */
	public Tile(TileType type) {
		this.type = type;
		nodes = new TreeMap<Position, FeatureNode>();
		edges = new LinkedList<Edge<FeatureType>>();
		pennant = false;
	}

	/**
	 * creates a tile via TileType and coatOfArms
	 * @param type
	 * @param coatOfArms
	 */
	public Tile(TileType type, boolean coatOfArms) {
		this.type = type;
		nodes = new TreeMap<Position, FeatureNode>();
		edges = new LinkedList<Edge<FeatureType>>();
		this.pennant = coatOfArms;
	}
	/**
	 * Returns the Tile Type
	 * @return
	 */
	public TileType getType() {
		return type;
	}
	/**
	 * adds a node at the given position and given node
	 * @param position
	 * @param node
	 */
	protected void addNode(Position position, FeatureNode node) {
		nodes.put(position, node);
	}
	/**
	 * returns the Node ad given position
	 * @param position
	 * @return
	 */
	public FeatureNode getNode(Position position) {
		return nodes.get(position);
	}
	/**
	 * checks if given node is available
	 * @param node
	 * @return
	 */
	public boolean containsNode(FeatureNode node) {
		for (FeatureNode n : nodes.values()) {
			if (n == node)
				return true;
		}
		return false;
	}
	/**
	 * adds an Edge 
	 * @param edge
	 * @return
	 */
	public boolean addEdge(Edge<FeatureType> edge) {
		return edges.add(edge);
	}
	/**
	 * adds the given position
	 * @param p
	 * @return
	 */
	public FeatureType featureAtPosition(Position p) {
		return nodes.get(p).getType();
	}
	/**
	 * return all the nodes
	 * @return
	 */
	public Collection<FeatureNode> getNodes() {
		return nodes.values();
	}
	/**
	 * return all the Edges 
	 * @return
	 */
	public List<Edge<FeatureType>> getEdges() {
		return edges;
	}

	/**
	 * Returns the node at given position.
	 * 
	 * @param p A Position
	 * @return The node at given position or null.
	 */
	public FeatureNode getNodeAtPosition(Position p) {
		return nodes.get(p);
	}

	/**
	 * Rotates this tile 90 degree to the right and updates the position and
	 * direction of its nodes accordingly.
	 */
	public void rotateRight() {
		SortedMap<Position, FeatureNode> rotatedNodes = new TreeMap<Position, FeatureNode>();

		// Iterate over all positions of the tile
		// rotate nodes
		// TODO

		nodes = rotatedNodes;

		// set rotation
		// TODO
	}

	/**
	 * Returns this tiles current rotation in degree.
	 * 
	 * @return this tiles current rotation in degree.
	 */
	public int getRotation() {
		return rotation;
	}


	/**
	 * Returns if this tile has a pennant.
	 * 
	 * @return true if this tile has a pennant, false if not.
	 */
	public boolean hasPennant() {
		return pennant;
	}

	/**
	 * 
	 * @return The position on which a meeple was placed or null if no meeple was
	 *         placed.
	 */
	public Position getMeeplePosition() {
		for (Position p : Position.values()) {
			FeatureNode n = nodes.get(p);
			if (n != null && n.hasMeeple())
				return p;
		}

		return null;
	}

	/**
	 * Returns true if this tile has a meeple placed on it.
	 * 
	 * @return true if it does, false if it doesn't
	 */
	public boolean hasMeeple() {
		for (FeatureNode n : nodes.values())
			if (n.hasMeeple())
				return true;
		return false;
	}

	/**
	 * Returns the Player that has placed a meeple on this tile.
	 * 
	 * @return The Player that has placed a meeple or null if there is no meeple
	 *         placed here.
	 */
	public Player getMeeple() {
		for (FeatureNode n : nodes.values())
			if (n.hasMeeple())
				return n.getPlayer();
		return null;
	}
	
	public void setGraph(List<Edge<FeatureType>> edges) {
		this.edges = edges; 
	}

}
