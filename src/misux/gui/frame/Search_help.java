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
package misux.gui.frame;

import javax.swing.JFrame;

/**
 * Help dialoge for the search feature.
 * 
 * @author DSIW
 */
public class Search_help extends HelpDialoge
{
  private static String message = "You can use regular expressions to search in the playlist.\n"
                                    + "\n"
                                    + "For example:\n<code>*creative*</code> find all Tracks, which have contained the String \"creative\".";


  /**
   * Creates a new help dialoge.
   * 
   * @param parent
   *          parent of the dialoge
   */
  public Search_help(final JFrame parent)
  {
    super(parent, Search_help.message);
  }
}
