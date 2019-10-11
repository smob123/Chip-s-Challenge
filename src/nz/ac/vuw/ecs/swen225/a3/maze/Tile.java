package nz.ac.vuw.ecs.swen225.a3.maze;


/**
 * An abstract class for different types of tiles.
 * 
 * @author Sultan 300489686
 *
 */
public abstract class Tile implements GameObject {
  private Location location;

  /**
   * constructor.
   * 
   * @param location the tile's coordinates on the maze
   */
  public Tile(Location location) {
    this.location = location;
  }

  @Override
  public Location getLocation() {
    return location;
  }

}
