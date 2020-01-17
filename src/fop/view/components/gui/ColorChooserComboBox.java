package fop.view.components.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import fop.model.player.MeepleColor;

/**
 * 
 * this class  creates am JComboBox with the available MeepleColors 
 * The background colors are set via the JComboBoxRenderer class 
 */
public class ColorChooserComboBox extends JComboBox<String> {
	private JComboBox<String> box = new JComboBox<String>();
	private JComboBoxRenderer<String> renderer;

	/**
	 * creates a JComboBox with the available colors 
	 */
	public ColorChooserComboBox() {
		
		box.setBackground(MeepleColor.availableMeepleColors[0]);
		//Creates the item
		for (Color color : MeepleColor.availableMeepleColors) {
			box.addItem("");
		}
		//sets the Color
		renderer = new JComboBoxRenderer<String>(box, MeepleColor.availableMeepleColors);
		box.setRenderer(renderer);
	}

	/**
	 * returns the combo box with the available colors
	 * 
	 * @return
	 */
	public JComboBox<String> getComboBox() {
		return box;
	}

}
/**
 * Sets for a cell the color 
 *
 * @param <String>
 */
class JComboBoxRenderer<String> extends JPanel implements ListCellRenderer<String>
{

    private Color[] colors;

    private JPanel panel;
    private JLabel label;
    private JComboBox<String> combo; 
    /**
     * creates a panel and a text inside and the text will set the background color via the getListCellRendererComponent method 
     * @param combo
     * @param color
     */
    public JComboBoxRenderer(JComboBox<String> combo, Color[] color ) {

        colors = color;
        this.combo = combo ;
        panel = new JPanel();
        panel.add(this);
        label = new JLabel();
        label.setOpaque(true);
        label.setFont(this.combo.getFont());
        panel.add(label);
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        if (index>-1) {
            label.setText(" ");
            label.setBackground(colors[index]);
            if(isSelected) {
         	   combo.setBackground(colors[index]);
            }
        }
        return label;
    }
    
}