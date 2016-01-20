import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class MenueEintragLaden implements MenueEintrag {
    /**
     * Stellt den Eintrag Laden im Menü dar.
     */
    private String name;

    public MenueEintragLaden() {
        name = "Laden";
    }

    public void interagieren() {
        /**
         * Lädt eine Datei und wählt im Menü Fortsetzen an.
         */
        String filename = Menue.eingabeDialogMenueEintrag("Dateinamen eingeben:", "");
        try {
            Spiel.getLabyrinth().loadFile(filename);
        } catch (java.io.FileNotFoundException ioe) {
            Menue.printMenu(1);
            Anzeige.setStringAt("Datei '" + filename + "' nicht gefunden.", Terminal.Color.RED, 1, 1);
            return;
        }
        Menue.printMenu(0);
        Anzeige.setStringAt("Spiel geladen!", Terminal.Color.GREEN, 1, 1);
    }

    public String getName() {
        return name;
    }
}
