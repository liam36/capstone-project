import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Anzeige {
    /**
     * Stellt das Lanterna-Terminal dar.
     */
    static private Terminal terminal;
    static private boolean menuOpen;
    static private boolean end;
    static private char[][] zeichen;
    static private Terminal.Color[][] farben;
    static private int xQuadrantSpieler;
    static private int yQuadrantSpieler;


    static public void startGame() {
        /**
         * Methode die das ganze Spiel über auf dem Callstack liegt.
         * Initialisiert das Spiel und beendet es.
         */
        Thread t2 = new Thread(new MonsterThread());
        terminal = TerminalFacade.createSwingTerminal();
        zeichen = new char[100][30];
        farben = new Terminal.Color[100][30];
        initialisiereFarben(farben);
        terminal.enterPrivateMode();
        terminal.setCursorVisible(false);
        Spiel.initialisieren();
        aktualisiereQuadrantSpieler();
        Menue.initialisieren();
        Legende.initialisieren();
        Spiel.open();
        aktualisiereEigenschaften();
        t2.start();
        play();
        end();
    }

    static public void play() {
        /**
         * Spielmethode mit while-Schleife bis Variable end true wird.
         * Kann das Spiel oder das Menü auf dem Terminal ausgeben, und aufrufen.
         * Bei jedem Aufruf wird die zuletzt gedrückte Taste aktualisiert.
         */
        Key key;
        while (!end) {
            key = Anzeige.terminal.readInput();
            if (key != null) {
                if (key.getKind() == Key.Kind.Escape && !Legende.isOpen()) {
                    openOrCloseMenu();
                }
                if (menuOpen) {
                    if (Legende.isOpen()) {
                        Legende.navigieren(key);
                    } else {
                        Menue.navigieren(key);
                    }
                } else {
                    Spiel.navigieren(key);
                }
            }
            Spiel.aktionsFeld();
        }
    }

    static private void end() {
        /**
         * Beendet das Spiel:
         */
        terminal.exitPrivateMode();
    }

    static public void openOrCloseMenu() {
        /**
         * Gibt mit der Methode open() aus der Klasse Spiel das Spielfeld, oder aus der Klasse Menue das Menü auf dem Terminal aus.
         */
        if (menuOpen) {
            Spiel.open();
        } else {
            Menue.open();
        }
        menuOpen = !menuOpen;
    }

    static private void initialisiereFarben(Terminal.Color[][] colorArray) {
        /**
         * Initialisiert ein Array von Farben, mit der Farbe schwarz.
         */
        for (int i = 0; i < colorArray.length; i++) {
            for (int j = 0; j < colorArray[1].length; j++) {
                colorArray[i][j] = Terminal.Color.BLACK;
            }
        }
    }

    static public void aktualisiereQuadrantSpieler() {
        /**
         * Aktualisiert den Quadrant, in dem sich der Spieler aufhält.
         * Dieser wird durch xQuadrantSpieler und yQuadrantSpieler festgelegt.
         * Wenn der Spieler den Quadranten gewechselt hat, wird der Sichbare Ausschnitt des Labyrinths aktualisiert.
         */
        int xOld = xQuadrantSpieler;
        int yOld = yQuadrantSpieler;
        xQuadrantSpieler = Spiel.getLabyrinth().getSpieler().getxPos() / 100;
        yQuadrantSpieler = Spiel.getLabyrinth().getSpieler().getyPos() / 28;
        if (xOld != xQuadrantSpieler || yOld != yQuadrantSpieler) {
            Spiel.open();
            aktualisiereEigenschaften();
        }

    }

    static public void aktualisiereEigenschaften() {
        /**
         * Aktualisiert die Schlüssel- und die Lebensanzeige.
         */
        aktualisiereSchluessel();
        aktualisiereLeben();
    }

    static public void aktualisiereSchluessel() {
        /**
         * Aktualisiert die Anzeige, ob ein Schlüssel eingesammelt wurde.
         * Vorher wird die alte Schlüsselanzeige mit Leerzeichen überschrieben.
         */
        setStringAt("                " + Spiel.getLabyrinth().getSpieler().isHatSchluessel(), Terminal.Color.BLACK, 1, 1);
        setStringAt("Schlüssel: " + Spiel.getLabyrinth().getSpieler().isHatSchluessel(), Terminal.Color.YELLOW, 1, 1);
    }

    static public void aktualisiereLeben() {
        /**
         * Aktualisiert die Lebensanzeige.
         * Enthält die Lebensanzahl weniger Ziffern als der Startwert, werden am Anfang Nullen ergänzt.
         */
        String leben = Integer.toString(Spiel.getLabyrinth().getSpieler().getAnzahlLeben());
        for (int i = leben.length(); i < 8; i++) {
            leben = 0 + leben;
        }
        setStringAt("Leben: " + leben, Terminal.Color.YELLOW, 1, 0);
    }

    static public void setCharAtPlayer(char character, Terminal.Color color, int xPos, int yPos) {
        /**
         * Zeichen wird nur auf dem Terminal ausgeben, wenn es im gleichen Quadranten mit der Spielfigur liegt.
         */
        if (xPos >= xQuadrantSpieler * 100 && xPos < (xQuadrantSpieler + 1) * 100 && yPos >= yQuadrantSpieler * 28 && yPos < (yQuadrantSpieler + 1) * 28) {
            zeichen[xPos % 100][yPos % 28] = character;
            farben[xPos % 100][yPos % 28] = color;
            putCharacterPlayer(xPos % 100, yPos % 28);
        }
    }

    static private void putCharacterPlayer(int xPos, int yPos) {
        /**
         * Gibt ein Zeichen auf dem Terminal aus.
         * Dieser wird immer um 2 nach unten verschoben ausgegeben, um Platz für die Lebens- und Schlüsselanzeige zu schaffen.
         */
        terminal.moveCursor(xPos, yPos + 2);
        terminal.applyForegroundColor(farben[xPos][yPos]);
        terminal.putCharacter(zeichen[xPos][yPos]);
    }

    static public void setCharAt(char character, Terminal.Color color, int xPos, int yPos) {
        /**
         * Zeichen wird auf dem Terminal ausgeben.
         */
        zeichen[xPos][yPos] = character;
        farben[xPos][yPos] = color;
        putCharacter(xPos, yPos);
    }

    static private void putCharacter(int xPos, int yPos) {
        /**
         * Gibt ein Zeichen an exakt der Stelle auf dem Terminal aus, die von xPos und yPos festgelegt wird.
         */
        terminal.moveCursor(xPos, yPos);
        terminal.applyForegroundColor(farben[xPos][yPos]);
        terminal.putCharacter(zeichen[xPos][yPos]);
    }

    static public void setStringAt(String string, Terminal.Color color, int xPos, int yPos) {
        /**
         * String wird auf dem Terminal ausgegeben.
         */
        for (int i = 0; i < string.length(); i++) {
            setCharAt(string.charAt(i), color, xPos + i, yPos);
        }
    }

    static public void clearAnzeige() {
        /**
         * Leer das Terminal und setzt die beiden Arrays zeichen und farben auf den Ausgangszustand zurück.
         */
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 30; j++) {
                zeichen[i][j] = 0;
                farben[i][j] = Terminal.Color.BLACK;
            }
        }
        terminal.clearScreen();
    }

    static public Terminal getTerminal() {
        return terminal;
    }

    public static synchronized boolean isEnd() {
        return end;
    }

    public static synchronized boolean isMenuOpen() {
        return menuOpen;
    }

    static public void setEnd(boolean end) {
        Anzeige.end = end;
    }
}
