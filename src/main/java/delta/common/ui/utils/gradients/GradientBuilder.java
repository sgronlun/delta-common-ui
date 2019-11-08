package delta.common.ui.utils.gradients;

import java.awt.Color;

import delta.common.ui.utils.ColorUtils;

/**
 * Builder for color gradients.
 * @author DAM
 */
public class GradientBuilder
{
  /**
   * Build a color gradient.
   * @param from Start color.
   * @param to End color.
   * @param steps Number of colors in the gradient.
   * @return a array of <code>steps</code> colors.
   */
  public static Color[] gradient(Color from, Color to, int steps)
  {
    Color[] ret=new Color[steps];
    Color fromLinear=ColorUtils.sRGB2Linear(from);
    Color toLinear=ColorUtils.sRGB2Linear(to);
    float[] fromComponents=fromLinear.getColorComponents(null);
    float[] toComponents=toLinear.getColorComponents(null);
    float[] linearSteps=new float[3];
    for(int i=0;i<linearSteps.length;i++)
    {
      linearSteps[i]=(toComponents[i]-fromComponents[i])/(steps-1);
    }
    ret[0]=from;
    for(int i=1;i<steps-1;i++)
    {
      for(int j=0;j<3;j++) fromComponents[j]+=linearSteps[j];
      ret[i]=new Color(fromLinear.getColorSpace(),fromComponents,0);
    }
    ret[steps-1]=to;
    return ret;
  }
}
