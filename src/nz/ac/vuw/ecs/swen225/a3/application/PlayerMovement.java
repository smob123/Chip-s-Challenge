package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Door;
import nz.ac.vuw.ecs.swen225.a3.maze.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.ExitLock;
import nz.ac.vuw.ecs.swen225.a3.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.a3.maze.GameObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Location;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Wall;

/**
 * handles the player's movement.
 * 
 * @author Sultan 300489686
 *
 */
public class PlayerMovement {
  private Maze maze;

  /**
   * constructor.
   * 
   * @param maze the maze of the current level
   */
  public PlayerMovement(Maze maze) {
    this.maze = maze;
  }

  /**
   * checks whether a move the player wants to make is valid or not.
   * 
   * @param nextTile the tile the player wants to move to.
   * @return whether a move the player wants to make is valid or not
   */
  private boolean validMove(GameObject nextTile) {
    // get the game object at the specified location
    Location location = nextTile.getLocation();
    GameObject o = maze.getTilesArray()[location.getX()][location.getY()];

    // check what type of object it is
    if (o instanceof Wall) {
      return false;
    } else if (o instanceof Door) {
      // check if player has a key with the same color as the door
      return maze.takeAction((Door) o);
    } else if (o instanceof ExitLock) {
      // check if the player has collected all the treasures      
      return maze.takeAction((ExitLock) o);
    } else if (o instanceof Enemy) {
      // check if the player moves into an enemy tile
      maze.takeAction((Enemy) o);
      return false;
    }

    // if this statement is reach, then the player can probably move into the next
    // tile
    return true;
  }

  /**
   * Moves chap player, detects collision.
   *
   * @author Jesse Tublin
   * @param s the movement string.
   * @return whether the movement is valid or not
   */
  public boolean playerMovement(String s) {
    // Makes later if statements neater
    Location location = maze.getPlayer().getLocation();
    GameObject nextTile;

    switch (s) {
      // move up
      case "w":
        nextTile = maze.getTilesArray()[location.getX()][location.getY() - 1];
        if (!validMove(nextTile)) {
          return false;
        } else {
          maze.getPlayer().moveUp();
          // check if the maze has to update after the player steps on the new tile
          maze.takeAction(nextTile);

          // check if previous location was where the info tile supposed to be
          if (location.equals(maze.getInfoTile().getLocation())) {
            maze.swapTiles(maze.getPlayer(), maze.getInfoTile());
          } else {
            maze.swapTiles(maze.getPlayer(), new FreeTile(location));
          }

          return true;
        }
        // move left
      case "a":
        nextTile = maze.getTilesArray()[location.getX() - 1][location.getY()];
        if (!validMove(nextTile)) {
          return false;
        } else {
          maze.getPlayer().moveLeft();
          // check if the maze has to update after the player steps on the new tile
          maze.takeAction(nextTile);

          // check if previous location was where the info tile supposed to be
          if (location.equals(maze.getInfoTile().getLocation())) {
            maze.swapTiles(maze.getPlayer(), maze.getInfoTile());
          } else {
            maze.swapTiles(maze.getPlayer(), new FreeTile(location));
          }
          
          return true;
        }
      // move down
      case "s":
        nextTile = maze.getTilesArray()[location.getX()][location.getY() + 1];
        if (!validMove(nextTile)) {
          return false;
        } else {
          maze.getPlayer().moveDown();
          // check if the maze has to update after the player steps on the new tile
          maze.takeAction(nextTile);

          // check if previous location was where the info tile supposed to be
          if (location.equals(maze.getInfoTile().getLocation())) {
            maze.swapTiles(maze.getPlayer(), maze.getInfoTile());
          } else {
            maze.swapTiles(maze.getPlayer(), new FreeTile(location));
          }
          
          return true;
        }
      // move right
      case "d":
        nextTile = maze.getTilesArray()[location.getX() + 1][location.getY()];
        if (!validMove(nextTile)) {
          return false;
        } else {
          maze.getPlayer().moveRight();
          // check if the maze has to update after the player steps on the new tile
          maze.takeAction(nextTile);

          // check if previous location was where the info tile supposed to be
          if (location.equals(maze.getInfoTile().getLocation())) {
            maze.swapTiles(maze.getPlayer(), maze.getInfoTile());
          } else {
            maze.swapTiles(maze.getPlayer(), new FreeTile(location));
          }
          
          return true;
        }

      default:
        return false;
    }
  }
}
