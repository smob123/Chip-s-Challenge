package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * Treasures to be collected by the player.
 * @author Sultan 300489686
 *
 */
public class Treasure extends Collectable {
  
  /**
   * constructor.
   * @param location the treasure's location on the maze.
   */
  public Treasure(Location location) {
    super(location);
  }

  /**
   * returns the the treasure's character on the maze.
   * 
   * @return the treasure's character on the maze
   */
  public char getMazeChar() {
    return 'T';
  }
}
