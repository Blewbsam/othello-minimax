package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CountGUI extends JPanel {
    private final Color color;
    private JLabel countLabel;

    // REQUIRES: char color: black or white
    public CountGUI(Color background,Color foreground) {
        super();
        this.color = background;
        this.setPreferredSize(new Dimension(400,200));
        countLabel = new JLabel("0");
        countLabel.setForeground(foreground);

        countLabel.setSize(new Dimension(200,100));
        this.add(countLabel);
        this.setVisible(true);
    }   


    // MODIFIES: this
    // EFFECTS: sets count of associated pieces on board.
    public void setCount(int n) {
        this.countLabel.setText(Integer.toString(n));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(0, 0, 30, 30);
    }

}
