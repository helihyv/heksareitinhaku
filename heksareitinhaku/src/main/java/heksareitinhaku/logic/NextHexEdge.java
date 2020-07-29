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
 * Class for the edge to next hex to be inserted in the priority queue in the 
 * Djikstra's and A* algorithms.
 * 
 * @author Heli Hyvättinen
 */


public class NextHexEdge {
    private int x;
    private int y;
    private int priority;

    /**
     * 
     * @param x The x-coordinate of the hex this edge leads ro
     * @param y The y-coordinate of the hex  this edge leads to
     * @param priority the priority used to determine the edges place in the 
     * queue
     */
    public NextHexEdge(int x, int y, int priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
    }

    /**
     *
     * @return x-coodinate of the hex this edge leads ro
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y-coodinate of the hex this edge leads to
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return the priority that determines this edge's place in the priority 
     * queue
     */
    public int getPriority() {
        return priority;
    }
    
    
    
    
}
