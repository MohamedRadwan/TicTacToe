package controller;

import model.TicTacToeModel;

/**
 * The type Tic tac toe controller.
 *
 * @author Mohamed Radwan
 */
public class TicTacToeController {

    /**
     * A model Object.
     */
    private TicTacToeModel model;

    /**
     * Instantiates a new Tic tac toe controller.
     *
     * @param model the model
     */
    public TicTacToeController(TicTacToeModel model) {
        this.model = model;
    }

    /**
     * Registers the move on the model object.
     *
     * @param x the x position
     * @param y the y position
     */
    public void move(int x, int y) {
        model.play(x, y);
    }

    /**
     * Load a saved tic tac toe model.
     *
     * @param newModel the new model
     * @return the tic tac toe model
     */
    public TicTacToeModel load(TicTacToeModel newModel) {
        return this.model = newModel;
    }

    /**
     * Reset tic tac toe model.
     *
     * @return the tic tac toe model
     */
    public TicTacToeModel reset() {
        return this.model = new TicTacToeModel();
    }


}
