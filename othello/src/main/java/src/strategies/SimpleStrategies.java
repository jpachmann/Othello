package src.strategies;

import src.Board;
import src.Move;
import src.Player;

import java.util.ArrayList;
import java.util.Random;

import static src.GameLogic.calculateDistance;
import static src.GameLogic.findAllLegalMoves;

public class SimpleStrategies {

    public static Move findRandomMove(Board board, Player player) {
        ArrayList<Move> possibleMoves = findAllLegalMoves(board, player);

        Move randomMove = null;
        Random rand = new Random();

        if (!possibleMoves.isEmpty()) {
            randomMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
        }

        return randomMove;
    }

    public static Move findGreedyMove(Board board, Player player) {
        ArrayList<Move> possibleMoves = findAllLegalMoves(board, player);

        Move greedyMove = null;
        int mostGreedyMoveFlips = 0;

        if (!possibleMoves.isEmpty()) {

            for (Move move : possibleMoves) {
                int moveFlips = calculateDistance(move, player.getColour(), board);

                if (moveFlips > mostGreedyMoveFlips) {
                    mostGreedyMoveFlips = moveFlips;
                    greedyMove = move;
                }
            }
        }

        return greedyMove;
    }
}
