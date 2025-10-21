package coconuts;

import java.util.List;

public abstract class AbstractSubject implements Subject {
    List<Observer> observers;
    public AbstractSubject(List<Observer> observers) {
        this.observers = observers;
    }
    public abstract void attach(Observer observer);
    public abstract void detach(Observer observer);
    public abstract void notifyObservers(boolean isHit);
}
