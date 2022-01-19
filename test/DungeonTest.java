import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.Dungeon;
import dungeon.DungeonModel;
import location.Cave;
import location.Location;
import location.Point2D;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import treasure.Treasure;
import weapon.Arrow;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * This class contains all the unit tests for Dungeon class.
 */
public class DungeonTest {
  private Dungeon nonWrappingDungeon;
  private Dungeon wrappingDungeon;

  /**
   * Setting up objects for all the tests.
   */
  @Before
  public void setUp() {
    nonWrappingDungeon = new DungeonModel(6, 6, false, 8, 40, 1);
    wrappingDungeon = new DungeonModel(6, 6, true, 5, 30, 1);
  }

  //BFS to get the minimum distance between two locations
  private int minDistance(Point2D start, Point2D end, Location[][] locations) {
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

  //BFS to get the completeness of the dungeon
  private int[][] getCompleteness(Point2D start, Location[][] locations) {
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
      if (p.getX() == start.getX() && p.getY() == start.getY()) {
        distance[p.getX()][p.getY()] = 0;
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
    return distance;
  }

  /**
   * Test resetPlayer() method.
   */
  @Test
  public void testResetPlayer() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
    int startX = tmpDungeon.getPlayer().getPosition().getX();
    int startY = tmpDungeon.getPlayer().getPosition().getY();
    //initial player
    assertEquals(0, startX);
    assertEquals(4, startY);
    assertEquals(3, tmpDungeon.getPlayer().getQuiver().size());
    assertEquals(0, tmpDungeon.getPlayer().getBag().size());
    //pick arrow and treasure
    tmpDungeon.pickTreasure();
    tmpDungeon.pickArrow();
    assertTrue(tmpDungeon.getPlayer().getQuiver().size() > 3);
    assertTrue(tmpDungeon.getPlayer().getBag().size() > 0);
    //make a move
    tmpDungeon.move(4);
    int currentX = tmpDungeon.getPlayer().getPosition().getX();
    int currentY = tmpDungeon.getPlayer().getPosition().getY();
    assertEquals(0, currentX);
    assertEquals(3, currentY);
    //reset player
    tmpDungeon.resetPlayer();
    int resetX = tmpDungeon.getPlayer().getPosition().getX();
    int resetY = tmpDungeon.getPlayer().getPosition().getY();
    assertEquals(0, resetX);
    assertEquals(4, resetY);
    assertEquals(3, tmpDungeon.getPlayer().getQuiver().size());
    assertEquals(0, tmpDungeon.getPlayer().getBag().size());
  }

  /**
   * Test resetStartCave() method.
   */
  @Test
  public void testResetStartCave() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
    int startX = tmpDungeon.getStartPosition().getX();
    int startY = tmpDungeon.getStartPosition().getY();
    assertEquals(0, startX);
    assertEquals(4, startY);
    //reset start cave
    tmpDungeon.resetStartCave(new Point2D(1, 1));
    int resetX = tmpDungeon.getStartPosition().getX();
    int resetY = tmpDungeon.getStartPosition().getY();
    assertEquals(1, resetX);
    assertEquals(1, resetY);
  }

  /**
   * Test resetEndCave() method.
   */
  @Test
  public void testResetEndCave() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
    int endX = tmpDungeon.getEndPosition().getX();
    int endY = tmpDungeon.getEndPosition().getY();
    assertEquals(1, endX);
    assertEquals(0, endY);
    //reset end cave
    tmpDungeon.resetEndCave(new Point2D(3, 3));
    int resetX = tmpDungeon.getEndPosition().getX();
    int resetY = tmpDungeon.getEndPosition().getY();
    assertEquals(3, resetX);
    assertEquals(3, resetY);
  }

  /**
   * Test resetDungeon() method.
   */
  @Test
  public void testResetDungeon() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
    //test initial monster number
    int initialMonster = 0;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (!tmpDungeon.getLocations()[i][j].getMonster().isEmpty()) {
          initialMonster++;
        }
      }
    }
    assertEquals(1, initialMonster);
    //create new dungeon
    Dungeon newDungeon = new DungeonModel(6, 7, false, 0, 100, 3);
    assertEquals(5, tmpDungeon.getLocations().length);
    assertEquals(5, tmpDungeon.getLocations()[0].length);
    Location[][] newLocations = newDungeon.getLocations();
    //reset dungeon
    tmpDungeon.resetDungeon(newLocations);
    //test reset size
    assertEquals(6, tmpDungeon.getLocations().length);
    assertEquals(7, tmpDungeon.getLocations()[0].length);

    int resetMonster = 0;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        if (!tmpDungeon.getLocations()[i][j].getMonster().isEmpty()) {
          resetMonster++;
        }
      }
    }
    //test reset monster number
    assertEquals(3, resetMonster);
  }

  /**
   * Test player win the game.
   */
  @Test
  public void testPlayerWin() {
    //start[0,4] end[1,0]
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
    tmpDungeon.pickArrow();
    tmpDungeon.move(4);
    assertEquals(0, tmpDungeon.getSmellLevel());
    tmpDungeon.move(4);
    tmpDungeon.move(4);
    //[0,1]
    assertEquals(1, tmpDungeon.getSmellLevel());
    tmpDungeon.shoot(4, 1);
    tmpDungeon.move(4);
    //[0,0]
    assertEquals(2, tmpDungeon.getSmellLevel());
    tmpDungeon.shoot(2, 1);
    assertEquals(0, tmpDungeon.getSmellLevel());
    //[1,0]
    tmpDungeon.move(2);
    //no monster at end cave
    assertEquals(0, tmpDungeon.combat());
    //player reach end cave alive, game over
    assertTrue(tmpDungeon.isGameOver());
    assertTrue(tmpDungeon.getPlayer().isAlive());
  }

  /**
   * Test getStartPosition() and getEndPosition() method for non-wrapping dungeon.
   * Also test placePlayer() method
   */
  @Test
  public void testGetStartAndEndPosition1() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 40, 1);
    Point2D tmpStart = tmpDungeon.getStartPosition();
    assertEquals(0, tmpStart.getX());
    assertEquals(4, tmpStart.getY());

    //test placePlayer() method
    Point2D playerPosition = tmpDungeon.getPlayer().getPosition();
    assertEquals(0, playerPosition.getX());
    assertEquals(4, playerPosition.getY());

    //test end cave position
    Point2D tmpEnd = tmpDungeon.getEndPosition();
    assertEquals(1, tmpEnd.getX());
    assertEquals(0, tmpEnd.getY());
  }

  /**
   * Test getStartPosition() and getEndPosition() method for wrapping dungeon.
   */
  @Test
  public void testGetStartAndEndPosition2() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, true, 0, 40, 1);
    Point2D tmpStart = tmpDungeon.getStartPosition();
    Point2D tmpEnd = tmpDungeon.getEndPosition();
    assertEquals(0, tmpStart.getX());
    assertEquals(1, tmpStart.getY());
    assertEquals(1, tmpEnd.getX());
    assertEquals(0, tmpEnd.getY());
  }

  /**
   * Test minimum distance between start and end position.
   */
  @Test
  public void testDistanceBetweenStartAndEnd() {
    //test minimum distance from start and end in wrapping dungeon
    for (int i = 0; i < 100; i++) {
      Dungeon tmpDungeon = new DungeonModel(8, 8, true, 20, 40, 1);
      Point2D tmpStart = tmpDungeon.getStartPosition();
      Point2D tmpEnd = tmpDungeon.getEndPosition();
      Location[][] locations = tmpDungeon.getLocations();
      assertTrue(minDistance(tmpStart, tmpEnd, locations) >= 5);
    }
    //test minimum distance from start and end in non-wrapping dungeon
    for (int i = 0; i < 100; i++) {
      Dungeon tmpDungeon = new DungeonModel(8, 8, false, 20, 40, 1);
      Point2D tmpStart = tmpDungeon.getStartPosition();
      Point2D tmpEnd = tmpDungeon.getEndPosition();
      Location[][] locations = tmpDungeon.getLocations();
      assertTrue(minDistance(tmpStart, tmpEnd, locations) >= 5);
    }
  }

  /**
   * Test completeness of the dungeon.
   */
  @Test
  public void testCompleteness() {
    //test completeness in wrapping dungeon
    for (int i = 0; i < 100; i++) {
      Dungeon tmpDungeon = new DungeonModel(8, 8, true, 20, 40, 1);
      Point2D tmpStart = tmpDungeon.getStartPosition();
      Location[][] locations = tmpDungeon.getLocations();
      int[][] completeness = getCompleteness(tmpStart, locations);
      int availableRoom = 0;
      for (int j = 0; j < locations.length; j++) {
        for (int k = 0; k < locations[0].length; k++) {
          if (completeness[j][k] != 1000000) {
            availableRoom++;
          }
        }
      }
      assertEquals(64, availableRoom);
    }

    //test completeness in non-wrapping dungeon
    for (int i = 0; i < 100; i++) {
      Dungeon tmpDungeon = new DungeonModel(7, 7, false, 20, 40, 1);
      Point2D tmpStart = tmpDungeon.getStartPosition();
      Location[][] locations = tmpDungeon.getLocations();
      int[][] completeness = getCompleteness(tmpStart, locations);
      int availableRoom = 0;
      for (int j = 0; j < locations.length; j++) {
        for (int k = 0; k < locations[0].length; k++) {
          if (completeness[j][k] != 1000000) {
            availableRoom++;
          }
        }
      }
      assertEquals(49, availableRoom);
    }
  }

  /**
   * Test getPlayerDescription() method.
   */
  @Test
  public void testGetPlayerDescription() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, true, 0, 100, 1);
    String description = "";
    description = tmpDungeon.getPlayerDescription();
    assertEquals("You have 3 arrows.\n", description);
    tmpDungeon.pickTreasure();
    description = tmpDungeon.getPlayerDescription();
    assertTrue(description.equals(("You have 1 diamonds, 0 rubies\nand 0 sapphires.\n"
            + "You have 3 arrows.\n"))
            || description.equals("You have 1 diamonds, 1 rubies\nand 0 sapphires.\n"
            + "You have 3 arrows.\n")
            || description.equals("You have 1 diamonds, 1 rubies\nand 1 sapphires.\n"
            + "You have 3 arrows.\n"));
  }

  /**
   * Test getLocationDescription() method.
   */
  @Test
  public void testGetLocationDescription() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, true, 0, 100, 1);
    String description = "";
    tmpDungeon.move(3);
    description = tmpDungeon.getLocationDescription();
    assertTrue(description.equals("You are in a tunnel\nYou find 1 arrow\n"
            + "Available move: east west") || description.equals("You are in a tunnel\n"
            + "You find 2 arrows\nAvailable move: east west"));
  }

  /**
   * Test move() method and wrapping dungeon.
   */
  @Test
  public void testMove() {
    //wrapping dungeon
    Dungeon tmpDungeon = new DungeonModel(5, 5, true, 0, 20, 1);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(1, tmpDungeon.getPlayer().getPosition().getY());
    //go east
    tmpDungeon.move(3);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(2, tmpDungeon.getPlayer().getPosition().getY());
    tmpDungeon.move(3);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(3, tmpDungeon.getPlayer().getPosition().getY());
    tmpDungeon.move(3);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(4, tmpDungeon.getPlayer().getPosition().getY());
    //wrapping to first column
    tmpDungeon.move(3);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getY());
    //go south
    tmpDungeon.move(2);
    assertEquals(1, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getY());
    //go north
    tmpDungeon.move(1);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getY());
    //go west
    tmpDungeon.move(4);
    assertEquals(0, tmpDungeon.getPlayer().getPosition().getX());
    assertEquals(4, tmpDungeon.getPlayer().getPosition().getY());

  }

  /**
   * Test shoot() method and non-wrapping dungeon.
   */
  @Test
  public void testShoot() {
    //start [0,1] end[2,4]
    DungeonModel tmpDungeon = new DungeonModel(5, 5, false, 16, 100, 20);
    tmpDungeon.pickArrow();
    Point2D playerPosition = tmpDungeon.getPlayer().getPosition();
    //player's position
    assertEquals(0, playerPosition.getX());
    assertEquals(1, playerPosition.getY());
    Location[][] locations = tmpDungeon.getLocations();
    assertFalse(locations[1][0].getMonster().isEmpty());
    assertEquals(2, locations[1][0].getMonster().get(0).getHealth());

    //shoot monster at [1,0] using the tunnel to change the direction
    assertTrue(tmpDungeon.shoot(4, 1));
    locations = tmpDungeon.getLocations();
    //monster health reduce by 1
    assertEquals(1, locations[1][0].getMonster().get(0).getHealth());
    //shoot monster at [1,0] using the tunnel to change the direction
    tmpDungeon.shoot(4, 1);
    locations = tmpDungeon.getLocations();
    //monster was killed and vanished
    assertTrue(locations[1][0].getMonster().isEmpty());

    //move west to the tunnel[0,0]
    tmpDungeon.move(4);
    tmpDungeon.pickArrow();
    //shoot monster at [0,2]
    assertTrue(tmpDungeon.shoot(3, 2));
    //move south to cave[1,0]
    tmpDungeon.move(2);
    tmpDungeon.pickArrow();

    //shoot monster at [2,0]
    assertTrue(tmpDungeon.shoot(2, 1));

    //shoot to north, no monster
    assertFalse(tmpDungeon.shoot(1, 1));

    //shoot monster located at [0,2] from [1,0] using the tunnel to change direction
    assertTrue(tmpDungeon.shoot(1, 2));
    locations = tmpDungeon.getLocations();
    assertTrue(locations[0][2].getMonster().isEmpty());
  }

  /**
   * Test pickTreasure() method.
   */
  @Test
  public void testPickTreasure() {
    for (int i = 0; i < 1000; i++) {
      Dungeon tmpDungeon = new DungeonModel(5, 5, true, 0, 100, 1);
      tmpDungeon.pickTreasure();
      //test player's bag
      Player player = tmpDungeon.getPlayer();
      List<Treasure> treasureList = player.getBag();
      assertTrue(treasureList.size() >= 1 && treasureList.size() <= 3);

      //test cave treasure(should be 0)
      Location[][] tmpLocations = tmpDungeon.getLocations();
      int startX = tmpDungeon.getStartPosition().getX();
      int startY = tmpDungeon.getStartPosition().getY();
      List<Treasure> caveTreasure = tmpLocations[startX][startY].getTreasure();
      assertEquals(0, caveTreasure.size());
    }
  }

  /**
   * Test pickArrow() method.
   */
  @Test
  public void testPickArrow() {
    for (int i = 0; i < 1000; i++) {
      Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
      tmpDungeon.pickArrow();
      //test player's quiver
      Player player = tmpDungeon.getPlayer();
      List<Arrow> arrowList = player.getQuiver();
      assertTrue(arrowList.size() >= 4 && arrowList.size() <= 5);

      //test cave arrow(should be 0)
      Location[][] tmpLocations = tmpDungeon.getLocations();
      int startX = tmpDungeon.getStartPosition().getX();
      int startY = tmpDungeon.getStartPosition().getY();
      List<Arrow> caveArrow = tmpLocations[startX][startY].getArrow();
      assertTrue(caveArrow.isEmpty());
    }
  }

  /**
   * Test treasure percentage is no smaller than user input.
   */
  @Test
  public void testTreasurePercentage() {
    int totalCave = 0;
    int treasureCave = 0;
    Location[][] tmpLocations = nonWrappingDungeon.getLocations();
    int x = tmpLocations.length;
    int y = tmpLocations[0].length;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (tmpLocations[i][j] instanceof Cave) {
          //System.out.println("aaa");
          totalCave++;
          if (tmpLocations[i][j].getTreasure().size() > 0) {
            treasureCave++;
          }
        }
      }
    }
    assertTrue((double) treasureCave / (double) totalCave > 0.4);
    assertEquals(0.4, (double) treasureCave / (double) totalCave, 0.1);
  }

  /**
   * Test arrow percentage is no smaller than user input.
   */
  @Test
  public void testArrowPercentage() {
    int arrowLocation = 0;
    Location[][] tmpLocations = nonWrappingDungeon.getLocations();
    int x = tmpLocations.length;
    int y = tmpLocations[0].length;
    int totalLocation = x * y;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (tmpLocations[i][j].getArrow().size() > 0) {
          arrowLocation++;
        }
      }
    }
    assertTrue((double) arrowLocation / (double) totalLocation > 0.4);
    assertEquals(0.4, (double) arrowLocation / (double) totalLocation, 0.1);
  }

  /**
   * Test monster amount.
   */
  @Test
  public void testMonsterAmount() {
    int monsterCave = 0;
    DungeonModel tmpDungeon = new DungeonModel(5, 5, false, 16, 100, 20);
    Location[][] tmpLocations = tmpDungeon.getLocations();
    int x = tmpLocations.length;
    int y = tmpLocations[0].length;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        if (tmpLocations[i][j].getMonster().size() > 0) {
          monsterCave++;
        }
      }
    }
    assertEquals(20, monsterCave);
  }

  /**
   * Test monster smell.
   */
  @Test
  public void testSmell() {
    //start [0,1] end[2,4]
    DungeonModel tmpDungeon = new DungeonModel(5, 5, false, 16, 100, 20);
    //test max level(more than 2 monsters within 2 distance)
    assertEquals(2, tmpDungeon.getSmellLevel());

    //start [0,4] end[1,0]
    DungeonModel preDefinedDungeon = new DungeonModel(5, 5, false, 0, 100, 1);
    //test min level
    assertEquals(0, preDefinedDungeon.getSmellLevel());
    //test middle level smell
    preDefinedDungeon.move(4);
    preDefinedDungeon.move(4);
    preDefinedDungeon.move(4);
    assertEquals(1, preDefinedDungeon.getSmellLevel());
    preDefinedDungeon.move(4);
    //1 monster within 1 distance
    assertEquals(2, preDefinedDungeon.getSmellLevel());
  }

  /**
   * Test combat.
   */
  @Test
  public void testCombat() {
    DungeonModel tmpDungeon1 = new DungeonModel(5, 5, false, 16, 100, 20);
    //start cave has no monster
    assertEquals(0, tmpDungeon1.combat());

    //go east, combat with monster(health = 2)
    tmpDungeon1.move(3);
    assertEquals(1, tmpDungeon1.combat());
    //player eaten by monster
    int life = tmpDungeon1.getPlayer().getLife();
    assertEquals(0, life);
    //Game over
    assertTrue(tmpDungeon1.isGameOver());
    assertFalse(tmpDungeon1.getPlayer().isAlive());

    //test combat with injured monster
    for (int i = 0; i < 1000; i++) {
      //start position 0,1
      DungeonModel tmpDungeon2 = new DungeonModel(5, 5, false, 16, 100, 20);
      tmpDungeon2.shoot(3, 1);
      tmpDungeon2.move(3);
      int result = tmpDungeon2.combat();
      assertTrue(result == 1 || result == 2);
    }
  }


  /**
   * Test interconnectivity for non-wrapping dungeon.
   */
  @Test
  public void testInterconnectivity1() {
    Location[][] tmpLocations = nonWrappingDungeon.getLocations();
    int x = tmpLocations.length;
    int y = tmpLocations[0].length;
    int doorNum = 0;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        //count the available doors at each location
        if (tmpLocations[i][j].getNorthDoor()) {
          doorNum++;
        }
        if (tmpLocations[i][j].getSouthDoor()) {
          doorNum++;
        }
        if (tmpLocations[i][j].getEastDoor()) {
          doorNum++;
        }
        if (tmpLocations[i][j].getWestDoor()) {
          doorNum++;
        }
      }
    }
    //edges number = doors number / 2
    //interconnectivity for non-wrapping dungeon = doors number / 2 - (xy - 1)
    assertEquals(8, doorNum / 2 - (x * y - 1));
  }

  /**
   * Test interconnectivity for wrapping dungeon.
   */
  @Test
  public void testInterconnectivity2() {
    Location[][] tmpLocations = wrappingDungeon.getLocations();
    int x = tmpLocations.length;
    int y = tmpLocations[0].length;
    int doorNum = 0;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        //count the available doors at each location
        if (tmpLocations[i][j].getNorthDoor()) {
          doorNum++;
        }
        if (tmpLocations[i][j].getSouthDoor()) {
          doorNum++;
        }
        if (tmpLocations[i][j].getEastDoor()) {
          doorNum++;
        }
        if (tmpLocations[i][j].getWestDoor()) {
          doorNum++;
        }
      }
    }
    //edges number = doors number / 2
    //interconnectivity for wrapping dungeon = doors number / 2 - (xy - 1)
    assertEquals(5, doorNum / 2 - (x * y - 1));
  }


  /**
   * Test invalid size in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSizeInConstructor() {
    Dungeon tmpDungeon = new DungeonModel(-1, 5, false, 0, 50, 1);
  }

  /**
   * Test negative interconnectivity in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeInterconnectivityInConstructor1() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, -1, 50, 1);
  }

  /**
   * Test invalid interconnectivity in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivityInConstructor2() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 17, 50, 1);
  }

  /**
   * Test invalid interconnectivity in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivityInConstructor3() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, true, 26, 50, 1);
  }

  /**
   * Test invalid treasure percentage in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasurePercentageInConstructor1() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 10, 1);
  }

  /**
   * Test invalid treasure percentage in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasurePercentageInConstructor2() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 200, 1);
  }

  /**
   * Test invalid monster amount in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsterAmountInConstructor1() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 50, -1);
  }

  /**
   * Test invalid monster amount in constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsterAmountInConstructor2() {
    Dungeon tmpDungeon = new DungeonModel(5, 5, false, 0, 50, 30);
  }

  /**
   * Test invalid direction number out of range in move() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumberInMove1() {
    wrappingDungeon.move(5);
  }

  /**
   * Test invalid move direction in wrapping dungeon.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumberInMove2() {
    //can't go north at the location
    wrappingDungeon.move(1);
  }

  /**
   * Test invalid move direction in non-wrapping dungeon.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumberInMove3() {
    //non-wrapping dungeon
    Dungeon tmpDungeon2 = new DungeonModel(5, 5, false, 0, 20, 1);
    //move north
    tmpDungeon2.move(1);
  }

  /**
   * Test invalid direction number out of range in shoot() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDirectionInShoot() {
    wrappingDungeon.shoot(5, 1);
  }

  /**
   * Test invalid distance in shoot() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDistanceInShoot() {
    wrappingDungeon.shoot(4, -2);
  }
}