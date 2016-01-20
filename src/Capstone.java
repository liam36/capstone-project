/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Capstone {
    /**
     * Diese Klasse dient nur dazu, das Spiel zu starten.
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(new HauptThread());
        t1.start();
    }
}