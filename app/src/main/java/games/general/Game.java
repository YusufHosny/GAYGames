package games.general;

public abstract class Game {
    protected Class act;
    public abstract void start();

    public Class getActiviy() {
        return act;
    }

}
