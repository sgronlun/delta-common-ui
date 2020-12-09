package delta.common.ui.swing.draw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * Methods to draw arrows.
 * @author DAM
 */
public class Arrows
{
  /**
   * Draw an arrow at toX,toY using angle defined by line from->to.
   * @param g Graphics to draw to.
   * @param fromX From X.
   * @param fromY From Y.
   * @param toX To X.
   * @param toY To Y.
   */
  public static void drawArrow(Graphics2D g, int fromX, int fromY, int toX, int toY)
  {
    double angle = Math.atan2(toY-fromY, toX-fromX);
    drawArrow(g,toX,toY,angle);
  }

  /**
   * Draw an arrow at x,y using th given angle.
   * @param g Graphics to draw to.
   * @param x X.
   * @param y Y.
   * @param angle Angle to use for arrow.
   */
  public static void drawArrow(Graphics2D g, int x, int y, double angle)
  {
    g=(Graphics2D)g.create();
    g.translate(x,y);
    g.rotate(angle);
    float arrowRatio=0.5f;
    float arrowLength=10.0f;

    BasicStroke stroke=(BasicStroke)g.getStroke();

    float endX=arrowLength/2;
    float veeX=endX-stroke.getLineWidth()*0.5f/arrowRatio;

    // Path
    Path2D.Float path=new Path2D.Float();
    float waisting=0.5f;
    float waistX=endX-arrowLength*0.5f;
    float waistY=arrowRatio*arrowLength*0.5f*waisting;
    float arrowWidth=arrowRatio*arrowLength;

    path.moveTo(veeX-arrowLength,-arrowWidth);
    path.quadTo(waistX,-waistY,endX,0.0f);
    path.quadTo(waistX,waistY,veeX-arrowLength,arrowWidth);

    // End of arrow is pinched in
    path.lineTo(veeX-arrowLength*0.75f,0.0f);
    path.lineTo(veeX-arrowLength,-arrowWidth);
    g.fill(path);
    g.dispose();
  }
}
