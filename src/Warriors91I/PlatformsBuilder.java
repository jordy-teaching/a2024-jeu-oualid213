package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Physics;
import Doctrina.StaticEntity;

import java.util.ArrayList;
import java.util.List;

public class PlatformsBuilder {

    private final List<Platform> groundPlatforms = new ArrayList<>();
    private final List<Platform> wallPlatforms = new ArrayList<>();
    private final List<Platform> deathPlatforms = new ArrayList<>();
    private final List<Platform> flyingPlatforms = new ArrayList<>();
    private final List<int[][]> maps = new ArrayList<>();
    private final List<int[][]> maps2 = new ArrayList<>();
    private final List<int[][]> maps3 = new ArrayList<>();


    private final ArrayList<Physics> physicsList;

    public PlatformsBuilder(ArrayList<Physics> physicsList) {
        this.physicsList = physicsList;

        // Chargement des cartes
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid1.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid2.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid3.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid4.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid5.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid6.csv"));

        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid8.csv"));
        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid9.csv"));
        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid10.csv"));
        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid7.csv"));
        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid12.csv"));
        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid13.csv"));
        maps2.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid14.csv"));

        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid15.csv"));
        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid16.csv"));
        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid17.csv"));
        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid18.csv"));
        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid19.csv"));
        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid20.csv"));
        maps3.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid21.csv"));





    }
    public void initializeMap() {
        generatePlatformsFromMaps();
        generatePlatformsFromMaps2();
        generatePlatformsFromMaps3();

    }

    public void initializePhysics() {
        applyPhysics(groundPlatforms, false);
        applyPhysics(flyingPlatforms, true);
    }

    private void generatePlatformsFromMaps() {
        int tileSize = 16;
        int offsetX = 0;

        for (int[][] map : maps) {
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    int value = map[row][col];
                    int x = col * tileSize + offsetX;
                    int y = row * tileSize;

                    switch (value) {
                        case 1 -> groundPlatforms.add(new Platform(x, y, PlatformType.GROUND));
                        case 2 -> wallPlatforms.add(new Platform(x, y, PlatformType.WALL));
                        case 3 -> flyingPlatforms.add(new Platform(x, y, PlatformType.ROOF));
                        case 4 -> flyingPlatforms.add(new Platform(x, y, PlatformType.FLYING));
                        case 5 -> deathPlatforms.add(new Platform(x,y,PlatformType.DEATH));
                    }
                }
            }
            offsetX += 800;
        }
    }
    private void generatePlatformsFromMaps2() {
        int tileSize = 16;
        int offsetX = 0;
        int offsetY = 600;

        for (int[][] map : maps2) {
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    int value = map[row][col];
                    int x = col * tileSize + offsetX;
                    int y = row * tileSize + offsetY;

                    switch (value) {
                        case 1 -> groundPlatforms.add(new Platform(x, y, PlatformType.GROUND));
                        case 2 -> wallPlatforms.add(new Platform(x, y, PlatformType.WALL));
                        case 3 -> flyingPlatforms.add(new Platform(x, y, PlatformType.ROOF));
                        case 4 -> flyingPlatforms.add(new Platform(x, y, PlatformType.FLYING));
                        case 5 -> deathPlatforms.add(new Platform(x,y,PlatformType.DEATH));
                    }
                }
            }
            offsetX += 800;
        }
    }
    private void generatePlatformsFromMaps3() {
        int tileSize = 16;
        int offsetX = 0;
        int offsetY = -600;

        for (int[][] map : maps3) {
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[row].length; col++) {
                    int value = map[row][col];
                    int x = col * tileSize + offsetX;
                    int y = row * tileSize + offsetY;

                    switch (value) {
                        case 1 -> groundPlatforms.add(new Platform(x, y, PlatformType.GROUND));
                        case 2 -> wallPlatforms.add(new Platform(x, y, PlatformType.WALL));
                        case 3 -> flyingPlatforms.add(new Platform(x, y, PlatformType.ROOF));
                        case 4 -> flyingPlatforms.add(new Platform(x, y, PlatformType.FLYING));
                        case 5 -> deathPlatforms.add(new Platform(x,y,PlatformType.DEATH));
                    }
                }
            }
            offsetX += 800;
        }
    }



    private void applyPhysics(List<Platform> platforms, boolean inTheAir) {
        for (Platform platform : platforms) {
            for (Physics physics : physicsList) {
                for (Blockade blockade : platform.getBlockade()) {
                    if (inTheAir) {
                        physics.checkCollisionsInTheAir(blockade);
                    } else {
                        physics.checkCollisions(blockade);
                    }
                }
                if (platform.getBlockade().length > 1) {
                    physics.roofImpact(platform.getBlockade()[1]);
                }
            }
        }
    }

    public void drawMap(Canvas canvas) {
    }

    public boolean isInDeathZone(StaticEntity entity){
        System.out.println(deathPlatforms.size());
        for (Platform platform : deathPlatforms) {
            if (entity.intersectWith(platform)){
                return true;
            }
        }
        return false;
    }
    public boolean drawDeathZone(Canvas canvas){

        return false;
    }

    private void drawPlatforms(Canvas canvas, List<Platform> platforms) {
        for (Platform platform : platforms) {
            platform.draw(canvas);
        }
    }
}

/*
* todo list:
*  i need to finish the map (potable) ---
*  i need to debug them ---
*  i need to do some refactoring ---
*  i need to put it full screen
*  i need to put the game play with the enemies
*  i need to put the object in the map
*  i need to put the child
*  i need to do the final boss
*  i need to find some bugs
*  *  i need to add the music
*  i need to refactor all the classes
*  i need to do some bugs searching
*  i need to make the menu more sweet
*  i need to go to the gym, cause i really need it
*  i need to sleep, cause its important */