package dungeon;

import location.Location;
import location.Point2D;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;




/**
 * TicTacToeSwingView represents the view of the Dungeon.
 */
public class DungeonSwingView extends JFrame implements DungeonView {
  private ReadonlyDungeonModel model;
  private JMenuItem settings;
  private JMenuItem restart;
  private JMenuItem quit;
  private DungeonPanel dungeonPanel;
  private DescriptionPanel descriptionPanel;
  private ButtonPanel buttonPanel;

  /**
   * Constructor taking in a read-only model.
   *
   * @param model read-only model
   */
  public DungeonSwingView(ReadonlyDungeonModel model) {
    super("Dungeon");
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
    //set frame
    //JFrame default layout -- BorderLayout
    setSize(1000, 700);
    setLocation(200, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //setLayout(new GridLayout(1,2));

    //set menu bar
    setMenuBar();


    //add dungeon panel(scrollable)
    dungeonPanel = new DungeonPanel(model);
    //dungeonBoard.setPreferredSize(new Dimension(800,800));
    dungeonPanel.setLocation(0, 0);
    dungeonPanel.setPreferredSize(new Dimension(700,700));
    JScrollPane scrollPane = new JScrollPane(dungeonPanel);
    //add(scrollPane,BorderLayout.WEST);
    add(scrollPane, BorderLayout.WEST);

    //new GridLayout(2,1)
    JPanel panel = new JPanel(new GridLayout(2, 1));
    //add description panel(location description)
    descriptionPanel = new DescriptionPanel(model);
    //descriptionPanel.setBackground(Color.black);
    //descriptionPanel.setSize();
    panel.add(descriptionPanel);

    //add button panel(player's information description)
    buttonPanel = new ButtonPanel();
    panel.add(buttonPanel);
    panel.setBounds(700, 0, 300, 700);
    //panel.setLocation(600,0);
    add(panel);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {
    this.addKeyListener(keys);
    settings.addActionListener(clicks);
    restart.addActionListener(clicks);
    quit.addActionListener(clicks);
    buttonPanel.addActionListener(clicks);

  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void setOperationDescription(String result) {
    descriptionPanel.updateOpResult(result);
  }

  @Override
  public void setInformation(String playerInformation) {
    buttonPanel.updateDescription(playerInformation);
  }

  @Override
  public void addDungeon(Location location, int x, int y) {
    dungeonPanel.addNewLocation(location, x, y);
  }

  @Override
  public void updateDungeon() {
    dungeonPanel.updateVisitedLocation();
  }

  //need to call this method in controller!
  @Override
  public void addClickListener(DungeonViewController listener) {
    MouseAdapter clickHandler = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        //System.out.println("ClickX = " + e.getX() + ", ClickY = " + e.getY());
        //get player's current position
        Point2D playerPosition = model.getPlayer().getPosition();
        int offset = 50;
        //convert the index position into view position(at the middle of image)
        int playerViewX = playerPosition.getY() * 100 + offset;
        int playerViewY = playerPosition.getX() * 100 + offset;
        int direction = convertDirection(playerViewX, playerViewY,e.getX(), e.getY());
        //System.out.println("playerX = " + playerViewX + ", playerY = " + playerViewY);
        //valid click
        if (direction != 0) {
          listener.handlePanelClick(direction);
        }
      }
    };
    dungeonPanel.addMouseListener(clickHandler);
  }

  @Override
  public String createSettings() {
    SettingsDialog settingsDialog = new SettingsDialog("Settings");
    return settingsDialog.getTextValue();
  }

  @Override
  public void restartGame() {
    dungeonPanel.resetPanel();
  }

  //set menu bar
  private void setMenuBar() {
    //add menu bar
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    //add menu to menu bar
    JMenu options = new JMenu("Options");
    menuBar.add(options);
    //add menu items to menu
    settings = new JMenuItem("Settings");
    restart = new JMenuItem("Restart");
    quit = new JMenuItem("Quit");

    options.add(settings);
    options.add(restart);
    options.add(quit);

  }

  //convert mouse click position into real move direction
  private int convertDirection(int playerX, int playerY, int clickX, int clickY) {
    int offset = 50;
    //move north
    if ((playerY - clickY > offset) && (playerY - clickY < 100 + offset)
            && Math.abs(playerX - clickX) < offset) {
      return 1;
      //move south
    } else if ((clickY - playerY > offset) && (clickY - playerY < 100 + offset)
            && Math.abs(playerX - clickX) < offset) {
      return 2;
      //move east
    } else if ((clickX - playerX > offset) && (clickX - playerX < 100 + offset)
            && Math.abs(playerY - clickY) < offset) {
      return 3;
      //move west
    } else if ((playerX - clickX > offset) && (playerX - clickX < 100 + offset)
            && Math.abs(playerY - clickY) < offset) {
      return 4;
    }
    return 0;
  }
}
