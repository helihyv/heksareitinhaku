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
 * huolehtii Battle of Wesnoth -kartan tulkinnasta liikkumispisteiksi heksojen 
 * välillä. Ei noudata pelin sääntöjä, vaan tulkitsee kartaa vapaasti.
 * @author Heli Hyvättinen
 */
public class WesnothMapInterpreter implements MapInterpreter {
    
    private String[][] map;
    private int [] movementPointsPerTerrain;

    /**
     *
     * @param map
     * @param width kartan leveys heksoina
     *
     */
    public WesnothMapInterpreter(String[][] map) {
        this.map = map;
        this.movementPointsPerTerrain = new int [400];
        // Tähän kovakoodattujen liikkumispisteiden lisääminen taulukkoon 

    }
    
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
    @Override
    public int getMovementPointsBetween(int fromX, int fromY, int toX, int toY) {
        
       if (toX < 0 || toX >= map[0].length || toY < 0 || toY >= map.length) {
           return -1;
       } 
       
       if (fromX < 0 || fromX >= map[0].length || fromY < 0 || fromY >= map.length) {
           return -1;
       }
       
        if (fromX == toX) {
           if (toY != toX +1 && toY != toX -1  ) {
               return -1;
           }
       }
       
       if (toX > fromX + 1 || toX < fromX - 1) {
           return -1;
       }
       
  
        if (fromX % 2 == 0 && toY != fromY && toY != fromY +1) {
            return -1;
            
        } 
        
        if (fromX % 2 == 1 && toY != fromY && toY != fromY -1) {
            return -1;
        }
              
        String fromHex = map[fromY][fromX];
        String toHex = map[toY][toX];
        
        // Tähän teiden ja siltojen käsittey!
        
        int index = toHex.charAt(0);
        
        return movementPointsPerTerrain[index];
                }

    @Override
    public int getWidth() {
        return map[0].length;
    }

    /**
     *
     * @return height of map in hexes
     */
    @Override
    public int getHeigth() {
        return map.length;
}

}
