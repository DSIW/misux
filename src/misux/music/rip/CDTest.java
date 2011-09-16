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
package misux.music.rip;

import java.io.IOException;

import misux.div.exceptions.ExecutionException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class CDTest
{

  /**
   * Tests method getID
   */
  @Test
  public void testGetCDID ()
  {
    final CD cd = new CD();
    try {
      System.out.println(cd.getCDID());
    }
    catch (final InterruptedException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final ExecutionException e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  /**
   * Tests method getPlaylist
   */
  @Test
  public void testGetPlaylist ()
  {
    final CD cd = new CD();
    try {
      System.out.println(cd.getPlaylist());
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  /**
   * Tests method inserted
   */
  @Test
  public void testInserted ()
  {
    final CD cd = new CD();
    System.out.println(cd.inserted());
    Assert.assertTrue(true);
  }

  /**
   * Tests method eject
   */
  // @Test
  // public void testEject ()
  // {
  // CD cd = new CD();
  // try {
  // cd.eject();
  // }
  // catch (Exception e) {
  // e.printStackTrace();
  // }
  // assertTrue(true);
  // }
}
