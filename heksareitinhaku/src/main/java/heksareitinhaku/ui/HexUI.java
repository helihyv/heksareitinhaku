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

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

/**
 * Käyttölittymän runko
 *
 * @author Heli Hyvättinen
 */
public class HexUI extends Application {

    private boolean selectingStartPoint;
    private boolean selectingGoal;
    private HexOutline startPoint;
    private HexOutline goal;
    private Label startPointText;
    private Label goalText;

    /**
     * Käyttöliittymä käynnistetään kutsumalla tätä funktiota
     *
     * @param args ohjelman komentoriviparametrit, ei tällä hetkeöllä käytetä
     * mihinkään
     */
    public static void main(String[] args) {
        HexUI.launch(args);
    }

    /**
     * JavaFX kutsuu tätä sovelluksen käyttöliittymän käynnistyksen yhteydessä
     * Funktiossa alustetaan käyttöliittymä
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        startPointText = new Label(
                "Lähtöpaikka:"
        );

        goalText = new Label("Maali:");

        selectingStartPoint = true;

        String[][] map = {{"G", "G~F"}, {"W", "H"}};
        MapUI mapView = new MapUI(map, this);

        VBox mainLayout = new VBox(startPointText, goalText, mapView.getMapTileGroup());

        Scene scene = new Scene(mainLayout, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Heksareitinhaku");

        stage.show();
    }

    boolean isSelectingStartPoint() {
        return selectingStartPoint;
    }

    boolean isSelectingGoal() {
        return selectingGoal;
    }

    void setStartPoint(HexOutline hex) {

        if (startPoint != null) {
            startPoint.unSelect();
        }
        startPoint = hex;
        selectingStartPoint = false;
        selectingGoal = true;
        startPointText.setText("Lähtöpaikka: ("
                + startPoint.getIndexX() + ", "
                + startPoint.getIndexY() + ")"
        );

    }

    void setGoal(HexOutline hex) {

        if (goal != null) {
            goal.unSelect();
        }
        goal = hex;
        selectingGoal = false;
        goalText.setText(
                "Maali: (" + goal.getIndexX() + ", " + goal.getIndexY() + ")"
        );

    }

}
