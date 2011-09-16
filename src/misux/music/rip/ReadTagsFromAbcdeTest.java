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

import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class ReadTagsFromAbcdeTest
{

  ReadTagsFromAbcde tags;


  private void init ()
  {
    final CD cd = new CD();
    try {
      tags = new ReadTagsFromAbcde(cd.getCDID());
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
  }


  /**
   * Tests the method getPlaylist
   * 
   * @author DSIW
   * @throws Exception
   */
  @Test
  public void testGetPlaylist () throws Exception
  {
    init();
    System.out.println(tags.getPlaylist());
  }

  // @Test
  // public void testRmComments ()
  // {
  // init();
  // // System.out.println(tags.content);
  // }

  // @Test
  // public void testAlbum()
  // {
  // init();
  // System.out.println(tags.getAlbum());
  // }
  //
  // @Test
  // public void testInterpret()
  // {
  // init();
  // System.out.println(tags.getInterpret());
  // }
  // @Test
  // public void testYear()
  // {
  // init();
  // System.out.println(tags.getYear());
  // }
  // @Test
  // public void testGenre()
  // {
  // init();
  // System.out.println(tags.getGenre());
  // }
  // @Test
  // public void testTracks()
  // {
  // init();
  // System.out.println(tags.getTitles());
  // }
}
