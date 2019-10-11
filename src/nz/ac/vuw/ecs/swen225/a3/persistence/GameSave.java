package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonWriter;

import nz.ac.vuw.ecs.swen225.a3.maze.Collectable;
import nz.ac.vuw.ecs.swen225.a3.maze.GameObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;

/**
 * responsible for saving/loading games.
 * @author Dominic
 *
 */
public class GameSave {
  
  File saveFile = new File("src/nz/ac/vuw/ecs/swen225/a3/saved games/game.json");
  
  /**
   * save the current state of the maze.
   * @param levelName the name of the level to save
   * @param maze the maze to save
   */
  public void saveGameState(String levelName, Maze maze) {
    // Saving the game's state
    try (FileWriter fw = new FileWriter(saveFile);
        JsonWriter jsonWriter = Json.createWriter(fw);) {
      // create a JSON builder
      JsonObjectBuilder jsonSavedGameObjectBuilder = Json.createObjectBuilder();
      
      // add the level's name, and the maze's layout
      jsonSavedGameObjectBuilder.add("Level name", levelName).build();
      jsonSavedGameObjectBuilder.add("maze", mazeToString(maze));
      
      // add the width, height, and info message of the maze
      jsonSavedGameObjectBuilder.add("width", maze.getWidth());
      jsonSavedGameObjectBuilder.add("height", maze.getHeight());
      jsonSavedGameObjectBuilder.add("message", maze.getInfoTile().getMessage());
      
      // get all the items the player has collected
      ArrayList<Collectable> collectedCollectables =
          maze.getPlayer().getInventory().getItems();
      // number of collected treasures
      int collectedTreasures = 0;
      // list of collected keys
      JsonArrayBuilder jsonCollectedKeys = Json.createArrayBuilder();
      
      for (Collectable c : collectedCollectables) {
        // if the collectible is a treasure
        if (c instanceof Treasure) {
          // increase the counter
          collectedTreasures++;
        } else if (c instanceof Key) {
          // otherwise store the key as a string in the list
          Key k = (Key) c;
          jsonCollectedKeys.add(k.getKeyColour().toString());
        }
      }
      
      // build the collected keys array
      JsonArray collectedKeys = jsonCollectedKeys.build();
      
      // add the collectibles
      jsonSavedGameObjectBuilder.add("treasures", collectedTreasures);
      jsonSavedGameObjectBuilder.add("keys", collectedKeys);
      
      // write the data into a file
      JsonObject savedGame = jsonSavedGameObjectBuilder.build();
      
      jsonWriter.writeObject(savedGame);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * converts the maze to a string.
   * @author Sultan
   * @param maze the maze to convert
   * @return the generated string
   */
  private String mazeToString(Maze maze) {
    GameObject[][] tiles = maze.getTilesArray();
    String s = "";
    
    for (int i = 0; i < maze.getHeight(); i++) {
      for (int j = 0; j < maze.getWidth(); j++) {
        s += tiles[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    return s;
  }
  
  /**
   * loads the last saved game.
   * @author Sultan
   * @return a maze object of the last save
   */
  public Maze loadLastSave() {
    Maze maze = null;
    try {
      // get the file
      FileReader fr = new FileReader(saveFile);

      // read the JSON file into a JSON object
      JsonReader parser = Json.createReader(fr);
      JsonObject obj = parser.readObject();
      // get the maze
      JsonString mazeObj = (JsonString) obj.getValue("/maze");
      // store it in a string
      String mazeStr = mazeObj.getString();
      // get the width
      JsonNumber jsonWidth = (JsonNumber) obj.getValue("/width");
      int width = jsonWidth.intValue();
      // get the height
      JsonNumber jsonHeight = (JsonNumber) obj.getValue("/height");
      int height = jsonHeight.intValue();
      // set the maze's array
      char[][] mazeArr = new char[width][height];
      
      // current char index in mazeString
      int index = 0;
      // current row in m
      int row = 0;
      // current column in m
      int col = 0;

      while (col < height && row < width) {
        // add the current char to the current row and column, and increase the numbers
        // for the next iteration
        mazeArr[row++][col] = mazeStr.charAt(index++);

        // detect when to move to the next column
        if (row == width) {
          row = 0;
          col++;
        }
      }
      
      // get the info message
      JsonString mazeInfoMessage = (JsonString) obj.getValue("/message");
      
      // setup the maze
      maze = new Maze(width, height, mazeInfoMessage.getString());

      // close the streams
      fr.close();
      parser.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (JsonException e) {
      e.printStackTrace();
      return null;
    }

    return maze;
  }
}
