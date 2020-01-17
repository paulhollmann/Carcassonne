package fop;

import fop.view.components.gui.Resources;
import fop.view.gui.GameWindow;

/**
 * Starts the game
 *
 */
public class Carcassonne {

	public static void main(String[] args) {
		Resources resources;
		resources = Resources.getInstance();
		if (!resources.load())
			return;
		Runtime.getRuntime().addShutdownHook(new Thread(resources::save));
		new GameWindow(resources);
	}

}
