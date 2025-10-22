package coconuts;

import java.util.List;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class AbstractSubject Purpose: Base class for Subjects
 *
 * @author John Balasbas
 * @version created on 10/10/2025 11:47 PM
 */
public abstract class AbstractSubject implements Subject {
    List<Observer> observers;
    public AbstractSubject(List<Observer> observers) {
        this.observers = observers;
    }
    public abstract void attach(Observer observer);
    public abstract void detach(Observer observer);
    public abstract void notifyObservers(boolean isHit);
}
