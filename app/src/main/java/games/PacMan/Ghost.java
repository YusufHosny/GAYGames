package games.PacMan;

import java.util.ArrayDeque;

import games.PacMan.Node;
import games.PacMan.PMBoard;
import games.PacMan.PacMan;

public class Ghost {
    private int xPosition, yPosition, ghostNumber;
    int[][] PacManGrid = PMBoard.getPMBoard();
    public Ghost(int startX,int startY, int ghostNum){
        xPosition = startX;
        yPosition = startY;
        ghostNumber = ghostNum;
        PacManGrid[yPosition][xPosition] = ghostNumber;
    }

    public void moveByShortestPath(){

        PacManGrid[yPosition][xPosition] = 0;
        // Retrieving the PacMan Grid and the current position of PacMan
        int xPacMan = PacMan.getxPosition();
        int yPacMan = PacMan.getyPosition();

        // Deque containing all the nodes that need to be explored
        ArrayDeque<Node> nodesToBeExplored = new ArrayDeque<>();

        // Creating starting position node of the ghost and adding it to nodesTobeExploredList
        Node startingGhostPosition = new Node(xPosition,yPosition);
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
        Node currentNode = new Node(xPosition,yPosition);

        // While loop that updates the three arrays
        while (!(currentNode.getX() == xPacMan && currentNode.getY() == yPacMan) && (nodesToBeExplored.size() != 0)){
            // Popping the current node from the toBeVisited Deque and setting it as visited in the Visited array
            currentNode = nodesToBeExplored.pop();
            Visited[currentNode.getY()][currentNode.getX()] = true;


            // Inspecting neighbors of current node

            for (int i=-1;i<2;i++) {

                if (!(Visited[currentNode.getY()][currentNode.getX() + i])) {
                    Node newNodeToBeExplored = new Node(currentNode.getY(), currentNode.getX() + i);
                    nodesToBeExplored.add(newNodeToBeExplored);

                    Visited[currentNode.getY()][currentNode.getX() + i] = true;
                    Previous[currentNode.getY()][currentNode.getX() + i] = currentNode;
                }

            }
            for (int j=-1;j<2;j++) {
                try{
                    if (!(Visited[currentNode.getY() + j][currentNode.getX()])) {
                        Node newNodeToBeExplored = new Node(currentNode.getY() + j, currentNode.getX());
                        nodesToBeExplored.add(newNodeToBeExplored);

                        Visited[currentNode.getY() + j][currentNode.getX() ] = true;
                        Previous[currentNode.getY() + j][currentNode.getX() ] = currentNode;
                    }
                }
                catch (IndexOutOfBoundsException e){

                }
            }

        }

        // Outside of While loop - Reconstructing Shortest path (with list of sequential nodes)
        ArrayDeque<Node> shortestPath = new ArrayDeque<>();
        currentNode = new Node(xPacMan,yPacMan);

        // We start from the node with PacMan and constantly retrieve the previous node and add it to
        // the shortest path list
        while (!(currentNode.getX() == xPosition && currentNode.getY() == yPosition)){
            shortestPath.addFirst(currentNode);
            currentNode = Previous[currentNode.getY()][currentNode.getX()];
        }

        // We only care about the first best next node from the ghost
        xPosition = shortestPath.getFirst().getX();
        yPosition = shortestPath.getFirst().getY();
        PacManGrid[yPosition][xPosition] = ghostNumber;
        System.out.println(xPosition + " " + yPosition);
    }

    public int getX(){
        return xPosition;
    }

    public int getY(){
        return yPosition;
    }
}
