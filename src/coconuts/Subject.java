package coconuts;
/**
 * Course SWE2410-121
 * Fall 2025-2026
 * Class AbstractSubject Purpose: Base class for Subjects
 *
 * @author John Balasbas
 * @version created on 10/10/2025 11:31 PM
 */
public interface Subject {
    public void attach(Observer observer);
    public void detach(Observer observer);
    public void notifyObservers(boolean isHit);
}
