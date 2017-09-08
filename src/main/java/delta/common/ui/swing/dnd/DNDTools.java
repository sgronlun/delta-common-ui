package delta.common.ui.swing.dnd;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Tools related to Drag&drop.
 * @author DAM
 */
public class DNDTools
{
  private static final Logger LOGGER=Logger.getLogger(DNDTools.class);

  /**
   * Install a files drop listener on a graphical component.
   * @param c Targeted component.
   * @param listener Listener to call.
   */
  public static void installFilesDropListener(final Component c, final DropListener<List<File>> listener)
  {
    try
    {
      FilesListExtractor filesExtractor=new FilesListExtractor();
      List<DataFlavor> flavors=new ArrayList<DataFlavor>();
      flavors.add(DataFlavor.javaFileListFlavor);
      DataFlavor uriListFlavor=new DataFlavor("text/uri-list;class=java.lang.String");
      flavors.add(uriListFlavor);
      DNDManager<List<File>> dndManager=new DNDManager<List<File>>(flavors,filesExtractor,listener);
      DropTarget dropTarget=new DropTarget(c,dndManager);
      /*
      DropTarget dropTarget=c.getDropTarget();
      if (dropTarget==null)
      {
        dropTarget=new DropTarget(c,dndManager);
      }
      else
      {
        dropTarget.addDropTargetListener(dndManager);
      }
      */
      c.setDropTarget(dropTarget);
    }
    catch (final Exception e)
    {
      LOGGER.error("",e);
    }
  }

  /**
   * Dump the contents of a DnD event to the specified output stream.
   * @param e DnD event to use.
   * @param out Output stream.
   */
  public static void dumpEvent(final DropTargetDropEvent e, final PrintStream out)
  {
    final Point p=e.getLocation();
    out.println("Location: x="+p.x+", y="+p.y);
    final Object source=e.getSource();
    out.println("Source: "+source);
    final int dropAction=e.getDropAction();
    out.println("Drop action: "+getActionName(dropAction));
    final int sourceActions=e.getSourceActions();
    out.println("Source actions: "+getActionName(sourceActions));
    final DataFlavor[] flavors=e.getCurrentDataFlavors();
    if ((flavors!=null)&&(flavors.length>0))
    {
      for(int i=0;i<flavors.length;i++)
      {
        dumpFlavor(flavors[i],out);
      }
    }
  }

  /**
   * Dump the contents of a DnD event to the specified output stream.
   * @param e DnD event to use.
   * @param out Output stream.
   */
  public static void dumpEvent(final DropTargetDragEvent e, final PrintStream out)
  {
    final Point p=e.getLocation();
    out.println("Location: x="+p.x+", y="+p.y);
    final Object source=e.getSource();
    out.println("Source: "+source);
    final int dropAction=e.getDropAction();
    out.println("Drop action: "+getActionName(dropAction));
    final int sourceActions=e.getSourceActions();
    out.println("Source actions: "+getActionName(sourceActions));
    final DataFlavor[] flavors=e.getCurrentDataFlavors();
    if ((flavors!=null)&&(flavors.length>0))
    {
      for(int i=0;i<flavors.length;i++)
      {
        dumpFlavor(flavors[i],out);
      }
    }
  }

  /**
   * Dump the contents the given flavor to the specified output stream.
   * @param flavor Data flavor to use.
   * @param out Output stream.
   */
  public static void dumpFlavor(final DataFlavor flavor, final PrintStream out)
  {
    out.println("Flavor: "+flavor.getHumanPresentableName());
    out.println("   Mime type: "+flavor.getMimeType());
    out.println("   Representation class: "+flavor.getRepresentationClass());
  }

  private static String getActionName(final int action)
  {
    if (action==DnDConstants.ACTION_NONE)
    {
      return "NONE";
    }
    final StringBuilder sb=new StringBuilder();
    if ((action&DnDConstants.ACTION_COPY)!=0)
    {
      sb.append("COPY ");
    }
    if ((action&DnDConstants.ACTION_MOVE)!=0)
    {
      sb.append("MOVE ");
    }
    if ((action&DnDConstants.ACTION_LINK)!=0)
    {
      sb.append("LINK/REFERENCE ");
    }
    return sb.toString().trim();
  }
}
