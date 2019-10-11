package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A door that can only be unlocked with a key that has a certain color.
 * 
 * @author Sultan 300489686
 *
 */
public class Door extends Tile {

  private boolean isLocked = true;
  private Key.Colour keyColor;
  private char canvasChar;

  /**
   * constructor.
   * 
   * @param location the tile's location on the maze
   * @param keyColor the color of the key that can unlock this door
   */
  public Door(Location location, Key.Colour keyColor) {
    super(location);
    this.keyColor = keyColor;
    setupTile();
  }

  private void setupTile() {
    // pick the door's character based on its colour
    if (keyColor != null) {
      switch (keyColor) {
        case BLUE:
          canvasChar = 'B';
          break;
        case GREEN:
          canvasChar = 'G';
          break;
        case YELLOW:
          canvasChar = 'Y';
          break;
        case RED:
          canvasChar = 'R';
          break;
        default:
          break;
      }
    }
  }

  /**
   * Get the color of the key that can unlock this door.
   * 
   * @return the color of the key that can unlock this door
   */
  public Key.Colour getKeyColor() {
    return keyColor;
  }

  /**
   * unlock the door.
   */
  public void unlock() {
    isLocked = false;
  }

  /**
   * check if the door is locked or not.
   * 
   * @return if the door is locked or not
   */
  public boolean isLocked() {
    return isLocked;
  }

  @Override
  public char getMazeChar() {
    return canvasChar;
  }
}
