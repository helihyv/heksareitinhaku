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
 * @author Heli Hyvättinen
 */
public interface MapInterpreter {
    
    
    /**
     * Kertoo liikkumispistekustannuksen ruutujen välillä. Jos ruutuun ei pääse
     * tuloruudusta, ruudut eivät ole vierekkäin tai jompikumpi ruuduista on
     * kartan ulkopuolella palautetaan -1.
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return tarvittavien liikepisteiden määrä, jos kohderuutuun on
     * mahdollista siirtyä suoraan tuloruudusta. Muuten -1.
     */
    public int getMovementPointsBetween(int fromX, int fromY, int toX, int toY);

    /**
     *
     * @return width of map in hexes
     */
    public int getWidth();

    
    public int getHeigth();
    
}
