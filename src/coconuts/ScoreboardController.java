package coconuts;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScoreboardController {
    @FXML
    public Label coconutsHitLabel;
    
    @FXML
    public Label coconutsMissedLabel;
    
    @FXML
    public Label accuracyLabel;
    
    @FXML
    public void initialize() {
        updateDisplay(new Scoreboard());
    }
    
    public void updateDisplay(Scoreboard scoreboard) {
        if (coconutsHitLabel != null) {
            coconutsHitLabel.setText(String.valueOf(scoreboard.getCoconutsHit()));
        }
        if (coconutsMissedLabel != null) {
            coconutsMissedLabel.setText(String.valueOf(scoreboard.getCoconutsMissed()));
        }
        if (accuracyLabel != null) {
            accuracyLabel.setText(String.format("%.1f%%", scoreboard.getAccuracy()));
        }
    }
}

