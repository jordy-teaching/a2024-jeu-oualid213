package Warriors91I;

import Doctrina.*;
import Doctrina.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class WarriorsGame extends Game {
    private Player player;
    private PrincipalMenu principalMenu;
    private GamePad gamePad;
    private World world;
    private PlatformsBuilder platformBuilder;
    private Camera camera;
    private Weapon weapon;
    private ArrayList<Shield> shields;
    private ArrayList<Enemy> enemies;
    private ArrayList<Physics> physicsEntities;
    private static final int PLAYER_START_X = 250;
    private static final int PLAYER_START_Y = 250;
    private static final int TARGET_FPS = 200;
    private static final long OPTIMAL_TIME_PER_FRAME = 1000000000 / TARGET_FPS;
    private long lastUpdateTime = System.nanoTime();
    private long lastFpsUpdateTime = System.nanoTime();
    private int fpsCount = 0;
    private int currentFps = 0;
    private GameOverMenu gameOverMenu;

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

        shields = new ArrayList<>();

        shields.add(new Shield(100,300));
        shields.add(new Shield(200,300));
        shields.add(new Shield(400,300));


        gameOverMenu = new GameOverMenu(this);
    }

    private void initializeMenu() {
        principalMenu = new PrincipalMenu();
    }

    private void initializeGamePad() {
        gamePad = new GamePad();
    }
    public void restartGame() {
        player.teleport(PLAYER_START_X, PLAYER_START_Y);
        player.setHealth();
        player.isAlive();

        initializeEnemies();
        initializeWeapon();
        initializePhysicsEntities();
        initializePlatformBuilder();
        initializeCamera();
        initializeWorld();


        gameOverMenu.deactivate();
        lastUpdateTime = System.nanoTime();
    }

    private void initializePlayer() {
        player = new Player(gamePad);
        player.teleport(PLAYER_START_X, PLAYER_START_Y);
    }

    private void initializeEnemies() {
        if (enemies == null) {
            enemies = new ArrayList<>();
        } else {
            enemies.clear();
        }
        enemies.add(new Zombie(1300, 230));
        //enemies.add(new Monster(600, 230));

        enemies.add(new Zombie(1700, 230));
        enemies.add(new Zombie(2800, 300));
        enemies.add(new Ghost(2700, 350));
        enemies.add(new Ghost(5490, 300));

        enemies.add(new Ghost(300, 1000));
        enemies.add(new Ghost(2000, 800));
        enemies.add(new Ghost(4200, 1000));
        enemies.add(new Zombie(4200, 1000));

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
        long now = System.nanoTime();
        long elapsedTime = now - lastUpdateTime;

        if (elapsedTime >= OPTIMAL_TIME_PER_FRAME) {
            if (principalMenu.isActive()) {
                principalMenu.handleInput(gamePad, this);
            } else if (gameOverMenu.isActive()) {
                gameOverMenu.handleInput(gamePad, this);
            } else {
                if (gamePad.isQuitPressed()) {
                    stop();
                }

                handelPlayerStat();
            }

            lastUpdateTime = now;
        }

        long sleepTime = OPTIMAL_TIME_PER_FRAME - (System.nanoTime() - now);
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime / 1_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handelPlayerStat() {
        if (platformBuilder.isInDeathZone(player)) {
            player.die();
        }

        if (player.dead()) {
            gameOverMenu.activate();
        } else {
            updateGame();
        }
    }

    private void updateGame() {
        updateEnemies();
        handleMeleeAttack();
        weapon.handleWeaponAttack(gamePad, enemies);
        camera.update(player);
        applyPhysics();
        platformBuilder.initializePhysics();
        handleJump();
        player.update();
        weapon.update();
        handelShield();
    }

    private void updateFps() {
        long now = System.nanoTime();
        fpsCount++;

        if (now - lastFpsUpdateTime >= 1_000_000_000) {
            currentFps = fpsCount;
            fpsCount = 0;
            lastFpsUpdateTime = now;
        }
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


    private void handelShield() {

        if (gamePad.isCollectPressed()) {
            for (Iterator<Shield> iterator = shields.iterator(); iterator.hasNext(); ) {
                Shield shield = iterator.next();

                if(player.intersectWith(shield)){
                    player.collectShield(shield);
                    iterator.remove();
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
        updateFps(); // Mets à jour le FPS ici

        if (principalMenu.isActive()) {
            principalMenu.draw(canvas);
        } else if (gameOverMenu.isActive()) {
            gameOverMenu.draw(canvas);
        } else {
            canvas.saveState();

            canvas.translate(-camera.getX(), -camera.getY());

            world.draw(canvas);
            platformBuilder.drawMap(canvas);
            drawEnemies(canvas);

            platformBuilder.drawDeathZone(canvas);

            player.draw(canvas);
            weapon.draw(canvas);

            for (Shield shield: shields) {
                shield.draw(canvas);
            }

            drawStatusBar(canvas, 5, System.nanoTime() - lastUpdateTime);

            canvas.restoreState();
        }
    }

    private void drawEnemies(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
    }

    public void drawStatusBar(Canvas canvas, int score, long elapsedTime) {
        int rectX = camera.getX() + 10;
        int rectY = 10;
        int rectWidth = 220;
        int rectHeight = 150;

        Color semiTransparent = new Color(0, 0, 0, 150);
        canvas.drawRectangle(camera.getX(), camera.getY(), rectWidth, rectHeight, semiTransparent);

        player.drawHealthBar(canvas, camera);
        player.drawShieldBar(canvas, camera);

        canvas.drawString("Score: " + score, rectX, camera.getY() + 70, Color.WHITE);
        canvas.drawString("Ammo: " + weapon.getNumberOfBall(), rectX, camera.getY() + 90, Color.white);
        canvas.drawString("Time: " + (elapsedTime / 1000) + "s", rectX, camera.getY() + 110, Color.WHITE);
        canvas.drawString("FPS: " + currentFps, rectX, camera.getY() + 130, Color.WHITE);
    }

    private boolean isPlayerInAlertZone(Enemy enemy, Player player) {
        int alertRadius = 200;
        int deltaX = player.getX() - enemy.getX();
        int deltaY = player.getY() - enemy.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= alertRadius;
    }
}
