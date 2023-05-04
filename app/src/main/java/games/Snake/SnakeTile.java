package games.Snake;

public class SnakeTile extends Tile{
    public SnakeTile(int row, int col) {
        super(row, col);
    }

    @Override
    public String revealTile() {
        return "Snake";
    }

}
