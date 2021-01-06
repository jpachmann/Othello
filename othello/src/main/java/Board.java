import static Constants.Constants.*;

public class Board {

    private int[][] boardObject;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        boardObject = new int[BOARDSIZE][BOARDSIZE];
        for(int x = 0; x < BOARDSIZE; x++){
            for(int y = 0; y < BOARDSIZE; y++){
                boardObject[x][y] = EMPTY;
            }
        }
        boardObject[BOARDSIZE/2 - 1][BOARDSIZE/2 - 1] = BLACK;
        boardObject[BOARDSIZE/2][BOARDSIZE/2 - 1] = WHITE;
        boardObject[BOARDSIZE/2 - 1][BOARDSIZE/2] = WHITE;
        boardObject[BOARDSIZE/2][BOARDSIZE/2] = BLACK;
    }

    public int[][] getBoard() {
        return boardObject;
    }

    public void setPiece(int piece, int x, int y) {
        boardObject[x][y] = piece;
    }

    public void printBoard(){
        System.out.println("Black: " + BLACK + ", White: " + WHITE);
        int y = 0;
        int x = 0;
        while(y < BOARDSIZE){
            while (x < BOARDSIZE){
                System.out.print(boardObject[x][y]);
                System.out.print("\t");
                x++;
            }
            x = 0;
            System.out.println();
            y++;
        }
    }

    public void flipEnclosed(int x1, int y1, int x2, int y2){

        int upperboundX = Math.max(x1, x2);
        int upperboundY = Math.max(y1, y2);
        int lowerboundX = Math.min(x1, x2);
        int lowerboundY = Math.min(y1, y2);

        System.out.println("Trying to flip between " + x1 + "," + y1 + " and " + x2 + "," + y2);

        if (x1 - x2 == 0) { //find vertically enclosed
            for (int y = lowerboundY + 1; y < upperboundY; y++) {
                flipPiece(x1, y);
            }
        }
        else if (y1 - y2 == 0) { //find horizontally enclosed
            for (int x = lowerboundX + 1; x < upperboundX; x++) {
                flipPiece(x, y1);
            }
        }
        else { //find diagonally enclosed

            if ((x1 - x2 < 0 && y1 - y2 > 0) || (x1 - x2 > 0 && y1 - y2 < 0 )) { //lower left to upper right
                lowerboundX++; //exclude itself
                upperboundY--; //exclude itself

                while (lowerboundX < upperboundX){
                    flipPiece(lowerboundX, upperboundY);
                    lowerboundX++;
                    upperboundY--;
                }
            }
            else { //lower right to upper left
                lowerboundX++; //exclude itself
                lowerboundY++; //exclude itself

                while (lowerboundX < upperboundX){
                    flipPiece(lowerboundX, lowerboundY);
                    lowerboundX++;
                    lowerboundY++;
                }
            }
        }
    }

    public void flipPiece(int x, int y) {
        if (boardObject[x][y] == BLACK) {
            setPiece(WHITE, x, y);
        }
        else if (boardObject[x][y] == WHITE) {
            setPiece(BLACK, x, y);
        }
        else {
            System.out.println("Tried to flip " + x + "," + y + " which should be empty");
            throw new IllegalStateException("EMPTY FIELDS CAN'T BE FLIPPED");
        }
    }

    public int countPoints(int color) {
        int points = 0;
        for (int x = 0; x < BOARDSIZE; x++) {
            for (int y = 0; y < BOARDSIZE; y++) {
                if (boardObject[x][y] == color) {
                    points++;
                }
            }
        }
        return points;
    }
}
