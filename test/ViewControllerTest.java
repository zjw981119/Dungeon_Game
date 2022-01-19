import static org.junit.Assert.assertEquals;

import dungeon.Dungeon;
import dungeon.DungeonModel;
import dungeon.DungeonView;
import dungeon.DungeonViewController;
import dungeon.DungeonViewControllerImpl;
import org.junit.Test;



/**
 * Test cases for the dungeon view controller, using mock view.
 */
public class ViewControllerTest {

  /**
   * Test controller constructor.
   */
  @Test
  public void testInitialController() {
    StringBuilder log = new StringBuilder();
    DungeonView view = new MockView(log);
    Dungeon model = new DungeonModel(5, 5, false, 0, 100, 1);
    DungeonViewController controller = new DungeonViewControllerImpl(model, view);
    assertEquals("listener added\n" + "reset focus\n", log.toString());
  }

  /**
   * Test playGame() method.
   */
  @Test
  public void testPlayGame() {
    StringBuilder log = new StringBuilder();
    DungeonView view = new MockView(log);
    Dungeon model = new DungeonModel(5, 5, false, 0, 100, 1);
    DungeonViewController controller = new DungeonViewControllerImpl(model, view);
    controller.playGame(model);
    assertEquals("listener added\n" + "reset focus\n"
            + "controller added\n" + "make visible\n", log.toString());
  }

  /**
   * Test handlePanelClick() method for an invalid move.
   */
  @Test
  public void TestHandlePanelClick1() {
    StringBuilder log = new StringBuilder();
    DungeonView view = new MockView(log);
    Dungeon model = new DungeonModel(5, 5, false, 0, 100, 1);
    DungeonViewController controller = new DungeonViewControllerImpl(model, view);
    controller.playGame(model);
    assertEquals(0,model.getPlayer().getPosition().getX());
    assertEquals(4,model.getPlayer().getPosition().getY());
    //invalid move
    controller.handlePanelClick(1);
    assertEquals("listener added\n" + "reset focus\n"
            + "controller added\n" + "make visible\n"
            + "display operation result\n" + "refresh\n", log.toString());
    assertEquals(0,model.getPlayer().getPosition().getX());
    assertEquals(4,model.getPlayer().getPosition().getY());
  }

  /**
   * Test handlePanelClick() method for a valid move.
   */
  @Test
  public void TestHandlePanelClick2() {
    StringBuilder log = new StringBuilder();
    DungeonView view = new MockView(log);
    Dungeon model = new DungeonModel(5, 5, false, 0, 100, 1);
    DungeonViewController controller = new DungeonViewControllerImpl(model, view);
    controller.playGame(model);
    assertEquals(0,model.getPlayer().getPosition().getX());
    assertEquals(4,model.getPlayer().getPosition().getY());
    //valid move
    controller.handlePanelClick(4);
    assertEquals("listener added\n" + "reset focus\n"
            + "controller added\n" + "make visible\n"
            + "display operation result\n" + "add visited dungeon\n" + "refresh\n", log.toString());
    assertEquals(0,model.getPlayer().getPosition().getX());
    assertEquals(3,model.getPlayer().getPosition().getY());
  }

}
