package model;

/**
 * The enum Status.
 *
 * @author Mohamed Radwan
 */
public enum Status {

    /**
     * In game.
     */
    IN_GAME("Game in progress"),
    /**
     * X won.
     */
    X_WON("X won the game"),
    /**
     * O won.
     */
    O_WON("O won the game"),
    /**
     * Tie.
     */
    TIE("Game is a Tie");

    /**
     * Represents the String value of each enum.
     */
    private String value;

    /**
     * Constructor for the enum class to pair each enum with a string value.
     *
     * @param value
     */
    Status(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
