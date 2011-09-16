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
package misux.pref;

import java.io.File;
import java.io.IOException;

import misux.io.file.FileHandler;

/**
 * This class implements methods to save and load preferences.
 * 
 * @author DSIW
 * 
 */
public class Preferences
{
  private static Pref    preferences;
  private static Pref    readedPrefs;
  private static Pref    defaultPrefs;
  private static boolean written = false;


  /**
   * @return the preferences
   * @throws Exception
   *           , if it can't be read or write
   */
  public static Pref get () throws Exception
  {
    if (Preferences.defaultPrefs == null) {
      Preferences.defaultPrefs = new DefaultPref();
    }
    try {
      if (Preferences.readedPrefs == null || Preferences.written) {
        Preferences.read();
      }
    }
    catch (final Exception e1) {
      Preferences.preferences = Preferences.defaultPrefs;
      System.out.println("The default preferences loaded.");
      Preferences.write();
      return Preferences.preferences;
    }
    // test, if defaultPrefs are more actual
    // if (!readedPrefs.equals(defaultPrefs)) {
    // write();
    // preferences = defaultPrefs;
    // System.out.println("The default preferences loaded.");
    // return preferences;
    // }
    // load prefs
    if (Preferences.preferences == null
        || Preferences.preferences.getNumberOfOptions() <= 0) {
      System.out.print("No preferences exists. ");
      try {
        Preferences.preferences = Preferences.readedPrefs;
        System.out.println("The preferences loaded from the config file.");
        return Preferences.preferences;
      }
      catch (final Exception e) {
        Preferences.preferences = Preferences.defaultPrefs;
        System.out.println("The default preferences loaded.");
        Preferences.write();
        return Preferences.preferences;
      }
    }
    return Preferences.preferences;
  }


  /**
   * Reads the preferences from the config file.
   * 
   * @throws Exception
   *           if it can't be read
   */
  public static void read () throws Exception
  {
    final File prefFile = new File(Preferences.defaultPrefs.getOption(
        "io.config.dir").getStringValue()
        + File.separator
        + Preferences.defaultPrefs.getOption("io.config.name").getStringValue());
    if (prefFile.exists() && prefFile.canRead()) {
      final Pref p = new Pref();
      try {
        p.parseXML(FileHandler.readFile(prefFile));
      }
      catch (final IOException e) {
        e.printStackTrace();
      }
      catch (final Exception e) {
        e.printStackTrace();
      }
      Preferences.readedPrefs = p;
      Preferences.written = false;
    } else {
      throw new Exception();
    }
  }


  /**
   * Writes the preferences in the config file.
   * 
   * @throws Exception
   *           if it can't be written into filesystem
   */
  public static void write () throws Exception
  {
    System.out.println("Write prefs...");
    if (Preferences.preferences == null) {
      Preferences.preferences = Preferences.defaultPrefs;
    }
    FileHandler.writeString(
        Preferences.preferences.toXML(),
        Preferences.preferences.getOption("io.config.dir").getStringValue()
            + File.separator
            + Preferences.preferences.getOption("io.config.name")
                .getStringValue());
    Preferences.written = true;
  }

  // {
  // preferences.addPrefListener(new PrefListener()
  // {
  // @Override
  // public void changed ()
  // {
  // try {
  // write();
  // }
  // catch (Exception e) {
  // Messages.showErrorMessage(null,
  // "The playlist can't be written into the file system.",
  // "Playlist error");
  // }
  // }
  // });
  // }
}
