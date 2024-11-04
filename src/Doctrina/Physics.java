package Doctrina;

public class Physics {
    private static final float GRAVITY = 0.7f; // Force de gravité (accélération par frame)
    private static final int GROUND_LEVEL = 500; // Niveau du sol, à ajuster selon le jeu
    private static final float JUMP_STRENGTH = -20.0f; // Force initiale du saut

    private static final float AIR_RESISTANCE = 0.05f; // Résistance de l'air
    private static final float MAX_FALL_SPEED = 100.0f; // Vitesse maximale de chute

    private MovableEntity entity;

    public Physics(MovableEntity entity) {
        this.entity = entity;
    }

    public void applyGravity() {
        if (entity.isInAir()) {
            entity.setVerticalVelocity(entity.getVerticalVelocity() + GRAVITY);

            if (entity.getVerticalVelocity() > MAX_FALL_SPEED) {
                entity.setVerticalVelocity(MAX_FALL_SPEED);
            }

            entity.setVerticalVelocity(entity.getVerticalVelocity() - AIR_RESISTANCE);

            entity.teleport(entity.getX(), (int) (entity.getY() + entity.getVerticalVelocity()));
        }

        if (entity.getY() >= GROUND_LEVEL) {
            entity.teleport(entity.getX(), GROUND_LEVEL);
            entity.setVerticalVelocity(0);
            entity.setInAir(false);
        }
    }

    // Gère le saut de l'entité
    public void jump() {
        if (!entity.isInAir() || entity.isInAirPlatform()) {
            entity.setVerticalVelocity(JUMP_STRENGTH); // Applique la force du saut
            entity.setInAir(true); // L'entité est maintenant en l'air
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
                entity.teleport(entity.getX(), staticEntity.getY() + staticEntity.getHeight());
            }
        }
    }

}
