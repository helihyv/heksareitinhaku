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
 *
 * @author Heli Hyvättinen
 */
public class MinimumHexEdgeHeap {

    private NextHexEdge[] heap;

    private int last;

    public MinimumHexEdgeHeap() {

        heap = new NextHexEdge[64];
        last = 0;

    }

    public boolean isEmpty() {
        return last == 0;
    }

    public NextHexEdge poll() {

        if (last == 0) {
            return null;
        }

        NextHexEdge minimum = heap[1];

        heap[1] = heap[last];
        heap[last] = null;
        last--;

        lowerIfNeeded(1);
        System.out.println("Heap after poll");
        for (int i = 1; i <= last; i++) {
            System.out.print(" " + heap[i].getPriority());
        }
        System.out.println("");

        return minimum;

    }

    public void insert(NextHexEdge edge) {

        if (last == heap.length - 1) {
            increaseHeap();
        }

        heap[++last] = edge;
        raiseIfNeeded(last);

        System.out.println("Heap after insert");
        for (int i = 1; i <= last; i++) {
            System.out.print(" " + heap[i].getPriority());
        }
        System.out.println("");

    }

    private void raiseIfNeeded(int index) {

        if (index < 2 || index > last) {
            return;
        }
        NextHexEdge current = heap[index];
        NextHexEdge parent = heap[index / 2];

        if (current.compareTo(parent) < 0) {
            heap[index] = parent;
            heap[index / 2] = current;
            raiseIfNeeded(index / 2);
        }
    }

    private void lowerIfNeeded(int index) {

        if (index < 1 || index * 2 > last) {
            return;
        }

        NextHexEdge current = heap[index];
        int leftChildIndex = 2 * index;
        int rigthChildIndex = 2 * index + 1;

        int smallerChildIndex;

        if (rigthChildIndex > last) {
            smallerChildIndex = leftChildIndex;

        } else if (heap[leftChildIndex].compareTo(heap[rigthChildIndex]) < 0) {
            smallerChildIndex = leftChildIndex;
        } else {
            smallerChildIndex = rigthChildIndex;
        }

        if (current.compareTo(heap[smallerChildIndex]) > 0) {
            heap[index] = heap[smallerChildIndex];
            heap[smallerChildIndex] = current;
            lowerIfNeeded(smallerChildIndex);
        }

    }

    private void increaseHeap() {

        NextHexEdge[] newHeap = new NextHexEdge[heap.length * 2];

        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

}
