import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Ausgang implements Feld {
    /**
     * Stellt einen Ausgang dar.
     */
    @Override
    public char getSymbol() {
        return 'A';
    }

    @Override
    public Terminal.Color getFarbe() {
        return Terminal.Color.WHITE;
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
        return 2;
    }

    @Override
    public String getBeschreibung() {
        return "Ausgang";
    }
}
