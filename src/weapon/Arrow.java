package weapon;

import location.Point2D;

/**
 * Arrow interface that contains operations that arrow should support.
 */
public interface Arrow {

  /**
   * Get the position of arrow.
   *
   * @return the name
   */
  Point2D getPosition();

  /**
   * Set the object of Point2D(which represents the position of arrow).
   *
   * @param x row index
   * @param y column index
   */
  void setPosition(int x, int y);

}
