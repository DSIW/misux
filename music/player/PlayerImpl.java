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
package misux.music.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import misux.music.Encoding;
import misux.music.pl.MainPlaylist;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistListener;
import misux.music.track.Track;

import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * This is a implementation of Player.
 * 
 * @author DSIW
 * @author DSIW
 */
public class PlayerImpl implements Player, BasicPlayerListener
{
  private Playlist                  playlist;
  private Playlist                  randomizedPlaylist;
  private BasicPlayer               player;
  private Repeat                    repeat;
  private boolean                   isRandomized;
  private Volume                    vol;
  private boolean                   isStopped;
  private AudioDevices              devices;
  private ArrayList<PlayerListener> listener;
  private long                      currentPos;
  private boolean                   manualSkip;
  private boolean                   manualSet;


  /**
   * Creates a new player, stops the player, sets the repeat to Nothing and the
   * random to false.
   * 
   * @throws Exception
   */
  public PlayerImpl() throws Exception
  {
    player = new BasicPlayer();
    player.addBasicPlayerListener(this);
    player.stop();
    vol = new Volume();
    repeat = Repeat.Nothing;
    isRandomized = false;
    isStopped = true;
    listener = new ArrayList<PlayerListener>();
  }


  /**
   * Creates a new player with a playlist
   * 
   * @param playlist
   *          playlist to play
   * @throws Exception
   */
  public PlayerImpl(final Playlist playlist) throws Exception
  {
    this();
    this.playlist = playlist;
    devices = new GetSystemAudioDevices().getDevices();
    if (devices.getInternal() == null) {
      devices.getExternal();
    } else {
      devices.getInternal();
    }
    // setAudioDevice(ad);
    // addPlaylistListener();
  }


  private void actualized ()
  {
    for (final PlayerListener mpl : listener) {
      mpl.songFinished();
    }
  }


  @Override
  public void addPlayerListener (final PlayerListener mpl)
  {
    listener.add(mpl);
  }


  private void addPlaylistListener ()
  {
    playlist.addPlaylistListener(new PlaylistListener()
    {
      @Override
      public void changed ()
      {
        try {
          MainPlaylist.write();
        }
        catch (final Exception e) {
          e.printStackTrace();
        }
      }
    });
  }


  private void convertActualTrack ()
  {
    if (!isRandomized) {
      convertActualTrack(playlist.getActualTrack(), randomizedPlaylist);
    } else {
      convertActualTrack(randomizedPlaylist.getActualTrack(), playlist);
    }
  }


  private void convertActualTrack (final Track toConvert, final Playlist newPl)
  {
    for (int t = 0; t < newPl.getLength(); t++) {
      final Track tmp = newPl.getTrack(t);
      if (toConvert.equals(tmp)) {
        newPl.setActualTrack(t);
        break;
      }
    }
  }


  @Override
  public Player copy () throws Exception
  {
    final PlayerImpl player = new PlayerImpl();
    player.currentPos = currentPos;
    player.devices = devices;
    player.isRandomized = isRandomized;
    player.isStopped = isStopped;
    player.listener = listener;
    player.player = this.player;
    player.playlist = playlist;
    player.randomizedPlaylist = randomizedPlaylist;
    player.repeat = repeat;
    player.vol = vol;
    return player;
  }


  @Override
  public Track getActualTrack () throws Exception
  {
    return getPlaylist().getActualTrack();
  }


  @Override
  public String getAudioDevice ()
  {
    return player.getMixerName();
  }


  /**
   * @return the current position of the track in bytes.
   * @author DSIW
   */
  @Override
  public long getCurrentPosition ()
  {
    return currentPos;
  }


  @Override
  public Playlist getPlaylist () throws Exception
  {
    if (isRandomized) {
      if (randomizedPlaylist == null || randomizedPlaylist.isEmpty()) {
        throw new Exception("No playlist exists to play.");
      }
      return randomizedPlaylist;
    } else {
      if (playlist == null /* || playlist.isEmpty() */) {
        throw new Exception("No playlist exists to play.");
      }
      return playlist;
    }
  }


  @Override
  public Repeat getRepeat ()
  {
    return repeat;
  }


  @Override
  public int getState ()
  {
    return player.getStatus();
  }


  // public void play (int trackPosition) throws Exception
  // {
  // play(trackPosition, actualTrack.getDuration());
  // }

  private int getTotalFrames () throws Exception
  {
    final File file = new File(getPlaylist().getActualTrack().getPathToMusic());
    if (!file.canRead()) {
      return 0;
    }
    AudioFileFormat fileFormat;
    try {
      fileFormat = AudioSystem.getAudioFileFormat(file);
      if (fileFormat instanceof TAudioFileFormat) {
        final Map<?, ?> properties = ((TAudioFileFormat) fileFormat)
            .properties();
        String key = null;
        if (playlist.getActualTrack().getEnc() == Encoding.MP3) {
          key = "mp3.length.frames";
        } else if (playlist.getActualTrack().getEnc() == Encoding.OGG) {
          key = "ogg.length.bytes";
        }
        return (Integer) properties.get(key);
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
    return 0;
  }


  @Override
  public int getVolume ()
  {
    return vol.getVolume();
  }


  @Override
  public void higherVolume () throws Exception
  {
    try {
      vol.higherVolume();
    }
    catch (final Exception e) {
      System.err.println(e.getMessage());
    }
    setVolume(vol.getVolume());
  }


  @Override
  public boolean isOpened ()
  {
    return player.getStatus() == BasicPlayer.OPENED;
  }


  // @Override
  // public void intro () throws Exception
  // {
  // int half = getActualTrack().getDuration() / 2;
  // System.out.println(half);
  // // plays 5 seconds
  // // D_EBUG
  // // play(10000);
  // // play(half, 10000);
  // }

  // @Override
  // public void introPlaylist () throws Exception
  // {
  // switch (repeat) {
  // case All:
  // // T_ODO terminate
  // while (true) {
  // intro();
  // getPlaylist().next();
  // }
  // case Track:
  // // T_ODO terminate
  // while (true) {
  // intro();
  // }
  // case Nothing:
  // default:
  // for (int i = 0; i < getPlaylist().getLength(); i++) {
  // intro();
  // getPlaylist().next();
  // }
  // break;
  // }
  // }

  @Override
  public boolean isPaused ()
  {
    return player.getStatus() == BasicPlayer.PAUSED;
  }


  @Override
  public boolean isPlaying ()
  {
    return player.getStatus() == BasicPlayer.PLAYING;
  }


  @Override
  public boolean isRandomized ()
  {
    return isRandomized;
  }


  @Override
  public boolean isSeeking ()
  {
    return player.getStatus() == BasicPlayer.SEEKING;
  }


  @Override
  public boolean isStopped ()
  {
    return player.getStatus() == BasicPlayer.STOPPED;
  }


  @Override
  public void lowerVolume () throws Exception
  {
    try {
      vol.lowerVolume();
    }
    catch (final Exception e) {
      System.err.println(e.getMessage());
    }
    setVolume(vol.getVolume());
  }


  // public void loadMixer ()
  // {
  // List<String> mixers = player.getMixers();
  // if (mixers != null) {
  // Iterator it = mixers.iterator();
  // // D_EBUG
  // // String mixer = config.getAudioDevice();
  // String mixer = null;
  // boolean mixerFound = false;
  // if ((mixer != null) && (mixer.length() > 0)) {
  // // Check if mixer is valid.
  // while (it.hasNext()) {
  // if (((String) it.next()).equals(mixer)) {
  // player.setMixerName(mixer);
  // mixerFound = true;
  // break;
  // }
  // }
  // }
  // if (mixerFound == false) {
  // // Use first mixer available.
  // it = mixers.iterator();
  // if (it.hasNext()) {
  // mixer = (String) it.next();
  // // System.out.println(mixer);
  // player.setMixerName(mixer);
  // }
  // }
  // }
  // }

  // public void loopVolume () throws Exception
  // {
  // vol.loop();
  // setVolume(vol.getVolume());
  // }

  @Override
  public void muteVolume () throws Exception
  {
    try {
      vol.mute();
    }
    catch (final Exception e) {
      System.err.println(e.getMessage());
    }
    setVolume(vol.getVolume());
  }


  @Override
  public void opened (final Object arg0,
      @SuppressWarnings("rawtypes") final Map arg1)
  {
    openedSong();
  }


  private void openedSong ()
  {
    for (final PlayerListener mpl : listener) {
      mpl.opened();
    }
  }


  @Override
  public void pause () throws BasicPlayerException
  {
    player.pause();
    isStopped = false;
  }


  @Override
  public void play () throws Exception
  {
    actualized();
    System.out.println("Playing Track \"" + getActualTrack() + "\"");
    final File musicFile = new File(getActualTrack().getPathToMusic());
    // System.out.println("TBytes:" + getTotalFrames());
    // System.out.println("Durat: "+actualTrack.getDuration());
    // System.out.println("Byte:  " + timeToFrame(startTimeInMiliSec));
    openedSong();
    try {
      player.open(musicFile);
      player.play();
    }
    catch (final NullPointerException e) {
      return;
    }
    setVolume(vol.getVolume());
  }


  private void playlistChanged ()
  {
    for (final PlayerListener mpl : listener) {
      mpl.playlistChanged();
    }
  }


  @Override
  public void playNext () throws Exception
  {
    playlist.next();
    manualSkip = true;
    if (isPlaying()) {
      playPlaylist();
    }
  }


  // Miliseconds
  // private void play (int startTime, int endtime)
  // throws UnsupportedAudioFileException, IOException, BasicPlayerException,
  // JavaLayerException
  // {
  // actualTrack = playlist.getActualTrack();
  // File soundFile = new File(actualTrack.getPathToMusic());
  // AudioInputStream originalAudioInputStream = AudioSystem
  // .getAudioInputStream(soundFile);
  // AudioFormat audioFormat = originalAudioInputStream.getFormat();
  //
  // float startInBytes = (startTime / 1000 * audioFormat.getSampleRate() *
  // audioFormat
  // .getFrameSize());
  // float lengthInFrames = ((endtime - startTime) / 1000 * audioFormat
  // .getSampleRate());
  //
  // originalAudioInputStream.skip((long) startInBytes);
  // AudioInputStream partAudioInputStream = new AudioInputStream(
  // originalAudioInputStream, originalAudioInputStream.getFormat(),
  // (long) lengthInFrames);
  //
  // player.open(partAudioInputStream);
  // player.play();
  // setVolume(defaultVolume);
  // }
  @Override
  public void playPlaylist () throws Exception
  {
    isStopped = false;
    playPlaylist(false, true);
  }


  @Override
  public void playPlaylist (final boolean manuelSet) throws Exception
  {
    isStopped = false;
    playPlaylist(manuelSet, true);
  }


  private void playPlaylist (final boolean manuelSet, final boolean firstPlay)
      throws Exception
  {
    manualSet = manuelSet;
    if (!firstPlay) {
      playlist.getActualTrack().incrementCount();
      playlist.firePlaylistChanged();
      switch (repeat) {
        case All:
          getPlaylist().next();
          break;
        case Track:
          break;
        case Nothing:
          stop();
          return;
        default:
          if (getActualTrack().getIndex() >= getPlaylist().getLength()) {
            stop();
            return;
          }
          getPlaylist().next();
          break;
      }
    }
    try {
      play();
    }
    catch (final InterruptedException e) {
      return;
    }
    manualSkip = false;
    manualSet = false;
  }


  @Override
  public void playPrev () throws Exception
  {
    playlist.prev();
    manualSkip = true;
    if (isPlaying()) {
      playPlaylist();
    }
  }


  @Override
  public void progress (final int arg0, final long arg1, final byte[] arg2,
      @SuppressWarnings("rawtypes") final Map arg3)
  {
    currentPos = arg1;
  }


  @Override
  public void removePlayerListener (final PlayerListener mpl)
  {
    listener.remove(mpl);
  }


  @Override
  public void resume () throws JavaLayerException, FileNotFoundException,
      BasicPlayerException
  {
    if (player != null && isPaused()) {
      player.resume();
      actualized();
    }
  }


  @Override
  public void setAudioDevice (final AudioDevice device)
  {
    player.setMixerName(device.getHardwareName());
  }


  @Override
  public void setController (final BasicController arg0)
  {
  }


  @Override
  public void setPlaylist (final Playlist playlist)
  {
    if (isRandomized) {
      randomizedPlaylist = playlist.getRandomized();
    } else {
      this.playlist = playlist;
    }
  }


  @Override
  public void setRandom (final boolean random)
  {
    isRandomized = random;
    if (isRandomized && playlist != null && randomizedPlaylist == null) {
      randomizedPlaylist = playlist.getRandomized();
    }
    convertActualTrack();
    playlistChanged();
  }


  @Override
  public void setRepeat (final Repeat rep)
  {
    repeat = rep;
  }


  @Override
  public void setVolume (final int volume) throws Exception
  {
    vol.setVolume(volume);
    player.setGain(vol.getVolume() / 100.0);
  }


  @Override
  public void stateUpdated (final BasicPlayerEvent arg0)
  {
    actualized();
    if (!isStopped && isStopped() && !manualSkip && !manualSet) {
      try {
        playPlaylist(true, false);
      }
      catch (final Exception e) {
        e.printStackTrace();
      }
    } else if (isStopped) {
      try {
        stop();
      }
      catch (final BasicPlayerException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public void stop () throws BasicPlayerException
  {
    isStopped = true;
    if (player != null && player.getStatus() != BasicPlayer.STOPPED) {
      player.stop();
      actualized();
    }
  }


  @SuppressWarnings("unused")
  private long timeToFrame (final int time) throws Exception
  {
    double factor = 0;
    try {
      factor = ((Integer) getTotalFrames()).intValue()
          / getActualTrack().getDuration();
    }
    catch (final ArithmeticException e) {}
    long ret = Math.round((time * factor));
    ret = (int) ret / 4;
    return ret;
  }


  @Override
  public void toggleMuteVolume () throws Exception
  {
    vol.toggleMute();
    setVolume(vol.getVolume());
  }


  @Override
  public void unMuteVolume () throws Exception
  {
    try {
      vol.unmute();
    }
    catch (final Exception e) {
      System.err.println(e.getMessage());
    }
    setVolume(vol.getVolume());
  }
}