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
package misux.gui.panel;

import java.util.List;

import javax.swing.JList;

import misux.music.pl.Playlist;

/**
 * This is a {@link JList} which is made to display {@link Playlist}s.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class PlaylistList extends JList
{
  /**
   * Gets the names from a few Playlists. This is used for the superconstructor
   * call.
   * 
   * @param playlists
   *          Playlists to get names from
   * @return string-array with names of playlists
   * @author DSIW
   */
  private static String[] getNames (final List<Playlist> playlists)
  {
    final String[] names = new String[playlists.size()];
    for (int i = 0; i < playlists.size(); i++) {
      names[i] = playlists.get(i).getName();
    }
    return names;
  }

  private List<Playlist> playlists;


  /**
   * Creates a PlaylistList.
   * 
   * @param playlists
   *          entries for the List
   * @author DSIW
   */
  public PlaylistList(final List<Playlist> playlists)
  {
    super(PlaylistList.getNames(playlists));
  }


  /**
   * Gets a {@link Playlist}.
   * 
   * @param pos
   *          Position in the PlaylistList of the Playlist to get
   * @return a specified Plalist from the PlaylistList
   * @author DSIW
   */
  public Playlist getPlaylist (final int pos)
  {
    return playlists.get(pos);
  }


  /**
   * Sets the list of playlists.
   * 
   * @param playlists
   *          list with playlists for displaying
   * @author DSIW
   */
  @SuppressWarnings("unused")
  private void setPlaylists (final List<Playlist> playlists)
  {
    this.playlists = playlists;
  }
}
