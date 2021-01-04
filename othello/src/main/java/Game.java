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

        player1.planNextMove(findBestMove(player1));
        player2.planNextMove(findBestMove(player2));

        player1.setActivePlayer(true);
    }

    public void makeMove() {
        Move move = getActivePlayer().getNextMove();
        if (gameOver) {
            throw new IllegalStateException();
        }
        if (!isLegalMove(move)){
            gameOver = true;
            setWinner();
        }
        else {
            board.setPiece(getActivePlayer().getColour(), move.getX(), move.getY());

            getActivePlayer().setActivePlayer(false);
            getOpponent(getActivePlayer()).setActivePlayer(true);

            getActivePlayer().planNextMove(findBestMove(getActivePlayer()));

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
        else if (board.getBoard()[move.getX() - 1][move.getY() - 1] != EMPTY) {
            return false; //Occupied
        }
        else if (flipsOpponent(move)){
            return false; //Enemy has to be flipped with every turn
        }
        return true;
    }

    private boolean outOfBounds(Move move) {
        return (0 >= move.getX() || move.getX() > BOARDSIZE || 0 >= move.getY() || move.getY() > BOARDSIZE);
    }

    private boolean flipsOpponent(Move move){
        int[][] neighbours = lookAround(move); // max. 8 Neighbours (including self) in each of 8 directions
        return false;
    }

    private int[][] lookAround(Move move) {
        int[][] neighbours = new int[8][8];

        for (int i = 0; i < 8; i++) {
            neighbours[i][NORTH_WEST] = findNeighbour(move, i, NORTH_WEST);
            neighbours[i][NORTH] = findNeighbour(move, i, NORTH);
            neighbours[i][NORTH_EAST] = findNeighbour(move, i, NORTH_EAST);
            neighbours[i][EAST] = findNeighbour(move, i, EAST);
            neighbours[i][SOUTH_EAST] = findNeighbour(move, i, SOUTH_EAST);
            neighbours[i][SOUTH] = findNeighbour(move, i, SOUTH);
            neighbours[i][SOUTH_WEST] = findNeighbour(move, i, SOUTH_WEST);
            neighbours[i][WEST] = findNeighbour(move, i, WEST);

            System.out.println("neighbours of " + move.getX() + ", " + move.getY() + " with distance " +
                    i + ": " + Arrays.toString(neighbours[i]));
        }

        return neighbours;
    }

    private int findNeighbour(Move position, int distance, int direction) {
        int boardOccupiedBy = -10;
        Move move = new Move(position.getX(), position.getY());

        switch (direction){
            case NORTH_WEST:
                move.setX(position.getX() - distance);
                move.setY(position.getY() - distance);

            case NORTH:
                move.setX(position.getX() - distance);
                move.setY(position.getY());

            case NORTH_EAST:
                move.setX(position.getX() - distance);
                move.setY(position.getY() + distance);

            case EAST:
                move.setX(position.getX());
                move.setY(position.getY() + distance);

            case SOUTH_EAST:
                move.setX(position.getX() + distance);
                move.setY(position.getY() + distance);

            case SOUTH:
                move.setX(position.getX() + distance);
                move.setY(position.getY());

            case SOUTH_WEST:
                move.setX(position.getX() + distance);
                move.setY(position.getY() - distance);

            case WEST:
                move.setX(position.getX());
                move.setY(position.getY() - distance);
        }

        if (!outOfBounds(move)) {
            boardOccupiedBy = board.getBoard()[move.getX()][move.getY()];
        }

        return boardOccupiedBy;
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
