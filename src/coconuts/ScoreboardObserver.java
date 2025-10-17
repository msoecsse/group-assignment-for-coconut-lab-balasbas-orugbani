package coconuts;

import javafx.application.Platform;

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
}
