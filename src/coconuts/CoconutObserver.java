package coconuts;

public class CoconutObserver extends AbstractObserver {
    public CoconutObserver(ObserverData observerData, AbstractSubject abstractSubject) {
        super(observerData, abstractSubject);
    }

    @Override
    public void update(boolean isHit) {
        observerData.setState(isHit);
    }

}
