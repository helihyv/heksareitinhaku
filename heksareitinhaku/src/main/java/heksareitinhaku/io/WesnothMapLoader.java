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
package heksareitinhaku.io;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Lukee Battle of Wesnoth -karttoja tiedostosta
 *
 * @author Heli Hyvättinen
 */
public class WesnothMapLoader implements MapLoader {

    /**
     * Lukee Battle of Wesnoth -kartaan tiedostosta
     *
     * @param filenameWithPath
     * @throws java.io.IOException
     */
    @Override

    public String[][] loadMap(String filenameWithPath) throws IOException {

        FileReader fileReader = new FileReader(filenameWithPath);

        BufferedReader reader = new BufferedReader(fileReader);

        String line;
        ArrayList<String> lines = new ArrayList();

        line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();

        }

        String[] terrainCodes = lines.get(0).split(",");

        int mapWidth = terrainCodes.length;
        int mapHeigth = lines.size();

        String[][] map = new String[mapWidth][mapHeigth];

        for (int i = 0; i < mapHeigth; i++) {

            terrainCodes = lines.get(i).split(",");

            for (int j = 0; j < mapWidth; j++) {
                map[i][j] = terrainCodes[j];
            }

        }

        return map;

    }
}
