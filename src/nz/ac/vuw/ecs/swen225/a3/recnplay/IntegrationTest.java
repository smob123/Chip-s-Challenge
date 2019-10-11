package nz.ac.vuw.ecs.swen225.a3.recnplay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import nz.ac.vuw.ecs.swen225.a3.maze.GameObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Level;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;

import org.junit.jupiter.api.Test;



/**
 * game recorder tests.
 * @author Sultan 300489686
 *
 */
public class IntegrationTest {
  GameRecorder recorder = new GameRecorder();
  RecordPlayer player = new RecordPlayer();
  String firstLevelFile = "record1.json";
  
  @Test
  void recordedMaze() {
    String maze = recorder.getMaze(firstLevelFile);
    
    String expected = 
        "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z______#\n" 
        + "#___?_T_#\n" 
        + "#_______#\n" 
        + "##Y#_#R##\n" 
        + "#C_#_#__#\n" 
        + "#_T#P#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(expected, maze);
  }
  
  @Test
  void forwardMovement() {
    Level level = new Level();
    // get the recorded movement
    ArrayList<String> playerMovement = recorder.getMovements(firstLevelFile);
    
    // set the recorded maze and movement
    player.setMaze(recorder.getMaze(firstLevelFile));
    player.setRecording(playerMovement);
    
    // checks if the recorded movement matches the game play from the first level
    boolean validMovement = true;
    
    // apply the next movement and check if it matches the recording
    for (String s : playerMovement) {
      Maze maze = level.getMaze();
      maze.playerMovement(s);
      player.nextPart();
      Maze recordedMaze = player.getMaze();
      validMovement = assertMazeEquals(maze.getTilesArray(), recordedMaze.getTilesArray());
      
      if (!validMovement) {
        break;
      }
    }
    
    assertTrue(validMovement);
  }
  
  @Test
  void invalidForwardMovement() {
    // get the recorded movement from the file
    ArrayList<String> playerMovement = recorder.getMovements(firstLevelFile);
    
    // set the recorded maze and movement
    player.setMaze(recorder.getMaze(firstLevelFile));
    player.setRecording(playerMovement);
    
    // play all the recorded parts
    for (String s : playerMovement) {
      player.nextPart();
    }
    
    // check that there is no next part
    assertFalse(player.nextPart());
  }
  
  /**
   * checks for equality between two mazes.
   * @param expected expected maze
   * @param actual actual maze
   * @return whether the mazes are equal or not
   */
  boolean assertMazeEquals(GameObject[][] expected, GameObject[][] actual) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        if (expected[i][j].getMazeChar() != actual[i][j].getMazeChar()) {
          return false;
        }
      }
    }
    return true;
  }
}
