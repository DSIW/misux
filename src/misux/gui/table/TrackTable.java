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
package misux.gui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import misux.gui.menu.PopUpListener;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistListener;
import misux.music.player.Player;

/**
 * This is a table to display a {@link Playlist}.
 * 
 * @author DSIW
 * @author DSIW
 */
@SuppressWarnings("serial")
public class TrackTable extends JTable
{
  private final TrackTableModel model;
  private final Player          player;


  /**
   * Creates a new track table.
   * 
   * @param player
   *          player of program
   * @param model
   *          track table model
   * @author DSIW
   * @author DSIW
   */
  public TrackTable(final Player player, final TrackTableModel model)
  {
    super();
    this.model = model;
    this.player = player;
    setModel(model);
    initDesign();

    final TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
        getModel());
    setRowSorter(rowSorter);

    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked (final MouseEvent e)
      {
        final int rowAtPoint = rowAtPoint(e.getPoint());
        final int convertedRowAtPoint = rowSorter
            .convertRowIndexToModel(rowAtPoint);
        if (player.isStopped() && e.getClickCount() == 1) {
          try {
            player.getPlaylist().setActualTrack(convertedRowAtPoint);
          }
          catch (final Exception e1) {
            e1.printStackTrace();
          }
        } else if (e.getClickCount() == 2) {
          try {
            // player.stop();
            player.getPlaylist().setActualTrack(convertedRowAtPoint);
            player.playPlaylist(true);
          }
          catch (final FileNotFoundException e1) {
            e1.printStackTrace();
          }
          catch (final JavaLayerException e1) {
            e1.printStackTrace();
          }
          catch (final BasicPlayerException e1) {
            e1.printStackTrace();
          }
          catch (final UnsupportedAudioFileException e1) {
            e1.printStackTrace();
          }
          catch (final IOException e1) {
            e1.printStackTrace();
          }
          catch (final Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });

    addMouseListener(new PopUpListener(player, this));// right click open
                                                      // popupmenu

    addPlaylistListener();
  }


  private void addPlaylistListener ()
  {
    try {
      player.getPlaylist().addPlaylistListener(new PlaylistListener()
      {
        @Override
        public void changed ()
        {
          initDesign();
        }
      });
    }
    catch (final Exception e) {}
  }


  /**
   * Gets the TrackTableModel used by this TrackTable.
   * 
   * @return TrackTableModel used by this TrackTable
   * @author DSIW
   */
  public TableModel getTrackTableModel ()
  {
    return getModel();
  }


  // /**
  // * Returns the playlist visualized by this table.
  // *
  // * @return Playlist of this table
  // * @author DSIW
  // */
  // public Playlist getPlaylist ()
  // {
  // try {
  // return model.getPlaylist();
  // }
  // catch (final Exception e) {
  // e.printStackTrace();
  // }
  // return null;
  // }

  private void initDesign ()
  {
    setShowGrid(false);
    setShowVerticalLines(true);

    final DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

    // column size
    setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

    for (int c = 0; c < getColumnModel().getColumnCount(); c++) {
      final TableColumn col = getColumnModel().getColumn(c);
      col.setPreferredWidth(model.getColumnWidth(c));
      col.setResizable(model.isResizable(c));

      // width
      if (c == getColumnModel().getColumnCount() - 1) {
        col.setMaxWidth(150);
        col.setMinWidth(85);
      }
      // resizeable
      if (!col.getResizable()) {
        col.setMinWidth(model.getColumnWidth(c));
        col.setMaxWidth(model.getColumnWidth(c));
        col.setWidth(model.getColumnWidth(c));
      }

      // horizontal alignment
      if (model.getRightAlignment(c)) {
        getColumnModel().getColumn(c).setCellRenderer(rightRenderer);
      }
    }

  }


  /**
   * Draws each second line in the same color.
   * 
   * @author DSIW
   */
  @Override
  public Component prepareRenderer (final TableCellRenderer renderer,
      final int row, final int column)
  {
    final Component c = super.prepareRenderer(renderer, row, column);
    if (!isRowSelected(row)) {
      final int COLOR = 230;
      c.setBackground(row % 2 == 0 ? null : new Color(COLOR, COLOR, COLOR));
    }
    return c;
  }

  // /**
  // * Sets a playlist for this table
  // *
  // * @param playlist
  // * Playlist for this table
  // * @author DSIW
  // */
  // private void setPlaylist (final Playlist playlist)// MUST be private!!!
  // {
  // model.setPlaylist(playlist);
  // }

  // /**
  // * Sets the PlayerWorker for this Tracktable.
  // *
  // * @param playerWorker
  // * PlayerWorker for this Tracktable
  // * @author DSIW
  // */
  // public void setWorker (final PlayerWorker playerWorker)
  // {
  // worker = playerWorker;
  // }
}
