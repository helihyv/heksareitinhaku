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
 * Finds the shortest route betwween two points on a hex map using the A*
 * algorithm.
 *
 * @author Heli Hyvättinen
 */
public class AStarHexMapRouteSearch implements HexMapRouteSearch {

    private MapInterpreter mapInterpreter;
    private MinimumHexEdgeHeap heap;
    private boolean[][] checked;
    private int[][] distance;
    private int[][] cameFromX;
    private int[][] cameFromY;

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
    public Route findRoute(
            int startX,
            int startY,
            int destinationX,
            int destinationY
    ) {
        System.out.println("A*");

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

        heap = new MinimumHexEdgeHeap();

        heap.insert(new NextHexEdge(startX, startY, 0));

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

            if (currentX % 2 == 0) {

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

        int[][] plainRoute = reconstructRoute(destinationX, destinationY);
        return new Route(plainRoute, distance[destinationY][destinationX]);

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

        if (currentX == 6 && currentY == 6) {
            System.out.println("Tässä menee pieleen");
            System.out.println("newY " + newY + " newX: " + newX);
            System.out.println("liikkumispiteitä tarvitaan " + movementPointsNeeded);

        }

        if (movementPointsNeeded < 0) {
            //impossible to move along this edge
            return;
        }

        int currentDistance = distance[newY][newX];

        int newDistance
                = distance[currentY][currentX]
                + movementPointsNeeded;

        if (currentX == 6 && currentY == 6) {

            System.out.println("new distance " + newDistance);
            System.out.println("current distance to new" + currentDistance);
            System.out.println(" distance to here" + distance[currentY][currentX]);
            System.out.println((newDistance < currentDistance));
            System.out.println(newDistance - currentDistance);

        }

        if (newDistance < currentDistance) {
            distance[newY][newX] = newDistance;
            System.out.println("Päästy");

            int priority = newDistance + heuristic(currentX, currentY, newX, newY);

            heap.insert(new NextHexEdge(newX, newY, priority));
            cameFromX[newY][newX] = currentX;
            cameFromY[newY][newX] = currentY;

            if (currentX == 6 && currentY == 6) {
                System.out.println("Tässä menee pieleen");
                System.out.println("newY " + newY + " newX: " + newX);
                System.out.println("old distance " + distance[currentY][currentX]);
                System.out.println("new distance " + distance[newY][newX]);
                System.out.println("Priority: " + priority);
            }

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

    /**
     * Returns the mimimum possible distanve betwwwen (x1,y1( and ((x2,y2) for
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

}
