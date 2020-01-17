package fop.view.components.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import fop.controller.GameController;
import fop.model.interfaces.GameViewMethods;
import fop.view.components.GameBoardPanel;
import fop.view.components.ToolbarPanel;
import fop.view.components.tile.TileStackPanel;

public class GameView extends JPanel implements GameViewMethods {

	private GameBoardPanel gameBoardPanel;
	private JPanel gameBoardPanelWrapper;
	private ToolbarPanel toolbarPanel;
	private TileStackPanel tileStackPanel;
	private JTextArea statusbarPanel;

	public GameView(GameController gc) {
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1200, 900));
		
		// Toolbar at the Top
		toolbarPanel = new ToolbarPanel(gc.getPlayers());
		this.add(toolbarPanel, BorderLayout.NORTH);

		// Tile stack on the Right 
		tileStackPanel = new TileStackPanel();
		this.add(tileStackPanel, BorderLayout.EAST);

		// game board (the wrapper is needed to be able to drag the board around. it can
		// be understood as a window that you look through onto the gameboard, only
		// seeing part of it)
		gameBoardPanel = new GameBoardPanel(gc);
		gameBoardPanelWrapper = new JPanel();
		gameBoardPanelWrapper.setLayout(null);

		gameBoardPanelWrapper.add(gameBoardPanel);
		this.add(gameBoardPanelWrapper);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// There is a total of 72 tiles so if they were – in a worst case – placed
				// in
				// one horizontal or vertical line starting from the center it could reach from
				// -72 to 0 or from 0 to 72 (either in x or y direction). Each tile is 100 pixel
				// in width and height, so we set the width and height of the board panel to 72
				// * 2 * 100 and move it relative the the wrappers width and height, so the
				// board is centered.
				gameBoardPanel.setBounds(-72 * 100 + 50 + gameBoardPanelWrapper.getWidth() / 2,
						-72 * 100 + gameBoardPanelWrapper.getHeight() / 2, 72 * 2 * 100, 72 * 2 * 100);

			}
		});


		// bottom status bar
		statusbarPanel = new JTextArea();
		statusbarPanel.setEditable(false);
		this.add(statusbarPanel, BorderLayout.SOUTH);
	}

	@Override
	public GameBoardPanel getGameBoardPanel() {
		return gameBoardPanel;
	}
	@Override
	public TileStackPanel getTileStackPanel() {
		return tileStackPanel;
	}
	@Override
	public ToolbarPanel getToolbarPanel() {
		return toolbarPanel;
	}
	@Override
	public void setStatusbarPanel(String status, Color color) {
		statusbarPanel.setForeground(color);
		statusbarPanel.setText(status);
	}
}
