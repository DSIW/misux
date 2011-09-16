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

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import misux.div.Images;

/**
 * HeldDialoge is a dialoge with an ok button and information.
 * 
 * @author DSIW
 */
public class HelpDialoge
{
  Icon icon = Images.loadLocalIcon("help.png");


  /**
   * Creates a new dialoge.
   * 
   * @param parent
   *          parent of the dialoge
   * @param message
   *          information, which will be displayed
   */
  public HelpDialoge(final JFrame parent, final String message)
  {
    JOptionPane.showMessageDialog(parent, message, "Help", 1, icon);
  }
}
