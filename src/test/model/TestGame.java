package model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import sampleboards.TestingBoards;


public class TestGame {

    Game newGame;
    TestingBoards tb;

    @BeforeEach
    void runBefore() {
        newGame = new Game(true);
        
    }

    @Test 
    public void testConstructor() {
        newGame = new Game(true);
        assertEquals('B',newGame.getTurn());
        assertFalse(newGame.isGameOver());
    }





    @Test 
    public void testMakePlayerMove() {
        tb = new TestingBoards();

        //move possible 
        newGame = new Game(true);
        boolean status = newGame.makePlayerMove(2,3);
        assertTrue(status);
        assertEquals(newGame.getTurn(), 'W');
        char[][] boardMatrix = newGame.getBoardMatrix();
        assertEquals('B',boardMatrix[2][3]);
        assertEquals('B',boardMatrix[2][3]);

        //not valid move to make
        status = newGame.makePlayerMove(0, 0);
        assertFalse(status);
        assertEquals(newGame.getTurn(), 'W');

        //move ends game
        newGame.setBoard(tb.closeToEndBoard());
        status = newGame.makePlayerMove(7, 7);
        assertTrue(status);
        assertTrue(newGame.isGameOver());
        assertEquals(56, newGame.getBlackDiskCount());
        assertEquals(8, newGame.getWhiteDiskCount());
    }

    @Test 
    public void testMakeBotMove() {
        newGame = new Game(true);
        newGame.setBot('W');
        IndexTuple botChoice = newGame.makeBotMove();
        assertEquals(2,botChoice.getRow());
        assertEquals(3,botChoice.getCol());
        char[][] boardMatrix = newGame.getBoardMatrix();
        assertEquals('B',boardMatrix[2][3]);
    }


    @Test
    public void testGetWinnerStatement() {
        newGame = new Game(true);
        newGame.setBot('W');
        assertEquals("The game is a tie.", newGame.getWinnerStatement());
        newGame.makeBotMove();
        assertEquals("Black has won with 4 disks against White's 1.", newGame.getWinnerStatement());
        newGame = new Game(true);
        newGame.setBot('B');
        newGame.makeBotMove();
        newGame.makeBotMove();
        assertEquals("White has won with 4 disks against Black's 1.", newGame.getWinnerStatement());
    }


    @Test 
    public void saveGame() {
        newGame = new Game(true);
        newGame.setBot('B');
        try {
            newGame.saveGame("./data/sampleBoard2.json");
            newGame.loadGame("./data/sampleBoard2.json");
        } catch (FileNotFoundException exception) {
            fail();
        }
        char[][] boardMatrix = newGame.getBoardMatrix();
        assertEquals('B', newGame.getTurn());
        assertEquals('B',newGame.getPlayerColor());
        assertEquals('W',boardMatrix[3][3]);
        assertEquals('B',boardMatrix[3][4]);
    }



    @Test
    public void testLoadGame() {
        newGame = new Game(true);
        newGame.loadGame("./data/sampleBoard.json");
        char[][] boardMatrix = newGame.getBoardMatrix();

        assertEquals('W', newGame.getTurn());
        assertEquals('W',newGame.getPlayerColor());
        assertEquals('W',boardMatrix[2][2]);
        assertEquals('B',boardMatrix[2][3]);
        // assertEquals("",boardMatrix[0][0]);

    }




}
