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
package heksareitinhaku.io;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Lukee Battle of Wesnoth -karttoja tiedostosta
 * @author Heli Hyvättinen
 */
public class WesnothMapLoader implements MapLoader {
    
    String[][] map;
    int mapWidth;  

    /**
     * Lukee kartaan tiedostosta
     * @param filenameWithPath
     */
    @Override
    public void loadMap(String filenameWithPath) {
        
        try (FileReader fileReader = new FileReader(filenameWithPath)) {
            
        BufferedReader reader = new BufferedReader(fileReader);
            
            String line;
            ArrayList<String> lines = new ArrayList();
            
            try {
                 line = reader.readLine() ;
                 while (line !=  null) {
                    lines.add(line);
                    line = reader.readLine(); 
                     
                 }

                
                String[] terrainCodes = lines.get(0).split(",");
                
                mapWidth = terrainCodes.length; 
                
                map = new String[terrainCodes.length][lines.size()];
                
                for (int i = 0; i < lines.size(); i++) {
                    
                    terrainCodes = lines.get(i).split(",");
                    
                    for (int j = 0; j < mapWidth; j++) {
                        map[i][j] = terrainCodes[j];
                    }
     
                }
                
            } 
            catch (Exception e ){
                //Tiedostoa luettaessa tapahtui virhe, virheenkäsittely tähän
                
            }
            
        } catch (Exception e){
            // Tiedostoa ei löytynyt virheenkäsittely tähän
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[][] getMap() {
        return map;
    }

    @Override
    public int getWidth() {
        
      return mapWidth;
    }
    
}
