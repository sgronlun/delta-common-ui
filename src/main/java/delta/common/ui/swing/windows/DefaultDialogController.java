package delta.common.ui.swing.windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Window;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.icons.ApplicationIcons;

/**
 * Default dialog controller.
 * @author DAM
 */
public class DefaultDialogController extends AbstractWindowController
{
  /**
   * Constructor.
   * @param parent Parent controller.
   */
  public DefaultDialogController(WindowController parent)
  {
    super(parent);
  }

  /**
   * Get the managed dialog.
   * @return the managed dialog.
   */
  public JDialog getDialog()
  {
    return (JDialog)getWindow();
  }

  /**
   * Build the managed window.
   * @return the managed window.
   */
  protected Window buildWindow()
  {
    return build();
  }

  protected JDialog build()
  {
    Window parentWindow=getParentWindow();

    JDialog dialog;
    if (parentWindow!=null)
    {
      dialog=new JDialog(parentWindow);
    }
    else
    {
      dialog=new JDialog();
    }
    JPanel backgroundPanel=GuiFactory.buildBackgroundPanel(new BorderLayout());
    dialog.setContentPane(backgroundPanel);
    List<Image> icons=ApplicationIcons.getApplicationIcons();
    dialog.setIconImages(icons);
    Container contentPane=dialog.getContentPane();
    JComponent component=buildContents();
    if (component!=null)
    {
      contentPane.add(component,BorderLayout.CENTER);
    }
    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    return dialog;
  }

  public void show()
  {
    show(false);
  }

  /**
   * Show the managed window.
   * @param modal Modality of the managed dialog.
   */
  public void show(boolean modal)
  {
    JDialog dialog=getDialog();
    dialog.setModal(modal);
    dialog.setVisible(true);
  }

  /**
   * Bring the managed window to front.
   */
  public void bringToFront()
  {
    JDialog dialog=getDialog();
    dialog.setVisible(true);
    dialog.toFront();
  }

  /**
   * Set window title.
   * @param title Title to set.
   */
  public void setTitle(String title)
  {
    JDialog dialog=getDialog();
    dialog.setTitle(title);
  }
}
