package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The model of the tictactoe game.
 *
 * @author Mohamed Radwan
 */
public class TicTacToeModel implements Serializable {
    /**
     * The constant SIZE.
     */
    public final static int SIZE = 3;
    /**
     * The constant xString.
     */
    public final static String xString = "X";
    /**
     * The constant oString.
     */
    public final static String oString = "O";
    /**
     * The grid that represents the board.
     */
    private String[][] grid;
    /**
     * This will be used to determine the players turn. O player if true is true and X player if true is false.
     */
    private boolean turn;
    /**
     * Current game status represented in an enum.
     */
    private Status status;
    /**
     * transient so that it wont  be serializable.
     */
    private transient List<BoardListener> listeners;
    /**
     * Number of turns that have been played.
     */
    private int numberOfTurns;

    /**
     * Instantiates a new Tic tac toe model.
     */
    public TicTacToeModel() {
        this.status = Status.IN_GAME;
        this.turn = true;
        this.grid = new String[SIZE][SIZE];
        this.listeners = new ArrayList<>();
        this.numberOfTurns = 0;
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                grid[x][y] = " ";
            }
        }

    }

    /**
     * Performs plays on the board given a location.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean play(int x, int y) {
        if (grid[x][y].equals(" ")) {
            if (turn) {
                grid[x][y] = oString;
                this.numberOfTurns++;
                turn = false;
            } else {
                grid[x][y] = xString;
                turn = true;
                this.numberOfTurns++;
            }
            updateStatus();
            notifyListeners(x, y);
            return true;
        }
        return false;
    }

    /**
     * Checks and updates the game status.
     */
    private void updateStatus() {
        if (numberOfTurns < 3) {
            return;
        }
        // Diagonal check
        if (grid[0][0].equals(xString) && grid[1][1].equals(xString) && grid[2][2].equals(xString)) {
            status = Status.X_WON;
        } else if (grid[0][0].equals(oString) && grid[1][1].equals(oString) && grid[2][2].equals(oString)) {
            status = Status.O_WON;
        } else if (grid[2][0].equals(xString) && grid[1][1].equals(xString) && grid[0][2].equals(xString)) {
            status = Status.X_WON;
        } else if (grid[2][0].equals(oString) && grid[1][1].equals(oString) && grid[0][2].equals(oString)) {
            status = Status.O_WON;
        } else {
            // Check Each row
            for (int i = 0; i < SIZE; i++) {
                // Horizontal check
                if (grid[0][i].equals(oString) && grid[1][i].equals(oString) && grid[2][i].equals(oString)) {
                    status = Status.O_WON;
                } else if (grid[0][i].equals(xString) && grid[1][i].equals(xString) && grid[2][i].equals(xString)) {
                    status = Status.X_WON;
                    // Vertical check
                } else if (grid[i][0].equals(oString) && grid[i][1].equals(oString) && grid[i][2].equals(oString)) {
                    status = Status.O_WON;
                } else if (grid[i][0].equals(xString) && grid[i][1].equals(xString) && grid[i][2].equals(xString)) {
                    status = Status.X_WON;
                }
            }
        }
        // check if its a tie
        if (numberOfTurns == SIZE * SIZE && status == Status.IN_GAME) {
            status = Status.TIE;
            return;
        }
    }

    /**
     * Add listeners.
     *
     * @param listener the listener
     */
    public void addListeners(BoardListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * New board listener list. Generate a new List when a saved board is loaded again.
     */
    public void newBoardListenerList() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Gets value of grid.
     *
     * @param x the x
     * @param y the y
     * @return the value of grid
     */
    public String getValueOfGrid(int x, int y) {
        return grid[x][y];
    }

    /**
     * Gets the current game status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets the current players turn.
     *
     * @return the boolean true if O and false if X
     */
    public boolean isTurn() {
        return turn;
    }

    /**
     * notifies all the listeners.
     *
     * @param x - location on the board
     * @param y - location on the board
     */
    private void notifyListeners(int x, int y) {
        for (BoardListener listener : listeners) {
            listener.handleTicTacToeEvent(new TicTacToeEvent(this, x, y, turn, status));
        }
    }
}
