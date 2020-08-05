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
public class WesnothMapLoaderJUnitTest {

    private WesnothMapLoader mapLoader;
    private File verySmallMap;

    public WesnothMapLoaderJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mapLoader = new WesnothMapLoader();
        verySmallMap = new File("src/test/resources/verysmallmap");
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
    public void loadsValidMap() throws IOException {

        String[][] map = mapLoader.loadMap(verySmallMap);
        assertNotNull(map);
    }

    @Test
    public void loadedMapIsOfCorrectSize() throws IOException {

        String[][] map = mapLoader.loadMap(verySmallMap);

        assertEquals(3, map.length);
        int rowLength = map[0].length;
        assertEquals(5, rowLength);

    }

    @Test
    public void allRowsOfMapAreOfSameLength() throws IOException {

        String[][] map = mapLoader.loadMap(verySmallMap);

        int rowLength = map[0].length;

        for (int i = 1; i < map.length; i++) {
            assertEquals(rowLength, map[i].length);
        }

    }

    @Test
    public void correctTerrainCodesInUpperRigthAndLowerLeftCorners() throws IOException {

        String[][] map = mapLoader.loadMap(verySmallMap);

        String upperRightCode = map[0][map[0].length - 1];
        String lowerrLeftCode = map[map.length - 1][0];

        assertEquals("Aa^Vha", upperRightCode);
        assertEquals("Gs", lowerrLeftCode);
    }
}
