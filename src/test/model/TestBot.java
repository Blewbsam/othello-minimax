package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sampleboards.TestingBoards;


public class TestBot {
    Bot bt;
    Board newBoard;
    TestingBoards tbs;

    @BeforeEach
    void runBefore() {
        bt = new Bot('B');
        newBoard = new Board(null);
        tbs = new TestingBoards();
    }


    @Test
    void testBotConstructor() {
        assertEquals('B', bt.getBotColor());
        assertEquals('W', bt.getPlayerColor());
    }


   
    @Test
    void testThinkMinimax() {
        IndexTuple choice = bt.thinkMinimax(newBoard, 5);
        assertEquals(2,choice.getRow());
        assertEquals(3, choice.getCol());
    }

    @Test 
    void testWhiteBot() {
        Bot whiteBot = new Bot('W');
        assertEquals('W', whiteBot.getBotColor());
        assertEquals('B', whiteBot.getPlayerColor());

    }

    @Test 
    void testGameStatusEvaluation() {
        Board allBlackBoard = new Board(tbs.allFillBoard('B'));
        int max = bt.minimax(allBlackBoard, 3,0,0,true);

    }



}
