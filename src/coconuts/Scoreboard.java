package coconuts;

public class Scoreboard implements ObserverData {
    private int coconutsHit = 0;
    private int coconutsMissed = 0;
    private int totalCoconuts = 0;
    
    public Scoreboard() {
    }
    
    @Override
    public void setState(boolean isHit) {
        if (isHit) {
            coconutsHit++;
        } else {
            coconutsMissed++;
        }
        totalCoconuts = coconutsHit + coconutsMissed;
    }

    public int getCoconutsHit() {
        return coconutsHit;
    }
    
    public int getCoconutsMissed() {
        return coconutsMissed;
    }
    
    public int getTotalCoconuts() {
        return totalCoconuts;
    }
    
    public double getAccuracy() {
        if (totalCoconuts == 0) {
            return 0.0;
        }
        return (double) coconutsHit / totalCoconuts * 100.0;
    }
    
    public void reset() {
        coconutsHit = 0;
        coconutsMissed = 0;
        totalCoconuts = 0;
    }
}
