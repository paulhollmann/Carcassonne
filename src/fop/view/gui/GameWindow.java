package fop.view.gui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import fop.controller.GameController;
import fop.view.components.View;
import fop.view.components.gui.Resources;


public class GameWindow extends JFrame {

    private View activeView;
    private Resources resources; 
    
    
    public GameWindow(Resources resources) {
        this.resources = resources;
        this.initWindow();
        this.setView(new StartScreen(this,new GameController()));
        this.setVisible(true);
    }
    
    /**
     * inits The GameWindow 
     */
    private void initWindow() {

        LookAndFeel laf = UIManager.getLookAndFeel();
        if(laf.getSupportsWindowDecorations()) {
            UIManager.getCrossPlatformLookAndFeelClassName();
        }

        this.setTitle("Carcassonne - FOP Projekt WiSe 19/20");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setMinimumSize(new Dimension(600, 400));
        this.setLocationRelativeTo(null); // Center Window
        this.addWindowStateListener(windowStateListener); // Resize Event
    }
    /**
     * sets the given View
     * @param view
     */
    public void setView(View view) {
        this.activeView = view;
        this.activeView.setSize(getContentPane().getSize());
        this.setContentPane(view);
        this.requestFocus();
    }
    private WindowStateListener windowStateListener = new WindowStateListener() {
        @Override
        public void windowStateChanged(WindowEvent windowEvent) {
            if ((windowEvent.getNewState() & JFrame.MAXIMIZED_BOTH) != JFrame.MAXIMIZED_BOTH) {
                activeView.onResize();
            }
        }
    };

  /**
   * return the Resources
   * @return
   */
    public Resources getResources() {
        return this.resources;
    }
}

