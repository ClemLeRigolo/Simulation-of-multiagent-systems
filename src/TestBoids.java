import java.awt.Color;

import gui.GUISimulator;

public class TestBoids {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        BoidsEngine game = new BoidsEngine(gui, 30, 600, 2);
    }
}
