/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class MenueEintragBeenden implements MenueEintrag{
    /**
     * Stellt den Eintrag Beenden im Menü dar.
     */
    private String name;

    public MenueEintragBeenden() {
        name = "Beenden";
    }

    public void interagieren() {
        Anzeige.setEnd(true);
    }

    public String getName() {
        return name;
    }
}
