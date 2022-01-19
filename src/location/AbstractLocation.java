package location;

import monster.Monster;
import treasure.Treasure;
import weapon.Arrow;
import weapon.CrookedArrow;

import java.util.ArrayList;
import java.util.List;


/**
 * An abstract class that contains all the code that is shared by all types
 * of locations, which have position(Point2D) and different doors.
 */
public abstract class AbstractLocation implements Location {
  private boolean northDoor;
  private boolean southDoor;
  private boolean eastDoor;
  private boolean westDoor;
  private List<Arrow> arrowList;

  /**
   * Protected constructor for use by subclasses.
   *
   * @param northDoor the status of north door
   * @param southDoor the status of south door
   * @param eastDoor  the status of east door
   * @param westDoor  the status of west door
   */
  protected AbstractLocation(boolean northDoor, boolean southDoor,
                             boolean eastDoor, boolean westDoor) {
    this.northDoor = northDoor;
    this.southDoor = southDoor;
    this.eastDoor = eastDoor;
    this.westDoor = westDoor;
    arrowList = new ArrayList<>();
  }

  @Override
  public boolean getNorthDoor() {
    return northDoor;
  }

  @Override
  public void setNorthDoor(boolean value) {
    this.northDoor = value;
  }

  @Override
  public boolean getSouthDoor() {
    return southDoor;
  }

  @Override
  public void setSouthDoor(boolean value) {
    this.southDoor = value;
  }

  @Override
  public boolean getEastDoor() {
    return eastDoor;
  }

  @Override
  public void setEastDoor(boolean value) {
    this.eastDoor = value;
  }

  @Override
  public boolean getWestDoor() {
    return westDoor;
  }

  @Override
  public void setWestDoor(boolean value) {
    this.westDoor = value;
  }

  @Override
  public List<Monster> getMonster() {
    List<Monster> monsterList = new ArrayList<>();
    return monsterList;
  }

  @Override
  public void addMonster(Monster monster) {
    if (monster == null) {
      throw new IllegalArgumentException("Argument cannot be null.");
    }
    //This method should be overridden by Cave class.
    //Tunnel class shouldn't be added monsters, so when user called this method
    //at a tunnel, it should do nothing.
  }

  @Override
  public void removeMonster() {
    //This method should be overridden by Cave class.
    //Tunnel class doesn't have monsters, so when user called this method
    //at a tunnel, it should do nothing.
  }

  @Override
  public List<Arrow> getArrow() {
    //defensive copy
    List<Arrow> tmpList = new ArrayList<>();
    for (Arrow a : arrowList) {
      Point2D tmpPosition = new Point2D(a.getPosition().getX(),
              a.getPosition().getY());
      tmpList.add(new CrookedArrow(tmpPosition));
    }
    return tmpList;
  }

  @Override
  public void addArrow(Arrow arrow) {
    if (arrow == null) {
      throw new IllegalArgumentException("Argument can not be null");
    }
    arrowList.add(arrow);
  }

  @Override
  public void removeArrow() {
    arrowList.clear();
  }

  @Override
  public List<Treasure> getTreasure() {
    List<Treasure> treasureList = new ArrayList<>();
    return treasureList;
  }

  @Override
  public void addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Argument can not be null");
    }
    //This method should be overridden by Cave class.
    //Tunnel class shouldn't be added treasure, so when user called this method
    //at a tunnel, it should do nothing.
  }

  @Override
  public void removeTreasure() {
    //This method should be overridden by Cave class.
    //Tunnel class doesn't have treasure, so when user called this method
    //at a tunnel, it should do nothing.
  }

}
