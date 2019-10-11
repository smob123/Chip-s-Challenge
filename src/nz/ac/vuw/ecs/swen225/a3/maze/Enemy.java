package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The enemy in the game.
 * 
 * @author Dominic Tjiptono
 */
public class Enemy implements GameObject {
  // Initialising private attributes of Enemy class
  private Location location;

  // Creating constructor of Enemy class

  /**
   * constructor.
   * 
   * @param location the coordinates of the enemy
   */
  public Enemy(Location location) {
    setLocation(location);
  }

  // Creating getters and setters of Enemy class

  /**
   * Getting the current location of the enemy.
   * 
   * @return current location of the enemy
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Setting the location of the enemy.
   * 
   * @param location new coordinates for enemy to be placed at
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  // Creating other methods of Enemy class

  /**
   * Moving up one tile.
   */
  public void moveUp() {
    location = new Location(location.getX(), location.getY() - 1);
  }

  /**
   * Moving down one tile.
   */
  public void moveDown() {
    location = new Location(location.getX(), location.getY() + 1);
  }

  /**
   * Moving left one tile.
   */
  public void moveLeft() {
    location = new Location(location.getX() - 1, location.getY());
  }

  /**
   * Moving right one tile.
   */
  public void moveRight() {
    location = new Location(location.getX() + 1, location.getY());
  }

  /**
   * Checking whether the enemy collides the main character or not.
   * 
   * @param chap the main character
   * @return whether the enemy collides the main character or not
   */
  public boolean collidesPlayer(Chap chap) {
    return chap.getLocation().equals(location);
  }

  @Override
  public char getMazeChar() {
    return 'F';
  }
}
