package coconuts;

public class CrabObserver extends AbstractObserver{
    OhCoconutsGameManager game;
    public CrabObserver(ObserverData observerData, AbstractSubject abstractSubject, OhCoconutsGameManager game) {
        super(observerData, abstractSubject);
        this.game = game;
    }

    @Override
    public void update(boolean isHit) {
        if (isHit) {
            observerData.setState(isHit);
            game.killCrab();
            abstractSubject.detach(this);
        }
    }
}
