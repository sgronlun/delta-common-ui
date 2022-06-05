package delta.common.ui.swing.tables;

import java.util.List;

import delta.common.ui.swing.misc.Disposable;
import delta.common.utils.misc.TypedProperties;

/**
 * Preferences management for generic tables.
 * @param <T> Type of managed table entries.
 * @author DAM
 */
public class TablePreferencesManager<T> implements Disposable
{
  /**
   * Name of the property for column IDs.
   */
  public static final String COLUMNS_PROPERTY="columns";
  /**
   * Name of the property for sort definition.
   */
  public static final String SORT_PROPERTY="sort";

  // Data
  private TypedProperties _prefs;
  // GUI
  private GenericTableController<T> _tableController;

  /**
   * Constructor.
   * @param tableController Table controller to use.
   */
  public TablePreferencesManager(GenericTableController<T> tableController)
  {
    _tableController=tableController;
  }

  /**
   * Set the preferences.
   * @param preferences Preferences to set.
   */
  public void setPreferences(TypedProperties preferences)
  {
    _prefs=preferences;
    applyPreferences();
  }

  /**
   * Apply preferences.
   */
  public void applyPreferences()
  {
    if (_prefs==null)
    {
      return;
    }
    // Columns
    List<String> columnIDs=getColumnIds();
    _tableController.getColumnsManager().setColumns(columnIDs);
    _tableController.updateColumns();
    // Sort
    Sort sort=getSort();
    if (sort!=null)
    {
      _tableController.setSort(sort);
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

  private Sort getSort()
  {
    String sortString=_prefs.getStringProperty(SORT_PROPERTY,null);
    Sort sort=null;
    if (sortString!=null)
    {
      sort=Sort.buildFromString(sortString);
    }
    return sort;
  }

  /**
   * Save preferences.
   */
  public void savePreferences()
  {
    if (_prefs!=null)
    {
      // Columns
      List<String> columnIds=_tableController.getColumnsManager().getSelectedColumnsIds();
      _prefs.setStringList(COLUMNS_PROPERTY,columnIds);
      // Sort
      Sort sort=_tableController.getCurrentSort();
      _prefs.setStringProperty(SORT_PROPERTY,sort.asString());
    }
  }

  @Override
  public void dispose()
  {
    _prefs=null;
    _tableController=null;
  }
}
