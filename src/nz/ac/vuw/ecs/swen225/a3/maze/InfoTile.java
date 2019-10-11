package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A tile that gives the player a hint on what to do so they can pass the
 * current level.
 * 
 * @author Sultan 300489686
 *
 */
public class InfoTile extends Tile {
  private String message;

  /**
   * constructor.
   * 
   * @param location the tile's location on the maze
   * @param message  the info message that the tile displays
   * 
   */
  public InfoTile(Location location, String message) {
    super(location);
    this.message = message;
  }

  /**
   * returns the hint message.
   * 
   * @return get the hint message
   */
  public String getMessage() {
    return message;
  }

  /**
   * returns the the tile's character on the maze.
   * 
   * @return the tile's character on the maze
   */
  @Override
  public char getMazeChar() {
    return '?';
  }
}
