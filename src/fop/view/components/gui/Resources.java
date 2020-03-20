package fop.view.components.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import fop.model.interfaces.GameConstants;
import fop.model.player.ScoreEntry;

public class Resources implements GameConstants {

	private static Resources instance;
	private Font celticFont;
	private List<ScoreEntry> scoreEntries;

	private boolean resourcesLoaded;

	/**
	 * private constructor, normally called once
	 */
	private Resources() {
		this.resourcesLoaded = false;
	}

	/**
	 * returns the instance of the resource manager or creates a new one
	 *
	 * @return resources
	 */
	public static Resources getInstance() {
		if (instance == null) {
			instance = new Resources();
			instance.load();
		}
		return instance;
	}

	/**
	 * loads all resources
	 *
	 * @return true, if all resources were loaded successfully
	 */
	public boolean load() {
		if (resourcesLoaded)
			return true;

		try {
			// Load font
			celticFont = loadFont("celtic.ttf");
			// Load score entries
			loadScoreEntries();
			resourcesLoaded = true;
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Konnte Resourcen nicht laden: " + ex.getMessage(),
					"Fehler beim Laden der Resourcen", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * saves determined resources, for now only the Highscore table
	 *
	 * @return true, if saved successfully
	 */
	public boolean save() {
		try {
			saveScoreEntries();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * please translate
	 *
	 * Diese Methode speichert alle Objekte des Typs {@link ScoreEntry} in der
	 * Textdatei "highscores.txt" Jede Zeile stellt dabei einen ScoreEntry dar.
	 * Sollten Probleme auftreten, muss eine {@link IOException} geworfen werden.
	 * Die Einträge sind in der Liste {@link #scoreEntries} zu finden.
	 *
	 * @see ScoreEntry#write(PrintWriter)
	 * @throws IOException Eine IOException wird geworfen, wenn Probleme beim
	 *                     Schreiben auftreten.
	 */
	private void saveScoreEntries() throws IOException {
		// TODO
		PrintWriter pw = new PrintWriter(HIGHSCORE_FILE);
		for (ScoreEntry se : scoreEntries) {
			se.write(pw);
			pw.write("\n");
		}
		pw.close();
	}

	/**
	 * please translate
	 *
	 * Lädt den Highscore-Table aus der Datei "highscores.txt". Dabei wird die Liste
	 * {@link #scoreEntries} neu erzeugt und befüllt. Beachte dabei, dass die Liste
	 * nach dem Einlesen absteigend nach den Punktzahlen sortiert sein muss. Sollte
	 * eine Exception auftreten, kann diese ausgegeben werden, sie sollte aber nicht
	 * weitergegeben werden, da sonst das Laden der restlichen Resourcen abgebrochen
	 * wird ({@link #load()}).
	 *
	 * @throws IOException wenn irgentetwas schief läuft
	 *
	 * @see ScoreEntry#read(String)
	 * @see #addScoreEntry(ScoreEntry)
	 */

	private void loadScoreEntries() throws IOException {
		scoreEntries = new ArrayList<ScoreEntry>();
		BufferedReader br = new BufferedReader(new FileReader(HIGHSCORE_FILE));
		String line = "";
		while ((line = br.readLine()) != null) {
			ScoreEntry se = ScoreEntry.read(line);
			if (se != null)
				scoreEntries.add(se);
		}
		br.close();
		scoreEntries.sort((s1, s2) -> s2.getScore() - s1.getScore());
	}

	/**
	 * please translate
	 *
	 * Fängt ein {@link ScoreEntry}-Objekt der Liste von Einträgen hinzu. Beachte:
	 * Nach dem Einfügen muss die Liste nach den Punktzahlen absteigend sortiert
	 * bleiben.
	 *
	 * @param scoreEntry Der einzufügende Eintrag
	 * @see ScoreEntry#compareTo(ScoreEntry)
	 */

	public void addScoreEntry(ScoreEntry scoreEntry) {
		if (scoreEntry != null)
			scoreEntries.add(scoreEntry);
		scoreEntries.sort((s1, s2) -> s2.getScore() - s1.getScore());
	}

	/**
	 * deletes the file and the data
	 * @throws IOException if FILE can not be loaded
	 */
	public void clearEntries() throws IOException {
		PrintWriter pw = new PrintWriter(HIGHSCORE_FILE);
		pw.print("");
		pw.close();
		loadScoreEntries();
	}


	/**
	 * @return a list of all entries
	 */
	public List<ScoreEntry> getScoreEntries() {
		return scoreEntries;
	}

	/**
	 * @param name file path
	 * @return the loaded font
	 * @throws IOException if loading problem
	 * @throws FontFormatException if wrong format
	 */
	private Font loadFont(String name) throws IOException, FontFormatException {
		InputStream is = Resources.class.getClassLoader().getResourceAsStream(name);
		Font f = Font.createFont(Font.TRUETYPE_FONT, is);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(f);
		return f.deriveFont(20f);
	}

	/**
	 * @return the font
	 */
	public Font getCelticFont() {
		return this.celticFont;
	}
}