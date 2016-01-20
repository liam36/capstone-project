import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class DynamischesHinderniss implements Feld {
    /**
     * Stellt ein dynamisches Hinderniss dar.
     */
    private int xPos;
    private int yPos;

    public DynamischesHinderniss(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public char getSymbol() {
        return '*';
    }

    public Terminal.Color getFarbe() {
        return Terminal.Color.RED;
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

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public String getBeschreibung() {
        return "Dynamisches Hinderniss";
    }
}
