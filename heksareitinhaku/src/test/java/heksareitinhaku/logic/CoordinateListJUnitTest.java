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
public class CoordinateListJUnitTest {

    public CoordinateListJUnitTest() {
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
    public void insertToStartFunctionsCorrectlyWhenListIsEmpty() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        list.insertToStart(firstNode);

        assertEquals(firstNode, list.getFirst());
        assertNull(firstNode.getPrevious());
        assertNull(firstNode.getNext());

    }

    @Test
    public void insertToStartFunctionsCorrectlyWhenListIsNotEmpty() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem oldNode = new CoordinateListItem(2, 3);

        list.insertToStart(oldNode);

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        list.insertToStart(firstNode);

        assertEquals(firstNode, list.getFirst());
        assertNull(firstNode.getPrevious());
        assertEquals(2, list.getFirst().getNext().getX());
        assertEquals(oldNode, firstNode.getNext());
        assertEquals(firstNode, oldNode.getPrevious());

    }

    @Test
    public void insertAfterToEndOfListLFunctionsCorrectly() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        CoordinateListItem secondNode = new CoordinateListItem(2, 3);
        list.insertToStart(firstNode);
        list.insertAfter(secondNode, firstNode);

        assertNull(secondNode.getNext());
        assertEquals(firstNode, secondNode.getPrevious());
        assertEquals(secondNode, firstNode.getNext());
        assertEquals(2, list.getFirst().getNext().getX());
    }

    @Test
    public void insertAfterToMiddleOfListLFunctionsCorrectly() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        CoordinateListItem secondNode = new CoordinateListItem(2, 3);
        CoordinateListItem middleNode = new CoordinateListItem(6, 7);
        list.insertToStart(firstNode);
        list.insertAfter(secondNode, firstNode);
        list.insertAfter(middleNode, firstNode);

        assertEquals(firstNode, middleNode.getPrevious());
        assertEquals(secondNode, middleNode.getNext());
        assertEquals(middleNode, firstNode.getNext());
        assertEquals(middleNode, secondNode.getPrevious());
        assertEquals(6, list.getFirst().getNext().getX());
    }

    @Test
    public void removeFromMiddleOfListRemovesTheNodeAndLeavesListInCorrectState() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        CoordinateListItem secondNode = new CoordinateListItem(2, 3);
        CoordinateListItem thirdNode = new CoordinateListItem(4, 5);
        list.insertToStart(firstNode);
        list.insertAfter(secondNode, firstNode);
        list.insertAfter(thirdNode, secondNode);

        list.remove(secondNode);

        assertEquals(firstNode, thirdNode.getPrevious());
        assertEquals(thirdNode, firstNode.getNext());
    }

    @Test
    public void removeFromStartOfListRemovesTheNodeAndLeavesListInCorrectState() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        CoordinateListItem secondNode = new CoordinateListItem(2, 3);

        list.insertToStart(firstNode);
        list.insertAfter(secondNode, firstNode);

        list.remove(firstNode);

        assertEquals(secondNode, list.getFirst());
        assertNull(secondNode.getPrevious());

    }

    public void removeFromEndOfListRemovesTheNodeAndLeavesListInCorrectState() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);
        CoordinateListItem secondNode = new CoordinateListItem(2, 3);

        list.insertToStart(firstNode);
        list.insertAfter(secondNode, firstNode);

        list.remove(secondNode);

        assertNull(firstNode.getNext());

    }

    public void removeOnlyNodeRemovesTheNodeAndLeavesListInCorrectState() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);

        list.insertToStart(firstNode);

        list.remove(firstNode);

        assertNull(list.getFirst());

    }

    public void isEmptyReturnsTrueForEmptyList() {

        CoordinateList list = new CoordinateList();

        assertTrue(list.isEmpty());

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);

        list.insertToStart(firstNode);

        list.remove(firstNode);

        assertTrue(list.isEmpty());

    }

    public void isEmptyReturnsFalseIfNodeOnList() {

        CoordinateList list = new CoordinateList();

        CoordinateListItem firstNode = new CoordinateListItem(1, 0);

        assertFalse(list.isEmpty());

    }
}
