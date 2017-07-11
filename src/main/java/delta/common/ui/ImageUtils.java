package delta.common.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * Image-related utilities.
 * @author DAM
 */
public class ImageUtils
{
  private static final Logger _logger=Logger.getLogger(ImageUtils.class);

  /**
   * Load an image from a file.
   * @param inputFile Input file.
   * @return An image or <code>null</code> in case of error.
   */
  public static BufferedImage loadImage(File inputFile)
  {
    BufferedImage image=null;
    try
    {
      image=ImageIO.read(inputFile);
    }
    catch(IOException ioe)
    {
      _logger.error("Cannot load image file: "+inputFile,ioe);
    }
    return image;
  }
}
