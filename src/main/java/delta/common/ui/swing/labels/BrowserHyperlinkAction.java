package delta.common.ui.swing.labels;

import java.awt.Desktop;
import java.net.URI;

/**
 * Hyperlink action that shows an URL in the default browser.
 * @author DAM
 */
public class BrowserHyperlinkAction implements HyperLinkAction
{
  private String _targetUrl;
  private String _linkText;

  /**
   * Constructor.
   * @param targetUrl Target URL.
   * @param linkText Text to display in the link.
   */
  public BrowserHyperlinkAction(String targetUrl, String linkText)
  {
    _targetUrl=targetUrl;
    _linkText=linkText;
  }

  public String getLinkText()
  {
    return _linkText;
  }

  public void doClick(HyperLinkController source)
  {
    try
    {
      Desktop.getDesktop().browse(new URI(_targetUrl));
    }
    catch (Exception ex)
    {
      // It looks like there's a problem
    }
  }
}
