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
package misux.gui.frame;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import misux.div.Images;
import misux.pref.Preferences;

/**
 * Displays information about the program and the authors.
 * 
 * @author DSIW
 * 
 */
@SuppressWarnings("serial")
public class Info extends MyJFrame
{

  /**
   * Creates a new dialoge.
   * 
   * @param parent
   *          parent
   */
  public Info(final JFrame parent)
  {
    final Icon icon = Images.loadLocalIcon("frame.png");

    final String message = parent.getTitle() + "\n" + "Version 1.0\n" + "\n"
        + "Autor: DSIW\n" + "E-Mail: dsiw@privatdemail.net\n" + "\n"
        + "License:\n"
        + "GPLv3 (siehe http://www.gnu.de/documents/gpl.de.html)";
    // setFont(new Font(getFont().getFontName(), Font.BOLD, (int) (getFont()
    // .getSize() * 1.3)));

    String programmTitle = "";
    try {
      programmTitle = Preferences.get().getOption("program.name")
          .getStringValue();
    }
    catch (final Exception e) {}
    JOptionPane.showMessageDialog(parent, message, "About " + programmTitle, 1,
        icon);
  }
}
