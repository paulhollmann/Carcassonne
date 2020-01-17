package fop.model.graph;

import fop.base.Graph;
import fop.model.tile.FeatureType;

public class FeatureGraph extends Graph<FeatureType> {

	/**
	 * adds an Edge between NodeA and node B
	 * @param nodeA
	 * @param nodeB
	 */
	public void addEdge(FeatureNode nodeA, FeatureNode nodeB) {
		nodeA.setConnectsTiles();
		nodeB.setConnectsTiles();
		super.addEdge(nodeA, nodeB);
	}

}
