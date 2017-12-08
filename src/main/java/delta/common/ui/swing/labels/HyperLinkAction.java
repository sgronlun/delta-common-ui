package delta.common.ui.swing.labels;

/**
 * Interfac of an hyperlink action.
 * @author DAM
 */
public interface HyperLinkAction
{
  /**
   * Get the text to display in the link.
   * @return A text.
   */
  String getLinkText();

  /**
   * Perform an action when the user clicks on the link.
   * @param source Source hyperlink.
   */
  void doClick(HyperLinkController source);
}
