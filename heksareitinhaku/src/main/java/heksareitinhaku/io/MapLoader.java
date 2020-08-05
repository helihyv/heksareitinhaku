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

import java.io.File;
import java.io.IOException;

/**
 * Loads a map from a file
 *
 * @author Heli Hyvättinen
 */
public interface MapLoader {

    /**
     * Loads a map from a file
     *
     * @param file mapfile
     * @return the map a two-dimensional table of Strings
     * @throws java.io.IOException if reading the file fails
     */
    public String[][] loadMap(File file) throws IOException;

}
