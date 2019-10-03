package delta.common.ui.swing.navigator;

import java.util.HashMap;
import java.util.Map;

import delta.common.utils.NumericTools;

/**
 * Page identifier.
 * @author DAM
 */
public class PageIdentifier
{
  /**
   * "ID" parameter (Integer).
   */
  public static final String ID_PARAMETER = "id";

  private String _baseAddress;
  private Map<String,String> _parameters;

  /**
   * Constructor.
   */
  public PageIdentifier()
  {
    _baseAddress="";
    _parameters=new HashMap<String,String>();
  }

  /**
   * Constructor.
   * @param dataType Data type identifier.
   * @param id Instance identifier.
   */
  public PageIdentifier(String dataType, int id)
  {
    this();
    _baseAddress=dataType;
    _parameters.put(ID_PARAMETER,String.valueOf(id));
  }

  /**
   * Get the base address for the page.
   * @return a base address.
   */
  public String getBaseAddress()
  {
    return _baseAddress;
  }

  /**
   * Set base address.
   * @param baseAddress Base address.
   */
  public void setBaseAddress(String baseAddress)
  {
    _baseAddress=baseAddress;
  }

  /**
   * Set a parameter value.
   * @param key Parameter name.
   * @param value Parameter value.
   */
  public void setParameter(String key, String value)
  {
    _parameters.put(key,value);
  }

  /**
   * Get an integer parameter.
   * @param parameterName Name of the parameter to get.
   * @return An <code>Integer</code> or <code>null</code> if not found.
   */
  public Integer getIntParameter(String parameterName)
  {
    Integer ret=null;
    String value=_parameters.get(parameterName);
    if (value!=null)
    {
      ret=NumericTools.parseInteger(value);
    }
    return ret;
  }

  /**
   * Get full address.
   * @return a string representation of this identifier.
   */
  public String getFullAddress()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_baseAddress);
    boolean isFirst=false;
    for(Map.Entry<String,String> entry : _parameters.entrySet())
    {
      sb.append(isFirst?':':'&');
      sb.append(entry.getKey());
      sb.append('=');
      sb.append(entry.getValue());
      isFirst=false;
    }
    return sb.toString();
  }

  /**
   * Build a page identifier from a string.
   * @param input Input string.
   * @return A page identifier.
   */
  public static PageIdentifier fromString(String input)
  {
    PageIdentifier ret=new PageIdentifier();
    String baseAddress=null;
    int index=input.indexOf(':');
    if (index==-1)
    {
      baseAddress=input;
    }
    else
    {
      baseAddress=input.substring(0,index);
      String params=input.substring(index+1);
      Map<String,String> parameters=parseParameters(params);
      for(Map.Entry<String,String> entry : parameters.entrySet())
      {
        ret.setParameter(entry.getKey(),entry.getValue());
      }
    }
    ret.setBaseAddress(baseAddress);
    return ret;
  }

  private static Map<String,String> parseParameters(String input)
  {
    Map<String,String> ret=new HashMap<String,String>();
    String[] parameters=input.split("&");
    for(String singleParameter : parameters)
    {
      int index=singleParameter.indexOf('=');
      if (index!=-1)
      {
        String parameterName=singleParameter.substring(0,index);
        String parameterValue=singleParameter.substring(index+1);
        ret.put(parameterName,parameterValue);
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Page: address=").append(_baseAddress);
    if (_parameters.size()>0)
    {
      sb.append(", parameters: ").append(_parameters);
    }
    return sb.toString();
  }
}
