package dungeon;

import location.Location;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;



/**
 * A view for Dungeon: display the dungeon, provide dungeon related information
 * and visual interface for users.
 */
public interface DungeonView {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Set action listener and key listeners to components on frame.
   *
   * @param clicks action listener
   * @param keys   key listener
   */
  void setListeners(ActionListener clicks, KeyListener keys);

  /**
   * Set focus back to main frame so that keyboard events work.
   */
  void resetFocus();

  /**
   * Set operation result for DescriptionPanel to draw.
   *
   * @param result operation result
   */
  void setOperationDescription(String result);

  /**
   * Set information of player for ButtonPanel to draw.
   *
   * @param playerInformation information
   */
  void setInformation(String playerInformation);

  /**
   * Add visited dungeon for DungeonPanel to draw.
   *
   * @param location player's new reached location
   * @param x        row index
   * @param y        column index
   */
  void addDungeon(Location location, int x, int y);

  /**
   * Update dungeon locations for DungeonPanel to draw.
   */
  void updateDungeon();

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(DungeonViewController listener);

  /**
   * Pop up setting dialog and return the user input.
   */
  String createSettings();

  /**
   * Restart game, reset the dungeon panel.
   */
  void restartGame();
}
