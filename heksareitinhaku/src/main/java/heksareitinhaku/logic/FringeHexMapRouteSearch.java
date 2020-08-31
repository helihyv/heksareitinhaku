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
     * @param destinationY
     * @return Route containing the route from start to destination with the
     * x-coordinate and y-coordinate of a hex to go trough next on each row and
     * the length of route in movement points.
     */
    @Override
    public Route findRoute(int startX, int startY, int destinationX, int destinationY) {

        fringe = new CoordinateList();
        fringe.insertToStart(nodes[startY][startX]);
        int width = mapInterpreter.getWidth();
        int height = mapInterpreter.getHeigth();
        distance = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[startY][startX] = 0;
        onList = new boolean[height][width];
        onList[startY][startX] = true;
        cameFromX = new int[height][width];
        cameFromX[startY][startX] = -1;
        cameFromY = new int[height][width];
        cameFromX[startY][startX] = -1;
        CoordinateListItem destination = nodes[destinationY][destinationX];

        boolean found = false;

        //set search debth for the first round to the minimum possible distance
        int fLimit = heuristic(startX, startY, destinationX, destinationY);

        while (!fringe.isEmpty() && !found) {

            int fMin = Integer.MAX_VALUE;

            //Iterate over fringe
            CoordinateListItem head = fringe.getFirst();
            while (head != null) {

                int currentX = head.getX();
                int currentY = head.getY();

                int f = distance[currentY][currentX]
                        + heuristic(currentX, currentY, destinationX, destinationY);

                if (f > fLimit) {
                    fMin = SimpleMath.min(f, fMin);
                    head = head.getNext();
                    continue;
                }

                if (head == destination) {
                    found = true;
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
                    handleEdge(currentX, currentY, currentX - 1, currentY + 1);
                    //Southeast for even colums
                    handleEdge(currentX, currentY, currentX + 1, currentY + 1);
                } else {
                    //Northwest for odd colums
                    handleEdge(currentX, currentY, currentX - 1, currentY - 1);
                    //Northeast for odd columns
                    handleEdge(currentX, currentY, currentX + 1, currentY - 1);
                }

                CoordinateListItem oldHead = head;
                head = head.getNext();

                fringe.remove(oldHead);

                onList[currentY][currentX] = false;

            }

            fLimit = fMin;
        }

        if (!found) {
            return null;
        }

        int[][] plainRoute = reconstructRoute(destinationX, destinationY);

        return new Route(plainRoute, distance[destinationY][destinationX]);
    }

    /**
     * Returns the minimum possible distance betwwwen (x1,y1( and ((x2,y2) for
     * use as heuristics. Uses Manhattan distance adapted to hex grid. (Which is
     * minimum distance assuming all cost >< 1.
     */
    int heuristic(int x1, int y1, int x2, int y2) {
        //transfor offset coordinates to cube coordinates
        //X is fine as it is
        int cubeZ1 = y1 - (x1 + (x1 & 1)) / 2;
        int cubeZ2 = y2 - (x2 + (x2 & 1)) / 2;
        int cubeY1 = -x1 - cubeZ1;
        int cubeY2 = -x2 - cubeZ2;

        //return Manhattan distance based on cube coordinates
        return ((SimpleMath.abs(x1 - x2)
                + SimpleMath.abs(cubeY1 - cubeY2)
                + SimpleMath.abs(cubeZ1 - cubeZ2))
                / 2);
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

        if (newDistance >= distance[newY][newX]) {
            return;
        }

        CoordinateListItem currentNode = nodes[currentY][currentX];
        CoordinateListItem newNode = nodes[newY][newX];
        if (onList[newY][newX]) {
            fringe.remove(newNode);
        }
        fringe.insertAfter(newNode, currentNode);
        onList[newY][newX] = true;

        distance[newY][newX] = newDistance;
        cameFromX[newY][newX] = currentX;
        cameFromY[newY][newX] = currentY;
    }

    private int[][] reconstructRoute(int destinationX, int destinationY) {

        int[][] routeBackwards
                = new int[mapInterpreter.getHeigth() * mapInterpreter.getWidth()][2];

        int routePointX = destinationX;
        int routePointY = destinationY;
        int index = 0;

        while (routePointX >= 0 && routePointY >= 0) {
            routeBackwards[index][0] = routePointX;
            routeBackwards[index][1] = routePointY;
            index++;
            int previousX = cameFromX[routePointY][routePointX];
            routePointY = cameFromY[routePointY][routePointX];
            routePointX = previousX;
        }
        int[][] route = new int[index - 1][2];
        for (int i = 0; i < index - 1; i++) {
            for (int j = 0; j < 2; j++) {
                route[i][j] = routeBackwards[index - 2 - i][j];

            }
        }

        return route;

    }

}
