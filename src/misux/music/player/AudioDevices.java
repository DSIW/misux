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

import java.util.ArrayList;

/**
 * This class implements a Map&lt;Audiodevice, String&gt; and methods to get the
 * hardware mixer name of the audio devices.
 * 
 * @author DSIW
 * 
 */
@SuppressWarnings("serial")
public class AudioDevices extends ArrayList<AudioDevice>
{
  /**
   * @return the internal audio device. If no found, the result will be
   *         <code>null</code>.
   * @author DSIW
   */
  public AudioDevice getExternal ()
  {
    if (get(1).getType() == AudioDeviceType.INTERNAL) {
      return get(1);
    }
    return null;
  }


  /**
   * @return the external audio device. If no found, the result will be
   *         <code>null</code>.
   * @author DSIW
   */
  public AudioDevice getInternal ()
  {
    if (get(0).getType() == AudioDeviceType.INTERNAL) {
      return get(0);
    }
    return null;
  }
}
