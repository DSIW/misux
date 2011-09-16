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
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import misux.pref.Preferences;

/**
 * This class implements methods to choose a cover from the filesystem.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class CoverChooser extends JFileChooser
{
  private JFileChooser    fc;
  private final Component parent;
  private String          coverPath;


  /**
   * Creates a new file chooser.
   * 
   * @param parent
   */
  public CoverChooser(final Component parent)
  {
    this.parent = parent;
    coverPath = "";
    initComponents();
  }


  /**
   * @return the selected absolute path of the cover. If no cover was selected,
   *         then it will return <code>null</code>.
   * @author DSIW
   */
  public String getCoverPath ()
  {
    return coverPath;
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

    fc.setDialogTitle("Choose cover");
    fc.setAccessory(new LabelAccessory(fc));
    final FileFilter filter = new CoverFilter();
    fc.addChoosableFileFilter(filter);

    final int status = fc.showOpenDialog(parent);
    if (status == JFileChooser.APPROVE_OPTION) {
      coverPath = fc.getSelectedFile().getAbsolutePath();
    } else if (status == JFileChooser.CANCEL_OPTION) {
      coverPath = null;
    }
  }
}

/**
 * View for the selected image.
 * 
 * @author DSIW
 * 
 */
@SuppressWarnings("serial")
class LabelAccessory extends JLabel implements PropertyChangeListener
{
  private static final int PREFERRED_WIDTH  = 200;
  private static final int PREFERRED_HEIGHT = 200;


  public LabelAccessory(final JFileChooser chooser)
  {
    setVerticalAlignment(SwingConstants.CENTER);
    setHorizontalAlignment(SwingConstants.CENTER);
    chooser.addPropertyChangeListener(this);
    setPreferredSize(new Dimension(LabelAccessory.PREFERRED_WIDTH,
        LabelAccessory.PREFERRED_HEIGHT));
  }


  @Override
  public void propertyChange (final PropertyChangeEvent changeEvent)
  {
    final String changeName = changeEvent.getPropertyName();
    if (changeName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
      final File file = (File) changeEvent.getNewValue();
      if (file != null) {
        ImageIcon icon = new ImageIcon(file.getPath());
        if (icon.getIconWidth() > LabelAccessory.PREFERRED_WIDTH) {
          icon = new ImageIcon(icon.getImage().getScaledInstance(
              LabelAccessory.PREFERRED_WIDTH, -1, Image.SCALE_DEFAULT));
          if (icon.getIconHeight() > LabelAccessory.PREFERRED_HEIGHT) {
            icon = new ImageIcon(icon.getImage().getScaledInstance(-1,
                LabelAccessory.PREFERRED_HEIGHT, Image.SCALE_DEFAULT));
          }
        }
        setIcon(icon);
      }
    }
  }
}
