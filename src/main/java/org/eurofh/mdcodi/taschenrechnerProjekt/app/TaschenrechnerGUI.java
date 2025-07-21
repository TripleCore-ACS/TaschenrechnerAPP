package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaschenrechnerGUI {
    private JFrame frame;
    private JTextField display; // Zeigt den aktuellen Wert oder Fehlermeldungen an.
    private JLabel statusLabel; // Zeigt den Winkelmodus u. Speicherstatus an.
    private JPanel tastenfeld; // Enthält alle Tasten des Taschenrechners.
    private final TaschenrechnerLogik logik; // Verbindet die GUI mit der Logik.

    public TaschenrechnerGUI(TaschenrechnerLogik logik) {
        this.logik = logik; // Verbindet die GUI mit der Logik.
        initialisieren(); // Initialisiert die GUI-Komponenten.
    }

    // Initialisiert die Hauptkomponenten der GUI.
    private void initialisieren() {
        frame = new JFrame("TaschenrechnerProjekt");
        frame.setSize(500, 600); // Setzt die Fenstergröße.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Zentriert das Fenster.
        frame.setLayout(new BorderLayout());

        // Oberer Berich: Display + Status
        JPanel obererBereich = new JPanel(new BorderLayout());

        // Erstellt das Anzeigefeld für Zahlen und Ergebnisse.
        display = new JTextField();
        display.setEditable(false); // Benutzer kann das Feld nicht bearbeiten.
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(500, 50));
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(display, BorderLayout.NORTH);

        //Status-Label (Winkelmodus und Speicherstatus)
        statusLabel = new JLabel("DEG");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        obererBereich.add(statusLabel, BorderLayout.SOUTH);

        frame.add(obererBereich, BorderLayout.CENTER);

        // Erstellt das Tastenfeld mit 6 Zeilen und 4 Spalten.
        tastenfeld = new JPanel();
        tastenfeld.setLayout(new GridLayout(9, 5, 3, 3));

        // Alle Tasten, die der Taschenrechner unterstützt.
        String[] tasten = {
                "AC", "CE", "Ans", "DEG", "π",
                "sin", "cos", "tan", "log", "ln",
                "asin", "acos", "atan", "10^x", "e^x",
                "x^2", "2√", "x!", "1/x", "|x|",
                "M+", "M", "MC", "(", ")",
                "7", "8", "9", "/", "%",
                "4", "5", "6", "*", "^",
                "1", "2", "3", "-", "e",
                "0", ",", "+/-", "+", "="
        };

        // Fügt die Tasten mit den entsprechenden Funktionen hinzu.
        for (String taste : tasten) {
            JButton button = new JButton(taste);
            button.setFont(new Font("Arial", Font.PLAIN, 14));

            // Farbkodierung für verschiedene Funktionstypen.
            if (isWissenschaftlicheFunktion(taste)) {
                button. setBackground(new Color(173, 216, 230)); // Hellblau
            } else if (isOperator(taste)) {
                button. setBackground(new Color(255, 165, 0)); // Orange
            } else if (isZahl(taste)) {
                button.setBackground(Color.WHITE); // Weiß
            } else {
                button.setBackground(new Color(211, 211, 211)); // HellGgau
            }

            button.addActionListener(new TastenHandler(taste)); // Verbindet die Tasten mit der Logik.
            tastenfeld.add(button);
        }

        // Fügt das Tastenfeld zum Hauptfenster hinzu.
        frame.add(tastenfeld, BorderLayout.CENTER);
        updateStatusLable();
    }

    private boolean isWissenschaftlicheFunktion(String taste) {
        return taste.matches("sin|cos|tan|asin|acos|atan|log|ln|10\\^x|e\\^x|x\\^2|x!|1/x|\\|x\\||π|e");
    }

    private boolean isOperator(String taste) {
        return taste.matches("[+\\-*/^%=]");
    }

    private boolean isZahl(String taste) {
        return taste.matches("[0-9]");
    }

    private void updateStatusLable() {
        String status = logik.getWinkelModus();
        String memoryStatus = logik.getMemoryStatus();
        if (!memoryStatus.isEmpty()) {
            status += " " + memoryStatus;
        }
        statusLabel.setText(status);
    }

    // Interne Klasse zur Verarbeitung der Tasteneingaben.
    private class TastenHandler implements ActionListener {
        private final String taste;

        public TastenHandler(String taste) {
            this.taste = taste; // Speichert die Taste, die gedrückt wurde.
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Verarbeitet die Eingabe basierend auf der gedrückten Taste.
            switch (taste) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    logik.zifferEingeben(Integer.parseInt(taste)); // Zahleneingabe.
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                case "%":
                    logik.operatorEingeben(taste); // String-Operatoren.
                    break;
                case ",":
                    logik.kommaEingeben(); // Dezimalpunkt.
                    break;
                case "+/-":
                    logik.vorzeichenWechseln(); // Vorzeichen wechseln.
                    break;
                case "2√":
                    logik.wurzel(); // Quadratwurzel.
                    break;
                case "=":
                    logik.gleichzeichen(); // Berechnung durchführen.
                    break;
                case "AC":
                    logik.allClear(); // Alles zurücksetzen.
                    break;
                case "CE":
                    logik.clearEntry(); // Nur aktuelle Eingabe löschen.
                    break;
                case "M+":
                    logik.memorySpeichern(); // Speichern.
                    break;
                case "M":
                    logik.memoryAbrufen(); // Abrufen.
                    break;
                case "MC":
                    logik.memoryClearAll(); // Alle Speicherwerte löschen.
                    break;
                case "sin":
                    logik.sinus();
                    break;
                case "cos":
                    logik.cosinus();
                    break;
                case "tan":
                    logik.tangens();
                    break;
                case "asin":
                    logik.arcSinus();
                    break;
                case "acos":
                    logik.arcCosinus();
                    break;
                case "atan":
                    logik.arcTangens();
                    break;
                case "log":
                    logik.logarithmus10();
                    break;
                case "ln":
                    logik.logarithmusNatural();
                case "(":
                    logik.klammerAuf();
                case ")":
                    logik.klammerZu(); // Klammern.
                    break;
                case "10^x":
                    logik.exponential10();
                    break;
                case "e^x":
                    logik.exponentialE();
                    break;
                case "x^2":
                    logik.quadrat();
                    break;
                case "x!":
                    logik.faktorial();
                    break;
                case "1/x":
                    logik.kehrwert();
                    break;
                case "|x|":
                    logik.absoluterWert();
                    break;

                // Konstanten
                case "π":
                    logik.setPi();
                    break;
                case "e":
                    logik.setE();
                    break;

                // Spezielle Funktionen
                case "DEG":
                    logik.setWinkelModusUmschalten();
                    break;
                case "Ans":
                    logik.antwortAbrufen();
                    break;
            }

            // Aktualisiert das Anzeigefeld mit dem aktuellen Wert.
            String anzeigeWert = logik.getAktuellerWert();
            display.setText(anzeigeWert);
            updateStatusLable();

            if (logik.istFehlerZustand()) {
                display.setForeground(Color.RED); // Zeigt Fehler in Rot.
            } else {
                display.setForeground(Color.BLACK); // Normale Anzeige in Schwarz.
            }
        }
    }

    // Getter-Methode für das Display in TaschenrechnerGUITest (Mockito/Gradle)
    public JTextField getDisplay() {
        return display;
    }

    public JPanel getTastenfeld() {
        return tastenfeld;
    }

    public JFrame getFrame() {
        return frame;
    }

    // Getter-Methode für die Tasten-Funktion in TaschenrechnerIntegrationsTest (Mockito/Gradle)
    public JButton findButton(String taste) {
        /* Schleifenbeginn zum Abfragen der einzelnen Tasten-Komponente
        im GUI-Framework*/
        for (Component c : tastenfeld.getComponents()) {
            /* Überprüft, ob das Komponentenobjekt c eine Instanz von JButton ist
            und ob die Eigenschaft 'taste' mit den gegebenen Tastenfeldern übereinstimmt */
            if (c instanceof JButton && ((JButton) c).getText().equals(taste)) {
                return (JButton) c;
            }
        }
        return null; // Falls kein Button mit gefunden wird
    }

    // Startet die grafische Oberfläche.
    public void starten() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}