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

import java.util.PriorityQueue;

/**
 * Finds the shortest route betwween two points on a hex map using the A*
 * algorithm.
 *
 * @author Heli Hyvättinen
 */
public class AStarHexMapRouteSearch implements HexMapRouteSearch {

    protected MapInterpreter mapInterpreter;
    protected PriorityQueue<NextHexEdge> heap;
    private boolean[][] checked;
    protected int[][] distance;
    protected int[][] cameFromX;
    protected int[][] cameFromY;

    /**
     *
     * @param mapInterpreter MapInterpeter containing the map to be used in the
     * search
     */
    public AStarHexMapRouteSearch(MapInterpreter mapInterpreter) {
        this.mapInterpreter = mapInterpreter;

    }

    /**
     * Finds shortest route betwween two points on a hex map using the A*
     * algorithm algorithm.
     *
     * @param startX
     * @param startY
     * @param destinationX
     * @param destinationY
     * @return int[][] containing the route from start to destination with the
     * x-coordinate and y-coordinate of a hex to go trough next on each row.
     */
    @Override
    public int[][] findRoute(
            int startX,
            int startY,
            int destinationX,
            int destinationY
    ) {
        int heigth = mapInterpreter.getHeigth();
        int width = mapInterpreter.getWidth();

        checked = new boolean[heigth][width];
        distance = new int[heigth][width];
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        distance[startY][startX] = 0;

        cameFromX = new int[heigth][width];
        cameFromY = new int[heigth][width];
        cameFromX[startY][startX] = -1;
        cameFromY[startY][startX] = -1;

        heap = new PriorityQueue<>();

        heap.add(new NextHexEdge(startX, startY, 0));

        boolean found = false;

        while (!heap.isEmpty()) {
            NextHexEdge currentEdge = heap.poll();
            int currentX = currentEdge.getX();
            int currentY = currentEdge.getY();

            if (currentX == destinationX && currentY == destinationY) {
                found = true;
                break;
            }

            if (checked[currentY][currentX]) {
                continue;
            }

            checked[currentY][currentX] = true;

            handleEdge(
                    currentX,
                    currentY,
                    currentX,
                    currentY - 1,
                    destinationX,
                    destinationY
            ); //North

            handleEdge(
                    currentX,
                    currentY,
                    currentX,
                    currentY + 1,
                    destinationX,
                    destinationY
            ); //South

            //Nortwest for even columns, Southeast for odd columns
            handleEdge(
                    currentX,
                    currentY,
                    currentX - 1,
                    currentY,
                    destinationX,
                    destinationY
            );

            //Northeast for even colums, southeast for odd columns
            handleEdge(
                    currentX,
                    currentY,
                    currentX + 1,
                    currentY,
                    destinationX,
                    destinationY
            );

            if (currentX % 2 != 0) {
                //even & odd are reversed by starting column numbering from zero

                //Southwest for even columns
                handleEdge(
                        currentX,
                        currentY,
                        currentX + -1,
                        currentY + 1,
                        destinationX,
                        destinationY
                );

                //Southeast for even columns
                handleEdge(
                        currentX,
                        currentY,
                        currentX + 1,
                        currentY + 1,
                        destinationX,
                        destinationY
                );
            } else {

                //Northwest for odd colums
                handleEdge(
                        currentX,
                        currentY,
                        currentX - 1,
                        currentY - 1,
                        destinationX,
                        destinationY
                );

                //Northeast for odd columns
                handleEdge(
                        currentX,
                        currentY,
                        currentX + 1,
                        currentY - 1,
                        destinationX,
                        destinationY
                );
            }

        }

        if (!found) {
            return null;
        }

        return reconstructRoute(destinationX, destinationY);

    }

    private void handleEdge(
            int currentX,
            int currentY,
            int newX,
            int newY,
            int destinationX,
            int destinationY
    ) {

        int movementPointsNeeded = mapInterpreter.getMovementPointsBetween(
                currentX,
                currentY,
                newX,
                newY);

        if (movementPointsNeeded < 0) {
            //impossible to move along this edge
            return;
        }

        int currentDistance = distance[newY][newX];

        int newDistance
                = distance[currentY][currentX]
                + movementPointsNeeded;

        if (newDistance < currentDistance) {
            distance[newY][newX] = newDistance;

            //Manhattan distance adapted to hex grid
            int yCoordinateDistance = SimpleMath.abs(destinationY - newY);
            int xCoordinateDistance = SimpleMath.abs(destinationX - newX);
            int maxYDiagonalTravel = yCoordinateDistance / 2;

            if (yCoordinateDistance % 2 == 1) {
                boolean onEvenRow = newX % 2 == 0;
                boolean destinationRoughlySouth = destinationY > newY;
                if ((onEvenRow && destinationRoughlySouth)
                        || (!onEvenRow && !destinationRoughlySouth)) {
                    maxYDiagonalTravel++;
                }
            }

            int minDiagonalMoves
                    = SimpleMath.min(maxYDiagonalTravel, xCoordinateDistance);
            int distanceInHexes = xCoordinateDistance + yCoordinateDistance - minDiagonalMoves;

            //Distance in hexees is the smallest possible distance when all
            //edges weigth at least one
            int priority = newDistance + distanceInHexes;

            heap.add(new NextHexEdge(newX, newY, priority));
            cameFromX[newY][newX] = currentX;
            cameFromY[newY][newX] = currentY;

        }

    }

    private int[][] reconstructRoute(int destinationX, int destinationY) {

        int[][] routeBackwards = new int[mapInterpreter.getHeigth() * mapInterpreter.getWidth()][2];

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
