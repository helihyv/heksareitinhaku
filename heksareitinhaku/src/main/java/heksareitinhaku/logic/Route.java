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
 * Encapsulates a route in coodinates and the cost of theroute in movement
 * points
 *
 * @author Heli Hyvättinen
 */
public class Route {

    int[][] route;
    int routeCostInMovementPoints;

    /**
     *
     * @param route coordinates of hexes to go trough with X and Y coordinate of
     * a hex on each row
     * @param routeCostInMovementPoints
     */
    public Route(int[][] route, int routeCostInMovementPoints) {
        this.route = route;
        this.routeCostInMovementPoints = routeCostInMovementPoints;
    }

    /**
     *
     * @return coordinates of hexes to go trough with X and Y coordinate of a
     * hex on each row
     */
    public int[][] getRoute() {
        return route;
    }

    public int getRouteCostInMovementPoints() {
        return routeCostInMovementPoints;
    }

}
