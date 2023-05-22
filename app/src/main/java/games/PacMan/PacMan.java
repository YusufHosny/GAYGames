package games.PacMan;

public class PacMan {

    static int xPosition,yPosition,Score,HP;
    static int[][] Board = PMBoard.getPMBoard();


    public static void spawnPacMan(){
        HP = 3;
        Score = 0;
        xPosition = 16;  // Initial spawn point of pacman
        yPosition = 17;
        Board[yPosition][xPosition]=6;
    }

    public static void updatePosition(int newXPosition, int newYPosition){
        if (!outOfBounds(newXPosition,newYPosition)){
            if (!ghostCollision(newXPosition,newYPosition) && !wallCollision(newXPosition,newYPosition)){
                // If we don't move out of the map and don't collide against a ghost or a wall, then
                // we perform a normal movement

                if (Board[newYPosition][newXPosition]==0)
                    Score++; // Increments score when our next position is on a "Coin Tile"

                Board[yPosition][xPosition]=9;
                xPosition = newXPosition;
                yPosition = newYPosition;
                Board[yPosition][xPosition]=6;
            }
            else {
                if (ghostCollision(newXPosition,newYPosition)){
                    // If we collide against a ghost, we lose 1 HP and go back to spawn
                    HP--;
                    Board[yPosition][xPosition]=9;
                    xPosition = 16;
                    yPosition = 17;
                    Board[yPosition][xPosition]=6;
                }
                // If we collide against a wall, we stay at the same position (don't do anything)
            }
        }
        // If we move out of the map, we teleport to the opposite part depending on the direction
        else {
            // Teleport to left side if we move rightwards
            if ((newXPosition - xPosition) > 0){
                Board[yPosition][xPosition]=9;
                xPosition = 0;
                Board[yPosition][xPosition]=6;
            }
            // Teleport to right side if we move leftwards
            else {
                Board[yPosition][xPosition]=9;
                xPosition = Board[0].length-1;
                Board[yPosition][xPosition]=6;
            }
        }

    }

    //////////// METHODS THAT CHECK THE NEXT TILE BASED ON POTENTIAL NEW POSITION //////////
    public static boolean ghostCollision(int newXPosition, int newYPosition){
        // Returns True if we collide against a ghost
        return Board[newYPosition][newXPosition] == 4;
    }

    public static boolean outOfBounds(int newXPosition, int newYPosition){
        // Returns True if the next position is out of bounds
           try{
               if (Board[newYPosition][newXPosition]==0 || Board[newYPosition][newXPosition]==9)
                   return false;
           }
           catch (IndexOutOfBoundsException e){
               return true;
           }
           return false;
    }

    public static boolean wallCollision(int newXPosition, int newYPosition){
        // Returns True if next position is a wall
        return Board[newYPosition][newXPosition] == 1;
    }

    ///////////////////////// GETTERS AND SETTERS ////////////////////////////////////////////////
    public static int getxPosition(){
        return xPosition;
    }

    public static int getyPosition(){
        return yPosition;
    }

    public static int getScore(){
        return Score;
    }

}
