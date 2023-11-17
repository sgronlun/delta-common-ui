package delta.common.ui.swing.windows;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Manages a set of windows.
 * @author DAM
 */
public class WindowsManager
{
  private HashMap<String,WindowController> _controllers;

  /**
   * Constructor.
   */
  public WindowsManager()
  {
    _controllers=new HashMap<String,WindowController>();
  }

  /**
   * Get a free integer identifier.
   * @return An unused identifier.
   */
  public int getFreeId()
  {
    int nb=_controllers.size();
    for(int i=0;i<nb;i++)
    {
      String id=String.valueOf(i);
      if (_controllers.get(id)==null)
      {
        return i;
      }
    }
    return nb;
  }

  /**
   * Register a new window controller.
   * @param controller Window controller to register.
   */
  public void registerWindow(WindowController controller)
  {
    String id=controller.getWindowIdentifier();
    if ((id!=null) && (id.length()>0))
    {
      Window window=controller.getWindow();
      if (window!=null)
      {
        WindowListener wl=new WindowTracker(id);
        window.addWindowListener(wl);
        _controllers.put(id,controller);
      }
    }
  }

  /**
   * Tracks window closings.
   * @author DAM
   */
  public class WindowTracker extends WindowAdapter
  {
    private String _id;

    /**
     * Constructor.
     * @param id Window identifier.
     */
    public WindowTracker(String id)
    {
      _id=id;
    }

    public void windowClosed(WindowEvent e)
    {
      Window w=e.getWindow();
      w.removeWindowListener(this);
      _controllers.remove(_id);
    }
  }

  /**
   * Get the window controller for a given identifier.
   * @param identifier Identifier to search.
   * @return A window controller or <code>null</code> if not found.
   */
  public WindowController getWindow(String identifier)
  {
    WindowController controller=_controllers.get(identifier);
    return controller;
  }

  /**
   * Get a list of all managed window controllers.
   * @return A possibly empty but not <code>null</code> list of controllers.
   */
  public List<WindowController> getAll()
  {
    List<WindowController> ret=new ArrayList<WindowController>();
    ret.addAll(_controllers.values());
    return ret;
  }

  /**
   * Dispose all managed controllers.
   */
  public void disposeAll()
  {
    Collection<WindowController> controllers=_controllers.values();
    WindowController[] ctrls=controllers.toArray(new WindowController[controllers.size()]);
    for(WindowController controller : ctrls)
    {
      controller.dispose();
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    disposeAll();
    _controllers.clear();
  }
}
