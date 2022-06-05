package delta.common.ui.swing.tables.panel;

/**
 * Configuration of the generic table panel.
 * @author DAM
 */
public class GenericTablePanelConfiguration
{
  private String _borderTitle;

  /**
   * Constructor.
   */
  public GenericTablePanelConfiguration()
  {
    _borderTitle="Elements";
  }

  /**
   * Get the title to use for the table border.
   * @return A title or <code>null</code> for no border.
   */
  public String getBorderTitle()
  {
    return _borderTitle;
  }

  /**
   * Set the title to use for the table border.
   * @param borderTitle Title to use.
   */
  public void setBorderTitle(String borderTitle)
  {
    _borderTitle=borderTitle;
  }
}
