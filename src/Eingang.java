import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Eingang implements Feld{
    /**
     * Stellt einen Eingang im Labyrinth dar.
     */
    @Override
    public char getSymbol() {
        return 'E';
    }

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
        return 1;
    }

    @Override
    public String getBeschreibung() {
        return "Eingang";
    }
}
