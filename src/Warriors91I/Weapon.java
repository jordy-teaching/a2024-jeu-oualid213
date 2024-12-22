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
    private WeaponType currentWeapon;

    BufferedImage image;

    Image weaponImage;

    public Weapon(Player player) {
        teleport(400, 250);
        setDimension(50, 50);
        this.player = player;
        this.balls = new BallsManager();
        this.hasWeapon = false;
        this.currentWeapon = WeaponType.GUN;
    }

    public Weapon(Player player, WeaponType type) {
       // teleport(5511, 370);
        teleport(400, 250);

        setDimension(50, 50);
        this.player = player;
        this.balls = new BallsManager();
        this.hasWeapon = false;
        this.currentWeapon = type;
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
        weaponImage= image.getSubimage(0, 0, 100, 100);
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
        if (isActive && player.intersectWith(this)) {
            hasWeapon = true;
            isActive = false;
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

    public void draw(Canvas canvas) {
        if (isActive) {
            canvas.drawImage(weaponImage, (int) getX(), (int) getY());
            canvas.drawString(currentWeapon.getWeaponLink(), (int) getX(), (int) getY() - 10, Color.WHITE);
        }

        balls.drawBalls(canvas);
    }


}
