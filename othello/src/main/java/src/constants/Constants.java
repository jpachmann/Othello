package src.constants;

/**
 * Constants to avoid redefinition
 */
public class Constants {

    // Pieces on the board
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final int EMPTY = -1;
    public static final int OUT_OF_BOUNDS = 10;

    // Board-properties
    public static final int BOARDSIZE = 8;

    // Player-properties
    public static final String AI = "ai";
    public static final String HUMAN = "human";

    // AI-properties
    public static final String RANDOM_AI = "random_ai";
    public static final String GREEDY_AI = "greedy_ai";
    public static final String PERFECT_AI = "perfect_ai";

    // Move related
    public static final int NORTH_WEST = 0;
    public static final int NORTH = 1;
    public static final int NORTH_EAST = 2;
    public static final int EAST = 3;
    public static final int SOUTH_EAST = 4;
    public static final int SOUTH = 5;
    public static final int SOUTH_WEST = 6;
    public static final int WEST = 7;

}
