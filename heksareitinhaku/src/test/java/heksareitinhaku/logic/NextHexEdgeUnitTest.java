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
public class NextHexEdgeUnitTest {

    public NextHexEdgeUnitTest() {
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
    public void compareToReturnsZeroWithEqualPriorities() {
        NextHexEdge edge1 = new NextHexEdge(1, 2, 3);
        NextHexEdge edge2 = new NextHexEdge(4, 5, 3);
        assertEquals(0, edge1.compareTo(edge2));
    }

    @Test
    public void compareToReturnsLessThanZeroWithLowerPriorityValue() {
        NextHexEdge edge1 = new NextHexEdge(1, 2, 1);
        NextHexEdge edge2 = new NextHexEdge(4, 5, 2);
        int result = edge1.compareTo(edge2);
        assertTrue(result < 0);
    }

    @Test
    public void compareToReturnsMoreThanZeroWithHigherPriorityValue() {
        NextHexEdge edge1 = new NextHexEdge(1, 2, 4);
        NextHexEdge edge2 = new NextHexEdge(4, 5, 3);
        int result = edge1.compareTo(edge2);
        assertTrue(result > 0);
    }
}
