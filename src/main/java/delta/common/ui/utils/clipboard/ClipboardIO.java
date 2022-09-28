package delta.common.ui.utils.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * I/O methods with the system clipboard. 
 * @author DAM
 */
public class ClipboardIO
{
  /**
   * Write some text to the system clipboard.
   * @param text Text to write.
   */
  public static void writeText(String text)
  {
    StringSelection stringSelection = new StringSelection(text);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }
}
