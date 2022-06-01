package delta.common.ui.swing.tables.selection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import delta.common.ui.swing.tables.DataProvider;

/**
 * Selection manager.
 * @author DAM
 * @param <POJO> Type of managed data.
 */
public class SelectionListener<POJO> implements ListSelectionListener
{
  private static final Logger LOGGER=Logger.getLogger(SelectionListener.class);

  // Data
  private DataProvider<POJO> _dataProvider;
  private JTable _table;

  /**
   * Constructor.
   * @param dataProvider Data provider.
   * @param table Swing Table.
   */
  public SelectionListener(DataProvider<POJO> dataProvider, JTable table)
  {
    _dataProvider=dataProvider;
    _table=table;
    ListSelectionModel lsm=table.getSelectionModel();
    lsm.addListSelectionListener(this);
  }

  private List<POJO> getSelection()
  {
    List<POJO> ret=new ArrayList<POJO>();
    ListSelectionModel lsm=_table.getSelectionModel();
    if (lsm.isSelectionEmpty())
    {
      // Empty
    }
    else
    {
      // Find out which indexes are selected
      int minIndex=lsm.getMinSelectionIndex();
      int maxIndex=lsm.getMaxSelectionIndex();
      for (int i=minIndex;i<=maxIndex;i++)
      {
        if (lsm.isSelectedIndex(i))
        {
          int modelIndex=_table.convertRowIndexToModel(i);
          POJO data=_dataProvider.getAt(modelIndex);
          if (data!=null)
          {
            ret.add(data);
          }
        }
      }
    }
    return ret;
  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
    boolean isAdjusting = e.getValueIsAdjusting();
    if (isAdjusting)
    {
      return;
    }
    if (LOGGER.isDebugEnabled())
    {
      List<POJO> selectedElements=getSelection();
      LOGGER.debug("Selection elements count: "+selectedElements.size());
      for(POJO selectedElement : selectedElements)
      {
        LOGGER.debug("Selected element: "+selectedElement);
      }
    }
  }
}
