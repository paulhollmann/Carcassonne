package fop.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fop.controller.GameController;
import fop.model.gameplay.State;
import fop.model.interfaces.GameConstants;
import fop.model.interfaces.GameMethods;
import fop.model.player.MeepleColor;
import fop.model.player.Player;
import fop.model.player.Players;
import fop.view.components.View;
import fop.view.components.gui.ColorChooserComboBox;
import fop.view.components.gui.NumberChooser;
import fop.model.interfaces.*;


/**
 * StartGame Area, sets all the necessary data for the game
 *
 */
public class StartGame extends View implements GameConstants{

	private JLabel lblTitle;
	private JLabel lblPlayerCount;

	private NumberChooser playerCount;
	
	private JComponent[][] playerConfig;
	private JButton btnStart, btnBack;
	private GameWindow gameWindow;
	private GameController gc; 
	public StartGame(GameWindow gameWindow, GameController gc) {
		
		super(gameWindow);
		this.gameWindow = gameWindow;
		this.gc = gc;
		Players.resetPlayers();
		
	}

	@Override
	public void onResize() {
		
		int offsetY = 25;
		int offsetX = 25;

		lblTitle.setLocation((this.getWidth()-lblTitle.getWidth()-25) /2, offsetY);
		offsetY += 50;

		int columnWidth = Math.max(300, (getWidth() - 75) / 2);

		// Column 1
		offsetX = (getWidth() - 2 * columnWidth - 25) / 2 + (columnWidth - 350) / 2;
		lblPlayerCount.setLocation((this.getWidth()-lblPlayerCount.getWidth()-25) /2 - lblPlayerCount.getWidth()/2, offsetY + 2);
		playerCount.setLocation((this.getWidth()-playerCount.getWidth()-25)/2 + lblPlayerCount.getWidth()/2 + 10, offsetY);
		offsetY += 50;

		for (int i = 0; i < GameConstants.MAX_PLAYERS; i++) {
			
			int tempOffsetX = (this.getWidth()-300)/2;
			for (JComponent c : playerConfig[i]) {	
				c.setLocation(tempOffsetX, offsetY);
				tempOffsetX +=  c.getWidth() + 10;
				c.setEnabled(i < playerCount.getValue());
			}

			offsetY += 40;
		}


		// Button bar
		offsetY = this.getHeight() - BUTTON_SIZE.height - 25;
		offsetX = (this.getWidth() - 2 * BUTTON_SIZE.width - 25) / 2;
		btnBack.setLocation(offsetX, offsetY);
		btnStart.setLocation(offsetX + BUTTON_SIZE.width + 25, offsetY);
	}

	@Override
	protected void onInit() {

		// Title
		lblTitle = createLabel("New Game", 25, true);

		// Player Count
		lblPlayerCount = createLabel("Number of Players:", 16);
		playerCount = new NumberChooser();
		playerCount.setSize(125, 25);
		playerCount.addValueListener((oldValue, newValue) -> onResize());
		add(playerCount);

		playerConfig = new JComponent[GameConstants.MAX_PLAYERS][];
		
		for (int i = 0; i < GameConstants.MAX_PLAYERS; i++) {
			playerConfig[i] = new JComponent[] { createLabel(String.format("%d.", i + 1), 16),
					new ColorChooserComboBox().getComboBox(),
					new JTextField(String.format("Player %d", i + 1)) };

			playerConfig[i][1].setSize(50, 25);
		
			playerConfig[i][2].setSize(200, 25);

			for (JComponent c : playerConfig[i])
				add(c);
		}
		
		
		
		// Buttons
		btnBack = createButton("Back");
		btnStart = createButton("Start");

		getWindow().setSize(750, 450);
		getWindow().setMinimumSize(new Dimension(750, 450));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		
		if (actionEvent.getSource() == btnBack) {
			GameMethods.GoToMainMenu();
		}
			
		else if (actionEvent.getSource() == btnStart) {

			try {

				// Check Inputs
				int playerCount = this.playerCount.getValue();
				
				// Create Players
				for (int i = 0; i < playerCount; i++) {
					String name = ((JTextField) playerConfig[i][2]).getText().replaceAll(";", "").trim();
					if (name.isEmpty()) {
						
						showErrorMessage(
								String.format("Please indicate a valid name for a player.", i + 1),
								"Invalid Data");
						
						return;
					}

					Color color1 = ((JComboBox<String>) playerConfig[i][1]).getBackground(); 
					MeepleColor color = MeepleColor.toMeepleColor(color1);
					Player currentPlayer = new Player(name, color);
					if (!Players.isColorAvailable(currentPlayer)) {

						Players.resetPlayers();
						showErrorMessage(
								String.format("There are two players with the same color"),"Same Color");
						return;
					}else {
						Players.addPlayer(currentPlayer);
					}
					
				
				}
				
			
				gameWindow.dispose();
				gc.setState(State.GAME_START);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
				showErrorMessage("Problem with creating player: " + ex.getMessage(), "Error Intern");
			}
		}
	}
}
