# TaschenrechnerAPP
TaschenrechnerApp in Java

# Benutzerhandbuch

## Bedienoberfläche
Die grafische Benutzeroberfläche (GUI) des Taschenrechners besteht aus einem Textfeld oberhalb der Oberfläche, das als Anzeige dient, sowie einem Tastenfeld mit neun Zeilen und fünf Spalten darunter. Die Schaltflächen sind in logische Gruppen aufgeteilt und bieten Ihnen Zugriff auf grundlegende wie auch erweiterte wissenschaftliche Rechenoperationen. Das Programm unterstützt die Darstellung in einem klaren und minimalistischen Layout mit Farbkodierung für verschiedene Funktionstypen, angepasst für Anwenderfreundlichkeit.

**Statusleiste:** Oberhalb des Tastenfeldes zeigt eine Statusleiste den aktuellen Winkelmodus (DEG/RAD) und den Speicherstatus (M) an.

## Anwendung der Taschenrechner-Funktionen

### Eingabe von Zahlen
Zur Eingabe von Zahlen können Sie die Tasten „0–9" verwenden. Diese Zifferntasten ermöglichen die Eingabe beliebiger Zahlen. Die Anzeige aktualisiert sich entsprechend mit der aktuell eingegebenen Zahl.

### Eingabe eines Dezimalpunkts
Für Dezimalzahlen drücken Sie die Komma-Taste „,". Dabei wechselt der Taschenrechner automatisch in den Dezimal-Modus, sodass eine präzise Eingabe von Zahlen mit Dezimalstellen möglich ist.

### Grundlegende Rechenoperationen
Die Rechenoperationen „Addition (+)", „Subtraktion (-)", „Multiplikation (*)" und „Division (/)" können über die entsprechenden Symboltasten angewendet werden. Nach der Eingabe eines Operators wird die nächste Zahl eingegeben. Drücken Sie die Taste „=", um die Berechnung durchzuführen und das Ergebnis anzuzeigen.

### Vorzeichenwechsel
Mit der Taste „+/-" können Sie das Vorzeichen einer Zahl ändern. Dies ist nützlich für Berechnungen mit negativen Zahlen.

### Quadratwurzel
Die Taste „2√" ermöglicht die Berechnung der Quadratwurzel einer Zahl. Negative Zahlen führen zu einem Fehler, da die Quadratwurzel von negativen Zahlen in diesem Taschenrechner nicht definiert ist.

### Potenzberechnung
Die Taste „^" berechnet Potenzen. Beispiel: Geben Sie eine Basis ein, wählen Sie „^", geben Sie dann den Exponenten ein, und drücken Sie „=", um das Ergebnis zu erhalten.

### Modulo-Rechnung
Mit der Taste „%" können Sie den Rest einer Division (Modulo) berechnen. Bemerkung: Die Division durch Null ist nicht erlaubt und verursacht einen Fehler.

## Erweiterte wissenschaftliche Funktionen

### Trigonometrische Funktionen
- **sin:** Berechnet den Sinus des aktuellen Wertes
- **cos:** Berechnet den Cosinus des aktuellen Wertes  
- **tan:** Berechnet den Tangens des aktuellen Wertes
- **Hinweis:** Die Berechnung erfolgt je nach Winkelmodus in Grad (DEG) oder Radiant (RAD)

### Inverse trigonometrische Funktionen
- **asin:** Berechnet den Arcussinus (sin⁻¹) des aktuellen Wertes
- **acos:** Berechnet den Arcuscosinus (cos⁻¹) des aktuellen Wertes
- **atan:** Berechnet den Arcustangens (tan⁻¹) des aktuellen Wertes
- **Gültigkeitsbereich:** Für asin und acos muss der Wert zwischen -1 und 1 liegen

### Logarithmusfunktionen
- **log:** Berechnet den Zehnerlogarithmus (log₁₀) des aktuellen Wertes
- **ln:** Berechnet den natürlichen Logarithmus (ln) des aktuellen Wertes
- **Hinweis:** Der Wert muss größer als Null sein

### Exponentialfunktionen
- **10^x:** Berechnet 10 hoch dem aktuellen Wert
- **e^x:** Berechnet e hoch dem aktuellen Wert (Exponentialfunktion)

### Weitere mathematische Funktionen
- **x^2:** Berechnet das Quadrat des aktuellen Wertes
- **x!:** Berechnet die Fakultät des aktuellen Wertes (nur für ganze Zahlen ≤ 20)
- **1/x:** Berechnet den Kehrwert des aktuellen Wertes
- **|x|:** Berechnet den Absolutwert des aktuellen Wertes

### Mathematische Konstanten
- **π:** Fügt die Kreiszahl Pi (≈ 3.14159) ein
- **e:** Fügt die Eulersche Zahl e (≈ 2.71828) ein

### Klammerfunktionen
- **(:** Öffnet eine neue Klammerebene für verschachtelte Berechnungen
- **):** Schließt die aktuelle Klammerebene und berechnet den Ausdruck
- **Beispiel:** (2+3)×4 = 20

### Winkelmodus
- **DEG:** Wechselt zwischen Grad- und Radiantmodus für trigonometrische Funktionen
- **Status:** Der aktuelle Modus wird in der Statusleiste angezeigt (DEG/RAD)

## Speicherfunktionen

### Speichern eines Wertes
Drücken Sie „M+", um den aktuellen Wert im Speicher abzulegen. Dieser gespeicherte Wert bleibt erhalten, bis Sie ihn gezielt überschreiben.

### Abrufen eines gespeicherten Wertes
Mit der Taste „M" können Sie den zuletzt gespeicherten Wert abrufen und ihn zur weiteren Verarbeitung verwenden.

### Speicher löschen
Mit der Taste „MC" (Memory Clear) löschen Sie den gesamten Speicherinhalt.

### Speicherstatus
Wenn ein Wert im Speicher gespeichert ist, erscheint „M" in der Statusleiste.

## Zusätzliche Bedienfunktionen

### Antwort-Funktion
- **Ans:** Ruft das Ergebnis der letzten Berechnung ab, auch nach einem Neustart des Rechners

### Berechnung fortsetzen
Nach einer gerechneten Operation können Sie auf „=" drücken, um dieselbe Operation mit dem zuletzt eingegebenen Wert erneut auszuführen.

### Löschen
- **AC** (All Clear): Setzt den Rechner vollständig zurück, einschließlich aller Werte, Speicherinhalte, Operatoren und Klammerebenen
- **CE** (Clear Entry): Löscht lediglich die aktuelle Eingabe, ohne das restliche Setup des Taschenrechners zu beeinflussen

### Fehlerbehandlung
Falls ungültige Operationen wie Division durch Null, negative Logarithmen oder andere mathematische Einschränkungen auftreten, wechselt der Taschenrechner in einen Fehlerzustand. Der Fehler wird in rot in der Anzeige dargestellt. Sie können den Fehlerzustand mit der Taste „AC" zurücksetzen.

**Häufige Fehler:**
- Division durch Null
- Logarithmus von Null oder negativen Zahlen
- Quadratwurzel negativer Zahlen
- Fakultät von nicht-ganzen oder zu großen Zahlen
- Arcussinus/Arcuscosinus außerhalb des Gültigkeitsbereichs

## Grafische Benutzeroberfläche

Das Bedienfeld des Taschenrechners enthält folgende Schaltflächen mit Farbkodierung:

### Wissenschaftliche Funktionen (Hellblau)
- **Trigonometrie:** sin, cos, tan, asin, acos, atan
- **Logarithmen:** log, ln
- **Exponentialfunktionen:** 10^x, e^x
- **Weitere Funktionen:** x^2, x!, 1/x, |x|
- **Konstanten:** π, e

### Rechenoperationen (Orange)
- **Grundoperationen:** +, -, *, /
- **Erweiterte Operationen:** ^ (Potenz), % (Modulo)
- **Berechnung:** = (Gleichheitszeichen)

### Zahlen (Weiß)
- **Zifferntasten:** 0–9

### Weitere Funktionen (Hellgrau)
- **Speicher:** M+ (Speichern), M (Abrufen), MC (Löschen)
- **Löschen:** AC (alles löschen), CE (aktuelle Eingabe löschen)
- **Sonstige:** +/- (Vorzeichen), , (Komma), (, ), DEG, Ans, 2√

Das Textfeld zeigt Zahlen, Zwischenergebnisse oder Fehlermeldungen an. Fehler werden immer in roter Schrift dargestellt, damit Sie diese leicht erkennen können.

## Starten des Programms

Um den Taschenrechner zu starten, öffnen Sie die Hauptklasse `TaschenrechnerStart`. Das Programm startet ein Fenster mit der GUI. Sollten Fehler beim Start auftreten, wird eine entsprechende Fehlermeldung in der Konsole angezeigt.

## Anwendungsbeispiele

### Einfache Berechnung
- Eingabe: 5 + 3 × 2 = 
- Ergebnis: 11 (Punktrechnung vor Strichrechnung)

### Mit Klammern
- Eingabe: (5 + 3) × 2 =
- Ergebnis: 16

### Trigonometrische Berechnung
- Eingabe: 30 → sin
- Ergebnis: 0.5 (im DEG-Modus)

### Logarithmus
- Eingabe: 100 → log
- Ergebnis: 2

### Potenz
- Eingabe: 2 ^ 3 =
- Ergebnis: 8

Der Taschenrechner ist für einfache sowie komplexe wissenschaftliche Rechenanwendungen bestens geeignet. Diese Bedienungsanleitung hilft Ihnen, alle Funktionen korrekt und effektiv zu nutzen.

## Technische Hinweise

- **Präzision:** Berechnungen werden mit BigDecimal durchgeführt für hohe Genauigkeit
- **Dezimalstellen:** Maximal 10 Dezimalstellen werden angezeigt
- **Java-Version:** Erfordert Java SDK 21 oder höher
- **GUI-Framework:** Implementiert mit Java Swing
