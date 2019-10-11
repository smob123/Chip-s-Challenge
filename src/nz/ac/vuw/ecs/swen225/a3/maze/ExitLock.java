package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A lock to the maze's exit that can only be unlocked if the player has 
 * collected all the treasures.
 * @author Sultan 300489686
 *
 */
public class ExitLock extends Tile {
  
  /**
   * constructor.
   * @param location the tile's location on the maze
   */
  public ExitLock(Location location) {
    super(location);
  }
  
  @Override
  public char getMazeChar() {
    return 'L';
  }
}
