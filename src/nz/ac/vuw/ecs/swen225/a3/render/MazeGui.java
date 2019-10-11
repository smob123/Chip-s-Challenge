package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.maze.GameObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;

/**
 * draws the maze on the canvas.
 * 
 * @author Sultan 300489686
 *
 */
public class MazeGui extends JPanel {
  /**
   * class serial version UID.
   */
  private static final long serialVersionUID = 1456210405604631709L;

  private Maze maze;
  private GameObject[][] mazeArray;
  private BufferedImage freeTile;
  private BufferedImage player;
  private BufferedImage enemy;
  private BufferedImage blueLock;
  private BufferedImage redLock;
  private BufferedImage greenLock;
  private BufferedImage yellowLock;
  private BufferedImage blueKey;
  private BufferedImage redKey;
  private BufferedImage greenKey;
  private BufferedImage yellowKey;
  private BufferedImage exit;
  private BufferedImage exitLock;
  private BufferedImage infoTile;
  private BufferedImage treasure;
  private BufferedImage wall;

  /**
   * constructor.
   * 
   * @param maze the maze we want to draw on the canvas.
   */
  public MazeGui(Maze maze) {
    this.maze = maze;
    initImages();
  }
  
  private void initImages() {
    try {
      freeTile = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/empty_tile.png"));
      player = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/chap.png"));
      enemy = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/enemy.png"));
      blueLock = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/blue_lock_tile.png"));
      greenLock = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/green_lock_tile.png"));
      yellowLock = 
          ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/yellow_lock_tile.png"));
      redLock = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/red_lock_tile.png"));
      exit = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/exit_tile.png"));
      exitLock = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/exit_lock_tile.png"));
      infoTile = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/info_tile.png"));
      blueKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_blue.png"));
      redKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_red.png"));
      greenKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_green.png"));
      yellowKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_yellow.png"));
      treasure = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/coin_gold.png"));
      wall = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/wall_tile.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
//  /**
//   * Set the current maze.
//   * @param m the new maze to replace the current one
//   */
//  public void setMaze(Maze m) {
//    maze = m;
//  }
  
  private BufferedImage getTileImage(char c) {
    if (c == '_') {
      return freeTile;
    } else if (c == '#') {
      return wall;
    } else if (c == 'B') {
      return blueLock;
    } else if (c == 'G') {
      return greenLock;
    } else if (c == 'Y') {
      return yellowLock;
    } else if (c == 'R') {
      return redLock;
    } else if (c == '?') {
      return infoTile;
    } else if (c == 'L') {
      return exitLock;
    } else if (c == 'E') {
      return exit;
    } else if (c == 'F') {
      return enemy;
    } else if (c == 'T') {
      return treasure;
    } else if (c == 'P') {
      return player;
    } else if (c == 'Z') {
      return blueKey;
    } else if (c == 'X') {
      return redKey;
    } else if (c == 'C') {
      return greenKey;
    } else if (c == 'V') {
      return yellowKey;
    } else {
      return null;
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // get the array of tiles
    mazeArray = maze.getTilesArray();

    for (int i = 0; i < mazeArray.length; i++) {
      for (int j = 0; j < mazeArray[i].length; j++) {
        // make sure that there is a tile in the current row
        if (mazeArray[i][j] != null) {
          // check the length of the current row
          if (j < mazeArray[i].length) {
            // calculate the width and height of each tile image
            int tileWidth = (getWidth() / mazeArray.length);
            int tileHeight = (getHeight() / mazeArray[i].length);
            g.drawImage(getTileImage(mazeArray[i][j].getMazeChar()), (tileWidth * i),
                (tileHeight * j), tileWidth, tileHeight, this);
          }
        }
      }
    }
    
    repaint();
  }
}
