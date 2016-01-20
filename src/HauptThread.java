/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class HauptThread implements Runnable {
    /**
     * Thread in dem alles passiert, außer das Bewegen der dynamischen Hindernisse.
     */
    @Override public void run()
    {
        Anzeige.startGame();
    }
}
