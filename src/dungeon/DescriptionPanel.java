package dungeon;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * BoardPanel represents the panel of the view, which draws the current location
 * descriptions of player and the game status.
 */
class DescriptionPanel extends JPanel {

  private final ReadonlyDungeonModel model;
  private String opResult;

  /**
   * DescriptionPanel constructor that using the read-only model.
   *
   * @param model read-only model
   */
  DescriptionPanel(ReadonlyDungeonModel model) {
    this.model = model;
    opResult = null;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    //draw location description
    g2d.setFont(new Font("Arial", Font.BOLD, 15));
    String locationDescription = model.getLocationDescription();
    String[] splitDescription = locationDescription.split("\n");
    int offset = 20;
    for (String s : splitDescription) {
      g2d.drawString(s, 10, 10 + offset);
      offset += 20;
    }

    //give result of operation to user, and update opResult to null;
    if (opResult != null) {
      offset = 0;
      String[] splitReuslt = opResult.split("\n");
      for (String s : splitReuslt) {
        g2d.drawString(s, 10, 120 + offset);
        offset += 20;
      }
      //set opResult to null for next paint
      opResult = null;
    }
  }

  /**
   * Update opResult for this panel to draw information.
   *
   * @param result result passed from controller
   */
  void updateOpResult(String result) {
    opResult = result;
  }
}
