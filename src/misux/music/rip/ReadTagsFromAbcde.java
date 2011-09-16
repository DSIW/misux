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
package misux.music.rip;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import misux.io.file.FileHandler;
import misux.music.Genre;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistImpl;
import misux.music.track.Track;
import misux.music.track.TrackImpl;

/**
 * This class implements methods to get the tags from the CD. It uses the tags
 * from the program <code>abcde</code>.
 * 
 * @author DSIW
 * 
 */
public class ReadTagsFromAbcde
{
  private final File file;
  private Playlist   playlist;
  String             content;


  /**
   * Creates a new object and reads the tags.
   * 
   * @param id
   * @throws IOException
   */
  public ReadTagsFromAbcde(final String id) throws IOException
  {
    file = new File(File.separator + "tmp" + File.separator + "abcde."
        + id.toLowerCase() + File.separator + "cddbread.1");
    readFile();
    rmComments();
  }


  private String getAfterPattern (final String PATTERN)
  {
    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\n");
    for (final String line : lines) {
      if (line.startsWith(PATTERN)) {
        final int startIndex = line.indexOf(PATTERN) + PATTERN.length();
        sb.append(line.substring(startIndex));
      }
    }
    return sb.toString();
  }


  private String getAlbum ()
  {
    final String title = getAfterPattern("DTITLE=");
    final String PATTERN = " / ";
    return title.substring(title.indexOf(PATTERN) + PATTERN.length());
  }


  private Genre getGenre ()
  {
    return Genre.value(getAfterPattern("DGENRE"));
  }


  private String getInterpret ()
  {
    final String title = getAfterPattern("DTITLE=");
    final String PATTERN = " / ";
    return title.substring(0, title.indexOf(PATTERN));
  }


  /**
   * Gets the playlist. If it is <code>null</code>, then it will be load.
   * 
   * @return the playlist.
   * @author DSIW
   * @throws Exception
   */
  public Playlist getPlaylist () throws Exception
  {
    if (playlist == null) {
      loadPlaylist();
    }
    return playlist;
  }


  private List<String> getTitles ()
  {
    final List<String> tracklist = new LinkedList<String>();
    final String[] lines = content.split("\n");
    for (final String line : lines) {
      if (line.startsWith("TTITLE")) {
        final int startIndex = line.indexOf("=") + "=".length();
        tracklist.add(line.substring(startIndex));
      }
    }
    return tracklist;
  }


  private int getYear ()
  {
    return Integer.parseInt(getAfterPattern("DYEAR="));
  }


  private void loadPlaylist () throws Exception
  {
    final String interpret = getInterpret();
    final String album = getAlbum();
    final Genre genre = getGenre();
    final int year = getYear();

    final Playlist pl = new PlaylistImpl("GETTAGS");
    for (int i = 0; i < getTitles().size(); i++) {
      final Track t = new TrackImpl();
      t.setCdIndex(i + 1);
      t.setIndex(t.getCdIndex());
      t.setTitle(getTitles().get(i));
      t.setAlbum(album);
      t.setInterpret(interpret);
      t.setGenre(genre);
      t.setYear(year);
      pl.add(t);
    }
    playlist = pl;
  }


  private void readFile () throws IOException
  {
    content = FileHandler.readFile(file);
  }


  private void rmComments ()
  {
    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\r\n");
    for (int l = 1; l < lines.length; l++) {
      if (lines[l].startsWith("#") || lines[l].startsWith("EXT")
          || lines[l].startsWith(".") || lines[l].startsWith("PLAYORDER")
          || lines[l].startsWith("DISCID")) {
        continue;
      }
      sb.append(lines[l] + "\n");
    }
    content = sb.toString();
  }
}
