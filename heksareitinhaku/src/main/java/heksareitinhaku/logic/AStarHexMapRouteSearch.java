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
import java.util.PriorityQueue;

/**
 * Finds the shortest route betwween two points on a hex map using the A*
 * algorithm algorithm.
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
    public int[][] findRoute(
            int startX,
            int startY,
            int destinationX,
            int destinationY
    ) {
        int width = mapInterpreter.getWidth();
        int heigth = mapInterpreter.getHeigth();

        checked = new boolean[width][heigth];
        distance = new int[width][heigth];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        distance[startX][startY] = 0;

        cameFromX = new int[width][heigth];
        cameFromY = new int[width][heigth];
        cameFromX[startX][startY] = -1;
        cameFromY[startX][startY] = -1;

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

            if (checked[currentX][currentY]) {
                continue;
            }

            checked[currentX][currentY] = true;

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

            if (currentX % 2 == 0) {

                //Southwest for evene columss
                handleEdge(
                        currentX,
                        currentY,
                        currentX + -1,
                        currentY + 1,
                        destinationX,
                        destinationY
                );

                //Southeast for even colums
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

        ArrayList<Integer> routeList = new ArrayList<>();

        int routePointX = cameFromX[destinationX][destinationY];
        int routePointY = cameFromY[destinationX][destinationY];

        while (routePointX > 0 && routePointY > 0) {
            routeList.add(routePointY);
            routeList.add(routePointX);
            int previousX = cameFromX[routePointX][routePointY];
            routePointY = cameFromY[routePointX][routePointY];
            routePointX = previousX;
        }
        int[][] route = new int[routeList.size() / 2][2];
        for (int i = 0; i < routeList.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                route[i][j] = routeList.get(routeList.size() - i * 2 - j - 1);

            }
        }

        return route;

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

        int currentDistance = distance[newX][newY];

        int newDistance
                = distance[currentX][currentY]
                + movementPointsNeeded;

        if (newDistance < currentDistance) {
            distance[newX][newY] = newDistance;

            //Manhattan distance adapted to hex grid
            int yCoordinateDistance = abs(destinationY - newY);
            int xCoordinateDistance = abs(destinationX - newX);
            int maxYDiagonalTravel = yCoordinateDistance / 2;

            if (yCoordinateDistance % 2 == 1) {
                boolean onEvenRow = newX % 2 == 0;
                boolean destinationRoughlySouth = destinationY > newY;
                if ((onEvenRow && destinationRoughlySouth)
                        || (!onEvenRow && !destinationRoughlySouth)) {
                    maxYDiagonalTravel++;
                }
            }

            int maxDiagonalMoves
                    = min(maxYDiagonalTravel, xCoordinateDistance);
            int distanceInHexes = xCoordinateDistance + yCoordinateDistance - maxDiagonalMoves;

            //Distance in hexees is the smallest possible distance when all
            //edges weigth at least one
            int priority = newDistance + distanceInHexes;

            heap.add(new NextHexEdge(newX, newY, priority));
            cameFromX[newX][newY] = currentX;
            cameFromY[newX][newY] = currentY;

        }

    }

    private int abs(int x) {
        if (x < 0) {
            return -x;
        }

        return x;
    }

    private int max(int x1, int x2) {

        if (x1 > x2) {
            return x1;
        }

        return x2;
    }

    private int min(int x1, int x2) {
        if (x1 < x2) {
            return x1;
        }

        return x2;
    }

}
