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

    // Speichert den aktuellen Operator und Statusinformationen.
    private String operator = "";  // String statt char
    private boolean neueEingabe = true; // True, wenn der Benutzer eine neue Zahl eingibt.
    private boolean fehlerZustand = false; // True, wenn ein Fehler aufgetreten ist.
    private boolean kommaGesetzt = false; // True, wenn ein Dezimalpunkt eingegeben wurde.
    private static final int DEZIMALSTELLEN = 10; // Maximale Dezimalstellen.
    private String aktuellerFehler = ""; // Beschreibung des aktuellen Fehlers.

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
        if (aktuellerWert.compareTo(BigDecimal.ZERO) < 0) {
            fehlerBehandlung("Fehler! Negative Wurzel!");
            return;
        }
        aktuellerWert = BigDecimal.valueOf(Math.sqrt(aktuellerWert.doubleValue()));
    }

    // Speichert den aktuellen Wert in den Speicher.
    public void memorySpeichern() {
        if (fehlerZustand) return;
        memory = aktuellerWert;
    }

    // Ruft den gespeicherten Wert ab.
    public void memoryAbrufen() {
        if (fehlerZustand) return;
        aktuellerWert = memory;
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
    }

    // Löscht nur die aktuelle Eingabe.
    public void clearEntry() {
        aktuellerWert = BigDecimal.ZERO;
        neueEingabe = true;
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