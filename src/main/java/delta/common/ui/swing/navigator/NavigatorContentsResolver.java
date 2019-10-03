package delta.common.ui.swing.navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic resolver for navigator contents.
 * @author DAM
 */
public class NavigatorContentsResolver
{
  private List<NavigablePanelControllerFactory> _factories;

  /**
   * Constructor.
   */
  public NavigatorContentsResolver()
  {
    _factories=new ArrayList<NavigablePanelControllerFactory>();
  }

  /**
   * Build a panel controller for the given page.
   * @param pageId Page identifier.
   * @return A controller or <code>null</code> if not supported.
   */
  public NavigablePanelController build(PageIdentifier pageId)
  {
    NavigablePanelController panel=null;
    for(NavigablePanelControllerFactory factory : _factories)
    {
      panel=factory.build(pageId);
      if (panel!=null)
      {
        break;
      }
    }
    return panel;
  }

  /**
   * Add a factory.
   * @param factory Factory to add.
   */
  public void addFactory(NavigablePanelControllerFactory factory)
  {
    _factories.add(factory);
  }
}
