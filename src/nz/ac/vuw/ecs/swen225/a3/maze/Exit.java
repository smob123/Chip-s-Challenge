package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The maze's exit.
 * 
 * @author Sultan 300489686
 *
 */
public class Exit extends Tile {
  /**
   * constructor.
   * 
   * @param location the tile's location on the maze
   */
  public Exit(Location location) {
    super(location);
  }

  @Override
  public char getMazeChar() {
    return 'E';
  }
}
