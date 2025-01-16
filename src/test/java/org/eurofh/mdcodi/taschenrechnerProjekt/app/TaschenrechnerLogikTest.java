package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaschenrechnerLogikTest {

    private TaschenrechnerLogik logik;

    @BeforeEach
    void setUp() {
        logik = new TaschenrechnerLogik();
        System.out.println("Test setup: Neue Taschenrechner-Instanz erstellt.");
    }

    @Test
    void testSubtrahieren() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("-");
        logik.zifferEingeben(2);
        logik.gleichzeichen();
        String erwartet = "-1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testMultiplizieren() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("*");
        logik.zifferEingeben(2);
        logik.gleichzeichen();
        String erwartet = "2";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testAddieren() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("+");
        logik.zifferEingeben(2);
        logik.gleichzeichen();
        String erwartet = "3";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testDividieren() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("/");
        logik.zifferEingeben(2);
        logik.gleichzeichen();
        String erwartet = "0.5";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testDividierenByZero() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("/");
        logik.zifferEingeben(0);
        logik.gleichzeichen();
        String erwartet = "Fehler! Division durch Null!";
        String erhalten = logik.getAktuellerWert();
        assertEquals(erwartet, erhalten, "Fehler! Anzeige stimmt nicht überein.");
    }

    @Test
    void testVorzeichenWechseln() {
        logik.zifferEingeben(1);
        logik.vorzeichenWechseln();
        logik.gleichzeichen();
        String erwartet = "-1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testModulo() {
        logik.zifferEingeben(9);
        logik.operatorEingeben("%");
        logik.zifferEingeben(3);
        logik.gleichzeichen();
        String erwartet = "0";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testModuloByZero() {
        logik.zifferEingeben(3);
        logik.operatorEingeben("%");
        logik.zifferEingeben(0);
        logik.gleichzeichen();
        String erwartet = "Fehler! Modulo durch Null!";
        String erhalten = logik.getAktuellerWert();
        assertEquals(erwartet, erhalten, "Fehler! Nicht erwartetes Ergebniss!");
    }

    @Test
    void testPotenz() {
        logik.zifferEingeben(2);
        logik.operatorEingeben("^");
        logik.zifferEingeben(2);
        logik.gleichzeichen();
        String erwartet = "4";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testPotenzByNegative() {
        logik.zifferEingeben(4);
        logik.operatorEingeben("^");
        logik.zifferEingeben(1);
        logik.vorzeichenWechseln();
        logik.gleichzeichen();
        String erwartet = "0.25";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testPotenzByZero() {
        logik.zifferEingeben(4);
        logik.operatorEingeben("^");
        logik.zifferEingeben(0);
        logik.gleichzeichen();
        String erwartet = "1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testPotenzZeroByZero() {
        logik.zifferEingeben(0);
        logik.operatorEingeben("^");
        logik.zifferEingeben(0);
        logik.gleichzeichen();
        String erwartet = "Fehler! Potenzwerte nicht definiert!";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testKomma() {
        logik.zifferEingeben(1);
        logik.kommaEingeben();
        logik.zifferEingeben(2);
        logik.gleichzeichen();
        String erwartet = "1.2";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testKommaByNegative() {
        logik.zifferEingeben(1);
        logik.kommaEingeben();
        logik.zifferEingeben(2);
        logik.vorzeichenWechseln();
        String erwartet = "-1.2";
        String erhalten = logik.getAktuellerWert();
    }

    @Test
    void testGleiczeichenOperationWiedeholen() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("+");
        logik.zifferEingeben(1);
        logik.gleichzeichen();
        logik.gleichzeichen();
        logik.gleichzeichen();
        String erwartet = "4";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testKommaByZero() {
        logik.zifferEingeben(2);
        logik.kommaEingeben();
        logik.zifferEingeben(0);
        logik.zifferEingeben(0);
        logik.zifferEingeben(0);
        String erwartet = "2.000";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testWurzel() {
        logik.zifferEingeben(1);
        logik.zifferEingeben(6);
        logik.wurzel();
        logik.gleichzeichen();
        String erwartet = "4";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testNegativeWurzel() {
        logik.zifferEingeben(1);
        logik.zifferEingeben(6);
        logik.vorzeichenWechseln();
        logik.wurzel();
        logik.gleichzeichen();
        String erwartet = "Fehler! Negative Wurzel!";
        String erhalten = logik.getAktuellerWert();
        assertEquals(erwartet, erhalten, "Fehler! Nicht erwartetes Ergebniss!");
    }

    @Test
    void testClearEntry() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("+");
        logik.zifferEingeben(2);
        logik.operatorEingeben("-");
        logik.zifferEingeben(5);
        logik.gleichzeichen();
        logik.clearEntry();
        String erwartet = "0";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testAllClear() {
        logik.zifferEingeben(1);
        logik.operatorEingeben("+");
        logik.zifferEingeben(2);
        logik.operatorEingeben("-");
        logik.zifferEingeben(5);
        logik.gleichzeichen();
        logik.clearEntry();
        String erwartet = "0";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testMemory() {
        logik.zifferEingeben(0);
        logik.memorySpeichern();
        logik.zifferEingeben(1);
        logik.memorySpeichern();
        logik.allClear();
        logik.memoryAbrufen();
        String erwartet = "1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testMemoryByZero() {
        logik.zifferEingeben(0);
        logik.memorySpeichern();
        logik.zifferEingeben(1);
        logik.memorySpeichern();
        logik.allClear();
        logik.memoryAbrufen();
        logik.zifferEingeben(0);
        logik.memorySpeichern();
        String erwartet = "0";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testMemoryByNegative() {
        logik.zifferEingeben(0);
        logik.memorySpeichern();
        logik.zifferEingeben(1);
        logik.vorzeichenWechseln();
        logik.memorySpeichern();
        logik.allClear();
        logik.memoryAbrufen();
        String erwartet = "-1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @AfterEach
    void tearDown() {
        logik = null;
        System.out.println("Teardown: Taschenrechner-Instanz gelöscht.");
    }
}
