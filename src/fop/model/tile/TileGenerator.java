package fop.model.tile;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fop.base.Edge;
import fop.model.graph.FeatureNode;
import fop.model.interfaces.GameConstants;

public class TileGenerator {

	private List<Tile> tiles;
	Tile startTile;
	URL tileTypesURL;
	DocumentBuilderFactory documentBuilderFactory;
	DocumentBuilder documentBuilder;
	Document document;

	public TileGenerator() {

		tiles = new LinkedList<Tile>();

		tileTypesURL = getClass().getResource(GameConstants.TILETYPES_FILE_LOCATION);
		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(tileTypesURL.openStream());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.getDocumentElement().normalize();

		NodeList tnl = document.getElementsByTagName("tile");

		for (int i = 0; i < tnl.getLength(); i++) {

			Node tile = tnl.item(i);
			NamedNodeMap tileAttributes = tile.getAttributes();

			// Depending on the ammount of tiles of a type, create the tile x times
			for (int j = 0; j < Integer.valueOf(tileAttributes.getNamedItem("ammount").getNodeValue()); j++) {

				Tile newTile;

				// If the tile has a pennant, pass that to the constructor
				if (tileAttributes.getNamedItem("pennant") != null
						&& tileAttributes.getNamedItem("pennant").getNodeValue().equals("true"))
					newTile = new Tile(TileType.valueOf(tileAttributes.getNamedItem("type").getNodeValue()), true);
				else
					newTile = new Tile(TileType.valueOf(tileAttributes.getNamedItem("type").getNodeValue()));

				NodeList nnl = ((Element) tile).getElementsByTagName("node");

				// Add every node
				for (int k = 0; k < nnl.getLength(); k++) {
					Node node = nnl.item(k);
					NamedNodeMap nodeAttributes = node.getAttributes();

					FeatureNode fn = new FeatureNode(
							FeatureType.valueOf(nodeAttributes.getNamedItem("feature").getNodeValue()));

					if (nodeAttributes.getNamedItem("meeplespot") != null
							&& nodeAttributes.getNamedItem("meeplespot").getNodeValue().equals("true"))
						fn.setMeepleSpot();

					newTile.addNode(Position.valueOf(nodeAttributes.getNamedItem("position").getNodeValue()), fn);

				}

				NodeList enl = ((Element) tile).getElementsByTagName("edge");

				// Add every edge
				for (int k = 0; k < enl.getLength(); k++) {

					Node node = enl.item(k);
					NamedNodeMap nodeAttributes = node.getAttributes();
					newTile.addEdge(new Edge<FeatureType>(
							newTile.getNode(Position.valueOf(nodeAttributes.getNamedItem("a").getNodeValue())),
							newTile.getNode(Position.valueOf(nodeAttributes.getNamedItem("b").getNodeValue()))));
				}

				// If there is no start tile yet, save it seperately
				if (startTile == null && newTile.getType().equals(GameConstants.START_TILE_TYPE))
					startTile = newTile;
				else
					tiles.add(newTile);
			}

		}

	}
	
	public Tile getInitTile(TileType type) {
		
		return tiles.stream().filter(x -> x.getType().equals(type)).findFirst().orElse(null); 
	}

	/**
	 * returns the list of Tiles
	 * 
	 * @return
	 */
	public List<Tile> getTiles() {
		return tiles;
	}
	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	/**
	 * returns the start tile
	 * 
	 * @return
	 */
	public Tile getStartTile() {
		return startTile;
	}
}
