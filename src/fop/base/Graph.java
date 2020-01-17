package fop.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Diese Klasse representiert einen generischen Graphen mit einer Liste aus
 * Knoten und Kanten.
 *
 * @param <T> Die zugrundeliegende Datenstruktur, beispielsweise
 *            {@link game.map.Castle}
 */

/**
 * This class represents a graph from type <T> This graph is created by two
 * lists. first list of edges second list of nodes
 *
 * @param <T>
 */
public class Graph<T> {

	private List<Edge<T>> edges;
	private List<Node<T>> nodes;

	/**
	 * Creates an empty graph
	 */
	public Graph() {
		this.nodes = new ArrayList<>();
		this.edges = new LinkedList<>();
	}

	/**
	 * gets a collection of nodes which will be added into the graph
	 * 
	 * @param nodes
	 */
	public void addAllNodes(Collection<? extends Node<T>> nodes) {
		this.nodes.addAll(nodes);
	}

	/**
	 * gets a collection of edges which will be added into the graph
	 * 
	 * @param edges
	 */
	public void addAllEdges(Collection<Edge<T>> edges) {
		this.edges.addAll(edges);
	}

	/**
	 * returns the edge of 2 nodes if exists. 
	 * else returns null
	 * @param nodeA
	 * @param nodeB
	 * @return edge 
	 */
	private Edge<T> getEdge(Node<T> nodeA, Node<T> nodeB) {
		return this.edges.stream().filter(edge -> edge.hasEdge(nodeA, nodeB)).findFirst().orElse(null);
	}

	/**
	 * if an edge between nodeA and nodeB is not available, than an edge will be
	 * created and added to the list
	 * 
	 * @param nodeA
	 * @param nodeB
	 */
	public void addEdge(Node<T> nodeA, Node<T> nodeB) {
		Edge<T> edge = getEdge(nodeA, nodeB);
		if (edge == null) {
			this.edges.add(new Edge<>(nodeA, nodeB));
		}
	}

	/**
	 * gets an node and returns all available edges
	 * 
	 * @param node
	 * @return List<Edge>
	 */
	public List<Edge<T>> getEdges(Node<T> node) {
		return this.edges.stream().filter(edge -> edge.contains(node)).collect(Collectors.toList());
	}


	/**
	 * Returns all nodes with inputed value
	 * 
	 * @param value
	 * @return List<Node<T>>
	 */
	public List<Node<T>> getNodes(T value) {
		return this.nodes.stream().filter(n -> n.getValue() == value).collect(Collectors.toList());
	}

}
