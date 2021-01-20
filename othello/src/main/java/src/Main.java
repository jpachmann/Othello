package src;

import static src.constants.Constants.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        game.addPlayer(AI, WHITE);
        game.addPlayer(AI, BLACK);

        game.startNewGame();

        while(!game.isGameOver()) {
            game.makeMove();
        }
    }
}
