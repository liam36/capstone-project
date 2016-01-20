import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;

/**
 * Created by Jan Rosemeier <jan.rosemeier@tum.de>.
 */
public class Labyrinth {
    /**
     * Stellt das Labyrinth dar, dass aus einer java-properties Datei eingelesen wurde.
     */
    private Properties properties;
    private Feld[][] labyrinth;
    private int width;
    private int height;
    private LinkedList<DynamischesHinderniss> dynamischeHindernisse;
    private Spieler spieler;
    private Random random = new Random();

    public Labyrinth() {
        initialLoadFile();
    }

    private void initialLoadFile() {
        /**
         * Stellt die Auswahl ganz zu Beginn des Spiels dar, aus welcher Datei das Labyrinth geladen werden soll.
         */
        boolean finished = false;
        while (!finished) {
            finished = true;
            try {
                loadFile(Menue.eingabeDialogMenueEintrag("Datei aus der geladen werden soll eingeben:", "level.properties"));
            } catch (java.io.FileNotFoundException ioe) {
                finished = false;
                Anzeige.setStringAt("Datei nicht gefunden!", Terminal.Color.RED, 3, 5);
            }
        }
    }

    public void loadFile(String filename) throws java.io.FileNotFoundException{
        /**
         * liest das Labyrinth aus der java-properties Datei
         * liest Länge und Breite des Labyrinths
         * speichert das Labyrinth als Array ab
         */
        properties = new Properties();

        try {
            properties.load(new FileInputStream(filename));
        } catch (IOException ioe) {
            throw new java.io.FileNotFoundException();
        }

        height = Integer.parseInt(properties.getProperty("Height"));
        width = Integer.parseInt(properties.getProperty("Width"));

        labyrinth = new Feld[width][height];
        dynamischeHindernisse = new LinkedList<>();
        spieler = new Spieler();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (properties.getProperty(i + "," + j) != null) {
                    switch (Integer.parseInt(properties.getProperty(i + "," + j))) {
                        case 0:
                            labyrinth[i][j] = new Wand();
                            break;
                        case 1:
                            labyrinth[i][j] = new Eingang();
                            spieler.setxPos(i);
                            spieler.setyPos(j);
                            break;
                        case 2:
                            labyrinth[i][j] = new Ausgang();
                            break;
                        case 3:
                            labyrinth[i][j] = new StatischesHinderniss();
                            break;
                        case 4:
                            DynamischesHinderniss temp = new DynamischesHinderniss(i, j);
                            labyrinth[i][j] = temp;
                            dynamischeHindernisse.add(temp);
                            break;
                        case 5:
                            labyrinth[i][j] = new Schluessel();
                            break;
                    }
                } else labyrinth[i][j] = new Gang();
            }
        }

        try {
            spieler.setxPos(Integer.parseInt(properties.getProperty("xPosSpieler")));
            spieler.setyPos(Integer.parseInt(properties.getProperty("yPosSpieler")));
            spieler.setAnzahlLeben(Integer.parseInt(properties.getProperty("Leben")));
            spieler.setHatSchluessel(Boolean.parseBoolean(properties.getProperty("Schluessel")));
        } catch (java.lang.NumberFormatException nfe) {
            //leer
        }
    }

    public void save(String filename) {
        /**
         * Speichert das aktuelle Spiel ab.
         */
        properties = new Properties();
        int id; //Feldbezeichnung
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                id = labyrinth[i][j].getId();
                if (id >= 0 && id <= 5) {
                    properties.setProperty(i + "," + j, Integer.toString(id));
                }
            }
        }
        properties.setProperty("Height", Integer.toString(height));
        properties.setProperty("Width", Integer.toString(width));
        properties.setProperty("xPosSpieler", Integer.toString(spieler.getxPos()));
        properties.setProperty("yPosSpieler", Integer.toString(spieler.getyPos()));
        properties.setProperty("Leben", Integer.toString(spieler.getAnzahlLeben()));
        properties.setProperty("Schluessel", Boolean.toString(spieler.isHatSchluessel()));

        try {
            properties.store(new FileOutputStream(filename), "Spielstand");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void moveSpieler(Key key) {
        /**
         * Prüft ob eine Pfeiltaste gedrückt wurde, um dann den Spieler zu bewegen.
         * Prüft ob der Spieler sich noch im selben Quadranten befindet mit Hilfe der Methode aktualisiereQuadrantSpieler().
         */
        if (key.getKind() == Key.Kind.ArrowUp && spieler.getyPos() > 0
                && feldAt(spieler.getxPos(), spieler.getyPos() - 1).isBetretbarSpieler()) {
            spieler.moveUp();
            Spiel.aktualisiereSpieler();
        } else if (key.getKind() == Key.Kind.ArrowRight && spieler.getxPos() < width - 1
                && feldAt(spieler.getxPos() + 1, spieler.getyPos()).isBetretbarSpieler()) {
            spieler.moveRight();
            Spiel.aktualisiereSpieler();
        } else if (key.getKind() == Key.Kind.ArrowDown && spieler.getyPos() < height - 1
                && feldAt(spieler.getxPos(), spieler.getyPos() + 1).isBetretbarSpieler()) {
            spieler.moveDown();
            Spiel.aktualisiereSpieler();
        } else if (key.getKind() == Key.Kind.ArrowLeft && spieler.getxPos() > 0
                && feldAt(spieler.getxPos() - 1, spieler.getyPos()).isBetretbarSpieler()) {
            spieler.moveLeft();
            Spiel.aktualisiereSpieler();
        }
        Anzeige.aktualisiereQuadrantSpieler();
    }

    public Feld feldAt(int xPos, int yPos) {
        /**
         * Gibt das Feld im Array labyrinth an der eingegebene Position zurück.
         */
        return labyrinth[xPos][yPos];
    }

    public void setFeldAt(Feld feld, int xPos, int yPos) {
        /**
         * Setz das Feld im Array labyrinth an der eingegebenen Position auf den Parameter feld.
         */
        labyrinth[xPos][yPos] = feld;
    }

    public synchronized void moveDynamischeHindernisse() {
        /**
         * Bewegt die Dynamischen Hindernisse zufällig.
         * Die Methode ist mit Hilfe von Thread.sleep() verlangsamt.
         */
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        int direction;
        for (int i = 0; i < dynamischeHindernisse.size(); i++) {
            if (Anzeige.isMenuOpen()) //Abbruchbedingung
                    return;
            boolean moved = false;
            int x = dynamischeHindernisse.get(i).getxPos();
            int y = dynamischeHindernisse.get(i).getyPos();
            Gang gang = new Gang();
            DynamischesHinderniss dynamischesHinderniss = dynamischeHindernisse.get(i);
            while (!moved) {
                direction = random.nextInt(4);
                /**
                 * 4 Fälle:
                 * 0 - Norden
                 * 1 - Osten
                 * 2 - Süden
                 * 3 - Westen
                 * Das dynamische Hinderniss bewegt sich in die jeweilige Himmelsrichtung wenn dies möglich ist.
                 * D.h. das dynamische Hinderniss kann sich nur im Gang zwischen den Wänden befinden.
                 */
                switch (direction) {
                    case 0:
                        if (y - 1 >= 0 && labyrinth[x][y - 1].isBetretbarMonster()) {
                            labyrinth[x][y] = gang;
                            labyrinth[x][y - 1] = dynamischesHinderniss;
                            Spiel.printFeld(gang, x, y);
                            Spiel.printFeld(dynamischesHinderniss, x, y - 1);
                            dynamischesHinderniss.setyPos(y - 1);
                            moved = true;
                        }
                        break;
                    case 1:
                        if (x + 1 < width && labyrinth[x + 1][y].isBetretbarMonster()) {
                            labyrinth[x][y] = gang;
                            labyrinth[x + 1][y] = dynamischesHinderniss;
                            Spiel.printFeld(gang, x, y);
                            Spiel.printFeld(dynamischesHinderniss, x + 1, y);
                            dynamischesHinderniss.setxPos(x + 1);
                            moved = true;
                        }
                        break;
                    case 2:
                        if (y + 1 < height && labyrinth[x][y + 1].isBetretbarMonster()) {
                            labyrinth[x][y] = gang;
                            labyrinth[x][y + 1] = dynamischesHinderniss;
                            Spiel.printFeld(gang, x, y);
                            Spiel.printFeld(dynamischesHinderniss, x, y + 1);
                            dynamischesHinderniss.setyPos(y + 1);
                            moved = true;
                        }
                        break;
                    case 3:
                        if (x - 1 >= 0 && labyrinth[x - 1][y].isBetretbarMonster()) {
                            labyrinth[x][y] = gang;
                            labyrinth[x - 1][y] = dynamischesHinderniss;
                            Spiel.printFeld(gang, x, y);
                            Spiel.printFeld(dynamischesHinderniss, x - 1, y);
                            dynamischesHinderniss.setxPos(x - 1);
                            moved = true;
                        }
                        break;
                }
            }
        }
        Anzeige.aktualisiereLeben();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Spieler getSpieler() {
        return spieler;
    }
}