package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Bot is implementation of Minimax algorithm that is attemptign to maximize.
// with access to game class.
public class Bot {


    private char botTurn;
    private char playerTurn;
    private final int parityWeight = 1;
    private final int mobilityWeight = 3;
    private final int stabilityWeight = 3;
    private final int gameStatusWeight = 100;
    private final int[][] staticWeights;

    // REQUIRES: botPlayer is either `W` or `B`
    // Asssuming that in all games white is maximizing
    // and black is minimizing
    public Bot(char botTurn) { 
        assert botTurn == 'W' || botTurn == 'B';
        this.botTurn = botTurn;
        if (botTurn == 'W') {
            this.playerTurn = 'B';
        } else {
            this.playerTurn = 'W';
        }

        staticWeights = new int[8][8];
        this.setStaticWeights();

    }

    public char getPlayerColor() {
        return this.playerTurn;
    }

    public char getBotColor() {
        return this.botTurn;
    }


    // REQURIES: state != null. state.getPossibleMoves(botTurn) is not empty
    // EFFECTS: uses minimax to decide which following move to make.
    public IndexTuple thinkMinimax(Board state, int depth) {
        List<Integer> minimaxEvals = new ArrayList<Integer>();
        ArrayList<IndexTuple> curPossibleMoves = state.getPossibleMoves(botTurn);
        int bestEval;
        Board loopState;
        for (IndexTuple move: curPossibleMoves) {
            loopState = new Board(state.getboardMatrix());
            loopState.makeMove(botTurn, move.getRow(), move.getCol());
            bestEval = minimax(loopState, depth, -10000, 10000, false);
            minimaxEvals.add(bestEval);
        }

        int maxEval = Collections.max(minimaxEvals);
        int maxIndex = minimaxEvals.indexOf(maxEval);
        return curPossibleMoves.get(maxIndex);
    }

    // setCurState to be called before initially calling minimax
    // EFFECTS: recursively calls itself to measure heuristic 
    //          evaluation for moves.
    @SuppressWarnings("methodlength")
    public int minimax(Board state, int depth, int alpha, int beta, boolean maximizingPlayer) {

        boolean gameStatus = state.isGameOver();
        if (depth == 0 || gameStatus) {
            return heuristicEvaluation(state);
        }

        Board loopState;
        if (maximizingPlayer) {
            int maxEval = -10000;
            for (IndexTuple moveIndex: state.getPossibleMoves(botTurn)) {
                loopState = new Board(state.getboardMatrix()); // deep copies
                loopState.makeMove(botTurn, moveIndex.getRow(), moveIndex.getCol());
                int eval = minimax(loopState, depth - 1, alpha, beta, false);
                maxEval = Math.max(eval,maxEval);
                alpha = Math.max(alpha,eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = 10000;
            for (IndexTuple moveIndex: state.getPossibleMoves(playerTurn)) {
                loopState = new Board(state.getboardMatrix()); // deep copies
                loopState.makeMove(playerTurn, moveIndex.getRow(), moveIndex.getCol());
                int eval = minimax(loopState,depth - 1, alpha, beta, true);
                minEval = Math.min(eval,minEval);
                beta = Math.min(beta,eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }

    }







    // EFFECTS: implements a heuristic evaluation of curState 
    //          base on linear combination of things which
    //          should be considered.
    private int heuristicEvaluation(Board curState) {
        return parityWeight * diskParityEvaluation(curState) + mobilityWeight * mobilityEvaluation(curState) 
            + stabilityWeight * stabilityEvaluation(curState) + gameStatusWeight + gameStatusEvaluation(curState);
    }

    // EFFECTS: returns difference between black and white states.
    private int diskParityEvaluation(Board curState) {
        return curState.getWhiteDiskCount() - curState.getBlackDiskCount();
    }

    // EFFECTS: returns difference betweemn number of possibleMoves.
    private int mobilityEvaluation(Board curState) {
        return curState.getPossibleMoves(botTurn).size() - curState.getPossibleMoves(playerTurn).size();
    }

    // EFFECTS: difference in stability between 
    //          the bot and player's pieces.
    private int stabilityEvaluation(Board curState) {
        assert this.staticWeights != null;
        return calculateStability(curState,botTurn) - calculateStability(curState,playerTurn);
    }
    
    // EFFECTS: 
    private int calculateStability(Board curState,char turn) {
        int stability = 0;

        for (IndexTuple pos: curState.getDiskIndices(turn)) {
            stability += this.staticWeights[pos.getRow()][pos.getCol()];
        }

        return stability;
    }


    // // EFFECTS: If game not over returns 0. Else gets winner.
    // //          Returns 1 if winner is White and -1 if Black, 0 if tie.
    // private int gameStatusEvaluation(Board curState) {
    //     if (curState.isGameOver()) {
    //         char winner = curState.getWinner();
    //         if (winner == 'W') {
    //             return 1;
    //         } else if (winner == 'B') {
    //             return -1;
    //         } else {
    //             return 0;
    //         }
    //     }

    //     return 0;
    // }


    // EFFECTS: If game not over returns 0. Else gets winner.
    //          Returns 1 if winner is White and -1 if Black, 0 if tie.
    private int gameStatusEvaluation(Board curState) {
        if (curState.isGameOver()) {
            char winner = curState.getWinner();
            if (winner == this.botTurn) {
                return 1;
            } else if (winner == this.playerTurn) {
                return -1;
            } else {
                return 0;
            }
        }

        return 0;
    }


    // REQUIRED: called from constructor only
    // EFFECTS: sets stability matrix. 
    private void setStaticWeights() {
        this.setOuterShell();
        this.set2ndShell();
        this.setThirdShell();
        this.setCoreShell();
    }



    // REQUIRED: called from setStaticWeights only
    private void setOuterShell() {
        List<Integer> shell1 = new ArrayList<Integer>() {{
                add(4);
                add(-3);
                add(2);
                add(2);
                add(2);
                add(2);
                add(-3);
                add(4);
            }};

        for (int n = 0; n < 8; n++) {
            this.staticWeights[0][n] = shell1.get(n);
        }
        for (int n = 0; n < 8; n++) {
            this.staticWeights[7][n] = shell1.get(n);
        }
        for (int n = 0; n < 8; n++) {
            this.staticWeights[n][0] = shell1.get(n);
        }
        for (int n = 0; n < 8; n++) {
            this.staticWeights[n][7] = shell1.get(n);
        }
    }

    // REQUIRED: called from setStaticWeights only
    private void set2ndShell() {

        List<Integer> shell2 = new ArrayList<Integer>() {{
                add(-4);
                add(-1);
                add(-1);
                add(-1);
                add(-1);
                add(-4);
            }};
                
        for (int n = 1; n < 7; n++) {
            this.staticWeights[1][n] = shell2.get(n - 1);
        }
        for (int n = 1; n < 7; n++) {
            this.staticWeights[6][n] = shell2.get(n - 1);
        }
        for (int n = 1; n < 7; n++) {
            this.staticWeights[n][1] = shell2.get(n - 1);
        }
        for (int n = 1; n < 7; n++) {
            this.staticWeights[n][6] = shell2.get(n - 1);
        }
    }

    // REQUIRED: called from setStaticWeights only
    private void setThirdShell() {

        List<Integer> shell3 = new ArrayList<Integer>() {{
                add(1);
                add(0);
                add(0);
                add(1);
            }};
        for (int n = 2; n < 6; n++) {
            this.staticWeights[2][n] = shell3.get(n - 2);
        }
        for (int n = 2; n < 6; n++) {
            this.staticWeights[5][n] = shell3.get(n - 2);
        }
        for (int n = 2; n < 6; n++) {
            this.staticWeights[n][2] = shell3.get(n - 2);
        }
        for (int n = 2; n < 6; n++) {
            this.staticWeights[n][5] = shell3.get(n - 2);
        }
    }

    // REQUIRED: called from setStaticWeights only
    private void setCoreShell() {
        this.staticWeights[3][3] = 1;
        this.staticWeights[3][4] = 1;
        this.staticWeights[4][3] = 1;
        this.staticWeights[4][4] = 1;
    }


}
