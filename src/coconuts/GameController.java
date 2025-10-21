package coconuts;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

// JavaFX Controller class for the game - generally, JavaFX elements (other than Image) should be here
public class GameController {

    /**
     * Time between calls to step() (ms)
     */
    private static final double MILLISECONDS_PER_STEP = 1000.0 / 30;
    private Timeline coconutTimeline;
    private boolean started = false;

    @FXML
    private Pane gamePane;
    @FXML
    private Pane theBeach;
    @FXML
    private Pane scoreboardPane;
    @FXML
    private Label coconutsHitLabel;
    @FXML
    private Label coconutsMissedLabel;
    @FXML
    private Label accuracyLabel;
    
    private OhCoconutsGameManager theGame;

    @FXML
    public void initialize() {
        ScoreboardController scoreboardController = new ScoreboardController();
        scoreboardController.coconutsHitLabel = this.coconutsHitLabel;
        scoreboardController.coconutsMissedLabel = this.coconutsMissedLabel;
        scoreboardController.accuracyLabel = this.accuracyLabel;
        scoreboardController.initialize();
        
        theGame = new OhCoconutsGameManager((int) (gamePane.getPrefHeight() - theBeach.getPrefHeight()),
                (int) (gamePane.getPrefWidth()), gamePane, scoreboardController);

        gamePane.setFocusTraversable(true);

        coconutTimeline = new Timeline(new KeyFrame(Duration.millis(MILLISECONDS_PER_STEP), _ -> {
            theGame.tryDropCoconut();
            theGame.advanceOneTick();
            if (theGame.done())
                coconutTimeline.pause();
        }));
        coconutTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT && !theGame.done()) {
            theGame.getCrab().crawl(10);
        } else if (keyEvent.getCode() == KeyCode.LEFT && !theGame.done()) {
            theGame.getCrab().crawl(-10);
        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            // If game is done, reset and restart
            if (theGame.done()) {
                theGame.reset();
                coconutTimeline.play();
                started = true;
            } 
            // Otherwise, toggle pause/resume
            else if (!started) {
                coconutTimeline.play();
                started = true;
            } else {
                coconutTimeline.pause();
                started = false;
            }
        } else if(keyEvent.getCode() == KeyCode.UP) {
            theGame.tryShootingLaser();
        }
    }
}
