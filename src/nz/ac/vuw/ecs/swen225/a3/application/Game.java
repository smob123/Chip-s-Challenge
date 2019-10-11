package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.a3.maze.Level;
import nz.ac.vuw.ecs.swen225.a3.persistence.GameSave;
import nz.ac.vuw.ecs.swen225.a3.recnplay.GameRecorder;
import nz.ac.vuw.ecs.swen225.a3.recnplay.RecordPlayerGui;
import nz.ac.vuw.ecs.swen225.a3.render.MazeGui;

/**
 * canvas to draw the maze on.
 *
 * @author Sultan 300489686
 *
 */
public class Game extends JFrame {
  /**
   * default class serial version.
   */
  private static final long serialVersionUID = 1L;
  private JPanel panel = new JPanel(); // the panel where the game's gui elements will be drawn
  private Dimension screenSize;
  private int width; // width of this JFrame
  private int height; // height of this JFrame
  private Timer timer; // reduces the level's timer by 1 each second
  private Level level;
  private MazeGui maze;
  private SideBar sideBar;
  private JOptionPane pauseDialog; // dialog to show the player when they pause the game
  private GameRecorder recorder;
  private boolean isRecording = false;

  /**
   * constructor.
   */
  public Game() {
    setTitle("Chap's Challenge");
    // get the size of the screen
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // make the window's width half of the screen's width
    width = (int) (screenSize.width * 0.5);
    // make the window's height 70% of the screen's height
    height = (int) (screenSize.height * 0.7);
    // set the JFrame's size
    setSize(new Dimension(width, height));
    addMenuBar();
    // set the panel's background colour
    panel.setBackground(Color.WHITE);
    // set the panel's layout
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    // add the panel to the JFrame
    add(panel);
    // initialize the timer
    timer = new Timer(1000, new TimeListener());
    // create a level
    level = new Level();
    // initialize the game
    initializeGame();
    // add the keyboard listener
    addKeyListener(new KeyboardListener());
    setFocusable(true);
  }

  private void initializeGame() {
    // load the current maze
    maze = new MazeGui(level.getMaze());
    sideBar = new SideBar(level.getLevelName(), level.getMaze());
    recorder = new GameRecorder();

    // set the maze and side bar sizes inside the panel
    maze.setPreferredSize(new Dimension((int) (getWidth() * 0.6), getHeight()));
    sideBar.setPreferredSize(new Dimension((int) (getWidth() * 0.25), getHeight()));
    sideBar.setMaximumSize(new Dimension((int) (getWidth() * 0.3), getHeight()));

    // initialize the pause dialog
    pauseDialog = new JOptionPane();

    // add the maze to the panel
    panel.add(maze);
    panel.add(sideBar);

    // start the timer
    timer.start();

    // add the current level to the game recorder
    recorder.addMaze(level.getMaze().getTilesArray());

    onWindowClose();
  }

  /**
   * saves the gameplay automatically when the window is about to close.
   */
  private void onWindowClose() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // save the gameplay in a JSON file if the game is recording
        if  (isRecording) {
          recorder.writeToFile();
        }
      }
    });
  }

  private void addMenuBar() {
    // create a menu item
    JMenuItem quit = new JMenuItem("Quit");
    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    // create a save item
    JMenuItem save = new JMenuItem("Save");
    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameSave save = new GameSave();
        save.saveGameState(level.getLevelName(), level.getMaze());
      }
    });

    // loading data
    JMenuItem load = new JMenuItem("Load");
    load.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameSave load = new GameSave();
        load.loadLastSave();
      }
    });

    JMenuItem playRecording = new JMenuItem("Play recording...");
    playRecording.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        // pause the game
        level.getMaze().pause();

        // get the recording files' names
        Object[] options = recorder.getRecordingFiles().toArray();

        // if there are no files
        if (options.length < 1) {
          // show a dialog message and exit the method
          JOptionPane.showMessageDialog(null, "You have no recording files");
          return;
        }

        // show a dialog box for the player to make a choice
        String choice = "";

          choice = (String) JOptionPane.showInputDialog(null,
              "Choose a file to watch", "Choose a file",
              JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

          // make sure that the player has made a choice
          if (choice == null || choice.equals("")) {
              return;
            }

          RecordPlayerGui rp = new RecordPlayerGui(recorder.getMaze(choice),
              recorder.getMovements(choice));
          rp.display();
      }
    });

    // create a JMenu
    JMenu menu = new JMenu("File");
    // add the items to the menu
    menu.add(save);
    menu.add(load);
    menu.add(playRecording);
    menu.add(quit);

    // create the JMenuBar object first
    JMenuBar menuBar = new JMenuBar();
    // add the menu to the menu bar
    menuBar.add(menu);
    // set the JFrame's menu bar to the one we just created
    setJMenuBar(menuBar);
  }

  /**
   * listens for key presses.
   *
   * @author Sultan
   *
   */
  private class KeyboardListener implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
      // check if an arrow key was pressed
      if (arrowMovements(e.getKeyCode())) {
        // start recording
        if (!isRecording) {
          isRecording = true;
        }

        // if the player steps on the info tile
        if  (level.getMaze().getPlayer().getLocation()
            .equals(level.getMaze().getInfoTile().getLocation())) {
          // show the message in as a dialog
          JOptionPane.showMessageDialog(null,
              level.getMaze().getInfoTile().getMessage());
        }

        // load the next level if there is one
        if (level.getMaze().getPlayer().wonLevel() && level.hasNextLevel()) {
          level.loadNextLevel();
          loadLevel();
        }

        return;
      }

      // check if any of the wasd keys were pressed
      if (wasdMovement(e)) {
        // start recording
        if (!isRecording) {
          isRecording = true;
        }

        // load the next level if there is one
        if (level.getMaze().getPlayer().wonLevel() && level.hasNextLevel()) {
          level.loadNextLevel();
          loadLevel();
        }

        // if the player steps in the tile info
        if  (level.getMaze().getPlayer().getLocation()
            .equals(level.getMaze().getInfoTile().getLocation())) {
          // show the message as a dialog
          JOptionPane.showMessageDialog(null,
              level.getMaze().getInfoTile().getMessage());
        }

        return;
      }

      // check if it's a shortcut key
      if (shortCuts(e.getKeyCode())) {
        return;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private boolean arrowMovements(int keyCode) {
      // handle arrow key movements
      if (keyCode == KeyEvent.VK_UP) {
        level.getMaze().playerMovement("w");
        // record the move
        recorder.addPlayerMove("w");
        return true;
      } else if (keyCode == KeyEvent.VK_LEFT) {
        level.getMaze().playerMovement("a");
        // record the move
        recorder.addPlayerMove("a");
        return true;
      } else if (keyCode == KeyEvent.VK_DOWN) {
        level.getMaze().playerMovement("s");
        // record the move
        recorder.addPlayerMove("s");
        return true;
      } else if (keyCode == KeyEvent.VK_RIGHT) {
        level.getMaze().playerMovement("d");
        // record the move
        recorder.addPlayerMove("d");
        return true;
      }

      return false;
    }

    private boolean wasdMovement(KeyEvent e) {
      // handle movements by wasd
      String c = e.getKeyChar() + "";
      String moveKeys = "wasd";

      for (int i = 0; i < moveKeys.length(); i++) {
        String currentChar = moveKeys.charAt(i) + "";
        if (c.equalsIgnoreCase(currentChar)) {
          level.getMaze().playerMovement(c.toLowerCase());
          // record the move
          recorder.addPlayerMove(c.toLowerCase());
          return true;
        }
      }

      return false;
    }

    private boolean shortCuts(int keyCode) {
      if (keyCode == KeyEvent.VK_SPACE) {
        level.getMaze().pause();

        // show dialog
        pauseDialog.showMessageDialog(null,
              "Game is paused. Close the window "
                  + "or press ESC to resume");

        // resume the game when the dialog is removed
        level.getMaze().resume();
        return true;
      }

      // check if the escape character was pressed
      if (keyCode == KeyEvent.VK_ESCAPE) {
        level.getMaze().resume();
        return true;
      }

      // detect ctrl + x
      KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
      // if ctrl + x were pressed then exit the game
      if (keyCode == ctrlX.getKeyCode()) {
        System.exit(0);
        return true;
      }
      
      // detect ctrl + s
      KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
      // if ctrl + x were pressed then exit the game
      if (keyCode == ctrlS.getKeyCode()) {
        GameSave save = new GameSave();
        // save the maze and exit
        save.saveGameState(level.getLevelName(), level.getMaze());
        System.exit(0);
        return true;
      }
      
      // detect ctrl + R
      KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
      // if ctrl + x were pressed then exit the game
      if (keyCode == ctrlR.getKeyCode()) {
        GameSave load = new GameSave();
        // load the last saved level
        load.loadLastSave();
        return true;
      }

      // detect ctrl + 1
      KeyStroke ctrl1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK);

      // if ctrl + 1 were pressed load the first level
      if (keyCode == ctrl1.getKeyCode()) {
        // load the first level
        level.setLevelNumber(1);
        loadLevel();
        return true;
      }

      return false;
    }

    /**
     * refreshes the JPanel to load a level.
     */
    private void loadLevel() {
      // remove everything from the panel
      panel.removeAll();
      panel.updateUI();
      // reset the game recorder
      recorder.writeToFile();
      recorder.reset();
      isRecording = false;
      // reinitialize the game
      initializeGame();
    }
  }

  /**Key
   * responsible for reducing the game's timer once every second, and moving the enemies.
   *
   * @author Sultan
   *
   */
  private class TimeListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      // reduce the game's timer
      level.getMaze().tick();
      // move enemies around the maze if there are any
      if (level.getMaze().hasEnemies()) {
        level.getMaze().moveEnemies();
      }

      // check if the game is over, but the player hasn't won
      if (level.getMaze().gameOver() && !
           level.getMaze().getPlayer().wonLevel()) {
        // ask them if they want to restart the level or exit
        int reply = JOptionPane.showConfirmDialog(null,
              "Would you like to restart the level?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
          level.reload();
          new KeyboardListener().loadLevel();
        } else {
          System.exit(0);
        }
      }
    }
  }
}