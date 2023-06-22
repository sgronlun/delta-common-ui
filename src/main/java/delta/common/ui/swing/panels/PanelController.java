package delta.common.ui.swing.panels;

import javax.swing.JPanel;

import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.misc.Disposable;

/**
 * Interface of a panel controller.
 * @author DAM
 */
public interface PanelController extends AreaController,Disposable
{
  /**
   * Get the managed panel.
   * @return
   */
  JPanel getPanel();
}
