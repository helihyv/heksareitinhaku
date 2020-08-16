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
public abstract class AbstractHexMapRouteSearchJUnitTest {

    public abstract HexMapRouteSearch
            createRouteSearchWithUniformTerrainMock(int movementPoints);

    public abstract HexMapRouteSearch createRouteSearchWithRealMap(String filename)
            throws IOException;

    public AbstractHexMapRouteSearchJUnitTest() {
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

        HexMapRouteSearch routeSearch = createRouteSearchWithUniformTerrainMock(-1);
        Route route = routeSearch.findRoute(2, 2, 8, 8);
        assertNull(route);

    }

    @Test
    public void findRouteReturnsRouteWithCorrectNumberOfHexesOnPassableUniformTerrain() {

        HexMapRouteSearch routeSearch = createRouteSearchWithUniformTerrainMock(3);
        Route route = routeSearch.findRoute(2, 2, 8, 8);
        assertEquals(9, route.getRoute().length);

    }

    @Test
    public void findRouteReturnsRouteWithCorrectCostInMovementPoints() {
        HexMapRouteSearch routeSearch = createRouteSearchWithUniformTerrainMock(4);
        Route route = routeSearch.findRoute(8, 8, 2, 2);
        assertEquals(36, route.getRouteCostInMovementPoints());
    }

    @Test
    public void findRouteReturnsRouteWithCorrectNumberOfHexesWhenMountainsOnTheWay()
            throws IOException {

        HexMapRouteSearch routeSearch
                = createRouteSearchWithRealMap("src/test/resources/mountains.map");
        Route route = routeSearch.findRoute(6, 2, 6, 5);

        assertEquals(8, route.getRoute().length);
    }

    @Test
    public void findRouteReturnsRouteThatIncludesBridgeWhenItIsClosestPassage()
            throws IOException {
        HexMapRouteSearch routeSearch
                = createRouteSearchWithRealMap("src/test/resources/bridgetest.map");
        Route route = routeSearch.findRoute(10, 1, 10, 10);
        boolean found = false;
        for (int i = 0; i < route.getRoute().length; i++) {
            if (route.getRoute()[i][0] == 6 && route.getRoute()[i][1] == 6) {
                found = true;
            }

        }
        assertTrue(found);

    }

    @Test
    public void findRouteReturnsRouteWithCorrectLengthIMovementPointsOnRealMap()
            throws IOException {

        HexMapRouteSearch routeSearch
                = createRouteSearchWithRealMap("src/test/resources/bridgetest.map");

        Route route = routeSearch.findRoute(6, 1, 6, 11);

        for (int i = 0; i < route.getRoute().length; i++) {
            System.out.println("X: " + route.getRoute()[i][0] + " Y: " + route.getRoute()[i][1]);
        }

        assertEquals(11, route.getRouteCostInMovementPoints());

    }

    @Test
    public void findRouteReturnsRouteWithCorrectNumberOfHexesAndMovementPoints()
            throws IOException {

        HexMapRouteSearch routeSearch
                = createRouteSearchWithRealMap("src/test/resources/mountains.map");
        Route route = routeSearch.findRoute(6, 1, 6, 11);

        assertEquals(13, route.getRoute().length);
        assertEquals(13, route.getRouteCostInMovementPoints());
    }

}
