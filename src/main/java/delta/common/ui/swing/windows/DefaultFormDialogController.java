package delta.common.ui.swing.windows;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.OKCancelPanelController;

/**
 * Base class for form dialog controllers.
 * @param <T> Managed data.
 * @author DAM
 */
public class DefaultFormDialogController<T> extends DefaultDialogController implements ActionListener
{
  // Data
  private T _data;
  // Controllers
  private OKCancelPanelController _okCancelController;

  /**
   * Constructor.
   * @param parentController Parent controller.
   * @param data Data to edit.
   */
  public DefaultFormDialogController(WindowController parentController, T data)
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
    return dialog;
  }

  @Override
  protected JComponent buildContents()
  {
    JPanel panel=GuiFactory.buildPanel(new BorderLayout());
    JPanel dataPanel=buildFormPanel();
    panel.add(dataPanel,BorderLayout.CENTER);
    _okCancelController=new OKCancelPanelController();
    JPanel okCancelPanel=_okCancelController.getPanel();
    panel.add(okCancelPanel,BorderLayout.SOUTH);
    _okCancelController.getOKButton().addActionListener(this);
    _okCancelController.getCancelButton().addActionListener(this);
    return panel;
  }

  protected JPanel buildFormPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new BorderLayout());
    return panel;
  }

  public void actionPerformed(ActionEvent event)
  {
    String action=event.getActionCommand();
    if (OKCancelPanelController.OK_COMMAND.equals(action))
    {
      ok();
    }
    else if (OKCancelPanelController.CANCEL_COMMAND.equals(action))
    {
      cancel();
    }
  }

  protected void ok()
  {
    boolean inputOk=checkInput();
    if (inputOk)
    {
      okImpl();
      dispose();
    }
  }

  protected boolean checkInput()
  {
    return true;
  }

  protected void okImpl()
  {
    // Nothing...
  }

  protected void cancelImpl()
  {
    // Nothing...
  }

  protected void cancel()
  {
    cancelImpl();
    dispose();
    _data=null;
  }

  @Override
  protected void doWindowClosing()
  {
    cancel();
  }

  /**
   * Modal edition.
   * @return Edited data or <code>null</code> if unchanged.
   */
  public T editModal()
  {
    show(true);
    T data=_data;
    _data=null;
    return data;
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    super.dispose();
    if (_okCancelController!=null)
    {
      _okCancelController.dispose();
      _okCancelController=null;
    }
  }
}
