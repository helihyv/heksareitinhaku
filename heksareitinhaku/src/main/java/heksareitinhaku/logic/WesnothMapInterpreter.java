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
 * huolehtii Battle for Wesnoth -kartan tulkinnasta liikkumispisteiksi heksojen
 * välillä. Ei noudata pelin sääntöjä, vaan tulkitsee kartaa vapaasti.
 *
 * @author Heli Hyvättinen
 */
public class WesnothMapInterpreter implements MapInterpreter {

    private String[][] map;
    private int[] movementPointsPerTerrain;

    /**
     *
     * @param map
     *
     *
     */
    public WesnothMapInterpreter(String[][] map) {
        this.map = map;
        this.movementPointsPerTerrain = new int[400];
        // meaning of codes from: https://wiki.wesnoth.org/TerrainCodesWML
        // actual movement points are not from the game
        movementPointsPerTerrain[0] = 3;  //A, arctic
        movementPointsPerTerrain[1] = 1; // B, bridge, reuires special treatment
        movementPointsPerTerrain[2] = 1; //C, castle
        movementPointsPerTerrain[3] = 3; //D, desert
        movementPointsPerTerrain[4] = 2; //E, embellishment
        movementPointsPerTerrain[5] = 4; //F, forest
        movementPointsPerTerrain[6] = 2; //G, grass e.g. flat
        movementPointsPerTerrain[7] = 4; //H, hills
        movementPointsPerTerrain[8] = 2;//I, interior
        movementPointsPerTerrain[9] = -1; //J, testing
        movementPointsPerTerrain[10] = 2; //K, keep
        movementPointsPerTerrain[1] = 2; //L not used, reserved for future use
        movementPointsPerTerrain[12] = 6; //M, mountains
        movementPointsPerTerrain[13] = 2; //N, not used,reserved fot future use
        movementPointsPerTerrain[14] = 2; //O
        movementPointsPerTerrain[15] = 2; //P
        movementPointsPerTerrain[16] = -1; //Q, un-walkable
        movementPointsPerTerrain[17] = 1; //R, roads or rails, requires special treatment
        movementPointsPerTerrain[18] = 5; //S, swamp
        movementPointsPerTerrain[19] = 2; //T, toadstool i.e. fungus
        movementPointsPerTerrain[20] = 3; //U, undergraound
        movementPointsPerTerrain[21] = 2; //V, village
        movementPointsPerTerrain[22] = 4; //w, water, shallow water is passable
        //   in the Battle for Wesnoth
        movementPointsPerTerrain[23] = -1; //X, impassable
        movementPointsPerTerrain[24] = 2; //Y, reserved for UMC
        movementPointsPerTerrain[25] = 2; //Z, reserved for UMC
        //Special sytem stuff has _ before first letter, needs spelcial treatment
    }

    /**
     * Kertoo liikkumispistekustannuksen ruutujen välillä. Jos ruutuun ei pääse
     * tuloruudusta, ruudut eivät ole vierekkäin tai jompikumpi ruuduista on
     * kartan ulkopuolella palautetaan -1.
     *
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
            if (toY != toX + 1 && toY != toX - 1) {
                return -1;
            }
        }

        if (toX > fromX + 1 || toX < fromX - 1) {
            return -1;
        }

        if (fromX % 2 == 0 && toY != fromY && toY != fromY + 1) {
            return -1;

        }

        if (fromX % 2 == 1 && toY != fromY && toY != fromY - 1) {
            return -1;
        }

        String fromHex = map[fromY][fromX];
        String toHex = map[toY][toX];
        int base = toHex.charAt(0);
        int overlay = -1;
        for (int i = 1; i < toHex.length(); i++) {
            if (toHex.charAt(i) == '^') {
                overlay = toHex.charAt(i + 1);
            }
        }

        // Tähän teiden ja siltojen käsittey!
        int baseMovementPoints = movementPointsPerTerrain[base - 'A'];
        if (overlay > 0) {
            int overlayMovementPoints = movementPointsPerTerrain[overlay - 'A'];
            if (overlayMovementPoints < 0) {
                return -1;
            }
            if (overlayMovementPoints > baseMovementPoints) {
                baseMovementPoints = overlayMovementPoints;
            }
        }
        return baseMovementPoints;
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
