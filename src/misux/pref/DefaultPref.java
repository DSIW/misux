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
package misux.pref;

import java.io.File;
import java.io.IOException;

import misux.io.file.FileHandler;

/**
 * Represention of default options.
 * 
 * @author DSIW
 * 
 */
public class DefaultPref extends Pref
{
  private static String readOS ()
  {
    return System.getProperty("os.name");
  }


  private static String readUserHomeDir ()
  {
    final String PATTERN = "user.home";
    return System.getProperties().getProperty(PATTERN);
  }


  private static String readUserMusicDir () throws Exception
  {
    if (!DefaultPref.readOS().equals("Linux")) {
      return "";
    }
    final String FILE_PATH = DefaultPref.readUserHomeDir() + File.separator
        + ".config" + File.separator + "user-dirs.dirs";
    final String COMMENT_PATTERN = "^#*";
    final String PATTERN = "XDG_MUSIC_DIR";

    String content = null;
    try {
      content = FileHandler.readFile(FILE_PATH);
    }
    catch (final IOException e) {
      e.printStackTrace();
    }

    final String[] lines = content.split("\n");
    for (final String line2 : lines) {
      if (line2.startsWith(PATTERN)) {
        String line = line2;
        line = line.trim();
        line = line.replaceAll(COMMENT_PATTERN, "");
        line = line.replaceAll(PATTERN + "=", "");
        line = line.replaceAll("\"", "");
        line = line.replaceAll("\\$HOME", DefaultPref.readUserHomeDir());
        return line;
      }
    }
    throw new Exception("The music dir can't read.");
  }


  private static String readUserName ()
  {
    final String PATTERN = "user.name";
    return System.getProperties().getProperty(PATTERN);
  }


  /**
   * Creates the default preferences.
   */
  public DefaultPref()
  {
    final String sep = File.separator;
    try {
      // OS
      addOption("os.name", PrefType.String, DefaultPref.readOS());
      addOption("is.linux", PrefType.Boolean, getOption("os.name")
          .getStringValue().equals("Linux") ? true : false);
      // Program
      addOption("program.name", PrefType.String, "misux");
      if (getOption("is.linux").getBooleanValue()) {
        addOption("program.uninstallsh", PrefType.String, "uninstall-misux.sh");
        addOption("program.cddiscid", PrefType.String, "cd-discid");
      }

      // System
      addOption("sys.user.homevar", PrefType.String, "$HOME");

      // Cover
      addOption("io.http.cover.source.az", PrefType.String, "Amazon.de");
      addOption("io.http.cover.source.gg", PrefType.String, "Google.de");
      addOption("io.http.cover.source", PrefType.Integer, 0);
      addOption("io.http.cover.size", PrefType.Integer, 200);

      // User
      addOption("io.user.name", PrefType.String, DefaultPref.readUserName());
      addOption("io.user.homedir", PrefType.String,
          DefaultPref.readUserHomeDir());
      addOption("io.user.musicdir", PrefType.String,
          DefaultPref.readUserMusicDir());
      if (getOption("is.linux").getBooleanValue()) {
        addOption("io.user.trashdir", PrefType.String,
            getOption("io.user.homedir").getStringValue() + sep + ".local"
                + sep + "share" + sep + "Trash" + sep + "files");
        addOption("io.user.userdirs", PrefType.String,
            getOption("io.user.homedir").getStringValue() + sep + ".config"
                + sep + "user-dirs.dirs");
      }

      // Config
      addOption("program.dir", PrefType.String, getOption("io.user.homedir")
          .getStringValue()
          + sep
          + "."
          + getOption("program.name").getStringValue());
      addOption("io.config.dir", PrefType.String, getOption("program.dir")
          .getStringValue() + sep + "config");
      addOption("io.pl.dir", PrefType.String, getOption("program.dir")
          .getStringValue() + sep + "pl");
      addOption("io.config.name", PrefType.String, "misux-config.xml");

      // Music
      addOption("io.music.convert", PrefType.Boolean, false);
      addOption("io.music.rip.freedb", PrefType.String,
          "http://swtsrv01.cs.hs-rm.de/FreeDB/GetCDInfo?id=");
      if (getOption("is.linux").getBooleanValue()) {
        addOption("io.music.rip.ripProgram.name", PrefType.String, "abcde");
        addOption(
            "io.music.rip.abcde.config",
            PrefType.String,
            getOption("io.config.dir").getStringValue() + sep
                + getOption("io.music.rip.ripProgram.name").getStringValue()
                + ".config");
        addOption(
            "io.music.rip.abcde.config.cddb",
            PrefType.String,
            getOption("io.config.dir").getStringValue() + sep
                + getOption("io.music.rip.ripProgram.name").getStringValue()
                + "-cddb.config");
      }
      addOption("io.music.rip.format", PrefType.Integer, 0);
      addOption("io.music.volume.step", PrefType.Integer, 5);
      addOption("io.music.volume.default", PrefType.Integer, 40);

      // Playlists
      addOption("pl.main.name", PrefType.String, "main");
      addOption("io.pl.main.name", PrefType.String, getOption("pl.main.name")
          .getStringValue() + ".pl");
      addOption("io.pl.other.name", PrefType.String, "playlist-#.pl");

      // TableModel
      addOption("gui.table.model.title", PrefType.Boolean, true);
      addOption("gui.table.model.album", PrefType.Boolean, true);
      addOption("gui.table.model.interpret", PrefType.Boolean, true);
      addOption("gui.table.model.duration", PrefType.Boolean, false);
      addOption("gui.table.model.license", PrefType.Boolean, true);
      addOption("gui.table.model.year", PrefType.Boolean, true);
      addOption("gui.table.model.genre", PrefType.Boolean, true);
      addOption("gui.table.model.rating", PrefType.Boolean, true);
      addOption("gui.table.model.count", PrefType.Boolean, false);
    }
    catch (final Exception e) {
      System.err.println("Default preferences can't be loaded.");
      e.printStackTrace();
    }
  }
}
