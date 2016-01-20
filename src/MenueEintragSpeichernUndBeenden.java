/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class MenueEintragSpeichernUndBeenden implements MenueEintrag{
    /**
     * Stellt den Eintrag Speichern + Beenden im Menü dar.
     */
    private String name;

    public MenueEintragSpeichernUndBeenden() {
        name = "Speichern + Beenden";
    }

    public void interagieren() {
        /**
         * Speichert das aktuelle Spiel ab und beendet es anschließend.
         */
        String filename = Menue.eingabeDialogMenueEintrag("Dateinamen eingeben:", "level.properties");
        Spiel.getLabyrinth().save(filename);
        Spiel.spielBeenden("Spiel gespeichert!");
    }

    public String getName() {
        return name;
    }
}
