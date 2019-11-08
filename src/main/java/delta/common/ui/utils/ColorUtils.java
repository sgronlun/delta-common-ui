package delta.common.ui.utils;

import java.awt.Color;
import java.awt.color.ColorSpace;

/**
 * Color utilities.
 * @author DAM
 */
public class ColorUtils
{
  /**
   * Convert a sRGB color into a linear RGB color.
   * @param c Input color.
   * @return the result color.
   */
  public static Color sRGB2Linear(Color c)
  {
    ColorSpace linear=ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);
    float[] sRGBComponents=c.getColorComponents(null);
    float[] linearComponents=linear.fromRGB(sRGBComponents);
    Color ret=new Color(linear,linearComponents,0);
    return ret;
  }

  /**
   * Convert a sRGB color into a linear RGB color.
   * @param c Input color.
   * @return the result color.
   */
  public static Color linear2sRGB(Color c)
  {
    float[] components=c.getColorComponents(null);
    ColorSpace colorSpace=ColorSpace.getInstance(ColorSpace.CS_sRGB);
    float[] sRGBComponents=colorSpace.toRGB(components);
    Color ret=new Color(colorSpace,sRGBComponents,0);
    return ret;
  }
}
