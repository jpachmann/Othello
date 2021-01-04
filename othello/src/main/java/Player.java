/**
 * Player.java
 * Includes Information about Player such as Identity and its next move
 */

public class Player {
    private String playerRole;
    private int colour;
    private Move nextMove;
    private boolean isActivePlayer;

    /**
     * Sets necessary Parameters
     * @param playerRole Human or AI
     * @param colour Black or White
     */
    public Player(String playerRole, int colour){
        this.playerRole = playerRole;
        this.colour = colour;
    }

    /**
     * Players can be Black or White
     * @return "BLACK" or "WHITE"
     */
    public int getColour() {
        return colour;
    }

    /**
     * Players can be controlled by Human or AI
     * @return "AI" or "HUMAN"
     */
    public String getPlayerRole() {
        return playerRole;
    }

    /**
     * Setter for next move
     * @param move next move
     */
    public void planNextMove(Move move) {
        nextMove = move;
    }

    /**
     * Getter for next move
     * @return next move
     */
    public Move getNextMove() {
        return nextMove;
    }

    public boolean isActivePlayer() {
        return isActivePlayer;
    }

    public void setActivePlayer(boolean activePlayer) {
        isActivePlayer = activePlayer;
    }
}
