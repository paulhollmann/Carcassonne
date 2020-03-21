package fop.view.gui;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fop.model.interfaces.GameMethods;
import fop.model.interfaces.MessagesConstants;
import fop.model.player.ScoreEntry;
import fop.view.components.View;
import fop.view.components.gui.Resources;

/**
 * HighScore Area
 *
 */
public class HighscoreView extends View {
	private JButton btnBack;
	private JButton btnClear;
	private JTable scoreTable;
	private JLabel lblTitle;
	private JScrollPane scrollPane;

	public HighscoreView(GameWindow gameWindow) {
		super(gameWindow);
	}

	@Override
	public void onResize() {
		int offsetY = 25;
		lblTitle.setLocation((getWidth() - lblTitle.getWidth()) / 2, offsetY);
		offsetY += lblTitle.getSize().height + 25;
		scrollPane.setLocation(25, offsetY);
		scrollPane.setSize(getWidth() - 50, getHeight() - 50 - BUTTON_SIZE.height - offsetY);

		btnBack.setLocation((getWidth() / 3) - (BUTTON_SIZE.width / 2), getHeight() - BUTTON_SIZE.height - 25);
		btnClear.setLocation((2 * (getWidth() / 3) - (BUTTON_SIZE.width / 2)), getHeight() - BUTTON_SIZE.height - 25);
	}

	@Override
	protected void onInit() {
		btnBack = createButton("Back");
		btnClear = createButton("Delete");
		lblTitle = createLabel("Highscores", 45, true);
		// TODO
		Resources resources = Resources.getInstance();
		String[][] rowData = new String[resources.getScoreEntries().size()][3];
		LinkedList<ScoreEntry> localScores = new LinkedList<>(resources.getScoreEntries());
		int i = 0;
		for (ScoreEntry se : localScores) {
			rowData[i][0] =  new SimpleDateFormat("(zzz) EEE dd MMM yyyy HH:mm:ss").format(se.getDate());
			rowData[i][1] = se.getName();
			rowData[i][2] = Integer.toString(se.getScore());
			i++;
		}

		Object[] headerData = new Object[] { "Date", "Name", "Score" };
		scoreTable = new JTable(rowData, headerData);
		scoreTable.setAutoCreateRowSorter(true);
		scoreTable.setDefaultEditor(Object.class, null);
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(scoreTable.getModel());
		scoreTable.setRowSorter(sorter);
		sorter.setComparator(0, (s1, s2) -> {
			try {
				Date d1 = new SimpleDateFormat("(zzz) EEE dd MMM yyyy HH:mm:ss").parse(s1.toString());
				Date d2 = new SimpleDateFormat("(zzz) EEE dd MMM yyyy HH:mm:ss").parse(s2.toString());
				return (int) (d1.getTime() - d2.getTime());
			} catch (Exception e) {
				return 0;
			}
		});
		sorter.sort();
		scrollPane = new JScrollPane(scoreTable);
		add(scrollPane);

	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(btnBack)) {
			GameMethods.GoToMainMenu();

		} else {
			int message = MessagesConstants.deleteHighScore();
			GameMethods.deleteHighScoreEntries(message);
		}
	}
}
