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

import java.util.concurrent.ExecutionException;

import misux.div.exceptions.MusicIsRippedException;
import misux.music.Encoding;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class RipperTest
{

  /**
   * Tests the method getPlaylist
   * 
   * @author DSIW
   */
  @Test
  public void testGetPlaylist ()
  {
    final Ripper ripper = new Ripper(Encoding.OGG);
    try {
      System.out.println(ripper.getPlaylist());
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Tests the method isAlreadyRipped
   * 
   * @author DSIW
   */
  @Test
  public void testIsAlreadyRipped ()
  {
    final Ripper ripper = new Ripper(Encoding.OGG);
    try {
      System.out.println(ripper.isAlreadyRipped());
    }
    catch (final ExecutionException e) {
      System.err.println("NO CD");
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  // @Test
  // public void testFoundTrackInList ()
  // {
  // List<Integer> tl = new LinkedList<Integer>();
  // tl.add(1);
  // tl.add(2);
  // for (int i = 1; i <= 15; i++) {
  // Track t = new TrackImpl();
  // t.setIndex(i);
  // System.out.println(Ripper.foundTrackInList(tl, t));
  // }
  // }

  /**
   * Tests the method notRipped
   * 
   * @author DSIW
   */
  // @Test
  // public void testNotRipped ()
  // {
  // final Ripper ripper = new Ripper(Encoding.OGG);
  // try {
  // System.out.println(ripper.getNotRippedTrackNumbers());
  // }
  // catch (final Exception e) {
  // e.printStackTrace();
  // }
  // }

  /**
   * Tests the method rip
   * 
   * @author DSIW
   */
  @Test
  public void testRip ()
  {
    final Ripper ripper = new Ripper(Encoding.OGG);
    try {
      ripper.rip();
    }
    catch (final MusicIsRippedException e) {
      System.err.println(e.getMessage());
    }
    catch (final Exception e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Tests the method ripped
   * 
   * @author DSIW
   */
  // @Test
  // public void testRipped ()
  // {
  // final Ripper ripper = new Ripper(Encoding.OGG);
  // try {
  // System.out.println(ripper.getRippedTrackNumbers());
  // }
  // catch (final Exception e) {
  // e.printStackTrace();
  // }
  // }

  // @Test
  // public void testMerge ()
  // {
  // Ripper ripper = new Ripper(Encoding.OGG);
  // try {
  // ripper.mergeTracks();
  // }
  // catch (Exception e) {
  // e.printStackTrace();
  // }
  // }
}
