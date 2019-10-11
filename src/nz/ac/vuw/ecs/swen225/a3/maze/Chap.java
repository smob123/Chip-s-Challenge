package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;

/**
 * The main character of the game.
 * 
 * @author Dominic Tjiptono
 */
public class Chap implements GameObject {
  // Initialising private attributes of Chap class
  private Location location;
  private Inventory inventory;
  private boolean wonLevel = false;

  /**
   * constructor.
   * 
   * @param location the coordinates of the main character
   */
  public Chap(Location location) {
    setLocation(location);
    inventory = new Inventory();
  }
  
  /**
   * sets the player's level won state to true.
   */
  public void win() {
    wonLevel = true;
  }
  
  /**
   * Check if the player has won the level.
   * @return if the player has won the level or not
   */
  public boolean wonLevel() {
    return wonLevel;
  }

  // Creating getters and setters of Chap class

  /**
   * Getting the current location of the main character.
   * 
   * @return current location of the main character
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Setting the location of the main character.
   * 
   * @param location new coordinates for the character to be placed at
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * Displaying what is in the player's inventory.
   * 
   * @return player's inventory
   */
  public Inventory getInventory() {
    return inventory;
  }

  // Creating other methods of Chap class

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
   * Checking whether the main character collides the enemy or not.
   * 
   * @param enemy an enemy
   * @return whether the main character collides the enemy or not
   */
  public boolean collidesEnemy(Enemy enemy) {
    return enemy.collidesPlayer(this);
  }

  /**
   * Getting the number of coins collected by the player by looking through the
   * items in the inventory.
   * 
   * @return the number of coins collected by the player.
   */
  public int getNumCollectedCoins() {
    int coins = 0; // initial value
    for (Collectable collectable : inventory.getItems()) {
      // Checking whether the collectable is a coin or not. If it is a coin, increment
      // coins.
      if (collectable instanceof Treasure) {
        coins++;
      }
    }

    return coins;
  }

  /**
   * Getting the list of collected keys by looking through each item in the
   * inventory.
   * 
   * @return a list of all the keys the player has collected.
   */
  public ArrayList<Key> getAllCollectedKeys() {
    ArrayList<Key> collectedKeys = new ArrayList<>();
    for (Collectable collectable : inventory.getItems()) {
      // Checking whether the collectable is a key or not. If it is, add it to the
      // list of collected keys
      if (collectable instanceof Key) {
        collectedKeys.add((Key) collectable);
      }
    }

    return collectedKeys;
  }

  @Override
  public char getMazeChar() {
    return 'P';
  }
}
