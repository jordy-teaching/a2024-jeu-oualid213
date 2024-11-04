package Warriors91I;

import Doctrina.Canvas;
import Doctrina.StaticEntity;
import Viking.Blockade;

import java.awt.image.BufferedImage;

public class Platform extends StaticEntity {

    private static final String SPRITE_PATH = "images/outside_sprites.png";

    private Blockade[] blockade;
    private BufferedImage[] levelSprite;



    public Platform(int x, int  y) {
        teleport(x, y);

        blockade = new Blockade[4];

        for (int i = 0; i<4 ; i ++ ){
            blockade[i] = new Blockade();
        }

        // 32 is the width of the platform
        blockade[0].teleport(x,y - 32 );
        blockade[0].setDimension(32,10);

        blockade[1].teleport(x,y + 32 );
        blockade[1].setDimension(33,1);

        blockade[2].teleport(x + 32,y );
        blockade[2].setDimension(1,32);

        blockade[3].teleport(x -10 ,y);
        blockade[3].setDimension(10,32);

        importOutsideSprites();
    }

    private void importOutsideSprites() {
        BufferedImage img = (BufferedImage) LoadSave.getInstance().getMap();

        levelSprite = new BufferedImage[48];

        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }



    public Blockade[] getBlockade() {
        return blockade;
    }

    public void draw(Canvas canvas) {
        canvas.drawImage(levelSprite[20], x, y);

    }
}
