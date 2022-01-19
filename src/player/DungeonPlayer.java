package player;

import location.Point2D;
import treasure.Treasure;
import weapon.Arrow;
import weapon.CrookedArrow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DungeonPlayer represents name, life, current position(Point2D), bag and quiver.
 * The player can move in the dungeon and pick the treasure at current location.
 */
public class DungeonPlayer implements Player {

  private int life;
  private String name;
  private Point2D position;
  private List<Treasure> bag;
  private List<Arrow> quiver;

  /**
   * Create a DungeonPlayer object using the given name.
   *
   * @param name the name of player
   */
  public DungeonPlayer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Argument cannot be null");
    }
    this.name = name;
    this.position = new Point2D(0, 0);
    this.bag = new ArrayList<>();
    this.quiver = new ArrayList<>();
    this.life = 1;
    //player have 3 arrows when created
    quiver.add(new CrookedArrow(new Point2D(0, 0)));
    quiver.add(new CrookedArrow(new Point2D(0, 0)));
    quiver.add(new CrookedArrow(new Point2D(0, 0)));
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getLife() {
    return this.life;
  }

  @Override
  public void reduceLife() {
    this.life--;
  }

  @Override
  public boolean isAlive() {
    return life == 1;
  }

  @Override
  public Point2D getPosition() {
    return new Point2D(position.getX(), position.getY());
  }

  @Override
  public void setPosition(int x, int y) {
    position.setX(x);
    position.setY(y);
  }

  @Override
  public List<Treasure> getBag() {
    List<Treasure> tmpList = new ArrayList<>();
    for (Treasure t : bag) {
      tmpList.add(t);
    }
    //sort the treasure by name
    Collections.sort(tmpList);
    return tmpList;
  }

  @Override
  public void addBag(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Argument cannot be null.");
    }
    bag.add(treasure);
  }

  @Override
  public List<Arrow> getQuiver() {
    //defensive copy
    List<Arrow> tmpList = new ArrayList<>();
    for (Arrow a : quiver) {
      Point2D ref = a.getPosition();
      tmpList.add(new CrookedArrow(new Point2D(ref.getX(), ref.getY())));
    }
    return tmpList;
  }

  @Override
  public void addQuiver(Arrow arrow) {
    if (arrow == null) {
      throw new IllegalArgumentException("Argument cannot be null.");
    }
    quiver.add(arrow);
  }

  @Override
  public void reduceArrow() {
    if (!quiver.isEmpty()) {
      quiver.remove(0);
    }
  }


}
