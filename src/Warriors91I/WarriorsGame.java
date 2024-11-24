package Warriors91I;

import Doctrina.*;
import Doctrina.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class WarriorsGame extends Game {
    private Player player;
    private GamePad gamePad;
    private World world;
    private PlatformsBuilder platformBuilder;
    private Camera camera;
    private ArrayList<Enemy> enemies;
    private ArrayList<Physics> physicsEntities;
    private WeaponManager weaponManager;

    @Override
    protected void initialize() {
        initializeGamePad();
        initializePlayer();
        initializeEnemies();
        initializePhysicsEntities();
        initializePlatformBuilder();
        initializeCamera();
        initializeWorld();
        initializeWeaponManager();
    }

    private void initializeGamePad() {
        gamePad = new GamePad();
    }

    private void initializePlayer() {
        player = new Player(gamePad);
        player.teleport(250, 250);
    }

    private void initializeEnemies() {
        enemies = new ArrayList<>();
        enemies.add(new Enemy(100, 300));
    }

    private void initializePhysicsEntities() {
        physicsEntities = new ArrayList<>();
        physicsEntities.add(new Physics(player));
        for (Enemy enemy : enemies) {
            physicsEntities.add(new Physics(enemy));
        }
    }

    private void initializePlatformBuilder() {
        platformBuilder = new PlatformsBuilder(physicsEntities);
        platformBuilder.initializeMap();
    }

    private void initializeCamera() {
        camera = new Camera(800, 600);
    }

    private void initializeWorld() {
        world = new World();
        world.load();
    }

    private void initializeWeaponManager() {
        weaponManager = new WeaponManager();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }

        updateEnemies();
        handleMeleeAttack();
        handleWeaponAttack();
        camera.update(player);
        applyPhysics();
        platformBuilder.initializePhysics();
        handleJump();
        player.update();
        weaponManager.updateWeapons();
    }

    private void updateEnemies() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (isPlayerInAlertZone(enemy, player)) {
                enemy.moveTowardsPlayer(player);
            }
            enemy.hitPlayer(player);
            enemy.update();
            if (enemy.isDead()) {
                iterator.remove();
            }
        }
    }

    private void handleMeleeAttack() {
        if (gamePad.isAttackPressed()) {
            for (Enemy enemy : enemies) {
                if (player.intersectWith(enemy)) {
                    enemy.takeDamage(player.getAttackDamage());
                    if (enemy.isDead()) {
                        enemies.remove(enemy);
                        break;
                    }
                }
            }
        }
    }

    private void handleWeaponAttack() {
        if (gamePad.isWeaponPressed()) {
            Weapon temp = player.shoot();
            if(temp != null){
                weaponManager.addWeapon(temp);
            }
        }

        for(Enemy enemy : enemies){
            if (weaponManager.isIntersect(enemy)){
                enemy.takeDamage(weaponManager.weaponDamage());
                if (enemy.isDead()) {
                    enemies.remove(enemy);
                    break;
                }
            }
        }
    }

    private void applyPhysics() {
        for (Physics physics : physicsEntities) {
            physics.applyGravity();
        }
    }

    private void handleJump() {
        if (gamePad.isJumpPressed()) {
            physicsEntities.get(0).jump();
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.saveState();
        canvas.translate(-camera.getX(), -camera.getY());

        world.draw(canvas);
        platformBuilder.drawMap(canvas);
        drawEnemies(canvas);
        weaponManager.drawWeapons(canvas);
        player.draw(canvas);
//     CollidableRepository.getInstance().draw(canvas);

        canvas.restoreState();
        player.drawHealthBar(canvas);
    }

    private void drawEnemies(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
    }

    private boolean isPlayerInAlertZone(Enemy enemy, Player player) {
        int alertRadius = 200;
        int deltaX = player.getX() - enemy.getX();
        int deltaY = player.getY() - enemy.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= alertRadius;
    }
}
