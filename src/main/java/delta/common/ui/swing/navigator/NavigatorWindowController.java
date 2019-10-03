package delta.common.ui.swing.navigator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;

import delta.common.ui.swing.windows.DefaultDialogController;
import delta.common.ui.swing.windows.WindowController;

/**
 * Controller for a "navigator" window.
 * @author DAM
 */
public class NavigatorWindowController extends DefaultDialogController
{
  // Controllers
  // - contents controller
  private NavigablePanelController _contentsController;
  // - navigation controller
  private NavigationPanelController _navigationController;
  // Data
  private NavigationHistory _history;
  private NavigatorContentsResolver _contentsResolver;
  private int _id;

  /**
   * Constructor.
   * @param parent Parent controller.
   * @param id Identifier.
   */
  public NavigatorWindowController(WindowController parent, int id)
  {
    super(parent);
    _id=id;
    _history=new NavigationHistory();
    _navigationController=new NavigationPanelController(_history,this);
  }

  /**
   * Set the contents resolver.
   * @param resolver Resolver to set.
   */
  public void setContentsResolver(NavigatorContentsResolver resolver)
  {
    _contentsResolver=resolver;
  }

  /**
   * Navigate to show a page.
   * @param pageId Page identifier.
   */
  public void navigateTo(PageIdentifier pageId)
  {
    boolean ok=doNavigateTo(pageId);
    if (ok)
    {
      _history.setPage(pageId);
      _navigationController.updateUi();
    }
  }

  /**
   * Navigate to show a page.
   * @param pageId Page identifier.
   * @return <code>true</code> if navigation succeeded, <code>false</code> if it did not.
   */
  private boolean doNavigateTo(PageIdentifier pageId)
  {
    if (_contentsResolver==null)
    {
      return false;
    }
    boolean ok=false;
    NavigablePanelController panelController=_contentsResolver.build(pageId);
    if (panelController!=null)
    {
      disposeCurrentPanel();
      _contentsController=panelController;
      JDialog dialog=getDialog();
      Container container=dialog.getContentPane();
      container.removeAll();
      // Contents
      JPanel panel=_contentsController.getPanel();
      container.add(panel,BorderLayout.CENTER);
      // Navigator
      JPanel navigationPanel=_navigationController.getPanel();
      container.add(navigationPanel,BorderLayout.NORTH);
      // Title
      String title=_contentsController.getTitle();
      dialog.setTitle(title);
      dialog.pack();
      WindowController controller=getParentController();
      if (controller!=null)
      {
        Window parentWindow=controller.getWindow();
        dialog.setLocationRelativeTo(parentWindow);
      }
      dialog.setResizable(false);
      ok=true;
    }
    return ok;
  }

  @Override
  public String getWindowIdentifier()
  {
    return "NAVIGATOR#"+_id;
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    disposeCurrentPanel();
    super.dispose();
  }

  private void disposeCurrentPanel()
  {
    if (_contentsController!=null)
    {
      _contentsController.dispose();
      _contentsController=null;
    }
  }
}
