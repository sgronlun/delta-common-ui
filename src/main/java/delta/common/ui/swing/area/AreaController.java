package delta.common.ui.swing.area;

import delta.common.ui.swing.windows.WindowController;

/**
 * Interface of an area controller.
 * @author DAM
 */
public interface AreaController
{
  /**
   * Get the parent controller.
   * @return the parent controller.
   */
  default AreaController getParentController()
  {
    return null;
  }

  /**
   * Get the closest window controller.
   * <p>
   * <ul>
   * <li>In case <code>this</code> is a window controller, then <code>this</code>.
   * <li>Otherwise, returns the closest window controller in the hierarchy of area controllers.
   * </ul>
   * @return A window controller or <code>null</code> if not found.
   */
  default WindowController getWindowController()
  {
    return AreaUtils.findWindowController(this);
  }

  /**
   * Get the parent window controller.
   * <p>
   * <ul>
   * <li>In case <code>this</code> is a window controller, then returns the parent window controller.
   * <li>Otherwise, returns the closest window controller in the hierarchy of area controllers.
   * </ul>
   * @return A window controller or <code>null</code> if not found.
   */
  default WindowController getParentWindowController()
  {
    return AreaUtils.findParentWindowController(this);
  }
}
