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
package misux.gui.test;

import misux.gui.panel.CoverPanel;
import misux.music.pl.MainPlaylist;

import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class TrackTableTest
{

  /**
   * Tests the TrackTable
   * 
   * @author DSIW
   */
  @Test
  public void testTrackTable ()
  {
    try {
      MainPlaylist.get();
    }
    catch (final Exception e1) {
      e1.printStackTrace();
    }
    new CoverPanel();
    // cover.setActualTrack(playlist.getActualTrack());
    // cover.setPlaylist(playlist);
    // final TrackTable tt = new TrackTable(playlist);
    // final JScrollPane sp = new JScrollPane(tt);
    // final JFrame f = new JFrame();
    // f.setLayout(new BorderLayout());
    // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // f.add(sp, BorderLayout.CENTER);
    // // f.add(new CoverPanel(playlist.getActualTrack()), BorderLayout.WEST);
    // cover.setMinimumSize(new Dimension(200, 200));
    // f.add(cover, BorderLayout.WEST);
    // f.pack();
    // f.setVisible(true);
    try {
      Thread.sleep(999999999);
    }
    catch (final InterruptedException e) {}
  }
}
