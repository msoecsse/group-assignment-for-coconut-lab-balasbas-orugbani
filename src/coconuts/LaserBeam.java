package coconuts;

import javafx.scene.image.Image;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class Laser Purpose: Laser implementation of HittableIslandObject
 *
 * @author Peli Orugbani
 * @version created on 10/09/2025 11:30 AM
 */
// Represents the beam of light moving from the crab to a coconut; can hit only falling objects
// This is a domain class; do not introduce JavaFX or other GUI components here
public class LaserBeam extends IslandObject {
    private static final int WIDTH = 20; // must be updated with image
    private static final Image laserImage = new Image("file:images/laser-1.png");

    public LaserBeam(OhCoconutsGameManager game, int eyeHeight, int crabCenterX) {
        super(game, crabCenterX, eyeHeight, WIDTH, laserImage);
    }

    public int hittable_height() {
        return y; // top of laser
    }

    @Override
    public void step() {
        y -= 3;
    }
    
    @Override
    public boolean canHit(IslandObject other) {
        return !other.isFalling();
    }
    
    @Override
    public boolean isTouching(IslandObject other) {
        if (canHit(other)) {
            return false;
        }
        
        int laserTop = y;
        int laserBottom = y + WIDTH;
        int otherHeight = other.hittable_height();
        
        boolean verticalCollision = laserTop <= otherHeight && laserBottom >= otherHeight;
        
        int laserCenter = x + WIDTH / 2;
        int otherLeft = other.x;
        int otherRight = other.x + other.width;
        boolean horizontalCollision = laserCenter >= otherLeft && laserCenter <= otherRight;
        
        return verticalCollision && horizontalCollision;
    }
}
