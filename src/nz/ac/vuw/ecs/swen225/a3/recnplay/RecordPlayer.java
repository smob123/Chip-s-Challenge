package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.util.ArrayList;
import java.util.Scanner;

import nz.ac.vuw.ecs.swen225.a3.maze.Level;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;

/**
 * plays previous recording of the game.
 * 
 * @author Sultan 300489686
 *
 */
public class RecordPlayer {

  private Level level;
  private ArrayList<String> recordings; // list of recorded player's moves
  private ArrayList<String> recordingsBackwards; // list of recorded player's moves backwards
  private int index = -1;

  /**
   * Set the list of the game's statuses.
   * 
   * @param r the list of the game's statuses
   */
  public void setRecording(ArrayList<String> r) {
    recordings = new ArrayList<>(r);
    recordingsBackwards = new ArrayList<>();
    level = new Level();
    
    getRecordingsBackwards();
  }
  
  /**
   * get the opposite of each move in recordings, and store it in recordingsBackwards.
   */
  private void getRecordingsBackwards() {
    for (int i = 0; i < recordings.size(); i++) {
      String s = recordings.get(i);
      
      switch (s) {
        case "w":
          s = "s";
          break;
        case "s":
          s = "w";
          break;
        case "a":
          s = "d";
          break;
        case "d":
          s = "a";
          break;
        default:
          break;
      }
      
      recordingsBackwards.add(s);
    }
  }

  /**
   * Set the maze for the recorded level.
   * 
   * @param maze the reorder level's maze
   */
  public void setMaze(String maze) {
    ArrayList<String> l = new ArrayList<>();

    Scanner scan = new Scanner(maze);

    // read it line by line
    while (scan.hasNext()) {
      l.add(scan.nextLine());
    }

    // close the scanner and increment the index for the next time the method is
    // called
    scan.close();
  }

  /**
   * Get the current state of the maze.
   * 
   * @return the current state of the maze
   */
  public Maze getMaze() {
    return level.getMaze();
  }

  /**
   * play the next part of the recording.
   * 
   * @return whether there is a next part of not
   */
  public boolean nextPart() {
    String s = getNextPart();
    if (s == null) {
      return false;
    }

    level.getMaze().playerMovement(s);
    return true;
  }

  /**
   * Get the previous player has maze.
   * @return the previous move the player has made
   */
  public boolean previousPart() {
    String s = getPreviousPart();

    if (s == null) {
      return false;
    }

    level.getMaze().playerMovement(s);
    return true;
  }

  /**
   * Get the next part of the recording.
   * 
   * @return the next part of the recording
   */
  private String getNextPart() {
    // make sure that there is a next part
    if (index >= recordings.size() - 1) {
      return null;
    }
    index++;
    return recordings.get(index);
  }

  /**
   * Get the previous part of the recording.
   * 
   * @return the previous part of the recording
   */
  private String getPreviousPart() {
    if (index < 0) {
      return null;
    }
    
    String s = recordingsBackwards.get(index);

    index--;
    return s;
  }
}
