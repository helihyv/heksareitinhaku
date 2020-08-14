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
 * Provides some simple mathematic funtions
 *
 * @author Heli Hyvättinen
 */
public class SimpleMath {

    /**
     * Calculates absolute
     *
     * @param x
     * @return absolute of x
     */
    public static int abs(int x) {
        if (x < 0) {
            return -x;
        }

        return x;
    }

    /**
     * Returns the minumum of parameters
     *
     * @param x1
     * @param x2
     * @return smaller of x1 & x2
     */
    public static int min(int x1, int x2) {
        if (x1 < x2) {
            return x1;
        }

        return x2;
    }

    /**
     * Returns the maximum of parameters
     *
     * @param x1
     * @param x2
     * @return larger of x1 and x2
     */
    public static int max(int x1, int x2) {
        if (x1 > x2) {
            return x1;
        }

        return x2;
    }

}
