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

/**
 * Käyttölittymän runko
 * @author Heli Hyvättinen
 */

public class HexUI extends Application {
    
    /**
     * Käyttöliittymä käynnistetään kutsumalla tätä funktiota
     * @param args ohjelman komentoriviparametrit, ei tällä hetkeöllä käytetä mihinkään
     */
    public static void main(String[] args) {
        HexUI.launch(args);
    }
    
    /**
     * JavaFX kutsuu tätä sovelluksen käyttöliittymän käynnistyksen yhteydessä
     * Funktiossa alustetaan käyttöliittymä
     * @param stage 
     */
    @Override
    public void start(Stage stage) {
        Label title = new Label("Heksareitinhaku");
        Group root = new Group(title);
        Scene scene = new Scene(root,1200,800);
        stage.setScene(scene);
                
        stage.show();
    }
    
}
