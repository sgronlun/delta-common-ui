package delta.common.ui.swing.icons;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for the management of application icons.
 * @author DAM
 */
public class ApplicationIcons
{
  private static List<String> _iconPaths=new ArrayList<String>();

  /**
   * Set the paths for the application icons.
   * @param iconPaths A list of paths.
   */
  public static void setApplicationIconPaths(List<String> iconPaths)
  {
    _iconPaths.clear();
    _iconPaths.addAll(iconPaths);
  }

  /**
   * Get a list of window icons.
   * @return a list of icons.
   */
  public static List<Image> getApplicationIcons()
  {
    List<Image> icons=new ArrayList<Image>();
    for(String iconPath : _iconPaths)
    {
      Image image=IconsManager.getImage(iconPath);
      if (image!=null)
      {
        icons.add(image);
      }
    }
    return icons;
  }
}
