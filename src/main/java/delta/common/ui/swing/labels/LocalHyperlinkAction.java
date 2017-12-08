package delta.common.ui.swing.labels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Hyperlink action that runs a local action listener.
 * @author DAM
 */
public class LocalHyperlinkAction implements HyperLinkAction
{
  private String _text;
  private ActionListener _listener;

  /**
   * Constructor.
   * @param text text to display in the link.
   * @param listener Listener to invoke on click.
   */
  public LocalHyperlinkAction(String text, ActionListener listener)
  {
    _text=text;
    _listener=listener;
  }

  public String getLinkText()
  {
    return _text;
  }

  public void doClick(HyperLinkController source)
  {
    ActionEvent e=new ActionEvent(source,1,"hyperlink");
    _listener.actionPerformed(e);
  }
}
