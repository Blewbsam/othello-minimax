package ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TurnGUI extends JPanel {
    JLabel turnLabel;

    // TODO: setup turn GUI

    public TurnGUI() {
        super();
        turnLabel = new JLabel("Turn");
        turnLabel.setSize(new Dimension(250,100));
        this.add(turnLabel);
        this.setVisible(true);
    }



    // MODIFIES: this
    //EFFECTS: switches turn from black to white
    //         and vice versa.
    public void setTurn(char turn) {
    assert turn == 'W' || turn == 'B';
        
        if (turn == 'W') {
            this.turnLabel.setText("White's turn");
        } else {
            turnLabel.setText("Black's turn");
        }
    }


    // MODIFIES: this
    // EFFECTS: displays gameOVer statement from game.
    public void setGameOver(String statemet) {
        this.turnLabel.setText(statemet);
    }

}
