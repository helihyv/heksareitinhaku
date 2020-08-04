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

/**
 *
 * @author Heli Hyvättinen
 */
public class MapUI {

    private final static double radius = 20;

    private Group mapTileGroup;

    public MapUI(String[][] map, HexUI ui) {

        mapTileGroup = new Group();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                double x = j * radius * 0.866 + radius;

                double y = (i + 0.5) * 1.732 * radius;

                if (j % 2 != 0) {
                    y += 0.866 * radius;
                    x += radius * 0.688;

                }

                HexOutline hex = new HexOutline(x, y, ui, j, i);

                mapTileGroup.getChildren().add(hex);

            }
        }

    }

    public Group getMapTileGroup() {
        return mapTileGroup;
    }

}
