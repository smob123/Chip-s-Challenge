package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.a3.render.MazeGui;

/**
 * GUI implementation for the RecordPlayer class.
 * 
 * @author Sultan 300489686
 *
 */
public class RecordPlayerGui extends JFrame {

  /**
   * auto generated serial version UID.
   */
  private static final long serialVersionUID = -2401814358667146582L;
  private RecordPlayer player = new RecordPlayer();
  private Dimension screenSize;
  private int width; // width of this JFrame
  private int height; // height of this JFrame
  private MazeGui maze;
  private JPanel panel;
  private Timer timer;
  private boolean isPlaying = false;

  /**
   * constructor.
   * 
   * @param mazeString the recorded level's maze as a string
   * @param recordings the list of recorded game states
   */
  public RecordPlayerGui(String mazeString, ArrayList<String> recordings) {
    setTitle("Gameplay Playback");
    // get the size of the screen
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // make the window's width half of the screen's width
    width = (int) (screenSize.width * 0.5);
    // make the window's height 70% of the screen's height
    height = (int) (screenSize.height * 0.7);
    // set the JFrame's size
    setSize(new Dimension(width, height));
    panel = new JPanel();
    // set the panel's background colour
    panel.setBackground(Color.WHITE);
    // set the panel's layout
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    // set the maze in the recorder
    player.setMaze(mazeString);
    // add the recorder player's movement
    player.setRecording(recordings);
    // add a JPanel to display the recorded game
    maze = new MazeGui(player.getMaze());
    panel.add(maze);
    add(panel);

    // add keyboard listener
    addKeyListener(new KeyboardListener());
    setFocusable(true);
  }

  /**
   * open the JFrame in a new window.
   */
  public void display() {
    setVisible(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    timer = new Timer(500, new TimeListener());
    timer.start();
    
    JOptionPane.showMessageDialog(null, "- Press Space to start/pause auto play"
        + "\n- press the Right arrow key to go forward"
        + "\n press the Left arrow key to go backwards");
  }

  private class KeyboardListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
      // if the player presses space, start/pause auto playing
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        isPlaying = !isPlaying;
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        // if the player presses the right arrow move to the next part
        isPlaying = false;
        player.nextPart();
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        // if the player presses the left arrow move to the previous part
        isPlaying = false;
        player.previousPart();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

  }

  /**
   * responsible for reducing the game's timer once every second.
   * 
   * @author Sultan
   *
   */
  private class TimeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (isPlaying) {
        player.nextPart();
      }
    }
  }
}
