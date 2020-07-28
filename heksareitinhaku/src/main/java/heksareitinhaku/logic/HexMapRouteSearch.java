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
 * Rajapinta reitinhakualgoritmeille
 * @author Heli Hyvättinen
 */
public interface HexMapRouteSearch {
    
    /**
     * Hakee reitin lähtöheksasta maaliheksaan annetulla kartalla
     * @param map kartta, jolla reitti etsitään (
     * @param startX lähtöheksan x-koordinaatti
     * @param startY lähtöheksan y-koordinaatti
     * @param destinationX maaliheksan x-koordinaatti
     * @param destinatinY maaliheksan y-koordinaatii
     * @return
     */
    public int[] findRoute(String[][] map, int startX, int startY, int destinationX, int destinatinY);
    
}
