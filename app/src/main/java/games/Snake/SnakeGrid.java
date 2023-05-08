package games.Snake;

import java.util.Random;

public class SnakeGrid {
    private Tile[][] snakeGrid;
    private int initialXPosition;

    public SnakeGrid(int rows, int cols) {
        snakeGrid = new Tile[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Tile EmptyTile = new EmptyTile(x, y);
                snakeGrid[x][y] = EmptyTile;
            }
        }
        initialXPosition=placeSnake();
        generateFruitTile();

    }

    public Tile[][] getSnakeGrid() {
        return snakeGrid;
    }

    public void generateFruitTile() {
        Random R = new Random();
        int x= R.nextInt(snakeGrid.length);
        int y= R.nextInt(snakeGrid[0].length);

        if (snakeGrid[x][y] instanceof EmptyTile && !(snakeGrid[x][y] instanceof SnakeTile)){
            snakeGrid[x][y]=new FruitTile(x,y);
                }

    }
    public int placeSnake(){
        Random R = new Random();
        int y= R.nextInt(snakeGrid[0].length);
        snakeGrid[snakeGrid.length-3][y] = new SnakeTile(snakeGrid.length-3,y);
        snakeGrid[snakeGrid.length-2][y] = new SnakeTile(snakeGrid.length-2,y);
        snakeGrid[snakeGrid.length-1][y] = new SnakeTile(snakeGrid.length-1,y);
        return y;
    }

    public int getInitialXPosition(){
        return initialXPosition;
    }
}
