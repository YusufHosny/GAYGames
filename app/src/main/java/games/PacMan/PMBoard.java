package games.PacMan;

public class PMBoard {
    private static int[][] PMBoard;

    public PMBoard(){
        // 0 = coin, 1 = wall, 6 = PacMan, 9 = empty tile
        // 2 = red ghost, 3 = pink ghost, 4 = orange ghost, 5 = blue ghost
        PMBoard = new int[][]{
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,0,1,1,0,0,0,0,1,1,0,1,1,1,1,0,1,1,0,1},
                {1,0,1,1,0,0,0,0,0,0,1,1,0,1,1,0,1,1,0,0,0,0,0,0,1,1,0,1},
                {1,0,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,0,1},
                {1,0,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
                {1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1},
                {9,9,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,9,9},
                {9,9,1,0,0,0,0,1,1,9,9,9,9,9,9,9,9,9,9,1,1,0,0,0,0,1,9,9},
                {1,1,1,0,1,1,0,1,1,9,1,1,1,9,9,1,1,1,9,1,1,0,1,1,0,1,1,1},
                {0,0,0,0,1,1,0,1,1,9,1,9,9,9,9,9,9,1,9,1,1,0,1,1,0,0,0,0},
                {1,1,1,1,1,1,0,0,0,9,1,9,9,9,9,9,9,1,9,0,0,0,1,1,1,1,1,1},
                {1,1,1,1,1,1,0,1,1,9,1,9,9,9,9,9,9,1,9,1,1,0,1,1,1,1,1,1},
                {0,0,0,0,1,1,0,1,1,9,1,1,1,1,1,1,1,1,9,1,1,0,1,1,0,0,0,0},
                {1,1,1,0,1,1,0,1,1,9,9,9,9,9,9,9,9,9,9,1,1,0,1,1,0,1,1,1},
                {9,9,1,0,0,0,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,0,0,0,1,9,9},
                {9,9,1,0,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,0,1,9,9},
                {9,9,1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,0,1,9,9},
                {9,9,1,0,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,0,1,9,9},
                {1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
                {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
                {1,0,1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1},
                {1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,0,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        };
    }

    public static int[][] getPMBoard(){
        // static factory method that creates one universal instance of the board
        if (PMBoard == null){
            PMBoard brd= new PMBoard();
        }
        return PMBoard;
    }


}
