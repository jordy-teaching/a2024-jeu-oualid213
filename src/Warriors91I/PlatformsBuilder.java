package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Physics;

import java.util.ArrayList;

public class PlatformsBuilder {

    private final ArrayList<Platform> groundPlatforms = new ArrayList<>();
    private final ArrayList<Platform> wallPlatforms = new ArrayList<>();
    private final ArrayList<Platform> flyingPlatforms = new ArrayList<>();
    private final int[][] map;
    private final ArrayList<Physics> physicsList;

    public PlatformsBuilder(ArrayList<Physics> physicsList) {
        this.physicsList = physicsList;
        this.map = FileLoader.loadCsvFile(55, 50, "resources/IntGrid.csv");
    }

    public void initializeMap() {
        generatePlatformsFromMap();
    }

    public void initializePhysics() {
        applyPhysics(groundPlatforms, false);
        applyPhysics(flyingPlatforms, true);
    }


    private void generatePlatformsFromMap() {
        int tileSize = 16;
        System.out.println(map.length);



        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int value = map[row][col];
                int x = col * tileSize;
                int y = row * tileSize;

                switch (value) {
                    case 1 -> groundPlatforms.add(new Platform(x, y, PlatformType.GROUND));
                    case 2 -> wallPlatforms.add(new Platform(x, y, PlatformType.WALL));
                    case 3 -> flyingPlatforms.add(new Platform(x, y, PlatformType.ROOF));
                    case 4 -> flyingPlatforms.add(new Platform(x, y, PlatformType.FLYING));
                }
            }
        }
    }

    private void applyPhysics(ArrayList<Platform> platforms, boolean inTheAir) {
        for (Platform platform : platforms) {
            for (Blockade blockade : platform.getBlockade()) {
                for (Physics physics : physicsList) {
                    if (inTheAir) {
                        physics.checkCollisionsInTheAir(blockade);
                    } else {
                        physics.checkCollisions(blockade);
                    }
                }
            }
            for (Physics physics : physicsList) {
                    physics.roofImpact(platform.getBlockade()[1]);
            }
        }
    }


    public void drawMap(Canvas canvas) {

    }

    private void drawPlatforms(Canvas canvas, ArrayList<Platform> platforms) {
        for (Platform platform : platforms) {
            platform.draw(canvas);
        }
    }
}
