/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class MenueEintragLegende implements MenueEintrag {
    /**
     * Stellt den Eintrag Legende im Menü dar.
     */
    private String name;

    public MenueEintragLegende() {
        name = "Legende";
    }

    public void interagieren() {
        Legende.open();
    }

    public String getName() {
        return name;
    }
}
