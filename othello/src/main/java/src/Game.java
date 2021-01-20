package src;

import java.util.ArrayList;

import static src.GameLogic.*;
import static src.constants.Constants.*;
import static src.strategies.SimpleStrategies.*;

public class Game{
    private Board board;

    private Player player1;
    private Player player2;

    private ArrayList<Player> playerList = new ArrayList<>();

    private int winner = -1;
    private boolean gameOver;

    public void addPlayer(String playerRole, int color){
        if (color == BLACK) {
            player1 = new Player(playerRole, color);
            playerList.add(player1);
        }
        else {
            player2 = new Player(playerRole, color);
            playerList.add(player2);
        }
    }

    public void startNewGame(){
        player1.setPlayerActive(true);
        board = new Board();
    }

    public void makeMove() {
        if (getActivePlayer().getPlayerRole().equals(AI)) {
            getActivePlayer().planNextMove(findGreedyMove(board, getActivePlayer()));
        }
        Move move = getActivePlayer().getNextMove();

        if (isGameOver()) {
            announceWinner();
        }
        else if (move == null) {
            pass();
        }
        else if (!isLegalMove(move, getActivePlayer(), board)){
            gameOver = true;

            setWinner(getOpponent(getActivePlayer()).getColour());
            System.out.println("GG, Congratz to Player " + getWinner());
        }
        else {
            updateBoard(move, board, getActivePlayer().getColour());

            takeTurn();
            board.printBoard();
        }
    }

    private void pass() {
        System.out.println("ROUND PASSED");
        takeTurn();
        if (findAllLegalMoves(board, getActivePlayer()).isEmpty()) {
            gameOver = true;
            announceWinner();
        }
    }

    private void announceWinner() {
        int pointsBlack = board.countPoints(BLACK);
        int pointsWhite = board.countPoints(WHITE);

        System.out.println("Player " + BLACK + ": " + pointsBlack + " Points");
        System.out.println("Player " + WHITE + ": " + pointsWhite + " Points");

        setWinner(pointsBlack > pointsWhite ? BLACK : WHITE);
        System.out.println("GG, Congratz to Player " + getWinner());
    }

    private void takeTurn(){
        if (player1.isActivePlayer() && ! player2.isActivePlayer()){
            player1.setPlayerActive(false);
            player2.setPlayerActive(true);
        }
        else if (player2.isActivePlayer() && !player1.isActivePlayer()){
            player2.setPlayerActive(false);
            player1.setPlayerActive(true);
        }
        else {
            throw new IllegalStateException("Both or Neither player marked as active");
        }
    }

    private void setWinner(int winningColor){
        winner = winningColor;
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
        if(player.equals(playerList.get(0))){
            return playerList.get(1);
        }
        return playerList.get(0);
    }

    public int getWinner() {
        return winner;
    }
}
