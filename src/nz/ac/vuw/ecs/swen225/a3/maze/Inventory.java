package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;

/**
 * The inventory containing items that the player has.
 * 
 * @author Dominic Tjiptono
 */
public class Inventory {
  // Initialising private attributes of Inventory class
  private ArrayList<Key> keys;
  private ArrayList<Treasure> treasures;

  /**
   * constructor.
   */
  public Inventory() {
    this.keys = new ArrayList<>();
    this.treasures = new ArrayList<>();
  }

  // Creating other methods of Inventory class

  /**
   * Getting a list of items in the inventory.
   * 
   * @return a list of items in the inventory
   */
  public ArrayList<Collectable> getItems() {
    ArrayList<Collectable> items = new ArrayList<>();
    items.addAll(keys);
    items.addAll(treasures);
    return items;
  }

  /**
   * Adding an item to the inventory.
   * 
   * @param item an item to be added to the inventory
   */
  public void addKey(Key item) {
    if (item != null) {
      keys.add(item);
    }
  }
  
  /**
   * Adding an item to the inventory.
   * 
   * @param item an item to be added to the inventory
   */
  public void addTreasure(Treasure item) {
    if (item != null) {
      treasures.add(item);
    }
  }

  /**
   * Removing an item from the inventory.
   * 
   * @param item
   */
  public void removeKey(Key item) {
    keys.remove(item);
  }

  /**
   * Removing an item from the inventory.
   *
   * @param item
   */
  public void removeTreasure(Treasure item){
    treasures.remove(item);
  }
}
