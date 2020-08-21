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
package heksareitinhaku.ui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Heli Hyvättinen
 */
public class MapUI {

    private final static double radius = 20;

    private Group mapTileGroup;

    private HexRouteSearchUI parentUI;

    private Circle[][][] routeMarks;

    public MapUI(HexRouteSearchUI ui) {
        this.parentUI = ui;
        mapTileGroup = new Group();
    }

    public Group getMapTileGroup() {
        return mapTileGroup;
    }

    public void setMap(String[][] map) {

        routeMarks = new Circle[map.length][map[0].length][3];

        mapTileGroup.getChildren().clear();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                double x = j * radius * 1.5 + radius;

                double y = (i + 0.5) * 1.732 * radius;

                if (j % 2 == 0) {
                    y += 0.866 * radius;

                }

                Color fill = getTerrainColor(map[i][j]);

                HexOutline hex = new HexOutline(x, y, parentUI, j, i, fill );

                mapTileGroup.getChildren().add(hex);

                createRouteMarks(x, y, i, j);

            }
        }

    }

    public void updateRoute(int[][] route, String algorithm) {

        int algorithmIndex;
        if (algorithm.equals("D")) {
            algorithmIndex = 0;
        } else if (algorithm.equals("A")) {
            algorithmIndex = 1;
        } else if (algorithm.equals("F")) {
            algorithmIndex = 2;
        } else {
            return;
        }

        clearSingleRoute(algorithmIndex);

        for (int i = 0; i < route.length; i++) {
            routeMarks[route[i][1]][route[i][0]][algorithmIndex]
                    .setVisible(true);

        }
    }

    private void createRouteMarks(double x, double y, int i, int j) {

        Circle djikstraCircle = new Circle(
                x - radius / 2,
                y - radius / 2,
                radius / 4,
                Color.RED
        );
        djikstraCircle.setVisible(false);
        routeMarks[i][j][0] = djikstraCircle;
        mapTileGroup.getChildren().add(djikstraCircle);

        Circle aStarCircle = new Circle(
                x + radius / 2,
                y - radius / 2,
                radius / 4,
                Color.ORANGE
        );
        aStarCircle.setVisible(false);
        routeMarks[i][j][1] = aStarCircle;
        mapTileGroup.getChildren().add(aStarCircle);

        Circle fringeCircle = new Circle(
                x,
                y + radius / 2,
                radius / 4,
                Color.VIOLET
        );
        fringeCircle.setVisible(false);
        routeMarks[i][j][2] = fringeCircle;
        mapTileGroup.getChildren().add(fringeCircle);

    }

    public void clearRoutes() {

        for (int i = 0; i < 3; i++) {
            clearSingleRoute(i);
        }

    }

    private void clearSingleRoute(int algorithmIndex) {
        for (int i = 0; i < routeMarks.length; i++) {
            for (int j = 0; j < routeMarks[0].length; j++) {
                routeMarks[i][j][algorithmIndex].setVisible(false);
            }

        }
    }

    private Color getTerrainColor(String terrainCode) {

        if (terrainCode.contains("B")) { //Rails are ignored for movementpoins,ignore here?

            if (terrainCode.contains("/")) {

            }

            if (terrainCode.contains("/")) {

            }

            if (terrainCode.contains("|")) {

            }

            return Color.BEIGE;

        }

        if (terrainCode.contains("X")
                || terrainCode.contains("Q")) {
            return Color.BLACK;
        }

        if (terrainCode.contains("R")) {
            return Color.BEIGE;
        }

        boolean forest = false;
        if (terrainCode.contains("F")) {
            forest = true;
        }

        switch (terrainCode.charAt(0)) {

            case 'M':

                return Color.gray(0.5);

            case 'H':
                if (forest) {
                    return Color.DARKGREEN;
                }
                return Color.BROWN;

            case 'W':
                return Color.BLUE;

            case 'A':
                return Color.WHITE;

            case 'D':
                if (forest) {
                    return Color.YELLOWGREEN;
                }
                return Color.YELLOW;

            case 'S':

                return Color.LIGHTBLUE;

            case 'U':
                return Color.CHOCOLATE;

            case 'C':
                 return Color.GREY;

            case 'K':
                return Color.GREY;

            case 'G':
                if (forest) {
                    return Color.FORESTGREEN;
                }

                return Color.LIGHTGREEN;

            default:

                if (forest) {
                    return Color.FORESTGREEN;
                }
                return Color.LIGHTGRAY;
        }
    }
}
