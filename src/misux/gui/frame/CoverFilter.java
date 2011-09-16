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

import java.io.File;
import java.util.Arrays;

import javax.swing.filechooser.FileFilter;

import misux.div.Utils;
import misux.io.http.cover.PictureExtensions;

/**
 * This is a filter for covers for the cover chooser.
 * 
 * @author DSIW
 * 
 */
public class CoverFilter extends FileFilter
{
  String   description;
  String[] exts;


  /**
   * Creates a new filter.
   */
  public CoverFilter()
  {
    description = "Cover files (JPG, PNG,...)";
    final PictureExtensions[] picExts = PictureExtensions.values();
    final String[] strPicExts = new String[picExts.length];
    for (int p = 0; p < strPicExts.length; p++) {
      strPicExts[p] = picExts[p].name().toLowerCase();
    }
    Arrays.sort(strPicExts);
    exts = strPicExts;
  }


  @Override
  public boolean accept (final File f)
  {
    if (f.isDirectory()) {
      return true;
    } else if (f.isFile()) {
      for (final String ext : exts) {
        try {
          if (Utils.getExtension(f).equalsIgnoreCase(ext)) {
            return true;
          }
        }
        catch (final Exception e) {
          return true;
        }
      }
    }
    return false;
  }


  @Override
  public String getDescription ()
  {
    return description;
  }
}
