/*
 * misux - musicplayer (written in Java)
 * Copyright (C) 2011  DSIW <dsiw@privatdemail.net>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package misux.pref;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class saves options in a list.
 * 
 * @author DSIW
 */
public class Pref
{
  List<Option>            options;
  ArrayList<PrefListener> listener;


  /**
   * Creates a new empty list
   */
  public Pref()
  {
    options = new LinkedList<Option>();
    listener = new ArrayList<PrefListener>();
  }


  /**
   * Creates a new list with a first option.
   * 
   * @param o
   *          the first option
   */
  public Pref(final OptionImpl o)
  {
    this();
    try {
      addOption(o);
    }
    catch (final Exception e) {
      /*
       * Exception can't be thrown, because there are no other option.
       */
    }
  }


  /**
   * Adds an option to the list.
   * 
   * @param o
   *          option to add
   * @throws Exception
   */
  public void addOption (final Option o) throws Exception
  {
    if (contains(o)) {
      throw new Exception("Option " + o.getName()
          + " is already in Preferences.");
    } else {
      options.add(o);
    }
    changed();
  }


  /**
   * Adds an option to the list.
   * 
   * @param name
   *          unique identifier of the option
   * @param type
   *          datatype of the value as PrefType
   * @param value
   *          value of the option
   * @throws Exception
   */
  public void addOption (final String name, final PrefType type,
      final Object value) throws Exception
  {
    final Option tmp = new OptionImpl(name, type, value);
    addOption(tmp);
  }


  /**
   * Adds an option to the list.
   * 
   * @param name
   *          unique identifier of the option
   * @param type
   *          datatype of the value as String
   * @param value
   *          value of the option
   * @throws Exception
   */
  public void addOption (final String name, final String type,
      final Object value) throws Exception
  {
    final Option tmp = new OptionImpl(name, type, value);
    addOption(tmp);
  }


  /**
   * Adds a PrefListener
   * 
   * @param lst
   * @author DSIW
   */
  public void addPrefListener (final PrefListener lst)
  {
    listener.add(lst);
  }


  private void changed ()
  {
    for (final PrefListener lst : listener) {
      lst.changed();
    }
  }


  private boolean contains (final Option o)
  {
    for (final Option op : options) {
      if (op.getName().equalsIgnoreCase(o.getName())) {
        return true;
      }
    }
    return false;
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
    if (!(obj instanceof Pref)) {
      return false;
    }
    final Pref other = (Pref) obj;
    if (options == null) {
      if (other.options != null) {
        return false;
      }
    } else if (!options.equals(other.options)) {
      return false;
    }
    return true;
  }


  /**
   * @return count of the set options in the list or the length of the list
   */
  public int getNumberOfOptions ()
  {
    return options.size();
  }


  /**
   * Gets an option from the list
   * 
   * @param key
   *          identifier of the option
   * @return option
   * @throws Exception
   *           if the identifier will not found in the list
   */
  public Option getOption (final String key) throws Exception
  {
    for (final Option o : options) {
      if (o.getName().equals(key)) {
        return o;
      }
    }
    throw new Exception("Option is not available.");
  }


  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (options == null ? 0 : options.hashCode());
    return result;
  }


  /**
   * Parses all options from the list in XML syntax
   * 
   * @param xml
   *          the options to parse in XML syntax
   * @throws Exception
   */
  public void parseXML (final String xml) throws Exception
  {
    final String[] xmlOptions = xml.split("\n");
    for (final String xmlOption : xmlOptions) {
      final Option o = new OptionImpl();
      addOption(o.parseOption(xmlOption));
    }
  }


  /**
   * Removes an option from the list.
   * 
   * @param o
   *          option, that will be remove
   */
  public void removeOption (final Option o)
  {
    options.remove(o);
    changed();
  }


  /**
   * Removes a PrefListener
   * 
   * @param lst
   * @author DSIW
   */
  public void removePrefListener (final PrefListener lst)
  {
    listener.remove(lst);
  }


  /**
   * Sets a new value of an option from the list
   * 
   * @param name
   *          identifier of the option
   * @param value
   *          the new value
   * @throws Exception
   */
  public void setOption (final String name, final Object value)
      throws Exception
  {
    getOption(name).setValue(value);
    changed();
  }


  @Override
  public String toString ()
  {
    String out = "";
    for (int i = 0; i < options.size(); i++) {
      final Option o = options.get(i);
      out += o.toString();
      if (i != options.size() - 1) {
        out += "\n";
      }
    }
    return out;
  }


  /**
   * @return all options from the list in XML syntax and seperated with a CR
   *         (\n)
   */
  public String toXML ()
  {
    String xml = "";
    for (int i = 0; i < options.size(); i++) {
      final Option o = options.get(i);
      xml += o.toXML();
      if (i != options.size() - 1) {
        xml += "\n";
      }
    }
    return xml;
  }
}
