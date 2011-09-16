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
package misux.music.rip;

import java.io.File;
import java.io.IOException;

import misux.div.exceptions.ExecutionException;
import misux.div.exceptions.NoCDException;
import misux.div.exceptions.NoCoverFoundException;
import misux.io.Run;
import misux.io.file.AbcdeConfigCDDB;
import misux.io.http.cover.Cover;
import misux.io.http.cover.GetCover;
import misux.io.http.cover.GetCoverAmazon;
import misux.music.pl.Playlist;
import misux.music.track.Track;
import misux.pref.Preferences;

/**
 * This class implements methods to get informations of the CD. The CD can
 * eject.
 * 
 * @author DSIW
 * 
 */
public class CD
{
  private String   id;
  private Cover    cover;
  private Playlist pl;


  /**
   * Ejects the inserted CD.
   * 
   * @throws Exception
   */
  public void eject () throws Exception
  {
    if (inserted()) {
      if (Preferences.get().getOption("is.linux").getBooleanValue()) {
        new Run("eject").exec();
      }
    } else {
      throw new NoCDException();
    }
  }


  /**
   * @return the id
   * @throws ExecutionException
   * @throws IOException
   * @throws InterruptedException
   */
  public String getCDID () throws InterruptedException, IOException,
      ExecutionException
  {
    if (id == null) {
      readCDID();
    }
    return id;
  }


  /**
   * @return the cover from the CD.
   * @throws Exception
   * @author DSIW
   */
  public Cover getCover () throws Exception
  {
    if (cover == null) {
      readCover();
    }
    return cover;
  }


  /**
   * @return the pl
   * @throws Exception
   */
  public Playlist getPlaylist () throws Exception
  {
    if (pl == null) {
      readPlaylist();
    }
    return pl;
  }


  /**
   * @return true, if a CD is inserted; returns false, if a CD is not inserted.
   */
  public boolean inserted ()
  {
    final GetCDID id = new GetCDID();
    try {
      if (id.getID().length() > 0) {
        return true;
      }
    }
    catch (final InterruptedException e) {}
    catch (final IOException e) {}
    catch (final ExecutionException e) {
      if (e.getMessage().contains("No medium found")) {
        return false;
      }
    }
    return false;
  }


  /**
   * Reads the CDID and sets the field.
   * 
   * @throws InterruptedException
   * @throws IOException
   * @throws ExecutionException
   */
  private void readCDID () throws InterruptedException, IOException,
      ExecutionException
  {
    if (inserted()) {
      final GetCDID id = new GetCDID();
      this.id = id.getID();
    } else {
      throw new NoCDException();
    }
  }


  private void readCover () throws Exception
  {
    final Track t0 = getPlaylist().getTrack(0);
    final GetCover gc = new GetCoverAmazon(t0.getInterpret(), t0.getAlbum(),
        200, 1);
    if (gc.noFound()) {
      throw new NoCoverFoundException();
    }
    cover = gc.getCover();
  }


  /**
   * Reads the tracks on the inserted CD.
   * 
   * @throws Exception
   */
  private void readPlaylist () throws Exception
  {
    if (inserted()) {
      // GetTags tags = new GetTags(getCDID());
      // tags.readFirstAlbum();
      // pl = tags.getPlaylist();

      final misux.io.file.File abcdeConfig = new AbcdeConfigCDDB();
      abcdeConfig.make();
      final String configPath = abcdeConfig.getFileName();
      final Run abcde = new Run("abcde", "-c", configPath.trim());
      abcde.setDir(File.separator + "tmp");
      abcde.exec();

      final ReadTagsFromAbcde tags = new ReadTagsFromAbcde(getCDID());
      pl = tags.getPlaylist();
    } else {
      throw new NoCDException();
    }
  }


  /**
   * @param id
   *          the id to set
   */
  public void setCDID (final String id)
  {
    this.id = id;
  }


  /**
   * Sets the cover for the CD.
   * 
   * @param cover
   *          cover
   * @author DSIW
   */
  public void setCover (final Cover cover)
  {
    this.cover = cover;
  }


  /**
   * @param pl
   *          the pl to set
   */
  public void setPlaylist (final Playlist pl)
  {
    this.pl = pl;
  }

}
