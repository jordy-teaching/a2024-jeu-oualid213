package Doctrina;

public class Physics {
    private static final float GRAVITY = 0.8f;
    private static final float JUMP_STRENGTH = -15.0f;
    private static final float AIR_RESISTANCE = 0.03f;
    private static final float MAX_FALL_SPEED = 100.0f;

    private MovableEntity entity;

    public Physics(MovableEntity entity) {
        this.entity = entity;
    }

    public void applyGravity() {
        entity.setVerticalVelocity(Math.min(entity.getVerticalVelocity() + GRAVITY, MAX_FALL_SPEED));

        entity.setVerticalVelocity(entity.getVerticalVelocity() - AIR_RESISTANCE);

        entity.teleport(entity.getX(), (int) (entity.getY() + entity.getVerticalVelocity()));
    }

    public void jump() {
        if (!entity.isInAir()) {
            entity.setVerticalVelocity(JUMP_STRENGTH);
            entity.setInAir(true);
        }
        if (entity.isInAirPlatform()){
            entity.setVerticalVelocity(JUMP_STRENGTH);
            entity.setInAir(true);
            entity.setInAirPlatform(false);
        }
    }

    public void checkCollisions(StaticEntity staticEntity) {
        if (entity.hitBoxIntersectWith(staticEntity)) {
            if (entity.getVerticalVelocity() > 0) {
                entity.teleport(entity.getX(), staticEntity.getY() - entity.getHeight());
                entity.setVerticalVelocity(0);
                entity.setInAir(false);
                entity.setInAirPlatform(false);
            }
        }
    }


    public void checkCollisionsInTheAir(StaticEntity staticEntity) {
        if (entity.hitBoxIntersectWith(staticEntity)) {
            if (entity.getVerticalVelocity() > 0) {
                entity.teleport(entity.getX(), staticEntity.getY() - entity.getHeight());
                entity.setVerticalVelocity(0);
                entity.setInAirPlatform(true);
            }
        }
    }
    public void roofImpact(StaticEntity staticEntity) {
        if (entity.hitBoxIntersectWith(staticEntity)) {
            if (entity.getVerticalVelocity() < 0) {
                entity.setVerticalVelocity(1);
                System.out.println(staticEntity.getHeight() );
                entity.teleport(entity.getX(), staticEntity.getY() + entity.getHeight() );
            }
        }
    }
}
