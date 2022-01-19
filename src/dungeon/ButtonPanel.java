package dungeon;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;



/**
 * BoardPanel represents the panel of the view, which contains a button and
 * draws descriptions of player.
 */
public class ButtonPanel extends JPanel {
  private JButton playerButton;
  private String playerDescription;

  /**
   * DescriptionPanel constructor that using the read-only model.
   *
   */
  ButtonPanel() {
    playerButton = new JButton("Information");
    //this.setLayout(null);
    this.add(playerButton);
    //playerButton.setBounds(50,50,100,50);
    playerDescription = null;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    //draw player description
    g2d.setFont(new Font("Arial", Font.BOLD, 15));
    if (playerDescription != null) {
      String[] splitDescription = playerDescription.split("\n");
      int offset = 30;
      for (String s : splitDescription) {
        g2d.drawString(s, 10, 40 + offset);
        offset += 30;
      }
      //set opResult to null for next paint
      playerDescription = null;
    }
  }


  /**
   * Set action listener to information button on button panel.
   *
   * @param clicks action listener
   */
  void addActionListener(ActionListener clicks) {
    playerButton.addActionListener(clicks);
  }

  /**
   * Update player description for this panel to draw information of player.
   *
   * @param description description passed from controller
   */
  void updateDescription(String description) {
    playerDescription = description;
  }
}
