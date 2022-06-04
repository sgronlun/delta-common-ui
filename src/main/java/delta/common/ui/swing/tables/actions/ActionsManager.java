package delta.common.ui.swing.tables.actions;

import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.misc.Disposable;

/**
 * Manager for actions.
 * @author DAM
 * @param <T> Type of managed data.
 */
public class ActionsManager<T> implements Disposable
{
  private List<SimpleAction<T>> _actions;

  /**
   * Constructor.
   */
  public ActionsManager()
  {
    _actions=new ArrayList<SimpleAction<T>>();
  }

  /**
   * Add a double-click action.
   * @param action Action to add.
   */
  public void addDoubleClickAction(SimpleAction<T> action)
  {
    _actions.add(action);
  }

  /**
   * Invoke double-click actions on the given data item.
   * @param data Targeted data item.
   */
  public void invokeDoubleClickActions(T data)
  {
    for(SimpleAction<T> action : _actions)
    {
      action.doIt(data);
    }
  }

  @Override
  public void dispose()
  {
    _actions=null;
  }
}
