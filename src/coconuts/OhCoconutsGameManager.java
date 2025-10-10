package coconuts;

// https://stackoverflow.com/questions/42443148/how-to-correctly-separate-view-from-model-in-javafx

import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.LinkedList;

// This class manages the game, including tracking all island objects and detecting when they hit
public class OhCoconutsGameManager {
    private final Collection<IslandObject> allObjects = new LinkedList<>();
    private final Collection<HittableIslandObject> hittableIslandSubjects = new LinkedList<>();
    private final Collection<IslandObject> scheduledForRemoval = new LinkedList<>();
    private final int height, width;
    private final int DROP_INTERVAL = 10;
    private int LASER_INTERVAL = 0;
    private final int LASER_COUNT = 15;
    private final int MAX_TIME = 100;
    private int score = 0;
    private int numCoconuts = 0;
    private int numLasers = 0;
    private double accuracy = 0.0;
    private Pane gamePane;
    private Crab theCrab;
    private Beach theBeach;
    /* game play */
    private int coconutsInFlight = 0;
    private int gameTick = 0;

    public OhCoconutsGameManager(int height, int width, Pane gamePane) {
        this.height = height;
        this.width = width;
        this.gamePane = gamePane;

        this.theCrab = new Crab(this, height, width);
        registerObject(theCrab);
        gamePane.getChildren().add(theCrab.getImageView());

        this.theBeach = new Beach(this, height, width);
        registerObject(theBeach);
        if (theBeach.getImageView() != null)
            System.out.println("Unexpected image view for beach");
    }

    private void registerObject(IslandObject object) {
        allObjects.add(object);
        if (object.isHittable()) {
            HittableIslandObject asHittable = (HittableIslandObject) object;
            hittableIslandSubjects.add(asHittable);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void coconutDestroyed() {
        coconutsInFlight -= 1;
    }

    public void tryDropCoconut() {
        if (gameTick % DROP_INTERVAL == 0 && theCrab != null) {
            coconutsInFlight += 1;
            Coconut c = new Coconut(this, (int) (Math.random() * width));
            numCoconuts += 1;
            registerObject(c);
            gamePane.getChildren().add(c.getImageView());
        }
        gameTick++;
    }
    public void tryShootingLaser() {
        if (theCrab != null && LASER_INTERVAL >= LASER_COUNT) {
            LaserBeam l = new LaserBeam(this, theCrab.y, theCrab.x + theCrab.width/3);
            registerObject(l);
            numLasers += 1;
            gamePane.getChildren().add(l.getImageView());
            LASER_INTERVAL = 0;
        }
    }


    public Crab getCrab() {
        return theCrab;
    }

    public void killCrab() {
        theCrab = null;
    }

    public void advanceOneTick() {
        for (IslandObject o : allObjects) {
            o.step();
            o.display();
        }
        // see if objects hit; the hit itself is something you will add
        // you can't change the lists while processing them, so collect
        //   items to be removed in the first pass and remove them later
        scheduledForRemoval.clear();
        for (IslandObject thisObj : allObjects) {
            for (HittableIslandObject hittableObject : hittableIslandSubjects) {
                if (thisObj.canHit(hittableObject) && thisObj.isTouching(hittableObject)) {
                    // TODO: add code here to process the hit

                    coconutDestroyed();

                    scheduledForRemoval.add(hittableObject);
                    gamePane.getChildren().remove(hittableObject.getImageView());
                }
            }
        }
        // actually remove the objects as needed
        for (IslandObject thisObj : scheduledForRemoval) {
            allObjects.remove(thisObj);
            if (thisObj instanceof HittableIslandObject) {
                hittableIslandSubjects.remove((HittableIslandObject) thisObj);
            }
        }
        scheduledForRemoval.clear();
        LASER_INTERVAL++;
    }

    public void scheduleForDeletion(IslandObject islandObject) {
        scheduledForRemoval.add(islandObject);
    }

    public boolean done() {
        return coconutsInFlight == 0 && gameTick >= MAX_TIME;
    }
    public void  printState() {
        System.out.println("Score: " + score + " Coconuts: " + numCoconuts + " Lasers: " + numLasers + " Accuracy: " + accuracy);
    }

}
