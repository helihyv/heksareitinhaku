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

import heksareitinhaku.io.MapLoader;
import heksareitinhaku.io.WesnothMapLoader;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Heli Hyvättinen
 */
public class AStarHexMapRouteSearchJUnitTest
        extends AbstractHexMapRouteSearchJUnitTest {

    public AStarHexMapRouteSearchJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Override
    public HexMapRouteSearch
            createRouteSearchWithUniformTerrainMock(int movementPoints) {

        MapInterpreter mapInterpreter
                = new UniformTerrainMockMapInterpreter(movementPoints);
        return new AStarHexMapRouteSearch(mapInterpreter);
    }

    @Override
    public HexMapRouteSearch createRouteSearchWithRealMap(String filename)
            throws IOException {
        File file = new File(filename);
        MapLoader mapLoader = new WesnothMapLoader();
        String[][] map = mapLoader.loadMap(file);
        MapInterpreter mapInterpreter = new WesnothMapInterpreter(map);
        return new AStarHexMapRouteSearch(mapInterpreter);
    }
}
