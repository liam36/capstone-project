/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class MenueEintragFortsetzen implements MenueEintrag {
    /**
     * Stellt den Eintrag Fortsetzen im Menü dar.
     */
    private String name;

    public MenueEintragFortsetzen() {
        name = "Fortsetzen";
    }

    public void interagieren() {
        Anzeige.openOrCloseMenu();
    }

    public String getName() {
        return name;
    }
}
