package delta.common.ui.swing.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Controller for a pop-up menu.
 * @author DAM
 */
public class PopupMenuController implements ActionListener
{
  // Delegate for triggered commands
  private CommandExecutor _executor;
  // UI
  private JPopupMenu _contextMenu;

  /**
   * Constructor.
   * @param executor Executor for triggered commands.
   */
  public PopupMenuController(CommandExecutor executor)
  {
    _executor=executor;
    _contextMenu=new JPopupMenu();
  }

  /**
   * Add a menu item.
   * @param label Label of the menu item.
   * @param command Command of the menu item.
   */
  public void addMenuItem(String label, String command)
  {
    JMenuItem remove=new JMenuItem(label);
    remove.setActionCommand(command);
    remove.addActionListener(this);
    _contextMenu.add(remove);
  }

  /**
   * Install this menu on a component.
   * @param component Targeted component.
   */
  public void install(JComponent component)
  {
    MouseListener popupListener=buildRightClickListener();
    component.addMouseListener(popupListener);
  }

  private MouseListener buildRightClickListener()
  {
    class PopClickListener extends MouseAdapter
    {
      @Override
      public void mousePressed(MouseEvent e)
      {
        if (e.isPopupTrigger()) doPop(e);
      }

      @Override
      public void mouseReleased(MouseEvent e)
      {
        if (e.isPopupTrigger()) doPop(e);
      }

      private void doPop(MouseEvent e)
      {
        _contextMenu.show(e.getComponent(),e.getX(),e.getY());
      }
    }
    return new PopClickListener();
  }

  /**
   * Callback for managed commands.
   * @param event Source event.
   */
  @Override
  public void actionPerformed(ActionEvent event)
  {
    String command=event.getActionCommand();
    if (_executor!=null)
    {
      _executor.invoke(command);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _executor=null;
    if (_contextMenu!=null)
    {
      _contextMenu.removeAll();
      _contextMenu=null;
    }
  }
}
