package src;

import java.util.ArrayList;

import static src.constants.Constants.*;

public class GameLogic {

    public static boolean isLegalMove(Move move, Player player, Board board){

        if (isOutOfBounds(move)) {
            return false; //Out of bounds
        }
        else if (isOccupied(move, board)) {
            return false; //Occupied
        }
        else if (!flipsOpponent(move, board, player)){
            return false; //Enemy has to be flipped with every turn
        }
        return true;
    }

    public static boolean isOccupied(Move move, Board board) {
        return board.getBoard()[move.getX()][move.getY()] != EMPTY;
    }

    public static boolean isOutOfBounds(Move move) {
        if (move.getX() < 0 || move.getY() < 0) {
            return true;
        }
        else if (move.getX() >= BOARDSIZE || move.getY() >= BOARDSIZE) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean flipsOpponent(Move move, Board board, Player player){
        int[][] neighbours = lookAround(move, board); // max. 8 Neighbours (including self) in each of 8 directions

        int enemyColor = WHITE;
        int playerColor = player.getColour();

        if (playerColor == WHITE) {
            enemyColor = BLACK;
        }

        for (int i = 0; i < 8; i++) { // direction (north, northeast etc.)
            boolean canBeFlipped = true;

            if (neighbours[i][1] == enemyColor) {
                for (int n = 2; n < 8; n++) { // depth; "follow that direction"

                    if (neighbours[i][n] == EMPTY) { // no connecting piece
                        //System.out.println("No connecting piece" + ", n = " + n + ", i = " + i);
                        canBeFlipped = false;
                    }

                    if (neighbours[i][n] == OUT_OF_BOUNDS) { // out of bounds
                        //System.out.println("Out of bounds");
                        canBeFlipped = false;
                    }

                    if (neighbours[i][n] == playerColor && canBeFlipped) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<Move> findOpposing(Move move, int color, Board board) {
        int[][] neighbours = lookAround(move, board); // max. 8 Neighbours (including self) in each of 8 directions

        int enemyColor = WHITE;
        if (color == WHITE) {
            enemyColor = BLACK;
        }

        System.out.println("Move " + color + ": " + move.getX() + "," + move.getY());

        ArrayList<Move> opposingPieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) { // direction (north, northeast etc.)
            boolean canBeFlipped = true;

            if (neighbours[i][1] == enemyColor) {
                for (int n = 2; n < 8; n++) { // depth; "follow that direction"

                    if (neighbours[i][n] == EMPTY) { // no connecting piece
                        canBeFlipped = false;
                    }

                    if (neighbours[i][n] == OUT_OF_BOUNDS) { // out of bounds
                        canBeFlipped = false;
                    }

                    if (neighbours[i][n] == color && canBeFlipped) {
                        opposingPieces.add(go(move, n, i));
                        canBeFlipped = false;
                    }
                }
            }
        }
        return opposingPieces;
    }

    public static int[][] lookAround(Move move, Board board) {
        int[][] neighbours = new int[8][8];

        for (int i = 0; i < 8; i++) {
            neighbours[NORTH_WEST][i] = findNeighbour(move, i, NORTH_WEST, board);
            neighbours[NORTH][i] = findNeighbour(move, i, NORTH, board);
            neighbours[NORTH_EAST][i] = findNeighbour(move, i, NORTH_EAST, board);
            neighbours[EAST][i] = findNeighbour(move, i, EAST, board);
            neighbours[SOUTH_EAST][i] = findNeighbour(move, i, SOUTH_EAST, board);
            neighbours[SOUTH][i] = findNeighbour(move, i, SOUTH, board);
            neighbours[SOUTH_WEST][i] = findNeighbour(move, i, SOUTH_WEST, board);
            neighbours[WEST][i] = findNeighbour(move, i, WEST, board);
        }

        return neighbours;
    }

    public static int calculateDistance(Move move, int color, Board board) {
        int distance = 0;

        ArrayList<Move> opposing = findOpposing(move, color, board);

        for (Move opposingMove : opposing) {

            int distX = Math.abs(move.getX() - opposingMove.getX());
            int distY = Math.abs(move.getY() - opposingMove.getY());

            if (distX == 0) { //vertical
                distance += distY - 1;
            }
            else if (distY == 0) { //horizontal
                distance += distX - 1;
            }
            else { //diagonal, so distance between hor/vert is similar
                distance += distX - 1;
            }
        }
        System.out.println("Move " + move.getX() + "," + move.getY() + " would flip " + distance);
        return distance;
    }

    public static void updateBoard(Move move, Board board, int color) {
        if(move == null){
            return;
        }

        for (Move opposingPiece : findOpposing(move, color, board)) {
            board.flipEnclosed(move.getX(), move.getY(), opposingPiece.getX(), opposingPiece.getY());
        }
        board.setPiece(color, move.getX(), move.getY());
    }

    public static ArrayList<Move> findAllLegalMoves(Board board, Player player) {
        ArrayList<Move> candidates = new ArrayList<>();
        for (int x = 0; x < BOARDSIZE; x++) {
            for (int y = 0; y < BOARDSIZE; y++) {
                Move candidate = new Move(x,y);
                if (isLegalMove(candidate, player, board)) {
                    candidates.add(candidate);
                }
            }
        }
        return candidates;
    }

    public static int findNeighbour(Move position, int distance, int direction, Board board) {
        int boardOccupiedBy = OUT_OF_BOUNDS;

        int x = go(position, distance, direction).getX();
        int y = go(position, distance, direction).getY();

        Move move = new Move(x,y);

        if (!isOutOfBounds(move)) {
            boardOccupiedBy = board.getBoard()[move.getX()][move.getY()];
        }

        return boardOccupiedBy;
    }

    public static Move go(Move position, int distance, int direction) {

        int x = OUT_OF_BOUNDS;
        int y = OUT_OF_BOUNDS;

        switch (direction) {
            case NORTH_WEST -> {
                x = position.getX() - distance;
                y = position.getY() - distance;
            }
            case NORTH -> {
                x = position.getX();
                y = position.getY() - distance;
            }
            case NORTH_EAST -> {
                x = position.getX() + distance;
                y = position.getY() - distance;
            }
            case EAST -> {
                x = position.getX() + distance;
                y = position.getY();
            }
            case SOUTH_EAST -> {
                x = position.getX() + distance;
                y = position.getY() + distance;
            }
            case SOUTH -> {
                x = position.getX();
                y = position.getY() + distance;
            }
            case SOUTH_WEST -> {
                x = position.getX() - distance;
                y = position.getY() + distance;
            }
            case WEST -> {
                x = position.getX() - distance;
                y = position.getY();
            }
        }
        return new Move(x, y);
    }
}
