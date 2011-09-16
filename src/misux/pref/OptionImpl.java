/*
 * misux - musicplayer (written in Java)
Copyright (C) 2011  DSIW <dsiw@privatdemail.net>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package misux.pref;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import misux.div.exceptions.TypeIsNotEqualsException;

import org.w3c.dom.Node;

/**
 * This is an implementation of interface <code>Option</code>.
 * 
 * @author DSIW
 * 
 */
public class OptionImpl implements Option
{
  private String   name;
  private PrefType type;
  private Object   value;


  /**
   * Creates a new object of Option
   * 
   * @throws TypeIsNotEqualsException
   */
  public OptionImpl() throws TypeIsNotEqualsException
  {
    setName(null);
    setValue(null);
  }


  /**
   * Creates a new object with parameters
   * 
   * @param name
   *          name of the Option, which will be added
   * @param type
   *          type of the value as enum
   * @param value
   *          value of the option
   * @throws Exception
   */
  public OptionImpl(final String name, final PrefType type, final Object value)
      throws Exception
  {
    setOption(name, type, value);
  }


  /**
   * Creates a new object with parameters
   * 
   * @param name
   *          name of the Option, which will be added
   * @param type
   *          type of the value as string, that will parse to enum
   * @param value
   *          value of the option
   * @throws Exception
   */
  public OptionImpl(final String name, final String type, final Object value)
      throws Exception
  {
    setOption(name, type, value);
  }


  @Override
  public boolean equals (final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof OptionImpl)) {
      return false;
    }
    final OptionImpl other = (OptionImpl) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (type != other.type) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }


  @Override
  public boolean getBooleanValue ()
  {
    return ((Boolean) value).booleanValue();
  }


  @Override
  public double getDoubleValue ()
  {
    return ((Double) value).doubleValue();
  }


  @Override
  public int getIntegerValue ()
  {
    return ((Integer) value).intValue();
  }


  @Override
  public String getName ()
  {
    return name;
  }


  @Override
  public String getStringValue ()
  {
    return ((String) value).toString();
  }


  @Override
  public PrefType getType ()
  {
    return type;
  }


  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (name == null ? 0 : name.hashCode());
    result = prime * result + (type == null ? 0 : type.hashCode());
    result = prime * result + (value == null ? 0 : value.hashCode());
    return result;
  }


  @Override
  public Option parseOption (final String optionInXML) throws Exception
  {
    String name = null;
    String value = null;
    String type = null;

    Node dom = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .parse(new ByteArrayInputStream(optionInXML.getBytes()));
    dom = dom.getFirstChild();
    if (// dom.getNodeType() == Node.TEXT_NODE
    dom.getNodeName().equalsIgnoreCase("pref")) {
      // ATTRS
      if (dom.hasAttributes()) {
        for (int i = 0; i < dom.getAttributes().getLength(); i++) {
          final String attr = dom.getAttributes().item(i).toString();
          if (i == 0) {
            name = attr.replaceAll("^name=", "").replaceAll("\"", "");
          }
          if (i == 1) {
            type = attr.replaceAll("^type=", "").replaceAll("\"", "");
          }
        }
      }
      // VALUE
      value = dom.getTextContent();
    } else {
      throw new Exception("False Node. Expected TextNode.");
    }

    if (type.equalsIgnoreCase("Boolean")) {
      return new OptionImpl(name, "Boolean", Boolean.parseBoolean(value));
    } else if (type.equalsIgnoreCase("Integer")) {
      return new OptionImpl(name, "Integer", Integer.parseInt(value));
    } else if (type.equalsIgnoreCase("Double")) {
      return new OptionImpl(name, "Double", Double.parseDouble(value));
    } else if (type.equalsIgnoreCase("String")) {
      return new OptionImpl(name, "String", new String(value));
    }
    return null;
  }


  @Override
  public void setName (final String name)
  {
    this.name = name;
  }


  @Override
  public void setOption (final String name, final PrefType type,
      final Object value) throws TypeIsNotEqualsException
  {
    setName(name);
    setValue(value);
    setType(type);
  }


  @Override
  public void setOption (final String name, final String type,
      final Object value) throws TypeIsNotEqualsException
  {
    setName(name);
    setValue(value);
    setType(type);
  }


  @Override
  public void setType (final PrefType type) throws TypeIsNotEqualsException
  {
    if (PrefType.valueOf(value.getClass().getSimpleName()) == type) {
      this.type = type;
    } else {
      throw new TypeIsNotEqualsException(name);
    }
  }


  @Override
  public void setType (final String type) throws TypeIsNotEqualsException
  {
    setType(PrefType.valueOf(type));
  }


  @Override
  public void setValue (final Object value)
  {
    this.value = value;
  }


  @Override
  public String toString ()
  {
    return name + ":" + value;
  }


  @Override
  public String toXML ()
  {
    return "<pref name=\"" + name + "\" type=\"" + type + "\">" + value
        + "</pref>";
  }
}
