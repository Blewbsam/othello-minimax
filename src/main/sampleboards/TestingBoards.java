package sampleboards;

// used to create custom char[8][8] that are helpful for testing boards.
public class TestingBoards {


    // REQUIRES: color == 'W' or 'B'
    // EFFECTS: returns a char[][] that has all pieces filled with
    // given char
    public char[][] allFillBoard(char color) {
        assert color == 'W' || color == 'B';

        char[][] filledBoardMatrix = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                filledBoardMatrix[i][j] = color;
            }
        }


        return filledBoardMatrix;
    }

    // EFFECTS: returns all black board with 
    //          empty char at (7,7) and 'w' at (0,7)
    public char[][] closeToEndBoard() {
        char[][] blackBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                blackBoard[i][j] = 'B';
            }
        }
        blackBoard[7][7] = 0;
        blackBoard[0][7] = 'W';
        return blackBoard;
    }

    
    // EFFECTS: all white board with black 
    //  at (3,3) and some other spots.
    public char[][] createDoughnutBoard() {
        char[][] board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 'W';
            }
        }
        board[0][0] = 'B';
        board[0][6] = 'B';
        board[6][0] = 'B';
        board[7][7] = 'B';
        board[0][3] = 'B';
        board[3][0] = 'B';
        board[7][3] = 'B';
        board[3][7] = 'B';
        board[3][3] = 'B';
        
        return board;
    }


}
