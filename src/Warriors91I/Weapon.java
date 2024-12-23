package Warriors91I;

import Doctrina.Canvas;
import Doctrina.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Weapon extends StaticEntity {
    private Player player;
    private BallsManager balls;

    private boolean hasWeapon;
    private boolean isActive = true;
    private GamePad gamePad;
    private WeaponType currentWeapon;

    BufferedImage image;
    Image weaponImage;

    public Weapon(Player player, WeaponType type, int x, int y) {
        teleport(x, y);

        setDimension(100, 128);
        this.player = player;
        this.balls = new BallsManager();
        this.hasWeapon = false;
        this.currentWeapon = type;
        gamePad = new GamePad();
        load();
    }



    private void load() {
        loadAnimationFrames();
        loadSpriteSheet();
    }

    private void loadAnimationFrames() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/weapon.png"));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du sprite sheet : " + e.getMessage());
        }
    }

    private void loadSpriteSheet() {
        weaponImage= image.getSubimage(40, 10*7 - 5, 45, 20);
    }

    public void update() {
        balls.updateWeapons();

        if (balls.numberOfBalls() <= 0) {
            hasWeapon = false;
        }

        getWeapon();
    }

    public int getNumberOfBall() {
        return balls.numberOfBalls();
    }

    private void getWeapon() {
        if (gamePad.isCollectPressed()){
            if (isActive && player.intersectWith(this)) {
                hasWeapon = true;
                isActive = false;
            }
        }

    }

    public void handleWeaponAttack(GamePad gamePad, ArrayList<Enemy> enemies) {
        if (gamePad.isWeaponPressed() && hasWeapon) {
            Ball projectile = player.shoot();
            if (projectile != null) {
                balls.addBall(projectile);
                balls.oneBallLeft();
            }
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (balls.isIntersect(enemy)) {
                enemy.takeDamage(currentWeapon.getDamage());
                if (enemy.isDead()) {
                    iterator.remove();
                }
            }
        }
    }
    public boolean isHasWeapon(){
        return hasWeapon;
    }

    public void draw(Canvas canvas) {
        if (isActive) {
            canvas.drawImage(weaponImage, (int) getX(), (int) getY());
            canvas.drawString(currentWeapon.getWeaponLink(), (int) getX(), (int) getY() - 10, Color.WHITE);
        }

        balls.drawBalls(canvas);
    }


}

/*
* faire le boss final
* faire les bruit
* debug */
