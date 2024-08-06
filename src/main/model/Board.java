package model;

import java.util.ArrayList;

// Board manages state of the board and 
// changes that should be made to it.
public class Board {
    private final char[][] boardMatrix;
    private int whiteDiskCount;
    private int blackDiskCount;
    private static final int NUMROWS = 8;
    private static final int NUMCOLUMNS = 8;



    public Board(char[][] boardMatrix) {

        if (boardMatrix == null) {
            this.boardMatrix = new char[NUMROWS][NUMCOLUMNS];
            this.boardMatrix[3][3] = 'W';
            this.boardMatrix[3][4] = 'B';
            this.boardMatrix[4][3] = 'B';
            this.boardMatrix[4][4] = 'W';


        } else {
            this.boardMatrix = boardMatrix;
        }
        this.setDiskCounts();

    }


    //REQUIRES: turn == "white" or turn =="black"
    // 0 <= row,col <= 7
    // MODIFIES: this
    // EFFECTS: checks if it can make move at row,col
    // makes move if possible and return true, else false.
    public boolean makeMove(char turn,int row, int col) {
        if (isMovePossible(turn,row, col)) {
            if (turn == 'W') {
                this.boardMatrix[row][col] = 'W';
            } else {
                this.boardMatrix[row][col] = 'B';
            }
            this.automaticMoves(row, col);
            return true;   
        }
        return false;

    }

    // EFFECTS: returns all possible indexes of moves that are playable
    public ArrayList<IndexTuple> getPossibleMoves(char turn) {
        ArrayList<IndexTuple> possibleMoves = new ArrayList<>();

        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLUMNS; j++) {
                if (this.boardMatrix[i][j] == 0) {

                    if (checkVertical(turn, i, j) || checkHorizontal(turn, i, j) || checkDiagonal(turn, i, j)) {
                        possibleMoves.add(new IndexTuple(i, j));
                    }
                }
            }

        }
        return possibleMoves;
    }

    // REQUIRES: board at (row,col) to be empty.
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any opposite disks vertically, false otherwise.
    public boolean checkVertical(char turn,int row, int col) {

        return (checkUp(row, col, turn) || checkDown(row, col, turn));

    }
    


    // REQUIRES: board at (row,col) to be empty.
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any hgihers disks, false otherwise.
    public boolean checkUp(int row, int col, char turn) {
        char oppositeTurn = getOppositeTurn(turn);
        if (row == 0) {
            return false;
        }

        char neighbor = this.boardMatrix[row - 1][col];
        if (neighbor == oppositeTurn) { 
            for (int i = row - 1; i >= 0; i--) {
                if (this.boardMatrix[i][col] == turn) {
                    return true;
                }
            }

        }

        return false;
    }


    // REQUIRES: board at (row,col) to be empty.
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any lower disks, false otherwise.
    public boolean checkDown(int row, int col, char turn) {
        char oppositeTurn = getOppositeTurn(turn);
        if (row == NUMROWS - 1) {
            return false;
        }

        char neighbor = this.boardMatrix[row + 1][col];
        if (neighbor == oppositeTurn) {
            for (int i = row + 1; i < NUMROWS; i++) {
                if (this.boardMatrix[i][col] == turn) {
                    return true;
                }
            }
        }

        return false;

    }



    // REQUIRES: board at (row,col) to be empty,  0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing diskl of turn at (row,col)
    //          reverse any opposite disks horizontally, false otherwise.
    public boolean checkHorizontal(char turn, int row, int col) {
        return (checkRight(row, col, turn) || checkLeft(row, col, turn));
    }


    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverse any right disks, false otherwise.
    public boolean checkRight(int row, int col, char turn) {
        char oppositeTurn = getOppositeTurn(turn);
        if (col == NUMCOLUMNS - 1) {
            return false;
        }
        char neighbor = this.boardMatrix[row][col + 1];
        if (neighbor == oppositeTurn) {
            for (int j = col + 1; j < NUMCOLUMNS; j++) {


                if (this.boardMatrix[row][j] == turn) {
                    return true;
                }
            }
        }
        
        return false;
    }
    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverse any left disks, false otherwise.

    public boolean checkLeft(int row,int col,char turn) {
        char oppositeTurn = getOppositeTurn(turn);
        if (col == 0) {
            return false;
        }
        
        char neighbor = this.boardMatrix[row][col - 1];
        if (neighbor == oppositeTurn) {
            for (int j = col - 1; j >= 0; j--) {
                if (this.boardMatrix[row][j] == turn) {
                    return true;
                }
            }
        }
        return false;
        
    }



    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any ooposite disks diagonally
    public boolean checkDiagonal(char turn, int row, int col) {


        return (checkUpRight(row, col, turn) || checkUpLeft(row, col, turn) 
            || checkDownLeft(row, col, turn) || checkDownRight(row, col, turn));   
    }



    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any ooposite disks up right
    public boolean checkUpRight(int row, int col, char turn) {
        char oppositeTurn = this.getOppositeTurn(turn);

        if (row == 0 || col == NUMCOLUMNS - 1) {
            return false;
        }

        int columnIndex = col + 1;
        char neighbor = this.boardMatrix[row - 1][col + 1];

        if (neighbor == oppositeTurn) { 
            for (int i = row - 1; i >= 0; i--) {
                if (columnIndex > 7) {
                    break;
                }

                if (this.boardMatrix[i][columnIndex] == 0) {
                    return false;
                }

                if (this.boardMatrix[i][columnIndex] == turn) {
                    return true;
                }
                
                columnIndex += 1;
            }
        }

        return false;
    }

    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any ooposite disks up left
    public boolean checkUpLeft(int row, int col, char turn) {
        char oppositeTurn = this.getOppositeTurn(turn);
        if (row == 0 || col == 0) {
            return false;
        }
        int columnIndex = col - 1;

        char neighbor = this.boardMatrix[row - 1][col - 1];

        if (neighbor == oppositeTurn) {
            for (int i = row - 1; i >= 0; i--) {
                if (columnIndex < 0) {
                    break;
                }

                if (this.boardMatrix[i][columnIndex] == 0) {
                    return false;
                }


                if (this.boardMatrix[i][columnIndex] == turn) {
                    return true;
                }

                columnIndex -= 1;
            }
        } 

        return false;
    }

    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any ooposite disks down right
    public boolean checkDownRight(int row, int col, char turn) {
        char oppositeTurn = this.getOppositeTurn(turn);
        if (row == NUMROWS - 1 || col == NUMCOLUMNS - 1) {
            return false;
        }
        int columnIndex = col + 1;
        char neighbor = this.boardMatrix[row + 1][col + 1];

        if (neighbor == oppositeTurn) {
            for (int i = row + 1; i < NUMROWS; i++) {
                if (columnIndex > 7) {
                    break;
                }
                if (this.boardMatrix[i][columnIndex] == 0) {
                    return false;
                }
                if (this.boardMatrix[i][columnIndex] == turn) {
                    return true;
                }
                columnIndex += 1;
            }
        } 
        return false;
    }

    // REQUIRES: board at (row,col) to be empty, 0 <= row,col < NUMROWS
    // EFFECTS: returns true if placing disk of turn at (row,col)
    //          reverses any ooposite disks down left
    public boolean checkDownLeft(int row, int col, char turn) {
        char oppositeTurn = this.getOppositeTurn(turn);
        if (row == NUMROWS - 1 || col == 0) {
            return false;
        }
        int columnIndex = col - 1;
        char neighbor = this.boardMatrix[row + 1][col - 1];
        if (neighbor == oppositeTurn) {
            for (int i = row + 1; i < NUMROWS; i++) {
                if (columnIndex < 0) {
                    break;
                }
                if (this.boardMatrix[i][columnIndex] == 0) {
                    return false;
                }
                if (this.boardMatrix[i][columnIndex] == turn) {
                    return true;
                }
                columnIndex -= 1;
            }
        }
        return false;
    }
    
    // REQUIRES: turn to be either W or B
    private char getOppositeTurn(char turn) {
        if (turn == 'W') {
            return 'B';
        } else {
            return 'W';
        }

    }

    // REQUIRES: new move to have been made
    // MODIFIES: this
    // EFFECTS: makes all changes that should occur to board
    //          as result of recent move.
    @SuppressWarnings("methodlength")
    public void automaticMoves(int row, int col) {
        
        char turn = this.boardMatrix[row][col];

        if (checkUp(row, col, turn)) {
            flipUp(row, col, turn);
        }
        if (checkDown(row, col, turn)) {
            flipDown(row, col, turn);
        }
        if (checkLeft(row, col, turn)) {
            flipLeft(row, col, turn);
        }
        if (checkRight(row, col, turn)) {
            flipRight(row, col, turn);
        }
        if (checkUpRight(row, col, turn)) {
            flipUpRight(row, col, turn);
        }
        if (checkUpLeft(row, col, turn)) {
            flipUpLeft(row, col, turn);
        }
        if (checkDownRight(row, col, turn)) {
            flipDownRight(row, col, turn);
        }
        if (checkDownLeft(row, col, turn)) {
            flipDownLeft(row, col, turn);
        }

        this.setDiskCounts();
    }

    // REQUIRES: flipping of one or more disks above
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn above disk at 
    //          (row,col) in sandwich between turn
    public void flipUp(int row, int col, char turn) {
        for (int i = row - 1; i >= 0; i--) {
            if (this.boardMatrix[i][col] == turn) {
                break;
            }
            if (turn == 'W') {
                this.boardMatrix[i][col] = 'W';
            } else {
                this.boardMatrix[i][col] = 'B';
            }
        }

    }

    // REQUIRES: flipping of one or more disks below
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn below disk at 
    //          (row,col) in sandwich between turn
    public void flipDown(int row, int col, char turn) {
        for (int i = row + 1; i < NUMROWS; i++) {
            if (this.boardMatrix[i][col] == turn) {
                break;
            }
            if (turn == 'W') {
                this.boardMatrix[i][col] = 'W';
            } else {
                this.boardMatrix[i][col] = 'B';
            }
        }
    }

    // REQUIRES: flipping of one or more disks to the right
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn to the right of disk at 
    //          (row,col) in sandwich between turn
    public void flipRight(int row, int col, char turn) {
        for (int j = col + 1; j < NUMCOLUMNS; j++) {
            if (this.boardMatrix[row][j] == turn) {
                break;
            }
            if (turn == 'W') {
                this.boardMatrix[row][j] = 'W';
            } else {
                this.boardMatrix[row][j] = 'B';
            }
        }
    }

    // REQUIRES: flipping of one or more disks to the left
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn to the left of disk at 
    //          (row,col) in sandwich between turn
    public void flipLeft(int row, int col, char turn) {
        for (int j = col - 1; j >= 0; j--) {
            if (this.boardMatrix[row][j] == turn) {
                break;
            }
            if (turn == 'W') {
                this.boardMatrix[row][j] = 'W';
            } else {
                this.boardMatrix[row][j] = 'B';
            }
        }
    }



    // REQUIRES: flipping of one or more disks to up right
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn to the up right of disk at 
    //          (row,col) in sandwich between turn
    private void flipUpRight(int row, int col, char turn) { 
        int columnIndex = col + 1;
    
        for (int i = row - 1; i >= 0; i--) {
            if (columnIndex >= NUMCOLUMNS) {
                break;
            }
            if (this.boardMatrix[i][columnIndex] == turn) {
                break;
            }
            this.boardMatrix[i][columnIndex] = turn;


            columnIndex += 1;
        }
    }


    // REQUIRES: flipping of one or more disks to down right
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn to the down right of disk at 
    //          (row,col) in sandwich between turn
    private void flipDownRight(int row, int col, char turn) {
        int columnIndex = col + 1;
        for (int i = row + 1; i < NUMROWS; i++) {
            if (columnIndex >= NUMCOLUMNS) {
                break;
            }

            if (this.boardMatrix[i][columnIndex] == turn) {
                break;
            }

            this.boardMatrix[i][columnIndex] = turn;

            columnIndex += 1;
        }
    }


    // REQUIRES: flipping of one or more disks to up left
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn to the up left of disk at 
    //          (row,col) in sandwich between turn
    private void flipUpLeft(int row, int col, char turn) {
        int columnIndex = col - 1;
        for (int i = row - 1; i >= 0; i--) {
            if (columnIndex < 0) {
                break;
            }

            if (this.boardMatrix[i][columnIndex] == turn) {
                break;
            }
            this.boardMatrix[i][columnIndex] = turn;


            columnIndex -= 1;
        }
    }

    // REQUIRES: flipping of one or more disks to down left
    //           disk at (row,col) should be required. 
    //           disk at (row,col) should have just been placed
    // MODIFIES: this
    // EFFECTS: flips all disks of oppositeTurn to the down left of disk at 
    //          (row,col) in sandwich between turn
    private void flipDownLeft(int row, int col, char turn) {
        int columnIndex = col - 1;
        for (int i = row + 1; i < NUMCOLUMNS; i++) {
            if (columnIndex < 0) {
                break;
            }

            if (this.boardMatrix[i][columnIndex] == turn) {
                break;
            }
            
            this.boardMatrix[i][columnIndex] = turn;


            columnIndex -= 1;
        }
    }


    // EFFECTS: returns true if its possible to place turn at row, col, false otherwise.
    public boolean isMovePossible(char turn, int row, int col) {
        for (IndexTuple move: getPossibleMoves(turn)) {
            if (move.getRow() == row && move.getCol() == col) {
                return true;
            }
        }

        return false;

    }


    // MODIFIES: this
    // EFFECTS: iterates over boardMatrix to set whiteDiskCount and blackDiskCount
    public void setDiskCounts() {
        int blackCount = 0;
        int whiteCount = 0;
        

        for (char[] row : this.boardMatrix) {
            for (char disk: row) {
                if (disk == 'B') {
                    blackCount += 1;
                } else if (disk == 'W') {
                    whiteCount += 1;
                }
            }
        }
        

        this.whiteDiskCount = whiteCount;
        this.blackDiskCount = blackCount;

    }


    public int getWhiteDiskCount() {
        return whiteDiskCount;
    }

    public int getBlackDiskCount() {
        return blackDiskCount;
    }


    // EFFECTS: returns deep clone of boardMatrix
    public char[][] getboardMatrix() {
        return this.getBoardDeepCopy();
    }    


    // EFFECTS: creates deep copy of array where inner 
    //          int arrays are also new.
    private char[][] getBoardDeepCopy() {
        char[][] deepCopy = new char[NUMROWS][NUMCOLUMNS];
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLUMNS; j++) {
                deepCopy[i][j] = this.boardMatrix[i][j];
            }
        }
        return deepCopy;
    }




    // REQUIRES: disk is either 'W' or 'B'
    public ArrayList<IndexTuple> getDiskIndices(char disk) {
        ArrayList<IndexTuple> diskIndices = new ArrayList<IndexTuple>();

        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLUMNS; j++) {
                if (this.boardMatrix[i][j] == disk) {
                    diskIndices.add(new IndexTuple(i, j));
                }
            }
        }
        return diskIndices;
    }

    // REQUIRES: game to be over
    // EFFECTS: returns winner of the game
    public char getWinner() {
        if (whiteDiskCount > blackDiskCount) {
            return 'W'; 
        } else if (whiteDiskCount < blackDiskCount) {
            return 'B';
        } else {
            return 'T';
        }
    }



    // EFFECTS: checks if game is over.
    public boolean isGameOver() {
        return (this.getPossibleMoves('W').isEmpty() && this.getPossibleMoves('B').isEmpty());
    }



}
