package fop.base;

/**
 * This class represents an edge for type <T>
 * @param <T>
 */
public class Edge<T> {

    private Node<T> nodeA, nodeB;

    /**
     * creates a new edge between nodeA and nodeB for type <T> 
     * @param nodeA
     * @param nodeB
     */
    public Edge(Node<T> nodeA, Node<T> nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    /**
     * Returns true if the inputed nodes do have an edge 
     * @param nodeA
     * @param nodeB
     * @return boolean
     */
    public boolean hasEdge(Node<T> nodeA, Node<T> nodeB){
    	return this.nodeA==nodeA && this.nodeB==nodeB; 
    }
    
    /**
     * This method checks if the inputed node is in the edge.
     * returns true if the given node is type of the edge 
     * @param node
     * @return boolean
     */
    public boolean contains(Node<T> node) {
        return nodeA == node || nodeB == node;
    }

    /**
     * This method returns the first node 
     * @return node<T> 
     */
    public Node<T> getNodeA() {
        return nodeA;
    }

    /**
     * This method returns the seconde node 
     * @return node<T> 
     */
    public Node<T> getNodeB() {
        return nodeB;
    }
    
    /**
     * this method returns the other node from an edge from the inputed node 
     * if the node does not have an edge: return null 
     * @param source
     * @return Node<T>
     */
    public Node<T> getOtherNode(Node<T> source) {
    	if(contains(source))
        return (nodeA == source ? nodeB : nodeA);
    	return null; 
    }
}
