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
package misux.io.file.scripts;

import java.io.File;
import java.io.IOException;

import misux.pref.Preferences;

/**
 * This class implements methods to create an uninstall script, that will remove
 * all config files from misux.
 * 
 * @author DSIW
 * 
 */
public class Uninstall extends Script
{
  /**
   * @throws Exception 
   */
  public Uninstall() throws Exception
  {
    super();
    setOverwrite(true);
  }


  /**
   * Creates the executable uninstall script
   * 
   * @throws Exception
   *           if the file can't be created
   */
  @Override
  public String createContent () throws Exception
  {
    if (!Preferences.get().getOption("is.linux").getBooleanValue()) {
      throw new Exception(
          "We can't write an uninstall script for this operating system.");
    }

    String comment = "#!/bin/bash\n# This file uninstalls all config files from the program misux. This uninstall script will remove, too.";
    String content = "rm -rv ";
    try {
      content += Preferences.get().getOption("program.dir").getStringValue();
    }
    catch (Exception e) {
      System.err.println("Uninstall file can't be created");
      e.printStackTrace();
    }
    content += "\nrm -v " + getFileName();

    return comment + "\n" + content;
  }


  @Override
  public String createFileName () throws Exception
  {
    String tmp = Preferences.get().getOption("io.user.homedir").getStringValue()
        + File.separator
        + Preferences.get().getOption("program.uninstallsh").getStringValue();
    if(tmp == null || tmp.equals("")) throw new IOException();
    return tmp;
  }
}
