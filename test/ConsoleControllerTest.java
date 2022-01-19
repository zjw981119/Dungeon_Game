import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Dungeon;
import dungeon.DungeonModel;
import dungeon.DungeonConsoleController;
import dungeon.DungeonController;
import org.junit.Test;

import java.io.StringReader;


/**
 * Test cases for the dungeon controller, using mocks for readable and
 * appendable.
 */
public class ConsoleControllerTest {

  /**
   * Test initial Appendable for a non-wrapping dungeon.
   */
  @Test
  public void testInitialAppendable1() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a cave", tmp[tmp.length - 5]);
    assertTrue(tmp[tmp.length - 4].equals("You find 1 Diamond")
            || tmp[tmp.length - 4].equals("You find 1 Diamond 1 Ruby")
            || tmp[tmp.length - 4].equals("You find 1 Diamond 1 Ruby 1 Sapphire"));
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test initial Appendable for a wrapping dungeon.
   */
  @Test
  public void testInitialAppendable2() {
    Dungeon m = new DungeonModel(5, 5, true, 0, 100, 1);
    StringReader input = new StringReader("");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a cave", tmp[tmp.length - 5]);
    assertTrue(tmp[tmp.length - 4].equals("You find 1 Diamond")
            || tmp[tmp.length - 4].equals("You find 1 Diamond 1 Ruby")
            || tmp[tmp.length - 4].equals("You find 1 Diamond 1 Ruby 1 Sapphire"));
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: east", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test valid move operation.
   */
  @Test
  public void testValidMove() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a tunnel", tmp[tmp.length - 4]);
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: east west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test pick arrow.
   */
  @Test
  public void testValidPickArrow() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("P arrow");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a cave", tmp[tmp.length - 4]);
    assertTrue(tmp[tmp.length - 3].equals("You find 1 Diamond")
            || tmp[tmp.length - 3].equals("You find 1 Diamond 1 Ruby")
            || tmp[tmp.length - 3].equals("You find 1 Diamond 1 Ruby 1 Sapphire"));
    assertEquals("Available move: west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test pick treasure.
   */
  @Test
  public void testValidPickTreasure() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("P treasure");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a cave", tmp[tmp.length - 4]);
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test pick treasure and arrow.
   */
  @Test
  public void testValidPickTreasureAndArrow() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("P treasure P arrow");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a cave", tmp[tmp.length - 3]);
    assertEquals("Available move: west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test pick arrow and treasure.
   */
  @Test
  public void testValidPickArrowAndTreasure() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("P arrow P treasure");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You are in a cave", tmp[tmp.length - 3]);
    assertEquals("Available move: west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test smell level 1.
   */
  @Test
  public void testSmellLevel1() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west M west");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    // tmp[tmp.length -4] ---- tmp[tmp.length -1] are information about current location
    assertEquals("You smell something terrible nearby", tmp[tmp.length - 5]);
  }

  /**
   * Test smell level 2.
   */
  @Test
  public void testSmellLevel2() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west M west M west");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    // tmp[tmp.length -4] ---- tmp[tmp.length -1] are information about current location
    assertEquals("You smell something more terrible nearby, be careful", tmp[tmp.length - 5]);
  }

  /**
   * Test shoot but miss monster.
   */
  @Test
  public void testShootButMiss() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west S west 4");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You shoot an arrow into the darkness", tmp[tmp.length - 5]);
    assertEquals("You are in a tunnel", tmp[tmp.length - 4]);
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: east west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test shoot the monster.
   */
  @Test
  public void testShootTheMonster() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west S west 1");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You shoot an arrow into the darkness", tmp[tmp.length - 6]);
    assertEquals("You hear a great howl in the distance", tmp[tmp.length - 5]);
    assertEquals("You are in a tunnel", tmp[tmp.length - 4]);
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: east west", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test player out of arrows.
   */
  @Test
  public void testOutOfArrows() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west S west 1 S west 1 S west 1 S west 1");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    // tmp[tmp.length -4] ---- tmp[tmp.length -1] are information about current location
    assertEquals("You are out of arrows, explore to find more", tmp[tmp.length - 5]);
  }

  /**
   * Test player slayed monster.
   */
  @Test
  public void testPlayerSlayMonster() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west M west M west S south 1 S south 1");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("You shoot an arrow into the darkness", tmp[tmp.length - 6]);
    assertEquals("You hear a great howl in the distance", tmp[tmp.length - 5]);
    //no smell means no monster nearby anymore(slayed by player)
    assertEquals("You are in a tunnel", tmp[tmp.length - 4]);
    assertTrue(tmp[tmp.length - 3].equals("You find 1 arrow")
            || tmp[tmp.length - 3].equals("You find 2 arrows"));
    assertEquals("Available move: south east", tmp[tmp.length - 2]);
    assertEquals("Move, Pickup, or Shoot(M-P-S)?", tmp[tmp.length - 1]);
  }

  /**
   * Test player slayed monster and reach the end cave alive.
   */
  @Test
  public void testPlayerWin() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west M west M west "
            + "S south 1 S south 1 M south");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("Congratulations, you win!", tmp[tmp.length - 1]);
  }

  /**
   * Test player eaten by monster.
   */
  @Test
  public void testPlayerEatenByMonster() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west M west M west M south");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!", tmp[tmp.length - 2]);
    assertEquals("Better luck next time", tmp[tmp.length - 1]);
  }

  /**
   * Test player escape from an injured monster / eaten by an injured monster.
   */
  @Test
  public void testPlayerEscape() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west M west M west S south 1 M south");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    //escape or be eaten
    assertTrue((tmp[tmp.length - 2].equals("Chomp, chomp, chomp, you are eaten by an Otyugh!")
            && tmp[tmp.length - 1].equals("Better luck next time"))
            || tmp[tmp.length - 5].equals("The monster is injured, "
            + "you're lucky to escape this time"));
  }


  /**
   * Test quit game at beginning.
   */
  @Test
  public void testQuitAtBeginning() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("q");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("Game quit!", tmp[tmp.length - 1]);
  }


  /**
   * Test quit game through move.
   */
  @Test
  public void testQuitThroughMove() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M q");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("Game quit!", tmp[tmp.length - 1]);
  }

  /**
   * Test quit game through shoot.
   */
  @Test
  public void testQuitThroughShoot() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("S q");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("Game quit!", tmp[tmp.length - 1]);
  }

  /**
   * Test quit game through pick.
   */
  @Test
  public void testQuitThroughPick() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("P q");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String[] tmp = gameLog.toString().split("\n");
    assertEquals("Game quit!", tmp[tmp.length - 1]);
  }

  /**
   * Test invalid input for an operation(M-P-S).
   */
  @Test
  public void testInvalidOperation() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west aaa");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String expected = "Not a valid operation: aaa";
    String[] tmp = gameLog.toString().split("\n");
    assertEquals(expected, tmp[tmp.length - 1]);
  }

  /**
   * Test invalid input for a direction in move.
   */
  @Test
  public void testInvalidDirectionInMove() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M bbb");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String expected = "Not a valid direction: bbb";
    String[] tmp = gameLog.toString().split("\n");
    assertEquals(expected, tmp[tmp.length - 1]);
  }

  /**
   * Test invalid move.
   */
  @Test
  public void testInvalidMove() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M north");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String expected1 = "Can not go to this direction";
    String expected2 = "Move, Pickup, or Shoot(M-P-S)?";
    String[] tmp = gameLog.toString().split("\n");
    // tmp[tmp.length -5] ---- tmp[tmp.length -1] are information about current location
    assertEquals(expected1, tmp[tmp.length - 6]);
    // test after invalid move, game continue running
    assertEquals(expected2, tmp[tmp.length - 1]);
  }

  /**
   * Test invalid input for a direction in shoot.
   */
  @Test
  public void testInvalidDirectionInShoot() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("S cc12");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String expected = "Not a valid direction: cc12";
    String[] tmp = gameLog.toString().split("\n");
    assertEquals(expected, tmp[tmp.length - 1]);
  }

  /**
   * Test invalid input for a distance in shoot.
   */
  @Test
  public void testInvalidDistanceInShoot() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("S west -2");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String expected = "Invalid distance: -2";
    String expected2 = "Move, Pickup, or Shoot(M-P-S)?";
    String[] tmp = gameLog.toString().split("\n");
    // tmp[tmp.length -5] ---- tmp[tmp.length -1] are information about current location
    assertEquals(expected, tmp[tmp.length - 6]);
    // test after invalid shoot, game continue running
    assertEquals(expected2, tmp[tmp.length - 1]);
  }

  /**
   * Test invalid input for a picking option.
   */
  @Test
  public void testInvalidOptionInPick() {
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("P abc123");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
    String expected = "Not a valid picking option: abc123";
    String[] tmp = gameLog.toString().split("\n");
    assertEquals(expected, tmp[tmp.length - 1]);
  }

  /**
   * Test null parameter in playGame().
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    StringReader input = new StringReader("P abc123");
    Appendable gameLog = new StringBuilder();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(null);
  }

  /**
   * Test FailingAppendable.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    Dungeon m = new DungeonModel(5, 5, false, 0, 100, 1);
    StringReader input = new StringReader("M west M west");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.playGame(m);
  }
}