package coconuts;

import java.util.List;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class HitEvent Purpose: An subject class for hit events
 *
 * @author Peli Orugbani
 * @version created on 10/09/2025 11:30 AM
 */
// An abstraction of all objects that can be hit by another object
// This captures the Subject side of the Observer pattern; observers of the hit event will take action
//   to process that event
// This is a domain class; do not introduce JavaFX or other GUI components here
public class HitEvent extends AbstractSubject{
    public HitEvent(List<Observer> observers) {
        super(observers);
    }

    @Override
    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(boolean isHit) {
        for (Observer observer : observers) {
            observer.update(isHit);
        }
    }
}
