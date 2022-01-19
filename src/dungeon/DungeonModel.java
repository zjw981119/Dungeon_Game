package dungeon;

import location.Cave;
import location.Location;
import location.Point2D;
import location.Tunnel;
import monster.Monster;
import monster.Otyugh;
import player.DungeonPlayer;
import player.Player;
import treasure.Diamond;
import treasure.Ruby;
import treasure.Sapphire;
import treasure.Treasure;
import weapon.Arrow;
import weapon.CrookedArrow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


/**
 * Dungeon start cave position(Point2D), end cave position(Point2D)
 * and 2-D locations.
 * The dungeon can place a player at the start cave.
 */
public class DungeonModel implements Dungeon {

  private Point2D start;
  private Point2D end;
  private Location[][] locations;
  private Player player;
  private static final int MIN_DISTANCE = 5;
  private static final double POSSIBILITY = 0.5;

  /**
   * Create a dungeon object using given size, wrapping condition and
   * treasure percentage.
   *
   * @param x                 the breadth size of dungeon
   * @param y                 the length size of dungeon
   * @param wrapping          the dungeon will be created as a wrapping dungeon or not
   *                          wrapping == true ==> creating a wrapping dungeon
   * @param interconnectivity the interconnectivity of the dungeon object
   * @param percentage        the percentage of caves in dungeon that contains treasure
   * @param monsterAmount     the number of caves that contain one Otyugh
   */
  public DungeonModel(int x, int y, boolean wrapping, int interconnectivity,
                      int percentage, int monsterAmount) {
    if (x < 5 || y < 5) {
      throw new IllegalArgumentException("Dungeon size should be greater than 5 * 5.");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity shouldn't be negative.");
    }
    if (percentage < 20 || percentage > 100) {
      throw new IllegalArgumentException("Percentage should be in the range [20,100].");
    }
    if (monsterAmount < 1) {
      throw new IllegalArgumentException("Monster number should be positive.");
    }
    locations = new Location[x][y];
    if (wrapping) {
      // interconnectivity constrain for wrapping dungeon
      // initial edges: xy - 1
      // total edges: 2xy
      // extra edges: xy + 1
      if (interconnectivity > (x * y + 1)) {
        throw new IllegalArgumentException("Interconnectivity can not "
                + "be larger than " + (x * y + 1));
      }
      //create a wrapping dungeon
      createWrappingDungeon(x, y, interconnectivity, percentage);
    } else {
      // interconnectivity constrain for non-wrapping dungeon
      // initial edges: xy - 1
      // total edges: 2xy  - (x + y)
      // extra edges: xy - (x + y) + 1
      int extraEdges = x * y - (x + y) + 1;
      if (interconnectivity > extraEdges) {
        throw new IllegalArgumentException("Interconnectivity can not "
                + "be larger than " + extraEdges);
      }
      //create a non-wrapping dungeon
      createNonWrappingDungeon(x, y, interconnectivity, percentage);
    }


    //add treasure to caves
    addTreasure(x, y, percentage);

    //add arrows to dungeon
    addArrow(x, y, percentage);

    //set start and end cave
    start = new Point2D(0, 0);
    end = new Point2D(0, 0);
    selectStartAndEnd(x, y);
    if (end.getX() == 0 && end.getY() == 0) {
      throw new IllegalStateException("The generated dungeon can not "
              + "set start cave and end cave with path length beyond 4.");
    }

    //create a player object
    player = new DungeonPlayer("player1");
    //set position
    placePlayer();

    //add monsters to caves
    addMonster(x, y, monsterAmount);
  }

  @Override
  public Point2D getStartPosition() {
    return new Point2D(start.getX(), start.getY());
  }

  @Override
  public Point2D getEndPosition() {
    return new Point2D(end.getX(), end.getY());
  }

  @Override
  public int getSmellLevel() {
    Location[][] tmpLocations = this.getLocations();
    int x = tmpLocations.length;
    int y = tmpLocations[0].length;
    int monsterAmount = 0;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        List<Monster> tmpList = locations[i][j].getMonster();
        //calculate the minimum distance between this location and player's current location
        int distance = minDistance(player.getPosition(), new Point2D(i, j));
        // the location has a monster
        if (!tmpList.isEmpty()) {
          //more pungent smell
          if (distance == 1) {
            return 2;
          } else if (distance == 2) {
            monsterAmount++;
          }
        }
      }
    }
    //more pungent smell
    if (monsterAmount >= 2) {
      return 2;
      //less pungent smell
    } else if (monsterAmount == 1) {
      return 1;
      //no smell
    } else {
      return 0;
    }
  }


  @Override
  public String getPlayerDescription() {
    String str = "";
    //str += "Player name: " + player.getName() + "\n";
    List<Treasure> treasureList = player.getBag();
    if (treasureList.size() != 0) {
      int diamondNum = 0;
      int rubyNum = 0;
      int sapphireNum = 0;
      //calculate the number of treasure
      for (Treasure t : treasureList) {
        if (t instanceof Diamond) {
          diamondNum++;
        } else if (t instanceof Ruby) {
          rubyNum++;
        } else {
          sapphireNum++;
        }
      }
      str += "You have " + diamondNum + " diamonds, "
              + rubyNum + " rubies\nand " + sapphireNum + " sapphires." + "\n";
    }
    List<Arrow> arrowList = player.getQuiver();
    str += "You have " + arrowList.size() + " arrows." + "\n";
    return str;
  }

  @Override
  public String getLocationDescription() {
    String str = "";
    //get player's current location
    Point2D position = player.getPosition();
    int indexX = position.getX();
    int indexY = position.getY();
    if (locations[indexX][indexY] instanceof Cave) {
      str += "You are in a cave" + "\n";
    } else {
      str += "You are in a tunnel" + "\n";
    }
    List<Treasure> treasureList = locations[indexX][indexY].getTreasure();
    if (!treasureList.isEmpty()) {
      str += "You find";
      for (Treasure t : treasureList) {
        str += " 1 " + t.getName();
      }
      str += "\n";
    }
    List<Arrow> arrowList = locations[indexX][indexY].getArrow();
    if (!arrowList.isEmpty()) {
      if (arrowList.size() == 1) {
        str += "You find 1 arrow\n";
      } else {
        str += "You find 2 arrows\n";
      }

    }
    str += "Available move:";
    if (locations[indexX][indexY].getNorthDoor()) {
      str += " north";
    }
    if (locations[indexX][indexY].getSouthDoor()) {
      str += " south";
    }
    if (locations[indexX][indexY].getEastDoor()) {
      str += " east";
    }
    if (locations[indexX][indexY].getWestDoor()) {
      str += " west";
    }
    return str;
  }

  @Override
  public Location[][] getLocations() {
    int x = locations.length;
    int y = locations[0].length;
    Location[][] tmpLocations = new Location[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        boolean northDoor = locations[i][j].getNorthDoor();
        boolean southDoor = locations[i][j].getSouthDoor();
        boolean eastDoor = locations[i][j].getEastDoor();
        boolean westDoor = locations[i][j].getWestDoor();
        //Add tunnel
        if (locations[i][j] instanceof Tunnel) {
          tmpLocations[i][j] = new Tunnel(northDoor, southDoor, eastDoor, westDoor);
          //Add cave
        } else {
          tmpLocations[i][j] = new Cave(northDoor, southDoor, eastDoor, westDoor);
          //add treasure
          for (Treasure t : locations[i][j].getTreasure()) {
            tmpLocations[i][j].addTreasure(t);
          }
          //add monster(defensive copies)
          for (Monster m : locations[i][j].getMonster()) {
            String name = m.getName();
            int health = m.getHealth();
            if (health == 2) {
              tmpLocations[i][j].addMonster(new Otyugh(name));
            } else {
              Otyugh tmpMonster = new Otyugh(name);
              tmpMonster.reduceHealth();
              tmpLocations[i][j].addMonster(tmpMonster);
            }
          }
        }

        //add arrow
        for (Arrow a : locations[i][j].getArrow()) {
          tmpLocations[i][j].addArrow(a);
        }
      }
    }
    return tmpLocations;
  }

  @Override
  public void move(int direction) {
    if (direction < 1 || direction > 4) {
      throw new IllegalArgumentException("Move direction must be 1, 2, 3 or 4");
    }

    //get player's current location
    Point2D position = player.getPosition();
    int indexX = position.getX();
    int indexY = position.getY();
    // go north
    if (direction == 1) {
      //check north door, if available, go and change player's position
      if (locations[indexX][indexY].getNorthDoor()) {
        //player move
        if (indexX == 0) {
          //wrapping to last line
          player.setPosition(locations.length - 1, indexY);
        } else {
          player.setPosition(indexX - 1, indexY);
        }
        // no available way
      } else {
        throw new IllegalArgumentException("Player can not go north at this location.");
      }
      //go south
    } else if (direction == 2) {
      //check south door, if available, go and change player's position
      if (locations[indexX][indexY].getSouthDoor()) {
        //player move
        if (indexX == locations.length - 1) {
          //wrapping to first line
          player.setPosition(0, indexY);
        } else {
          player.setPosition(indexX + 1, indexY);
        }
        // no available way
      } else {
        throw new IllegalArgumentException("Player can not go south at this location.");
      }
      //go east
    } else if (direction == 3) {
      //check east door, if available, go and change player's position
      if (locations[indexX][indexY].getEastDoor()) {
        //player move
        if (indexY == locations[0].length - 1) {
          //wrapping to first column
          player.setPosition(indexX, 0);
        } else {
          player.setPosition(indexX, indexY + 1);
        }
        // no available way
      } else {
        throw new IllegalArgumentException("Player can not go east at this location.");
      }
      //go west
    } else {
      //check west door, if available, go and change player's position
      if (locations[indexX][indexY].getWestDoor()) {
        //player move
        if (indexY == 0) {
          //wrapping to last column
          player.setPosition(indexX, locations[0].length - 1);
        } else {
          player.setPosition(indexX, indexY - 1);
        }
        // no available way
      } else {
        throw new IllegalArgumentException("Player can not go west at this location.");
      }
    }

  }

  @Override
  public boolean shoot(int direction, int distance) {
    if (direction < 1 || direction > 4 || distance < 1 || distance > 4) {
      throw new IllegalArgumentException("Arguments value out of range");
    }
    // no arrow
    if (player.getQuiver().isEmpty()) {
      throw new IllegalStateException("No arrow");
    }
    //get player's current location
    Point2D position = player.getPosition();
    int x = position.getX();
    int y = position.getY();
    //get an arrow
    Arrow arrow = player.getQuiver().get(0);
    player.reduceArrow();
    arrow.setPosition(x, y);

    while (distance > 0) {
      //shoot north
      if (direction == 1) {
        //check north door, if available, arrow can go through it
        if (locations[x][y].getNorthDoor()) {
          //arrow move
          if (x == 0) {
            //wrapping to last line
            x = locations.length - 1;
          } else {
            x = x - 1;
          }
          arrow.setPosition(x, y);
          if (locations[x][y] instanceof Cave) {
            //update distance
            distance--;
          } else {
            //arrow go through a tunnel, change direction
            direction = changeDirection(locations[x][y], direction);
          }
        } else {
          //hit the wall, arrow stops
          return false;
        }
        //shoot south
      } else if (direction == 2) {
        //check south door, if available, arrow can go through it
        if (locations[x][y].getSouthDoor()) {
          //arrow move
          if (x == locations.length - 1) {
            //wrapping to first line
            x = 0;
          } else {
            x = x + 1;
          }
          arrow.setPosition(x, y);
          if (locations[x][y] instanceof Cave) {
            //update distance
            distance--;
          } else {
            //arrow go through a tunnel, change direction
            direction = changeDirection(locations[x][y], direction);
          }
        } else {
          //hit the wall, arrow stops
          return false;
        }
        //shoot east
      } else if (direction == 3) {
        //check east door, if available, arrow can go through it
        if (locations[x][y].getEastDoor()) {
          //arrow move
          if (y == locations[0].length - 1) {
            //wrapping to first column
            y = 0;
          } else {
            y = y + 1;
          }
          arrow.setPosition(x, y);
          if (locations[x][y] instanceof Cave) {
            //update distance
            distance--;
          } else {
            //arrow go through a tunnel, change direction
            direction = changeDirection(locations[x][y], direction);
          }
        } else {
          //hit the wall, arrow stops
          return false;
        }
        //shoot west
      } else {
        //check west door, if available, arrow can go through it
        if (locations[x][y].getWestDoor()) {
          //arrow move
          if (y == 0) {
            //wrapping to last column
            y = locations[0].length - 1;
          } else {
            y = y - 1;
          }
          arrow.setPosition(x, y);
          if (locations[x][y] instanceof Cave) {
            //update distance
            distance--;
          } else {
            //arrow go through a tunnel, change direction
            direction = changeDirection(locations[x][y], direction);
          }
        } else {
          //hit the wall, arrow stops
          return false;
        }
      }
    }

    //shoot the monster?
    List<Monster> monsterList = locations[x][y].getMonster();
    //no monster at final place
    if (monsterList.isEmpty()) {
      return false;
      //shoot the monster
    } else {
      //reduce the health of monster
      Monster monster = locations[x][y].getMonster().get(0);
      monster.reduceHealth();
      //monster is died, remove it
      if (monster.getHealth() == 0) {
        locations[x][y].removeMonster();
      }
      return true;
    }
  }

  @Override
  public void pickTreasure() {
    //get player's current location
    Point2D position = player.getPosition();
    int indexX = position.getX();
    int indexY = position.getY();
    List<Treasure> treasureList = locations[indexX][indexY].getTreasure();
    //pick treasure
    for (Treasure t : treasureList) {
      player.addBag(t);
    }
    //remove treasure at current location
    locations[indexX][indexY].removeTreasure();

  }

  @Override
  public void pickArrow() {
    //get player's current location
    Point2D position = player.getPosition();
    int indexX = position.getX();
    int indexY = position.getY();
    List<Arrow> arrowList = locations[indexX][indexY].getArrow();
    //pick up all arrows
    for (Arrow a : arrowList) {
      player.addQuiver(a);
    }
    //remove arrow at current location
    locations[indexX][indexY].removeArrow();
  }

  @Override
  public int combat() {
    Point2D position = player.getPosition();
    int x = position.getX();
    int y = position.getY();
    //no monster
    if (locations[x][y].getMonster().isEmpty()) {
      return 0;
      //monster exists
    } else {
      Monster monster = locations[x][y].getMonster().get(0);
      //injured monster have 50% possibility to eat player
      if (monster.getHealth() == 1) {
        Random random = new Random();
        if (random.nextDouble() < POSSIBILITY) {
          //player is able to escape
          return 2;
        }
      }
      //player is eaten by monster
      player.reduceLife();
      return 1;
    }
  }

  @Override
  public void resetPlayer() {
    player = new DungeonPlayer("player");
    placePlayer();
  }

  @Override
  public void resetStartCave(Point2D position) {
    start = new Point2D(position.getX(), position.getY());
  }

  @Override
  public void resetEndCave(Point2D position) {
    end = new Point2D(position.getX(), position.getY());
  }

  @Override
  public void resetDungeon(Location[][] locations) {
    int x = locations.length;
    int y = locations[0].length;
    this.locations = new Location[x][y];
    //reset locations
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        this.locations[i][j] = locations[i][j];
      }
    }
  }

  @Override
  public boolean isGameOver() {
    //player reach the end alive or player is dead
    int x = player.getPosition().getX();
    int y = player.getPosition().getY();
    //player reach the end alive with no monster: win
    //player died: lose
    return (player.isAlive() && x == end.getX() && y == end.getY()
            && locations[x][y].getMonster().isEmpty())
            || !player.isAlive();
  }

  @Override
  public List<String> getDungeon() {
    List<String> description = new ArrayList<>();
    String str = "";
    str += "Start cave: [" + start.getX() + ", " + start.getY() + "]";
    description.add(str);
    str = "";
    str += "End cave: [" + end.getX() + ", " + end.getY() + "]";
    description.add(str);
    int lineNum = locations.length;
    int columnNum = locations[0].length;
    for (int i = 0; i < lineNum; i++) {
      for (int j = 0; j < columnNum; j++) {
        str = "";
        //if cave
        if (locations[i][j] instanceof Cave) {
          str = "Cave[" + i + ", " + j + "]" + "\n";
          //description of treasure
          if (locations[i][j].getTreasure().size() > 0) {
            str += "Treasure information: " + "\n";
            for (Treasure s : locations[i][j].getTreasure()) {
              str += s.getName() + "\n";
            }
          }
        } else {
          //if tunnel
          str = "Tunnel[" + i + ", " + j + "]" + "\n";
        }

        str += "Doors status: " + "\n";
        //status of doors
        if (locations[i][j].getNorthDoor()) {
          str += "North door is open" + "\n";
        }
        if (locations[i][j].getSouthDoor()) {
          str += "South door is open" + "\n";
        }
        if (locations[i][j].getEastDoor()) {
          str += "East door is open" + "\n";
        }
        if (locations[i][j].getWestDoor()) {
          str += "West door is open" + "\n";
        }
        description.add(str);
      }
    }
    return description;
  }

  @Override
  public Player getPlayer() {
    String name = player.getName();
    //defensive copies
    Player refPlayer = new DungeonPlayer(name);
    for (int i = 1; i <= 3; i++) {
      refPlayer.reduceArrow();
    }
    Point2D position = player.getPosition();
    refPlayer.setPosition(position.getX(), position.getY());
    List<Treasure> bag = player.getBag();
    for (Treasure t : bag) {
      refPlayer.addBag(t);
    }
    List<Arrow> quiver = player.getQuiver();
    for (Arrow a : quiver) {
      refPlayer.addQuiver(a);
    }
    int life = player.getLife();
    if (life == 0) {
      refPlayer.reduceLife();
    }
    return refPlayer;
  }

  //create a non wrapping dungeon
  private void createNonWrappingDungeon(int x, int y, int interconnectivity, int percent) {
    // Create initial dungeon whose interconnectivity is 0
    // 0 ---- 1 ---- 2 ---- 3
    // |
    // 4 ---- 5 ---- 6 ---- 7
    // |
    // 8 ---- 9 ---- 10 ---- 11
    //initialize first column
    locations[0][0] = new Tunnel(false, true, true, false);
    locations[x - 1][0] = new Tunnel(true, false, true, false);
    for (int i = 1; i < x - 1; i++) {
      locations[i][0] = new Cave(true, true, true, false);
    }
    //initialize middle columns
    for (int i = 0; i < x; i++) {
      for (int j = 1; j < y - 1; j++) {
        locations[i][j] = new Tunnel(false, false, true, true);
      }
    }
    //initialize last column
    for (int i = 0; i < x; i++) {
      locations[i][y - 1] = new Cave(false, false, false, true);
    }
    //add extra edges for non-wrapping dungeon
    addNonWrappingExtraEdges(x, y, interconnectivity);
  }

  //create a wrapping dungeon
  private void createWrappingDungeon(int x, int y, int interconnectivity, int percent) {
    // Create initial dungeon whose interconnectivity is 0
    // ---- 0     1 ---- 2 ---- 3 ----
    //      |
    // ---- 4     5 ---- 6 ---- 7 ----
    //      |
    // ---- 8     9 ---- 10 ---- 11 ----
    //initialize first column
    locations[0][0] = new Tunnel(false, true, false, true);
    locations[x - 1][0] = new Tunnel(true, false, false, true);
    for (int i = 1; i < x - 1; i++) {
      locations[i][0] = new Cave(true, true, false, true);
    }

    //initialize second column
    for (int i = 0; i < x; i++) {
      locations[i][1] = new Cave(false, false, true, false);
    }

    //initialize 3 to y column
    for (int i = 0; i < x; i++) {
      for (int j = 2; j < y; j++) {
        locations[i][j] = new Tunnel(false, false, true, true);
      }
    }

    //add extra edges for wrapping dungeon
    addWrappingExtraEdges(x, y, interconnectivity);
  }

  //add extra edges to non-wrapping dungeon
  private void addNonWrappingExtraEdges(int x, int y, int interconnectivity) {
    List<String> extraEdges = new ArrayList<>();
    //store all extra edges
    for (int i = 0; i < x - 1; i++) {
      for (int j = 1; j < y; j++) {
        // "0,1-1,1" represents locations[0,1] and locations[1,1] is connected to each other
        // the extra edge will always be vertical connection
        extraEdges.add(i + "," + j + "-" + (i + 1) + "," + j);
      }
    }
    //check the interconnectivity
    if (interconnectivity > extraEdges.size()) {
      throw new IllegalArgumentException(
              "This dungeon with this size cannot have this interconnectivity");
    }

    while (interconnectivity > 0) {
      //Select edges randomly to increase interconnectivity
      Random number = new Random();
      int index = number.nextInt(extraEdges.size());
      String connect = extraEdges.get(index);
      //parse connect string and update status of related doors(add the edge)
      //"0,1-1,1" ==> "0,1","1,1"
      String[] locationIndex = connect.split("-");
      //"0,1" ==> "0","1"
      String[] formerLocation = locationIndex[0].split(",");
      int indexX = Integer.parseInt(formerLocation[0]);
      int indexY = Integer.parseInt(formerLocation[1]);
      //update status of doors in two connected locations
      locations[indexX][indexY].setSouthDoor(true);
      locations[indexX + 1][indexY].setNorthDoor(true);
      //remove edges that already added to dungeon
      extraEdges.remove(index);
      interconnectivity -= 1;
    }

    //update dungeon
    //these locations were tunnels
    for (int i = 0; i < x; i++) {
      for (int j = 1; j < y - 1; j++) {
        int doorNum = 0;
        //count the available doors at each location
        boolean northDoor = false;
        if (locations[i][j].getNorthDoor()) {
          doorNum++;
          northDoor = true;
        }
        boolean southDoor = false;
        if (locations[i][j].getSouthDoor()) {
          doorNum++;
          southDoor = true;
        }
        boolean eastDoor = false;
        if (locations[i][j].getEastDoor()) {
          doorNum++;
          eastDoor = true;
        }
        boolean westDoor = false;
        if (locations[i][j].getWestDoor()) {
          doorNum++;
          westDoor = true;
        }
        //check number of doors at current location
        //if extra edges were added, update dungeon
        if (doorNum != 2) {
          //remove object
          locations[i][j] = null;
          //update
          locations[i][j] = new Cave(northDoor, southDoor, eastDoor, westDoor);
        }
      }
    }

    //these locations were caves(only 1 door)
    for (int i = 0; i < x; i++) {
      int doorNum = 0;
      //count the available doors at each location
      boolean northDoor = false;
      if (locations[i][y - 1].getNorthDoor()) {
        doorNum++;
        northDoor = true;
      }
      boolean southDoor = false;
      if (locations[i][y - 1].getSouthDoor()) {
        doorNum++;
        southDoor = true;
      }
      boolean eastDoor = false;
      if (locations[i][y - 1].getEastDoor()) {
        doorNum++;
        eastDoor = true;
      }
      boolean westDoor = false;
      if (locations[i][y - 1].getWestDoor()) {
        doorNum++;
        westDoor = true;
      }
      //check number of doors at current location
      //if extra edges were added, update dungeon
      //if doorNum == 2, the cave become tunnel
      if (doorNum == 2) {
        //remove object
        locations[i][y - 1] = null;
        //update
        locations[i][y - 1] = new Tunnel(northDoor, southDoor, eastDoor, westDoor);
      } else if (doorNum > 2) {
        //extra edges added, set the status of doors in current cave
        locations[i][y - 1].setNorthDoor(northDoor);
        locations[i][y - 1].setSouthDoor(southDoor);
        locations[i][y - 1].setEastDoor(eastDoor);
        locations[i][y - 1].setWestDoor(westDoor);
      }
    }
  }

  //add extra edges for non-wrapping dungeon
  private void addWrappingExtraEdges(int x, int y, int interconnectivity) {
    List<String> extraEdges = new ArrayList<>();
    //store all extra edges
    for (int i = 0; i < x - 1; i++) {
      for (int j = 0; j < y; j++) {
        // "0,1-1,1" represents locations[0,1] and locations[1,1] is connected to each other
        if (j == 0) {
          //add horizontal connection for fist column
          extraEdges.add(i + "," + j + "-" + i + "," + (j + 1));
          //last line should add edge to south room(wrapping to first line)
          if (i == x - 1) {
            extraEdges.add(i + "," + j + "-" + 0 + "," + j);
          }
          //from column 2 to y, all vertical edges
        } else {
          if (i != x - 1) {
            extraEdges.add(i + "," + j + "-" + (i + 1) + "," + j);
          } else {
            //last line, add wrapping edge
            extraEdges.add(i + "," + j + "-" + 0 + "," + j);
          }
        }
      }
    }

    //check the interconnectivity
    if (interconnectivity > extraEdges.size()) {
      throw new IllegalArgumentException(
              "This dungeon with this size cannot have this interconnectivity");
    }

    //update status of doors
    while (interconnectivity > 0) {
      //Select edges randomly to increase interconnectivity
      Random number = new Random();
      int index = number.nextInt(extraEdges.size());
      String connect = extraEdges.get(index);
      //parse connect string and update status of related doors(add the edge)
      //"0,1-1,1" ==> "0,1","1,1"
      String[] locationIndex = connect.split("-");
      //"0,1" ==> "0","1", split two rooms location
      String[] formerLocation = locationIndex[0].split(",");
      String[] latterLocation = locationIndex[1].split(",");

      int formerIndexX = Integer.parseInt(formerLocation[0]);
      int formerIndexY = Integer.parseInt(formerLocation[1]);
      int latterIndexX = Integer.parseInt(latterLocation[0]);
      int latterIndexY = Integer.parseInt(latterLocation[1]);

      //horizontal connection
      if (formerIndexX == latterIndexX) {
        locations[formerIndexX][formerIndexY].setEastDoor(true);
        locations[latterIndexX][latterIndexY].setWestDoor(true);
      } else {
        //vertical connection
        locations[formerIndexX][formerIndexY].setSouthDoor(true);
        locations[latterIndexX][latterIndexY].setNorthDoor(true);
      }
      //remove edges that already added to dungeon
      extraEdges.remove(index);
      interconnectivity -= 1;
    }

    //update dungeon
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        int doorNum = 0;
        //count the available doors at each location
        boolean northDoor = false;
        if (locations[i][j].getNorthDoor()) {
          doorNum++;
          northDoor = true;
        }
        boolean southDoor = false;
        if (locations[i][j].getSouthDoor()) {
          doorNum++;
          southDoor = true;
        }
        boolean eastDoor = false;
        if (locations[i][j].getEastDoor()) {
          doorNum++;
          eastDoor = true;
        }
        boolean westDoor = false;
        if (locations[i][j].getWestDoor()) {
          doorNum++;
          westDoor = true;
        }

        //remove object
        locations[i][j] = null;

        //check number of doors at current location
        //update dungeon
        if (doorNum != 2) {
          //update
          locations[i][j] = new Cave(northDoor, southDoor, eastDoor, westDoor);
        } else {
          //only two doors, tunnel
          locations[i][j] = new Tunnel(northDoor, southDoor, eastDoor, westDoor);
        }
      }
    }
  }

  //place player to start cave
  private void placePlayer() {
    Point2D startPosition = this.getStartPosition();
    this.player.setPosition(startPosition.getX(), startPosition.getY());
  }

  //add treasure to caves
  private void addTreasure(int x, int y, int percentage) {
    List<Point2D> caveIndex = new ArrayList<>();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (locations[i][j] instanceof Cave) {
          caveIndex.add(new Point2D(i, j));
        }
      }
    }

    int objectCaveNum = (int) ((caveIndex.size() * (percentage * 0.01)) + 1);
    if (objectCaveNum > caveIndex.size()) {
      objectCaveNum = caveIndex.size();
    }
    //add treasure to cave
    Random number = new Random();

    //add treasure
    while (objectCaveNum > 0) {
      //get random cave index
      int randomIndex = number.nextInt(caveIndex.size());
      int indexX = caveIndex.get(randomIndex).getX();
      int indexY = caveIndex.get(randomIndex).getY();
      //add random treasure
      int treasureNum = number.nextInt(3) + 1;
      if (treasureNum == 1) {
        Diamond diamond = new Diamond("Diamond");
        locations[indexX][indexY].addTreasure(diamond);
      } else if (treasureNum == 2) {
        Diamond diamond = new Diamond("Diamond");
        Ruby ruby = new Ruby("Ruby");
        locations[indexX][indexY].addTreasure(diamond);
        locations[indexX][indexY].addTreasure(ruby);
      } else {
        Diamond diamond = new Diamond("Diamond");
        Ruby ruby = new Ruby("Ruby");
        Sapphire sapphire = new Sapphire("Sapphire");
        locations[indexX][indexY].addTreasure(diamond);
        locations[indexX][indexY].addTreasure(ruby);
        locations[indexX][indexY].addTreasure(sapphire);
      }
      //remove this cave
      caveIndex.remove(randomIndex);
      objectCaveNum--;
    }

  }

  //add arrows to dungeon
  private void addArrow(int x, int y, int percentage) {
    List<Point2D> locationList = new ArrayList<>();
    //save all positions
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        locationList.add(new Point2D(i, j));
      }
    }
    //calculate the objective number of locations which should be assigned arrows
    int objectLocNum = (int) ((locationList.size() * (percentage * 0.01)) + 1);
    if (objectLocNum > locationList.size()) {
      objectLocNum = locationList.size();
    }
    //add arrows to dungeon
    Random number = new Random();
    while (objectLocNum > 0) {
      //get random location index
      int randomIndex = number.nextInt(locationList.size());
      int indexX = locationList.get(randomIndex).getX();
      int indexY = locationList.get(randomIndex).getY();
      //add random arrow
      int arrowAmount = number.nextInt(2) + 1;
      //add one arrow
      if (arrowAmount == 1) {
        Arrow arrow = new CrookedArrow(new Point2D(indexX, indexY));
        locations[indexX][indexY].addArrow(arrow);
      } else {
        //add two arrows
        Arrow arrow1 = new CrookedArrow(new Point2D(indexX, indexY));
        Arrow arrow2 = new CrookedArrow(new Point2D(indexX, indexY));
        locations[indexX][indexY].addArrow(arrow1);
        locations[indexX][indexY].addArrow(arrow2);
      }
      //remove this location
      locationList.remove(randomIndex);
      objectLocNum--;
    }
  }

  //add monsters to caves
  private void addMonster(int x, int y, int amount) {
    List<Point2D> caveList = new ArrayList<>();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (locations[i][j] instanceof Cave) {
          //skip the start and end cave
          if ((i == end.getX() && j == end.getY())
                  || (i == start.getX() && j == start.getY())) {
            continue;
          }
          caveList.add(new Point2D(i, j));
        }
      }
    }
    //invalid monster amount
    if (amount - 1 > caveList.size()) {
      throw new IllegalArgumentException("Invalid monster amount");
    }

    //add one monster to end cave
    Point2D end = getEndPosition();
    locations[end.getX()][end.getY()].addMonster(new Otyugh("Otyugh1"));
    amount--;

    //randomly add monster to cave
    Random number = new Random();
    int monsterNum = 2;
    while (amount > 0) {
      //get random cave index
      int randomIndex = number.nextInt(caveList.size());
      int indexX = caveList.get(randomIndex).getX();
      int indexY = caveList.get(randomIndex).getY();
      //add monster
      Otyugh otyugh = new Otyugh("Otyugh" + monsterNum);
      locations[indexX][indexY].addMonster(otyugh);
      monsterNum++;
      //remove cave
      caveList.remove(randomIndex);
      amount--;
    }

  }

  //find the minimum distance between start and end
  //BFS
  private int minDistance(Point2D start, Point2D end) {
    Queue<Point2D> que = new LinkedList<>();
    //initialize distance from start
    int[][] distance = new int[locations.length][locations[0].length];
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[0].length; j++) {
        distance[i][j] = 1000000;
      }
    }
    que.offer(start);
    //start distance = 0
    distance[start.getX()][start.getY()] = 0;
    while (!que.isEmpty()) {
      Point2D p = que.poll();
      //System.out.println(p.getX() + ", " + p.getY());
      //already calculate the distance from start to end
      if (p.getX() == end.getX() && p.getY() == end.getY()) {
        break;
      }
      //explore all possible move from current location
      int nx = p.getX();
      int ny = p.getY();
      //go north
      if (locations[p.getX()][p.getY()].getNorthDoor()) {
        //wrapping to last line
        if (p.getX() == 0) {
          nx = locations.length - 1;
        } else {
          // move north
          nx = p.getX() - 1;
        }
        ny = p.getY();
        //haven't been visited, able to move to
        if (distance[nx][ny] == 1000000) {
          //add element
          que.offer(new Point2D(nx, ny));
          //calculate distance
          distance[nx][ny] = distance[p.getX()][p.getY()] + 1;
        }
      }
      //go south
      if (locations[p.getX()][p.getY()].getSouthDoor()) {
        //wrapping to first line
        if (p.getX() == locations.length - 1) {
          nx = 0;
        } else {
          // move south
          nx = p.getX() + 1;
        }
        ny = p.getY();
        //haven't been visited, able to move to
        if (distance[nx][ny] == 1000000) {
          //add element
          que.offer(new Point2D(nx, ny));
          //calculate distance
          distance[nx][ny] = distance[p.getX()][p.getY()] + 1;
        }
      }
      //go east
      if (locations[p.getX()][p.getY()].getEastDoor()) {
        //wrapping to first column
        if (p.getY() == locations[0].length - 1) {
          ny = 0;
        } else {
          // move east
          ny = p.getY() + 1;
        }
        nx = p.getX();
        //haven't been visited, able to move to
        if (distance[nx][ny] == 1000000) {
          //add element
          que.offer(new Point2D(nx, ny));
          //calculate distance
          distance[nx][ny] = distance[p.getX()][p.getY()] + 1;
        }
      }
      //go west
      if (locations[p.getX()][p.getY()].getWestDoor()) {
        //wrapping to last column
        if (p.getY() == 0) {
          ny = locations[0].length - 1;
        } else {
          // move east
          ny = p.getY() - 1;
        }
        nx = p.getX();
        //haven't been visited, able to move to
        if (distance[nx][ny] == 1000000) {
          //add element
          que.offer(new Point2D(nx, ny));
          //calculate distance
          distance[nx][ny] = distance[p.getX()][p.getY()] + 1;
          //System.out.println("d" + nx + "," + ny + ":" + distance[nx][ny]);
        }
      }
      //System.out.println(nx + "," + ny + ":" + distance[nx][ny]);
    }
    return distance[end.getX()][end.getY()];
  }

  //select start cave and end cave
  private void selectStartAndEnd(int x, int y) {
    List<Point2D> caveList = new ArrayList<>();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (locations[i][j] instanceof Cave) {
          caveList.add(new Point2D(i, j));
        }
      }
    }
    //find start cave and end cave
    for (int i = 0; i < caveList.size(); i++) {
      for (int j = i + 1; j < caveList.size(); j++) {
        int pathLength = minDistance(caveList.get(i), caveList.get(j));
        if (pathLength >= MIN_DISTANCE) {
          start = caveList.get(i);
          end = caveList.get(j);
          return;
        }
      }
    }
  }

  //change shooting direction of arrow
  private int changeDirection(Location location, int direction) {
    boolean northDoor = location.getNorthDoor();
    boolean southDoor = location.getSouthDoor();
    boolean eastDoor = location.getEastDoor();
    boolean westDoor = location.getWestDoor();
    // arrow shot from south
    if (direction == 1) {
      if (northDoor) {
        return 1;
      } else if (eastDoor) {
        return 3;
      } else if (westDoor) {
        return 4;
      }
      // arrow shot from north
    } else if (direction == 2) {
      if (southDoor) {
        return 2;
      } else if (eastDoor) {
        return 3;
      } else if (westDoor) {
        return 4;
      }
      // arrow shot from west
    } else if (direction == 3) {
      if (northDoor) {
        return 1;
      } else if (southDoor) {
        return 2;
      } else if (eastDoor) {
        return 3;
      }
      //arrow shot from east
    } else {
      if (northDoor) {
        return 1;
      } else if (southDoor) {
        return 2;
      } else if (westDoor) {
        return 4;
      }
    }
    return 0;
  }

}
