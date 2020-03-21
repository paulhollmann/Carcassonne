package fop.model.player;

import java.io.PrintWriter;
import java.util.Date;

public class ScoreEntry implements Comparable<ScoreEntry> {

	private String name;
	private Date date;
	private int score;

	/**
	 * creates a score entry
	 *
	 * @param name the name of the player
	 * @param score the score of the player
	 * @param date the date of the game
	 */
	public ScoreEntry(String name, int score, Date date) {
		this.name = name;
		this.score = score;
		this.date = date;
	}

	/**
	 * creates a score entry via player
	 *
	 * @param player the player thats score should be saved
	 */
	public ScoreEntry(Player player) {
		this.name = player.getName();
		this.score = player.getScore();
		this.date = new Date();
	}

	/**
	 * compares the score
	 */
	@Override
	public int compareTo(ScoreEntry scoreEntry) {
		return Integer.compare(this.score, scoreEntry.score);
	}

	/**
	 * prints a new high score entry
	 *
	 * @param printWriter the printWriter used to print
	 */
	public void write(PrintWriter printWriter) {
		// TODO
		printWriter.append(this.getName() + ";" + this.getDate().getTime() + ";" + this.getScore());
	}

	/**
	 * reads a score entry and checks if it is allowed
	 * @param line the file line to be evaluated
	 * @return a new ScoreEntry iff valid else null
	 */
	public static ScoreEntry read(String line) {
		// TODO
		try {
			String[] data = line.split(";");
			if (!data[0].isEmpty())
				if (Long.parseLong(data[1]) >= 0)
					if (Integer.parseInt(data[2]) >= 0)
						return new ScoreEntry(data[0], Integer.parseInt(data[2]), new Date(Long.parseLong(data[1])));
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * returns the current Date
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * returns the name
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * returns the score
	 *
	 * @return the score
	 */
	public int getScore() {
		return this.score;
	}

}