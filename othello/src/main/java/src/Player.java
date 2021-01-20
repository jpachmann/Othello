package src;

import java.util.Random;

/**
 * Simulates a Player with all necessary attributes (such as color) and actions (such as planing the next move).
 * @author Jakob Pachmann
 * @version %I%, %G%
 */
public class Player {
    private final String playerRole;
    private final int colour;
    private Move nextMove;
    private boolean isActivePlayer;
    private Random rand;

    /**
     * Class Constructor
     * @param playerRole Human or AI
     * @param colour Black or White
     */
    public Player(String playerRole, int colour){
        this.playerRole = playerRole;
        this.colour = colour;
        rand = new Random();
    }

    /**
     * Players can be Black or White
     * @return "BLACK" or "WHITE"
     */
    public int getColour() {
        return colour;
    }

    /**
     * Players can be controlled by a Human or an AI
     * @return "AI" or "HUMAN"
     */
    public String getPlayerRole() {
        return playerRole;
    }

    /**
     * plans next move
     */
    public void planNextMove(Move nextMove) {
        this.nextMove = nextMove;
    }

    /**
     * Gets next move
     * @return next move
     */
    public Move getNextMove() {
        return nextMove;
    }

    public boolean isActivePlayer() {
        return isActivePlayer;
    }

    public void setPlayerActive(boolean activePlayer) {
        isActivePlayer = activePlayer;
    }

}
