/*
 * Copyright (C) 2020 Heli Hyvättinen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package heksareitinhaku.logic;

import java.util.ArrayList;

/**
 *
 * Finds the shortest route between two points on a hex map using the Fringe
 * Search algorithm.
 *
 * @author Heli Hyvättinen
 */
public class FringeHexMapRouteSearch implements HexMapRouteSearch {

    private MapInterpreter mapInterpreter;
    private CoordinateList fringe;
    private CoordinateListItem[][] nodes;
    private boolean[][] onList;
    private boolean[][] visited;
    private int destinationX;
    private int destinationY;
    private int cameFromX[][];
    private int cameFromY[][];
    private int distance[][];

    public FringeHexMapRouteSearch(MapInterpreter mapInterpreter) {
        this.mapInterpreter = mapInterpreter;

        int width = mapInterpreter.getWidth();
        int heigth = mapInterpreter.getHeigth();

        nodes = new CoordinateListItem[heigth][width];

        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                nodes[i][j] = new CoordinateListItem(j, i);
            }
        }

    }

    /**
     * Finds the shortest route between two points on a hex map using the Fringe
     * Search algorithm.
     *
     * @param startX
     * @param startY
     * @param destinationX
     * @param destinatinY
     * @return int[][] containing the route from start to destination with the
     * x-coordinate and y-coordinate of a hex to go trough next on each row.
     */
    @Override
    public int[][] findRoute(int startX, int startY, int destinationX, int destinationY) {

        fringe = new CoordinateList();
        fringe.insertToStart(nodes[startY][startX]);
        int width = mapInterpreter.getWidth();
        int height = mapInterpreter.getHeigth();
        distance = new int[height][width];
        distance[startY][startX] = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        onList = new boolean[height][width];
        onList[startY][startX] = true;
        visited = new boolean[height][width];
        cameFromX = new int[height][width];
        cameFromY = new int[height][width];
        CoordinateListItem destination = nodes[destinationY][destinationX];

        boolean found = false;

        //set search debth for the first round to minimum possible distance
        int fLimit = heuristic(startX, startY);

        while (!fringe.isEmpty() && !found) {
            int fMin = Integer.MAX_VALUE;
            //Iterate over fringe
            System.out.println("Fringe");

            for (CoordinateListItem node = fringe.getFirst(); node != null; node = node.getNext()) {

                int currentX = node.getX();
                int currentY = node.getY();

                int f = distance[currentY][currentX]
                        + heuristic(currentX, currentY);

                if (f > fLimit) {
                    fMin = SimpleMath.min(f, fMin);
                    continue;
                }

                if (node == destination) {
                    found = true;
                    System.out.println("X: " + node.getX() + " Y: " + node.getY());
                    break;

                }

                handleEdge(currentX, currentY, currentX, currentY - 1); //North
                handleEdge(currentX, currentY, currentX, currentY + 1); //South
                //Nortwest for even columns, Southeast for odd columns
                handleEdge(currentX, currentY, currentX - 1, currentY);
                //Northeast for even colums, southeast for odd columns
                handleEdge(currentX, currentY, currentX + 1, currentY);
                if (currentX % 2 == 0) {
                    //Southwest for evene columss
                    handleEdge(currentX, currentY, currentX + -1, currentY + 1);
                    //Southeast for even colums
                    handleEdge(currentX, currentY, currentX + 1, currentY + 1);
                } else {
                    //Northwest for odd colums
                    handleEdge(currentX, currentY, currentX - 1, currentY - 1);
                    //Northeast for odd columns
                    handleEdge(currentX, currentY, currentX + 1, currentY - 1);
                }

                fringe.remove(node);
                System.out.println("");
                onList[currentY][currentX] = false;

            }

            fLimit = fMin;
        }

        if (!found) {
            return null;
        }

        int[][] routeBackwards = new int[width * height][2];

        int routePointX = cameFromX[destinationX][destinationY];
        int routePointY = cameFromY[destinationX][destinationY];
        int index = 0;

        while (routePointX > 0 && routePointY > 0) {
            routeBackwards[index][0] = routePointY;
            routeBackwards[index][1] = routePointX;
            int previousX = cameFromX[routePointX][routePointY];
            routePointY = cameFromY[routePointX][routePointY];
            routePointX = previousX;
            index++;
        }
        int[][] route = new int[index][2];
        for (int i = 0; i < index; i++) {
            for (int j = 0; j < 2; j++) {
                route[i][j] = routeBackwards[index - i - 1][j];

            }
        }

        return route;
    }

    private int heuristic(int x, int y) {
        //Returns smallest possible distance from given
        //coordinate to the destination (assuming all costs > 1)
        //Manhattan distance adapted to hex grid
        int yCoordinateDistance = SimpleMath.abs(destinationY - y);
        int xCoordinateDistance = SimpleMath.abs(destinationX - x);
        int maxYDiagonalTravel = yCoordinateDistance / 2;

        if (yCoordinateDistance % 2 == 1) {
            boolean onEvenRow = x % 2 == 0;
            boolean destinationRoughlySouth = destinationY > y;
            if ((onEvenRow && destinationRoughlySouth)
                    || (!onEvenRow && !destinationRoughlySouth)) {
                maxYDiagonalTravel++;
            }
        }

        int minDiagonalMoves
                = SimpleMath.min(maxYDiagonalTravel, xCoordinateDistance);

        return minDiagonalMoves;
    }

    private void handleEdge(
            int currentX,
            int currentY,
            int newX,
            int newY
    ) {

        int movementPointsNeeded
                = mapInterpreter.getMovementPointsBetween(
                        currentX,
                        currentY,
                        newX,
                        newY
                );
        if (movementPointsNeeded < 0) {
            return;
        }
        int newDistance = distance[currentY][currentX] + movementPointsNeeded;

        if (newDistance > distance[newY][newX]) {
            return;
        }

        CoordinateListItem currentNode = nodes[currentY][currentX];
        CoordinateListItem newNode = nodes[currentY][currentX];
        if (onList[newY][newX]) {
            fringe.remove(newNode);
        }
        fringe.insertAfter(newNode, currentNode);
        onList[newX][newY] = true;

        distance[newY][newX] = newDistance;
        cameFromX[newY][newX] = currentX;
        cameFromY[newY][newX] = currentY;
    }

}
