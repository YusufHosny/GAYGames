package games.Snake;

public class EmptyTile extends Tile {
    public EmptyTile(int row, int col) {
        super(row, col);
    }

    @Override
    public String revealTile() {
        return "Empty";
    }
}
