package dungeon;

import location.Location;
import player.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 * BoardPanel represents the panel of the view, which draws the game scene
 * of Dungeon.
 */
class DungeonPanel extends JPanel {

  private static final int SIZE = 100;
  private final ReadonlyDungeonModel model;
  private Location[][] visitedLocations;
  private JLabel[][] labels;

  /**
   * DungeonPanel constructor that using the read-only model.
   *
   * @param model read-only model
   */
  DungeonPanel(ReadonlyDungeonModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
    //this.setLayout(new GridLayout(x,y));
    setLayout(null);
    //reset panel
    resetPanel();

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    //iterate visited locations, draw the dungeon
    for (int i = 0; i < visitedLocations.length; i++) {
      for (int j = 0; j < visitedLocations[0].length; j++) {
        //player has visited this location, draw this location
        if (visitedLocations[i][j] != null) {
          URL locationUrl = getLocationUrl(i, j);
          URL treasureUrl = getTreasureUrl(i, j);
          URL arrowUrl = getArrowUrl(i, j);
          URL playerUrl = getPlayerUrl(i, j);
          URL stenchUrl = getStenchUrl(i, j);
          URL otyughUrl = getOtyughUrl(i, j);
          try {
            //combine images
            Image combineImage = combineImage(locationUrl, treasureUrl, arrowUrl,
                    playerUrl, stenchUrl, otyughUrl);
            ImageIcon combinedImageIcon = new ImageIcon(combineImage);
            labels[i][j].setIcon(combinedImageIcon);
            //throw new IOException("nn");
          } catch (IOException e) {
            e.printStackTrace();
          }

        }
      }
    }


  }

  /**
   * Add player's visited locations(player may have already visited before).
   */
  void addNewLocation(Location location, int x, int y) {
    if (location == null) {
      throw new IllegalArgumentException("Parameters can not be null.");
    }
    if (visitedLocations[x][y] == null) {
      visitedLocations[x][y] = location;
    }

  }

  /**
   * Update player's visited locations.
   */
  void updateVisitedLocation() {
    Location[][] currentDungeon = model.getLocations();
    int x = currentDungeon.length;
    int y = currentDungeon[0].length;
    //traverse the dungeon to update visited locations
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        //player has visited this location, available to player
        if (visitedLocations[i][j] != null) {
          visitedLocations[i][j] = currentDungeon[i][j];
        }
      }
    }
  }

  /**
   * Reset panel display when new game starts.
   */
  void resetPanel() {
    //remove all components on this panel
    this.removeAll();

    Location[][] dungeon = this.model.getLocations();
    int x = dungeon.length;
    int y = dungeon[0].length;
    visitedLocations = new Location[x][y];
    //initialize visitedLocations
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        visitedLocations[i][j] = null;
      }
    }

    Player player = this.model.getPlayer();
    int startX = player.getPosition().getX();
    int startY = player.getPosition().getY();
    //at the beginning, only the start cave is available.
    visitedLocations[startX][startY] = model.getLocations()[startX][startY];
    labels = new JLabel[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        //JLabel label = new JLabel(i + "," + j, JLabel.CENTER);
        JLabel label = new JLabel();
        labels[i][j] = label;
        label.setBounds(j * SIZE, i * SIZE, SIZE, SIZE);
        this.add(label);
      }
    }
  }

  //get url according to different location
  private URL getLocationUrl(int i, int j) {
    //get location's door status
    boolean northDoor = visitedLocations[i][j].getNorthDoor();
    boolean southDoor = visitedLocations[i][j].getSouthDoor();
    boolean eastDoor = visitedLocations[i][j].getEastDoor();
    boolean westDoor = visitedLocations[i][j].getWestDoor();
    //north door is open
    if (northDoor) {
      if (southDoor && eastDoor && westDoor) {
        //NSEW
        return DungeonPanel.class.getResource("icons/NSEW.png");
      } else if (southDoor && eastDoor) {
        //NSE
        return DungeonPanel.class.getResource("icons/NSE.png");
      } else if (southDoor && westDoor) {
        //NSW
        return DungeonPanel.class.getResource("icons/NSW.png");
      } else if (westDoor && eastDoor) {
        //NEW
        return DungeonPanel.class.getResource("icons/NEW.png");
      } else if (southDoor) {
        //NS
        return DungeonPanel.class.getResource("icons/NS.png");
      } else if (eastDoor) {
        //NE
        return DungeonPanel.class.getResource("icons/NE.png");
      } else if (westDoor) {
        //NW
        return DungeonPanel.class.getResource("icons/NW.png");
      } else {
        //N
        return DungeonPanel.class.getResource("icons/N.png");
      }
      //south door is open
    } else if (southDoor) {
      if (westDoor && eastDoor) {
        //SEW
        return DungeonPanel.class.getResource("icons/SEW.png");
      } else if (eastDoor) {
        //SE
        return DungeonPanel.class.getResource("icons/SE.png");
      } else if (westDoor) {
        //SW
        return DungeonPanel.class.getResource("icons/SW.png");
      } else {
        //S
        return DungeonPanel.class.getResource("icons/S.png");
      }
      //east door is open
    } else if (eastDoor) {
      if (westDoor) {
        //EW
        return DungeonPanel.class.getResource("icons/EW.png");
      } else {
        //E
        return DungeonPanel.class.getResource("icons/E.png");
      }
    } else {
      //W
      return DungeonPanel.class.getResource("icons/W.png");
    }

  }

  //get stench url
  private URL getStenchUrl(int i, int j) {
    if (getPlayerUrl(i, j) != null) {
      //no smell
      if (model.getSmellLevel() == 0) {
        return null;
      } else if (model.getSmellLevel() == 1) {
        //level 1 stench
        return DungeonPanel.class.getResource("icons/stench01.png");
      } else {
        //level 2 stench
        return DungeonPanel.class.getResource("icons/stench02.png");
      }
    }
    return null;
  }

  //get treasure URL
  private URL getTreasureUrl(int i, int j) {
    //no treasure
    if (visitedLocations[i][j].getTreasure().isEmpty()) {
      return null;
    } else {
      //have treasure, return treasure image path
      return DungeonPanel.class.getResource("icons/ruby.png");
    }
  }

  //get arrow url
  private URL getArrowUrl(int i, int j) {
    //no arrow
    if (visitedLocations[i][j].getArrow().isEmpty()) {
      return null;
    } else {
      //have arrows, return treasure image path
      return DungeonPanel.class.getResource("icons/arrow-white.png");
    }
  }

  //get otyugh url
  private URL getOtyughUrl(int i, int j) {
    //no arrow
    if (visitedLocations[i][j].getMonster().isEmpty()) {
      return null;
    } else {
      //have arrows, return treasure image path
      return DungeonPanel.class.getResource("icons/otyugh.png");
    }
  }

  //get player url
  private URL getPlayerUrl(int i, int j) {
    Player player = this.model.getPlayer();
    int startX = player.getPosition().getX();
    int startY = player.getPosition().getY();
    //player's current location
    if (i == startX && j == startY) {
      return DungeonPanel.class.getResource("icons/player.png");
    } else {
      //no player
      return null;
    }
  }


  //combine different images
  private Image combineImage(URL locationPath, URL treasurePath, URL arrowPath, URL playerPath,
                             URL stenchPath, URL otyughPath) throws IOException {

    //Creating the final image of width and height that
    //will match the final image. The image will be RGB+Alpha
    BufferedImage finalImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
    Graphics finalImageGraphics = finalImage.getGraphics();

    //draw location
    BufferedImage locationImage = ImageIO.read(locationPath);
    Image resizedLocationImage = locationImage.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);
    finalImageGraphics.drawImage(resizedLocationImage, 0, 0, null);

    //draw stench
    if (stenchPath != null) {
      BufferedImage stenchImage = ImageIO.read(stenchPath);
      Image resizedStenchImage = stenchImage.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);
      finalImageGraphics.drawImage(resizedStenchImage, 0, 0, null);
    }

    //draw otyugh
    if (otyughPath != null) {
      BufferedImage otyughImage = ImageIO.read(otyughPath);
      Image resizedOtyughImage = otyughImage.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
      finalImageGraphics.drawImage(resizedOtyughImage, 30, 50, null);
    }

    //draw player
    if (playerPath != null) {
      BufferedImage playerImage = ImageIO.read(playerPath);
      Image resizedPlayerImage = playerImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
      finalImageGraphics.drawImage(resizedPlayerImage, 30, 30, null);
    }

    //draw treasure
    if (treasurePath != null) {
      BufferedImage treasureImage = ImageIO.read(treasurePath);
      Image resizedTreasureImage = treasureImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
      finalImageGraphics.drawImage(resizedTreasureImage, 50, 30, null);
    }

    //draw arrow
    if (arrowPath != null) {
      BufferedImage arrowImage = ImageIO.read(arrowPath);
      Image resizedArrowImage = arrowImage.getScaledInstance(50, 10, Image.SCALE_DEFAULT);
      finalImageGraphics.drawImage(resizedArrowImage, 30, 80, null);
    }


    //release
    finalImageGraphics.dispose();

    return finalImage;

  }


}
