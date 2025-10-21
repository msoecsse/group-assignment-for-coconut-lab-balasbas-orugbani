package coconuts;

// https://stackoverflow.com/questions/42443148/how-to-correctly-separate-view-from-model-in-javafx

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

// This class manages the game, including tracking all island objects and detecting when they hit
public class OhCoconutsGameManager {
    private final Collection<IslandObject> allObjects = new LinkedList<>();
    private final Collection<HittableIslandObject> hittableIslandSubjects = new LinkedList<>();
    private final Collection<IslandObject> scheduledForRemoval = new LinkedList<>();
    private final int height, width;
    private int LASER_INTERVAL = 0;
    private int numCoconuts = 0;
    private int numLasers = 0;
    private final Pane gamePane;
    private Crab theCrab;
    private Beach theBeach;
    private int coconutsInFlight = 0;
    private int gameTick = 0;
    private boolean isCrabDead = false;

    private final ScoreboardObserver scoreboardObserver;
    private final CrabObserver crabObserver;

    private final HitEvent coconutHitEvent;
    private final HitEvent crabHitEvent;

    public OhCoconutsGameManager(int height, int width, Pane gamePane, ScoreboardController scoreboardController) {
        this.height = height;
        this.width = width;
        this.gamePane = gamePane;

        List<Observer> coconutObservers = new ArrayList<>();
        coconutHitEvent = new HitEvent(coconutObservers);
        Scoreboard scoreboard = new Scoreboard();
        this.scoreboardObserver = new ScoreboardObserver(scoreboard, coconutHitEvent, scoreboardController);
        coconutHitEvent.attach(scoreboardObserver);

        this.theCrab = new Crab(this, height, width);
        List<Observer> crabObservers = new ArrayList<>();
        crabHitEvent = new HitEvent(crabObservers);
        this.crabObserver = new CrabObserver(theCrab, crabHitEvent, this);
        registerObject(theCrab);
        gamePane.getChildren().add(theCrab.getImageView());
        crabHitEvent.attach(crabObserver);

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

    public void coconutDestroyed() {
        coconutsInFlight -= 1;
    }

    public void tryDropCoconut() {
        int DROP_INTERVAL = 10;
        if (gameTick % DROP_INTERVAL == 0 && getCrab() != null) {
            coconutsInFlight += 1;
            Coconut c = new Coconut(this, (int) (Math.random() * width));
            numCoconuts += 1;
            registerObject(c);
            gamePane.getChildren().add(c.getImageView());
        }
        gameTick++;
    }

    public void tryShootingLaser() {
        int LASER_COUNT = 10;
        if (getCrab() != null && LASER_INTERVAL >= LASER_COUNT) {
            LaserBeam l = new LaserBeam(this, getCrab().y, getCrab().x + getCrab().width / 3);
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
        if (!isCrabDead && getCrab() != null) {
            isCrabDead = true;
            IslandObject crabToRemove = getCrab();
            scheduleForDeletion(crabToRemove);
            gamePane.getChildren().remove(crabToRemove.getImageView());
            theCrab = null;
        }
    }

    public void advanceOneTick() {
        for (IslandObject o : allObjects) {
            o.step();
            o.display();
        }

        scheduledForRemoval.clear();
        for (IslandObject thisObj : allObjects) {
            for (HittableIslandObject hittableObject : hittableIslandSubjects) {
                if (thisObj == hittableObject) {
                    continue;
                }

                if (!thisObj.isTouching(hittableObject)) {
                    continue;
                }

                boolean targetIsFalling = hittableObject.isFalling();

                if (thisObj instanceof LaserBeam && targetIsFalling) {
                    coconutHitEvent.notifyObservers(true); // Coconut destroyed by laser
                    coconutDestroyed();
                    scheduledForRemoval.add(hittableObject); // Remove coconut
                    gamePane.getChildren().remove(hittableObject.getImageView());
                    scheduledForRemoval.add(thisObj); // Remove laser
                    gamePane.getChildren().remove(thisObj.getImageView());
                    break;
                }

                if (thisObj instanceof Crab && targetIsFalling) {
                    crabHitEvent.notifyObservers(true); // Notify crab observers of the hit
                    coconutDestroyed();
                    scheduledForRemoval.add(hittableObject); // Remove coconut
                    gamePane.getChildren().remove(hittableObject.getImageView());
                    break;
                }
            }
        }

        for (IslandObject thisObj : allObjects) {
            if (thisObj.isFalling() && thisObj.isGroundObject()) {
                coconutHitEvent.notifyObservers(false); // Coconut missed
                scheduledForRemoval.add(thisObj);
                gamePane.getChildren().remove(thisObj.getImageView());
                coconutDestroyed();
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
        int MAX_TIME = 100;
        return isCrabDead || coconutsInFlight == 0 && gameTick >= MAX_TIME;
    }

    public void printState() {
        double accuracy = 0.0;
        int score = 0;
        System.out.println("Score: " + score + " Coconuts: " + numCoconuts + " Lasers: " + numLasers + " Accuracy: " + accuracy);
    }

    public void reset() {
        coconutHitEvent.detach(scoreboardObserver);
        crabHitEvent.detach(crabObserver);

        for (IslandObject obj : allObjects) {
            if (obj.getImageView() != null) {
                gamePane.getChildren().remove(obj.getImageView());
            }
        }

        allObjects.clear();
        hittableIslandSubjects.clear();
        scheduledForRemoval.clear();

        numCoconuts = 0;
        numLasers = 0;
        coconutsInFlight = 0;
        gameTick = 0;
        LASER_INTERVAL = 0;
        isCrabDead = false;

        if (scoreboardObserver.observerData instanceof Scoreboard scoreboard) {
            scoreboard.reset();
            scoreboardObserver.refreshDisplay();
        }

        // Recreate the crab
        theCrab = new Crab(this, height, width);
        registerObject(theCrab);
        gamePane.getChildren().add(theCrab.getImageView());

        // Update the crab observer's reference to the new crab
        crabObserver.observerData = theCrab;

        // Reattach observers
        coconutHitEvent.attach(scoreboardObserver);
        crabHitEvent.attach(crabObserver);
    }

}
