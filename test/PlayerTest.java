import static org.junit.Assert.assertEquals;

import location.Point2D;
import org.junit.Before;
import org.junit.Test;
import player.DungeonPlayer;
import player.Player;
import treasure.Diamond;
import treasure.Ruby;
import treasure.Sapphire;
import treasure.Treasure;
import weapon.Arrow;
import weapon.CrookedArrow;

import java.util.List;


/**
 * This class contains all the unit tests for Player class.
 */
public class PlayerTest {
  private Player player;

  /**
   * Setting up objects for all of the tests.
   */
  @Before
  public void setUp() throws Exception {
    player = new DungeonPlayer("Abbey");
  }

  /**
   * Test getName() method.
   */
  @Test
  public void testGetName() {
    assertEquals("Abbey", player.getName());
  }

  /**
   * Test getBag() and addBag() method.
   */
  @Test
  public void testBag() {
    Diamond diamond = new Diamond("Diamond1");
    Ruby ruby = new Ruby("Ruby1");
    Sapphire sapphire = new Sapphire("Sapphire1");
    player.addBag(diamond);
    player.addBag(ruby);
    player.addBag(sapphire);
    List<Treasure> treasureList = player.getBag();
    assertEquals(3, treasureList.size());
    assertEquals("Diamond1", treasureList.get(0).getName());
    assertEquals("Ruby1", treasureList.get(1).getName());
    assertEquals("Sapphire1", treasureList.get(2).getName());
  }

  /**
   * Test getQuiver(), addQuiver() and removeArrow() method.
   */
  @Test
  public void testQuiver() {
    Arrow arrow1 = new CrookedArrow(new Point2D(0, 0));
    Arrow arrow2 = new CrookedArrow(new Point2D(1, 1));
    player.addQuiver(arrow1);
    player.addQuiver(arrow2);
    List<Arrow> arrowList = player.getQuiver();
    assertEquals(5, arrowList.size());
    assertEquals(0, arrowList.get(0).getPosition().getX());
    assertEquals(0, arrowList.get(0).getPosition().getY());
    for (int i = 0; i < 4; i++) {
      player.reduceArrow();
    }
    arrowList = player.getQuiver();
    assertEquals(1, arrowList.size());
    assertEquals(1, arrowList.get(0).getPosition().getX());
    assertEquals(1, arrowList.get(0).getPosition().getY());
  }

  /**
   * Test getPosition() and setPosition() method.
   */
  @Test
  public void testPosition() {
    assertEquals(0, player.getPosition().getX());
    assertEquals(0, player.getPosition().getY());
    player.setPosition(1, 3);
    assertEquals(1, player.getPosition().getX());
    assertEquals(3, player.getPosition().getY());
  }

  /**
   * Test null argument in addBag() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullParameterInAddTreasure() {
    player.addBag(null);
  }

  /**
   * Test null argument in addQuiver() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullParameterInAddQuiver() {
    player.addQuiver(null);
  }
}