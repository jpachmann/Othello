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

        for (int x = x1 + 1; x < upperboundX; x++) {
            for (int y = y1 + 1; y < upperboundY; y++) {
                flipPiece(x,y);
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
            throw new IllegalStateException("EMPTY FIELDS CAN'T BE FLIPPED");
        }
    }
}
