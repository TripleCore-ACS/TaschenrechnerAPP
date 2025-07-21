package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaschenrechnerLogikTest {

    private TaschenrechnerLogik logik;
    // private static final double DELTA = 0.0001;

    @BeforeEach
    void setUp() {
        logik = new TaschenrechnerLogik();
        System.out.println("Test setup: Neue Taschenrechner-Instanz erstellt.");
    }

    @Test
    void testSinus() {
        logik.zifferEingeben(3);
        logik.zifferEingeben(0);
        //logik.setWinkelModusUmschalten();
        logik.sinus();
        String erwartet = "0.5";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testCosinus() {
        logik.zifferEingeben(6);
        logik.zifferEingeben(0);
        logik.cosinus();
        String erwartet = "0.5";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testTangens() {
        logik.zifferEingeben(4);
        logik.zifferEingeben(5);
        logik.tangens();
        String erwartet = "1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testLogarithmus10() {
        logik.zifferEingeben(1);
        logik.zifferEingeben(0);
        logik.zifferEingeben(0);
        logik.logarithmus10();
        String erwartet = "2";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testLogarithmusNaturalis() {
        logik.setE();
        logik.logarithmusNatural();
        String erwartet = "1";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testExponentialE() {
        logik.zifferEingeben(1);
        logik.exponentialE();
        double erwartet = Math.E;
        double erhalten = Double.parseDouble(logik.getAktuellerWert());
        assertEquals(erwartet, erhalten, 0.000001, "e^1 sollte ungefähr e sein");
    }

    @Test
    void testQuadrat() {
        logik.zifferEingeben(2);
        logik.quadrat();
        String erwartet = "4";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testKehrwert() {
        logik.zifferEingeben(4);
        logik.kehrwert();
        String erwartet = "0.25";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testFakultaet() {
        logik.zifferEingeben(5);
        logik.faktorial();
        String erwartet = "120";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testAbsolutwert() {
        logik.zifferEingeben(5);
        logik.vorzeichenWechseln();
        logik.absoluterWert();
        String erwartet = "5";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
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
    void testPi() {
        logik.setPi();
        double ergebnis = Double.parseDouble(logik.getAktuellerWert());
        assertEquals(Math.PI, ergebnis, 0.000001);
    }

    @Test
    void testE() {
        logik.setE();
        double ergebnis = Double.parseDouble(logik.getAktuellerWert());
        assertEquals(Math.E, ergebnis, 0.000001);
    }

    @Test
    void testWinkelModus() {
        // Teste direkt den boolean-Wert
        assertTrue(logik.istGradModus(), "Initial sollte Gradmodus (DEG) aktiv sein");
        assertEquals("DEG", logik.getWinkelModus(), "Initial sollte DEG angezeigt werden");

        logik.setWinkelModusUmschalten();
        assertFalse(logik.istGradModus(), "Nach Umschalten sollte Radiant-Modus aktiv sein");
        assertEquals("RAD", logik.getWinkelModus(), "Nach Umschalten sollte RAD angezeigt werden");

        logik.setWinkelModusUmschalten();
        assertTrue(logik.istGradModus(), "Nach erneutem Umschalten sollte Gradmodus wieder aktiv sein");
        assertEquals("DEG", logik.getWinkelModus(), "Nach erneutem Umschalten sollte DEG wieder angezeigt werden");
    }

    @Test
    void testAntwortFunktion() {
        logik.zifferEingeben(5);
        logik.operatorEingeben("+");
        logik.zifferEingeben(3);
        logik.gleichzeichen();
        logik.allClear();
        logik.antwortAbrufen();
        String erwartet = "8";
        String erhalten = logik.getAktuellerWert();
        assert erwartet.equals(erhalten);
    }

    @Test
    void testFehlerBehandlungLogarithmus() {
        // log(0) sollte Fehler auslösen
        logik.zifferEingeben(0);
        logik.logarithmus10();

        assertTrue(logik.istFehlerZustand(), "log(0) sollte Fehler auslösen");
        assertEquals("Fehler! Logarithmus undefiniert!", logik.getAktuellerWert());
    }

    @Test
    void testFehlerBehandlungFakultaet() {
        // 25! sollte Fehler auslösen (zu groß)
        logik.zifferEingeben(2);
        logik.zifferEingeben(5);
        logik.faktorial();

        assertTrue(logik.istFehlerZustand(), "25! sollte Fehler auslösen");
        assertEquals("Fehler! Zahl zu groß für Fakultät!", logik.getAktuellerWert());
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
