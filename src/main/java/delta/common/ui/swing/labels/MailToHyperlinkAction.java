package delta.common.ui.swing.labels;

import java.awt.Desktop;
import java.net.URI;

/**
 * Hyperlink action to send a mail.
 * @author DAM
 */
public class MailToHyperlinkAction implements HyperLinkAction
{
  private String _address;
  private String _subject;

  /**
   * Constructor.
   * @param address Address to write to.
   * @param subject Subject of the draft mail.
   */
  public MailToHyperlinkAction(String address, String subject)
  {
    _address=address;
    _subject=subject;
  }

  public String getLinkText()
  {
    return _address;
  }

  public void doClick(HyperLinkController source)
  {
    try
    {
      String uri="mailto:"+_address;
      if ((_subject!=null)&&(_subject.length()>0))
      {
        uri=uri+"?subject="+_subject;
      }
      Desktop.getDesktop().mail(new URI(uri));
    }
    catch (Exception ex)
    {
      // It looks like there's a problem
    }
  }

}
