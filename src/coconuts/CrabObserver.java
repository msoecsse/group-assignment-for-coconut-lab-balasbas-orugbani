package coconuts;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class CrabObserver Purpose: Crab observer implementation
 *
 * @author John Balasbas
 * @version created on 10/15/2025 1:35 PM
 */
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
