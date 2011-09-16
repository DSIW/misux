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
package misux.gui.table;

import java.text.DecimalFormat;

import javax.swing.table.AbstractTableModel;

import misux.music.pl.Playlist;
import misux.music.pl.PlaylistImpl;
import misux.music.pl.PlaylistListener;
import misux.music.player.Player;

/**
 * This is a tablemodel for a playlist.
 * 
 * @author DSIW
 * @author DSIW
 */
@SuppressWarnings("serial")
public class TrackTableModel extends AbstractTableModel
{
  private final Player player;


  /**
   * Creates a new track tabel model.
   * 
   * @param pl
   *          Playlist to display in a table.
   * @author DSIW
   */
  public TrackTableModel(final Player pl)
  {
    player = pl;
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
          fireTableDataChanged();
          fireTableStructureChanged();
        }
      });
    }
    catch (final Exception e) {}
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getColumnCount ()
  {
    return 11;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   * @author DSIW
   */
  @Override
  public String getColumnName (final int index)
  {
    switch (index) {
      case 0:
        return "T";
      case 1:
        return "P";
      case 2:
        return "Title";
      case 3:
        return "Interpret";
      case 4:
        return "Album";
      case 5:
        return "Rank";
      case 6:
        return "Cnt";
      case 7:
        return "Dur";
      case 8:
        return "Genre";
      case 9:
        return "Year";
      case 10:
        return "License";
      default:
        return null;
    }
  }


  /**
   * @param index
   * @return the column width of the column with the specified index
   * @author DSIW
   */
  public int getColumnWidth (final int index)
  {
    Playlist pl = null;
    try {
      pl = player.getPlaylist();
    }
    catch (final Exception e1) {
      pl = new PlaylistImpl("");
    }

    switch (index) {
      case 0:
        return (pl.getLength() + "").length() * 10;
      case 1:
        return 18;
      case 5:
        return 50;
      case 6:
        return 35;
      case 7:
        return 60;
      case 9:
        return 45;
      case 2:
      case 3:
      case 4:
      case 8:
      case 10:
      default:
        return 40;
    }
  }


  /**
   * @param index
   * @return the column horizontal right alignment of the column with the
   *         specified index
   * @author DSIW
   */
  public boolean getRightAlignment (final int index)
  {
    switch (index) {
      case 6:
      case 7:
      case 9:
        return true;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 8:
      case 10:
      default:
        return false;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getRowCount ()
  {
    try {
      return player.getPlaylist().getLength();
    }
    catch (final Exception e) {
      return 0;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   * @author DSIW
   */
  @Override
  public Object getValueAt (final int row, final int col)
  {
    Playlist pl = null;
    try {
      pl = player.getPlaylist();
    }
    catch (final Exception e1) {
      pl = new PlaylistImpl("");
    }
    switch (col) {
      case 0:
        String format = "";
        for (int i = 0; i < (pl.getLength() + "").length(); i++) {
          format += "0";
        }
        final DecimalFormat df = new DecimalFormat(format);
        return df.format(pl.getTrack(row).getIndex());
      case 1:
        return pl.getTrack(row).equals(pl.getActualTrack())
            && player.isPlaying() ? "\u25B6" : "";
      case 2:
        return pl.getTrack(row).getTitle();
      case 3:
        return pl.getTrack(row).getInterpret();
      case 4:
        return pl.getTrack(row).getAlbum();
      case 5:
        String ranking = "";
        for (int r = 1; r <= pl.getTrack(row).getRating(); r++) {
          ranking += "*";
        }
        return ranking;
      case 6:
        return pl.getTrack(row).getCount();
      case 7:
        try {
          return pl.getTrack(row).getDurationToString();
        }
        catch (final Exception e) {
          return "00:00";
        }
      case 8:
        return pl.getTrack(row).getGenre();
      case 9:
        final int year = pl.getTrack(row).getYear();
        if (year == 0) {
          return "";
        }
        return year + "";
      case 10:
        return pl.getTrack(row).getLicense();
      default:
        return "";
    }
  }


  /**
   * @param index
   * @return the flag of the resizeability of the column with the specified
   *         index
   * @author DSIW
   */
  public boolean isResizable (final int index)
  {
    switch (index) {
      case 0:
      case 1:
      case 5:
      case 6:
      case 7:
      case 9:
        return false;
      case 2:
      case 3:
      case 4:
      case 8:
      case 10:
      default:
        return true;
    }
  }
}
