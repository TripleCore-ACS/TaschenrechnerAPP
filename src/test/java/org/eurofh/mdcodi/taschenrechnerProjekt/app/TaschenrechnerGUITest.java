package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class TaschenrechnerGUITest {

    private TaschenrechnerGUI gui;
    private TaschenrechnerLogik mocklogik;

    private JButton findeButtonMitText(String text) {
        // Alle Komponenten im tastenfeld durchlaufen
        for (Component comp : gui.getTastenfeld().getComponents()) {
            if (comp instanceof JButton) { // Prüfen, ob die Komponente ein JButton ist
                JButton button = (JButton) comp;
                if (button.getText().equals(text)) { // Prüfen, ob die Beschriftung übereinstimmt
                    return button; // Button gefunden
                }
            }
        }
        return null; // Kein Button mit der gesuchten Beschriftung gefunden
    }

    @BeforeEach
    void setUp() {
        mocklogik = Mockito.mock(TaschenrechnerLogik.class);
        gui = new TaschenrechnerGUI(mocklogik);

        // Verhalten für getAktuellerWert() konfigurieren
        Mockito.when(mocklogik.getAktuellerWert()).thenReturn("0");
    }

    @Test
    public void testZiffernTasten() {
        for (int ziffer = 0; ziffer <= 9; ziffer++) {
            String zifferAlsText = String.valueOf(ziffer);

            // Konfiguriere Mocklogik, damit jede Ziffer zurückgegeben wird
            Mockito.when(mocklogik.getAktuellerWert()).thenReturn(zifferAlsText);
            // Button mit der Ziffer suchen
            JButton taste = findeButtonMitText(zifferAlsText);
            assertNotNull(taste, "Die Taste '" + zifferAlsText + "' sollte existieren!");

            // Klick auf die Taste simulieren
            taste.doClick();

            // Überprüfen, ob das Display die Ziffer korrekt anzeigt
            String angezeigterText = gui.getDisplay().getText();
            assertEquals(zifferAlsText, angezeigterText, "Das Display zeigt nicht die richtige Ziffer an!");

            // Verifiziere, dass die Logik aufgerufen wurde
            verify(mocklogik).zifferEingeben(Integer.parseInt(zifferAlsText));
            System.out.println("Angezeigter Text im Display: " + gui.getDisplay().getText());
        }
    }

    @Test
    public void testOperatorTasten() {
        // Liste aller Operatoren-Tasten.
        String[] operatoren = {"+", "-", "*", "/", "^", "%"};

        for (String operator : operatoren) {
            JButton taste = findeButtonMitText(operator);
            assertNotNull(taste, "Die Taste '" + operator + "' sollte existieren!");

            // Klick auf die Taste simulieren
            taste.doClick();

            // Überprüfen, ob logik.operatorEingeben mit dem richtigen Operator aufgerufen wird
            verify(mocklogik).operatorEingeben(operator);
        }
    }

    @Test
    public void testKommaTaste() {
        JButton taste = findeButtonMitText(",");
        assertNotNull(taste, "Die Taste ',' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.kommaEingeben() aufgerufen wird
        verify(mocklogik).kommaEingeben();
    }

    @Test
    public void testVorzeichenWechselnTaste() {
        JButton taste = findeButtonMitText("+/-");
        assertNotNull(taste, "Die Taste '+/-' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.vorzeichenWechseln() aufgerufen wird
        verify(mocklogik).vorzeichenWechseln();
    }

    @Test
    public void testQuadratwurzelTaste() {
        JButton taste = findeButtonMitText("2√");
        assertNotNull(taste, "Die Taste '2√' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.wurzel() aufgerufen wird
        verify(mocklogik).wurzel();
    }

    @Test
    public void testGleichzeichenTaste() {
        JButton taste = findeButtonMitText("=");
        assertNotNull(taste, "Die Taste '=' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.gleichzeichen() aufgerufen wird
        verify(mocklogik).gleichzeichen();
    }

    @Test
    public void testAllClearTaste() {
        JButton taste = findeButtonMitText("AC");
        assertNotNull(taste, "Die Taste 'AC' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.allClear() aufgerufen wird
        verify(mocklogik).allClear();
    }

    @Test
    public void testClearEntryTaste() {
        JButton taste = findeButtonMitText("CE");
        assertNotNull(taste, "Die Taste 'CE' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.clearEntry() aufgerufen wird
        verify(mocklogik).clearEntry();
    }

    @Test
    public void testMemoryPlusTaste() {
        JButton taste = findeButtonMitText("M+");
        assertNotNull(taste, "Die Taste 'M+' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.memoryAbrufen() aufgerufen wird
        verify(mocklogik).memorySpeichern();
    }

    @Test
    public void testMemoryTaste() {
        JButton taste = findeButtonMitText("M");
        assertNotNull(taste, "Die Taste 'M' sollte existieren!");

        // Klick auf die Taste simulieren
        taste.doClick();

        // Überprüfen, ob logik.memory() aufgerufen wird
        verify(mocklogik).memoryAbrufen();
    }

    @AfterEach
    void tearDown() {
        // Cleanup nach jedem Test: Schließt die GUI-Komponente und gibt Ressourcen frei.
        if (gui != null) {
            JFrame frame = gui.getFrame();
            if (frame != null) {
                frame.dispose(); // Schließt das Fenster (falls vorhanden).
            }
        }
    }
}
