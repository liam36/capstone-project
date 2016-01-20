import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Spieler implements Feld{
    /**
     * Stellt die Spielfigur dar.
     */
    private int anzahlLeben;
    private int xPos;
    private int yPos;
    private int xPosVorher;
    private int yPosVorher;
    private boolean hatSchluessel;

    public Spieler() {
        anzahlLeben = 20000000;
    }

    public void moveUp() {
        aktualisieren();
        yPos--;
    }

    public void moveRight() {
        aktualisieren();
        xPos++;
    }

    public void moveDown() {
        aktualisieren();
        yPos++;
    }

    public void moveLeft() {
        aktualisieren();
        xPos--;
    }

    private void aktualisieren() {
        /**
         * Setzt die vorherige Position auf die gleiche Stelle, wie die aktuelle Position.
         */
        xPosVorher = xPos;
        yPosVorher = yPos;
    }

    public void lebenAbziehen() {
        anzahlLeben--;
    }

    @Override
    public char getSymbol() {
        return ACS.FACE_WHITE;
    }

    public Terminal.Color getFarbe() {
        return Terminal.Color.YELLOW;
    }

    @Override
    public boolean isBetretbarSpieler() {
        return false;
    }

    @Override
    public boolean isBetretbarMonster() {
        return true;
    }

    @Override
    public boolean isSchmerzhaft() {
        return false;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxPosVorher() {
        return xPosVorher;
    }

    public int getyPosVorher() {
        return yPosVorher;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public int getId() {
        return 7;
    }

    public int getAnzahlLeben() {
        return anzahlLeben;
    }

    public boolean isHatSchluessel() {
        return hatSchluessel;
    }

    public void setHatSchluessel(boolean hatSchluessel) {
        this.hatSchluessel = hatSchluessel;
    }

    public void setAnzahlLeben(int anzahlLeben) {
        this.anzahlLeben = anzahlLeben;
    }

    @Override
    public String getBeschreibung() {
        return "Spielfigur";
    }
}
