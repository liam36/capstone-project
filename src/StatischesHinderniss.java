import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class StatischesHinderniss implements Feld {
    /**
     * Stellt ein statisches Hinderniss dar.
     */
    @Override
    public char getSymbol() {
        return '#';
    }

    public Terminal.Color getFarbe() {
        return Terminal.Color.MAGENTA;
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
        return true;
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public String getBeschreibung() {
        return "Statisches Hinderniss";
    }
}
