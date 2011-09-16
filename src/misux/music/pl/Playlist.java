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

import java.util.Set;

import misux.music.track.Track;

/**
 * This is a playlist. The name of each playlist shall be unique.
 * 
 * @author DSIW
 */
/**
 * @author DSIW
 * @author DSIW
 * 
 */
public interface Playlist
{
  /**
   * Adds the tracks from the specified playlist to this playlist.
   * 
   * @param pl
   *          playlist
   * @author DSIW
   */
  public void add (Playlist pl);


  /**
   * Adds a track to the playlist at the end of it.
   * 
   * @param track
   *          track
   * @author DSIW
   */
  public void add (Track track);


  /**
   * Addds a specified playlist listener
   * 
   * @param lst
   *          playlist listener
   * @author DSIW
   */
  public void addPlaylistListener (PlaylistListener lst);


  /**
   * Cleans up the indexes. You can increment the index and you will find on
   * each of it a track.
   * 
   * @author DSIW
   */
  public void cleanUpIndex ();


  /**
   * Checks if a track is contained in the playlist.
   * 
   * @param track
   *          track to check if contained
   * @return true if contained
   * @author DSIW
   */
  public boolean contains (Track track);


  /**
   * @return a new instance of this playlist. All fields will be copied.
   * @author DSIW
   */
  public Playlist copy ();


  @Override
  public boolean equals (Object obj);


  /**
   * Calls the method changed in playlist listener.
   * 
   * @author DSIW
   */
  public void firePlaylistChanged ();


  /**
   * @return actually track of the playlist
   * @author DSIW
   */
  public Track getActualTrack ();


  /**
   * Returns a set of all albums contained in the playlist.
   * 
   * @return all albums contained in the playlist
   * @author DSIW
   */
  public Set<String> getAlbums ();


  /**
   * Returns a set of all interprets contained in the playlist.
   * 
   * @return all interprets contained in the playlist
   * @author DSIW
   */
  public Set<String> getInterprets ();


  /**
   * @return count of all tracks in this playlist
   * @author DSIW
   */
  public int getLength ();


  /**
   * @return name of the playlist
   * @author DSIW
   */
  public String getName ();


  /**
   * @return a randomized playlist. All tracks are randomized.
   * @author DSIW
   */
  public Playlist getRandomized ();


  /**
   * @param index
   *          position of the track in the playlist
   * @return track with the specified index
   * @author DSIW
   */
  public Track getTrack (int index);


  /**
   * @return true if the playlist have no tracks
   * @author DSIW
   */
  public boolean isEmpty ();


  /**
   * Moves the track with the specified index down by one step.
   * 
   * @param index
   *          track with the index will be moved
   * @author DSIW
   */
  public void moveTrackDown (int index);


  /**
   * Moves the track with the specified index up by one step.
   * 
   * @param index
   *          track with the index will be moved
   * @author DSIW
   */
  public void moveTrackUp (int index);


  /**
   * The new actually track is the next track.
   * 
   * @author DSIW
   */
  public void next ();


  /**
   * The new actually track is the previous track.
   * 
   * @author DSIW
   */
  public void prev ();


  /**
   * Removes the track with the specified index from the playlist.
   * 
   * @param index
   *          track with the index will be removed
   * @author DSIW
   */
  public void remove (int index);


  /**
   * Removes the specified playlist listener
   * 
   * @param lst
   *          playlist listener
   * @author DSIW
   */
  public void removePlaylistListener (PlaylistListener lst);


  /**
   * Searches in the playlist.
   * 
   * @param searchString
   *          content to search for
   * @param title
   *          if you want to search for titles
   * @param album
   *          if you want to search for albums
   * @param interpret
   *          if you want to search for interprets
   * @return playlist with content found by searching
   * @author DSIW
   */
  public Playlist search (String searchString, boolean title, boolean album,
      boolean interpret);


  /**
   * Searches in the playlist for albums.
   * 
   * @param searchString
   *          content to search for
   * @return playlist with content found by searching
   * @author DSIW
   */
  public Playlist searchAlbum (String searchString);


  /**
   * Searches in the playlist for interprets.
   * 
   * @param searchString
   *          content to search for
   * @return playlist with content found by searching
   * @author DSIW
   */
  public Playlist searchInterpret (String searchString);


  /**
   * Searches in the playlist for titles.
   * 
   * @param searchString
   *          content to search for
   * @return playlist with content found by searching
   * @author DSIW
   */
  public Playlist searchTitle (String searchString);


  /**
   * Sets the actually track with the spicified index.
   * 
   * @param index
   *          the track with this index will be the actually track
   * @author DSIW
   */
  public void setActualTrack (int index);


  /**
   * Sets the name of the playlist.
   * 
   * @param name
   *          name of playlist
   * @author DSIW
   */
  public void setName (String name);



  /**
   * @param index
   *          position for the track in the playlist
   * @param track
   *          Track
   * @author DSIW
   */
  public void setTrack (int index, Track track);


  /**
   * Sorts all tracks by index.
   * 
   * @author DSIW
   */
  public void sort ();


  @Override
  public String toString ();
}
