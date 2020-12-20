

public class Board {
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final int EMPTY = -1;

    public static final int BOARDSIZE = 8;

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

    public void printBoard(){
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
}
