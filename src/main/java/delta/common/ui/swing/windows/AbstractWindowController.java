package delta.common.ui.swing.windows;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import delta.common.utils.misc.TypedProperties;

/**
 * Base class for window controllers (both dialog and frame controllers).
 * @author DAM
 */
public abstract class AbstractWindowController implements WindowController
{
  /**
   * Parent controller, if any.
   */
  private WindowController _parent;
  /**
   * Managed window.
   */
  private Window _window;
  /**
   * Listener for window close events.
   */
  private WindowListener _closeWindowAdapter;

  /**
   * Constructor.
   * @param parent Parent controller, if any.
   */
  public AbstractWindowController(WindowController parent)
  {
    _parent=parent;
  }

  /**
   * Compute a window identifier.
   * @return A string that uniquely identifies the managed frame.
   */
  public String getWindowIdentifier()
  {
    return null;
  }

  /**
   * Get the managed window.
   * @return the managed window.
   */
  public Window getWindow()
  {
    if (_window==null)
    {
      _window=buildWindow();
      setupWindowCloseListener();
    }
    return _window;
  }

  /**
   * Build the managed window.
   * @return the managed window.
   */
  protected abstract Window buildWindow();

  protected JComponent buildContents()
  {
    return new JPanel();
  }

  /**
   * Perform window closing.
   */
  protected void doWindowClosing()
  {
    dispose();
  }

  /**
   * Get the parent controller.
   * @return a controller or <code>null</code> if there's none.
   */
  public WindowController getParentController()
  {
    return _parent;
  }

  /**
   * Get the parent window.
   * @return A window or <code>null</code>.
   */
  public Window getParentWindow()
  {
    Window parentWindow=null;
    if (_parent!=null)
    {
      parentWindow=_parent.getWindow();
    }
    return parentWindow;
  }

  public TypedProperties getUserProperties(String id)
  {
    if (_parent!=null)
    {
      return _parent.getUserProperties(id);
    }
    return null;
  }

  private void setupWindowCloseListener()
  {
    _closeWindowAdapter=new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        doWindowClosing();
      }
    };
    _window.addWindowListener(_closeWindowAdapter);
  }

  /**
   * Hide this window.
   */
  public void hide()
  {
    if (_window!=null)
    {
      _window.setVisible(false);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_window!=null)
    {
      if (_closeWindowAdapter!=null)
      {
        _window.removeWindowListener(_closeWindowAdapter);
        _closeWindowAdapter=null;
      }
      _window.setVisible(false);
      _window.removeAll();
      _window.dispose();
      _window=null;
    }
    _parent=null;
  }
}
