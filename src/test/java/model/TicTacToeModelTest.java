package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Tic tac toe model test.
 */
public class TicTacToeModelTest {


    /**
     * Play.
     */
    @Test
    public void play() {
        TicTacToeModel model = new TicTacToeModel();
        assertTrue(model.play(0, 0));
        assertTrue(model.play(0, 1));
        assertFalse(model.play(0, 0));
    }


    /**
     * Gets value of grid.
     */
    @Test
    public void getValueOfGrid() {
        TicTacToeModel model = new TicTacToeModel();
        model.play(0, 0); // O
        model.play(0, 1); // X
        model.play(1, 0); // O
        assertEquals("O", model.getValueOfGrid(0, 0));
        assertEquals("X", model.getValueOfGrid(0, 1));
        assertEquals("O", model.getValueOfGrid(1, 0));
    }

    /**
     * Gets status.
     */
    @Test
    public void getStatus() {
        TicTacToeModel model;
        // Check For InGame Status
        model = new TicTacToeModel();
        model.play(0, 0); // O
        assertEquals("O has made a turn check GameStatus", Status.IN_GAME, model.getStatus());
        model.play(1, 1); // X
        assertEquals("X has made a turn check GameStatus", Status.IN_GAME, model.getStatus());
        model.play(1, 2); // O
        assertEquals("X has made a turn check GameStatus", Status.IN_GAME, model.getStatus());

        // Diagonal Winner - O Winner
        model = new TicTacToeModel();
        model.play(0, 0); // O
        model.play(1, 0); // X
        model.play(1, 1); // O
        model.play(0, 1); // X
        model.play(2, 2); // O
        assertEquals("There should be a winner", Status.O_WON, model.getStatus());

        // Diagonal Winner - X Winner
        model = new TicTacToeModel();
        model.play(2, 1); // O
        model.play(2, 0); // X
        model.play(1, 0); // O
        model.play(1, 1); // X
        model.play(2, 2); // O
        model.play(0, 2); // X
        assertEquals("There should be a winner", Status.X_WON, model.getStatus());

        // Row Winner
        model = new TicTacToeModel();
        model.play(0, 0); // O
        model.play(1, 1); // X
        model.play(1, 0); // O
        model.play(0, 1); // X
        model.play(2, 0); // O
        assertEquals("There should be a winner", Status.O_WON, model.getStatus());


        // Column Winner
        model = new TicTacToeModel();
        model.play(0, 0); // O
        model.play(1, 1); // X
        model.play(0, 1); // O
        model.play(2, 1); // X
        model.play(0, 2); // O
        assertEquals("There should be a winner", Status.O_WON, model.getStatus());

        // Tie Game
        model = new TicTacToeModel();
        model.play(0, 0); // O
        model.play(1, 0); // X
        model.play(2, 0); // O

        model.play(1, 1); // X
        model.play(0, 1); // O
        model.play(2, 2); // X

        model.play(2, 1); // O
        model.play(0, 2); // X
        model.play(1, 2); // O
        assertEquals("There should be a TIE", Status.TIE, model.getStatus());


    }

    /**
     * Is turn.
     */
    @Test
    public void isTurn() {


    }
}