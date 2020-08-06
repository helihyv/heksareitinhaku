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
public class CoordinateList {

    private CoordinateListItem first;

    public boolean isEmpty() {
        return first == null;

    }

    public CoordinateListItem getFirst() {
        return first;
    }

    void insertToStart(CoordinateListItem node) {
        if (first == null) {
            first = node;
            node.setNext(null);
        } else {
            first.setPrevious(node);
            first = node;
            node.setNext(first);
        }
        node.setPrevious(null);
    }

    void remove(CoordinateListItem node) {
        if (first == node) {
            first = node.getNext();
        }
        node.removeFromList();
    }

    void insertAfter(CoordinateListItem newNode, CoordinateListItem oldNode) {
        if (oldNode != null) {
            oldNode.insertAfter(newNode);
        } else {
            insertToStart(newNode);
        }
    }

}
