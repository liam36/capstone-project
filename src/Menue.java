import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.LinkedList;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Menue {
    /**
     * Stellt das Spielmenü dar.
     */
    static private LinkedList<MenueEintrag> eintraege;
    static private MenueEintrag aktuellAusgewählt;

    static public void initialisieren() {
        /**
         * Initialisiert das Menü, mit 5 Einträgen und speichert den ersten Eintrag als aktuell ausgewählten Eintrag ab.
         */
        eintraege = new LinkedList<>();
        eintraege.add(new MenueEintragFortsetzen());
        eintraege.add(new MenueEintragLaden());
        eintraege.add(new MenueEintragSpeichernUndBeenden());
        eintraege.add(new MenueEintragBeenden());
        eintraege.add(new MenueEintragLegende());

        aktuellAusgewählt = eintraege.getFirst();
    }

    static public void open() {
        /**
         * Gibt das Menü auf dem Terminal aus, und hebt den ersten Menüeintrag hervor.
         */
        printMenu(0);
    }

    static public void navigieren(Key key) {
        /**
         * Navigiert mit den beiden Pfeiltasten hoch und runter durch das Menü.
         * Mit Enter kann ein Eintrag ausgewählt werden.
         */
        if (key.getKind() == Key.Kind.ArrowUp && eintraege.indexOf(aktuellAusgewählt) > 0) {
            printMenu(eintraege.indexOf(aktuellAusgewählt) - 1);
        } else if (key.getKind() == Key.Kind.ArrowDown && eintraege.indexOf(aktuellAusgewählt) < eintraege.size() - 1) {
            printMenu(eintraege.indexOf(aktuellAusgewählt) + 1);
        } else if (key.getKind() == Key.Kind.Enter) {
            aktuellAusgewählt.interagieren();
        }
    }

    static public void printMenu(int selectedEntry) {
        /**
         * Gibt das Menü auf dem Terminal aus, und hebt den Eintrag an der Stelle selectedEntry hervor.
         */
        aktuellAusgewählt = eintraege.get(selectedEntry);
        Anzeige.clearAnzeige();
        Anzeige.setStringAt("MENÜ", Terminal.Color.YELLOW, 3, 3);
        for (int i = 0; i < eintraege.size(); i++) {
            if (i == selectedEntry) {
                Anzeige.getTerminal().applyBackgroundColor(Terminal.Color.WHITE);
                Anzeige.setStringAt(eintraege.get(i).getName(), Terminal.Color.YELLOW, 4, i * 2 + 6);
            } else {
                Anzeige.getTerminal().applyBackgroundColor(Terminal.Color.BLACK);
                Anzeige.setStringAt(eintraege.get(i).getName(), Terminal.Color.YELLOW, 4, i * 2 + 6);
            }
        }
        Anzeige.getTerminal().applyBackgroundColor(Terminal.Color.BLACK);
    }

    static public String eingabeDialogMenueEintrag(String string, String defaultString) {
        /**
         * Wird benutzt, um im Menü einen Eingabedialog um Dateinamen zum Speichern/Laden einzugeben.
         * If-Bedingung mit null, gibt es nur, um die Methode ganz zu Beginn des Spiels benutzen zu können,
         * um die Datei anzugeben, aus der das Spiel geladen werden soll.
         * stringDefault ist der vorgegebene Dateiname, der mit Enter bestätigt, oder vorher geändert werden kann.
         */
        String output = defaultString;
        int x;
        int y;
        if (Spiel.getLabyrinth() == null) {
            x = 1;
            y = 1;
        } else {
            x = 28;
            y = eintraege.indexOf(aktuellAusgewählt) * 2 + 6;
        }
        Terminal terminal = Anzeige.getTerminal();
        Key key = new Key('a');
        Anzeige.setStringAt(string, Terminal.Color.YELLOW, x, y);
        terminal.moveCursor(x + 2, y + 2);
        terminal.setCursorVisible(true);
        Anzeige.setStringAt(output, Terminal.Color.YELLOW, x + 2, y + 2);
        terminal.moveCursor(x + 2 + output.length(), y + 2);
        while (key == null || key.getKind() != Key.Kind.Enter) {
            key = terminal.readInput();
            if (key != null && (Key.Kind.NormalKey == key.getKind() || Key.Kind.Backspace == key.getKind())) {
                if (key.getKind() == Key.Kind.Backspace && output.length() > 0) {
                    output = output.substring(0, output.length() - 1);
                    Anzeige.setCharAt(' ', Terminal.Color.BLACK, x + 2 + output.length(), y + 2); //Zeichen löschen
                } else if (output.length() < 35){
                    Anzeige.setCharAt(key.getCharacter(), Terminal.Color.YELLOW, x + 2 + output.length(), y + 2);
                    output += key.getCharacter();
                }
                terminal.moveCursor(x + 2 + output.length(), y + 2);
            }
        }
        terminal.setCursorVisible(false);
        return output;
    }
}
