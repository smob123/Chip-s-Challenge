package nz.ac.vuw.ecs.swen225.a3.maze;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

/**
 * Levels for the game.
 * 
 * @author Sultan 300489686
 */
public class Level {

  private Maze maze; // the maze for the current level
  private int levelNum = 1;
  private String levelName = "level" + levelNum; // the current level's name
  private boolean hasEnemies = false; // checks if the level has enemies in it
  private ArrayList<Location> enemyPattern;
  private File levelDir; // directory that contains the level files
  private int availableLevels = 0;
  // the message that gets displayed when the player steps on an info tile
  private String mazeInfoMessage = "";

  /**
   * constructor.
   */
  public Level() {
    // get levels in the directory
    getLevels();
    // read the maze from the .txt file
    readMazeFile(1);
  }

  private void getLevels() {
    levelDir = new File("src/nz/ac/vuw/ecs/swen225/a3/levels/");
    File[] levels = levelDir.listFiles();

    if (levels == null) {
      throw new IllegalArgumentException("There are no level files in the specified directory");
    }

    for (File f : levels) {
      if (f.getName().contains("level") && f.getName().contains(".json")) {
        availableLevels++;
      }
    }
  }

  /**
   * read the current level's maze file assuming that all the rows have the same
   * length.
   * 
   * @param num the number of the level we want to load
   */
  private void readMazeFile(int num) {
    // get the file containing the first maze
    File mazeFile = new File("src/nz/ac/vuw/ecs/swen225/a3/levels/level" + num + ".json");

    try {
      // read the level's file
      FileReader fr = new FileReader(mazeFile);
      // read the JSON object in the file
      JsonReader reader = Json.createReader(fr);
      JsonObject objects = reader.readObject();
      // get the width of the maze
      JsonNumber widthObj = (JsonNumber) objects.getValue("/width");
      int width = widthObj.intValue();

      // get the height of the maze
      JsonNumber heightObj = (JsonNumber) objects.getValue("/height");
      int height = heightObj.intValue();
      
      JsonString messageObj = (JsonString) objects.getValue("/message");
      mazeInfoMessage = messageObj.getString();

      if (objects.containsKey("enemyPattern")) {
        hasEnemies = true;
        readEnemyPattern(objects.getJsonArray("enemyPattern"));
      }

      // get the layout of the maze
      JsonString mazeObj = (JsonString) objects.getValue("/maze");
      String maze = mazeObj.getString();

      // initialize the maze object
      setMaze(maze, width, height);

      // set the new level's name
      levelNum = num;
      levelName = "level" + num;
    } catch (Exception e) {
      System.err.println("Couldn't find maze file");
    }
  }

  /**
   * read the enemy's pattern from the JSON file.
   * 
   * @param patterns steps of the pattern
   */
  private void readEnemyPattern(JsonArray patterns) {
    enemyPattern = new ArrayList<>();
    for (int i = 0; i < patterns.size(); i++) {
      // get the object at the current index
      JsonObject curr = patterns.get(i).asJsonObject();
      // get the coordinates from the object
      JsonArray coords = curr.getValue("/dest" + (i + 1)).asJsonArray();
      // get the x coordinate
      JsonNumber rowCoord = (JsonNumber) coords.get(0).asJsonObject().getValue("/x");
      int x = rowCoord.intValue();

      // get the y coordinate
      JsonNumber columnCoord = (JsonNumber) coords.get(0).asJsonObject().getValue("/y");
      int y = columnCoord.intValue();

      enemyPattern.add(new Location(x, y));
    }
  }

  /**
   * initializes the maze for the current level.
   * 
   * @param mazeString the maze layout as a string
   * @param width      width of the maze
   * @param height     height of the maze
   */
  public void setMaze(String mazeString, int width, int height) {
    // stores the maze's layout as a 2d array
    char[][] m = new char[width][height];

    // current char index in mazeString
    int index = 0;
    // current row in m
    int row = 0;
    // current column in m
    int col = 0;

    while (col < height && row < width) {
      // add the current char to the current row and column, and increase the numbers
      // for the next iteration
      m[row++][col] = mazeString.charAt(index++);

      // detect when to move to the next column
      if (row == width) {
        row = 0;
        col++;
      }
    }

    // setup the maze
    maze = new Maze(width, height, mazeInfoMessage);

    if (hasEnemies) {
      maze.setEnemyPatterns(enemyPattern);
    }

    maze.setMaze(m);
  }

  /**
   * set the maze's file name based on the passed number.
   * 
   * @param num the number of the level
   */
  public void setLevelNumber(int num) {
    readMazeFile(num);
  }
  
  /**
   * reload the current level.
   */
  public void reload() {
    readMazeFile(levelNum);
  }
  
  /**
   * Check if there is a level after the current one.
   * @return if there is a level after the current one
   */
  public boolean hasNextLevel() {
    return levelNum < availableLevels;
  }
  
  /**
   * load the next level file.
   */
  public void loadNextLevel() {
    readMazeFile(levelNum + 1);
  }

  /**
   * Get the level's maze.
   * 
   * @return the level's maze
   */
  public Maze getMaze() {
    return maze;
  }

  /**
   * Get the name of the level.
   * 
   * @return the name of the level
   */
  public String getLevelName() {
    return levelName;
  }
}
