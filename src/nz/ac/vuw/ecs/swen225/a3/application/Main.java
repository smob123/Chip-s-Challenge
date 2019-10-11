package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * main class.
 * @author Sultan 300489686
 *
 */
public class Main {
  /**
   * main method.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      Game game = new Game();
      game.setVisible(true);
      game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    });
  }
}
