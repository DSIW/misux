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
package misux.div;

import java.text.DecimalFormat;

/**
 * This class implements methods to convert miliseconds in a human readable
 * String.
 * 
 * @author DSIW
 * 
 */
public class Time
{
  private static int[] getDurationArray (final int timeInMili)
  {
    final int sec = timeInMili / 1000 % 60;
    final int min = timeInMili / 1000 / 60;
    return new int[] { min, sec };
  }


  /**
   * @param timeInMili
   * @return the human readable String (min:sec)
   * @author DSIW
   */
  public static String MiliSecondsToString (final int timeInMili)
  {
    final int[] time = Time.getDurationArray(timeInMili);
    if (time == null) {
      return "00:00";
    }
    final DecimalFormat df = new DecimalFormat("00");
    return df.format(time[0]) + ":" + df.format(time[1]);
  }
}
