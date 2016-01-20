import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public interface Feld {
    /**
     * Stellt ein Feld im Labyrinth dar.
     * Außerdem nutzt die Klasse Spieler dieses Interface.
     */
    char getSymbol();
    Terminal.Color getFarbe();
    boolean isBetretbarSpieler();
    boolean isBetretbarMonster();
    boolean isSchmerzhaft();
    int getId();
    String getBeschreibung();;
}