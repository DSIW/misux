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
package misux.gui.menu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import misux.gui.table.TrackTable;
import misux.music.player.Player;

/**
 * This is a listener for a {@link PopUpMenu}. If mouseClicked (right mouse
 * button), a PopUpMenu is shown.
 * 
 * @author DSIW
 */
public class PopUpListener extends MouseAdapter
{
  private final TrackTable tt;
  private final Player     player;


  /**
   * Creates a new popup listener.
   * 
   * @param player
   *          player
   * @param tt
   *          tracktable
   */
  public PopUpListener(final Player player, final TrackTable tt)
  {
    this.tt = tt;
    this.player = player;
  }


  /**
   * If right mouse button is clicked, a {@link PopUpMenu} is shown.
   * 
   * @author DSIW
   */
  @Override
  public void mouseClicked (final MouseEvent e)
  {
    if (e.getButton() == MouseEvent.BUTTON3) {// right mouse button
      final PopUpMenu menu = new PopUpMenu(player, tt, e);
      menu.show(e.getComponent(), e.getX(), e.getY());
    }
  }
}
