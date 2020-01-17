package fop.model.interfaces;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JOptionPane;

import fop.Carcassonne;
import fop.view.components.gui.Resources;
import fop.view.gui.GameWindow;

public interface GameMethods {
	/**
	 * Goes straight to the Main Menu
	 */
	public static void GoToMainMenu() {
		for (Window win : GameWindow.getWindows()) {
			win.dispose();
		}
		Carcassonne.main(null);
	}
	/**
	 * deletes the highscore entries 
	 * @param message
	 */
	public static void deleteHighScoreEntries(int message) {
		if (message == JOptionPane.YES_OPTION) {
			Resources ressources = Resources.getInstance();
			try {
				ressources.clearEntries();
				MessagesConstants.sucessFullDeleted();
				GoToMainMenu();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}


	






}
