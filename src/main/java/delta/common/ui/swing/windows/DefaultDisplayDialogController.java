package delta.common.ui.swing.windows;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.OKCancelPanelController;

/**
 * Base class for display form dialog controllers.
 * @param <T> Managed data.
 * @author DAM
 */
public class DefaultDisplayDialogController<T> extends DefaultDialogController
{
  // Data
  protected T _data;
  // Controllers
  private OKCancelPanelController _okCancelController;

  /**
   * Constructor.
   * @param parentController Parent controller.
   * @param data Data to edit.
   */
  public DefaultDisplayDialogController(WindowController parentController, T data)
  {
    super(parentController);
    _data=data;
  }

  @Override
  protected JDialog build()
  {
    JDialog dialog=super.build();
    dialog.pack();
    WindowController controller=getParentController();
    if (controller!=null)
    {
      Window parentWindow=controller.getWindow();
      dialog.setLocationRelativeTo(parentWindow);
    }
    initShortcuts(dialog);
    return dialog;
  }

  @Override
  protected JComponent buildContents()
  {
    JPanel panel=GuiFactory.buildPanel(new BorderLayout());
    JPanel dataPanel=buildFormPanel();
    panel.add(dataPanel,BorderLayout.CENTER);
    _okCancelController=new OKCancelPanelController(true,false);
    JPanel okCancelPanel=_okCancelController.getPanel();
    panel.add(okCancelPanel,BorderLayout.SOUTH);
    ActionListener actionListener=new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        String action=event.getActionCommand();
        handleButton(action);
      }
    };
    _okCancelController.getOKButton().addActionListener(actionListener);
    return panel;
  }

  private void initShortcuts(JDialog dialog)
  {
    // OK
    {
      KeyStroke stroke=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
      JRootPane rootPane=dialog.getRootPane();
      InputMap inputMap=rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      inputMap.put(stroke, OKCancelPanelController.OK_COMMAND);
      Action action=new AbstractAction()
      {
        public void actionPerformed(ActionEvent e)
        {
          ok();
        }
      };
      rootPane.getActionMap().put(OKCancelPanelController.OK_COMMAND, action);
    }
    // Cancel
    {
      KeyStroke stroke=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
      JRootPane rootPane=dialog.getRootPane();
      InputMap inputMap=rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      inputMap.put(stroke, OKCancelPanelController.CANCEL_COMMAND);
      Action action=new AbstractAction()
      {
        public void actionPerformed(ActionEvent e)
        {
          ok();
        }
      };
      rootPane.getActionMap().put(OKCancelPanelController.CANCEL_COMMAND, action);
    }
  }

  private void removeShortcuts()
  {
    JDialog dialog=(JDialog)getUnsafeWindow();
    if (dialog!=null)
    {
      // Enter
      {
        KeyStroke stroke=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        JRootPane rootPane=dialog.getRootPane();
        InputMap inputMap=rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.remove(stroke);
        rootPane.getActionMap().remove(OKCancelPanelController.OK_COMMAND);
      }
      // Escape
      {
        KeyStroke stroke=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        JRootPane rootPane=dialog.getRootPane();
        InputMap inputMap=rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.remove(stroke);
        rootPane.getActionMap().remove(OKCancelPanelController.CANCEL_COMMAND);
      }
    }
  }

  protected JPanel buildFormPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new BorderLayout());
    return panel;
  }

  private void handleButton(String action)
  {
    if (OKCancelPanelController.OK_COMMAND.equals(action))
    {
      ok();
    }
  }

  protected void ok()
  {
    okImpl();
    dispose();
  }

  protected void okImpl()
  {
    // Nothing...
  }

  @Override
  protected void doWindowClosing()
  {
    ok();
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    // Remove shortcuts
    removeShortcuts();
    if (_okCancelController!=null)
    {
      _okCancelController.dispose();
      _okCancelController=null;
    }
    super.dispose();
  }
}
