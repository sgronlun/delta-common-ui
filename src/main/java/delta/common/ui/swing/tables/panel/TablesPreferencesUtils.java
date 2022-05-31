package delta.common.ui.swing.tables.panel;

import java.util.List;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.utils.misc.TypedProperties;

/**
 * Preferences management for generic tables.
 * @param <T> Type of managed table entries.
 * @author DAM
 */
public class TablesPreferencesUtils<T>
{
  /**
   * Name of the property for column IDs.
   */
  public static final String COLUMNS_PROPERTY="columns";

  // Data
  private TypedProperties _prefs;
  // GUI
  private GenericTableController<T> _tableController;

  /**
   * Constructor.
   * @param prefs Preferences.
   * @param tableController Table controller to use.
   */
  public TablesPreferencesUtils(TypedProperties prefs, GenericTableController<T> tableController)
  {
    _prefs=prefs;
    _tableController=tableController;
  }

  /**
   * Apply preferences.
   */
  public void applyPreferences()
  {
    if ((_prefs!=null) && (_tableController!=null))
    {
      List<String> columnIDs=getColumnIds();
      _tableController.getColumnsManager().setColumns(columnIDs);
    }
  }

  private List<String> getColumnIds()
  {
    List<String> columnIds=_prefs.getStringList(COLUMNS_PROPERTY);
    if (columnIds==null)
    {
      columnIds=_tableController.getColumnsManager().getDefaultColumnsIds();
    }
    return columnIds;
  }

  /**
   * Save preferences.
   */
  public void savePreferences()
  {
    if ((_prefs!=null) && (_tableController!=null))
    {
      List<String> columnIds=_tableController.getColumnsManager().getSelectedColumnsIds();
      _prefs.setStringList(COLUMNS_PROPERTY,columnIds);
    }
  }
}
