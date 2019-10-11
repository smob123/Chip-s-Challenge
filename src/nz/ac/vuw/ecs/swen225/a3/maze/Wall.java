package nz.ac.vuw.ecs.swen225.a3.maze;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A wall on the maze.
 * 
 * @author Sultan 300489686
 *
 */
public class Wall extends Tile {
  BufferedImage img;

  /**
   * constructor.
   * 
   * @param location  the tile's location on the maze
   */
  public Wall(Location location) {
    super(location);
  }

  @Override
  public char getMazeChar() {
    return '#';
  }
}
