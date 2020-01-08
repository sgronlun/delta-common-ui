package delta.common.ui.swing.windows;

import java.awt.Window;

import delta.common.utils.misc.TypedProperties;

/**
 * Interface of a window controller.
 * @author DAM
 */
public interface WindowController
{
  /**
   * Get the managed window.
   * @return the managed window.
   */
  public Window getWindow();

  /**
   * Get the parent window controller, if any.
   * @return A window controller or <code>null</code> if none or undefined.
   */
  public WindowController getParentController();

  /**
   * Compute a window identifier.
   * @return A string that uniquely identifies the managed frame.
   */
  public String getWindowIdentifier();

  /**
   * Show the managed window.
   */
  public void show();

  /**
   * Bring the managed window to front.
   */
  public void bringToFront();

  /**
   * Pack contents to preferred size.
   */
  public void pack();

  /**
   * Set window title.
   * @param title Title to set.
   */
  public void setTitle(String title);

  /**
   * Get some user properties attached to the managed window.
   * @param id Properties identifier.
   * @return Some properties or <code>null</code>.
   */
  public TypedProperties getUserProperties(String id);

  /**
   * Get the child windows manager.
   * @return the child windows manager.
   */
  public WindowsManager getWindowsManager();

  /**
   * Release all managed resources.
   */
  public void dispose();
}
