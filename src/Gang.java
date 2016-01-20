import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Gang implements Feld{
    /**
     * Stellt ein Feld vom Typ Gang dar.
     */
    @Override
    public char getSymbol() {
        return ' ';
    }

    public Terminal.Color getFarbe() {
        return Terminal.Color.BLACK;
    }

    @Override
    public boolean isBetretbarSpieler() {
        return true;
    }

    @Override
    public boolean isBetretbarMonster() {
        return true;
    }

    @Override
    public boolean isSchmerzhaft() {
        return false;
    }

    @Override
    public int getId() {
        return 6;
    }

    @Override
    public String getBeschreibung() {
        return "Gang";
    }
}
