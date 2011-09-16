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
package misux.music.track;

import misux.io.http.cover.Cover;
import misux.music.Encoding;
import misux.music.Genre;
import misux.music.license.License;

/**
 * This interface implements getters and setters and other usefull methods for a
 * track.
 * 
 * @author DSIW
 * @author DSIW
 */
public interface Track
{
  /**
   * Adds a track listener.
   * 
   * @param lst
   *          track listener to add
   * @author DSIW
   */
  public void addTrackListener (TrackListener lst);


  /**
   * @return copied track. It is a new instance of this class.
   * @author DSIW
   */
  public Track copy ();


  /**
   * Check, if the parameter is equals with this track. The following field of
   * the track will not check: count, index, rating
   * 
   * @param obj
   *          the object which will compare
   * @return true if both are the same
   * @author DSIW
   */
  @Override
  public boolean equals (Object obj);


  /**
   * Calls the method <code>changed()</code> in track listener.
   * 
   * @author DSIW
   */
  public void fireTrackChanged ();


  /**
   * @return name of the album
   * @author DSIW
   */
  public String getAlbum ();


  /**
   * @return index in the playlist from the CD
   * @author DSIW
   */
  public int getCdIndex ();


  /**
   * 
   * @return count of playing this track
   * @author DSIW
   */
  public int getCount ();


  /**
   * @return cover as cover object
   * @author DSIW
   */
  public Cover getCover ();


  /**
   * @return duration in milliseconds
   * @author DSIW
   */
  public int getDuration ();


  /**
   * @return duration as String <code>min:sec</code>
   * @throws Exception
   * @author DSIW
   */
  public String getDurationToString () throws Exception;


  /**
   * @return encoding of the track
   * @author DSIW
   */
  public Encoding getEnc ();


  /**
   * @return genre of the track
   * @author DSIW
   */
  public Genre getGenre ();


  /**
   * @return genre of the track
   * @author DSIW
   */
  public String getGenreToString ();


  /**
   * @return index to search and sort in a playlist
   * @author DSIW
   */
  public int getIndex ();


  /**
   * @return name of the interpret
   * @author DSIW
   */
  public String getInterpret ();


  /**
   * @return license of the track
   * @author DSIW
   */
  public License getLicense ();


  /**
   * @return absolute path from cover
   * @author DSIW
   */
  public String getPathToCover ();


  /**
   * @return absolute path from the music track
   * @author DSIW
   */
  public String getPathToMusic ();


  /**
   * @return rating of the track. Values are: 0-5
   * @author DSIW
   */
  public int getRating ();


  /**
   * @return title of the track
   * @author DSIW
   */
  public String getTitle ();


  /**
   * @return year of the track
   * @author DSIW
   */
  public int getYear ();


  @Override
  public int hashCode ();


  /**
   * Increments the count of playing.
   * 
   * @author DSIW
   */
  public void incrementCount ();


  /**
   * A track is writeable, if interpret, album, cd index, title and the encoding
   * is set.
   * 
   * @return true, if the track is writeable
   * @author DSIW
   */
  public boolean isWriteable ();


  /**
   * The path of the music file will be refresh. It needs the following data: cd
   * index, title, interpret, album and encoding. The duration will be
   * refreshed, too. It is calculated from the music file.
   * 
   * @author DSIW
   */
  public void refresh ();


  /**
   * Removes a track listener.
   * 
   * @param lst
   *          track listener to remove
   * @author DSIW
   */
  public void removeTrackListener (final TrackListener lst);


  /**
   * Sets the name of the album.
   * 
   * @param album
   *          name of the album
   * @author DSIW
   * @author DSIW
   * @throws Exception
   *           if the track or cover can't be moved or the music directory can't
   *           be cleaned up.
   */
  public void setAlbum (String album) throws Exception;


  /**
   * Sets the cd index
   * 
   * @param cdIndex
   * @author DSIW
   */
  public void setCdIndex (int cdIndex);


  /**
   * Sets the count of playing
   * 
   * @param count
   *          count of playing
   * @author DSIW
   */
  public void setCount (int count);


  /**
   * Sets the cover.
   * 
   * @param cover
   *          cover object
   * @author DSIW
   */
  public void setCover (Cover cover);


  /**
   * Sets the duration
   * 
   * @param dur
   *          duration of the track in miliseconds
   * @author DSIW
   */
  public void setDuration (int dur);


  /**
   * Sets the encoding
   * 
   * @param enc
   *          encoding of the track
   * @author DSIW
   */
  public void setEnc (Encoding enc);


  /**
   * Sets the genre
   * 
   * @param genre
   *          genre of the track
   * @author DSIW
   */
  public void setGenre (Genre genre);


  /**
   * Sets the index for searching and sorting in a playlist.
   * 
   * @param index
   *          index in the playlist
   * @author DSIW
   */
  public void setIndex (int index);


  /**
   * Sets the name of the interpret.
   * 
   * @param interpret
   *          name
   * @author DSIW
   * @author DSIW
   * @throws Exception
   *           if the track or cover can't be moved or the music directory can't
   *           be cleaned up.
   */
  public void setInterpret (String interpret) throws Exception;


  /**
   * Sets the license.
   * 
   * @param l
   *          the license of the track
   * 
   * @author DSIW
   */
  public void setLicense (License l);


  /**
   * Sets the absolute path to the cover image. The image will be load from the
   * filesystem.
   * 
   * @param absolutPath
   *          absolute path to the image
   * @author DSIW
   */
  public void setPathToCover (String absolutPath);


  /**
   * Sets the absolute path to the music file.
   * 
   * @param absolutPath
   *          path to the music file
   * @author DSIW
   */
  public void setPathToMusic (String absolutPath);


  /**
   * Sets the rating. Valid values are 1 to 5 or 0, if you don't want a rating.
   * 
   * @param rating
   *          rating of the track
   * @author DSIW
   */
  public void setRating (int rating);


  /**
   * Sets the title of the track.
   * 
   * @param title
   *          title of the track.
   * @author DSIW
   * @author DSIW
   * @throws Exception
   *           if the track or cover can't be moved or the music directory can't
   *           be cleaned up.
   */
  public void setTitle (String title) throws Exception;


  /**
   * Sets the year.
   * 
   * @param year
   *          year of the track
   * @author DSIW
   */
  public void setYear (int year);


  @Override
  public String toString ();
}
