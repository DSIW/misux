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
package misux.music.player;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import misux.music.pl.Playlist;
import misux.music.track.Track;

/**
 * This interface implements methods for a player.
 * 
 * @author DSIW
 */
/**
 * @author DSIW
 * @author DSIW
 * 
 */
public interface Player
{
  /**
   * Adds a playerlistener to the player.
   * 
   * @param mpl
   *          playerlistener
   * @author DSIW
   */
  public void addPlayerListener (PlayerListener mpl);


  /**
   * @return a new instance of the player, with the data from this player.
   * @throws Exception
   * @author DSIW
   */
  public Player copy () throws Exception;


  /**
   * @return actually track
   * @author DSIW
   * @throws Exception
   */
  public Track getActualTrack () throws Exception;


  /**
   * @return the hardware name of the specified audio device.
   * @author DSIW
   */
  public String getAudioDevice ();


  /**
   * @return current position of the playing track
   * @author DSIW
   */
  public long getCurrentPosition ();


  /**
   * @return playlist
   * @author DSIW
   * @throws Exception
   */
  public Playlist getPlaylist () throws Exception;


  /**
   * @return the repeat
   * @author DSIW
   */
  public Repeat getRepeat ();


  /**
   * @return state of the player
   * @author DSIW
   */
  public int getState ();


  /**
   * @return current volume
   * @author DSIW
   */
  public int getVolume ();


  /**
   * Sets the new volume to the player.
   * 
   * @see Volume
   * @throws Exception
   * @author DSIW
   */
  public void higherVolume () throws Exception;


  /**
   * @return true, if the state of player is opened.
   * @author DSIW
   */
  public boolean isOpened ();


  /**
   * @return true, if the state of player is plausing.
   * @author DSIW
   */
  public boolean isPaused ();


  /**
   * @return true, if the state of player is playing.
   * @author DSIW
   */
  public boolean isPlaying ();


  /**
   * @return true, if the playlist is randomized.
   * @author DSIW
   */
  public boolean isRandomized ();


  /**
   * @return true, if the state of player is seeking.
   * @author DSIW
   */
  public boolean isSeeking ();


  /**
   * @return true, if the state of player is stopped.
   * @author DSIW
   */
  public boolean isStopped ();


  // public void intro () throws JavaLayerException, FileNotFoundException,
  // UnsupportedAudioFileException, IOException, BasicPlayerException,
  // Exception;

  // public void introPlaylist () throws JavaLayerException,
  // FileNotFoundException, BasicPlayerException,
  // UnsupportedAudioFileException, IOException, Exception;

  /**
   * Sets the new volume to the player.
   * 
   * @see Volume
   * @throws Exception
   * @author DSIW
   */
  public void lowerVolume () throws Exception;


  /**
   * Sets the new volume to the player.
   * 
   * @see Volume
   * @throws BasicPlayerException
   * @throws Exception
   * @author DSIW
   */
  public void muteVolume () throws BasicPlayerException, Exception;


  /**
   * Pauses the playing.
   * 
   * @author DSIW
   * @throws BasicPlayerException
   */
  public void pause () throws BasicPlayerException;


  /**
   * Plays the actually track.
   * 
   * @author DSIW
   * @throws JavaLayerException
   * @throws FileNotFoundException
   * @throws BasicPlayerException
   * @throws IOException
   * @throws UnsupportedAudioFileException
   * @throws Exception
   */
  public void play () throws JavaLayerException, FileNotFoundException,
      UnsupportedAudioFileException, IOException, BasicPlayerException,
      Exception;


  /**
   * Plays the playlist. The start is the next track.
   * 
   * @throws Exception
   * @author DSIW
   */
  public void playNext () throws Exception;


  /**
   * Plays the playlist. The start is the actually track.
   * 
   * @throws JavaLayerException
   * @throws FileNotFoundException
   * @throws BasicPlayerException
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws Exception
   * @author DSIW
   */
  public void playPlaylist () throws JavaLayerException, FileNotFoundException,
      BasicPlayerException, UnsupportedAudioFileException, IOException,
      Exception;


  /**
   * Plays the playlist with a manual flag. This is for the tracktable mouse
   * listener.
   * 
   * @param manuelSet
   * @throws Exception
   * @author DSIW
   */
  public void playPlaylist (final boolean manuelSet) throws Exception;


  /**
   * Plays the playlist. The start is the previous track.
   * 
   * @throws Exception
   * @author DSIW
   */
  public void playPrev () throws Exception;


  /**
   * Removes the specified playerlistener.
   * 
   * @param mpl
   *          playerlistener to remove
   * @author DSIW
   */
  public void removePlayerListener (PlayerListener mpl);


  /**
   * Resumes the paused player. If the player isn't paused, the method has no
   * effect.
   * 
   * @author DSIW
   * @throws JavaLayerException
   * @throws FileNotFoundException
   * @throws BasicPlayerException
   */
  public void resume () throws JavaLayerException, FileNotFoundException,
      BasicPlayerException;


  /**
   * Sets the audio device.
   * 
   * @param device
   *          audio device
   * @author DSIW
   */
  public void setAudioDevice (AudioDevice device);


  /**
   * Sets the playlist to play.
   * 
   * @param playlist
   *          playlist
   * @author DSIW
   */
  public void setPlaylist (Playlist playlist);


  /**
   * Sets the random option.
   * 
   * @param random
   *          random
   * @author DSIW
   */
  public void setRandom (boolean random);


  /**
   * Sets the repeat option.
   * 
   * @param rep
   *          repeat
   * @author DSIW
   */
  public void setRepeat (Repeat rep);


  /**
   * Sets the volume.
   * 
   * @see Volume
   * @param volume
   * @throws BasicPlayerException
   * @throws Exception
   * @author DSIW
   */
  public void setVolume (int volume) throws BasicPlayerException, Exception;


  /**
   * Stops the player. The state will be STOPPED.
   * 
   * @author DSIW
   * @throws BasicPlayerException
   */
  public void stop () throws BasicPlayerException;


  /**
   * Toggles the mute.
   * 
   * @see Volume
   * @throws BasicPlayerException
   * @throws Exception
   * @author DSIW
   */
  public void toggleMuteVolume () throws BasicPlayerException, Exception;


  /**
   * Unmute the volume.
   * 
   * @see Volume
   * @throws BasicPlayerException
   * @throws Exception
   * @author DSIW
   */
  public void unMuteVolume () throws BasicPlayerException, Exception;
}
