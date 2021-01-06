import java.util.Arrays;
import java.util.Random;

import static Constants.Constants.*;

public class Game {
    private Board board;

    private Player player1;
    private Player player2;

    private Player[] playerList = new Player[2];

    private int winner = -1;
    Random rand;

    private boolean gameOver;

    public Game(){
        board = new Board();
        player1 = new Player(HUMAN, BLACK);
        player2 = new Player(HUMAN, WHITE);

        rand = new Random();

        playerList[0] = player1;
        playerList[1] = player2;

        player1.setActivePlayer(true);

        player1.planNextMove(findBestMove(player1));
        takeTurn();
        player2.planNextMove(findBestMove(player2));
        takeTurn();
    }

    public void makeMove() {
        Move move = getActivePlayer().getNextMove();
        if (gameOver) {
            throw new IllegalStateException();
        }
        if (!isLegalMove(move)){
            gameOver = true;
            setWinner();
            System.out.println("GAME IS OVER!");
        }
        else {
            board.setPiece(getActivePlayer().getColour(), move.getX(), move.getY());

            getActivePlayer().setActivePlayer(false);
            getOpponent(getActivePlayer()).setActivePlayer(true);

            getActivePlayer().planNextMove(findBestMove(getActivePlayer()));

            board.printBoard();
        }
    }

    public void simulateMove(int x, int y) {
        Move move = new Move(x, y);

        if (gameOver) {
            throw new IllegalStateException();
        }
        if (!isLegalMove(move)){
            gameOver = true;
            setWinner();
            System.out.println("GAME IS OVER!");
        }
        else {
            board.setPiece(getActivePlayer().getColour(), move.getX(), move.getY());

            System.out.println("Player " + getActivePlayer().getColour() + " set his piece on (" + move.getX() + "," +
                    move.getY() + ")");

            takeTurn();
            board.printBoard();
        }
    }

    private Move findBestMove(Player activePlayer) {
        int x = rand.nextInt(BOARDSIZE);
        int y = rand.nextInt(BOARDSIZE);

        Move move = new Move(x, y);

        while (!isLegalMove(move)) {
            move.setX(rand.nextInt(BOARDSIZE));
            move.setY(rand.nextInt(BOARDSIZE));
        }

        return move;
    }

    private boolean isLegalMove(Move move){
        if (outOfBounds(move)) {
            return false; //Out of bounds
        }
        else if (board.getBoard()[move.getX()][move.getY()] != EMPTY) {
            return false; //Occupied
        }
        else if (!flipsOpponent(move)){
            return false; //Enemy has to be flipped with every turn
        }
        return true;
    }

    private boolean outOfBounds(Move move) {
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

    private boolean flipsOpponent(Move move){
        int[][] neighbours = lookAround(move); // max. 8 Neighbours (including self) in each of 8 directions

        int enemyColor = WHITE;
        int playerColor = getActivePlayer().getColour();

        if (playerColor == WHITE) {
            enemyColor = BLACK;
        }

        String pattern = "\\d";

        System.out.println("selbst: " + neighbours[0][0]);
        System.out.println("1 links oben: " + neighbours[0][1]);
        System.out.println("2 links oben: " + neighbours[0][2]);

        for (int i = 0; i < 8; i++) { // direction (north, northeast etc.)
            boolean canBeFlipped = true;

            int[] x = neighbours[i];
            String neighboursInDirection = Arrays.toString(x);


            if (neighbours[i][1] == enemyColor) {
                for (int n = 2; n < 8; n++) { // depth; "follow that direction"

                    if (neighbours[i][n] == EMPTY) { // no connecting piece
                        System.out.println("No connecting piece" + ", n = " + n + ", i = " + i);
                        canBeFlipped = false;
                    }

                    if (neighbours[i][n] == OUT_OF_BOUNDS) { // out of bounds
                        System.out.println("Out of bounds");
                        canBeFlipped = false;
                    }

                    if (neighbours[i][n] == playerColor && canBeFlipped) {
                        board.flipEnclosed(move.getX(), move.getY(), go(move, n, i).getX(), go(move, n, i).getY());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int[][] lookAround(Move move) {
        int[][] neighbours = new int[8][8];

        for (int i = 0; i < 8; i++) {
            neighbours[NORTH_WEST][i] = findNeighbour(move, i, NORTH_WEST);
            neighbours[NORTH][i] = findNeighbour(move, i, NORTH);
            neighbours[NORTH_EAST][i] = findNeighbour(move, i, NORTH_EAST);
            neighbours[EAST][i] = findNeighbour(move, i, EAST);
            neighbours[SOUTH_EAST][i] = findNeighbour(move, i, SOUTH_EAST);
            neighbours[SOUTH][i] = findNeighbour(move, i, SOUTH);
            neighbours[SOUTH_WEST][i] = findNeighbour(move, i, SOUTH_WEST);
            neighbours[WEST][i] = findNeighbour(move, i, WEST);
        }

        return neighbours;
    }

    private int findNeighbour(Move position, int distance, int direction) {
        int boardOccupiedBy = OUT_OF_BOUNDS;

        int x = go(position, distance, direction).getX();
        int y = go(position, distance, direction).getY();

        Move move = new Move(x,y);

        if (!outOfBounds(move)) {
            boardOccupiedBy = board.getBoard()[move.getX()][move.getY()];
        }

        return boardOccupiedBy;
    }

    private Move go(Move position, int distance, int direction) {

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

    private void takeTurn(){
        if (player1.isActivePlayer()){
            player1.setActivePlayer(false);
            player2.setActivePlayer(true);
        }
        else if (player2.isActivePlayer()){
            player2.setActivePlayer(false);
            player1.setActivePlayer(true);
        }
        else {
            throw new IllegalStateException();
        }
    }

    private void setWinner(){
        winner = -1;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getActivePlayer() {
        if (player1.isActivePlayer()){
            return player1;
        }
        return player2;
    }

    public Player getOpponent(Player player) {
        if(player.equals(playerList[0])){
            return playerList[1];
        }
        return playerList[0];
    }

    public int getWinner() {
        return winner;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
