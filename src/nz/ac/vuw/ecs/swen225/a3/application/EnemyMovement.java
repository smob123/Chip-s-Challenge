package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.Location;

/**
 * handles enemy movement around the maze.
 * 
 * @author Sultan 300489686
 *
 */
public class EnemyMovement {
  private Enemy enemy;
  private Location dest;

  /**
   * constructor.
   * 
   * @param enemy the enemy to move
   */
  public EnemyMovement(Enemy enemy) {
    this.enemy = enemy;
  }

  /**
   * Set the destination of the movement.
   * 
   * @param d the new destination
   */
  public void setDest(Location d) {
    dest = d;
  }

  /**
   * moves the enemy towards the destination.
   */
  public void move() {
    // get the current location of the enemy
    Location currentLocation = enemy.getLocation();

    if (!currentLocation.equals(dest)) {
      if (currentLocation.getX() > dest.getX()) {
        enemy.moveLeft();
      } else if (currentLocation.getX() < dest.getX()) {
        enemy.moveRight();
      } else if (currentLocation.getY() > dest.getY()) {
        enemy.moveUp();
      } else if (currentLocation.getY() < dest.getY()) {
        enemy.moveDown();
      }
    }
  }
  
  /**
   * Get the destination of the movement.
   * @return the destination of the movement
   */
  public Location getDestination() {
    return dest;
  }
  
  /**
   * Get the current location of the enemy.
   * @return current location of the enemy
   */
  public Location getEnemyLocation() {
    return enemy.getLocation();
  }
  
  /**
   * Get the enemy linked to this movement.
   * @return the enemy linked to this movement
   */
  public Enemy getEnemy() {
    return enemy;
  }

  /**
   * Get whether the the destination was reached or not.
   * 
   * @return whether the the destination was reached or not
   */
  public boolean destinationReached() {
    return enemy.getLocation().equals(dest);
  }
}
