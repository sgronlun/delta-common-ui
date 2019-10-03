package delta.common.ui.swing.navigator;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

/**
 * Navigation history.
 * @author DAM
 */
public class NavigationHistory
{
  /**
   * Known pages.
   */
  private List<PageIdentifier> _pages;
  /**
   * Current page.
   */
  private PageIdentifier _currentPage;

  /**
   * Constructor.
   */
  public NavigationHistory()
  {
    _pages=new ArrayList<PageIdentifier>();
    _currentPage=null;
  }

  /**
   * Get the 'previous' page identifier.
   * @return a page identifier or <code>null</code> if none.
   */
  public PageIdentifier getPrevious()
  {
    PageIdentifier ret=null;
    int index=getCurrentPageIndex();
    if (index>0)
    {
      ret=_pages.get(index-1);
    }
    return ret;
  }

  /**
   * Get the 'next' page identifier.
   * @return a page identifier or <code>null</code> if none.
   */
  public PageIdentifier getNext()
  {
    PageIdentifier ret=null;
    int index=getCurrentPageIndex();
    if (index>=0)
    {
      int nbPages=_pages.size();
      if (index+1<nbPages)
      {
        ret=_pages.get(index+1);
      }
    }
    return ret;
  }

  private int getCurrentPageIndex()
  {
    int ret=-1;
    if (_currentPage!=null)
    {
      ret=_pages.indexOf(_currentPage);
    }
    return ret;
  }

  /**
   * Set current page.
   * @param pageIdentifier Identifier of the page to set.
   */
  public void setPage(PageIdentifier pageIdentifier)
  {
    int index=getCurrentPageIndex();
    if (index==-1)
    {
      // No page yet
      _pages.add(pageIdentifier);
      _currentPage=pageIdentifier;
      return;
    }
    // Is known page?
    int knownIndex=_pages.indexOf(pageIdentifier);
    if (knownIndex!=-1)
    {
      _currentPage=pageIdentifier;
      return;
    }
    // Navigate to an unknown page
    int nbPages=_pages.size();
    // Remove all pages after the current page
    int nbPagesToRemove=nbPages-index-1;
    for(int i=0;i<nbPagesToRemove;i++)
    {
      _pages.remove(index+1);
    }
    // Add given page and set it as current page
    _pages.add(pageIdentifier);
    _currentPage=pageIdentifier;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Page history:").append(EndOfLine.NATIVE_EOL);
    int nbPages=_pages.size();
    for(int i=0;i<nbPages;i++)
    {
      PageIdentifier page=_pages.get(i);
      boolean isCurrent=(page==_currentPage);
      if (isCurrent)
      {
        sb.append("* ");
      }
      sb.append(page).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
