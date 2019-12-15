package model;

/**
 * The interface Board listener.
 *
 * @author Mohamed Radwan
 */
public interface BoardListener {
    /**
     * Handle tic tac toe event.
     *
     * @param event the event
     */
    void handleTicTacToeEvent(TicTacToeEvent event);
}
