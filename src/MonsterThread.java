/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class MonsterThread implements Runnable {
    /**
     * Thread der die dynmaischen Hindernisse bewegt, bis die Variable end aus der Klasse Anzeige true wird.
     */
    @Override public void run()
    {
        while (!Anzeige.isEnd()) {
            bewegen();
        }
    }

    private void bewegen() {
        /**
         * Bewegt die dynamischen Hindernisse, bis das Menü geöffnet wird.
         */
        while (!Anzeige.isMenuOpen()) {
            Spiel.getLabyrinth().moveDynamischeHindernisse();
            if (Anzeige.isEnd())
                return;
        }
    }
}
