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
public class MinimumHexEdgeHeapJUnitTest {

    private MinimumHexEdgeHeap heap;

    public MinimumHexEdgeHeapJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        heap = new MinimumHexEdgeHeap();
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
    public void isEmptyReturnsTrueForEmptyHeap() {

        assertTrue(heap.isEmpty());

    }

    @Test
    public void isEmptyReturnsFalseForNonEmptyHeap() {
        heap.insert(new NextHexEdge(0, 0, 0));
        assertFalse(heap.isEmpty());
    }

    @Test
    public void pollReturnsEdgeWithSmallestPriorityOfInsertedEdges() {
        heap.insert(new NextHexEdge(0, 0, 7));
        heap.insert(new NextHexEdge(1, 2, 18));
        heap.insert(new NextHexEdge(2, 5, 12));
        heap.insert(new NextHexEdge(0, 0, 16));
        heap.insert(new NextHexEdge(15, 20, 10));
        heap.insert(new NextHexEdge(1, 12, 5));
        heap.insert(new NextHexEdge(8, 12, 32));
        heap.insert(new NextHexEdge(8, 9, 4));
        heap.insert(new NextHexEdge(3, 10, 11));
        heap.insert(new NextHexEdge(9, 11, 6));

        NextHexEdge min = heap.poll();

        assertEquals(4, min.getPriority());
    }

    @Test
    public void pollReturnsEdgeWithSmallestPriorityAfterPolling() {
        heap.insert(new NextHexEdge(0, 0, 7));
        heap.insert(new NextHexEdge(1, 2, 18));
        heap.insert(new NextHexEdge(2, 5, 12));
        heap.insert(new NextHexEdge(0, 0, 16));
        heap.insert(new NextHexEdge(15, 20, 10));
        heap.insert(new NextHexEdge(1, 12, 5));
        heap.insert(new NextHexEdge(8, 12, 32));
        heap.insert(new NextHexEdge(8, 9, 4));
        heap.insert(new NextHexEdge(3, 10, 11));
        heap.insert(new NextHexEdge(9, 11, 6));

        heap.poll();

        NextHexEdge min = heap.poll();

        assertEquals(5, min.getPriority());

    }

    @Test
    public void pollReturnEdgeWithSmallestPriorityAfterEnlargeningHeap() {

        for (int i = 0; i < 150; i++) {
            heap.insert(new NextHexEdge(2, 2, 200 - i));
        }

        NextHexEdge min = heap.poll();

        assertEquals(51, min.getPriority());

    }

    @Test
    public void valuesInsertedBeforeEnlagreningHeapRemainInHeapWhenHeapEnlargened() {
        for (int i = 0; i < 127; i++) {
            heap.insert(new NextHexEdge(2, 2, i + 1));
        }
        heap.insert(new NextHexEdge(2, 3, 0));
        heap.insert(new NextHexEdge(0, 1, 15));

        NextHexEdge min = heap.poll();

        assertEquals(0, min.getPriority());

    }

}
