package coconuts;

public abstract class AbstractObserver implements Observer{
    protected AbstractSubject abstractSubject;
    protected ObserverData observerData;

    public AbstractObserver(ObserverData observerData, AbstractSubject abstractSubject){
        this.observerData = observerData;
        this.abstractSubject = abstractSubject;
    }

    public abstract void update(boolean isHit);
}
