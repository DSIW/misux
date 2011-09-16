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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import misux.div.exceptions.MusicIsRippedException;
import misux.div.exceptions.NoCDException;
import misux.div.exceptions.NoCoverFoundException;
import misux.io.Run;
import misux.io.file.AbcdeConfig;
import misux.io.http.cover.Cover;
import misux.io.http.cover.GetCover;
import misux.io.http.cover.GetCoverAmazon;
import misux.music.Encoding;
import misux.music.pl.MainPlaylist;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistImpl;
import misux.music.track.Track;
import misux.pref.Preferences;

/**
 * This class implements methods to rip music from a CD.
 * 
 * @author DSIW
 * 
 */
public class Ripper
{
  private static boolean foundTrackInList (final List<Integer> tl, final Track t)
  {
    for (int i = 0; i < tl.size(); i++) {
      final int track_id = t.getCdIndex();
      final int list_id = tl.get(i);
      if (track_id == list_id) {
        return true;
      } else if (list_id < track_id) {
        continue;
      } else {
        return false;
      }
    }
    return false;
  }

  private final Encoding                  enc;
  private final CD                        cd;
  private final ArrayList<RipperListener> listener = new ArrayList<RipperListener>();
  /**
   * State of the player, if is canceled.
   */
  public final static short               CANCELED = 0;
  /**
   * State of the player, if is ripping.
   */
  public final static short               RIPPING  = 1;
  /**
   * State of the player, if the rip process ended.
   */
  public final static short               RIPPED   = 2;

  private short                           state;


  /**
   * Sets the encoding for the music files.
   * 
   * @param enc
   *          encoding
   */
  public Ripper(final Encoding enc)
  {
    state = Ripper.CANCELED;
    this.enc = enc;
    cd = new CD();
  }


  private void addAllToMainPlaylist () throws Exception
  {
    final boolean flag = MainPlaylist.get().getLength() > 0;
    final Playlist tmp = new PlaylistImpl(cd.getPlaylist().getName());
    for (int t = 0; t < cd.getPlaylist().getLength(); t++) {
      final Track track = cd.getPlaylist().getTrack(t);
      track.setEnc(enc);
      if (!MainPlaylist.get().contains(track)) {
        MainPlaylist.get().add(track.copy());
        if (!flag) {
          tmp.add(track.copy()); // IMPORTANT!
        }
      }
    }
    cd.setPlaylist(tmp);
    MainPlaylist.write();
  }


  /**
   * Adds a ripper listener.
   * 
   * @param lst
   *          ripper listener
   * @author DSIW
   */
  public void addRipperListener (final RipperListener lst)
  {
    listener.add(lst);
  }


  private void cancel () throws Exception
  {
    state = Ripper.CANCELED;
    final String ripProgram = Preferences.get()
        .getOption("io.music.rip.ripProgram.name").getStringValue();
    new Run("killall", ripProgram).exec();
    mergeTracks();
    addAllToMainPlaylist();
    ended();
  }


  private void ended ()
  {
    for (final RipperListener lst : listener) {
      lst.ended(getState() == Ripper.RIPPED);
    }
  }


  /**
   * This will call the cancel method for all listeners.
   * 
   * @throws Exception
   * @author DSIW
   */
  public void fireCanceled () throws Exception
  {
    cancel();
  }


  /**
   * The state will be <code>ripping</code>. If a exception will be thrown, then
   * the parameter of started() in the RipperListener will be <code>null</code>.
   * 
   * @author DSIW
   */
  public void fireStarted ()
  {
    started();
  }


  /**
   * @return the enc
   */
  public Encoding getEnc ()
  {
    return enc;
  }


  private List<Integer> getNotRippedTrackNumbers () throws Exception
  {
    final List<Integer> notRipped = getTrackNumbers();
    final List<Integer> ripped = getRippedTrackNumbers();
    notRipped.removeAll(ripped);
    return notRipped;
  }


  /**
   * @return playlist of ripped tracks
   * @throws Exception
   * @author DSIW
   */
  public Playlist getPlaylist () throws Exception
  {
    return cd.getPlaylist();
  }


  private List<Integer> getRippedTrackNumbers () throws Exception
  {
    final List<Integer> ripped = new LinkedList<Integer>();
    final List<Integer> tracks = getTrackNumbers();
    for (int i = 0; i < tracks.size(); i++) {
      final Track t = cd.getPlaylist().getTrack(i);
      t.setEnc(enc);
      final File file = new File(t.getPathToMusic());
      if (file.exists() || file.canRead()) {
        ripped.add(i + 1);
      }
    }
    return ripped;
  }


  /**
   * @return the state of the ripper.
   * @author DSIW
   */
  public int getState ()
  {
    return state;
  }


  private List<Integer> getTrackNumbers () throws Exception
  {
    final List<Integer> ripped = new LinkedList<Integer>();
    if (!cd.inserted()) {
      throw new NoCDException();
    }
    if (cd.getPlaylist().isEmpty()) {
      return ripped;
    }
    for (int i = 0; i < cd.getPlaylist().getLength(); i++) {
      ripped.add(i + 1);
    }
    return ripped;
  }


  /**
   * @return true, if the music files of the CD are already in the music XML
   *         file.
   * @throws Exception
   */
  public boolean isAlreadyRipped () throws Exception
  {
    if (cd.getPlaylist().isEmpty()) {
      return false;
    }
    if (getNotRippedTrackNumbers().size() == 0) {
      return true;
    }
    return false;
  }


  private void mergeFilesWithCover () throws Exception
  {
    final Playlist playlist = new CD().getPlaylist();
    if (playlist.getLength() <= 0) {
      return;
    }

    final Track t0 = playlist.getTrack(0);
    t0.setEnc(enc);
    final String pathToMusic = new File(t0.getPathToMusic()).getParent();

    // load cover
    Cover cover = null;
    try {
      final GetCover gc = new GetCoverAmazon(t0.getInterpret(), t0.getAlbum(),
          200, 1);
      if (gc.noFound()) {
        throw new NoCoverFoundException();
      }
      cover = gc.getCover();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    // write cover to filesystem
    if (cover != null) {
      cover.setPath(pathToMusic);
      if (cover.isWriteble()) {
        cover.write();
      }
    }

    // merge
    for (int i = 0; i < cd.getPlaylist().getLength(); i++) {
      final Track t = cd.getPlaylist().getTrack(i);
      t.setEnc(enc);
      t.setCover(cover);
    }
  }


  private void mergeFilesWithTags () throws Exception
  {
    final List<Integer> rippedTrackIDs = getRippedTrackNumbers();
    final Playlist tmpPL = new PlaylistImpl(cd.getPlaylist().getName());
    for (int i = 0; i < cd.getPlaylist().getLength(); i++) {
      final Track t = cd.getPlaylist().getTrack(i);
      if (Ripper.foundTrackInList(rippedTrackIDs, t)) { // faster search,
                                                        // because the
        // list is sorted. You can
        // implement a binary
        // search.
        t.setEnc(enc);
        tmpPL.add(t);
      }
    }
    cd.setPlaylist(tmpPL);
  }


  private void mergeTracks () throws Exception
  {
    // order is important!
    mergeFilesWithTags();
    mergeFilesWithCover();
  }


  /**
   * Removes the specified listener
   * 
   * @param lst
   *          listener, which will remove.
   * @author DSIW
   */
  public void removeRipperListener (final RipperListener lst)
  {
    listener.remove(lst);
  }


  /**
   * Test, if a CD is inserted and if all tracks are ripped, rips the CD,
   * downloads a cover, add the not ripped tracks to the main playlist.
   * 
   * @throws NoCDException
   *           if no CD is inserted
   * @throws MusicIsRippedException
   *           if the music is already ripped
   * @throws Exception
   * @author DSIW
   */
  public void rip () throws NoCDException, MusicIsRippedException, Exception
  {
    try {
      final List<Integer> notRipped = getNotRippedTrackNumbers();
      if (isAlreadyRipped()) {
        mergeTracks();
        addAllToMainPlaylist();
        state = Ripper.RIPPED;
        throw new MusicIsRippedException();
      } else {
        rip(notRipped);
        state = Ripper.RIPPED;
      }
      System.out.println(getPlaylist());
    }
    catch (final NoCDException e) {
      throw e;
    }
    catch (final MusicIsRippedException e) {
      throw e;
    }
    catch (final Exception e) {
      throw e;
    }
    finally {
      state = Ripper.CANCELED;
    }
  }


  private void rip (final List<Integer> tracksToRip) throws NoCDException,
      Exception
  {
    // cancel tests
    if (!cd.inserted()) {
      throw new NoCDException();
    }
    started();

    // init abcdeConfig file
    final misux.io.file.File abcdeConfig = new AbcdeConfig();
    abcdeConfig.make();
    final String configPath = abcdeConfig.getFileName();

    // init run command
    final String encoder = enc.toString().toLowerCase();
    final Run abcde = new Run("abcde", "-c", configPath.trim(), "-o", encoder);
    // add tracks
    for (int t = 0; t < tracksToRip.size(); t++) {
      abcde.getArgs().add(tracksToRip.get(t).toString());
    }
    abcde.setDir(File.separator + "tmp");

    // ripping
    if (tracksToRip.size() > 1) {
      System.out.println("Ripping tracks "
          + tracksToRip.toString().substring(1,
              tracksToRip.toString().length() - 1) + " ...");
    } else {
      System.out.println("Ripping track " + tracksToRip.get(0) + " ...");
    }
    abcde.exec();
    System.out.println("Ripped.");

    mergeTracks();
    addAllToMainPlaylist();
    ended();
  }


  private void started ()
  {
    state = Ripper.RIPPING;
    for (final RipperListener lst : listener) {
      try {
        lst.started(getNotRippedTrackNumbers());
      }
      catch (final Exception e) {
        lst.started(null);
      }
    }
  }
}
