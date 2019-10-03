package delta.common.ui.swing.navigator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;

/**
 * Navigation controller for a navigator window.
 * @author DAM
 */
public class NavigationPanelController
{
  // Data
  private NavigationHistory _history;
  // Controllers
  private NavigatorWindowController _navigator;
  // UI
  private JButton _back;
  private ActionListener _backListener;
  private JButton _next;
  private ActionListener _nextListener;
  private JPanel _panel;

  /**
   * Constructor.
   * @param history Navigation history.
   * @param navigator Navigator.
   */
  public NavigationPanelController(NavigationHistory history, NavigatorWindowController navigator)
  {
    _history=history;
    _navigator=navigator;
    _panel=build();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new FlowLayout());
    // Back
    _back=GuiFactory.buildButton("Back");
    _backListener=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        doBack();
      }
    };
    _back.addActionListener(_backListener);
    panel.add(_back);
    // Next
    _next=GuiFactory.buildButton("Next");
    _nextListener=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        doNext();
      }
    };
    _next.addActionListener(_nextListener);
    panel.add(_next);
    updateUi();
    return panel;
  }

  /**
   * Update UI to reflect the current history state.
   */
  public void updateUi()
  {
    // Next
    PageIdentifier next=_history.getNext();
    _next.setEnabled(next!=null);
    // Previous
    PageIdentifier previous=_history.getPrevious();
    _back.setEnabled(previous!=null);
  }

  private void doBack()
  {
    PageIdentifier page=_history.getPrevious();
    if (page!=null)
    {
      _navigator.navigateTo(page);
    }
  }

  private void doNext()
  {
    PageIdentifier page=_history.getNext();
    if (page!=null)
    {
      _navigator.navigateTo(page);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Back
    if (_back!=null)
    {
      if (_backListener!=null)
      {
        _back.removeActionListener(_backListener);
      }
      _backListener=null;
      _back=null;
    }
    // Next
    if (_next!=null)
    {
      if (_nextListener!=null)
      {
        _next.removeActionListener(_nextListener);
      }
      _nextListener=null;
      _next=null;
    }
    // History
    _history=null;
  }
}
