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
public class DjikstraHexMapRouteSearchJUnitTest {

    public DjikstraHexMapRouteSearchJUnitTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void findRouteReturnsNullWhenAllTerrainIsImpassable() {

        MapInterpreter mapInterpreter = new UniformTerrainMockMapInterpreter(-1);
        HexMapRouteSearch algorithm = new DjikstraHexMapRouteSearch(mapInterpreter);
        int[][] route = algorithm.findRoute(2, 2, 8, 8);
        assertNull(route);

    }

    @Test
    public void findRouteReturnsRouteWithCorrectNumberOfHexesOnPassableUniformTerrain() {

        MapInterpreter mapInterpreter = new UniformTerrainMockMapInterpreter(3);
        HexMapRouteSearch algorithm = new DjikstraHexMapRouteSearch(mapInterpreter);
        int[][] route = algorithm.findRoute(2, 2, 8, 8);
        assertEquals(9, route.length);

    }
}
