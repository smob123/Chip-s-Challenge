package nz.ac.vuw.ecs.swen225.a3.recnplay;

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
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonWriter;

import nz.ac.vuw.ecs.swen225.a3.maze.GameObject;

/**
 * records the status of the maze after each time it updates, and stores the
 * data in a json file.
 * 
 * @author Sultan 300489686
 *
 */
public class GameRecorder {
  // the file containing the recording for the current game
  private File file;
  // the directory where the recording is saved
  private String dir = "src/nz/ac/vuw/ecs/swen225/a3/recnplay/recordings/";
  // the name of the file
  private String fileName = "record1.json";
  private FileWriter fw;
  private JsonWriter jw;
  private FileReader fr;
  // the number of the file in the folder
  private int index = 1;
  private JsonArrayBuilder arrBuilder;

  /**
   * constructor.
   */
  public GameRecorder() {
    file = new File(dir + fileName);
    arrBuilder = Json.createArrayBuilder();
  }

  /**
   * creates a new file to store the game's recordings in.
   */
  private void changeFileName() {
    // get the directory
    File folder = new File(dir);
    
    // check if there are files in the directory
    File[] files = folder.listFiles();
    if (files == null) {
      return;
    }
    
    // check all the files in the directory
    for (File f : files) {
      // check the JSON files
      if (f.getName().contains(".json")) {
        // get the number of the file
        String numString = f.getName().substring(f.getName().indexOf("record") + 6,
            f.getName().indexOf("."));

        // increment it by 1
        int num = Integer.parseInt(numString);
        num++;

        // set the new file's name
        fileName = "record" + num + ".json";
      }
    }
  }

  /**
   * adds the current level's maze to an arraylist.
   * 
   * @param maze the maze in a char array format
   */
  public void addMaze(GameObject[][] maze) {
    String s = "";

    for (int i = 0; i < maze[0].length; i++) {
      for (int j = 0; j < maze.length; j++) {
        // make sure that there is a tile in the current row
        if (maze[j][i] != null) {
          // check the length of the current row
          if (j < maze.length) {
            s += maze[j][i].getMazeChar();
          }
        }
      }

      s += "\n";
    }

    JsonObjectBuilder obj = Json.createObjectBuilder().add("maze", s);
    arrBuilder.add(obj);
  }

  /**
   * add player's movement as a JSON object.
   * 
   * @param move the player's next move
   */
  public void addPlayerMove(String move) {
    JsonObjectBuilder obj = Json.createObjectBuilder().add("move " + index++, move);
    arrBuilder.add(obj);
  }

  /**
   * Get the maze as a string.
   * 
   * @param name the name of the file
   * @return the maze as a string
   */
  public String getMaze(String name) {
    String maze = "";
    try {
      // get the file
      fr = new FileReader(new File(dir + name));

      // read the JSON file into a JSON array
      JsonReader parser = Json.createReader(fr);
      JsonArray arr = parser.readArray();

      JsonObject obj = arr.get(0).asJsonObject();
      JsonString js = (JsonString) obj.getValue("/maze");
      maze = js.getString();

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

  /**
   * returns the recorded player's movement as a list.
   * 
   * @param name the name of the file
   * @return the recorded player's movement as a list
   */
  public ArrayList<String> getMovements(String name) {
    ArrayList<String> list = new ArrayList<>();
    try {
      // get the file
      fr = new FileReader(new File(dir + name));

      // read the JSON file into a JSON array
      JsonReader parser = Json.createReader(fr);
      JsonArray arr = parser.readArray();

      // get all the objects from the array, and add them to the list as strings
      for (int i = 1; i < arr.size(); i++) {
        JsonObject obj = arr.get(i).asJsonObject();
        JsonString js = (JsonString) obj.getValue("/move " + i);
        String s = js.getString();
        list.add(s);
      }

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

    return list;
  }

  /**
   * Get the list of recording files.
   * 
   * @return the list of recording files
   */
  public ArrayList<String> getRecordingFiles() {
    // get the directory
    File folder = new File(dir);
    ArrayList<String> fileNames = new ArrayList<>();

    File[] files = folder.listFiles();
    
    // check if there are files in the directory
    if (files == null) {
      return new ArrayList<String>();
    }
    
    // check all the files in the directory
    for (File f : files) {
      // check the JSON files
      if (f.getName().contains(".json")) {
        // get the number of the file
        fileNames.add(f.getName());
      }
    }

    return fileNames;
  }
  
  /**
   * reset the array builder.
   */
  public void reset() {
    arrBuilder = Json.createArrayBuilder();
  }

  /**
   * write the recordings array to a file.
   */
  public void writeToFile() {
    // if the file already exists then create a new one with a different name
    if (file.exists()) {
      changeFileName();
    }

    // write the recordings list into a JSON object
    try {
      fw = new FileWriter(new File(dir + fileName));
      jw = Json.createWriter(fw);
      jw.writeArray(arrBuilder.build());
      jw.close();
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
