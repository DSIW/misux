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
package misux.music.track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import misux.div.Time;
import misux.div.exceptions.ExecutionException;
import misux.io.Convert;
import misux.io.Run;
import misux.io.http.cover.Cover;
import misux.io.http.cover.PictureExtensions;
import misux.music.Encoding;
import misux.music.Genre;
import misux.music.license.Copyright;
import misux.music.license.License;
import misux.pref.Preferences;

import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * This is an implementation of Track.
 * 
 * @author DSIW
 * @author DSIW
 */
public class TrackImpl implements Track
{
  private Genre                          genre;
  private String                         album;
  private int                            count;
  private String                         interpret;
  private int                            duration;
  private String                         pathToMusic;
  private int                            rating;
  private String                         title;
  private int                            year;
  private int                            index;
  private int                            cdIndex;
  private License                        license;
  private Encoding                       enc;
  private Cover                          cover;
  private final ArrayList<TrackListener> listener;


  /**
   * This will create a track with the following data: index = 0; cdIndex = 0;
   * title = ""; album = ""; interpret = ""; duration = 0; year = 0; genre =
   * Unknown; rating = 0; pathToMusic = ""; pathToCover = ""; encoding = No;
   * license = Copyright; count = 0. The path to the music will be refreshed.
   */
  public TrackImpl()
  {
    this(0, 0, "", "", "", 0, 0, Genre.Unknown, 0, "", "", Encoding.NO,
        new Copyright(), 0);
    refresh();
  }


  /**
   * Creates a new track with the following parameters. The cover will loaded
   * from the filesystem.
   * 
   * @param index
   *          index in playlist
   * @param cdIndex
   *          index of the CD
   * @param title
   *          title of the track
   * @param album
   *          name of the album
   * @param interpret
   *          name of the interpret
   * @param duration
   *          count of the duration in milliseconds
   * @param year
   *          year
   * @param genre
   *          genre of the track
   * @param rating
   *          rating is a number ([worst] 0 - 5 [best])
   * @param pathToMusic
   *          the absolute path to the music
   * @param pathToCover
   *          the absolute path to the cover
   * @param enc
   *          encoding (OGG or MP3)
   * @param license
   *          license (Copyright, Copyleft, CreativeCommons, ...)
   * @param count
   *          count of playing
   */
  public TrackImpl(final int index, final int cdIndex, final String title,
      final String album, final String interpret, final int duration,
      final int year, final Genre genre, final int rating,
      final String pathToMusic, final String pathToCover, final Encoding enc,
      final License license, final int count)
  {
    listener = new ArrayList<TrackListener>();
    this.genre = genre;
    this.album = album;
    this.count = count;
    this.interpret = interpret;
    this.duration = duration;
    this.pathToMusic = pathToMusic;
    setRating(rating);
    this.title = title;
    this.year = year;
    this.index = index;
    this.license = license;
    this.enc = enc;
    this.cdIndex = cdIndex;
    loadCover(pathToCover);
  }


  /**
   * Creates a new track. The path to the music will be refreshed.
   * 
   * @param index
   *          index in playlist
   * @param title
   *          title of the track
   * @param album
   *          name of the album
   * @param interpret
   *          name of the interpret
   * @param enc
   *          encoding (OGG or MP3)
   */
  public TrackImpl(final int index, final String title, final String album,
      final String interpret, final Encoding enc)
  {
    this();
    this.index = index;
    cdIndex = index;
    this.title = title;
    this.album = album;
    this.interpret = interpret;
    this.enc = enc;
    refresh();
  }


  @Override
  public void addTrackListener (final TrackListener lst)
  {
    listener.add(lst);
  }


  private void changed ()
  {
    for (final TrackListener tl : listener) {
      tl.changed();
    }
  }


  private void cleanUpMusicDir () throws Exception
  {
    final String musicDir = Preferences.get().getOption("io.user.musicdir")
        .getStringValue();

    if (coverDirIsEmpty()) {
      final File coverFile = new File(cover.getAbsolutePath());
      coverFile.delete();
    }

    final Run deleteEmpty = new Run("find", ".", "-type", "d", "-empty",
        "-delete");
    deleteEmpty.setDir(musicDir);
    deleteEmpty.exec();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Track copy ()
  {
    final TrackImpl track = new TrackImpl();
    track.setGenre(getGenre());
    track.album = getAlbum();
    track.setCount(getCount());
    track.interpret = getInterpret();
    track.setPathToMusic(getPathToMusic());
    track.setPathToCover(getPathToCover());
    track.setRating(getRating());
    track.title = getTitle();
    track.setYear(getYear());
    track.setIndex(getIndex());
    track.setLicense(getLicense());
    track.setDuration(getDuration());
    track.setCdIndex(getCdIndex());
    track.setEnc(getEnc());
    return track;
  }


  /* Checks directory, if only one cover exists and non music file */
  private boolean coverDirIsEmpty ()
  {
    for (final Encoding enc : Encoding.values()) {
      final Run checkDir = new Run("find", ".", "-type", "f", "-iname", "*."
          + enc.toString());
      checkDir.setDir(cover.getPath());
      try {
        checkDir.exec();
      }
      catch (final InterruptedException e) {}
      catch (final IOException e) {}
      catch (final ExecutionException e) {}
      if (checkDir.getOutput().length() != 0) {
        return false;
      }
    }
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
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
    if (!(obj instanceof TrackImpl)) {
      return false;
    }
    final TrackImpl other = (TrackImpl) obj;
    // album
    if (album == null) {
      if (other.album != null) {
        return false;
      }
    } else if (!album.equals(other.album)) {
      return false;
    }
    // cdIndex
    if (cdIndex != other.cdIndex) {
      return false;
    }
    // count
    // if (count != other.count) {
    // return false;
    // }
    // duration
    // if (duration != other.duration) {
    // return false;
    // }
    // enc
    if (enc != other.enc) {
      return false;
    }
    // genre
    if (genre != other.genre) {
      return false;
    }
    // // index
    // if (index != other.index) {
    // return false;
    // }
    // interpret
    if (interpret == null) {
      if (other.interpret != null) {
        return false;
      }
    } else if (!interpret.equals(other.interpret)) {
      return false;
    }
    // license
    if (license == null) {
      if (other.license != null) {
        return false;
      }
    } else if (!license.equals(other.license)) {
      return false;
    }
    // pathToCover
    // if (getPathToCover() == null) {
    // if (other.getPathToCover() != null) {
    // return false;
    // }
    // } else if (!getPathToCover().equals(other.getPathToCover())) {
    // return false;
    // }
    // pathToMusic
    if (pathToMusic == null) {
      if (other.pathToMusic != null) {
        return false;
      }
    } else if (!pathToMusic.equals(other.pathToMusic)) {
      return false;
    }
    // rating
    // if (rating != other.rating) {
    // return false;
    // }
    // title
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    // year
    if (year != other.year) {
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
  public void fireTrackChanged ()
  {
    changed();
  }


  @Override
  public String getAlbum ()
  {
    return album;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getCdIndex ()
  {
    return cdIndex;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getCount ()
  {
    return count;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Cover getCover ()
  {
    return cover;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getDuration ()
  {
    return duration;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getDurationToString ()
  {
    return Time.MiliSecondsToString(getDuration());
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Encoding getEnc ()
  {
    return enc;
  }


  private String getFileName ()
  {
    final String index = (getCdIndex() < 10 ? "0" : "") + getCdIndex();
    return index + "-" + Convert.toIO(getInterpret()) + "-"
        + Convert.toIO(getAlbum()) + "-" + Convert.toIO(getTitle()) + "."
        + getEnc().toString().toLowerCase();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Genre getGenre ()
  {
    return genre;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getGenreToString ()
  {
    return genre.toString();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getIndex ()
  {
    return index;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getInterpret ()
  {
    return interpret;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public License getLicense ()
  {
    return license;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getPathToCover ()
  {
    return cover.getAbsolutePath();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getPathToMusic ()
  {
    return pathToMusic;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getRating ()
  {
    return rating;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getTitle ()
  {
    return title;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getYear ()
  {
    return year;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (album == null ? 0 : album.hashCode());
    result = prime * result + cdIndex;
    result = prime * result + count;
    result = prime * result + duration;
    result = prime * result + (enc == null ? 0 : enc.hashCode());
    result = prime * result + (genre == null ? 0 : genre.hashCode());
    result = prime * result + (interpret == null ? 0 : interpret.hashCode());
    result = prime * result + (license == null ? 0 : license.hashCode());
    result = prime * result
        + (getPathToCover() == null ? 0 : getPathToCover().hashCode());
    result = prime * result
        + (pathToMusic == null ? 0 : pathToMusic.hashCode());
    result = prime * result + rating;
    result = prime * result + (title == null ? 0 : title.hashCode());
    result = prime * result + year;
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void incrementCount ()
  {
    setCount(getCount() + 1);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public boolean isWriteable ()
  {
    if (getInterpret() == null || getInterpret().isEmpty()
        || getAlbum() == null || getAlbum().isEmpty() || getCdIndex() == 0
        || getTitle() == null || getTitle().isEmpty() || getEnc() == null
        || getEnc().toString().isEmpty() || getEnc() == Encoding.NO) {
      return false;
    }
    return true;
  }


  private void loadCover (final String pathToCover)
  {
    final String ext = pathToCover.substring(pathToCover.lastIndexOf(".") + 1);
    cover = new Cover(new ImageIcon(pathToCover).getImage(), interpret, album,
        PictureExtensions.value(ext), null);
    cover.setPath(new File(pathToCover).getParent());
  }


  private void loadDuration ()
  {
    final File file = new File(pathToMusic);
    if (!file.canRead()) {
      duration = 0;
      return;
    }
    AudioFileFormat fileFormat;
    try {
      fileFormat = AudioSystem.getAudioFileFormat(file);
      if (fileFormat instanceof TAudioFileFormat) {
        final Map<?, ?> properties = ((TAudioFileFormat) fileFormat)
            .properties();
        final String key = "duration";
        final Long microseconds = (Long) properties.get(key);
        final int mili = (int) (microseconds / 1000);
        duration = mili;
      } else {
        throw new UnsupportedAudioFileException();
      }
    }
    catch (final UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
  }


  private void moveTrack (final String newInterpret, final String newAlbum,
      final String newTitle) throws Exception
  {
    final TrackImpl newTrack = (TrackImpl) copy();
    newTrack.interpret = newInterpret;
    newTrack.album = newAlbum;
    newTrack.title = newTitle;
    newTrack.refresh();
    if (!newTrack.isWriteable()) {
      return;
    }

    final String oldPath = getPathToMusic();
    final String newPath = newTrack.getPathToMusic();
    if (oldPath.equals(newPath)) {
      return;
    }

    final Run mkdir = new Run("mkdir", "-p", new File(newPath).getParent());
    mkdir.exec();
    final Run mv = new Run("mv", oldPath, newPath);
    mv.exec();

    if (this.getCover().exists()) {
      final Run cpCover = new Run("cp", getPathToCover(),
          newTrack.getPathToCover());
      cpCover.exec();
    }
    cleanUpMusicDir();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void refresh ()
  {
    if (!isWriteable()) {
      pathToMusic = "PATH-IS-NOT-WRITEABLE";
    } else {
      String musicPath = null;
      try {
        musicPath = Preferences.get().getOption("io.user.musicdir")
            .getStringValue();
      }
      catch (final Exception e) {
        e.printStackTrace();
      }
      final String path = musicPath + File.separator
          + Convert.toIO(getInterpret()) + File.separator
          + Convert.toIO(getAlbum()) + File.separator + getFileName();
      pathToMusic = path;
      cover.setAlbum(getAlbum());
      cover.setInterpret(getInterpret());
      cover.setPath(new File(getPathToMusic()).getParent());
      loadDuration();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void removeTrackListener (final TrackListener lst)
  {
    listener.remove(lst);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setAlbum (final String album) throws Exception
  {
    if (!(this.album == null || this.album.equals("") || album
        .equals(this.album))) {
      moveTrack(getInterpret(), album, getTitle());
    }
    this.album = album;
    refresh();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setCdIndex (final int cdIndex)
  {
    this.cdIndex = cdIndex;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setCount (final int count)
  {
    this.count = count;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setCover (final Cover cover)
  {
    this.cover = cover;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setDuration (final int timeInMili)
  {
    duration = timeInMili;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setEnc (final Encoding enc)
  {
    this.enc = enc;
    refresh();
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setGenre (final Genre genre)
  {
    this.genre = genre;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setIndex (final int index)
  {
    this.index = index;
    refresh();
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setInterpret (final String interpret) throws Exception
  {
    if (!(this.interpret == null || this.interpret.equals("") || this.interpret
        .equals(interpret))) {
      moveTrack(interpret, getAlbum(), getTitle());
    }
    this.interpret = interpret;
    refresh();
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setLicense (final License l)
  {
    license = l;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setPathToCover (final String absolutPath)
  {
    loadCover(absolutPath);
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setPathToMusic (final String absolutPath)
  {
    pathToMusic = absolutPath;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setRating (final int rating)
  {
    if (rating >= 5) {
      this.rating = 5;
    } else {
      this.rating = rating;
    }
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setTitle (final String title) throws Exception
  {
    if (!(this.title == null || this.title.equals("") || this.title
        .equals(title))) {
      moveTrack(getInterpret(), getAlbum(), title);
    }
    this.title = title;
    refresh();
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setYear (final int year)
  {
    this.year = year;
    changed();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String toString ()
  {
    return getIndex() + ", " + getInterpret() + ", " + getAlbum() + " ("
        + getCdIndex() + ")" + ", " + getTitle() + ", " + getGenreToString()
        + ", " + getYear() + ", " + getDurationToString() + ", " + enc + " | "
        + getPathToMusic() + " | " + getPathToCover();
  }
}
