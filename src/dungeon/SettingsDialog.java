package dungeon;


import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * SettingsDialog represents the settings dialog of the view, which
 * get the user input and then use the parameters to create a new game.
 */
class SettingsDialog extends JDialog {
  private String input;

  //constructor
  SettingsDialog(String title) {
    super((Frame) null, title);
    Container container = this.getContentPane();
    container.setLayout(null);
    JTextField txtRows = new JTextField();
    JTextField txtCols = new JTextField();
    JTextField txtWrapping = new JTextField();
    JTextField txtConnectivity = new JTextField();
    JTextField txtPercent = new JTextField();
    JTextField txtMonster = new JTextField();
    JButton startButton = new JButton("Start");
    JLabel rowLabel = new JLabel("Row size :");
    JLabel colLabel = new JLabel("Column size :");
    JLabel wrappingLabel = new JLabel("Wrapping type :");
    JLabel connectivityLabel = new JLabel("Interconnectivity :");
    JLabel percentLabel = new JLabel("Treasure percentage :");
    JLabel monsterLabel = new JLabel("Monster number :");
    input = null;

    this.setSize(300, 320);
    this.setLocationRelativeTo(null);
    //Must set Modal Dialogue first, then controller will react only
    //after the dialog is closed!
    this.setModal(true);

    //set row txt and label
    rowLabel.setSize(150, 20);
    rowLabel.setLocation(30, 20);
    rowLabel.setFont(new Font("Arial", Font.PLAIN, 15));

    txtRows.setSize(80, 20);
    txtRows.setLocation(180, 20);
    txtRows.setFont(new Font("Arial", Font.PLAIN, 15));

    //set column txt and label
    colLabel.setSize(150, 20);
    colLabel.setLocation(30, 60);
    colLabel.setFont(new Font("Arial", Font.PLAIN, 15));

    txtCols.setSize(80, 20);
    txtCols.setLocation(180, 60);
    txtCols.setFont(new Font("Arial", Font.PLAIN, 15));

    //set dungeon style txt and label
    wrappingLabel.setSize(150, 20);
    wrappingLabel.setLocation(30, 100);
    wrappingLabel.setFont(new Font("Arial", Font.PLAIN, 15));

    txtWrapping.setSize(80, 20);
    txtWrapping.setLocation(180, 100);
    txtWrapping.setFont(new Font("Arial", Font.PLAIN, 15));

    //set interconnectivity txt and label
    connectivityLabel.setSize(150, 20);
    connectivityLabel.setLocation(30, 140);
    connectivityLabel.setFont(new Font("Arial", Font.PLAIN, 15));

    txtConnectivity.setSize(80, 20);
    txtConnectivity.setLocation(180, 140);
    txtConnectivity.setFont(new Font("Arial", Font.PLAIN, 15));

    //set percentage txt and label
    percentLabel.setSize(150, 20);
    percentLabel.setLocation(30, 180);
    percentLabel.setFont(new Font("Arial", Font.PLAIN, 15));

    txtPercent.setSize(80, 20);
    txtPercent.setLocation(180, 180);
    txtPercent.setFont(new Font("Arial", Font.PLAIN, 15));

    //set monster number txt and label
    monsterLabel.setSize(150, 20);
    monsterLabel.setLocation(30, 220);
    monsterLabel.setFont(new Font("Arial", Font.PLAIN, 15));

    txtMonster.setSize(80, 20);
    txtMonster.setLocation(180, 220);
    txtMonster.setFont(new Font("Arial", Font.PLAIN, 15));

    //set button
    startButton.setSize(80, 30);
    startButton.setLocation(100, 250);
    startButton.setFont(new Font("Arial", Font.PLAIN, 15));
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        input = txtRows.getText() + "\n" + txtCols.getText() + "\n"
                + txtWrapping.getText() + "\n" + txtConnectivity.getText()
                + "\n" + txtPercent.getText() + "\n" + txtMonster.getText() + "\n";
        //close the dialog
        SettingsDialog.this.dispose();
      }
    });

    //add them to container
    container.add(rowLabel);
    container.add(txtRows);
    container.add(colLabel);
    container.add(txtCols);
    container.add(wrappingLabel);
    container.add(txtWrapping);
    container.add(connectivityLabel);
    container.add(txtConnectivity);
    container.add(percentLabel);
    container.add(txtPercent);
    container.add(monsterLabel);
    container.add(txtMonster);
    container.add(startButton);

    this.setVisible(true);
  }

  //return the text value
  String getTextValue() {
    return input;
  }
}
