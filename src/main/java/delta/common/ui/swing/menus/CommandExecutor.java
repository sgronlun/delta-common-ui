package delta.common.ui.swing.menus;

/**
 * Interface of a command executor.
 * @author DAM
 */
public interface CommandExecutor
{
  /**
   * Invoke a command.
   * @param command Command identifier.
   */
  void invoke(String command);
}
