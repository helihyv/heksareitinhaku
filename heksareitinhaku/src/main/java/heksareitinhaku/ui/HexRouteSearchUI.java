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
import heksareitinhaku.logic.FringeHexMapRouteSearch;
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
    private HexMapRouteSearch aStarSearch;
    private int[][] aStarRoute;
    private Label aStarResultLabel;
    private HexMapRouteSearch fringeSearch;
    private int[][] fringeRoute;
    private Label fringeResultLabel;
    private boolean selectingStartPoint;
    private boolean selectingGoal;
    private HexOutline startPoint;
    private HexOutline goal;
    private Label startPointText;
    private Label goalText;
    private MapUI mapView;
    private Label guideText;
    private Button newSearchButton;

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
                        fringeSearch = new FringeHexMapRouteSearch(mapInterpreter);
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
        fringeResultLabel = new Label();

        newSearchButton = new Button("Uusi haku");

        EventHandler newSearchPressedHander = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                selectingStartPoint = true;
                guideText.setText("Valitse haun alkupiste kartalta");
                newSearchButton.setVisible(false);
                mapView.clearRoutes();
                startPoint.unSelect();
                startPoint = null;
                goal.unSelect();;
                goal = null;
                startPointText.setText("Lähtöpaikka:");
                goalText.setText("Maali:");
                djikstraResultLabel.setText("");
                aStarResultLabel.setText("");
                fringeResultLabel.setText("");

            }
        };

        newSearchButton.setOnAction(newSearchPressedHander);
        newSearchButton.setVisible(false);

        mapView = new MapUI(this);

        VBox mainLayout = new VBox(
                guideText,
                openMapFileButton,
                startPointText,
                goalText,
                djikstraResultLabel,
                aStarResultLabel,
                fringeResultLabel,
                newSearchButton,
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

        search();
        newSearchButton.setVisible(true);

    }

    private void search() {

        long djikstraStart = System.nanoTime();
        djikstraRoute = djikstraSearch.findRoute(
                startPoint.getIndexX(),
                startPoint.getIndexY(),
                goal.getIndexX(),
                goal.getIndexY()
        );

        long djikstraEndAStarStart = System.nanoTime();

        aStarRoute = aStarSearch.findRoute(
                startPoint.getIndexX(),
                startPoint.getIndexY(),
                goal.getIndexX(),
                goal.getIndexY()
        );

        long aStarEndFringeStart = System.nanoTime();

        fringeRoute = fringeSearch.findRoute(
                startPoint.getIndexX(),
                startPoint.getIndexY(),
                goal.getIndexX(),
                goal.getIndexY()
        );

        long fringeEnd = System.nanoTime();

        String djikstraText;

        if (djikstraRoute != null) {

            djikstraText = "Djikstran algoritmi löysi reitin "
                    + "(merkitty karttaan punaisilla ympyröillä).";
            mapView.updateRoute(djikstraRoute, "D");
        } else {
            djikstraText
                    = "Djikstra's algoritmin mukaan reittiä ei ole.";
        }

        djikstraText += " Reitinhaku kesti "
                + (djikstraEndAStarStart - djikstraStart) / 1e6 + " ms.";

        djikstraResultLabel.setText(djikstraText);

        String aStarText;

        if (aStarRoute != null) {

            aStarText = "A*-algoritmi löysi reitin "
                    + "(merkitty karttaan oransseilla ympyröillä)";
            mapView.updateRoute(aStarRoute, "A");

        } else {
            aStarText = "A*-algoritmin mukaan reittiä ei ole.";
        }

        aStarText += " Reitinhaku kesti "
                + (aStarEndFringeStart - djikstraEndAStarStart) / 1e6 + " ms.";
        aStarResultLabel.setText(aStarText);

        String fringeText;

        if (fringeRoute != null) {

            fringeText = "Fringe search -algoritmi löysi reitin "
                    + "(merkitty karttaan violeteilla ympyröillä)";
            mapView.updateRoute(fringeRoute, "F");

        } else {
            fringeText = "Fringe Search-algoritmin mukaan reittiä ei ole.";
        }

        fringeText += " Reitinhaku kesti "
                + (fringeEnd - aStarEndFringeStart) / 1e6 + " ms.";
        fringeResultLabel.setText(fringeText);

    }

}
