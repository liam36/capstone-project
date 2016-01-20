import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Wand implements Feld {
    /**
     * Stellt eine Wand im Labyrinth dar.
     */
    @Override
    public char getSymbol() {
        return ACS.BLOCK_SPARSE;
    }

    public Terminal.Color getFarbe() {
        return Terminal.Color.GREEN;
    }

    @Override
    public boolean isBetretbarSpieler() {
        return false;
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
        return 0;
    }

    @Override
    public String getBeschreibung() {
        return "Wand";
    }
}
