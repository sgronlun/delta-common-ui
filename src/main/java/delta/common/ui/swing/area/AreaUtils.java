package delta.common.ui.swing.area;

import delta.common.ui.swing.windows.WindowController;

/**
 * Utility methods related to area controllers.
 * @author DAM
 */
public class AreaUtils
{
  /**
   * Get the closest window controller.
   * <p>
   * <ul>
   * <li>In case <code>controller</code> is a window controller, then <code>controller</code>.
   * <li>Otherwise, returns the closest window controller in the hierarchy of area controllers.
   * </ul>
   * @param controller Area controller to use.
   * @return A window controller or <code>null</code> if not found.
   */
  public static WindowController findWindowController(AreaController controller)
  {
    if (controller==null)
    {
      return null;
    }
    if (controller instanceof WindowController)
    {
      return (WindowController)controller;
    }
    return findParentWindowController(controller);
  }

  /**
   * Get the parent window controller.
   * <p>
   * <ul>
   * <li>In case <code>controller</code> is a window controller, then returns the parent window controller.
   * <li>Otherwise, returns the closest window controller in the hierarchy of area controllers.
   * </ul>
   * @param controller Area controller to use.
   * @return A window controller or <code>null</code> if not found.
   */
  public static WindowController findParentWindowController(AreaController controller)
  {
    if (controller==null)
    {
      return null;
    }
    AreaController parent=controller.getParentController();
    if (parent==null)
    {
      return null;
    }
    if (parent instanceof WindowController)
    {
      return (WindowController)parent;
    }
    return findParentWindowController(parent);
  }
}
