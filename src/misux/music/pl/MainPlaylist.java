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
package misux.music.pl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import misux.gui.Messages;
import misux.io.file.FileHandler;
import misux.io.xml.XMLHandler;
import misux.pref.Preferences;

/**
 * This will save and load the playlist.
 * 
 * @author DSIW
 * 
 */
public class MainPlaylist
{
  private static Playlist pl;


  private static void addListener ()
  {
    MainPlaylist.pl.addPlaylistListener(new PlaylistListener()
    {
      @Override
      public void changed ()
      {
        try {
          MainPlaylist.write();
        }
        catch (final Exception e) {
          Messages.showErrorMessage(null,
              "The playlist can't be written into the file system.",
              "Playlist error");
        }
      }
    });
  }


  /**
   * @return the playlist
   * @throws Exception
   */
  public static Playlist get () throws Exception
  {
    if (MainPlaylist.pl == null || MainPlaylist.pl.getLength() <= 0) {
      System.out.println("No track exists.");
      try {
        MainPlaylist.read();
        System.out.println("The music loaded from the file.");
      }
      catch (final Exception e) {
        final String mainPlName = Preferences.get()
            .getOption("io.pl.main.name").getStringValue();
        MainPlaylist.pl = new PlaylistImpl(mainPlName);
        MainPlaylist.addListener();
        System.out.println("Starts a new main playlist.");
        MainPlaylist.write();
      }
    }
    return MainPlaylist.pl;
  }


  /**
   * Read the playlist from the file.
   * 
   * @throws Exception
   */
  public static void read () throws Exception
  {
    final String mainPlName = Preferences.get().getOption("io.pl.main.name")
        .getStringValue();
    final File plFile = new File(Preferences.get().getOption("io.pl.dir")
        .getStringValue()
        + File.separator + mainPlName);

    if (plFile.exists() && plFile.canRead()) {
      final List<Playlist> playlists = new LinkedList<Playlist>();
      try {
        playlists.addAll(XMLHandler.fromXML(plFile.getPath()));
      }
      catch (final IOException e) {
        e.printStackTrace();
      }
      catch (final Exception e) {
        e.printStackTrace();
      }

      if (playlists.size() <= 0) {
        throw new Exception("Readed playlist file have no playlists.");
      }
      MainPlaylist.pl = playlists.get(0);
    } else {
      throw new Exception();
    }
  }


  /**
   * Write the playlist in the file, which is specified in the options.
   * 
   * @throws Exception
   */
  public static void write () throws Exception
  {
    final String mainPlName = Preferences.get().getOption("io.pl.main.name")
        .getStringValue();
    final File plFile = new File(Preferences.get().getOption("io.pl.dir")
        .getStringValue()
        + File.separator + mainPlName);

    System.out.println("Write playlist...");
    if (MainPlaylist.pl == null) {
      MainPlaylist.pl = new PlaylistImpl(mainPlName);
      MainPlaylist.addListener();
    }
    final String content = XMLHandler.toXML(MainPlaylist.pl);

    FileHandler.writeString(content, plFile);
  }
}
