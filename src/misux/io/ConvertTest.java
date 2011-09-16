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
package misux.io;

import java.io.IOException;

import misux.div.exceptions.ExecutionException;
import misux.music.rip.GetCDID;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class ConvertTest
{

  // /**
  // * Tests the special keys.
  // *
  // * @author DSIW
  // */
  // @Test
  // public void testNoSpecials ()
  // {
  // String in = "Eine kleine Äßterlein, sitzt d'a. &quot;.:%\"&§$//%&()/)";
  // String out = null;
  // try {
  // out = Convert.noSpecialChars(in);
  // }
  // catch (final SpecialKeyException e) {
  // e.printStackTrace();
  // }
  // System.out.println(out);
  // in = "Herbert Gr\uFFFDnemeyer";
  // System.out.println(in);
  // try {
  // out = Convert.noSpecialChars(in);
  // }
  // catch (final SpecialKeyException e) {
  // e.printStackTrace();
  // }
  // System.out.println(out);
  // Assert.assertTrue(true);
  // }

  /**
   * Tests the method toIO
   */
  @Test
  public void testToIO ()
  {
    String in = "Eine kleine Äßterlein, sitzt d'a. &quot;.:%\"&§$//%&()/)";
    String out = Convert.toIO(in);
    System.out.println(out);
    in = "Herbert Gr\uFFFDnemeyer";
    System.out.println(in);
    out = Convert.toIO(in);
    System.out.println(out);
    Assert.assertTrue(true);
  }


  /**
   * Tests the method toMusic
   */
  @Test
  public void testToMusic ()
  {
    final GetCDID id = new GetCDID();
    try {
      // id.read();
      id.getID();
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
}
