package src;

import java.util.Random;
import static src.constants.Constants.*;

public class AI {
    String kind;
    Random rand;

    public AI (String kind){
        this.kind = kind;
        rand = new Random();
    }

    public void makeMove() {
        switch (kind) {
            case RANDOM_AI -> findRandomMove();
            case GREEDY_AI -> findGreedyMove();
            default -> throw new IllegalArgumentException(kind + " is not (yet) supported");
        }
    }

    private void findGreedyMove() {
    }

    private void findRandomMove() {
    }


}
