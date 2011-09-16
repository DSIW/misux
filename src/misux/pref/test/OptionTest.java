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
misux - musicplayer (written in Java)
Copyright (C) 2011  DSIW <dsiw@privatdemail.net>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
package misux.pref.test;

import static org.junit.Assert.assertTrue;
import misux.div.exceptions.TypeIsNotEqualsException;
import misux.pref.Option;
import misux.pref.OptionImpl;
import misux.pref.PrefType;

import org.junit.Test;

/**
 * Test class
 * @author DSIW
 *
 */
public class OptionTest
{

  /**
   * Tests method setOption
   */
  @Test
  public void testSetOption ()
  {
    Option o = null;
    try {
      o = new OptionImpl();
      o.setOption("source", PrefType.String, "amazon.de");
    }
    catch (TypeIsNotEqualsException e) {
      e.printStackTrace();
    }
    System.out.println(o);
    assertTrue(o.getName().equals("source") && o.getType() == PrefType.String
        && o.getStringValue().equals("amazon.de"));
  }


  /**
   * Tests the method testToXML with a String value.
   */
  @Test
  public void testToXML ()
  {
    Option o = null;
    try {
      o = new OptionImpl();
      o.setOption("source", "String", "amazon.de");
    }
    catch (TypeIsNotEqualsException e) {
      e.printStackTrace();
    }
    System.out.println(o.toXML());
    assertTrue(o.toXML().equals(
        "<pref name=\"source\" type=\"String\">amazon.de</pref>"));
  }


  /**
   * Tests the method testToXML with a boolean value.
   */
  @Test
  public void testToXML1 ()
  {
    Option o = null;
    try {
      o = new OptionImpl();
      o.setOption("source", "Boolean", true);
    }
    catch (TypeIsNotEqualsException e) {
      e.printStackTrace();
    }
    System.out.println(o.toXML());
    assertTrue(o.toXML().equals(
        "<pref name=\"source\" type=\"Boolean\">true</pref>"));
  }


  /**
   * Tests the method testToXML with a integer value.
   */
  @Test
  public void testToXML2 ()
  {
    Option o = null;
    try {
      o = new OptionImpl();
      o.setOption("source", "Integer", 3);
    }
    catch (TypeIsNotEqualsException e) {
      e.printStackTrace();
    }
    System.out.println(o.toXML());
    assertTrue(o.toXML().equals(
        "<pref name=\"source\" type=\"Integer\">3</pref>"));
  }


  /**
   * Tests the method parseOption with a String value.
   * @throws Exception 
   */
  @Test
  public void testParseOption () throws Exception
  {
    Option ref = new OptionImpl();
    ref.setOption("source", "String", "amazon.de");
    Option res = new OptionImpl();
    res.parseOption("<pref name=\"source\" type=\"String\">amazon.de</pref>");
    System.out.println(res.getName() + "; " + res.getStringValue());
    assertTrue(ref.getName().equals(res.getName())
        && ref.getStringValue().equals(res.getStringValue()));
  }


  /**
   * Tests the method parseOption with a integer value.
   * @throws Exception 
   */
  @Test
  public void testParseOption1 () throws Exception
  {
    Option ref = new OptionImpl();
    ref.setOption("source", "Integer", 3);
    Option res = new OptionImpl();
    res.parseOption("<pref name=\"source\" type=\"Integer\">3</pref>");
    System.out.println(res.getName() + "; " + res.getIntegerValue());
    assertTrue(ref.getName().equals(res.getName())
        && ref.getIntegerValue() == res.getIntegerValue());
  }


  /**
   * Tests the method parseOption with a boolean value.
   * @throws Exception 
   */
  @Test
  public void testParseOption2 () throws Exception
  {
    Option ref = new OptionImpl();
    ref.setOption("source", "Boolean", true);
    Option res = new OptionImpl();
    res.parseOption("<pref name=\"source\" type=\"Boolean\">true</pref>");
    System.out.println(res.getName() + "; " + res.getBooleanValue());
    assertTrue(ref.getName().equals(res.getName())
        && ref.getBooleanValue() == res.getBooleanValue());
  }
}
