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
import java.io.File;
import java.io.IOException;

/**
 * Reads Battle for Wesnoth maps from files
 *
 * @author Heli Hyvättinen
 */
public class WesnothMapLoader implements MapLoader {

    /**
     * Reads a Battle for Wesnoth map from a file
     *
     * @param file The file to read the map from.
     * @return The loaded map as a two-dimensional table of Strings
     * @throws java.io.IOException
     */
    @Override

    public String[][] loadMap(File file) throws IOException {

        FileReader fileReader = new FileReader(file);

        BufferedReader reader = new BufferedReader(fileReader);

        String line = reader.readLine();

        int mapWidth = line.split(",").length;

        int lineCount = 0;

        while (line != null) {
            lineCount++;
            line = reader.readLine();

        }
        fileReader = new FileReader(file);
        reader = new BufferedReader(fileReader);

        String[][] map = new String[lineCount][mapWidth];

        for (int i = 0; i < lineCount; i++) {
            line = reader.readLine();
            separateTerrainCodesToMap(line, map, i);
        }

        return map;

    }

    private void separateTerrainCodesToMap(
            String terrainCodes,
            String[][] map,
            int lineNumber
    ) {
        int x = 0;
        int charCount = 0;
        char[] code = new char[terrainCodes.length()];

        for (int i = 0; i < terrainCodes.length(); i++) {

            char character = terrainCodes.charAt(i);

            if (character == ',' || character == '\n') {

                char[] readyCode = new char[charCount];
                for (int j = 0; j < charCount; j++) {
                    readyCode[j] = code[j];
                }

                String codeString = new String(readyCode);
                map[lineNumber][x++] = codeString;
                charCount = 0;
                continue;
            }

            if (character != '_'
                    && character != '/'
                    && character != '|'
                    && character != '\\'
                    && character != '^'
                    && !(character >= 'A' && character <= 'Z')
                    && !(character >= 'a' && character <= 'z')) {
                continue;
            }

            code[charCount++] = character;

        }
        char[] readyCode = new char[charCount];
        for (int j = 0; j < charCount; j++) {
            readyCode[j] = code[j];
        }

        String codeString = new String(readyCode);
        map[lineNumber][x++] = codeString;

    }

}
