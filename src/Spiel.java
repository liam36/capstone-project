import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Spiel {
    /**
     * Stellt die Spieloberfläche dar.
     * z.B. Labyrinth, Lebensanzeige, eingesammelter Schlüssel
     */
    static private Labyrinth labyrinth;

    static public void initialisieren() {
        labyrinth = new Labyrinth();
    }

    static public void open() {
        /**
         * Öffnet die Spieloberfläche:
         * - clearScreen()
         * - Labyrinth anzeigen
         * - Spielfigur anzeigen
         * - Lebens- und Schlüsselanzeige anzeigen
         */
        Anzeige.clearAnzeige();
        printLabyrinth();
        Anzeige.aktualisiereQuadrantSpieler();
        platziereSpieler();
        Anzeige.aktualisiereEigenschaften();
    }

    static public void navigieren(Key key) {
        /**
         * Ruft Methode moveSpieler in Klasse Labyrinth, mit vorheriger Überprüfung ob der Key = null ist, auf.
         * Anschließen wir mit aktualisiereSpieler() der Spieler neu platziert.
         */
        if (key != null) {
            labyrinth.moveSpieler(key);
            aktualisiereSpieler();
        }
    }

    static public void aktualisiereSpieler() {
        /**
         * Überschreibt alte Spielerposition mit dem ürsprünglichen Labyrinth und gibt Spielersymbol an neuer Position aus.
         */
        //Alt überschreiben:
        printFeld(labyrinth.feldAt(labyrinth.getSpieler().getxPosVorher(), labyrinth.getSpieler().getyPosVorher()), labyrinth.getSpieler().getxPosVorher(), labyrinth.getSpieler().getyPosVorher());

        //Neu setzen:
        platziereSpieler();
    }

    static private void platziereSpieler() {
        /**
         * Platziert die Spielfigut im Labyrinth.
         */
        printFeld(labyrinth.getSpieler(), labyrinth.getSpieler().getxPos(), labyrinth.getSpieler().getyPos());
    }

    static private void printLabyrinth() {
        /**
         * Iteriert durch das gesamte Labyrinth-Array, um das Labyrinth auf dem Terminal anzuzeigen.
         */
        for (int i = 0; i < labyrinth.getWidth(); i++) {
            for (int j = 0; j < labyrinth.getHeight(); j++) {
                printFeld(labyrinth.feldAt(i, j), i, j);
            }
        }
    }

    static public void printFeld(Feld feld, int xPos, int yPos) {
        /**
         * Gibt ein Feld des Labyrinths an der Stelle xPos / yPos mit Hilfe von Lanterna aus.
         */
        Anzeige.setCharAtPlayer(feld.getSymbol(), feld.getFarbe(), xPos, yPos);
    }

    static public void aktionsFeld() {
        /**
         * für folgende Aktionen aus, wenn das entsprechende Feld vom Spieler betreten wird:
         * - Lebenspunkte wegen statischen oder dynamischen Hinderniss abziehen, bis der Spieler tot ist und das Spiel beendet wird
         *   Hierbei wird auch die Lebensanzeige aktualisiert.
         * - Schlüssel einsammeln
         * - Spiel gewinnen wenn der Ausgang erreicht ist, und ein Schlüssel eingesammelt wurde.
         */
        int xSpieler = labyrinth.getSpieler().getxPos();
        int ySpieler = labyrinth.getSpieler().getyPos();

        if (labyrinth.feldAt(xSpieler, ySpieler).isSchmerzhaft()) {
            labyrinth.getSpieler().lebenAbziehen();
            if (labyrinth.getSpieler().getAnzahlLeben() <= 0) {
                spielBeenden("GAME OVER");
            }
        }
        if (labyrinth.feldAt(xSpieler, ySpieler) instanceof Schluessel && !labyrinth.getSpieler().isHatSchluessel()) {
            labyrinth.getSpieler().setHatSchluessel(true);
            labyrinth.setFeldAt(new Gang(), xSpieler, ySpieler);
            Spiel.printFeld(labyrinth.getSpieler(), xSpieler, ySpieler);
            Anzeige.aktualisiereSchluessel();
        }
        if (labyrinth.feldAt(xSpieler, ySpieler) instanceof Ausgang) {
            if (labyrinth.getSpieler().isHatSchluessel()) {
                spielBeenden("Gewonnen!");
            } else {
                // TODO: 16/01/16 Ausgabe, dass der Schlüssel noch nicht eingesammelt wurde.
            }
        }
    }

    static public void spielBeenden(String string) {
        /**
         * Führt zum Ende des Spiels mit vorherigem Anzeigen des eingegebenen Strings.
         */
        Anzeige.setEnd(true);
        Anzeige.clearAnzeige();
        Anzeige.setStringAt(string, Terminal.Color.YELLOW, 3, 3);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static synchronized Labyrinth getLabyrinth() {
        return labyrinth;
    }
}