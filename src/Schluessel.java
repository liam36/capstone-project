import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Schluessel implements Feld{
    /**
     * Stellt einen Schlüssel dar.
     */
    @Override
    public char getSymbol() {
        return ACS.DIAMOND;
    }

    public Terminal.Color getFarbe() {
        return Terminal.Color.CYAN;
    }

    @Override
    public boolean isBetretbarSpieler() {
        return true;
    }

    @Override
    public boolean isBetretbarMonster() {
        return false;
    }

    @Override
    public boolean isSchmerzhaft() {
        return false;
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public String getBeschreibung() {
        return "Schlüssel";
    }
}
