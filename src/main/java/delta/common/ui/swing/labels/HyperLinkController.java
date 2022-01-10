package delta.common.ui.swing.labels;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import delta.common.ui.swing.GuiFactory;

/**
 * Controller for an hyperlink label.
 * @author DAM
 */
public class HyperLinkController
{
  private JLabel _label;

  private HyperLinkAction _action;

  /**
   * Constructor.
   * @param action Hyperlink action.
   */
  public HyperLinkController(HyperLinkAction action)
  {
    _action=action;
    _label=GuiFactory.buildLabel("");
    _label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    _label.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        doIt();
      }
    });
    String linkText=action.getLinkText();
    setText(linkText);
  }

  /**
   * Get the managed label.
   * @return a label.
   */
  public JLabel getLabel()
  {
    return _label;
  }

  /**
   * Set link text.
   * @param linkText Text to set.
   */
  public void setText(String linkText)
  {
    _label.setText("<html><a href=\"\">"+linkText+"</a></html>");
  }

  private void doIt()
  {
    _action.doClick(this);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _label=null;
  }
}
