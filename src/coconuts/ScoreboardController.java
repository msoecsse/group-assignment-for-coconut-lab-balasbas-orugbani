package coconuts;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class ScoreboardController Purpose: Scoreboard controller for updating scoreboard display
 *
 * @author Peli Orugbani
 * @version created on 10/12/2025 11:30 PM
 */
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
