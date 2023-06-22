package delta.common.ui.swing.panels;

import javax.swing.JPanel;

import delta.common.ui.swing.area.AbstractAreaController;
import delta.common.ui.swing.area.AreaController;

/**
 * Base class for panel controllers.
 * @author DAM
 */
public class AbstractPanelController extends AbstractAreaController implements PanelController
{
  private JPanel _panel;

  /**
   * Constructor.
   */
  public AbstractPanelController()
  {
    this(null);
  }

  /**
   * Constructor.
   * @param parent Parent area.
   */
  public AbstractPanelController(AreaController parent)
  {
    super(parent);
  }

  @Override
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Set the managed panel.
   * @param panel Panel to set.
   */
  public void setPanel(JPanel panel)
  {
    _panel=panel;
  }

  @Override
  public void dispose()
  {
    super.dispose();
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
  }
}
