package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Physics;

import java.util.ArrayList;
import java.util.List;

public class PlatformsBuilder {

    private final List<Platform> groundPlatforms = new ArrayList<>();
    private final List<Platform> wallPlatforms = new ArrayList<>();
    private final List<Platform> flyingPlatforms = new ArrayList<>();
    private final List<int[][]> maps = new ArrayList<>();
    private final ArrayList<Physics> physicsList;

    public PlatformsBuilder(ArrayList<Physics> physicsList) {
        this.physicsList = physicsList;

        // Chargement des cartes
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid1.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid2.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid3.csv"));
        maps.add(FileLoader.loadCsvFile(55, 50, "resources/IntGrid4.csv"));

    }

    // Initialisation des plateformes
    public void initializeMap() {
        generatePlatformsFromMaps();
    }

    // Application des règles de physique
    public void initializePhysics() {
        applyPhysics(groundPlatforms, false); // Physique au sol
        applyPhysics(flyingPlatforms, true);  // Physique en l'air
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

    private void drawPlatforms(Canvas canvas, List<Platform> platforms) {
        for (Platform platform : platforms) {
            platform.draw(canvas);
        }
    }
}
