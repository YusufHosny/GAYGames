package games.PacMan;

public class Node {
    private int xPosition, yPosition;

    public Node(int xPos,int yPos){
        xPosition = xPos;
        yPosition = yPos;
    }

    public int getX(){
        return xPosition;
    }

    public int getY(){
        return yPosition;
    }
}
