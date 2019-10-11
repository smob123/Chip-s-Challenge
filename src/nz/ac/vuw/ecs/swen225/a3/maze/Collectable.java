package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * Items that can be collected by the player in the game.
 * @author Kyle Mans
 *
 */
public abstract class Collectable implements GameObject {

  private Location location;
  private boolean isCollected = false;

  /**
   * constructor.
   * @param location
   *
   */
  public Collectable(Location location) {
    this.location = location;
  }

  /**
   * obtains location.
   * @return location
   *
   */
  public Location getLocation() {
    return this.location;
  }

  /**
   * check if the treasure has been collected.
   * @return if the treasure has been collected.
   *
   */
  public boolean isCollected() {
    return isCollected;
  }

  /**
   * set the treasure as collected so it can be removed from the maze.
   *
   */
  public void collect() {
    isCollected = true;
  }

}

