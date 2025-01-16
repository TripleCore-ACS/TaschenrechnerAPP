package org.eurofh.mdcodi.taschenrechnerProjekt.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaschenrechnerGUI {
    private JFrame frame;
    private JTextField display; // Zeigt den aktuellen Wert oder Fehlermeldungen an.
    private JPanel tastenfeld; // Enthält alle Tasten des Taschenrechners.
    private final TaschenrechnerLogik logik; // Verbindet die GUI mit der Logik.

    public TaschenrechnerGUI(TaschenrechnerLogik logik) {
        this.logik = logik; // Verbindet die GUI mit der Logik.
        initialisieren(); // Initialisiert die GUI-Komponenten.
    }

    // Initialisiert die Hauptkomponenten der GUI.
    private void initialisieren() {
        frame = new JFrame("TaschenrechnerProjekt");
        frame.setSize(400, 500); // Setzt die Fenstergröße.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Zentriert das Fenster.
        frame.setLayout(new BorderLayout());

        // Erstellt das Anzeigefeld für Zahlen und Ergebnisse.
        display = new JTextField();
        display.setEditable(false); // Benutzer kann das Feld nicht bearbeiten.
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(400, 50));
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(display, BorderLayout.NORTH);

        // Erstellt das Tastenfeld mit 6 Zeilen und 4 Spalten.
        tastenfeld = new JPanel();
        tastenfeld.setLayout(new GridLayout(6, 4, 5, 5));

        // Alle Tasten, die der Taschenrechner unterstützt.
        String[] tasten = {
                "AC", "CE", "%", "+/-",
                "2√", "^", "M+", "M",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ",", "=", "+"
        };

        // Fügt die Tasten mit den entsprechenden Funktionen hinzu.
        for (String taste : tasten) {
            JButton button = new JButton(taste);
            button.setFont(new Font("Arial", Font.PLAIN, 18)); // Setzt die Schriftart.
            button.addActionListener(new TastenHandler(taste)); // Verbindet die Tasten mit der Logik.
            tastenfeld.add(button);
        }

        // Fügt das Tastenfeld zum Hauptfenster hinzu.
        frame.add(tastenfeld, BorderLayout.CENTER);
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
            }

            // Aktualisiert das Anzeigefeld mit dem aktuellen Wert.
            String anzeigeWert = logik.getAktuellerWert();
            display.setText(anzeigeWert);

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