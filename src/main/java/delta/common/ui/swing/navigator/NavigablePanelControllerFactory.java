package delta.common.ui.swing.navigator;

/**
 * Factory for navigable panel controllers.
 * @author DAM
 */
public interface NavigablePanelControllerFactory
{
  /**
   * Build a controller for the given page.
   * @param pageId Page identifier.
   * @return A controller or <code>null</code> if not supported.
   */
  NavigablePanelController build(PageIdentifier pageId);
}
