package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nz.ac.vuw.ecs.swen225.a3.maze.GameObject;
import nz.ac.vuw.ecs.swen225.a3.maze.InfoTile;
import nz.ac.vuw.ecs.swen225.a3.maze.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.Level;
import nz.ac.vuw.ecs.swen225.a3.maze.Location;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;

import org.junit.jupiter.api.Test;

/**
 * Test cases for the Maze and Level classes.
 * 
 * @author Sultan 300489686
 *
 */
public class LevelTests {
  Level level = new Level();
  
  @Test
  void validLevelRenerding() {
    char[][] mazeArr = level.getMaze().getMaze();

    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i];
      }
      s += "\n";
    }
    
    String excpected = 
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
    
    assertEquals(excpected, s);
  }
  
  @Test
  void correctNumberOfLevels() {
    Level l = level;
    
    // set it to 1, because we already know that there is a first level
    int numOfLevels = 1;
    
    while (l.hasNextLevel()) {
      l.loadNextLevel();
      numOfLevels++;
    }
    
    assertEquals(2, numOfLevels);
  }
  
  @Test
  void changeTile() {
    Maze m = level.getMaze();
    Location l = new Location(0, 0);
    Treasure t = new Treasure(l);
    m.setTile(m.getTile(l.getX(), l.getY()), t);
    
    assertTrue(m.getTile(l.getX(), l.getY()) instanceof Treasure);
  }
  
  @Test
  void validMoveUp() {
    Maze m = level.getMaze();
    m.playerMovement("w");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z______#\n" 
        + "#___?_T_#\n" 
        + "#_______#\n" 
        + "##Y#_#R##\n" 
        + "#C_#P#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void validMoveDown() {
    Maze m = level.getMaze();
    m.playerMovement("w");
    m.playerMovement("s");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
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
    
    assertEquals(excpected, s);
  }
  
  @Test
  void validMoveRight() {
    Maze m = level.getMaze();
    m.playerMovement("w");
    m.playerMovement("w");
    m.playerMovement("w");
    m.playerMovement("d");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z______#\n" 
        + "#___?_T_#\n" 
        + "#____P__#\n" 
        + "##Y#_#R##\n" 
        + "#C_#_#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void validMoveLeft() {
    Maze m = level.getMaze();
    m.playerMovement("w");
    m.playerMovement("w");
    m.playerMovement("w");
    m.playerMovement("a");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z______#\n" 
        + "#___?_T_#\n" 
        + "#__P____#\n" 
        + "##Y#_#R##\n" 
        + "#C_#_#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void invalidMoveUp() {
    Maze m = level.getMaze();
    for (int i = 0; i < 7; i++) {
      m.playerMovement("w");
    }
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z__P___#\n" 
        + "#___?_T_#\n" 
        + "#_______#\n" 
        + "##Y#_#R##\n" 
        + "#C_#_#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void invalidMoveDown() {
    Maze m = level.getMaze();
    m.playerMovement("s");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
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
    
    assertEquals(excpected, s);
  }
  
  @Test
  void invalidMoveLeft() {
    Maze m = level.getMaze();
    m.playerMovement("a");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
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
    
    assertEquals(excpected, s);
  }
  
  @Test
  void invalidMoveRight() {
    Maze m = level.getMaze();
    m.playerMovement("d");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
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
    
    assertEquals(excpected, s);
  }
  
  @Test
  void loadSecondLevel() {
    Level l = level;
    l.setLevelNumber(2);
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#___________#\n" 
        +  "#_########__#\n" 
        +  "#_FFF_______#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void colliedWithEnemy() {
    Level l = level;
    l.setLevelNumber(2);
    Maze m = l.getMaze();
    
    for (int i = 0; i < 2; i++) {
      m.playerMovement("w");
    }
    
    for (int i = 0; i < 2; i++) {
      m.playerMovement("a");
    }
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#___________#\n" 
        +  "#_########__#\n" 
        +  "#_FFF_______#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void enemyLeftMovement() {
    Level l = level;
    l.setLevelNumber(2);
    Maze m = l.getMaze();
    
    m.moveEnemies();
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#___________#\n" 
        +  "#_########__#\n" 
        +  "#FFF________#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void enemyUpMovement() {
    Level l = level;
    l.setLevelNumber(2);
    Maze m = l.getMaze();
    
    for (int i = 0; i < 3; i++) {
      m.moveEnemies();
    }
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#F__________#\n" 
        +  "#F########__#\n" 
        +  "#F__________#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void enemyRightMovement() {
    Level l = level;
    l.setLevelNumber(2);
    Maze m = l.getMaze();
    
    for (int i = 0; i < 5; i++) {
      m.moveEnemies();
    }
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#FFF________#\n" 
        +  "#_########__#\n" 
        +  "#___________#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void enemyDownMovement() {
    Level l = level;
    l.setLevelNumber(2);
    Maze m = l.getMaze();
    
    for (int i = 0; i < 14; i++) {
      m.moveEnemies();
    }
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#_________F_#\n" 
        +  "#_########F_#\n" 
        +  "#_________F_#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void enemyHittingPlayer() {
    Level l = level;
    l.setLevelNumber(2);
    Maze m = l.getMaze();
    
    // move player to the enemy's path
    for (int i = 0; i < 2; i++) {
      m.playerMovement("w");
    }
    
    // move enemies until they collied with the player
    for (int i = 0; i < 19; i++) {
      m.moveEnemies();
    }
    
    GameObject[][] mazeArr = l.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < l.getMaze().getHeight(); i++) {
      for (int j = 0; j < l.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = "#############\n" 
        +  "#____#E#T___#\n" 
        +  "#T_V_#_#____#\n" 
        +  "###R##L##Y###\n" 
        +  "#X__T____Z__#\n" 
        +  "#___________#\n" 
        +  "#_########__#\n" 
        +  "#____FFF____#\n"
        +  "####__?__####\n"
        +  "#TCB__P__G_T#\n" 
        +  "#############\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void collectingTreasures() {
    Maze m = level.getMaze();
    for (int i = 0; i < 4; i++) {
      m.playerMovement("w");
    }
    for (int i = 0; i < 2; i++) {
      m.playerMovement("d");
    }
    
    GameObject o = m.getPlayer().getInventory().getItems().get(0);
    
    assertTrue(o instanceof Treasure);
  }
  
  @Test
  void collectingKeys() {
    Maze m = level.getMaze();
    for (int i = 0; i < 5; i++) {
      m.playerMovement("w");
    }
    for (int i = 0; i < 3; i++) {
      m.playerMovement("a");
    }
    
    GameObject o = m.getPlayer().getInventory().getItems().get(0);
    
    assertTrue(o instanceof Key);
  }
  
  @Test
  void unlockingADoorWithKey() {
    Maze m = level.getMaze();
    for (int i = 0; i < 5; i++) {
      m.playerMovement("w");
    }
    for (int i = 0; i < 3; i++) {
      m.playerMovement("a");
    }
    
    m.playerMovement("d");
    m.playerMovement("w");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##P#L#G##\n" 
        + "#_______#\n" 
        + "#___?_T_#\n" 
        + "#_______#\n" 
        + "##Y#_#R##\n" 
        + "#C_#_#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void unlockingADoorWithoutKey() {
    Maze m = level.getMaze();
    for (int i = 0; i < 3; i++) {
      m.playerMovement("w");
    }
    for (int i = 0; i < 2; i++) {
      m.playerMovement("a");
    }
    
    m.playerMovement("s");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < mazeArr[0].length; i++) {
      for (int j = 0; j < mazeArr.length; j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z______#\n" 
        + "#___?_T_#\n" 
        + "#_P_____#\n" 
        + "##Y#_#R##\n" 
        + "#C_#_#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertEquals(excpected, s);
  }
  
  @Test
  void pauseGame() {
    Maze m = level.getMaze();
    m.pause();
    m.playerMovement("w");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < mazeArr[0].length; i++) {
      for (int j = 0; j < mazeArr.length; j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
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
    
    assertTrue(m.isPaused());
    assertEquals(excpected, s);
  }
  
  @Test
  void resumeGame() {
    Maze m = level.getMaze();
    m.pause();
    m.resume();
    m.playerMovement("w");
    
    GameObject[][] mazeArr = level.getMaze().getTilesArray();
    
    String s = "";
    for (int i = 0; i < level.getMaze().getHeight(); i++) {
      for (int j = 0; j < level.getMaze().getWidth(); j++) {
        s += "" + mazeArr[j][i].getMazeChar();
      }
      s += "\n";
    }
    
    String excpected = 
          "#########\n" 
        + "#T_#E#_X#\n" 
        + "#V_#_#__#\n" 
        + "##B#L#G##\n" 
        + "#Z______#\n" 
        + "#___?_T_#\n" 
        + "#_______#\n" 
        + "##Y#_#R##\n" 
        + "#C_#P#__#\n" 
        + "#_T#_#_T#\n" 
        + "#########\n"
        + "";
    
    assertFalse(m.isPaused());
    assertEquals(excpected, s);
  }
  
  @Test
  void tickTimer() {
    Maze m = level.getMaze();
    m.tick();
    
    assertEquals("01:29", m.getTimer());
  }
  
  @Test
  void timerExpiry() {
    Maze m = level.getMaze();
    for (int i = 0; i < 91; i++) {
      m.tick();
    }
    
    assertEquals("00:00", m.getTimer());
  }
  
  @Test
  void winningTheGame() {
    Maze m = level.getMaze();
    
    for (int i = 0; i < m.getTotalNumberOfCoins(); i++) {
      m.getPlayer().getInventory().addTreasure(new Treasure(null));
    }
    
    for (int i = 0; i < 8; i++) {
      m.playerMovement("w");
    }
    
    assertTrue(m.gameOver());
  }
  
  @Test
  void infoMessege() {
    InfoTile t = new InfoTile(new Location(0, 0), "Information");
    String tInfo = t.getMessage();
    assertTrue(tInfo.equals("Information"));
  }
}
