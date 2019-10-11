package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * This class is responsible for handling the location of each object in the
 * maze.
 * 
 * @author Sultan 300489686
 *
 */

public class Location {
  private int row;
  private int column;

  /**
   * constructor.
   * 
   * @param row the object's x coordinate on the canvas
   * @param column the object's y coordinate on the canvas
   */
  public Location(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Set the location's x coordinate.
   * 
   * @param newRow new x coordinate
   */
  public void setX(int newRow) {
    row = newRow;
  }

  /**
   * Set the location's y coordinate.
   * 
   * @param newColumn new y coordinate
   */
  public void setY(int newColumn) {
    column = newColumn;
  }

  /**
   * returns the object's x coordinate on the canvas.
   * 
   * @return the object's x coordinate on the canvas
   */
  public int getX() {
    return row;
  }

  /**
   * returns the object's y coordinate on the canvas.
   * 
   * @return the object's y coordinate on the canvas
   */
  public int getY() {
    return column;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Location)) {
      return false;
    }
    Location l = (Location) obj;
    return getX() == l.getX() && getY() == l.getY();
  }
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
