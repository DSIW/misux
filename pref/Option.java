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

import misux.div.exceptions.TypeIsNotEqualsException;

/**
 * This class implements <code>Option</code> to save and load these. It will
 * save the name of an option and the datatype of the value. The name is a
 * unique identifier.
 * 
 * @author DSIW
 * 
 */
public interface Option
{

  @Override
  public boolean equals (Object obj);


  /**
   * @return the value parsed to boolean
   */
  public boolean getBooleanValue ();


  /**
   * @return the value parsed to double
   */
  public double getDoubleValue ();


  /**
   * @return the value parsed to integer
   */
  public int getIntegerValue ();


  /**
   * @return the unique identifier of the option
   */
  public String getName ();


  /**
   * @return the value parsed to String
   */
  public String getStringValue ();


  /**
   * @return the type
   */
  public PrefType getType ();


  @Override
  public int hashCode ();


  /**
   * This function will parse an object in XML syntax
   * 
   * @param optionInXML
   *          option in the xml syntax
   * @return parsed option as option object
   * @throws Exception
   */
  public Option parseOption (String optionInXML) throws Exception;


  /**
   * @param name
   *          the unique identifier to set
   */
  public void setName (String name);


  /**
   * Sets an option
   * 
   * @param name
   *          optionsname for identification
   * @param type
   *          of the value
   * @param value
   *          value of pption
   * @throws TypeIsNotEqualsException
   *           will thrown, if the datetype of the value not equals with the set
   *           datatype.
   */
  public void setOption (String name, PrefType type, Object value)
      throws TypeIsNotEqualsException;


  /**
   * Sets an option
   * 
   * @param name
   *          unique identifier
   * @param type
   *          type of the value
   * @param value
   *          value of the option
   * @throws TypeIsNotEqualsException
   */
  public void setOption (String name, String type, Object value)
      throws TypeIsNotEqualsException;


  /**
   * Sets a type
   * 
   * @param type
   *          the type as enum to set
   * @throws TypeIsNotEqualsException
   */
  public void setType (PrefType type) throws TypeIsNotEqualsException;


  /**
   * Sets a type
   * 
   * @param type
   *          the type to set, that will parsed to enum
   * @throws TypeIsNotEqualsException
   */
  public void setType (String type) throws TypeIsNotEqualsException;


  /**
   * Sets a value
   * 
   * @param value
   *          the value to set
   */
  public void setValue (Object value);


  /**
   * Convert the option to String
   * 
   * @return XML string: <code>name : value</code>
   */
  @Override
  public String toString ();


  /**
   * Convert the option to XML
   * 
   * @return xml string: &lt;pref name="name" type="type"&gt;value&lt;/pref&gt;
   */
  public String toXML ();

}
