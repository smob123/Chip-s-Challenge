package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.maze.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.persistence.GameSave;

/**
 * side bar that shows the timer, inventory, and allows the player to pause the
 * game.
 *
 * @author Sultan 300489686
 *
 */
public class SideBar extends JPanel {
  /**
   * class serial version UID.
   */
  private static final long serialVersionUID = 3504595693788186193L;
  private final Maze maze;
  private String levelTitle;
  private Chap player;
  private JLabel title;
  private JLabel timer;
  private JButton pause;
  private JButton save;
  private JButton quit;
  private JButton load;
  private JButton help;
  private BufferedImage treasure; // to get the treasure's image
  private BufferedImage blueKey;
  private BufferedImage redKey;
  private BufferedImage greenKey;
  private BufferedImage yellowKey;

  /**
   * constructor.
   * @param title the title of the level
   *
   * @param maze the maze to show the info for
   */
  public SideBar(String title, Maze maze) {
    this.maze = maze;
    player = maze.getPlayer();
    levelTitle = title;

    try {
      treasure = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/coin_gold.png"));
      blueKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_blue.png"));
      redKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_red.png"));
      greenKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_green.png"));
      yellowKey = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/a3/render/key_yellow.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(new Color(106, 207, 101));
    timer = new JLabel();

    addComponents();
  }

  private void addComponents() {
    title = new JLabel(levelTitle);
    pause = new JButton("Pause");
    save = new JButton("Save");
    load = new JButton("Load");
    help = new JButton("Help");
    quit = new JButton("Quit");

    JButton[] buttons = { pause, save, load, help, quit };

    // make buttons not focusable so they won't interfere with the keyboard listener
    for (JButton b : buttons) {
      b.setFocusable(false);
    }

    pause.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (maze.isPaused()) {
          maze.resume();
        } else {
          maze.pause();
        }
      }
    });

    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameSave save = new GameSave();
        save.saveGameState(title.getText(), maze);
      }
    });

    load.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameSave load = new GameSave();
        load.loadLastSave();
      }
    });

    help.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        maze.pause();
        // show the rules of the game
        JOptionPane.showMessageDialog(null, "- Use the wasd, or arrow keys to move around."
            + "\n- Collect keys to unlock doors"
            + "\n- Collect coints to unlock the door to the exit"
            + "\n- Press R to record your game");
        maze.resume();
      }
    });

    quit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    // add level's title
    title.setAlignmentX(CENTER_ALIGNMENT);
    add(title);
    // align all the items to the center, and add them to the panel
    timer.setAlignmentX(CENTER_ALIGNMENT);
    add(timer);
    for (JButton b : buttons) {
      b.setAlignmentX(CENTER_ALIGNMENT);
      add(b);
    }
  }

  private BufferedImage getKeyColor(char c) {
    if (c == 'Z') {
      return blueKey;
    } else if (c == 'X') {
      return redKey;
    } else if (c == 'C') {
      return greenKey;
    } else if (c == 'V') {
      return yellowKey;
    }

    return null;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // set the pause button based on the game's state
    if (maze.isPaused()) {
      pause.setText("Resume");
    } else {
      pause.setText("Pause");
    }

    // set the graphic's color
    g.setColor(Color.DARK_GRAY);

    // calculate the size of the timer's font
    int fontSize = (int) (getWidth() * 0.10);

    timer.setFont(new Font("Arial", Font.BOLD, fontSize));
    timer.setText(maze.getTimer());

    // draw the number of collected treasures

    // calculate the center point of the canvas
    int center = getWidth() / 2;
    // calculate the string's y position
    int imgWidth = 50;
    int imgHeight = (int) (getHeight() * 0.5) - imgWidth;
    g.drawImage(treasure, center - imgWidth, imgHeight, imgWidth, imgWidth, this);

    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("x " + player.getNumCollectedCoins() + "/" + maze.getTotalNumberOfCoins(),
        center, imgHeight);

    // draw the inventory items
    g.setColor(Color.white);
    // calculate the width and height for each box
    int width = getWidth() / 4;
    int height = getHeight() - width;
    // draw a background behind the boxes
    g.fillRect(0, height, getWidth(), width);

    // draw the boxes
    g.setColor(Color.DARK_GRAY);
    for (int i = 0; i < 4; i++) {
      g.drawRect(i * width, height, width, width);
    }

    // draw the collected keys inside the boxes
    for (int i = 0; i < player.getAllCollectedKeys().size(); i++) {
      Key k = player.getAllCollectedKeys().get(i);
      g.drawImage(getKeyColor(k.getMazeChar()), i * width, height, width, width, this);
    }

    repaint();
  }
}
