import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import location.Cave;
import location.Point2D;
import location.Tunnel;
import monster.Monster;
import monster.Otyugh;
import org.junit.Before;
import org.junit.Test;
import treasure.Diamond;
import treasure.Ruby;
import treasure.Sapphire;
import treasure.Treasure;
import weapon.Arrow;
import weapon.CrookedArrow;

import java.util.List;


/**
 * This class contains all the unit tests for Location class.
 */
public class LocationTest {
  private Cave cave;
  private Tunnel tunnel;

  /**
   * Setting up objects for all the tests.
   */
  @Before
  public void setUp() throws Exception {
    cave = new Cave(true, true, true, true);
    tunnel = new Tunnel(true, true, false, false);
  }

  /**
   * Test getDoor() and setDoor() method.
   */
  @Test
  public void testDoor() {
    assertTrue(cave.getNorthDoor());
    assertTrue(cave.getSouthDoor());
    assertTrue(cave.getEastDoor());
    assertTrue(cave.getWestDoor());
    assertFalse(tunnel.getEastDoor());
    //set door
    cave.setEastDoor(false);
    assertFalse(cave.getEastDoor());
  }

  /**
   * Test addTreasure() and getTreasure() method.
   * Also test the AbstractTreasure class.
   */
  @Test
  public void testTreasure() {
    Diamond diamond = new Diamond("Diamond1");
    Ruby ruby = new Ruby("Ruby1");
    Sapphire sapphire = new Sapphire("Sapphire1");
    cave.addTreasure(diamond);
    cave.addTreasure(ruby);
    cave.addTreasure(sapphire);
    List<Treasure> treasureList = cave.getTreasure();
    assertEquals(3, treasureList.size());
    assertEquals("Diamond1", treasureList.get(0).getName());
    assertEquals("Ruby1", treasureList.get(1).getName());
    assertEquals("Sapphire1", treasureList.get(2).getName());
  }

  /**
   * Test addMonster(), getMonster() and removeMonster() method.
   */
  @Test
  public void testMonster() {
    Otyugh otyugh = new Otyugh("monster");
    cave.addMonster(otyugh);
    List<Monster> monsters = cave.getMonster();
    assertEquals(1, monsters.size());
    assertEquals("monster", monsters.get(0).getName());
    cave.removeMonster();
    monsters = cave.getMonster();
    assertTrue(monsters.isEmpty());
  }

  /**
   * Test addArrow(), getArrow() and removeArrow method.
   */
  @Test
  public void testArrow() {
    Arrow arrow1 = new CrookedArrow(new Point2D(0, 0));
    Arrow arrow2 = new CrookedArrow(new Point2D(0, 0));
    cave.addArrow(arrow1);
    cave.addArrow(arrow2);
    List<Arrow> arrowList = cave.getArrow();
    assertEquals(2, arrowList.size());
    assertEquals(0, arrowList.get(0).getPosition().getX());
    assertEquals(0, arrowList.get(0).getPosition().getY());
    cave.removeArrow();
    arrowList = cave.getArrow();
    assertTrue(arrowList.isEmpty());
  }

  /**
   * Test null argument in addTreasure() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullParameterInAddTreasure() {
    cave.addTreasure(null);
  }

  /**
   * Test null argument in addMonster() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullParameterInAddMonster() {
    cave.addMonster(null);
  }

  /**
   * Test null argument in addArrow() method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullParameterInAddArrow() {
    cave.addArrow(null);
  }
}