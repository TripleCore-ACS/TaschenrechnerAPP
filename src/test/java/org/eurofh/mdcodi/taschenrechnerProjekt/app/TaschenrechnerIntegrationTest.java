package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaschenrechnerIntegrationTest {
    private TaschenrechnerStart app; // Hauptklasse der App
    private TaschenrechnerGUI gui; // GUI-Logik
    private TaschenrechnerLogik logik; // Berechnungslogik

    @BeforeEach
    void setup() {
        // Initialisiert alle benötigten Komponenten der Anwendung
        logik = new TaschenrechnerLogik(); // Erstellt eine Instanz der Logik
        gui = new TaschenrechnerGUI(logik);    // Erstellt die GUI-Komponente
        app = new TaschenrechnerStart(); // Verbindung zwischen GUI und Logik
    }

    @Test
    void testAddition() {
        // Simuliere Button-Klicks
        gui.findButton("5").doClick(); // Klickt auf "5"
        gui.findButton("+").doClick(); // Klickt auf "+"
        gui.findButton("3").doClick(); // Klickt auf "3"
        gui.findButton("=").doClick(); // Klickt auf "-"

        // Überprüfe das Ergebnis im Display
        String ergebnis = gui.getDisplay().getText(); // Ruft den GUI-Anzeigetext ab und speichert diesen in "ergebnis"
        /* Vergleicht eine "erwarteten" Wert mit dem "ergebnis" Wert ||
        Ausgabe eines Rückgabetextes bei Misslingen des Tests*/
        assertEquals("8", ergebnis, "Die Addition 5 + 3 sollte 8 ergeben.");
    }

    @Test
    void testSubtraktion() {
        gui.findButton("1").doClick();
        gui.findButton("0").doClick(); // "1" und "0" eingeben ergibt "10"
        gui.findButton("-").doClick();  // "-" auswählen
        gui.findButton("4").doClick();  // "4" eingeben
        gui.findButton("=").doClick();  // "=" bestätigen

        String ergebnis = gui.getDisplay().getText();
        assertEquals("6", ergebnis, "Die Subtraktion 10 - 4 sollte 6 ergeben.");
    }

    @Test
    void testKettenoperation() {
        gui.findButton("5").doClick();
        gui.findButton("+").doClick();
        gui.findButton("3").doClick();
        gui.findButton("-").doClick(); // Klickt auf "-"
        gui.findButton("9").doClick(); // Klickt auf "9"
        gui.findButton("=").doClick(); // Klickt auf "="

        String ergebnis = gui.getDisplay().getText();
        assertEquals("-1", ergebnis, "Die Addition 5 + 3 - 9 sollte -1 ergeben.");
    }

    @Test
    void testMultiplikation() {
        gui.findButton("1").doClick();
        gui.findButton("0").doClick();
        gui.findButton("*").doClick();
        gui.findButton("2").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("20", ergebnis, "Die Multiplikation 10 * 2 sollte 20 ergeben.");
    }

    @Test
    void testDivision() {
        gui.findButton("2").doClick();
        gui.findButton("0").doClick();
        gui.findButton("/").doClick();
        gui.findButton("5").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("4", ergebnis, "Die Division 20 / 5 sollte 4 ergeben.");
    }

    @Test
    void testDivisionDurchNull() {
        gui.findButton("0").doClick();
        gui.findButton("/").doClick();
        gui.findButton("0").doClick();
        gui.findButton("=").doClick();

        String result = gui.getDisplay().getText();
        assertEquals("Fehler! Division durch Null!", result, "Division durch 0 sollte eine Fehlermeldung ausgeben.");
    }

    @Test
    void testVorzeichenWechsel() {
        gui.findButton("1").doClick();
        gui.findButton("7").doClick();
        gui.findButton("+/-").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("-17", ergebnis, "Der Vorzeichenwechsel von 17 sollte -17 ergeben.");
    }

    @Test
    void testZifferEingeben() {
        gui.findButton("1").doClick();
        gui.findButton("2").doClick();
        gui.findButton("3").doClick();
        gui.findButton("4").doClick();
        gui.findButton("5").doClick();
        gui.findButton("6").doClick();
        gui.findButton("7").doClick();
        gui.findButton("8").doClick();
        gui.findButton("9").doClick();
        gui.findButton("0").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("1234567890", ergebnis, "Die Eingabe aller Ziffern sollte 1234567890 ergeben.");
    }

    @Test
    void testPotenz() {
        gui.findButton("9").doClick();
        gui.findButton("^").doClick();
        gui.findButton("2").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("81", ergebnis, "Die Potenz von 9 mit 2 sollte 81 ergeben.");
    }

    @Test
    void testPotenzByNegative() {
        gui.findButton("9").doClick();
        gui.findButton("+/-").doClick();
        gui.findButton("^").doClick();
        gui.findButton("2").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("81", ergebnis, "Die Potenz von -9 mit 2 sollte 81 ergeben.");
    }

    @Test
    void testPotenzMitNegativemExponent() {
        gui.findButton("1").doClick();
        gui.findButton("6").doClick();
        gui.findButton("^").doClick();
        gui.findButton("1").doClick();
        gui.findButton("+/-").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("0.0625", ergebnis, "Die Potenz von 16 mit -1 sollte 0.0625 ergeben.");
    }

    @Test
    void testKommaEingeben() {
        gui.findButton("1").doClick();
        gui.findButton(",").doClick();
        gui.findButton("2").doClick();
        gui.findButton("0").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("1.20", ergebnis, "Die Anzeige sollte 1,20 ergeben.");
    }

    @Test
    void testOperatorEingeben() {
        gui.findButton("1").doClick();
        gui.findButton("+").doClick();
        gui.findButton("4").doClick();
        gui.findButton("-").doClick();
        gui.findButton("2").doClick();
        gui.findButton("*").doClick();
        gui.findButton("2").doClick();
        gui.findButton("7").doClick();
        gui.findButton("/").doClick();
        gui.findButton("9").doClick();
        gui.findButton("=").doClick();  // Abschluss der Kettenoperation
        gui.findButton("2√").doClick();
        gui.findButton("^").doClick();
        gui.findButton("2").doClick();
        gui.findButton("=").doClick();
        gui.findButton("%").doClick();
        gui.findButton("2").doClick();
        gui.findButton("=").doClick(); // Finaler Abschluss des Rechenweges

        String ergebnis = gui.getDisplay().getText();
        assertEquals("1", ergebnis, "Das Ergebnis dieser Kettenoperation sollte 1 ergeben.");
    }

    @Test
    void testWurzel() {
        gui.findButton("8").doClick();
        gui.findButton("1").doClick();
        gui.findButton("2√").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("9", ergebnis, "Die Wurzel von 25 sollte 5 ergeben.");
    }

    @Test
    void testNegativeWurzel() {
        gui.findButton("3").doClick();
        gui.findButton("+/-").doClick();
        gui.findButton("2√").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("Fehler! Negative Wurzel!", ergebnis, "Die Wurzel von -3 sollte eine Fehlermeldung ausgeben.");
    }

    @Test
    void testMemorySpeichern() {
        gui.findButton("4").doClick();
        gui.findButton("6").doClick();
        gui.findButton("M+").doClick();
        gui.findButton("AC").doClick();
        gui.findButton("M").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("46", ergebnis, "Der Speichervorgang von 46 sollte 46 ergeben.");
    }

    @Test
    void testClearEntry() {
        gui.findButton("1").doClick();
        gui.findButton("0").doClick();
        gui.findButton("+").doClick();
        gui.findButton("3").doClick();
        gui.findButton("CE").doClick();
        gui.findButton("9").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("19", ergebnis, "Der Einsatz von CE sollte 19 ergeben.");
    }

    @Test
    void testAllClear() {
        gui.findButton("5").doClick();
        gui.findButton("+").doClick();
        gui.findButton("3").doClick();
        gui.findButton("AC").doClick();
        gui.findButton("6").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("6", ergebnis, "Der Einsatz von AC sollte 6 ergeben.");
    }

    @Test
    void testMehrfachGleichzeichen() {
        gui.findButton("5").doClick();
        gui.findButton("+").doClick();
        gui.findButton("3").doClick();
        gui.findButton("=").doClick();
        gui.findButton("=").doClick();
        gui.findButton("=").doClick();

        String ergebnis = gui.getDisplay().getText();
        assertEquals("14", ergebnis, "Der 3malige Einsatz '=' sollte 14 ergeben.");
    }

    @AfterEach
    void tearDown() {
        // Schließt das JFrame-Fenster, falls es noch sichtbar ist
        if (gui.getFrame().isDisplayable()) {
            gui.getFrame().dispose(); // Gibt die Ressourcen frei
        }

        // Setzt die Logik auf den Ausgangszustand zurück
        logik.allClear(); // Volle Zurücksetzung der Logik-Interna

        // Optionale Protokollierung für Diagnosezwecke
        System.out.println("Test-Umgebung nach Test zurückgesetzt.");
    }
}