package delta.common.ui.swing.navigator;

import delta.common.ui.swing.panels.AbstractPanelController;

/**
 * Base class for navigable panel controllers.
 * @author DAM
 */
public abstract class AbstractNavigablePanelController extends AbstractPanelController implements NavigablePanelController
{
  private NavigatorWindowController _parent;

  /**
   * Constructor.
   * @param parent Parent area.
   */
  public AbstractNavigablePanelController(NavigatorWindowController parent)
  {
    super(parent);
    _parent=parent;
  }

  /**
   * Get the parent controller.
   * @return the parent controller.
   */
  public NavigatorWindowController getParent()
  {
    return _parent;
  }
}
