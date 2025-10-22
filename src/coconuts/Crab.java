package coconuts;

import javafx.scene.image.Image;
// Represents the object that shoots down coconuts but can be hit by coconuts. Killing the
//   crab ends the game
// This is a domain class; other than Image, do not introduce JavaFX or other GUI components here
public class Crab extends HittableIslandObject implements ObserverData{
    private static final int WIDTH = 50; // assumption: height and width are the same
    private static final Image crabImage = new Image("file:images/crab-1.png");

    public Crab(OhCoconutsGameManager game, int skyHeight, int islandWidth) {
        super(game, islandWidth / 2, skyHeight, WIDTH, crabImage);
    }

    @Override
    public void step() {
        // do nothing
    }

    // Captures the crab crawling sideways
    public void crawl(int offset) {
        x += offset;
        display();
    }
    
    @Override
    public boolean canHit(IslandObject other) {
        return !other.isFalling();
    }
    
    @Override
    public boolean isGroundObject() {
        return true;
    }
    
    @Override
    protected int hittable_height() {
        return y; // top of crab
    }

    @Override
    public void setState(boolean isHit) {

    }

    @Override
    public boolean isTouching(IslandObject other) {
        if (canHit(other)) {
            return false;
        }

        int crabTop = y;
        int crabBottom = y + WIDTH;
        int otherHeight = other.hittable_height();

        boolean verticalCollision = crabTop <= otherHeight && crabBottom >= otherHeight;

        int crabCenter = x + WIDTH / 2;
        int otherLeft = other.x;
        int otherRight = other.x + other.width;
        boolean horizontalCollision = crabCenter >= otherLeft && crabCenter <= otherRight;

        return verticalCollision && horizontalCollision;
    }
}
