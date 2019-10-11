package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A general interface that defines what each object in the game should have.
 * 
 * @author Sultan 300489686
 *
 */
public interface GameObject {
  
  /**
   * Get the tile's location on the maze.
   * 
   * @return the tile's coordinates
   */
  public Location getLocation();
  
  /**
   * returns the the tile's character on the maze.
   * 
   * @return the tile's character on the maze
   */
  public char getMazeChar();
}
