package delta.common.ui.swing.tables;

import java.util.ArrayList;
import java.util.List;

/**
 * Sort specification.
 * @author DAM
 */
public class Sort
{
  /**
   * Sort items separator.
   */
  public static final String SORT_ITEM_SEPARATOR=";";
  /**
   * Marker for 'ascending' sort.
   */
  public static final String SORT_ASCENDING="+";
  /**
   * Marker for 'descending' sort.
   */
  public static final String SORT_DESCENDING="-";

  private List<String> _ids;
  private List<Boolean> _ascending;

  /**
   * Constructor.
   */
  public Sort()
  {
    _ids=new ArrayList<String>();
    _ascending=new ArrayList<Boolean>();
  }

  /**
   * Get the number of items in this sort.
   * @return An items count.
   */
  public int getNumberOfItems()
  {
    return _ids.size();
  }

  /**
   * Get the column identifier at the given index.
   * @param index Index to use.
   * @return A column identifier.
   */
  public String getColumnId(int index)
  {
    return _ids.get(index);
  }

  /**
   * Get the 'ascending' flag at the given index.
   * @param index Index to use.
   * @return the ascending flag at the index.
   */
  public boolean isAscending(int index)
  {
    return _ascending.get(index).booleanValue();
  }

  /**
   * Build a sort specification from a string.
   * @param sortSpec Sort specification string.
   * @return Newly built sort specification.
   */
  public static Sort buildFromString(String sortSpec)
  {
    Sort sort=new Sort();
    String[] sortItems=sortSpec.split(SORT_ITEM_SEPARATOR);
    for(String sortItem : sortItems)
    {
      boolean ascending=true;
      if (sortItem.startsWith(SORT_ASCENDING))
      {
        ascending=true;
        sortItem=sortItem.substring(SORT_ASCENDING.length());
      }
      else if (sortItem.startsWith(SORT_DESCENDING))
      {
        ascending=false;
        sortItem=sortItem.substring(SORT_DESCENDING.length());
      }
      sort._ids.add(sortItem);
      sort._ascending.add(Boolean.valueOf(ascending));
    }
    return sort;
  }
}
