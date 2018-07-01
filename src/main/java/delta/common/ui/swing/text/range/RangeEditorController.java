package delta.common.ui.swing.text.range;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.combobox.ItemSelectionListener;
import delta.common.utils.ListenersManager;

/**
 * Controller for a range editor panel.
 * @author DAM
 */
public class RangeEditorController
{
  /**
   * Disposition of gadgets.
   * @author DAM
   */
  public enum Disposition
  {
    /**
     * Horizontal.
     */
    HORIZONTAL,
    /**
     * Vertical.
     */
    VERTICAL
  }
  // Data
  private ListenersManager<RangeListener> _listeners;
  private Disposition _disposition;
  // GUI
  private JPanel _panel;
  private ComboBoxController<Integer> _minValue;
  private ComboBoxController<Integer> _maxValue;

  /**
   * Constructor.
   */
  public RangeEditorController()
  {
    _disposition=Disposition.HORIZONTAL;
    _listeners=new ListenersManager<RangeListener>();
  }

  /**
   * Get the listeners manager for range update listeners.
   * @return a listeners manager.
   */
  public ListenersManager<RangeListener> getListeners()
  {
    return _listeners;
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
    }
    return _panel;
  }

  /**
   * Get the minimum value.
   * @return A value or <code>null</code> if not set.
   */
  public Integer getMinValue()
  {
    return _minValue.getSelectedItem();
  }

  /**
   * Get the maximum value.
   * @return A value or <code>null</code> if not set.
   */
  public Integer getMaxValue()
  {
    return _maxValue.getSelectedItem();
  }

  /**
   * Set the current range.
   * @param minValue Min value (may be <code>null</code>).
   * @param maxValue Max value (may be <code>null</code>).
   */
  public void setCurrentRange(Integer minValue, Integer maxValue)
  {
    _minValue.selectItem(minValue);
    _maxValue.selectItem(maxValue);
  }

  /**
   * Set the possible values for min/max values.
   * @param values Possible values.
   */
  public void setRangeValues(List<Integer> values)
  {
    initValueChooser(_minValue,values);
    initValueChooser(_maxValue,values);
  }

  private void initValueChooser(ComboBoxController<Integer> controller, List<Integer> values)
  {
    controller.removeAllItems();
    controller.addEmptyItem(" ");
    for(Integer date : values)
    {
      controller.addItem(date,date.toString());
    }
  }

  private JPanel build()
  {
    // Build combo-boxes
    _minValue=new ComboBoxController<Integer>();
    _maxValue=new ComboBoxController<Integer>();

    // Build panel
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    {
      GridBagConstraints c=new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(2,2,2,2),0,0);
      panel.add(GuiFactory.buildLabel("Min:"),c);
      c.gridx=1;
      panel.add(_minValue.getComboBox(),c);
      if (_disposition==Disposition.VERTICAL)
      {
        c.gridy=1;c.gridx=0;
      }
      else
      {
        c.gridx++;
      }
      panel.add(GuiFactory.buildLabel("Max:"),c);
      c.gridx++;
      panel.add(_maxValue.getComboBox(),c);
      ItemSelectionListener<Integer> listenerMinDates=new ItemSelectionListener<Integer>()
      {
        @Override
        public void itemSelected(Integer selected)
        {
          rangeUpdated();
        }
      };
      _minValue.addListener(listenerMinDates);
      ItemSelectionListener<Integer> listenerMaxDates=new ItemSelectionListener<Integer>()
      {
        @Override
        public void itemSelected(Integer selected)
        {
          rangeUpdated();
        }
      };
      _maxValue.addListener(listenerMaxDates);
    }

    return panel;
  }

  private void rangeUpdated() {
    Integer minValue = _minValue.getSelectedItem();
    Integer maxValue = _maxValue.getSelectedItem();
    for(RangeListener listener : _listeners)
    {
      listener.rangeUpdated(this,minValue,maxValue);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    if (_listeners!=null)
    {
      _listeners.removeAllListeners();
      _listeners=null;
    }
    // Controllers
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    if (_minValue!=null)
    {
      _minValue.dispose();
      _minValue=null;
    }
    if (_maxValue!=null)
    {
      _maxValue.dispose();
      _maxValue=null;
    }
  }
}
