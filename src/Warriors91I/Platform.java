package Warriors91I;

import Doctrina.Canvas;
import Doctrina.StaticEntity;

import java.awt.image.BufferedImage;

public class Platform extends StaticEntity {
    private static final String SPRITE_PATH = "images/outside_sprites.png";

    private Blockade[] blockade;
    private BufferedImage[] levelSprite;
    private PlatformType type;

    public Platform(int x, int y, PlatformType type) {
        teleport(x, y);
        this.type = type;
        this.blockade = configureBlockades();
        importOutsideSprites();
    }

    private Blockade[] configureBlockades() {
        switch (type) {
            case WALL:
                return createWallBlockades();
            case GROUND:
                return createGroundBlockades();
            case FLYING:
                return createFlyingBlockades();
            case EMBEDDED_GROUND:
                return createEmbeddedGroundBlockades();
            case ROOF:
                return createRoofBlockades();
            default:
                return new Blockade[0];
        }
    }



    private void importOutsideSprites() {
        BufferedImage img = (BufferedImage) LoadSave.getInstance().getMap();

        levelSprite = new BufferedImage[48];

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }
    private Blockade[] createRoofBlockades() {
        Blockade[] blockades = new Blockade[3];

        blockades[0] = new Blockade();
        blockades[0].teleport(x, y - 32); // top
        blockades[0].setDimension(16, 0);

        blockades[1] = new Blockade();
        blockades[1].teleport(x, y + 16); // bottom
        blockades[1].setDimension(16, 3);

        blockades[2] = new Blockade();
        blockades[2].teleport(x + 16, y);
        blockades[2].setDimension(1, 16);


        return blockades;

    }

    private Blockade[] createWallBlockades() {
        Blockade[] blockades = new Blockade[2];

        blockades[0] = new Blockade();
        blockades[0].teleport(x - 10, y);
        blockades[0].setDimension(1, 16);

        blockades[1] = new Blockade();
        blockades[1].teleport(x + 16, y);
        blockades[1].setDimension(1, 16);

        return blockades;
    }

    private Blockade[] createGroundBlockades() {
        Blockade[] blockades = new Blockade[4];

        blockades[0] = new Blockade();
        blockades[0].teleport(x, y - 32);
        blockades[0].setDimension(32, 10);

        blockades[1] = new Blockade();
        blockades[1].teleport(x, y + 32);
        blockades[1].setDimension(16, 10);

        blockades[2] = new Blockade();
        blockades[2].teleport(x - 10, y);
        blockades[2].setDimension(10, 16);

        blockades[3] = new Blockade();
        blockades[3].teleport(x + 32, y);
        blockades[3].setDimension(10, 16);

        return blockades;
    }

    private Blockade[] createFlyingBlockades() {
        Blockade[] blockades = new Blockade[4];

        blockades[0] = new Blockade();
        blockades[0].teleport(x, y - 32); // top
        blockades[0].setDimension(16, 10);

        blockades[1] = new Blockade();
        blockades[1].teleport(x, y + 16); // bottom
        blockades[1].setDimension(16, 3);

        blockades[2] = new Blockade();
        blockades[2].teleport(x + 16,y );
        blockades[2].setDimension(1,8);

        blockades[3] = new Blockade();
        blockades[3].teleport(x -5 ,y);
        blockades[3]. setDimension(1,8);



        return blockades;
    }

    private Blockade[] createEmbeddedGroundBlockades() {
        Blockade[] blockades = new Blockade[4];

        blockades[0] = new Blockade(); // Toit
        blockades[0].teleport(x, y - 10);
        blockades[0].setDimension(32, 10);

        blockades[1] = new Blockade(); // Bas
        blockades[1].teleport(x, y + 32);
        blockades[1].setDimension(32, 10);

        blockades[2] = new Blockade(); // Gauche
        blockades[2].teleport(x - 10, y);
        blockades[2].setDimension(10, 32);

        blockades[3] = new Blockade(); // Droite
        blockades[3].teleport(x + 32, y);
        blockades[3].setDimension(10, 32);

        return blockades;
    }

    public Blockade[] getBlockade() {
        return blockade;
    }

    @Override

    public void draw(Canvas canvas) {
        switch (type) {
            case WALL:
                canvas.drawImage(levelSprite[12], x, y); // Exemple de sprite pour les murs
                break;
            case GROUND:
                canvas.drawImage(levelSprite[10], x, y); // Exemple de sprite pour le sol
                break;
            case FLYING:
                canvas.drawImage(levelSprite[8], x, y); // Exemple de sprite pour plateformes volantes
                break;
            case EMBEDDED_GROUND:
                canvas.drawImage(levelSprite[6], x, y); // Exemple de sprite pour sol intégré
                break;
        }

//        for (int i = 0; i< blockade.length;i++){
//            blockade[i].draw(canvas);
//        }

    }
}
