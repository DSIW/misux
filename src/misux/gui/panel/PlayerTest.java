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

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import misux.div.Images;
import misux.gui.frame.MyJFrame;
import misux.music.pl.MainPlaylist;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class PlayerTest
{
  /**
   * Tests the loading from a image into a button.
   * 
   * @author DSIW
   */
  @Test
  public void testImages ()
  {
    Images img = null;
    try {
      img = new Images("thumb-media_shuffle.png");
    }
    catch (final IOException e1) {
      e1.printStackTrace();
    }

    final JFrame f = new MyJFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(new JLabel(Images.toImageIcon(img.getImage())));
    f.pack();
    f.setVisible(true);
    try {
      Thread.sleep(1000000);
    }
    catch (final InterruptedException e) {}
  }


  /**
   * Tests the player
   * 
   * @author DSIW
   */
  @Test
  public void testPlayer ()
  {
    try {
      MainPlaylist.get();
    }
    catch (final Exception e1) {
      e1.printStackTrace();
    }

    // TrackTable tt = new TrackTable(playlist);
    // JScrollPane sp = new JScrollPane(tt);
    // PlayerPanel playPanel = new PlayerPanel(tt.getWorker());
    // JFrame f = new MyJFrame("Player");
    // // System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
    // f.setLayout(new BorderLayout());
    // f.add(playPanel, BorderLayout.NORTH);
    // f.add(sp, BorderLayout.CENTER);
    // // f.add(tt.getWorker().getCoverPanel(), BorderLayout.WEST);
    // f.pack();
    // f.setSize(1000, 700);
    // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // f.setVisible(true);
    try {
      Thread.sleep(1000000);
    }
    catch (final InterruptedException e) {}

    Assert.assertFalse(true);
  }
}
