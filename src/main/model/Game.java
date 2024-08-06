package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.JsonReader;
import persistence.JsonWriter;



// Represents an othello game.
public class Game {
    private final int depth = 3;
    private Board gameBoard;
    private char turn;
    private boolean gameOver;
    private char botColor;
    private char playerColor;
    private ArrayList<IndexTuple> possibleMoves;
    private Bot bot;
    private static final String JSON_STORE = "./data/board.json";

    public Game(boolean newGame) {
        if (newGame) {
            this.gameBoard = new Board(null);
            this.turn = 'B';
            this.gameOver = false;
            this.possibleMoves = this.gameBoard.getPossibleMoves(this.turn);
        } else {
            this.loadGame(JSON_STORE);
        }
    }

    // REQUIRES: playerColor is "Black" or "White"
    // MODIFIES: this
    // EFFECTS: sets color of both player and bot
    //          initializes bot.
    public void setBot(char playerColor) {
        assert playerColor == 'B' || playerColor == 'W';

        if (playerColor == 'B') {
            this.playerColor = 'B';
            this.botColor = 'W';
            
        } else {
            this.playerColor = 'W';
            this.botColor = 'B';
        }
        this.bot = new Bot(this.botColor);

    }

    // MODIFIES: this
    // EFFECTS: Makes move at (row,col) index 
    //          placing this.turn on Board
    public boolean makePlayerMove(int row, int col) {
  
        if (!this.gameBoard.makeMove(this.turn, row, col)) {
            return false;
        }

        this.switchTurn();
        this.setPossibleMoves();

        this.checkGameOver();
        
        return true;
    } 


    // REQUIRES: Bot to be initialized
    // MODIFIES: this
    // EFFECTS: makes a move on board from Bot.
    public IndexTuple makeBotMove() {

        IndexTuple botChoice = this.bot.thinkMinimax(this.gameBoard, this.depth);
        this.gameBoard.makeMove(this.turn, botChoice.getRow(), botChoice.getCol());
        this.switchTurn();
        this.setPossibleMoves();

        this.checkGameOver();

        return botChoice;
    }


    // MODIFES: this,
    // EFFECTS: sets game to be over if no possible moves are left.
    public void checkGameOver() {
        if (this.possibleMoves.isEmpty()) {
            this.switchTurn();
            this.setPossibleMoves();
            if (this.possibleMoves.isEmpty()) {
                this.setGameOver();
            }
        }    
    }



    // MODIFIES: this
    // EFFECTS: switces turn from B to W and vice versa.
    private void switchTurn() {
        if (this.turn == 'B') {
            this.turn = 'W'; 
        } else {
            this.turn = 'B';
        }
    }

    public char getTurn() {
        return this.turn;
    }
    
    public char[][] getBoardMatrix() {
        return this.gameBoard.getboardMatrix();
    }


    // MODIFIES: this
    // EFFECTS: updates possibleMoves to all for current turn.
    public void setPossibleMoves() {
        this.possibleMoves = this.gameBoard.getPossibleMoves(turn);
    }

    // EFFECTS: returns possible moves 
    public ArrayList<IndexTuple> getPossibleMoves() {
        return this.possibleMoves;
    }

    // REQUIRES: given char[][] to be 8x8
    // MODIFES: this
    // EFFECTS: changes game's board. To be used for testing.
    public void setBoard(char[][] boardMatrix) {
        this.gameBoard = new Board(boardMatrix);
    }


    public void setGameOver() {
        this.gameOver = true;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    // REQUIRES: game to be over.
    // EFFECTS: displays statement for the winner.
    public String getWinnerStatement() {
        char winner = this.gameBoard.getWinner();
        int whiteDiskCount = this.gameBoard.getWhiteDiskCount();
        int blackDiskCount = this.gameBoard.getBlackDiskCount();


        if (winner == 'W') {
            return "White has won with " + whiteDiskCount + " disks against Black's " + blackDiskCount + ".";
        } else if (winner == 'B') {
            return "Black has won with " + blackDiskCount + " disks against White's " + whiteDiskCount + ".";
        } else {
            return "The game is a tie.";
        }
    }


    public int getWhiteDiskCount() {
        return this.gameBoard.getWhiteDiskCount();
    }

    public int getBlackDiskCount() {
        return this.gameBoard.getBlackDiskCount();
    }


    public char getPlayerColor() {
        return this.playerColor;
    }

    // MODIFIES: JSON save path
    // EFFECTS: converts information to JSONObject
    //  and saves in in json file in designated path.
    public void saveGame(String src) throws FileNotFoundException {
        // GameData gameData = new GameData(this.getBoardMatrix(), this.playerColor, this.botColor, this.turn);
        JSONObject json = new JSONObject();
        json.put("playerColor",this.playerColor);
        json.put("botColor",this.botColor);
        json.put("turn",this.turn);
        
        JSONArray jsonBoard = new JSONArray(this.getBoardMatrix());
        json.put("board",jsonBoard);
        
        JsonWriter jw = new JsonWriter(src);

        jw.open();
        jw.write(json);
        jw.close();

    }

    // MODIFIES: this
    // EFFECTS: reads json file and sets game up
    //          according to JSONObject
    @SuppressWarnings("methodlength")
    public void loadGame(String src) {
        // stub
        try {
            JsonReader reader = new JsonReader(src);
            JSONObject gameJson = reader.read();
            int playerT = gameJson.getInt("turn");
            int playerC = gameJson.getInt("playerColor");
            JSONArray board = gameJson.getJSONArray("board"); 
            this.turn = (char) playerT;
            this.setBot((char) playerC);

            char[][] loadedBoard = new char[8][8];
            JSONArray curBoardRow;
            String curString;
            for (int i = 0; i < 8; i++) {
                curBoardRow = board.getJSONArray(i);
                for (int j = 0; j < 8; j++) {
                    curString = curBoardRow.getString(j);
                    if (!curString.equals("")) {
                        loadedBoard[i][j] = curString.charAt(0);
                    }
                }
            }
            this.gameBoard = new Board(loadedBoard);
            this.setPossibleMoves();
        } catch (IOException e) {
            System.out.print("Previous saved file does not exist.");
        }
    }

}
