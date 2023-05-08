package games.Snake;

public abstract class Tile {
    private int tileRow;
    private int tileCol;

    public Tile(int row, int col){
        tileRow=row;
        tileCol=col;
    }
    public abstract String revealTile();

    //Getters
    public int getRow(){
        return tileRow;
    }
    public int getCol(){
        return tileCol;
    }

    public void setTileRow(int tileRow) {
        this.tileRow = tileRow;
    }
    public void setTileCol(int tileCol) {
        this.tileCol = tileCol;
    }
}
