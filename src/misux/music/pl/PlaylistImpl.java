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
package misux.music.pl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import misux.div.RandomInteger;
import misux.music.track.Track;
import misux.music.track.TrackListener;

/**
 * This class implements the interface Playlist.
 * 
 * @author DSIW
 */
public class PlaylistImpl implements Playlist
{
  private final List<Track>                 tracklist;
  private String                            name;
  private int                               pos;
  private final ArrayList<PlaylistListener> listener;


  // @Override
  // public void setTracks(List<Track> tracklist) {
  // this.tracklist = tracklist;
  // }

  /**
   * Creates a new playlist with no tracks and the specified name.
   * 
   * @param name
   *          name of the playlist
   */
  public PlaylistImpl(final String name)
  {
    listener = new ArrayList<PlaylistListener>();
    tracklist = new ArrayList<Track>();
    setName(name);
    pos = 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void add (final Playlist pl)
  {
    for (int i = 0; i < pl.getLength(); i++) {
      final Track t = pl.getTrack(i);
      add(t);
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void add (final Track track)
  {
    add(track, false);
  }


  private void add (final Track track, final boolean fromAddPlaylist)
  {
    if (track == null) {
      return;
    }
    track.setIndex(getLength() + 1);
    tracklist.add(track);
    if (!fromAddPlaylist) {
      addTrackListener(track);
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void addPlaylistListener (final PlaylistListener lst)
  {
    listener.add(lst);
  }


  private void addTrackListener (final Track t)
  {
    t.addTrackListener(new TrackListener()
    {
      @Override
      public void changed ()
      {
        playlistChanged();
      }
    });
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void cleanUpIndex ()
  {
    sort();
    for (int t = 0; t < getLength(); t++) {
      tracklist.get(t).setIndex(t + 1);
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public boolean contains (final Track track)
  {
    for (int i = 0; i < getLength(); i++) {
      if (getTrack(i).equals(track)) {
        return true;
      }
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Playlist copy ()
  {
    final PlaylistImpl playlist = new PlaylistImpl(getName());
    for (int i = 0; i < getLength(); i++) {
      playlist.add(getTrack(i));
    }
    return playlist;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public boolean equals (final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof PlaylistImpl)) {
      return false;
    }
    final PlaylistImpl other = (PlaylistImpl) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (pos != other.pos) {
      return false;
    }
    if (tracklist == null) {
      if (other.tracklist != null) {
        return false;
      }
    } else if (!tracklist.equals(other.tracklist)) {
      return false;
    }
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void firePlaylistChanged ()
  {
    playlistChanged();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Track getActualTrack ()
  {
    return tracklist.get(pos);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Set<String> getAlbums ()
  {
    final Set<String> set = new HashSet<String>();
    for (int i = 0; i < getLength(); i++) {
      set.add(getTrack(i).getAlbum());
    }
    return set;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Set<String> getInterprets ()
  {
    final Set<String> set = new HashSet<String>();
    for (int i = 0; i < getLength(); i++) {
      set.add(getTrack(i).getInterpret());
    }
    return set;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getLength ()
  {
    if (tracklist == null || tracklist.isEmpty()) {
      return 0;
    }
    return tracklist.size();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getName ()
  {
    return name;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   * @author DSIW
   */
  @Override
  public Playlist getRandomized ()
  {
    System.out.println(this);
    final RandomInteger random = new RandomInteger();
    final Playlist copy = copy();
    for (int t = 0; t < copy.getLength(); t++) {
      final int ri = random.getRandomInteger(0, getLength() * 10);
      copy.getTrack(t).setIndex(ri);
    }
    copy.cleanUpIndex();
    if (this.equals(copy)) {
      return copy.getRandomized();
    }
    System.out.println();
    System.out.println(copy);
    return copy;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Track getTrack (final int pos)
  {
    return tracklist.get(pos);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public boolean isEmpty ()
  {
    return getLength() == 0;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void moveTrackDown (final int pos)
  {
    moveTrackUp(pos + 1);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void moveTrackUp (final int pos)
  {
    final Track up = getTrack(pos - 1);
    final Track down = getTrack(pos);
    final int swapindex = up.getIndex();
    up.setIndex(down.getIndex());
    down.setIndex(swapindex);
    setTrack(pos - 1, down);
    setTrack(pos, up);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void next ()
  {
    if (pos + 1 < getLength()) {
      pos++;
    } else {
      pos = 0;
    }
  }


  private void playlistChanged ()
  {
    for (final PlaylistListener pl : listener) {
      pl.changed();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void prev ()
  {
    if (pos - 1 >= 0) {
      pos--;
    } else {
      pos = getLength() - 1;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void remove (final int pos)
  {
    tracklist.remove(pos);
    playlistChanged();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void removePlaylistListener (final PlaylistListener lst)
  {
    listener.remove(lst);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Playlist search (final String searchString, final boolean title,
      final boolean album, final boolean interpret)
  {
    final Playlist playlist = new PlaylistImpl(getName());
    if (title) {
      final Playlist t = searchTitle(searchString);
      for (int i = 0; i < t.getLength(); i++) {
        final Track track = t.getTrack(i);
        if (!playlist.contains(track)) {
          playlist.add(track);
        }
      }
    }
    if (album) {
      final Playlist a = searchAlbum(searchString);
      for (int i = 0; i < a.getLength(); i++) {
        final Track track = a.getTrack(i);
        if (!playlist.contains(track)) {
          playlist.add(track);
        }
      }
    }
    if (interpret) {
      final Playlist inter = searchInterpret(searchString);
      for (int i = 0; i < inter.getLength(); i++) {
        final Track track = inter.getTrack(i);
        if (!playlist.contains(track)) {
          playlist.add(track);
        }
      }
    }
    return playlist;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Playlist searchAlbum (final String searchString)
  {
    final Playlist playlist = new PlaylistImpl(getName());
    for (int i = 0; i < getLength(); i++) {
      final Track t = getTrack(i);
      if (t.getAlbum().toLowerCase().contains(searchString.toLowerCase())) {
        playlist.add(t);
      }
    }
    return playlist;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Playlist searchInterpret (final String searchString)
  {
    final Playlist playlist = new PlaylistImpl(getName());
    for (int i = 0; i < getLength(); i++) {
      final Track t = getTrack(i);
      if (t.getInterpret().toLowerCase().contains(searchString.toLowerCase())) {
        playlist.add(t);
      }
    }
    return playlist;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Playlist searchTitle (final String searchString)
  {
    final Playlist playlist = new PlaylistImpl(getName());
    for (int i = 0; i < getLength(); i++) {
      final Track t = getTrack(i);
      if (t.getTitle().toLowerCase().contains(searchString.toLowerCase())) {
        playlist.add(t);
      }
    }
    return playlist;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setActualTrack (final int pos)
  {
    this.pos = pos;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setName (final String name)
  {
    this.name = name;
    playlistChanged();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setTrack (final int pos, final Track track)
  {
    tracklist.set(pos, track);
    playlistChanged();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void sort ()
  {
    for (int i = 0; i < getLength() - 1; i++) {
      int swaps = 0;
      for (int j = 0; j < getLength() - i - 1; j++) {
        final Track t1 = getTrack(j);
        final Track t2 = getTrack(j + 1);
        if (t2.getIndex() < t1.getIndex()) {
          setTrack(j, t2);
          setTrack(j + 1, t1);
          swaps++;
        }
      }
      if (swaps == 0) {
        return;
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String toString ()
  {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < getLength(); i++) {
      final Track t = tracklist.get(i);
      sb.append(t);
      sb.append("\n");
    }
    return sb.toString();
  }
}
