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
   * Add a sort item.
   * @param id Column identifier.
   * @param ascending Ascending or not.
   */
  public void addSortItem(String id, boolean ascending)
  {
    _ids.add(id);
    _ascending.add(Boolean.valueOf(ascending));
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
   * Get a string representation of this sort.
   * @return A string representation that may be used in method <code>buildFromString</code>.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    int nbSortItems=_ids.size();
    for(int i=0;i<nbSortItems;i++)
    {
      if (i>0)
      {
        sb.append(SORT_ITEM_SEPARATOR);
      }
      if (_ascending.get(i).booleanValue())
      {
        sb.append(SORT_ASCENDING);
      }
      else
      {
        sb.append(SORT_DESCENDING);
      }
      sb.append(_ids.get(i));
    }
    return sb.toString();
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
      if (sortItem.length()>0)
      {
        sort._ids.add(sortItem);
        sort._ascending.add(Boolean.valueOf(ascending));
      }
    }
    return sort;
  }
}
