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

import misux.pref.Preferences;

/**
 * This class represents the volume for the player.
 * 
 * @author DSIW
 * 
 */
public class Volume
{
  private int             volume;
  private int             volumeStep;
  /**
   * This is the minimum volume.
   */
  public final static int VOL_MAX = 100;
  /**
   * This is the maximum volume.
   */
  public final static int VOL_MIN = 0;
  private int             oldVolume;


  /**
   * Creates a new volume. The default volume and the volume steps will be
   * loaded from the preferences.
   * 
   * @throws Exception
   */
  public Volume() throws Exception
  {
    loadDefaultVolume();
    loadVolumeStep();
    oldVolume = 0;
  }


  /**
   * @return the actually volume
   */
  public int getVolume ()
  {
    return volume;
  }


  /**
   * @return the volumeStep
   */
  public double getVolumeStep ()
  {
    return volumeStep;
  }


  /**
   * The new volume will be the old volume + volume step.
   * 
   * @throws Exception
   *           if the old volume is the maximum volume.
   * @author DSIW
   */
  public void higherVolume () throws Exception
  {
    if (isMax()) {
      throw new Exception("Volume " + getVolume() + " is the maximum.");
    }
    setVolume(getVolume() + volumeStep);
  }


  private boolean isMax ()
  {
    if (Volume.VOL_MAX < getVolume() + getVolumeStep()) {
      try {
        setVolume(Volume.VOL_MAX);
      }
      catch (final Exception e) {}
    }
    return Volume.VOL_MAX == getVolume();
  }


  private boolean isMin ()
  {
    if (Volume.VOL_MIN > getVolume() - getVolumeStep()) {
      try {
        setVolume(Volume.VOL_MIN);
        return true;
      }
      catch (final Exception e) {}
    }
    return Volume.VOL_MIN == getVolume();
  }


  private void loadDefaultVolume () throws Exception
  {
    volume = Preferences.get().getOption("io.music.volume.default")
        .getIntegerValue();
  }


  private void loadVolumeStep () throws Exception
  {
    volumeStep = Preferences.get().getOption("io.music.volume.step")
        .getIntegerValue();
  }


  /**
   * The new volume will be the old volume - volume step.
   * 
   * @throws Exception
   *           if the old volume is the minimum volume.
   * @author DSIW
   */
  public void lowerVolume () throws Exception
  {
    if (isMin()) {
      throw new Exception("Volume " + getVolume() + " is the minimum.");
    }
    setVolume(getVolume() - volumeStep);
  }


  /**
   * The volume will be the minimum.
   * 
   * @author DSIW
   */
  public void mute ()
  {
    oldVolume = getVolume();
    try {
      setVolume(Volume.VOL_MIN);
    }
    catch (final Exception e) {}
  }


  /**
   * Sets the volume.
   * 
   * @param volume
   *          the volume to set
   * @throws Exception
   *           if the parameter is not between VOL_MIN and VOL_MAX
   */
  public void setVolume (final int volume) throws Exception
  {
    if (volume < Volume.VOL_MIN || volume > Volume.VOL_MAX) {
      throw new Exception("Volume must be in the range (" + Volume.VOL_MIN
          + " - " + Volume.VOL_MAX + ")");
    }
    this.volume = volume;
  }


  /**
   * @param volumeStep
   *          the volumeStep to set
   */
  public void setVolumeStep (final int volumeStep)
  {
    this.volumeStep = volumeStep;
  }


  /**
   * Toggles the mute. If the volume is muted, then it will be unmuted, and the
   * opposite.
   * 
   * @author DSIW
   */
  public void toggleMute ()
  {
    if (isMin()) {
      unmute();
    } else {
      mute();
    }
  }


  /**
   * The volume will be the volume before it was muted.
   * 
   * @author DSIW
   */
  public void unmute ()
  {
    try {
      setVolume(oldVolume);
    }
    catch (final Exception e) {}
  }
}
