package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Game;
import model.IndexTuple;

// BoardLabel is the board image which is being drawn upon.
public class BoardLabel extends JLabel {
    private char[][] boardMatrix;
    private char turn;
    private ArrayList<IndexTuple> availableMoves;
    public final int diskDiameter = 50;
    public final int initHorizontalStep = 37;
    public final int initVerticalStep = 14;
    public final int horizontalStep = 65;
    public final int verticalStep = 65;
    private final int relativeHorizontalStep = 15;
    private final int relativeVerticalStep = 11;


    public BoardLabel(ImageIcon boardIMG, Game game) {
        super(boardIMG);
    }


    // REQUIRES: boardMatrix != null
    // MODIFIES: this
    // EFFECTS: updates boardMatrix and forces new render.
    public void reRenderBoard(char[][] boardMatrix, ArrayList<IndexTuple> availableMoves,char turn) {
        this.boardMatrix = boardMatrix;
        this.availableMoves = availableMoves;
        this.turn = turn;
        repaint();
    }


    // MODIFIES: this
    // EFFECTS: rerenders board and draws disks on it.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDisks(g);
        drawAvailableMoves(g);
    }


    // REQUIRES: boardMatrix to be set
    // MODIFIES: this
    // EFFECTS: draws disks of proper color on Board.
    public void drawDisks(Graphics g) {
        // g.setColor(Color.BLACK);
        // g.fillOval(0, 0, 300, 300);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char disk = this.boardMatrix[i][j];
                if (disk != 0) {
                    if (disk == 'W') {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(relativeVerticalStep + j * verticalStep, relativeHorizontalStep + i * horizontalStep, 
                            diskDiameter, diskDiameter); // reordered this. 
                }

            }
        }
    }


    // REQUIRES: this.availableMoves and this.turn to be set.
    // MODIFIES: this
    // EFFECTS: draws outline of proper colors circle where moves can be made.
    private void drawAvailableMoves(Graphics g) {
        if (this.turn == 'W') {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }

        for (IndexTuple move: this.availableMoves) {
            g.drawOval(relativeVerticalStep + move.getCol() * verticalStep, 
                    relativeHorizontalStep + move.getRow() * horizontalStep,
                    diskDiameter, diskDiameter);
        }
    }

}
