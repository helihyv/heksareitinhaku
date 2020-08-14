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
 * Interprets Battle for Wesnoth maps telling the movement points needed for
 * moving between adjacent tiles.
 *
 * @author Heli Hyvättinen
 */
public class WesnothMapInterpreter implements MapInterpreter {

    private String[][] map;
    private int[] movementPointsPerTerrain;

    /**
     *
     * @param map The Battle for Wesnoth map to be interpreted as
     * two-dimensional array of terrain codes
     *
     *
     */
    public WesnothMapInterpreter(String[][] map) {
        this.map = map;
        this.movementPointsPerTerrain = new int[26];
        // meaning of codes from: https://wiki.wesnoth.org/TerrainCodesWML
        // actual movement points are not from the game
        movementPointsPerTerrain[0] = 3;  //A, arctic
        movementPointsPerTerrain[1] = 1; // B, bridge, reuires special treatment
        movementPointsPerTerrain[2] = 1; //C, castle
        movementPointsPerTerrain[3] = 2; //D, desert
        movementPointsPerTerrain[4] = 1; //E, embellishment
        movementPointsPerTerrain[5] = 3; //F, forest
        movementPointsPerTerrain[6] = 1; //G, grass e.g. flat
        movementPointsPerTerrain[7] = 3; //H, hills
        movementPointsPerTerrain[8] = 1;//I, interior
        movementPointsPerTerrain[9] = -1; //J, testing
        movementPointsPerTerrain[10] = 1; //K, keep
        movementPointsPerTerrain[11] = 1; //L not used, reserved for future use
        movementPointsPerTerrain[12] = 5; //M, mountains
        movementPointsPerTerrain[13] = 1; //N, not used,reserved fot future use
        movementPointsPerTerrain[14] = 1; //O, not used,reserved fot future use
        movementPointsPerTerrain[15] = 1; //P, not used,reserved fot future use
        movementPointsPerTerrain[16] = -1; //Q, un-walkable
        movementPointsPerTerrain[17] = 1; //R, roads or rails, requires special treatment
        movementPointsPerTerrain[18] = 4; //S, swamp
        movementPointsPerTerrain[19] = 1; //T, toadstool i.e. fungus
        movementPointsPerTerrain[20] = 2; //U, undergraound
        movementPointsPerTerrain[21] = 1; //V, village
        movementPointsPerTerrain[22] = -1; // W, water
        movementPointsPerTerrain[23] = -1; //X, impassable
        movementPointsPerTerrain[24] = 1; //Y, reserved for UMC
        movementPointsPerTerrain[25] = 1; //Z, reserved for UMC
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
            if (toY != fromY + 1 && toY != fromY - 1) {
                return -1;
            }
        } else {

            if (toX > fromX + 1 || toX < fromX - 1) {
                return -1;
            }

            if (fromX % 2 == 0 && toY != fromY && toY != fromY + 1) {
                return -1;

            }

            if (fromX % 2 != 0 && toY != fromY && toY != fromY - 1) {
                return -1;
            }
        }

        String fromHex = map[fromY][fromX];
        String toHex = map[toY][toX];

        int base = extractBase(toHex);

        int overlay = extractOverlay(toHex);

        int fromBase = extractBase(fromHex);

        int fromOverlay = extractOverlay(fromHex);

        if (overlay == 'B') { //Bridges
            if (toHex.charAt(1) == 'r') { //mine rail, move like under ground

                return 3;
            }
            int bridgeMovementPoints = handleBridge(toHex, toX, toY, fromX, fromY);

            return bridgeMovementPoints;

        }
        if (fromHex.charAt(0) == 'B' && fromHex.charAt(1) != 'r') {
            //Bridges, but not mine rails that also start with B!
            if (handleBridge(fromHex, fromX, fromY, toX, toY) < 0) {
                return -1;
            }
        }

        System.out.println("base: " + base + " toHex: " + toHex + " base-A: " + (base - 'A') + "G-A" + ('G' - 'A'));
        char first = toHex.charAt(0);
        System.out.println("first: " + first);

        if (base == '_') {
            return 1; //special system terrain codes treated as easy terrain
        }
        int baseMovementPoints = movementPointsPerTerrain[base - 'A'];
        if (baseMovementPoints < 0) {
            return -1;
        }
        // codes starting with _ are special system codes
        // apparently found only in overlays
        if (overlay > 0 && overlay != '_') {

            int overlayMovementPoints = movementPointsPerTerrain[overlay - 'A'];
            if (overlayMovementPoints < 0) {
                return -1;
            }

            boolean movingAlongRoad = (base == 'R' || overlay == 'R');
            if (fromBase != 'R' && fromOverlay != 'R') {
                movingAlongRoad = false;
            }
            //Roads override rough terrain,
            //otherwise rougher terrain type dominates
            //Rails are treated as roads here
            if ((overlayMovementPoints > baseMovementPoints
                    && !movingAlongRoad)) {
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

    private int handleBridge(
            String bridgeTerrainCode,
            int bridgeHexX,
            int bridgeHexY,
            int otherHexX,
            int otherHexY
    ) {
        for (int i = 1; i < bridgeTerrainCode.length(); i++) {
            char direction = bridgeTerrainCode.charAt(i);

            if (direction == '/') {

                if (bridgeHexX % 2 != 0) {
                    if ((otherHexY == bridgeHexY && otherHexX == bridgeHexY + 1)
                            || (otherHexY == bridgeHexY + 1
                            && otherHexX == bridgeHexX - 1)) {
                        return 11;
                    }

                    return -1;
                }

                if ((otherHexY == bridgeHexY - 1 && otherHexX == bridgeHexX + 1)
                        || (otherHexY == bridgeHexY
                        && otherHexX == bridgeHexX - 1)) {
                    return 1;
                }

                return -1;

            }

            if (direction == '\\') {

                if (bridgeHexX % 2 == 0) {
                    if ((otherHexY == bridgeHexY && otherHexX == bridgeHexX - 1)
                            || (otherHexY == bridgeHexY + 1
                            && otherHexX == bridgeHexY + 1)) {
                        return 1;
                    }

                    return -1;
                }

                if ((otherHexY == bridgeHexY - 1 && otherHexX == bridgeHexX - 1)
                        || (otherHexY == bridgeHexY + 1
                        && otherHexX == bridgeHexX + 1)) {
                    return 1;
                }
                return -1;

            }

            if (direction == '|') {

                if ((otherHexY == bridgeHexY + 1 || otherHexY == bridgeHexY - 1)
                        && otherHexX == bridgeHexX) {
                    return 1;
                }

                return -1;
            }

        }

        return -1; //invalid bridge

    }

    int extractOverlay(String terrainCode) {

        int overlay = -1;
        for (int i = 1; i < terrainCode.length(); i++) {
            if (terrainCode.charAt(i) == '^') {
                overlay = terrainCode.charAt(i + 1);
                if (overlay == 'B' && terrainCode.charAt(i + 2) == 'r') {
                    overlay = -1; //ignore mine rails
                }
            }
        }

        return overlay;

    }

    int extractBase(String terrainCode) {
        //Starting positions before actual terrain code main char are skipped
        for (int i = 0; i < terrainCode.length(); i++) {
            int base = terrainCode.charAt(i);
            if ((base >= 'A' && base <= 'Z') || base == '_') {
                return base;
            }
        }

        return 'X'; //Invalid code, treat as impassable
    }

}
