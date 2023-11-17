package delta.common.ui.swing.area;

import delta.common.ui.swing.misc.Disposable;

/**
 * Base class for area controllers.
 * @author DAM
 */
public class AbstractAreaController implements AreaController,Disposable
{
  private AreaController _parent;

  /**
   * Constructor.
   * @param parent Parent controller.
   */
  public AbstractAreaController(AreaController parent)
  {
    _parent=parent;
  }

  @Override
  public AreaController getParentController()
  {
    return _parent;
  }

  @Override
  public void dispose()
  {
    _parent=null;
  }
}
