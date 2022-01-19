import dungeon.DungeonView;
import dungeon.DungeonViewController;
import location.Location;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Mock view class for testing DungeonViewController, using Appendable.
 */
public class MockView implements DungeonView {

  private Appendable log;

  /**
   * Constructor using Appendable.
   *
   * @param log Appendable object
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void refresh() {
    try {
      log.append("refresh\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void makeVisible() {
    try {
      log.append("make visible\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {
    try {
      log.append("listener added\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void resetFocus() {
    try {
      log.append("reset focus\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setOperationDescription(String result) {
    try {
      log.append("display operation result\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setInformation(String playerInformation) {
    try {
      log.append("update player's information\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addDungeon(Location location, int x, int y) {
    try {
      log.append("add visited dungeon\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void updateDungeon() {
    try {
      log.append("update visited dungeon\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addClickListener(DungeonViewController listener) {
    try {
      log.append("controller added\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public String createSettings() {
    try {
      log.append("create settings\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public void restartGame() {
    try {
      log.append("game restart\n");
    } catch (IOException e) {
      // do nothing
    }
  }
}
