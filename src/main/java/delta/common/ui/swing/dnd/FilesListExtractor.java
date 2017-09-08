package delta.common.ui.swing.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Extracts a list of files from a DND event.
 * @author DAM
 */
public class FilesListExtractor implements DataExtractor<List<File>>
{
  private static final Logger LOGGER=Logger.getLogger(FilesListExtractor.class);

  public List<File> extractData(final DropTargetDropEvent e, final DataFlavor flavor)
  {
    List<File> files=null;
    try
    {
      final Transferable t=e.getTransferable();
      final Object o=t.getTransferData(flavor);
      if (o!=null)
      {
        if (LOGGER.isDebugEnabled())
        {
          LOGGER.debug("Data class ["+o.getClass()+"]");
          LOGGER.debug("Data ["+o+"]");
        }
        if (o instanceof List<?>)
        {
          files=new ArrayList<File>();
          for(final Object file:(List<?>)o)
          {
            files.add((File)file);
          }
        }
        else if (o instanceof String)
        {
          files=textURIListToFileList((String)o);
        }
        else
        {
          LOGGER.error("Cannot extract data !");
        }
      }
      else
      {
        LOGGER.error("Data is null !");
      }
    }
    catch (final Exception exception)
    {
      LOGGER.error("",exception);
    }
    return files;
  }

  private static List<File> textURIListToFileList(final String data)
  {
    final List<File> list=new ArrayList<File>();
    for(final StringTokenizer st=new StringTokenizer(data,"\r\n");st.hasMoreTokens();)
    {
      final String s=st.nextToken();
      if (s.startsWith("#"))
      {
        // the line is a comment (as per the RFC 2483)
        continue;
      }
      try
      {
        final URI uri=new URI(s);
        final File file=new File(uri);
        list.add(file);
      }
      catch (final URISyntaxException e)
      {
        // Malformed URI
        LOGGER.error("Malformed URI ["+s+"]",e);
      }
      catch (final IllegalArgumentException e)
      {
        // The URI is not a valid 'file:' URI. Ignore.
        if (LOGGER.isDebugEnabled())
        {
          LOGGER.debug("Ignored URI ["+s+"]");
        }
      }
    }
    return list;
  }
}
