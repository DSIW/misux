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
package misux.gui.frame;

import javax.swing.JFrame;

import misux.music.Encoding;
import misux.music.Genre;
import misux.music.license.CreativeCommons;
import misux.music.track.Track;
import misux.music.track.TrackImpl;

import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class TagsTest
{

  /**
   * Tests the tags dialoge.
   * 
   * @author DSIW
   */
  @Test
  public void testTags ()
  {
    final Track t = new TrackImpl();
    t.setEnc(Encoding.MP3);
    t.setLicense(new CreativeCommons());
    t.setGenre(Genre.Rock);
    System.out.println(t);
    final Tags tags = new Tags(new JFrame(), t);
    tags.setVisible(true);
    try {
      Thread.sleep(999999999);
    }
    catch (final InterruptedException e) {}
  }
}
