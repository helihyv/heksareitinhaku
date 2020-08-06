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
class CoordinateListItem {

    private int x;
    private int y;
    private CoordinateListItem next;
    private CoordinateListItem previous;

    public CoordinateListItem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CoordinateListItem getNext() {
        return next;
    }

    public void setNext(CoordinateListItem next) {
        this.next = next;
    }

    public CoordinateListItem getPrevious() {
        return previous;
    }

    public void setPrevious(CoordinateListItem previous) {
        this.previous = previous;
    }

    public void insertAfter(CoordinateListItem newItem) {
        if (newItem == null) {
            return;
        }
        newItem.setNext(next);
        newItem.setPrevious(this);
        if (next != null) {
            next.setPrevious(newItem);
        }
        next = newItem;

    }

    public void insertBefore(CoordinateListItem newItem) {
        if (newItem == null) {
            return;
        }
        newItem.setNext(this);
        newItem.setPrevious(previous);
        if (previous != null) {
            previous.setNext(newItem);
        }
        previous = newItem;
    }

    public void removeFromList() {
        if (next != null) {
            next.setPrevious(previous);
        }
        if (previous != null) {
            previous.setNext(next);
        }
        next = null;
        previous = null;
    }

}
