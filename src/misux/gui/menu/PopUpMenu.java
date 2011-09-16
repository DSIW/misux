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
package misux.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import misux.gui.frame.Tags;
import misux.gui.table.TrackTable;
import misux.music.player.Player;

/**
 * This is a PopUpMenu. It gives possibillyties to remove a track from a
 * {@link TrackTable}, change its data, move it up or down.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class PopUpMenu extends JPopupMenu
{
  // /**
  // * Test
  // *
  // * @author DSIW
  // */
  // public static void main (final String[] args)
  // {
  // final JPanel p = new JPanel();
  // p.addMouseListener(new MouseAdapter()
  // {
  // @Override
  // public void mouseClicked (final MouseEvent e)
  // {
  // if (e.getButton() == MouseEvent.BUTTON3) {
  // final PopUpMenu m = new PopUpMenu(new TrackTable(new PlaylistImpl(
  // "test")), e);
  // m.show(e.getComponent(), e.getX(), e.getY());
  // }
  // }
  // });
  // final JFrame f = new JFrame();
  // f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  // f.add(p);
  // f.setSize(200, 200);
  // f.setVisible(true);
  // }

  /**
   * Creates a new popup menu.
   * 
   * @param player
   *          player
   * @param tt
   *          tracktable
   * @param e
   *          mouseevent
   */
  public PopUpMenu(final Player player, final TrackTable tt, final MouseEvent e)
  {
    final JMenuItem item1 = new JMenuItem("play");
    item1.addActionListener(new ActionListener()// play
        {
          @Override
          public void actionPerformed (final ActionEvent e1)
          {

            final int rowAtPoint = tt.rowAtPoint(e.getPoint());
            final int convertedRowAtPoint = tt.getRowSorter()
                .convertRowIndexToModel(rowAtPoint);
            try {
              player.getPlaylist().setActualTrack(convertedRowAtPoint);
            }
            catch (final Exception e3) {
              e3.printStackTrace();
            }
            try {
              player.playPlaylist();
            }
            catch (final FileNotFoundException e2) {
              e2.printStackTrace();
            }
            catch (final JavaLayerException e2) {
              e2.printStackTrace();
            }
            catch (final BasicPlayerException e2) {
              e2.printStackTrace();
            }
            catch (final UnsupportedAudioFileException e2) {
              e2.printStackTrace();
            }
            catch (final IOException e2) {
              e2.printStackTrace();
            }
            catch (final Exception e2) {
              e2.printStackTrace();
            }
          }
        });
    final JMenuItem item2 = new JMenuItem("delete from playlist");// remove
    item2.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e1)
      {
        final int[] rows = tt.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
          try {
            player.getPlaylist().remove(rows[i] - i);
          }
          catch (final Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
    final JMenuItem item3 = new JMenuItem("edit tags");// change data
    item3.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent arg0)
      {
        final int rowAtPoint = tt.rowAtPoint(e.getPoint());
        final int convertedRowAtPoint = tt.getRowSorter()
            .convertRowIndexToModel(rowAtPoint);
        try {
          new Tags(null, player.getPlaylist().getTrack(convertedRowAtPoint));
        }
        catch (final Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    final JMenuItem item4 = new JMenuItem("move up");// move up
    item4.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent arg0)
      {
        final int[] rows = tt.getSelectedRows();
        for (final int row : rows) {
          try {
            player.getPlaylist().moveTrackUp(row);
          }
          catch (final IndexOutOfBoundsException e2) {
            e2.printStackTrace();
          }
          catch (final Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
    final JMenuItem item5 = new JMenuItem("move down");// move down
    item5.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent arg0)
      {
        final int[] rows = tt.getSelectedRows();
        for (final int row : rows) {
          try {
            player.getPlaylist().moveTrackDown(row);
          }
          catch (final IndexOutOfBoundsException e2) {
            e2.printStackTrace();
          }
          catch (final Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
    add(item1);
    add(item2);
    add(item3);
    add(item4);
    add(item5);
  }
}
