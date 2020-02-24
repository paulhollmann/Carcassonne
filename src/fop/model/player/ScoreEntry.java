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
	 * @param name
	 * @param score
	 * @param date
	 */
	public ScoreEntry(String name, int score, Date date) {
		this.name = name;
		this.score = score;
		this.date = date;
	}

	/**
	 * creates a score entry via player
	 *
	 * @param player
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
	 * prints a new highscore entry
	 *
	 * @param printWriter
	 */
	public void write(PrintWriter printWriter) {
		// TODO
	}

	/**
	 * reads a score entry and checks if it is allowed
	 *
	 * @param line
	 * @return
	 */
	public static ScoreEntry read(String line) {
		// TODO

		try {
			String[] data = line.split(";");
			for (Player player : Players.getPLayers()) {
				if (player.getName().equals(data[0]))
					if (Long.parseLong(data[1]) >= 0)
						if (Integer.parseInt(data[2]) >= 0)
							return new ScoreEntry(data[0], Integer.parseInt(data[2]),
									new Date(Integer.parseInt(data[1])));
			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	/**
	 * returns the current Date
	 *
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * returns the name
	 *
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * returns the score
	 *
	 * @return
	 */
	public int getScore() {
		return this.score;
	}

}