package fop.model.interfaces;

import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import fop.model.player.Player;
import fop.model.player.ScoreEntry;
import fop.view.components.gui.Resources;

public interface MessagesConstants {

	/**
	 * which player has to place a tile
	 *
	 * @param name
	 * @return
	 */
	public static String playerPlacingTile(String name) {
		return "Player " + name + ", please place a tile.";
	}

	/**
	 * which player has to place a meeple
	 *
	 * @param name
	 * @return
	 */
	public static String playerPlacingMeeple(String name) {
		return "Player " + name + ", please place a meeple or skip (right mouse button)";
	}

	/**
	 * if player does not have a placing option
	 *
	 * @param name
	 * @return
	 */
	public static String playerNoPlacingMeepleOption(String name) {
		return "There was no option for " + name + " to place a meeple. Going to next round.";
	}

	/**
	 * creates the winner message with the list of winners
	 *
	 * @param winners all winners
	 */
	public static void showWinner(List<Player> winners) {
		if (winners.size() == 1) {
			JOptionPane.showMessageDialog(null, "Der Gewinner ist " + winners.get(0).getName(), null,
					JOptionPane.CLOSED_OPTION);
		} else {
			StringBuilder strg = new StringBuilder();
			winners.stream().forEach(x -> {
				strg.append(x.getName() + ", ");
			});
			String nameofwinner = strg.toString();
			nameofwinner = nameofwinner.substring(0, nameofwinner.length() - 2);
			JOptionPane.showMessageDialog(null, "Die Gewinner sind " + nameofwinner, null, JOptionPane.CLOSED_OPTION);

		}
	}

	/**
	 * creates the winner message with the mission winner
	 *
	 * @param winner  the player that completed the mission, and therefore has won
	 *                the game
	 * @param mission the completed number of the mission that was completed
	 */
	public static void showMissionWinner(Player winner, int mission) {
		JOptionPane.showMessageDialog(null, "Mission " + mission + " geschafft: Der Gewinner ist " + winner.getName(),
				null, JOptionPane.CLOSED_OPTION);
	}

	/**
	 * Generates a string listing all winners and their score.
	 *
	 * @param winners all winners
	 *
	 * @return A string listing all winners and their score.
	 */

	public static String getWinnersMessage(List<Player> winners) {
		String message = "Game over! ";
		Iterator<Player> i = winners.iterator();
		StringBuilder strg = new StringBuilder();
		while (i.hasNext()) {
			Player p = i.next();
			message += p.getName();
			if (i.hasNext())
				message += " and ";
			else
				message += " won, scoring " + p.getScore() + " points.";

			strg.append(p.getName() + ", ");
			ScoreEntry scoreEntry = new ScoreEntry(p);
			Resources.getInstance().addScoreEntry(scoreEntry);
		}

		return message;
	}

	/**
	 * Generates a string listing the mission winner and the mission number.
	 *
	 * @param winner  the player that completed the mission, and therefore has won
	 *                the game
	 * @param mission the completed number of the mission that was completed
	 *
	 * @return A string listing the mission winner and the mission number.
	 */
	public static String getMissionWinnerMessage(Player winner, int mission) {
		String message = "Game over! ";
		message += winner.getName() + " won, by completing Mission " + mission;

		ScoreEntry scoreEntry = new ScoreEntry(winner);
		Resources.getInstance().addScoreEntry(scoreEntry);

		return message;
	}

	/**
	 * returns if highscore should be deleted
	 *
	 * @return
	 */
	public static int deleteHighScore() {
		return JOptionPane.showConfirmDialog(null, "Should all entries be deleted?", "Deleting highscores",
				JOptionPane.YES_NO_OPTION);
	}

	/**
	 * shows if all is deleted
	 */
	public static void sucessFullDeleted() {
		JOptionPane.showMessageDialog(null, "Entries are deleted successfully", "Deleted", JOptionPane.OK_OPTION);
	}
}
