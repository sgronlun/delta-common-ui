package delta.common.ui.swing.labels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * Style of a label line.
 * @author DAM
 */
public class LabelLineStyle
{
  private static final Font DEFAULT_FONT=new JLabel().getFont();

  /**
   * Get the default line style.
   */
  public static final LabelLineStyle DEFAULT_LINE_STYLE=new LabelLineStyle();

  private boolean _halo;
  private Font _font;
  private Color _foregroundColor;
  private Color _haloColor;

  /**
   * Constructor.
   */
  public LabelLineStyle()
  {
    _halo=false;
    _font=DEFAULT_FONT;
    _foregroundColor=Color.BLACK;
    _haloColor=Color.BLACK;
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public LabelLineStyle(LabelLineStyle source)
  {
    _halo=source._halo;
    _font=source._font;
    _foregroundColor=source._foregroundColor;
    _haloColor=source._haloColor;
  }

  /**
   * Indicates if a halo is used.
   * @return <code>true</code> to use a halo, <code>false</code> otherwise.
   */
  public boolean isHalo()
  {
    return _halo;
  }

  /**
   * Get the font to use.
   * @return the font to use.
   */
  public Font getFont()
  {
    return _font;
  }

  /**
   * Get the foreground color to use.
   * @return a color.
   */
  public Color getForegroundColor()
  {
    return _foregroundColor;
  }

  /**
   * Get the halo color.
   * @return the color to use for the halo.
   */
  public Color getHaloColor()
  {
    return _haloColor;
  }

  /**
   * Set the halo state.
   * @param enabled Halo state.
   * @return A new style.
   */
  public LabelLineStyle setHalo(boolean enabled)
  {
    if (enabled!=_halo)
    {
      LabelLineStyle ret=new LabelLineStyle(this);
      ret._halo=enabled;
      return ret;
    }
    return this;
  }

  /**
   * Set the font size.
   * @param size Size to set.
   * @return A new style.
   */
  public LabelLineStyle setFontSize(float size)
  {
    LabelLineStyle ret=new LabelLineStyle(this);
    ret._font=_font.deriveFont(size);
    return ret;
  }

  /**
   * Set the foreground color.
   * @param foregroundColor Color to set.
   * @return A new style.
   */
  public LabelLineStyle setForegroundColor(Color foregroundColor)
  {
    LabelLineStyle ret=new LabelLineStyle(this);
    ret._foregroundColor=foregroundColor;
    return ret;
  }
}
