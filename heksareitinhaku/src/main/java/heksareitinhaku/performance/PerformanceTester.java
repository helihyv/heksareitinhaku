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
    private final int searchesPerMap = 1000;

    private MapInterpreter[] mapInterpreters;

    public void run(String[] filenames) {
        /*
        mapInterpreters = new MapInterpreter[filenames.length];
        HexMapRouteSearch[] djikstraSearches = new DjikstraHexMapRouteSearch[filenames.length];
        HexMapRouteSearch[] aStarSearches = new AStarHexMapRouteSearch[filenames.length];
        HexMapRouteSearch[] fringeSearches = new FringeHexMapRouteSearch[filenames.length];
         */

        for (int i = 0; i < filenames.length; i++) {
            runTestsForSingleMap(filenames[i]);
        }

    }

    private void runTestsForSingleMap(String filename) {

        MapLoader mapLoader = new WesnothMapLoader();

        String[][] map = null;

        try {
            File mapFile = new File(filename);
            map = mapLoader.loadMap(mapFile);
        } catch (IOException e) {
            System.out.println("Virhe ladattaessa karttatiedostoa " + filename);
        }

        if (map == null) {
            return;
        }

        MapInterpreter mapInterpreter = new WesnothMapInterpreter(map);
        DjikstraHexMapRouteSearch djikstraSearch = new DjikstraHexMapRouteSearch(mapInterpreter);
        AStarHexMapRouteSearch aStarSearch = new AStarHexMapRouteSearch(mapInterpreter);
        FringeHexMapRouteSearch fringeSearch = new FringeHexMapRouteSearch(mapInterpreter);

        double[] djikstraResults = test(djikstraSearch, mapInterpreter.getWidth(), mapInterpreter.getHeigth());
        double[] aStarResults = test(aStarSearch, mapInterpreter.getWidth(), mapInterpreter.getHeigth());
        double[] fringeResults = test(fringeSearch, mapInterpreter.getWidth(), mapInterpreter.getHeigth());

        System.out.println("Tehtiin " + searchesPerMap + " hakua kartalta "
                + filename + ", koko " + mapInterpreter.getHeigth()
                + " X " + mapInterpreter.getWidth());

        System.out.println(
                "Djikstran algoritmilla "
                + " reitin hakemiseen kului keskimäärin" + djikstraResults[0] / 1e6 + " ms"
                + " keskihajonnalla" + djikstraResults[1] / 1e6 + ".");

        System.out.println(
                "A*-algoritmilla "
                + " reitin hakemiseen kului keskimäärin  " + aStarResults[0] / 1e6 + " ms"
                + " keskihajonnalla " + aStarResults[1] / 1e6 + " .");

        System.out.println(
                "Fringe search-algoritmilla "
                + " reitin hakemiseen kului keskimäärin " + fringeResults[0] / 1e6 + " ms"
                + " keskihajonalla " + fringeResults[1] / 1e6 + ".");

        System.out.println("");
    }

    private double[] test(HexMapRouteSearch routeSearch, int mapWidth, int mapHeigth) {

        Random rand = new Random(seed);

        long totalTime = 0;

        long[] results = new long[searchesPerMap];

        for (int i = 0; i < searchesPerMap; i++) {
            int startX = rand.nextInt(mapWidth);
            int startY = rand.nextInt(mapHeigth);
            int goalX = rand.nextInt(mapWidth);
            int goalY = rand.nextInt(mapHeigth);

            long start = System.nanoTime();
            routeSearch.findRoute(startX, startY, goalX, goalY);
            long end = System.nanoTime();

            long time = end - start;
            results[i] = time;
            totalTime += time;

        }

        double mean = totalTime / searchesPerMap;

        double variance = 0;

        for (int i = 0; i < searchesPerMap; i++) {
            variance += (mean - results[i]) * (mean - results[i]);
        }

        variance /= searchesPerMap - 1;

        double[] meanAndStandardDeviation = new double[2];
        meanAndStandardDeviation[0] = mean;
        meanAndStandardDeviation[1] = Math.sqrt(variance);

        return meanAndStandardDeviation;
    }

    public static void main(String[] args) {
        PerformanceTester tester = new PerformanceTester();

        String[] mapfilenames = {
            "data/large_maps/Kalian.map",
            "data/large_maps/08b_Ray_of_Hope.map",
            //       "data/large_maps/23_Test_of_the_Clans.map",
            "data/medium_size_maps/02_The_Chase.map",
            "data/medium_size_maps/03_The_Isle_of_Alduin.map"//,    "data/medium_size_maps/05_Northern_Outpost.map"
        };

        tester.run(mapfilenames);
    }

}
