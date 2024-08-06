package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sampleboards.TestingBoards;


public class TestBoard {

    Board newBoard;
    Board customBoard;
    TestingBoards tb;


    @BeforeEach
    public void runBefore() {
        newBoard = new Board(null);


        char[][] midGameBoard = new char[8][8];
        midGameBoard[1][1] = 'B';
        midGameBoard[2][2] = 'B';
        midGameBoard[3][2] = 'B';
        midGameBoard[3][3] = 'B';
        midGameBoard[2][6] = 'B';
        midGameBoard[3][4] = 'W';
        midGameBoard[3][5] = 'W';
        midGameBoard[3][6] = 'W';
        midGameBoard[4][3] = 'W';
        midGameBoard[4][4] = 'B';
        midGameBoard[5][3] = 'W';
        midGameBoard[5][4] = 'B';

        customBoard = new Board(midGameBoard);
    }

    @Test
    public void testConstrucor() {
        char[][] newBoardMatrix = newBoard.getboardMatrix();
        
        assertEquals(2,newBoard.getBlackDiskCount());
        assertEquals(2,newBoard.getWhiteDiskCount());

        assertEquals('W',newBoardMatrix[3][3]);
        assertEquals('B',newBoardMatrix[3][4]);
        assertEquals('B',newBoardMatrix[4][3]);
        assertEquals('W',newBoardMatrix[4][4]);


        char[][] customBoardMatrix = customBoard.getboardMatrix();
        assertEquals('B',customBoardMatrix[1][1]);
        assertEquals('W',customBoardMatrix[3][6]);
        assertEquals(0,customBoardMatrix[0][0]);



    }



    @Test 
    public void testMakeMove() { 
        boolean moveStatus = newBoard.makeMove('W',0,0);
        assertFalse(moveStatus);

        moveStatus = newBoard.makeMove('W',5,3);
        assertTrue(moveStatus);

        moveStatus = newBoard.makeMove('B', 5, 3);
        assertFalse(moveStatus);

        moveStatus = newBoard.makeMove('B',2,3);
        assertFalse(moveStatus);

        char[][] boardArray = newBoard.getboardMatrix();
        assertEquals(0,boardArray[0][0]);
        assertEquals('W',boardArray[5][3]);
        assertEquals(0,boardArray[2][3]);
    }


    
    @Test
    public void testCheckVertical() {
        boolean status = newBoard.checkVertical('W', 7, 7);
        assertFalse(status);

    }

    @Test 
    public void testCheckUp() {
        boolean status = customBoard.checkUp(6, 3, 'B');
        assertTrue(status);

        status = customBoard.checkUp(6, 2, 'B');
        assertFalse(status);

        status = newBoard.checkUp(5, 3, 'W');
        assertTrue(status);
    }

    @Test
    public void testCheckDown() {
        boolean status = customBoard.checkDown(2, 4, 'B');
        assertTrue(status);

        status = customBoard.checkDown(0, 1, 'B');
        assertFalse(status);

        status = newBoard.checkDown(2,4, 'W');
        assertTrue(status);

        status = newBoard.checkDown(7, 7, 'W');
        assertFalse(status);
    }


    @Test 
    public void testCheckHorizontal() {
        boolean status = customBoard.checkHorizontal('W', 3, 7);
        assertFalse(status);
    }

    @Test
    public void testCheckRight() {
        //check right
        boolean status = customBoard.checkRight(3, 1, 'W');
        assertTrue(status);

        status = customBoard.checkRight(0, 0, 'W');
        assertFalse(status);

        status = customBoard.checkRight(5,2,'B');
        assertTrue(status);
    }

    @Test
    public void testCheckLeft() {
        boolean status = customBoard.checkLeft(3, 7, 'B');
        assertTrue(status);

        status = customBoard.checkLeft(3, 7, 'W');
        assertFalse(status);
    }


    @Test
    public void testCheckDiagonal() {
        char[][] fourthBoardMatrix = new char[8][8];
        fourthBoardMatrix[3][3] = 'W';
        fourthBoardMatrix[4][4] = 'B';

     
        Board fourthBoard = new Board(fourthBoardMatrix);

        boolean status = fourthBoard.checkDiagonal('B', 2, 2);
        assertTrue(status);
    }

    @Test 
    void testCheckDownRight() {
        char[][] thirdBoardMatrix = new char[8][8];
        thirdBoardMatrix[3][3] = 'W';
        thirdBoardMatrix[3][4] = 'W';
        thirdBoardMatrix[3][5] = 'W';
        thirdBoardMatrix[4][3] = 'B';
        thirdBoardMatrix[4][4] = 'B';
        thirdBoardMatrix[5][4] = 'B';
        thirdBoardMatrix[5][7] = 'B';

        Board thirdBoard = new Board(thirdBoardMatrix);

        boolean status = thirdBoard.checkDownRight(2, 2, 'B');
        assertTrue(status);
        status = thirdBoard.checkDownRight(2, 2, 'W');
        assertFalse(status);

        status = thirdBoard.checkDownRight(4, 6, 'W');
        assertFalse(status);

    }

    @Test
    void testCheckDownLeft() {
        char[][] thirdBoardMatrix = new char[8][8];
        thirdBoardMatrix[3][3] = 'W';
        thirdBoardMatrix[3][4] = 'W';
        thirdBoardMatrix[3][5] = 'W';
        thirdBoardMatrix[4][3] = 'B';
        thirdBoardMatrix[4][4] = 'B';
        thirdBoardMatrix[5][4] = 'B';
        thirdBoardMatrix[5][0] = 'B';

        Board thirdBoard = new Board(thirdBoardMatrix); 
        
        boolean status = thirdBoard.checkDownLeft(2, 6, 'B');
        assertTrue(status);

        status = thirdBoard.checkDownLeft(2, 2, 'W');
        assertFalse(status);

        status = thirdBoard.checkDownLeft(4, 1, 'W');
        assertFalse(status);
    }



    @Test
    void testCheckUpRight() {
        boolean status = customBoard.checkUpRight(6, 2,'B');
        assertTrue(status);

        status = customBoard.checkUpRight(6, 3,'B');
        assertFalse(status);
    }

    @Test
    void testCheckUpLeft() {
        boolean status = customBoard.checkUpLeft(6,5,'W');
        assertTrue(status);

        status = customBoard.checkUpLeft(4,6,'B');
        assertFalse(status);
        
    }


    @Test
    void testFlipUp() {
        boolean status = newBoard.makeMove('W', 5, 3);
        assertTrue(status);

        status = newBoard.makeMove('B', 5, 4);
        assertTrue(status);

        char[][] boardMatrix = newBoard.getboardMatrix();
        newBoard.flipUp(5,3,'W');
        newBoard.flipUp(5, 4, 'B');

        assertEquals('W', boardMatrix[4][3]);
        assertEquals('B', boardMatrix[4][4]);

    }



    @Test
    void testFlipDown() {
        boolean status = newBoard.makeMove('W', 2, 4);
        assertTrue(status);

        status = newBoard.makeMove('B', 2, 3);
        assertTrue(status);

        newBoard.flipDown(2,4,'W');
        newBoard.flipDown(2, 3, 'B');

        char[][] boardMatrix = newBoard.getboardMatrix();

        assertEquals('W', boardMatrix[3][4]);
        assertEquals('B', boardMatrix[3][3]);
    }


    @Test
    void testFlipRight() {
        boolean status = newBoard.makeMove('W', 4, 2);
        assertTrue(status);

        status = newBoard.makeMove('B', 3, 2);
        assertTrue(status);

        newBoard.flipRight(4,2,'W');
        newBoard.flipRight(3, 2, 'B');
        char[][] boardMatrix = newBoard.getboardMatrix();

        assertEquals('W', boardMatrix[4][3]);
        assertEquals('B', boardMatrix[3][3]);
    }


    @Test
    void testFlipLeft() {
        boolean status = newBoard.makeMove('W', 3, 5);
        assertTrue(status);

        status = newBoard.makeMove('B', 4, 5);
        assertTrue(status);

        newBoard.flipLeft(3,5,'W');
        newBoard.flipLeft(4, 5, 'B');
        char[][] boardMatrix = newBoard.getboardMatrix();

        assertEquals('W', boardMatrix[3][4]);
        assertEquals('B', boardMatrix[4][4]);
    }





    @Test 
    public void testAutomaticMoves() {
        tb = new TestingBoards();
        char[][] board = tb.createDoughnutBoard();
        Board doughnutBoard = new Board(board);
        doughnutBoard.automaticMoves(3, 3);
        
        char[][] boardMatrix = doughnutBoard.getboardMatrix();
        assertEquals('B', boardMatrix[3][2]);
        assertEquals('B', boardMatrix[2][3]);
        assertEquals('B', boardMatrix[3][4]);
        assertEquals('B', boardMatrix[4][3]);
        assertEquals('B', boardMatrix[2][2]);
        assertEquals('B', boardMatrix[4][4]);
        assertEquals('B', boardMatrix[2][4]);
        assertEquals('B', boardMatrix[4][2]);

    }




    @Test 
    public void testSetDiskCounts() {
        newBoard.setDiskCounts();
        assertEquals(2,newBoard.getWhiteDiskCount());
        assertEquals(2,newBoard.getBlackDiskCount());

        customBoard.setDiskCounts();
        assertEquals(5,customBoard.getWhiteDiskCount());
        assertEquals(7,customBoard.getBlackDiskCount());

    }


    @Test 
    @SuppressWarnings("methodlength")
    public void testGetWinner() {
        char[][] allBlackBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                allBlackBoard[i][j] = 'B';
            }
        }
        Board blackBoard = new Board(allBlackBoard);
        char winner = blackBoard.getWinner();
        assertEquals('B',winner);

        char[][] allWhiteBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                allWhiteBoard[i][j] = 'W';
            }
        }
        Board whiteBoard = new Board(allWhiteBoard);
        winner = whiteBoard.getWinner();
        assertEquals('W',winner);

        
        char[][] halfWhiteHalfBlackBoard =  new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                allBlackBoard[i][j] = 'B';
            }
            for (int j = 4; j < 8; j++) {
                allBlackBoard[i][j] = 'W';
            }
        }
        Board halfBoard = new Board(halfWhiteHalfBlackBoard);
        winner = halfBoard.getWinner();
        assertEquals('T',winner);
    }


}
