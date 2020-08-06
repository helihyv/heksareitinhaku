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

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import heksareitinhaku.io.MapLoader;
import heksareitinhaku.io.WesnothMapLoader;
import heksareitinhaku.logic.DjikstraHexMapRouteSearch;
import heksareitinhaku.logic.AStarHexMapRouteSearch;
import heksareitinhaku.logic.HexMapRouteSearch;
import heksareitinhaku.logic.MapInterpreter;
import heksareitinhaku.logic.WesnothMapInterpreter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Käyttölittymän runko
 *
 * @author Heli Hyvättinen
 */
public class HexRouteSearchUI extends Application {

    private HexMapRouteSearch djikstraSearch;
    private int[][] djikstraRoute;
    private Label djikstraResultLabel;
    private AStarHexMapRouteSearch aStarSearch;
    private int[][] aStarRoute;
    private Label aStarResultLabel;
    private boolean selectingStartPoint;
    private boolean selectingGoal;
    private HexOutline startPoint;
    private HexOutline goal;
    private Label startPointText;
    private Label goalText;
    private MapUI mapView;
    private Label guideText;

    /**
     * Käyttöliittymä käynnistetään kutsumalla tätä funktiota
     *
     * @param args ohjelman komentoriviparametrit, ei tällä hetkeöllä käytetä
     * mihinkään
     */
    public static void main(String[] args) {
        HexRouteSearchUI.launch(args);
    }

    /**
     * JavaFX kutsuu tätä sovelluksen käyttöliittymän käynnistyksen yhteydessä
     * Funktiossa alustetaan käyttöliittymä
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {

        Button openMapFileButton = new Button("Valitse karttatiedosto");

        EventHandler fileOpenPressedHandler = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Valitse karttatiedosto");
                File mapFile = fileChooser.showOpenDialog(null);

                if (mapFile != null) {

                    MapLoader mapLoader = new WesnothMapLoader();

                    try {
                        String[][] map = mapLoader.loadMap(mapFile);
                        MapInterpreter mapInterpreter = new WesnothMapInterpreter(map);
                        djikstraSearch = new DjikstraHexMapRouteSearch(mapInterpreter);
                        aStarSearch = new AStarHexMapRouteSearch(mapInterpreter);
                        mapView.setMap(map);
                        guideText.setText("Valitse lähtöpaikka kartalta");

                    } catch (Exception e) {

                        Alert errorAlert = new Alert(
                                AlertType.ERROR,
                                "Kartan lukeminen tiedostosta epäonnistui.\n"
                                + e.getMessage()
                        );
                        errorAlert.show();

                    }

                }
            }
        };

        openMapFileButton.setOnAction(fileOpenPressedHandler);

        guideText = new Label("Valitse karrttatiedosto");

        startPointText = new Label(
                "Lähtöpaikka:"
        );

        goalText = new Label("Maali:");

        selectingStartPoint = true;

        djikstraResultLabel = new Label();
        aStarResultLabel = new Label();

        mapView = new MapUI(this);

        VBox mainLayout = new VBox(
                guideText,
                openMapFileButton,
                startPointText,
                goalText,
                djikstraResultLabel,
                aStarResultLabel,
                mapView.getMapTileGroup()
        );

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

        guideText.setText("Valitse määränpää kartalta");

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
        guideText.setText("");
        search();

    }

    private void search() {
        djikstraRoute = djikstraSearch.findRoute(
                startPoint.getIndexX(),
                startPoint.getIndexY(),
                goal.getIndexX(),
                goal.getIndexY()
        );

        aStarRoute = aStarSearch.findRoute(
                startPoint.getIndexX(),
                startPoint.getIndexY(),
                goal.getIndexX(),
                goal.getIndexY()
        );

        if (djikstraRoute != null) {
            djikstraResultLabel
                    .setText("Djikstran algoritmi löysi reitin.");
        } else {
            djikstraResultLabel
                    .setText("Djikstra's algoritmin mukaan reittiä ei ole.");
        }

        if (aStarRoute != null) {

            aStarResultLabel.setText("A*-algoritmi löysi reitin");

        } else {
            aStarResultLabel.setText("A*-algoritmin mukaan reittiä ei ole.");
        }

        //mapView.updateRoute();
    }

}
