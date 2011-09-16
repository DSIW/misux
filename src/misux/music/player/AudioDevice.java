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

/**
 * This class implements methods to save an audio device.
 * 
 * @author DSIW
 * 
 */
public class AudioDevice
{
  private String          hardwareName;
  private AudioDeviceType type;


  /**
   * Creates a new audio device with a type and the hardware name.
   * 
   * @param type
   *          type of the audio device
   * @param hardwareName
   *          hardware name of the device
   */
  public AudioDevice(final AudioDeviceType type, final String hardwareName)
  {
    this.type = type;
    this.hardwareName = hardwareName;
  }


  /**
   * @return the hardwareName
   */
  public String getHardwareName ()
  {
    return hardwareName;
  }


  /**
   * @return the type
   */
  public AudioDeviceType getType ()
  {
    return type;
  }


  /**
   * @param hardwareName
   *          the hardwareName to set
   */
  public void setHardwareName (final String hardwareName)
  {
    this.hardwareName = hardwareName;
  }


  /**
   * @param type
   *          the type to set
   */
  public void setType (final AudioDeviceType type)
  {
    this.type = type;
  }


  @Override
  public String toString ()
  {
    return type.name().toLowerCase() + ": " + hardwareName;
  }
}
