package delta.common.ui.swing.tables;

import java.util.Comparator;

import javax.swing.table.TableCellRenderer;

/**
 * Table column controller that proxies another one.
 * @author DAM
 * @param <SOURCE_POJO> Source POJO type.
 * @param <POJO> Intermediate POJO type.
 * @param <VALUE> Column value type.
 */
public class ProxiedTableColumnController<SOURCE_POJO,POJO,VALUE> implements TableColumnController<SOURCE_POJO,VALUE>
{
  private TableColumnController<POJO,VALUE> _controller;
  private CellDataProvider<SOURCE_POJO,VALUE> _dataProvider;
  private CellDataUpdater<SOURCE_POJO> _dataUpdater;

  /**
   * Constructor.
   * @param controller Proxied column.
   * @param proxiedProvider Provider to resolve a POJO from a source POJO.
   */
  public ProxiedTableColumnController(TableColumnController<POJO,VALUE> controller, final CellDataProvider<SOURCE_POJO,POJO> proxiedProvider)
  {
    _controller=controller;
    // Resolved cell data provider
    _dataProvider=new CellDataProvider<SOURCE_POJO,VALUE>()
    {
      @Override
      public VALUE getData(SOURCE_POJO p)
      {
        POJO pojo=proxiedProvider.getData(p);
        CellDataProvider<POJO,VALUE> innerProvider=_controller.getValueProvider();
        VALUE value=innerProvider.getData(pojo);
        return value;
      }
    };
    // Resolved cell data updater
    final CellDataUpdater<POJO> innerUpdater=_controller.getValueUpdater();
    if (innerUpdater!=null)
    {
      _dataUpdater=new CellDataUpdater<SOURCE_POJO>()
      {
        @Override
        public void setData(SOURCE_POJO p, Object data)
        {
          POJO pojo=proxiedProvider.getData(p);
          innerUpdater.setData(pojo,data);
        }
      };
    }
  }

  @Override
  public String getId()
  {
    return _controller.getId();
  }

  @Override
  public int getMinWidth()
  {
    return _controller.getMinWidth();
  }

  @Override
  public int getMaxWidth()
  {
    return _controller.getMaxWidth();
  }

  @Override
  public int getPreferredWidth()
  {
    return _controller.getPreferredWidth();
  }

  @Override
  public boolean isSortable()
  {
    return _controller.isSortable();
  }

  @Override
  public TableCellRenderer getCellRenderer()
  {
    return _controller.getCellRenderer();
  }

  @Override
  public TableCellRenderer getHeaderCellRenderer()
  {
    return _controller.getHeaderCellRenderer();
  }

  @Override
  public boolean isEditable()
  {
    return _controller.isEditable();
  }

  @Override
  public Boolean isUseToString()
  {
    return _controller.isUseToString();
  }

  @Override
  public Comparator<VALUE> getComparator()
  {
    return _controller.getComparator();
  }

  @Override
  public Class<VALUE> getDataType()
  {
    return _controller.getDataType();
  }

  @Override
  public String getHeader()
  {
    return _controller.getHeader();
  }

  @Override
  public CellDataProvider<SOURCE_POJO,VALUE> getValueProvider()
  {
    return _dataProvider;
  }

  @Override
  public CellDataUpdater<SOURCE_POJO> getValueUpdater()
  {
    return _dataUpdater;
  }

  @Override
  public String toString()
  {
    return _controller.toString();
  }
}
