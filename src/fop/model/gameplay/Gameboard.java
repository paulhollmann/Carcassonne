package fop.model.gameplay;

import static fop.model.tile.FeatureType.CASTLE;
import static fop.model.tile.FeatureType.FIELDS;
import static fop.model.tile.FeatureType.ROAD;
import static fop.model.tile.Position.BOTTOM;
import static fop.model.tile.Position.BOTTOMLEFT;
import static fop.model.tile.Position.BOTTOMRIGHT;
import static fop.model.tile.Position.LEFT;
import static fop.model.tile.Position.RIGHT;
import static fop.model.tile.Position.TOP;
import static fop.model.tile.Position.TOPLEFT;
import static fop.model.tile.Position.TOPRIGHT;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import fop.base.Edge;
import fop.base.Node;
import fop.model.graph.FeatureGraph;
import fop.model.graph.FeatureNode;
import fop.model.player.Player;
import fop.model.player.Players;
import fop.model.tile.FeatureType;
import fop.model.tile.Position;
import fop.model.tile.Tile;

public class Gameboard extends Observable<Gameboard> {

	private boolean officalFieldCalculation = true;
	private Tile[][] board;
	private List<Tile> tiles;
	private FeatureGraph graph;
	private Tile newestTile;
	private HashSet<completedCastle> completedCastles;
	private int castleIdCounter;

	public Gameboard() {
		board = new Tile[144][144];
		tiles = new LinkedList<Tile>();
		graph = new FeatureGraph();
		castleIdCounter = 0;
		completedCastles = new HashSet<completedCastle>();
	}

	// kann nicht im konstrukor erfolgen, weil erst observer gesetzt werden muss
	public void initGameboard(Tile t) {
		newTile(t, 72, 72);
	}

	public void newTile(Tile t, int x, int y) {
		t.x = x;
		t.y = y;
		board[x][y] = newestTile = t;
		tiles.add(t);

		connectNodes(x, y);
		push(this); // pushes the new gameboard state to its observers (= GameBoardPanel)
	}

	/**
	 * Connects the nodes of all neighboring tiles facing the tile at given
	 * coordinates x, y. It is assumed that the tile is placed according to the
	 * rules.
	 *
	 * @param x coordinate
	 * @param y coordinate
	 */
	private void connectNodes(int x, int y) {
		graph.addAllNodes(board[x][y].getNodes());
		graph.addAllEdges(board[x][y].getEdges());

		Tile t = board[x][y];

		// TODO 6.1.3
		// Check top tile
		if (board[x][y - 1] != null) {
			Tile topTile = board[x][y - 1];
			graph.addEdge(t.getNode(TOP), topTile.getNode(BOTTOM));

			if (t.getNode(TOP).getType() == ROAD) {
				graph.addEdge(t.getNode(TOPLEFT), topTile.getNode(BOTTOMLEFT));
				graph.addEdge(t.getNode(TOPRIGHT), topTile.getNode(BOTTOMRIGHT));
			}
		}

		// Check left tile
		if (board[x - 1][y] != null) {
			Tile leftTile = board[x - 1][y];
			graph.addEdge(t.getNode(LEFT), leftTile.getNode(RIGHT));

			if (t.getNode(LEFT).getType() == ROAD) {
				graph.addEdge(t.getNode(TOPLEFT), leftTile.getNode(TOPRIGHT));
				graph.addEdge(t.getNode(BOTTOMLEFT), leftTile.getNode(BOTTOMRIGHT));
			}
		}

		// Check right tile
		if (board[x + 1][y] != null) {
			Tile rightTile = board[x + 1][y];
			graph.addEdge(t.getNode(RIGHT), rightTile.getNode(LEFT));

			if (t.getNode(RIGHT).getType() == ROAD) {
				graph.addEdge(t.getNode(TOPRIGHT), rightTile.getNode(TOPLEFT));
				graph.addEdge(t.getNode(BOTTOMRIGHT), rightTile.getNode(BOTTOMLEFT));
			}
		}

		// Check bottom tile
		if (board[x][y + 1] != null) {
			Tile bottomTile = board[x][y + 1];
			graph.addEdge(t.getNode(BOTTOM), bottomTile.getNode(TOP));

			if (t.getNode(BOTTOM).getType() == ROAD) {
				graph.addEdge(t.getNode(BOTTOMLEFT), bottomTile.getNode(TOPLEFT));
				graph.addEdge(t.getNode(BOTTOMRIGHT), bottomTile.getNode(TOPRIGHT));
			}
		}

		// This might be helpful:
		// As we already ensured that the tile on top exists and fits the tile at x, y,
		// we know that if the feature of its top is a ROAD, the feature at the bottom
		// of the tile on top is a ROAD as well. As every ROAD has FIELD nodes as
		// neighbors on both sides, we can connect those nodes of the two tiles. The
		// same logic applies to the next three routines.
	}

	/**
	 * Checks if the given tile could be placed at position x, y on the board
	 * according to the rules.
	 *
	 * @param t The tile
	 * @param x The x position on the board
	 * @param y The y position on the board
	 * @return True if it would be allowed, false if not.
	 */
	public boolean isTileAllowed(Tile t, int x, int y) {

		boolean top = false;
		boolean left = false;
		boolean right = false;
		boolean bottom = false;
		boolean result = false;

		boolean topNull = false;
		boolean leftNull = false;
		boolean rightNull = false;
		boolean bottomNull = false;

		Tile[][] board = this.getBoard();

		// TODO 6.1.1
		// Check top tile
		if (board[x][y - 1] != null) {
			if (t.getNode(TOP).getType() == board[x][y - 1].getNode(BOTTOM).getType()) {
				top = true;
			}
		} else {
			topNull = true;
			top = true;
		}
		// Check left tile
		if (board[x - 1][y] != null) {
			if (t.getNode(LEFT).getType() == board[x - 1][y].getNode(RIGHT).getType()) {
				left = true;
			}
		} else {
			leftNull = true;
			left = true;
		}
		// Check right tile
		if (board[x + 1][y] != null) {
			if (t.getNode(RIGHT).getType() == board[x + 1][y].getNode(LEFT).getType()) {
				right = true;
			}
		} else {
			rightNull = true;
			right = true;
		}
		// Check bottom tile
		if (board[x][y + 1] != null) {
			if (t.getNode(BOTTOM).getType() == board[x][y + 1].getNode(TOP).getType()) {
				bottom = true;
			}
		} else {
			bottomNull = true;
			bottom = true;
		}

		// end result
		if (top && left && right && bottom) {
			if (topNull && leftNull && rightNull && bottomNull) {
				result = false;
			} else {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Checks if the given tile would be allowed anywhere on the board adjacent to
	 * other tiles and according to the rules.
	 *
	 * @param newTile The tile.
	 * @return True if it is allowed to place the tile somewhere on the board, false
	 *         if not.
	 */
	public boolean isTileAllowedAnywhere(Tile newTile) {
		// TODO 6.1.2
		// Iterate over all tiles
		for (int i = 0; i < tiles.size(); i++) {
			Tile currentTile = tiles.get(i);
			int x = currentTile.x;
			int y = currentTile.y;

			// check top
			if (board[x][y - 1] == null) {
				if (isTileAllowed(newTile, x, y - 1)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x, y - 1)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x, y - 1)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x, y - 1)) {
					return true;
				}
				newTile.rotateRight();
			}

			// check left
			if (board[x - 1][y] == null) {
				if (isTileAllowed(newTile, x - 1, y)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x - 1, y)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x - 1, y)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x - 1, y)) {
					return true;
				}
				newTile.rotateRight();
			}

			// check right
			if (board[x + 1][y] == null) {
				if (isTileAllowed(newTile, x + 1, y)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x + 1, y)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x + 1, y)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x + 1, y)) {
					return true;
				}
				newTile.rotateRight();
			}

			// check bottom
			if (board[x][y + 1] == null) {
				if (isTileAllowed(newTile, x, y + 1)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x, y + 1)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x, y + 1)) {
					return true;
				}
				newTile.rotateRight();
				if (isTileAllowed(newTile, x, y + 1)) {
					return true;
				}
				newTile.rotateRight();
			}

		}
		// no valid position was found
		return false;
	}

	/**
	 * Calculates points for monasteries (one point for the monastery and one for
	 * each adjacent tile).
	 *
	 * @param state state of game
	 */
	public void calculateMonasteries(State state) {
		// TODO 6.1.4 a)
		for (int i = 0; i < tiles.size(); i++) {
			Tile currentTile = tiles.get(i);

			// the methods getNode() and getType of class Tile and FeatureNode might be
			// helpful
			FeatureNode centerNode = currentTile.getNode(Position.CENTER);
			if (centerNode == null)
				continue;
			if (centerNode.getType() == FeatureType.MONASTERY && centerNode.hasMeeple()) {
				int score = 0;
				int x = currentTile.x;
				int y = currentTile.y;
				// Check all surrounding tiles and add the points
				for (int j = -1; j <= 1; j++) {
					for (int j2 = -1; j2 <= 1; j2++) {
						// Test auf Brettgröße
						if ((x + j >= 0 && x + j <= 143) || (y + j2 >= 0 && y + j2 <= 143)) {
							if (board[x + j][y + j2] != null) {
								score++;
							}
						}
					}
				}

				// Points are given if the landscape is complete or the game is over
				// Meeples are only returned while game is running and monasterie is surrounded
				// by 8 other tiles
				if (score == 9 && state != State.GAME_OVER) {
					centerNode.getPlayer().addScore(score);
					centerNode.getPlayer().returnMeeple();
					centerNode.setPlayer(null);
				}
				if (state == State.GAME_OVER) {
					centerNode.getPlayer().addScore(score);
				}
			}
			// After adding the points to the overall points of the player, set the score to
			// 1 again
		}
	}

	/**
	 * Calculates points and adds them to the players score, if a feature was
	 * completed. FIELDS are only calculated when the game is over.
	 *
	 * @param state The current game state.
	 */
	public void calculatePoints(State state) {
		// state = State.GAME_OVER;
		calculateMonasteries(state);
		calculatePoints(CASTLE, state);
		calculatePoints(ROAD, state);
		if (state == State.GAME_OVER) {
			calculatePoints(FIELDS, state);
		}
	}

	/**
	 * checks if  mission1 in the game is accomplished
	 *
	 * @return the winning player iff mission is accomplished, else null
	 */
	public Player checkMission1() {
		// TODO 6.3.3
		// mission 1: check if one player has 3 more completed CASTLE the others
		// note maybe move some elements of calculatePoints to a separate method to then
		// check here
		return null;
	}


	/**
	 * checks if  mission2 in the game is accomplished
	 *
	 * @return the winning player iff mission is accomplished, else null
	 */
	public Player checkMission2() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Calculates and adds points to the players that scored a feature. If the given
	 * state is GAME_OVER, points are added to the player with the most meeple on a
	 * subgraph, even if it is not completed.
	 *
	 * @param type  The FeatureType that is supposed to be calculated.
	 * @param state The current game state.
	 */
	public void calculatePoints(FeatureType type, State state) {
		// TODO 6.1.4 b)
		// Liste mit allen Knoten eines types
		List<Node<FeatureType>> nodeList = new ArrayList<>(graph.getNodes(type));

		while (!nodeList.isEmpty()) {
			
			// queue defines the connected graph. If this queue is empty, every node in this
			// graph will be visited.
			// if nodeList is non-empty, insert the next node of nodeList into this queue
			ArrayDeque<Node<FeatureType>> queue = new ArrayDeque<>();
			int score = 0;
			boolean completed = true;
			// Is the feature completed? Is set to false if a node is
			// visited that does not
			// connect to any other tile
			// take first feature node into queue
			queue.push(nodeList.remove(0));
			// Iterate as long as the queue is not empty
			// Remember: queue defines a connected graph
			List<Node<FeatureType>> visitedNodeList = new ArrayList<Node<FeatureType>>();
			while (!queue.isEmpty()) {
				Node<FeatureType> queueNode = queue.getFirst();
				for (Edge<FeatureType> edge : graph.getEdges(queueNode)) {
					Node<FeatureType> connectedNode = edge.getOtherNode(queueNode);
					if (!visitedNodeList.contains(connectedNode)) {
						queue.push(connectedNode);
						nodeList.remove(connectedNode);
					}
				}
				visitedNodeList.add(queueNode);
				queue.remove(queueNode);
			}

			// Hint:
			// If there is one straight positioned node that does not connect to another
			// tile, the feature cannot be completed.

			HashMap<Player, Integer> players = new HashMap<Player, Integer>();
			HashSet<Tile> tiles = new HashSet<Tile>();

			for (Node<FeatureType> n : visitedNodeList) {
				FeatureNode node = (FeatureNode) n;
				// Zählen der Meeple
				if (node.hasMeeple()) {
					Player p = node.getPlayer();
					if (!players.containsKey(p)) {
						players.put(p, 1);
					} else {
						players.put(p, players.get(p) + 1);
					}
				}
				// Test auf Abgeschlossenheit
				if (isNodeOpen(node)) {
					completed = false;
				}
				// Bestimmen aller beteiligten Tiles
				tiles.add(getTileContainingNode(node));
			}

			// Abgeschlossene Städte werden gespeichert, um später offizielle Wiesenwertung
			// zu berechnen
			// (6.3.2) durchzuführen
			if (officalFieldCalculation == true && state == State.GAME_OVER && type == CASTLE
					&& completed == true) {
				completedCastles.add(new completedCastle(visitedNodeList, castleIdCounter));
				castleIdCounter++;
			}

			// Log der Zusammenhangskomponente für Testzwecke
			// logTiles(tiles);

			// Berechnung der maximalen Anzahl an gleichfarbigen Meeple
			int max = 0;
			for (Player p : players.keySet()) {
				if (max < players.get(p)) {
					max = players.get(p);
				}
			}

			// Berechnung der Punkte
			switch (type) {
			case ROAD:
				score = tiles.size();
				break;
			case FIELDS:
				if (officalFieldCalculation == true) {
					score = officialFieldScore(visitedNodeList);
				} else {
					score = tiles.size() / 4;
				}
				break;
			case CASTLE:
				if (completed) {
					score = (tiles.size() + amountCoatOfPennants(tiles)) * 2;
				} else {
					score = tiles.size() + amountCoatOfPennants(tiles);
				}
				break;
			default:
				break;
			}
			// Zuschreiben der Punkte
			if (completed || state == State.GAME_OVER) {
				for (Player p : players.keySet()) {
					if (max == players.get(p)) {
						p.addScore(score);
					}
				}
			}
			if (completed && state != State.GAME_OVER) {
				returnAllMeepels(visitedNodeList);
			}
		}
	}

	/**
	 * Gibt eine Liste zurück, die den Spieler enthält, der die meisten Meeple der überprüften Stadt enthält.
	 * Haben zwei Spieler gleich viele Meeple in der Stadt, enthält die Liste diese beiden Spieler.
	 * Ist die Stadt unbesetzt, so ist die Liste leer.
	 * @param castleList zu überprüfende Stadt in Form einer Liste mit allen Castle-Nodes
	 */
	public List<Player> wichPlayerLeadsTheCastle(List<Node<FeatureType>> castleList) {

		List<Player> listOfCastleLeadingPlayers = new ArrayList<Player>();
		HashMap<Player, Integer> players = new HashMap<Player, Integer>();

		for (Node<FeatureType> n : castleList) {
			FeatureNode node = (FeatureNode) n;
			// Zählen der Meeple
			if (node.hasMeeple()) {
				Player p = node.getPlayer();
				if (!players.containsKey(p)) {
					players.put(p, 1);
				} else {
					players.put(p, players.get(p) + 1);
				}
			}
		}

		// Berechnung der maximalen Anzahl an gleichfarbigen Meeple
		int max = 0;
		for (Player p : players.keySet()) {
			if (max < players.get(p)) {
				max = players.get(p);
			}
		}

		// Bestimmen der Player mit den meisten Meeple in der Stadt
		for (Player p : players.keySet()) {
			if (max == players.get(p)) {
				listOfCastleLeadingPlayers.add(p);
			}
		}
		return listOfCastleLeadingPlayers;
	}

	/**
	 * returns all Meeples on a List of Nodes
	 *
	 * @param visitedNodeList List of Node where Meeples should be removed from
	 */
	private void returnAllMeepels(List<Node<FeatureType>> visitedNodeList) {
		for (Node<FeatureType> n : visitedNodeList) {
			FeatureNode node = (FeatureNode) n;
			if (node.hasMeeple()) {
				Player p = node.getPlayer();
				node.setPlayer(null);
				p.returnMeeple();
			}
		}
	}

	/**
	 * returns amount of pennants on the HashSet of Tiles
	 *
	 * @param tiles Set of Tiles
	 * @return Integer representing the amount of pennants
	 */
	private int amountCoatOfPennants(HashSet<Tile> tiles) {
		int count = 0;
		for (Tile t : tiles) {
			if (t.hasPennant()) {
				count++;
			}
		}
		return count;
	}

	//// Methode zum Testen
	
//	private void logTiles(HashSet<Tile> tiles) {
//		System.out.print("Tile: ");
//		for (Tile t : tiles) {
//			System.out.print("[ " + t.x + ", " + t.y + " ] ");
//		}
//		System.out.println();
//	}

	/**
	 * Returns all Tiles on the Gameboard.
	 *
	 * @return all Tiles on the Gameboard.
	 */
	public List<Tile> getTiles() {
		return tiles;
	}

	/**
	 * Returns the Tile containing the given FeatureNode.
	 *
	 * @param node A FeatureNode.
	 * @return the Tile containing the given FeatureNode.
	 */
	private Tile getTileContainingNode(FeatureNode node) {
		for (Tile t : tiles) {
			if (t.containsNode(node))
				return t;
		}
		return null;
	}

	/**
	 * Returns Position for a given node.
	 *
	 * @param node A FeatureNode
	 * @return Position
	 */
	private Position getNodePositionOnTile(FeatureNode node) {
		Tile t = getTileContainingNode(node);
		return t.getPositionOfNode(node);
	}

	/**
	 * Hilfsmethode zur Bestimmung der zu überprüfenden Nachbar-Tiles
	 *
	 * @param node FeatureNode
	 * @return Vector
	 */
	private Vector getPositionVector(FeatureNode node) {
		Position p = getNodePositionOnTile(node);
		switch (p) {
		case BOTTOM:
			return new Vector(0, 1);
		case LEFT:
			return new Vector(-1, 0);
		case TOP:
			return new Vector(0, -1);
		case RIGHT:
			return new Vector(1, 0);
		case BOTTOMRIGHT:
			return new Vector(1, 1);
		case BOTTOMLEFT:
			return new Vector(-1, 1);
		case TOPRIGHT:
			return new Vector(1, -1);
		case TOPLEFT:
			return new Vector(-1, -1);
		default:
			return null;
		}
	}

	/**
	 * Testet ob Node an einer freien Kante eines Tiles liegt. In diesem Fall ist
	 * die zugehörige Straße/Wiese/Stadt nicht abgeschlossen.
	 *
	 * @param node FeatureNode
	 * @return true wenn nicht abgeschlossen
	 */
	private boolean isNodeOpen(FeatureNode node) {
		Tile t = getTileContainingNode(node);
		Vector v = getPositionVector(node);
		// Testet ob in Richtung des Vektors ein Tile existiert
		if (v.x != 0) {
			if (board[t.x + v.x][t.y] == null) {
				return true;
			}
		}
		if (v.y != 0) {
			if (board[t.x][t.y + v.y] == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the spots on the most recently placed tile on which it is allowed to
	 * place a meeple.
	 *
	 * @return The spots on which it is allowed to place a meeple as a boolean array
	 *         representing the tile split in nine cells from top left, to right, to
	 *         bottom right. If there is no spot available at all, returns null.
	 */
	public boolean[] getMeepleSpots() {
		boolean[] positions = new boolean[9];
		boolean anySpot = false; // if there is not a single spot, this remains false

		for (Position p : Position.values()) {
			FeatureNode n = newestTile.getNodeAtPosition(p);
			if (n != null)
				if (n.hasMeepleSpot() && !hasMeepleOnSubGraph(n))
					positions[p.ordinal()] = anySpot = true;
		}

		if (anySpot)
			return positions;
		else
			return null;
	}

	/**
	 * Checks if there are any meeple on the subgraph that FeatureNode n is a part
	 * of.
	 *
	 * @param n The FeatureNode to be checked.
	 * @return True if the given FeatureNode has any meeple on its subgraph, false
	 *         if not.
	 */
	private boolean hasMeepleOnSubGraph(FeatureNode n) {
		List<Node<FeatureType>> visitedNodes = new ArrayList<>();
		ArrayDeque<Node<FeatureType>> queue = new ArrayDeque<>();

		queue.push(n);
		while (!queue.isEmpty()) {
			FeatureNode node = (FeatureNode) queue.pop();
			if (node.hasMeeple())
				return true;

			List<Edge<FeatureType>> edges = graph.getEdges(node);
			for (Edge<FeatureType> edge : edges) {
				Node<FeatureType> nextNode = edge.getOtherNode(node);
				if (!visitedNodes.contains(nextNode)) {
					queue.push(nextNode);
					visitedNodes.add(nextNode);
				}
			}
		}
		return false;
	}

	/**
	 * Returns the newest tile.
	 *
	 * @return the newest tile.
	 */
	public Tile getNewestTile() {
		return newestTile;
	}

	/**
	 * Places a meeple of given player at given position on the most recently placed
	 * tile (it is only allowed to place meeple on the most recent tile).
	 *
	 * @param position The position the meeple is supposed to be placed on on the
	 *                 tile (separated in a 3x3 grid).
	 * @param player   The owner of the meeple.
	 */
	public void placeMeeple(Position position, Player player) {
		board[newestTile.x][newestTile.y].getNode(position).setPlayer(player);
		player.removeMeeple();
	}

	public Tile[][] getBoard() {
		return board;
	}

	public FeatureGraph getGraph() {
		return this.graph;
	}

	public void setFeatureGraph(FeatureGraph graph) {
		this.graph = graph;
	}

	/**
	 * Calculates points for Fields (three points for each completed castle adjacent
	 * to the Field)
	 *
	 * @param fieldNodeList
	 * @return
	 *
	 */
	private int officialFieldScore(List<Node<FeatureType>> fieldNodeList) {

		// TODO 6.3.2
		int score = 0;
		HashSet<Integer> alreadyScoredCastleIds = new HashSet<Integer>();
		for (Node<FeatureType> fieldNode : fieldNodeList) {
			for (Node<FeatureType> castleNode : getAllNearbyCastleNodes(fieldNode)) {
				for (completedCastle cc : completedCastles) {
					if (cc.getCastleNodes().contains(castleNode)) {
						if (!alreadyScoredCastleIds.contains(cc.getId())) {
							alreadyScoredCastleIds.add(cc.getId());
							score = score + 3;
						}
					}
				}
			}
		}
		return score;

	}

	private List<Node<FeatureType>> getAllNearbyCastleNodes(Node<FeatureType> fieldNode) {
		List<Node<FeatureType>> returnList = new ArrayList<Node<FeatureType>>();
		Tile t = getTileContainingNode((FeatureNode) fieldNode);
		Position p = getNodePositionOnTile((FeatureNode) fieldNode);
		List<Position> possibleCastlePositions = getPossibleCastlePositions(p);
		for (Position castlePosition : possibleCastlePositions) {
			FeatureNode castleNode = t.getNode(castlePosition);
			if (castleNode.getType() == CASTLE) {
				returnList.add(castleNode);
			}
		}
		return returnList;
	}

	private List<Position> getPossibleCastlePositions(Position p) {
		switch (p) {
		case BOTTOM:
		case TOP:
			return Arrays.asList(LEFT, RIGHT);
		case RIGHT:
		case LEFT:
			return Arrays.asList(TOP, BOTTOM);
		case BOTTOMRIGHT:
			return Arrays.asList(BOTTOM, RIGHT);
		case BOTTOMLEFT:
			return Arrays.asList(BOTTOM, LEFT);
		case TOPRIGHT:
			return Arrays.asList(TOP, RIGHT);
		case TOPLEFT:
			return Arrays.asList(TOP, LEFT);
		default:
			return null;

		}
	}


}
