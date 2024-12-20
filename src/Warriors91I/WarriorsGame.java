package Warriors91I;

import Doctrina.*;
import Doctrina.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class WarriorsGame extends Game {
    private Player player;
    private Menu menu;
    private GamePad gamePad;
    private World world;
    private PlatformsBuilder platformBuilder;
    private Camera camera;
    private Weapon weapon;
    private ArrayList<Enemy> enemies;
    private ArrayList<Physics> physicsEntities;
    private static final int PLAYER_START_X = 250;
    private static final int PLAYER_START_Y = 250;
    private static final int TARGET_FPS = 60;  // Fréquence cible de 60 FPS
    private static final long OPTIMAL_TIME_PER_FRAME = 1000000000 / TARGET_FPS;  // Nanosecondes par frame
    private long lastUpdateTime = System.nanoTime();

    @Override
    protected void initialize() {
        initializeMenu();
        initializeGamePad();
        initializePlayer();
        initializeEnemies();
        initializeWeapon();
        initializePhysicsEntities();
        initializePlatformBuilder();
        initializeCamera();
        initializeWorld();
    }

    private void initializeMenu() {
        menu = new Menu();
        menu.addOption("Start Game");
        menu.addOption("Options");
        menu.addOption("Exit");
    }

    private void initializeGamePad() {
        gamePad = new GamePad();
    }

    private void initializePlayer() {
        player = new Player(gamePad);
        player.teleport(PLAYER_START_X, PLAYER_START_Y);
    }

    private void initializeEnemies() {
        enemies = new ArrayList<>();
        enemies.add(new Ghost(100, 300));
        enemies.add(new Zombie(100, 300));
        enemies.add(new Ghost(1200, 300));
    }

    private void initializeWeapon() {
        weapon = new Weapon(player, WeaponType.FLAME_THROWER);
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

    @Override
    protected void update() {
        if (menu.isActive()) {
            menu.handleInput(gamePad);
        } else {
            long now = System.nanoTime();
            long elapsedTime = now - lastUpdateTime;

            if (elapsedTime >= OPTIMAL_TIME_PER_FRAME) {
                if (gamePad.isQuitPressed()) {
                    stop();
                }

                if (platformBuilder.isInDeathZone(player)) {
                    teleportPlayerToStart();
                }

                updateEnemies();
                handleMeleeAttack();
                weapon.handleWeaponAttack(gamePad, enemies);
                camera.update(player);
                applyPhysics();
                platformBuilder.initializePhysics();
                handleJump();
                player.update();

                weapon.update();

                lastUpdateTime = now;
            }
        }
    }


    private void teleportPlayerToStart() {
        System.out.println("Le joueur est tombé dans la death zone ! Retour au début.");
        player.teleport(PLAYER_START_X, PLAYER_START_Y);
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
            for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
                Enemy enemy = iterator.next();
                if (player.intersectWith(enemy)) {
                    enemy.takeDamage(player.getAttackDamage());
                    if (enemy.isDead()) {
                        iterator.remove();
                        break;
                    }
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
        if (menu.isActive()) {
            menu.draw(canvas);
        } else {
            canvas.saveState();
            canvas.translate(-camera.getX(), -camera.getY());

            world.draw(canvas);
            platformBuilder.drawMap(canvas);
            drawEnemies(canvas);

            platformBuilder.drawDeathZone(canvas);
            player.draw(canvas);
            weapon.draw(canvas);



            canvas.restoreState();

            canvas.drawString(Integer.toString(weapon.getNumberOfBall()), 10, 40, Color.white);

            System.out.println("fps : " + GameTime.getCurrentFps());
        }
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

