package delta.common.ui.swing.navigator;

import delta.common.ui.swing.panels.PanelController;

/**
 * Interface of a navigable panel controller.
 * @author DAM
 */
public interface NavigablePanelController extends PanelController
{
  /**
   * Get the window title for this panel.
   * @return a window title.
   */
  String getTitle();
}
