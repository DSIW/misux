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

import java.awt.Component;

import javax.swing.JFileChooser;

import misux.pref.Preferences;

/**
 * This is a dialoge to select a path.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class PathChooser extends JFileChooser
{
  private JFileChooser    fc;
  private final Component parent;
  private String          path;


  /**
   * Creates a new path chooser.
   * 
   * @param parent
   *          parent of the dialoge
   */
  public PathChooser(final Component parent)
  {
    this.parent = parent;
    path = "";
    initComponents();
  }


  /**
   * @return selected path. If no path was selected, then it will return
   *         <code>null</code>.
   * @author DSIW
   */
  public String getPath ()
  {
    return path;
  }


  private void initComponents ()
  {
    String pathToLibrary = "";
    try {
      pathToLibrary = Preferences.get().getOption("io.user.musicdir")
          .getStringValue();
    }
    catch (final Exception e) {
      pathToLibrary = ".";
    }
    fc = new JFileChooser(pathToLibrary);

    fc.setDialogTitle("Choose path");
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    final int status = fc.showOpenDialog(parent);
    if (status == JFileChooser.APPROVE_OPTION) {
      path = fc.getSelectedFile().getAbsolutePath();
    } else if (status == JFileChooser.CANCEL_OPTION) {
      path = null;
    }
  }
}