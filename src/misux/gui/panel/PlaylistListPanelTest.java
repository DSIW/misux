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
package misux.gui.panel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import misux.music.pl.Playlist;
import misux.music.pl.PlaylistImpl;

import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class PlaylistListPanelTest
{

  /**
   * Tests the method getPlaylistList
   * 
   * @author DSIW
   */
  @Test
  public void testGetPlaylistList ()
  {
    final Playlist p1 = new PlaylistImpl("erste Liste");
    final Playlist p2 = new PlaylistImpl("zweite Liste");
    final List<Playlist> list = new ArrayList<Playlist>();
    list.add(p1);
    list.add(p2);
    final String[] playlists = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      playlists[i] = list.get(i).getName();
    }
    final PlaylistList pll = new PlaylistList(list);
    final PlaylistListPanel pllp = new PlaylistListPanel(pll);
    final JScrollPane sp = new JScrollPane();
    sp.add(pllp);
    final JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.add(pll);
    f.pack();
    f.setVisible(true);
  }
}
