package delta.common.ui.swing.editors.numbers;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.progress.ProgressBarController;
import delta.common.ui.swing.text.IntegerEditionController;
import delta.common.ui.swing.text.NumberEditionController;
import delta.common.ui.swing.text.NumberListener;

/**
 * Integer edition facility that inclues:
 * <ul>
 * <li>a progress bar to show the value,
 * <li>an integer editor to edit the value.
 * </ul>
 * @author DAM
 */
public class ProgressAndNumberEditorController
{
  // Controllers
  private ProgressBarController _progress;
  private IntegerEditionController _editor;
  // UI
  private JPanel _panel;

  /**
   * Constructor.
   */
  public ProgressAndNumberEditorController()
  {
    _progress=new ProgressBarController();
    JTextField textField=GuiFactory.buildTextField("");
    _editor=new IntegerEditionController(textField);
    setRange(0,100);
    _panel=build();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new FlowLayout());
    panel.add(_progress.getProgressBar());
    panel.add(_editor.getTextField());
    NumberListener<Integer> listener=new NumberListener<Integer>()
    {
      @Override
      public void valueChanged(NumberEditionController<Integer> source, Integer newValue)
      {
        int valueToSet=_editor.getMinValue().intValue();
        if (newValue!=null)
        {
          valueToSet=newValue.intValue();
        }
        _progress.setValue(valueToSet);
      }
    };
    _editor.addValueListener(listener);
    return panel;
  }

  /**
   * Get the managed progress bar.
   * @return the managed progress bar.
   */
  public ProgressBarController getProgressBar()
  {
    return _progress;
  }

  /**
   * Get the managed integer editor.
   * @return the managed integer editor.
   */
  public IntegerEditionController getEditor()
  {
    return _editor;
  }

  /**
   * Get the edited value.
   * @return A value or <code>null</code> if not valid.
   */
  public Integer getValue()
  {
    return _editor.getValue();
  }

  /**
   * Set the currently displayed value.
   * @param value Value to set.
   */
  public void setValue(int value)
  {
    _progress.setValue(value);
    _editor.setValue(Integer.valueOf(value));
  }

  /**
   * Set the range of possible values.
   * @param min Minimum value.
   * @param max Maximum value.
   */
  public void setRange(int min, int max)
  {
    _progress.setRange(min,max);
    _editor.setValueRange(Integer.valueOf(min),Integer.valueOf(max));
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Controllers
    if (_progress!=null)
    {
      _progress.dispose();
      _progress=null;
    }
    if (_editor!=null)
    {
      _editor.dispose();
      _editor=null;
    }
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
  }
}
