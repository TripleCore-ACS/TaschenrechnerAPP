package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaschenrechnerLogik {
    // Logger zur Protokollierung von Fehlern und Debugging-Informationen.
    private static final Logger LOGGER = Logger.getLogger(TaschenrechnerLogik.class.getName());

    // Speicher für den aktuellen Wert und vorherige Berechnungen.
    private BigDecimal aktuellerWert = BigDecimal.ZERO;
    private BigDecimal vorherigerWert = BigDecimal.ZERO;
    private BigDecimal memory = BigDecimal.ZERO;
    private BigDecimal letzterOperand = BigDecimal.ZERO;
    private BigDecimal letzteAntwort = BigDecimal.ZERO;

    // Speichert den aktuellen Operator und Statusinformationen.
    private String operator = "";
    private boolean neueEingabe = true; // True, wenn der Benutzer eine neue Zahl eingibt.
    private boolean fehlerZustand = false; // True, wenn ein Fehler aufgetreten ist.
    private boolean kommaGesetzt = false; // True, wenn ein Dezimalpunkt eingegeben wurde.
    private boolean winkelModus = true;
    private static final int DEZIMALSTELLEN = 10; // Maximale Dezimalstellen.
    private String aktuellerFehler = ""; // Beschreibung des aktuellen Fehlers.
    private java.util.Stack<BigDecimal> werteStack = new java.util.Stack<>();
    private java.util.Stack<String> operatorStack = new java.util.Stack<>();
    private int klammerEbene = 0;

    // Wichtige mathematische Konstanten.
    public static final BigDecimal PI = BigDecimal.valueOf(Math.PI);
    public static final BigDecimal E = BigDecimal.valueOf(Math.E);

    // Fehlerbehandlung: Setzt den Rechner in einen Fehlerzustand.
    public void fehlerBehandlung(String fehlerNachricht) {
        LOGGER.log(Level.SEVERE, fehlerNachricht); // Protokolliert den Fehler.
        aktuellerWert = BigDecimal.ZERO; // Setzt den aktuellen Wert zurück.
        fehlerZustand = true; // Aktiviert den Fehlerzustand.
        aktuellerFehler = fehlerNachricht; // Speichert die Fehlermeldung.
    }

    // Gibt zurück, ob der Rechner in einem Fehlerzustand ist.
    public boolean istFehlerZustand() {
        return fehlerZustand;
    }

    // Verarbeitung der Eingabe von Ziffern (0–9).
    public void zifferEingeben(int ziffer) {
        if (fehlerZustand || ziffer < 0 || ziffer > 9) return; // Abbruch bei Fehler oder ungültiger Eingabe.

        if (neueEingabe) {
            // Startet die Eingabe einer neuen Zahl.
            aktuellerWert = BigDecimal.valueOf(ziffer);
            neueEingabe = false;
        } else if (kommaGesetzt) {
            // Fügt Ziffern nach dem Dezimalpunkt hinzu.
            int dezimalstellenAnzahl = aktuellerWert.scale();
            if (dezimalstellenAnzahl < DEZIMALSTELLEN) {
                aktuellerWert = aktuellerWert.add(
                        BigDecimal.valueOf(ziffer).movePointLeft(dezimalstellenAnzahl + 1)
                );
            }
        } else {
            // Fügt Ziffern zur bestehenden Zahl hinzu.
            aktuellerWert = aktuellerWert.multiply(BigDecimal.TEN).add(BigDecimal.valueOf(ziffer));
        }
    }

    // Negiert den aktuellen Wert (Vorzeichen wechseln).
    public void vorzeichenWechseln() {
        if (fehlerZustand) return; // Abbruch bei Fehler.
        aktuellerWert = aktuellerWert.negate();
    }

    // Aktiviert die Eingabe eines Dezimalpunkts.
    public void kommaEingeben() {
        if (fehlerZustand) return;
        if (!kommaGesetzt) {
            kommaGesetzt = true;
            if (neueEingabe) {
                aktuellerWert = BigDecimal.ZERO;
                neueEingabe = false;
            }
        }
    }

    public void klammerAuf() {
        if (fehlerZustand) return;

        // Speichere aktuellen Zustand auf Stack
        werteStack.push(aktuellerWert);
        operatorStack.push(operator);

        // Neue KlammerEbene beginnen
        klammerEbene++;
        aktuellerWert = BigDecimal.ZERO;
        operator = "";
        neueEingabe = true;
        kommaGesetzt = false;
    }

    public void klammerZu() {
        if (fehlerZustand) return;

        if (klammerEbene <= 0) {
            return;
        }

        // Berechne aktuellen Ausdruck in der Klammer
        if (!operator.isEmpty() && !neueEingabe) {
            berechnen();
        }

        // Hole vorherigen Zustand aus Stack
        BigDecimal klammerErgebnis = aktuellerWert;
        aktuellerWert = werteStack.pop();
        operator = operatorStack.pop();
        klammerEbene--;

        // Verwende KlammerErgebnis für weitere Berechnungen
        if (!operator.isEmpty()) {
            vorherigerWert = aktuellerWert;
            aktuellerWert = klammerErgebnis;
            berechnen();
        } else {
            aktuellerWert = klammerErgebnis;
        }

        neueEingabe = true;
        kommaGesetzt = false;
    }


    // Für später behalten(!)
    public int getKlammerEbene() {
        return klammerEbene;
    }

    // Verarbeitung der Eingabe eines Operators.
    public void operatorEingeben(String op) { // String statt char
        if (fehlerZustand) return;

        if (!neueEingabe) {
            // Führt die Berechnung mit dem vorherigen Operator durch.
            berechnen();
        }

        // Speichert den Operator und den vorherigen Wert.
        operator = op; // Speichert den Operator als String
        vorherigerWert = aktuellerWert;
        neueEingabe = true;
        kommaGesetzt = false;
        letzterOperand = BigDecimal.ZERO;
    }

    // Führt die Berechnung basierend auf dem aktuellen Operator aus.
    public void berechnen() {
        if (fehlerZustand) return;

        switch (operator) { // Vergleicht jetzt Strings
            case "+":
                aktuellerWert = vorherigerWert.add(aktuellerWert);
                break;
            case "-":
                aktuellerWert = vorherigerWert.subtract(aktuellerWert);
                break;
            case "*":
                aktuellerWert = vorherigerWert.multiply(aktuellerWert);
                break;
            case "/":
                if (aktuellerWert.compareTo(BigDecimal.ZERO) == 0) {
                    fehlerBehandlung("Fehler! Division durch Null!");
                    return;
                }
                aktuellerWert = vorherigerWert.divide(aktuellerWert, DEZIMALSTELLEN, RoundingMode.HALF_UP).stripTrailingZeros();
                break;
            case "^":
                if (vorherigerWert.compareTo(BigDecimal.ZERO) == 0 && aktuellerWert.compareTo(BigDecimal.ZERO) == 0) {
                    fehlerBehandlung("Fehler! Potenzwerte nicht definiert!");
                    return;
                }
                aktuellerWert = BigDecimal.valueOf(Math.pow(vorherigerWert.doubleValue(), aktuellerWert.doubleValue()));
                break;
            case "%":
                if (aktuellerWert.compareTo(BigDecimal.ZERO) == 0) {
                    fehlerBehandlung("Fehler! Modulo durch Null!");
                    return;
                }
                aktuellerWert = vorherigerWert.remainder(aktuellerWert);
                break;
        }

        vorherigerWert = aktuellerWert;
    }

    // Berechnung der Quadratwurzel.
    public void wurzel() {
        // if (fehlerZustand) return;
        if (aktuellerWert.compareTo(BigDecimal.ZERO) < 0) {
            fehlerBehandlung("Fehler! Negative Wurzel!");
            return;
        }
        aktuellerWert = BigDecimal.valueOf(Math.sqrt(aktuellerWert.doubleValue()));
        // neueEingabe = true;
    }

    // Trigonometrische Funktionen.

    // Berechnung der Sinusfunktion.
    public void sinus() {
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        if (winkelModus) {
            wert = Math.toRadians(wert);
        }
        double result = Math.sin(wert);
        aktuellerWert = BigDecimal.valueOf(result).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        neueEingabe = true;
    }

    // Berechnung der Cosinusfunktion.
    public void cosinus(){
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        if (winkelModus) {
            wert = Math.toRadians(wert);
        }
        double result = Math.cos(wert);
        aktuellerWert = BigDecimal.valueOf(result).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        neueEingabe = true;
    }

    // Berechnung der Tangensfunktion.
    public void tangens(){
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        if (winkelModus) {
            wert = Math.toRadians(wert);
        }
        double result = Math.tan(wert);
        if (Double.isInfinite(result)) {
            fehlerBehandlung("Fehler! Tangens undefiniert!");
            return;}
        aktuellerWert = BigDecimal.valueOf(result).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        neueEingabe = true;
    }


    // Inverse Trigonometrische Funktionen.

    // Berechnung der Arcsinusfunktion.
    public void arcSinus(){
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        if (wert > 1 || wert < -1) {
            fehlerBehandlung("Fehler! Arcsinus undefiniert!");
            return;
        }
        double result = Math.asin(wert);
        if (winkelModus) {
            result = Math.toDegrees(result);
        }
        aktuellerWert = BigDecimal.valueOf(result).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        neueEingabe = true;
    }

    // Berechnung der Arccosinusfunktion.
    public void arcCosinus(){
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        if (wert > 1 || wert < -1) {
            fehlerBehandlung("Fehler! Arccosinus undefiniert!");
            return;
        }
        double result = Math.acos(wert);
        if (winkelModus) {
            result = Math.toRadians(result);
        }
        aktuellerWert = BigDecimal.valueOf(result).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        neueEingabe = true;
    }

    // Berechnung der Arctangensfunktion.
    public void arcTangens(){
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        double result = Math.atan(wert);
        if (winkelModus) {
            wert = Math.toRadians(result);
        }
        aktuellerWert = BigDecimal.valueOf(result).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        neueEingabe = true;
    }

    // Berechnung der Logarithmusfunktion.
    public void logarithmus10(){
        if (fehlerZustand) return;
        if (aktuellerWert.compareTo(BigDecimal.ZERO) <= 0) {
            fehlerBehandlung("Fehler! Logarithmus undefiniert!");
            return;
        }
        double wert = aktuellerWert.doubleValue();
        double result = Math.log10(wert);
        aktuellerWert = BigDecimal.valueOf(result);
        neueEingabe = true;
    }

    public void logarithmusNatural(){
        if (fehlerZustand) return;
        if (aktuellerWert.compareTo(BigDecimal.ZERO) <= 0) {
            fehlerBehandlung("Fehler! Logarithmus undefiniert!");
            return;
        }
        double wert = aktuellerWert.doubleValue();
        double result = Math.log(wert);
        aktuellerWert = BigDecimal.valueOf(result);
        neueEingabe = true;
    }

    // Exponentialfunktionen
    public void exponentialE(){
        if (fehlerZustand) return;
        try {
            double wert = aktuellerWert.doubleValue();
            double result = Math.exp(wert);
            aktuellerWert = BigDecimal.valueOf(result);
            neueEingabe = true;
        } catch (ArithmeticException e) {
            fehlerBehandlung("Fehler! Exponential undefiniert!");
        }
    }

    public void exponential10(){
        if (fehlerZustand) return;
        try {
            double wert = aktuellerWert.doubleValue();
            double result = Math.pow(10, wert);
            aktuellerWert = BigDecimal.valueOf(result);
            neueEingabe = true;
        } catch (ArithmeticException e) {
            fehlerBehandlung("Fehler! Exponential undefiniert!");
        }
    }

    // Kehrwert
    public void kehrwert(){
        if (fehlerZustand) return;
        if (aktuellerWert.compareTo(BigDecimal.ZERO) == 0) {
            fehlerBehandlung("Fehler! Kehrwert undefiniert!");
            return;
        }
        double wert = aktuellerWert.doubleValue();
        double result = 1 / wert;
        aktuellerWert = BigDecimal.valueOf(result);
        neueEingabe = true;
    }

    // Quadrat
    public void quadrat(){
        if (fehlerZustand) return;
        double wert = aktuellerWert.doubleValue();
        double result = Math.pow(wert, 2);
        aktuellerWert = BigDecimal.valueOf(result);
        neueEingabe = true;
    }

    // Fakultäten
    public void faktorial(){
        if (fehlerZustand) return;
        int n = aktuellerWert.intValue();
        if (n < 0 || aktuellerWert.compareTo(BigDecimal.valueOf(n)) != 0) {
            fehlerBehandlung("Fehler! Faktorial undefiniert!");
            return;
        }

        if (n > 20) {
            fehlerBehandlung("Fehler! Zahl zu groß für Fakultät!");
            return;
        }
        long faktorial = 1;
        for (int i = 1; i <= n; i++) {
            faktorial *= i;
        }
        aktuellerWert = BigDecimal.valueOf(faktorial);
        neueEingabe = true;
    }

    // Absolutwert
    public void absoluterWert(){
        if (fehlerZustand) return;
        aktuellerWert = aktuellerWert.abs();
        neueEingabe = true;
    }

    // Pi
    public void setPi(){
        if (fehlerZustand) return;
        aktuellerWert = PI;
        neueEingabe = true;
    }

    // Eulerische Zahl
    public void setE(){
        if (fehlerZustand) return;
        aktuellerWert = E;
        neueEingabe = true;
    }

    // Winkelmodus umschalten
    public void setWinkelModusUmschalten(){
        winkelModus = !winkelModus;
    }

    public boolean istGradModus(){
        return winkelModus;
    }

    public String getWinkelModus(){
        return winkelModus ? "DEG" : "RAD";
    }

    // Ans-Funktion
    public void antwortAbrufen(){
        if (fehlerZustand) return;
        aktuellerWert = letzteAntwort;
        neueEingabe = true;
    }

    /*
     * Speicherfunktionen
     */

    // Speichert den aktuellen Wert in den Speicher.
    public void memorySpeichern() {
        if (fehlerZustand) return;
        memory = aktuellerWert;
    }

    // Ruft den gespeicherten Wert ab.
    public void memoryAbrufen() {
        if (fehlerZustand) return;
        aktuellerWert = memory;
        neueEingabe = true;
    }

    public String getMemoryStatus() {
        return memory.compareTo(BigDecimal.ZERO) != 0 ? "M" : "";
    }

    public void memoryClearAll() {
        memory = BigDecimal.ZERO;
    }

    // Beendet eine Berechnung und wendet den Operator erneut an.
    public void gleichzeichen() {
        if (fehlerZustand || operator.isEmpty()) return; // Prüft auf leeren String

        if (letzterOperand.compareTo(BigDecimal.ZERO) == 0 && !neueEingabe) {
            letzterOperand = aktuellerWert;
        }

        aktuellerWert = letzterOperand;
        berechnen();
        vorherigerWert = aktuellerWert;

        letzteAntwort = aktuellerWert;
        vorherigerWert = aktuellerWert;

        neueEingabe = true;
        kommaGesetzt = false;
    }

    // Setzt den Taschenrechner komplett zurück.
    public void allClear() {
        aktuellerWert = BigDecimal.ZERO;
        vorherigerWert = BigDecimal.ZERO;
        operator = "";  // Leerer String
        neueEingabe = true;
        kommaGesetzt = false;
        fehlerZustand = false;
        letzterOperand = BigDecimal.ZERO;

        // Klammer-Stacks leeren
        werteStack.clear();
        operatorStack.clear();
        klammerEbene = 0;
    }

    // Löscht nur die aktuelle Eingabe.
    public void clearEntry() {
        aktuellerWert = BigDecimal.ZERO;
        neueEingabe = true;
        kommaGesetzt = false;
    }

    // Gibt den aktuellen Wert oder die Fehlermeldung zurück.
    public String getAktuellerWert() {
        if (fehlerZustand) {
            return aktuellerFehler; // Gibt die Fehlermeldung zurück, falls ein Fehler vorliegt.
        }

        String wert = aktuellerWert.toPlainString(); // Holt den aktuellen Wert als String.

        // Fall 1: Der Benutzer hat einen Dezimalpunkt aktiv eingegeben.
        if (kommaGesetzt) {
            if (!wert.contains(".")) {
                wert += "."; // Fügt den Dezimalpunkt hinzu, falls er fehlt.
            }
            return wert; // Gibt den Wert zurück, einschließlich des Dezimalpunkts.
        }

        // Fall 2: Reduziere `.0`, wenn keine Nachkommastellen erforderlich sind (automatische Ergebnisse).
        if (aktuellerWert.stripTrailingZeros().scale() <= 0) {
            return aktuellerWert.stripTrailingZeros().toPlainString();
        }

        return wert; // Gibt den Wert mit Dezimalstellen zurück, falls notwendig.
    }
}