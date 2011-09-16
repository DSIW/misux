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

import java.io.IOException;

import misux.io.file.FileHandler;
import misux.pref.Option;
import misux.pref.Pref;
import misux.pref.PrefType;
import misux.pref.Preferences;

import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class PrefTest
{

  /**
   * Tests the method addOption.
   */
  @Test
  public void testAddOption ()
  {
    Pref p = new Pref();
    Option o = null;
    try {
      p.addOption("yes", "Boolean", true);
      o = p.getOption("yes");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(o.getName().equals("yes")
        && o.getType() == PrefType.valueOf("Boolean")
        && o.getBooleanValue() == true);
  }


  /**
   * Tests the method getNumberOfOptions
   */
  @Test
  public void testGetNumberOfOptions ()
  {
    Pref p = new Pref();
    try {
      p.addOption("path", "String", "~/bin");
      p.addOption("size", "Integer", 200);
      p.addOption("yes", "Boolean", true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    assertTrue(p.getNumberOfOptions() == 3);
  }


  /**
   * Tests the method toXML
   */
  @Test
  public void testToXML ()
  {
    Pref p = new Pref();
    try {
      p.addOption("path", "String", "~/bin");
      p.addOption("size", "Integer", 200);
      p.addOption("yes", "Boolean", true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    try {
      FileHandler.writeString(p.toXML(), "/tmp/preferences.xml");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    assertTrue(true);
  }


  /**
   * Tests method parseXML
   */
  @Test
  public void testParseXML ()
  {
    String content = null;
    try {
      Preferences.read();
      System.out.println("###");
      content = Preferences.get().toXML();
      System.out.println(Preferences.get().toString());
    }
    catch (Exception e1) {
      e1.printStackTrace();
    }

    Pref p = new Pref();
    try {
      p.parseXML(content);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    assertTrue(p.toXML().equals(content));
  }
}
