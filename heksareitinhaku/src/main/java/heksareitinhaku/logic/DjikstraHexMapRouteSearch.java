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
import java.util.ArrayList;

/**
 * Finds shortest route betwween two points on a hex map using Djikstra's
 * algorithm.
 * @author hzkoskin
 */
public class DjikstraHexMapRouteSearch implements HexMapRouteSearch {
    
    private MapInterpreter mapInterpreter;
    private PriorityQueue<NextHexEdge> heap;
    private boolean [][] checked;
    private int[][] distance;
    private int[][] cameFromX;
    private int[][] cameFromY;
  
    /**
     *
     * @param mapInterpreter MapInterpeter containing the map to be used in the search
     */
    public DjikstraHexMapRouteSearch(MapInterpreter mapInterpreter) {
        this.mapInterpreter = mapInterpreter;
        
        
    }
    
    /**
     * Finds shortest route betwween two points on a hex map using Djikstra's
     * algorithm.
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
        int width = mapInterpreter.getWidth();
        int heigth = mapInterpreter.getHeigth();

        checked = new boolean [width][heigth];        
        distance = new int[width][heigth];
        cameFromX = new int[width][heigth];
        cameFromY = new int[width][heigth];
        cameFromX[startX][startY] = -1;
        cameFromY[startX][startY] = -1;
        
        heap = new PriorityQueue<>();
        
        heap.add(new NextHexEdge(startX,startY,0));
        
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
           
           handleEdge(currentX,currentY,currentX,currentY-1); //North
           handleEdge(currentX,currentY, currentX, currentY + 1); //South
           //Nortwest for even columns, Southeast for odd columns
           handleEdge(currentX,currentY,currentX-1,currentY); 
           //Northeast for even colums, southeast for odd columns
           handleEdge(currentX,currentY, currentX+1,currentY);
           if (currentX % 2 == 0) {
               //Southwest for evene columss
               handleEdge(currentX,currentY,currentX+-1,currentY+1);
               //Southeast for even colums
               handleEdge(currentX,currentY,currentX+1,currentY+1);
           } else {
               //Northwest for odd colums
               handleEdge(currentX,currentY,currentX-1,currentY-1);
               //Northeast for odd columns
               handleEdge(currentX,currentY,currentX+1,currentY-1);
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
        int[][] route = new int[2][routeList.size()/2];
        for (int i = 0; i < routeList.size()/2; i++) {
            for (int j = 0; j < 2; j++) {
            route[i][j] = routeList.get(routeList.size()-i*2+j);
            }
        }
        
        return route;
        
    }

    private void handleEdge (
 
                int currentX, 
                int currentY, 
                int newX, 
                int newY
    ){
            
        int currentDistance = distance[newX][newY];

        int newDistance = 
                distance[currentX][currentY] + 
                mapInterpreter.getMovementPointsBetween(
                        currentX, 
                        currentY, 
                        newX, 
                        newY); 

        if (newDistance < currentDistance) {
            distance[newX][newY] = newDistance;
            heap.add(new NextHexEdge(newX,newY,newDistance));
            cameFromX[newX][newY] = currentX;
            cameFromY[newX][newY] = currentY;
            
        }                
    }
    
}