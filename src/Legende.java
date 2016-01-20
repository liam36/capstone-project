import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.LinkedList;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Legende {
    /**
     * Stellt die Menüunterseite Legende dar.
     */
    static private boolean open;
    static private LinkedList<Feld> felder;

    static public void initialisieren() {
        /**
         * Fügt der Legende die verschiedenen Einträge hinzu.
         */
        felder = new LinkedList<>();
        felder.add(new Wand());
        felder.add(new Eingang());
        felder.add(new Ausgang());
        felder.add(new StatischesHinderniss());
        felder.add(new DynamischesHinderniss(0, 0));
        felder.add(new Schluessel());
        felder.add(new Spieler());
    }

    static public void open() {
        /**
         * Gibt die Legende auf dem Terminal aus.
         */
        open = true;
        Anzeige.clearAnzeige();
        Anzeige.setStringAt("Legende (Zurück: Enter)", Terminal.Color.YELLOW, 3, 3);
        for (int i = 0; i < felder.size(); i++) {
            Anzeige.setCharAt(felder.get(i).getSymbol(), felder.get(i).getFarbe(), 4, i * 2 + 6);
            Anzeige.setStringAt(felder.get(i).getBeschreibung(), Terminal.Color.YELLOW, 6, i * 2 + 6);
        }
    }

    static public void navigieren(Key key) {
        /**
         * Verlässt mit Enter die Legende.
         */
        if (key.getKind() == Key.Kind.Enter) {
            Menue.printMenu(4);
            open = false;
        }
    }

    public static boolean isOpen() {
        return open;
    }
}
