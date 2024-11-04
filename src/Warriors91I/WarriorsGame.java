package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Game;
import Doctrina.Physics;

public class WarriorsGame extends Game {
    private Player player;
    private GamePad gamePad;
    private World world;

    private PlatformsBuilder platformBuilder;
    private Platform[] horizontalPlatform;
    private Platform[] flyingPlatform;

    private Physics physics;

    @Override
    protected void initialize() {
        gamePad = new GamePad();
        player = new Player(gamePad);
        player.teleport(250, 250);
        physics = new Physics(player);
        platformBuilder = new PlatformsBuilder(physics);

        horizontalPlatform = platformBuilder.createHorizontalPlatform(-100, 400, 100);
        flyingPlatform = platformBuilder.createHorizontalPlatform(0, 200, 20);


        world = new World();
        world.load();
    }

    @Override
    protected void update() {
        if (gamePad.isQuitPressed()) {
            stop();
        }

        physics.applyGravity();

        if (gamePad.isJumpPressed()) {
            physics.jump();
        }


        platformBuilder.physicsSetUp(horizontalPlatform, false);

        platformBuilder.physicsSetUp(flyingPlatform, true);
        player.update();
    }

    @Override
    protected void draw(Canvas canvas) {
        world.draw(canvas);

        platformBuilder.drawPlatforms(canvas, horizontalPlatform);
        platformBuilder.drawPlatforms(canvas, flyingPlatform);

        player.draw(canvas);
    }
}
