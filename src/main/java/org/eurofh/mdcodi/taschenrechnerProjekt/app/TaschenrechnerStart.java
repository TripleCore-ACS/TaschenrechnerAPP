package org.eurofh.mdcodi.taschenrechnerProjekt.app;

public class TaschenrechnerStart {
    public static void main(String[] args) {
        try {
            TaschenrechnerLogik logik = new TaschenrechnerLogik();
            TaschenrechnerGUI gui = new TaschenrechnerGUI(logik);
            gui.starten();
        } catch (Exception e) {
            System.out.println("Fehler beim Starten des Taschenrechners: " + e.getMessage());
        }
    }
}