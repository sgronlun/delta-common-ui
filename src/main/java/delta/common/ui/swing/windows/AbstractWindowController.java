package delta.common.ui.swing.windows;

import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.utils.misc.Preferences;
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
  protected Window _window;
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
      configureWindow();
      setupWindowCloseListener();
    }
    return _window;
  }

  /**
   * Get the window.
   * @return a window or <code>null</code> if not built.
   */
  protected Window getUnsafeWindow()
  {
    return _window;
  }

  /**
   * Override this method to configure the window
   * just after it has been built!
   */
  public void configureWindow()
  {
    // Nothing!
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
   * Perform automatic location setup using:
   * <ul>
   * <li>bounds preferences,
   * <li>location relative to parent.
   * </ul>
   */
  public void automaticLocationSetup()
  {
    Rectangle bounds=getBoundsPreferences();
    if (bounds!=null)
    {
      _window.setBounds(bounds);
    }
    else
    {
      WindowController parentController=getParentController();
      if (parentController!=null)
      {
        Window parentWindow=parentController.getWindow();
        _window.setLocationRelativeTo(parentWindow);
      }
    }
  }

  /**
   * Get the preferred bounds for this window.
   * @return Some bounds or <code>null</code> if undefined.
   */
  public Rectangle getBoundsPreferences()
  {
    Rectangle bounds=null;
    Preferences preferences=GuiFactory.getPreferences();
    if (preferences!=null)
    {
      TypedProperties props=preferences.getPreferences("ui.windows");
      String windowId=getWindowIdentifier();
      String preferenceName=windowId+".bounds";
      bounds=props.getBoundsProperty(preferenceName);
    }
    return bounds;
  }

  /**
   * Save the preferred bounds for this window.
   */
  public void saveBoundsPreferences()
  {
    Preferences preferences=GuiFactory.getPreferences();
    if (preferences!=null)
    {
      if (_window!=null)
      {
        String windowId=getWindowIdentifier();
        if ((windowId!=null) && (windowId.length()>0))
        {
          TypedProperties props=preferences.getPreferences("ui.windows");
          String preferenceName=windowId+".bounds";
          Rectangle bounds=_window.getBounds();
          props.setBoundsProperty(preferenceName,bounds);
        }
      }
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
