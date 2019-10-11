
package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.Assert.assertTrue;

import nz.ac.vuw.ecs.swen225.a3.maze.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.Location;

import org.junit.jupiter.api.Test;

/**
 * Test out the keys.
 * @author Kyle Mans
 *
 */
public class KeyTests {

  @Test
   void toStringBlue() {
    Key k = new Key(new Location(5,5), Key.Colour.BLUE);
    String keyCol = k.toString();
    assertTrue(keyCol.equals("The colour of the key is Blue."));
  }

  @Test
   void toStringRed() {
    Key k = new Key(new Location(5,5), Key.Colour.RED);
    String keyCol = k.toString();
    assertTrue(keyCol.equals("The colour of the key is Red."));
  }

  @Test
   void toStringGreen() {
    Key k = new Key(new Location(5,5), Key.Colour.GREEN);
    String keyCol = k.toString();
    assertTrue(keyCol.equals("The colour of the key is Green."));
  }

  @Test
   void toStringYellow() {
    Key k = new Key(new Location(5,5), Key.Colour.YELLOW);
    String keyCol = k.toString();
    assertTrue(keyCol.equals("The colour of the key is Yellow."));
  }
}