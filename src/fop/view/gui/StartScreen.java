package fop.view.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fop.controller.GameController;
import fop.view.components.View;

/**
 * StartScreen Area
 *
 */
public class StartScreen extends View {

    private JButton btnStart, btnStats, btnInfo, btnQuit;
    private JLabel lblTitle;
    GameController gc; 
    GameWindow gamewindow;
    public StartScreen(GameWindow gw, GameController gc ) {
        super(gw);
        this.gc = gc;
        this.gamewindow =gw;
    }

    @Override
    public void onResize() {
        int width = getWidth();
        int height = getHeight();
        int labelHeight = 40;

        int offsetY = (height - 4 * (BUTTON_SIZE.height + 15) - labelHeight) / 3;

        lblTitle.setSize(width, labelHeight);
        lblTitle.setLocation(0, offsetY);

        offsetY += labelHeight + 50;

        int offsetX = (width - BUTTON_SIZE.width) / 2;
        JButton[] buttons = { btnStart, btnStats, btnInfo, btnQuit };
        for (JButton button : buttons) {
            button.setLocation(offsetX, offsetY);
            offsetY += BUTTON_SIZE.height + 15;
        }
    }

    @Override
    protected void onInit() {
        this.lblTitle = createLabel("Carcassonne", 25);
        this.lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.lblTitle.setFont(View.createCelticFont(25));
        this.btnStart = createButton("Start");
        this.btnStats = createButton("Points");
        this.btnInfo = createButton("Info");
        this.btnQuit = createButton("Exit");

        getWindow().setSize(750, 450);
        getWindow().setMinimumSize(new Dimension(600, 400));
    }
 
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == btnQuit) {
            getWindow().dispose();
        } else if(actionEvent.getSource() == btnStart) {
            getWindow().setView(new StartGame(getWindow(), gc));
        } else if(actionEvent.getSource() == btnInfo) {
        	 getWindow().setView(new InfoView(getWindow()));
        } else if(actionEvent.getSource() == btnStats) {
            getWindow().setView(new HighscoreView(getWindow()));
        }
    }

}
