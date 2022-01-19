package weapon;

import location.Point2D;

/**
 * This class represents a crooked arrow. It offers method
 * to create a CrookedArrow object using the given name.
 */
public class CrookedArrow implements Arrow {
  private Point2D position;

  /**
   * Create a CrookedArrow object using the given name.
   *
   * @param position the position of arrow
   * @throws IllegalArgumentException if position is null.
   */
  public CrookedArrow(Point2D position) {
    if (position == null) {
      throw new IllegalArgumentException("Name can not be null");
    }
    this.position = position;
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

}
