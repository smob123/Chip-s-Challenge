package nz.ac.vuw.ecs.swen225.a3.maze;


/**
 * Class for collectable key in game.
 *
 * @author Kyle Mans
 *
 */
public class Key extends Collectable {

  private Colour keyColour;

  /**
   * Color enumerator.
   * @author kylem
   *
   */
  public enum Colour {
    /**
     * Blue key.
     */
    BLUE,
    /**
     * Red key.
     */
    RED,
    /**
     * Green key.
     */
    GREEN,
    /**
     * Yellow key.
     */
    YELLOW
  }

  /**
   * Key constructor.
   *
   * @param location Location of key
   * @param keyColour Color of key
   */
  public Key(Location location, Colour keyColour) {
    super(location); // passes location to super constructor in Collectable
    this.keyColour = keyColour; // key color field
  }

  /**
   * Gets key color.
   *
   * @return keyColour
   */
  public Colour getKeyColour() {
    return keyColour;
  }

  /**
   * Returns string depending on color.
   *
   * @return string
   */
  @Override
  public String toString() {
    String str = "The colour of the key is ";

    // adds the color of the key to the end of the string
    if (getKeyColour().equals(Colour.BLUE)) {
      str += "Blue.";

    } else if (getKeyColour().equals(Colour.RED)) {
      str += "Red.";

    } else if (getKeyColour().equals(Colour.GREEN)) {
      str += "Green.";

    } else {
      str += "Yellow.";
    }
    return str;
  }


  @Override
  public char getMazeChar() {
    if (getKeyColour().equals(Colour.BLUE)) {
      return 'Z';

    } else if (getKeyColour().equals(Colour.RED)) {
      return 'X';

    } else if (getKeyColour().equals(Colour.GREEN)) {
      return 'C';

    } else {
      return 'V';
    }
  }
}
