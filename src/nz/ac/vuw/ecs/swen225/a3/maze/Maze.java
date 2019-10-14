package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;

import nz.ac.vuw.ecs.swen225.a3.application.EnemyMovement;
import nz.ac.vuw.ecs.swen225.a3.application.PlayerMovement;

/**
 * Maze for each level.
 *
 * @author Sultan 300489686
 *
 */
public class Maze {
  private final int width;
  private final int height;
  private char[][] maze;
  private GameObject[][] tileMaze;
  private InfoTile infoTile;
  private Chap playerChap;
  private Location playerSpawn;
  private PlayerMovement movement;
  private ArrayList<Enemy> enemies; // list of the enemies in the maze
  private ArrayList<Location> enemyPatterns; // the patterns of the enemies' movements
  private ArrayList<EnemyMovement> enemyMovement;
  private boolean paused = false;
  private boolean gameOver = false;
  private int minutes = 1;
  private int seconds = 30;
  private int numOfTrreasures = 0; // total number of treasures in the maze
  private String infoMessage;

  /**
   * constructor.
   *
   * @param width  the maze's width
   * @param height the maze's height
   * @param infoMessage the message that gets displayed when the player steps on an info tile
   */
  public Maze(int width, int height, String infoMessage) {
    this.width = width;
    this.height = height;
    this.infoMessage = infoMessage;
    maze = new char[width][height];
    tileMaze = new GameObject[width][height];

    enemies = new ArrayList<>();
    enemyPatterns = new ArrayList<>();
    enemyMovement = new ArrayList<>();

    movement = new PlayerMovement(this);
  }

  /**
   * Get the maze's width.
   *
   * @return the maze's width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the maze's height.
   *
   * @return the maze's height
   */
  public int getHeight() {
    return height;
  }

  /**
   * check if the game is over or not.
   * 
   * @return whether the game is over or not
   */
  public boolean gameOver() {
    return gameOver;
  }

  /**
   * pause the game.
   */
  public void pause() {
    paused = true;
  }

  /**
   * resume the game.
   */
  public void resume() {
    paused = false;
  }

  /**
   * check if the game is paused or not.
   * 
   * @return whether the game is paused or not
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * Get how much time is left before the game is over.
   * 
   * @return the timer in the format mm:ss
   */
  public String getTimer() {
    String minute = minutes < 10 ? "0" + minutes : "" + minutes;
    String second = seconds < 10 ? "0" + seconds : "" + seconds;
    String timer = minute + ":" + second;

    if (timer.equals("00:00")) {
      gameOver = true;
    }

    return timer;
  }

  /**
   * reduce seconds by 1.
   */
  public void tick() {
    // if the game is over then stop reducing the timer
    if (gameOver || paused) {
      return;
    }

    // if seconds are 0
    if (seconds == 0) {
      // and minutes are 0 as well
      if (minutes == 0) {
        // then end the game
        gameOver = true;
      } else {
        // otherwise set the seconds to 59 and reduce the minutes by 1
        minutes--;
        seconds = 59;
      }
    } else {
      // otherwise reduce the seconds by 1
      seconds--;
    }
  }

  /**
   * move enemies around the maze.
   */
  public void moveEnemies() {
    if (gameOver || paused) {
      return;
    }

    for (EnemyMovement em : enemyMovement) {
      // get the enemy's current location
      Location currentLocation = em.getEnemyLocation();

      // check if the destination has been reached
      if (!em.destinationReached()) {
        em.move();
      } else {
        // if it has, grab the index of current destination
        int index = enemyPatterns.indexOf(em.getDestination());
        // check if the last destination was reached
        if (index == enemyPatterns.size() - 1) {
          // go back to the first one
          index = -1;
        }

        // set the enemy's destination to the next one in the list
        em.setDest(enemyPatterns.get(++index));
        // apply the move
        em.move();
      }

      swapTiles(em.getEnemy(), new FreeTile(currentLocation));

      // if the player and enemy collied
      if (em.getEnemyLocation().equals(playerChap.getLocation())) {
        // put the player back in the spawn location
        respawnPlayer();
      }
    }
  }

  /**
   * Set the tile at a given position.
   *
   * @param oldTile the old tile we want to change
   * @param newTile the new tile to add instead of the old tile
   */
  public void setTile(GameObject oldTile, GameObject newTile) {
    Location tileLocation = oldTile.getLocation();

    tileMaze[tileLocation.getX()][tileLocation.getY()] = newTile;
  }

  /**
   * Get the tile at the given position.
   *
   * @param x the tile's x position.
   * @param y the tile's y position.
   * @return the tile at the given position.
   */
  public GameObject getTile(int x, int y) {
    return tileMaze[x][y];
  }

  /**
   * Sets the maze.
   *
   * @param newMaze the new maze layout
   */
  public void setMaze(char[][] newMaze) {
    maze = newMaze;
    setTileMaze();

    // if there are enemies in the maze, then initialize their movement objects
    if (hasEnemies()) {
      initEnemyMovements();
    }
  }

  /**
   * initialize enemy movements array.
   */
  private void initEnemyMovements() {
    for (Enemy e : enemies) {
      EnemyMovement em = new EnemyMovement(e);
      em.setDest(enemyPatterns.get(0));
      enemyMovement.add(em);
    }
  }

  /**
   * translate the char array into a tile array for the GUI part.
   */
  private void setTileMaze() {
    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[i].length; j++) {
        // the current character in the array
        char c = maze[i][j];

        // the current x, y location
        Location l = new Location(i, j);

        // add tiles based on their character in the maze
        if (c == '_') {
          tileMaze[i][j] = new FreeTile(l);
        } else if (c == '#') {
          tileMaze[i][j] = new Wall(l);
        } else if (c == 'B') {
          tileMaze[i][j] = new Door(l, Key.Colour.BLUE);
        } else if (c == 'G') {
          tileMaze[i][j] = new Door(l, Key.Colour.GREEN);
        } else if (c == 'Y') {
          tileMaze[i][j] = new Door(l, Key.Colour.YELLOW);
        } else if (c == 'R') {
          tileMaze[i][j] = new Door(l, Key.Colour.RED);
        } else if (c == '?') {
          tileMaze[i][j] = new InfoTile(l, infoMessage);
          infoTile = (InfoTile) tileMaze[i][j];
        } else if (c == 'L') {
          tileMaze[i][j] = new ExitLock(l);
        } else if (c == 'E') {
          tileMaze[i][j] = new Exit(l);
        } else if (c == 'F') {
          Enemy e = new Enemy(l);
          enemies.add(e);
          tileMaze[i][j] = e;
        } else if (c == 'T') {
          tileMaze[i][j] = new Treasure(l);
          numOfTrreasures++;
        } else if (c == 'P') {
          playerChap = new Chap(l);
          playerSpawn = l;
          tileMaze[i][j] = new Chap(l);
        } else if (c == 'Z') {
          tileMaze[i][j] = new Key(l, Key.Colour.BLUE); // creates blue key
        } else if (c == 'X') {
          tileMaze[i][j] = new Key(l, Key.Colour.RED); // creates blue red key
        } else if (c == 'C') {
          tileMaze[i][j] = new Key(l, Key.Colour.GREEN); // creates green key
        } else if (c == 'V') {
          tileMaze[i][j] = new Key(l, Key.Colour.YELLOW); // creates yellow key
        }
      }
    }
  }

  /**
   * Set the list of patterns.
   * 
   * @param p the new list patterns
   */
  public void setEnemyPatterns(ArrayList<Location> p) {
    enemyPatterns = p;
  }

  /**
   * Get if there are enemies in the maze.
   * 
   * @return if there are enemies in the maze
   */
  public boolean hasEnemies() {
    return enemies.size() > 0;
  }

  /**
   * Get total number of coins in the maze.
   * 
   * @return total number of coins in the maze
   */
  public int getTotalNumberOfCoins() {
    return numOfTrreasures;
  }

  /**
   * Get the info tile.
   * 
   * @return the info tile
   */
  public InfoTile getInfoTile() {
    return infoTile;
  }

  /**
   * Get the player.
   * 
   * @return the player
   */
  public Chap getPlayer() {
    return playerChap;
  }

  /**
   * Get the tiles array.
   *
   * @return the tiles array
   */
  public GameObject[][] getTilesArray() {
    return tileMaze;
  }

  /**
   * returns the maze as an array.
   *
   * @return the maze as an array
   */
  public char[][] getMaze() {
    return maze;
  }

  /**
   * check whether the move the player wants to make will change the game's state.
   * 
   * @param nextTile the tile that the player wants to move to
   * @return whether the game's state should change or not
   */
  public boolean takeAction(GameObject nextTile) {
    // get the game object at the specified location
    Location location = nextTile.getLocation();
    GameObject o = tileMaze[location.getX()][location.getY()];

    if (o instanceof Collectable) {
      // check if the collectable is a key or a treasure
      if (o instanceof Key) {
        playerChap.getInventory().addKey((Key) o);
      } else if (o instanceof Treasure) {
        playerChap.getInventory().addTreasure((Treasure) o);
      }
      return true;
    } else if (o instanceof Door) {
      // look for the door's key in the player's inventory
      for (int i = 0; i < playerChap.getAllCollectedKeys().size(); i++) {
        Key k = playerChap.getAllCollectedKeys().get(i);
        Door d = (Door) o;
        // if the player has the key that unlocks the door
        if (k.getKeyColour() == d.getKeyColor()) {
          // remove the key from the player's inventory and allow them to move into the
          // room
          playerChap.getInventory().removeKey(k);
          return true;
        }
      }
    } else if (o instanceof ExitLock) {
      // check if the player has collected all the coins
      if (playerChap.getNumCollectedCoins() == getTotalNumberOfCoins()) {
        return true;
      }
    } else if (o instanceof Exit) {
      playerChap.win();
      gameOver = true;
    } else if (o instanceof Enemy) {
      // put the player back in the spawn area
      respawnPlayer();
    }

    return false;
  }

  private void respawnPlayer() {
    Location oldLocation = playerChap.getLocation();
    playerChap.setLocation(playerSpawn);
    swapTiles(playerChap, new FreeTile(oldLocation));
  }

  /**
   * swaps two tiles in the tileMaze.
   * 
   * @param go1 the first tile
   * @param go2 the second tile
   */
  public void swapTiles(GameObject go1, GameObject go2) {
    GameObject o = go1;
    go1 = go2;
    go2 = o;

    tileMaze[go1.getLocation().getX()][go1.getLocation().getY()] = go1;
    tileMaze[go2.getLocation().getX()][go2.getLocation().getY()] = go2;
  }

  /**
   * Moves chap player, detects collision.
   *
   * @author Jesse Tublin
   * @param s the movement string.
   * @return whether the movement is valid or not
   */
  public boolean playerMovement(String s) {
    // if the game is over, then revoke all the player's moves
    if (gameOver || paused) {
      return false;
    }

    return movement.playerMovement(s);
  }
}
