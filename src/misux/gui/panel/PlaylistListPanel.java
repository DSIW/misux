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
package misux.gui.panel;

import javax.swing.JPanel;

/**
 * This is a {@link JPanel} for a {@link PlaylistList}.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class PlaylistListPanel extends JPanel
{
  private PlaylistList pll;


  /**
   * Creates a {@link PlaylistListPanel}.
   * 
   * @param pll
   *          a {@link PlaylistList} to display in this {@link JPanel}.
   * @author DSIW
   */
  public PlaylistListPanel(final PlaylistList pll)
  {
    setPlaylistList(pll);
  }


  /**
   * Gets the PlaylistList.
   * 
   * @return used PlaylistList
   * @author DSIW
   */
  public PlaylistList getPlaylistList ()
  {
    return pll;
  }


  /**
   * Sets the PlaylistList.
   * 
   * @param pll
   *          PlaylistList to set
   * @author DSIW
   */
  public void setPlaylistList (final PlaylistList pll)
  {
    this.pll = pll;
  }
}
