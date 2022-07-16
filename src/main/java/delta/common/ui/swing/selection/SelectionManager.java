package delta.common.ui.swing.selection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import delta.common.ui.swing.misc.Disposable;
import delta.common.utils.ListenersManager;

/**
 * Selection manager.
 * @author DAM
 * @param <POJO> Type of managed data.
 */
public abstract class SelectionManager<POJO> implements ListSelectionListener,Disposable
{
  private static final Logger LOGGER=Logger.getLogger(SelectionManager.class);

  // Listeners
  private ListenersManager<SelectionChangedListener<POJO>> _listeners;

  /**
   * Constructor.
   */
  protected SelectionManager()
  {
    _listeners=new ListenersManager<SelectionChangedListener<POJO>>();
  }

  /**
   * Get the Swing selection model.
   * @return the Swing selection model.
   */
  protected abstract ListSelectionModel getSelectionModel();

  /**
   * Get an element from a view index.
   * @param index View index.
   * @return An element.
   */
  protected abstract POJO resolveElement(int index);

  /**
   * Get the listeners manager for 'selection changed' events.
   * @return A listeners manager.
   */
  public ListenersManager<SelectionChangedListener<POJO>> getListeners()
  {
    return _listeners;
  }

  /**
   * Get the current selection.
   * @return a possibly empty but never <code>null</code> list of selected items.
   */
  public List<POJO> getSelection()
  {
    List<POJO> ret=new ArrayList<POJO>();
    ListSelectionModel lsm=getSelectionModel();
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
          POJO data=resolveElement(i);
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
    boolean isAdjusting=e.getValueIsAdjusting();
    if (isAdjusting)
    {
      return;
    }
    if (_listeners.getListenersCount()>0)
    {
      List<POJO> selectedElements=getSelection();
      if (LOGGER.isDebugEnabled())
      {
        LOGGER.debug("Selection elements count: "+selectedElements.size());
        for(POJO selectedElement : selectedElements)
        {
          LOGGER.debug("Selected element: "+selectedElement);
        }
      }
      SelectionChangedEvent<POJO> event=new SelectionChangedEvent<>(selectedElements);
      invokeSelectionChangedEvent(event);
    }
  }

  protected void invokeSelectionChangedEvent(SelectionChangedEvent<POJO> event)
  {
    for(SelectionChangedListener<POJO> listener : _listeners)
    {
      listener.selectionChanged(event);
    }
  }

  @Override
  public void dispose()
  {
    if (_listeners!=null)
    {
      _listeners.removeAllListeners();
      _listeners=null;
    }
  }
}
