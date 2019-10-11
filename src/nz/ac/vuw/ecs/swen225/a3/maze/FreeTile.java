package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * An empty tile that the player can move to.
 * 
 * @author Sultan 300489686
 *
 */
public class FreeTile extends Tile {

  /**
   * constructor.
   * 
   * @param location the tile's location on the maze
   */
  public FreeTile(Location location) {
    super(location);
  }

  @Override
  public char getMazeChar() {
    return '_';
  }
}
