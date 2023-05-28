package games.PacMan;

import java.util.ArrayDeque;

public class Ghost {
    private int xCurrent,yCurrent;
    private int xNext,yNext;

    private int xInitial,yInitial;
    private final int ghostNumber;
    private int tileToBeReplaced;

    private PacMan pacman;
    int[][] PacManGrid; // = PMBoard.getPMBoard();
    public Ghost(int startX,int startY, int ghostNum, PacMan pacMan, PMBoard board){
        PacManGrid = board.getPMBoard();
        pacman = pacMan;
        xInitial = startX;
        yInitial = startY;
        xCurrent = xInitial;
        yCurrent = yInitial;
        ghostNumber = ghostNum;
        tileToBeReplaced = 9;
        PacManGrid[yCurrent][xCurrent] = ghostNumber;
    }

    public void findShortestPath(){

        PacManGrid[yCurrent][xCurrent] = 0;
        // Retrieving the PacMan Grid and the current position of PacMan
        int xPacMan = pacman.getxPosition();
        int yPacMan = pacman.getyPosition();

        // Deque containing all the nodes that need to be explored
        ArrayDeque<Node> nodesToBeExplored = new ArrayDeque<>();

        // Creating starting position node of the ghost and adding it to nodesTobeExploredList
        Node startingGhostPosition = new Node(xCurrent, yCurrent);
        nodesToBeExplored.add(startingGhostPosition);


        // 2D boolean Array that tells which nodes are visited
        boolean[][] Visited = new boolean[PacManGrid.length][PacManGrid[0].length];
        for (int i=0;i<Visited.length;i++){
            for (int j=0;j<Visited[0].length;j++){
                if (PacManGrid[i][j]==1)
                    Visited[i][j] = true;
                else
                    Visited[i][j] = false;
            }
        }

        Node[][] Previous = new Node[PacManGrid.length][PacManGrid[0].length];
        for (int i=0;i<Previous.length;i++){
            for (int j=0;j<Previous[0].length;j++){
                Previous[i][j] = null;
            }
        }


        // Just instantiating the variable that can be used in the loop
        Node currentNode = new Node(xCurrent, yCurrent);

        // While loop that updates the three arrays
        while (!(currentNode.getX() == xPacMan && currentNode.getY() == yPacMan) && (nodesToBeExplored.size() != 0)){
            // Popping the current node from the toBeVisited Deque and setting it as visited in the Visited array
            currentNode = nodesToBeExplored.pop();
            Visited[currentNode.getY()][currentNode.getX()] = true;


            // Inspecting neighbors of current node

            for (int i=-1;i<2;i++) {
                try {
                    if (!(Visited[currentNode.getY()][currentNode.getX() + i]) && isNotGhostTile(currentNode.getX() + i,currentNode.getY())) {
                        Node newNodeToBeExplored = new Node(currentNode.getX() + i, currentNode.getY());
                        nodesToBeExplored.add(newNodeToBeExplored);

                        Visited[currentNode.getY()][currentNode.getX() + i] = true;
                        Previous[currentNode.getY()][currentNode.getX() + i] = currentNode;
                    }
                }
                catch (IndexOutOfBoundsException ignore){}
            }

            for (int j=-1;j<2;j++) {
                try{
                    if (!(Visited[currentNode.getY() + j][currentNode.getX()]) //&& isNotGhostTile(currentNode.getX(),currentNode.getY()+j)
                     ) {
                        Node newNodeToBeExplored = new Node(currentNode.getX(), currentNode.getY() + j);
                        nodesToBeExplored.add(newNodeToBeExplored);

                        Visited[currentNode.getY() + j][currentNode.getX() ] = true;
                        Previous[currentNode.getY() + j][currentNode.getX() ] = currentNode;
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }

        // Outside of While loop - Reconstructing Shortest path (with list of sequential nodes)
        ArrayDeque<Node> shortestPath = new ArrayDeque<>();
        currentNode = new Node(xPacMan,yPacMan);

        // We start from the node with PacMan and constantly retrieve the previous node and add it to
        // the shortest path list
        while (!(currentNode.getX() == xCurrent && currentNode.getY() == yCurrent)){
            try{
                shortestPath.addFirst(currentNode);
                currentNode = Previous[currentNode.getY()][currentNode.getX()];
            }
            catch (NullPointerException ignored){

            }
        }

        // We only care about the first best next node from the ghost
        xNext = shortestPath.getFirst().getX();
        yNext = shortestPath.getFirst().getY();


    }

    public void moveByShortestPath(){
        PacManGrid[yCurrent][xCurrent] = tileToBeReplaced;
        if (PacManGrid[yNext][xNext] == 9)
            tileToBeReplaced = 9;
        else
            tileToBeReplaced = 0;
        PacManGrid[yNext][xNext] = ghostNumber;
    }


    public void updateCurrentAndNext(){
        xCurrent = xNext;
        yCurrent = yNext;
    }

    public boolean checkPacmanCollision(){
        if (PacManGrid[yNext][xNext] == 6){
            pacman.decreaseHP();
            return true;
        }
        return false;
    }

    public void respawnGhost(int startX,int startY){
        xCurrent = startX;
        yCurrent = startY;

        PacManGrid[yCurrent][xCurrent] = ghostNumber;
    }

    public boolean isNotGhostTile(int x,int y){
        return PacManGrid[y][x] < 2 || PacManGrid[y][x] > 5;
    }

    public int getX(){
        return xCurrent;
    }

    public int getY(){
        return yCurrent;
    }

    public int getxNext(){
        return xNext;
    }

    public int getyNext(){
        return yNext;
    }

    public int getInitialX(){
        return xInitial;
    }

    public int getInitialY(){
        return yInitial;
    }

    public void setXYNext(int XNext,int YNext){
        xNext = XNext;
        yNext = YNext;
    }

    public void setTileToBeReplaced(int num){
        tileToBeReplaced = num;
    }
}
