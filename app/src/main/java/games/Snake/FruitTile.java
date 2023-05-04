package games.Snake;

public class FruitTile extends Tile{
    public FruitTile(int row, int col) {
        super(row, col);
    }
    @Override
    public String revealTile() {
        return "Fruit";
    }
}
