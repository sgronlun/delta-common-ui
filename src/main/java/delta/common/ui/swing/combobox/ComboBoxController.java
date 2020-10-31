package delta.common.ui.swing.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import delta.common.ui.swing.GuiFactory;
import delta.common.utils.NumericTools;

/**
 * Controller for a combo box.
 * @param <T> Type of managed data.
 * @author DAM
 */
public class ComboBoxController<T>
{
  private JComboBox<ComboBoxItem<T>> _comboBox;
  private List<ComboBoxItem<T>> _items;
  private List<ItemSelectionListener<T>> _listeners;
  private Class<T> _type;
  private boolean _listenersEnabled;

  /**
   * Constructor.
   */
  public ComboBoxController()
  {
    this(false,null);
  }

  /**
   * Constructor.
   * @param editable Editable or not.
   * @param type Managed type.
   */
  public ComboBoxController(boolean editable, Class<T> type)
  {
    _comboBox=GuiFactory.buildComboBox();
    _comboBox.setEditable(editable);
    _items=new ArrayList<ComboBoxItem<T>>();
    _listeners=new ArrayList<ItemSelectionListener<T>>();
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        callListeners();
      }
    };
    _comboBox.addActionListener(al);
    _type=type;
    _listenersEnabled=true;
  }

  /**
   * Get the managed combo-box.
   * @return the managed combo-box.
   */
  public JComboBox<ComboBoxItem<T>> getComboBox()
  {
    return _comboBox;
  }

  /**
   * Add empty item.
   * @param label Label for the empty item.
   */
  public void addEmptyItem(String label)
  {
    ComboBoxItem<T> item=new ComboBoxItem<T>(null,label);
    _items.add(item);
    _comboBox.addItem(item);
  }

  /**
   * Add a new item.
   * @param data Data item.
   * @param label Label to use to display this item.
   */
  public void addItem(T data, String label)
  {
    ComboBoxItem<T> item=new ComboBoxItem<T>(data,label);
    _items.add(item);
    _comboBox.addItem(item);
  }

  /**
   * Update the items displayed in this combo-box.
   * Keeps the selection if possible.
   * @param items Items to set.
   */
  public void updateItems(List<ComboBoxItem<T>> items)
  {
    _listenersEnabled=false;
    // Try to keep selection, if any
    ComboBoxItem<T> itemToSelect=null;
    int index=_comboBox.getSelectedIndex();
    if (index!=-1)
    {
      T selectedItem=getSelectedItem();
      for(ComboBoxItem<T> item : items)
      {
        if (equal(item.getItem(),selectedItem))
        {
          itemToSelect=item;
        }
      }
    }
    removeAllItems();
    for(ComboBoxItem<T> item : items)
    {
      _items.add(item);
      _comboBox.addItem(item);
    }
    if ((index!=-1) && (itemToSelect!=null))
    {
      _comboBox.setSelectedItem(itemToSelect);
      _listenersEnabled=true;
    }
    else
    {
      _listenersEnabled=true;
      if (items.size()>0)
      {
        _comboBox.setSelectedItem(items.get(0));
      }
      else
      {
        _comboBox.setSelectedIndex(-1);
      }
    }
  }

  /**
   * Remove an item.
   * @param data Item to remove.
   */
  public void removeItem(T data)
  {
    ComboBoxItem<T> item=getItemForData(data);
    if (item!=null)
    {
      _comboBox.removeItem(item);
      _items.remove(item);
    }
  }

  /**
   * Remove all items.
   */
  public void removeAllItems()
  {
    _items.clear();
    _comboBox.removeAllItems();
  }

  /**
   * Get the list of items in this combo-box.
   * @return A possibly empty but not <code>null</code> list of items.
   */
  public List<T> getItems()
  {
    List<T> ret=new ArrayList<T>();
    for(ComboBoxItem<T> item : _items)
    {
      ret.add(item.getItem());
    }
    return ret;
  }

  /**
   * Get the currently selected item.
   * @return A data item or <code>null</code> if nothing selected.
   */
  public T getSelectedItem()
  {
    T ret=null;
    int index=_comboBox.getSelectedIndex();
    if (index!=-1)
    {
      ComboBoxItem<T> item=_items.get(index);
      ret=item.getItem();
    }
    else
    {
      if (_comboBox.isEditable())
      {
        Object itemValue=_comboBox.getSelectedItem();
        if (itemValue!=null)
        {
          ret=convert(itemValue);
        }
      }
    }
    return ret;
  }

  private ComboBoxItem<T> getItemForData(T data)
  {
    for(ComboBoxItem<T> item : _items)
    {
      if (equal(item.getItem(),data))
      {
        return item;
      }
    }
    return null;
  }

  private boolean equal(T t1, T t2)
  {
    if (t1==null)
    {
      return t2==null;
    }
    return t1.equals(t2);
  }

  @SuppressWarnings("unchecked")
  private T convert(Object value)
  {
    if (value instanceof String)
    {
      if (Integer.class.equals(_type))
      {
        Integer intValue=NumericTools.parseInteger((String)value,false);
        return (T)intValue;
      }
    }
    return null;
  }

  /**
   * Select an item.
   * @param data Data item to select.
   */
  public void selectItem(T data)
  {
    ComboBoxItem<T> item=getItemForData(data);
    _comboBox.setSelectedItem(item);
  }

  /**
   * Select an item (does not call the listeners).
   * @param data Item to select.
   */
  public void setSelectedItem(T data)
  {
    ComboBoxItem<T> item=getItemForData(data);
    _listenersEnabled=false;
    _comboBox.setSelectedItem(item);
    _listenersEnabled=true;
  }

  /**
   * Add a listener for item selection.
   * @param listener Listener to add.
   */
  public void addListener(ItemSelectionListener<T> listener)
  {
    _listeners.add(listener);
  }

  /**
   * Remove a listener for item selection.
   * @param listener Listener to remove.
   */
  public void removeListener(ItemSelectionListener<T> listener)
  {
    _listeners.remove(listener);
  }

  private void callListeners()
  {
    if (!_listenersEnabled)
    {
      return;
    }
    if (_listeners!=null)
    {
      T item=getSelectedItem();
      for(ItemSelectionListener<T> listener : _listeners)
      {
        listener.itemSelected(item);
      }
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _comboBox=null;
    _items=null;
    _listeners=null;
    _type=null;
  }
}
