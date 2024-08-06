package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Game;
import model.IndexTuple;

// Represents Board image and pieces that are placed on it.
public class BoardGUI extends JPanel {
    Game game;
    GameGUI gameGUI;
    BoardLabel boardLabel;
    BoardMouseListener bml;

    
    public BoardGUI(GameGUI gameGUI,Game game) {
        this.game = game;
        this.gameGUI = gameGUI;
        try {
            BufferedImage boardIMG = ImageIO.read(new File("img/Board.jpg"));
            boardLabel = new BoardLabel(new ImageIcon(boardIMG), game);
            this.add(boardLabel);
            drawDisks();
        } catch (IOException ioe) {
            System.out.println("Game display was not found at path.");
        }


    }

    // REQUIRES: game to be set.
    // MODIFIES: boardLabel
    // EFFECTS: draws disks and possible moves on boardLabel
    public void drawDisks() {
        char[][] boardMatrix = this.game.getBoardMatrix();
        ArrayList<IndexTuple> availableMoves = this.game.getPossibleMoves();
        char turn = this.game.getTurn();
        boardLabel.reRenderBoard(boardMatrix,availableMoves,turn);
    }


    public void initializeListener() {
        bml = new BoardMouseListener();
        this.addMouseListener(bml);
    }


    // MODIFIES:
    // EFFECTS: makes move on board if valid. 
    // removes listener if move valid
    private boolean handleMousePress(int x, int y) {
        int col = Math.floorDiv(x - boardLabel.initHorizontalStep, boardLabel.horizontalStep);
        int row = Math.floorDiv(y - boardLabel.initVerticalStep, boardLabel.verticalStep);
        if (!(row < 0 || col < 0 || row > 7 || col > 7)) {
            boolean moveStatus = game.makePlayerMove(row, col);
            if (moveStatus) {
                System.out.print("Move made at " + row + " " + col + ".");
                this.removeMouseListener(bml);
                this.gameGUI.gameStep();
                return true;
            } else {
                System.out.print("Move not sucessful.");
                return false;
            }
        }
        return false;
    }



    // Represents MouseListener for clicking on Board.
    // Modeled from https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter/blob/main/src/ui/DrawingEditor.java
    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            handleMousePress(e.getX(), e.getY());
        }

    }

}
