package fop.model.gameplay;

import java.util.List;

import fop.base.Node;
import fop.model.tile.FeatureType;

public class completedCastle {

	private List<Node<FeatureType>> castleNodes;
	private int id;

	public completedCastle(List<Node<FeatureType>> l, int i) {
		castleNodes = l;
		id = i;
	}

	public List<Node<FeatureType>> getCastleNodes() {
		return castleNodes;
	}

	public void setCastleNodes(List<Node<FeatureType>> castleNodes) {
		this.castleNodes = castleNodes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof completedCastle)) {
			return false;
		}

		completedCastle completedCastle = (completedCastle) o;

		return (this.id == completedCastle.getId());
	}
}
