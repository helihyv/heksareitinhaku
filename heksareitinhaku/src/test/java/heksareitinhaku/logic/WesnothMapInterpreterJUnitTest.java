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
public class WesnothMapInterpreterJUnitTest {

    private String[][] firstCharOfBaseOnlyMap = {
        {"A", "B", "C", "D", "E"},
        {"F", "G", "H", "I", "J"},
        {"K", "L", "M", "N", "O"},
        {"P", "Q", "R", "S", "T"},
        {"U", "V", "W", "X", "Y"}
    };

    private String[][] baseAndOverlayMap = {
        {"Ds^Do", "Hh^Ecf"},
        {"Mm^Xm", "Md^Fdf"},
        {"Gd^Ft", "Gs^Eff"}
    };

    ;

    public WesnothMapInterpreterJUnitTest() {

    }

    @Before
    public void setUp() {

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void impassableTerrainCannotBeEntered() {
        //X
        MapInterpreter mapInterpreter
                = new WesnothMapInterpreter(firstCharOfBaseOnlyMap);

        int movementPoints = mapInterpreter.getMovementPointsBetween(3, 3, 3, 4);

        assertEquals(-1, movementPoints);

    }

    @Test
    public void UnWalkableTerrainCannotBeEntered() {
        //Q
        MapInterpreter mapInterpreter
                = new WesnothMapInterpreter(firstCharOfBaseOnlyMap);

        int movementPoints = mapInterpreter.getMovementPointsBetween(1, 2, 1, 3);

        assertEquals(-1, movementPoints);

    }

    @Test
    public void grassCanBeEnteredUsingOneMovementPoint() {
        //
        MapInterpreter mapInterpreter
                = new WesnothMapInterpreter(firstCharOfBaseOnlyMap);

        int movementPoints = mapInterpreter.getMovementPointsBetween(0, 1, 1, 1);

        assertEquals(1, movementPoints);

    }

    @Test
    public void ifBothBaseAndOverLayArePassableHigherCostIsApplied() {
        //
        MapInterpreter mapInterpreter
                = new WesnothMapInterpreter(baseAndOverlayMap);

        //Mountains with forest, mountains decisive
        int movementPoints = mapInterpreter.getMovementPointsBetween(0, 1, 1, 1);

        assertEquals(5, movementPoints);

        //Grass with forest, forest decisive
        movementPoints = mapInterpreter.getMovementPointsBetween(1, 2, 0, 2);

        assertEquals(3, movementPoints);

    }

    @Test
    public void impassableOverlayCannotBeEntered() {
        //Q
        MapInterpreter mapInterpreter
                = new WesnothMapInterpreter(baseAndOverlayMap);

        int movementPoints = mapInterpreter.getMovementPointsBetween(1, 1, 0, 1);

        assertEquals(-1, movementPoints);

    }

}
