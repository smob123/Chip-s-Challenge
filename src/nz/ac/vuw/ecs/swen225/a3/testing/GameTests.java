package nz.ac.vuw.ecs.swen225.a3.testing;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.a3.application.Game;

import org.junit.jupiter.api.Test;

/**
 * @author Kyle Mans
 *
 */
public class GameTests {

  @Test
  void makingGame() {
    Game game = new Game();
    game.setVisible(true);
    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    assert(true);
  }
}
