package delta.common.ui.swing.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.io.PrintStreamToStringBridge;

/**
 * Drop target listener.
 * @param <T> Type of dropped data.
 * @author DAM
 */
class DNDManager<T> implements DropTargetListener
{
  private static final Logger LOGGER=Logger.getLogger(DNDManager.class);

  private final List<DataFlavor> _flavors;
  private final DataExtractor<T> _extractor;
  private final DropListener<T> _listener;

  DNDManager(final DataFlavor flavor, final DataExtractor<T> extractor, final DropListener<T> listener)
  {
    _flavors=new ArrayList<DataFlavor>(1);
    _flavors.add(flavor);
    _extractor=extractor;
    _listener=listener;
  }

  DNDManager(final List<DataFlavor> flavors, final DataExtractor<T> extractor, final DropListener<T> listener)
  {
    _flavors=new ArrayList<DataFlavor>(flavors);
    _extractor=extractor;
    _listener=listener;
  }

  /**
   * Handle 'drag enter'.
   * @param e DnD event.
   */
  public void dragEnter(final DropTargetDragEvent e)
  {
    if (LOGGER.isDebugEnabled())
    {
      final PrintStreamToStringBridge bridge=new PrintStreamToStringBridge();
      DNDTools.dumpEvent(e,bridge.getPrintStream());
      LOGGER.debug(bridge);
    }
    if (shouldAcceptDrag(e))
    {
      e.acceptDrag(DnDConstants.ACTION_COPY);
    }
    else
      e.rejectDrag();
  }

  /**
   * Handle 'drag over'.
   * @param e DnD event.
   */
  public void dragOver(final DropTargetDragEvent e)
  {
    dragEnter(e);
  }

  /**
   * Handle 'drag exit'.
   * @param e DnD event.
   */
  public void dragExit(final DropTargetEvent e)
  {
    // Nothing to do !
  }

  /**
   * Handle 'drag scroll'.
   * @param e DnD event.
   */
  public void dragScroll(final DropTargetDragEvent e)
  {
    // Nothing to do !
  }

  /**
   * Handle 'drop action changed'.
   * @param e DnD event.
   */
  public void dropActionChanged(final DropTargetDragEvent e)
  {
    dragEnter(e);
  }

  /**
   * Handle 'drop'.
   * @param e DnD event.
   */
  public void drop(final DropTargetDropEvent e)
  {
    if (LOGGER.isDebugEnabled())
    {
      final PrintStreamToStringBridge bridge=new PrintStreamToStringBridge();
      DNDTools.dumpEvent(e,bridge.getPrintStream());
      LOGGER.debug(bridge);
    }
    // Check to accept drop
    final DataFlavor flavor=chooseDropFlavor(e);
    if (flavor!=null)
    {
      e.acceptDrop(DnDConstants.ACTION_COPY);
    }
    else
    {
      e.rejectDrop();
      return;
    }

    final DropTargetContext targetContext=e.getDropTargetContext();
    boolean outcome=false;

    final T data=_extractor.extractData(e,flavor);
    if (data!=null)
    {
      _listener.drop(data,e);
      outcome=true;
    }
    targetContext.dropComplete(outcome);
  }

  private boolean shouldAcceptDrag(final DropTargetDragEvent e)
  {
    boolean ret=false;
    for(final DataFlavor flavor : _flavors)
    {
      if (e.isDataFlavorSupported(flavor))
      {
        ret=true;
        break;
      }
    }
    return ret;
  }

  private DataFlavor chooseDropFlavor(final DropTargetDropEvent e)
  {
    DataFlavor ret=null;
    for(final DataFlavor flavor : _flavors)
    {
      if (e.isDataFlavorSupported(flavor))
      {
        ret=flavor;
        break;
      }
    }
    return ret;
  }
}
