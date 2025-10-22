package coconuts;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class AbstractObserver Purpose: Base class for Observers
 *
 * @author John Balasbas
 * @version created on 10/10/2025 11:45 PM
 */
public abstract class AbstractObserver implements Observer{
    protected AbstractSubject abstractSubject;
    protected ObserverData observerData;

    public AbstractObserver(ObserverData observerData, AbstractSubject abstractSubject){
        this.observerData = observerData;
        this.abstractSubject = abstractSubject;
    }

    public abstract void update(boolean isHit);
}
