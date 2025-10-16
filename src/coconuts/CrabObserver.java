package coconuts;

public class CrabObserver extends AbstractObserver{
    public CrabObserver(ObserverData observerData, AbstractSubject abstractSubject) {
        super(observerData, abstractSubject);
    }

    @Override
    public void update(boolean isHit) {
        observerData.setState(isHit);
    }
}
