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
package heksareitinhaku.performance;

import heksareitinhaku.io.MapLoader;
import heksareitinhaku.io.WesnothMapLoader;
import heksareitinhaku.logic.AStarHexMapRouteSearch;
import heksareitinhaku.logic.DjikstraHexMapRouteSearch;
import heksareitinhaku.logic.FringeHexMapRouteSearch;
import heksareitinhaku.logic.HexMapRouteSearch;
import heksareitinhaku.logic.MapInterpreter;
import heksareitinhaku.logic.WesnothMapInterpreter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * rchs
 *
 * @author Heli Hyvättinen
 */
public class PerformanceTester {

    private final int seed = 501;
    private final int searchesPerMap = 100;

    private MapInterpreter[] mapInterpreters;

    public void run() {
        File dir = new File("src/test/resources/");
        String[] filenames = dir.list();
        mapInterpreters = new MapInterpreter[filenames.length];
        HexMapRouteSearch[] djikstraSearches = new DjikstraHexMapRouteSearch[filenames.length];
        HexMapRouteSearch[] aStarSearches = new AStarHexMapRouteSearch[filenames.length];
        HexMapRouteSearch[] fringeSearches = new FringeHexMapRouteSearch[filenames.length];

        MapLoader mapLoader = new WesnothMapLoader();

        for (int i = 0; i < filenames.length; i++) {

            try {
                File mapFile = new File(filenames[i]);
                String[][] map = mapLoader.loadMap(mapFile);
                MapInterpreter mapInterpreter = new WesnothMapInterpreter(map);
                mapInterpreters[i] = mapInterpreter;
                djikstraSearches[i] = new DjikstraHexMapRouteSearch(mapInterpreter);
                aStarSearches[i] = new AStarHexMapRouteSearch(mapInterpreter);
                fringeSearches[i] = new FringeHexMapRouteSearch(mapInterpreter);

            } catch (IOException e) {
                System.out.println("Virhe ladattaessa karttatiedostoa " + filenames[i]);
            }

        }

        long djikstraTime = test(djikstraSearches);
        long aStarTime = test(aStarSearches);
        long fringeTime = test(fringeSearches);

        System.out.println("Djikstran algoritmilla " + filenames.length * searchesPerMap
                + " hun tekemiseen kului " + djikstraTime / 1e9 + " s.");

        System.out.println("A*-algoritmilla " + filenames.length * searchesPerMap
                + " hun tekemiseen kului " + djikstraTime / 1e9 + " s.");

        System.out.println("Fringe search-algoritmilla " + filenames.length * searchesPerMap
                + " hun tekemiseen kului " + djikstraTime / 1e9 + " s."
        );

    }

    private long test(HexMapRouteSearch[] routeSearchs) {

        Random rand = new Random(seed);

        long start = System.nanoTime();

        for (int i = 0; i < routeSearchs.length; i++) {

            HexMapRouteSearch routeSearch = routeSearchs[i];
            int mapWidth = mapInterpreters[i].getWidth();
            int mapHeigth = mapInterpreters[i].getHeigth();
            for (int j = 0; j < searchesPerMap; j++) {
                int startX = rand.nextInt(mapWidth);
                int startY = rand.nextInt(mapHeigth);
                int goalX = rand.nextInt(mapWidth);
                int goalY = rand.nextInt(mapHeigth);

                routeSearchs[i].findRoute(startX, startY, goalX, goalY);
            }
        }

        long end = System.nanoTime();

        return end - start;
    }

}
