package coconuts;

import javafx.application.Platform;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class ScoreboardObserver Purpose: Scoreboard observer implementation
 *
 * @author Peli Orugbani
 * @version created on 10/12/2025 11:30 PM
 */
public class ScoreboardObserver extends AbstractObserver {
    private final ScoreboardController controller;

    public ScoreboardObserver(ObserverData observerData, AbstractSubject abstractSubject, ScoreboardController controller) {
        super(observerData, abstractSubject);
        this.controller = controller;
    }

    @Override
    public void update(boolean isHit) {
        observerData.setState(isHit);
        if (controller != null && observerData instanceof Scoreboard scoreboard) {
            Platform.runLater(() -> controller.updateDisplay(scoreboard));
        }
    }

    public void refreshDisplay() {
        if (controller != null && observerData instanceof Scoreboard scoreboard) {
            Platform.runLater(() -> controller.updateDisplay(scoreboard));
        }
    }
}
