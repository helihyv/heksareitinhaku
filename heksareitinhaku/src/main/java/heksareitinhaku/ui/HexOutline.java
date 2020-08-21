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
package heksareitinhaku.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Heli Hyvättinen
 */
public class HexOutline extends Polygon {

    private final static double radius = 20;

    //approximate of (radius * square root of 3) / 2
    private final static double distanceToMiddleOfSide = 0.866 * radius;

    private int indexX;
    private int indexY;

    HexRouteSearchUI ui;

    public HexOutline(
            double centerX,
            double centerY,
            HexRouteSearchUI ui,
            int indexX,
            int indexY,
            Color fill
    ) {

        super(
                centerX - (radius / 2), centerY - distanceToMiddleOfSide,
                centerX + (radius / 2), centerY - distanceToMiddleOfSide,
                centerX + radius, centerY,
                centerX + radius / 2, centerY + distanceToMiddleOfSide,
                centerX - radius / 2, centerY + distanceToMiddleOfSide,
                centerX - radius, centerY
        );

        setFill(fill);
        unSelect();

        this.ui = ui;
        this.indexX = indexX;
        this.indexY = indexY;

        EventHandler mouseClickedHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {

                HexOutline source = (HexOutline) event.getSource();

                if (ui.isSelectingStartPoint()) {

                    setStroke(Color.GREEN);
                    ui.setStartPoint(source);

                } else if (ui.isSelectingGoal()) {
                    setStroke(Color.RED);
                    ui.setGoal(source);

                }

                event.consume();

            }

        };

        this.setOnMouseClicked(mouseClickedHandler);

    }

    public void unSelect() {

        setStroke(Color.BLACK);

    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

}
