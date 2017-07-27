package delta.common.ui.swing.labels;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JLabel;

import delta.common.ui.swing.GuiFactory;

/**
 * Controller for an hyperlink label.
 * @author DAM
 */
public class HyperLinkController
{
  private JLabel _label;

  /**
   * Hyperlink type.
   * @author DAM
   */
  public enum TYPE
  {
    /**
     * Mail-to.
     */
    MAILTO,
    /**
     * Browse.
     */
    BROWSE
  }

  private TYPE _type;
  private String _text;
  private String _subject;

  /**
   * Constructor.
   * @param type Hyperlink type.
   */
  public HyperLinkController(TYPE type)
  {
    _type=type;
    _subject="";
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
    setText("");
  }

  /**
   * Get the managed label.
   * @return a label.
   */
  public JLabel getLabel()
  {
    return _label;
  }

  private void setText(String text)
  {
    _text=text;
    _label.setText("<html><a href=\"\">"+text+"</a></html>");
  }

  /**
   * Set the URL to browse.
   * @param url URL to browse.
   */
  public void setUrl(String url)
  {
    setText(url);
  }

  /**
   * Configure mail.
   * @param address Address to write to.
   * @param subject Subject of the draft mail.
   */
  public void configureMail(String address, String subject)
  {
    setText(address);
    _subject=subject;
  }

  private void doIt()
  {
    if (_type==TYPE.MAILTO)
    {
      try
      {
        String uri="mailto:"+_text;
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
    else if (_type==TYPE.BROWSE)
    {
      try
      {
        Desktop.getDesktop().browse(new URI(_text));
      }
      catch (Exception ex)
      {
        // It looks like there's a problem
      }
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _label=null;
  }
}
