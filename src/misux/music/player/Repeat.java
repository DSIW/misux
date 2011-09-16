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

/**
 * This enum implements fields for the repeat from the player.
 * 
 * @author DSIW
 * 
 */
public enum Repeat
{
  /**
   * All tracks will repeated. If the last track in the playlist is finished,
   * then the first track will play.
   */
  All,
  /**
   * The actually track will repeat each playing.
   */
  Track,
  /* Album, */
  /**
   * The playing will stop after the last track in the playlist is finished.
   */
  Nothing;

  /**
   * This is an other implementation of valueOf(). If the valueOf-method will
   * throw an IllegalArgumentException, the return type is <code>Nothing</code>.
   * 
   * @param repeat
   *          to parsed String.
   * @return Repeat object
   * @author DSIW
   */
  public static Repeat value (final String repeat)
  {
    try {
      return Repeat.valueOf(repeat);
    }
    catch (final IllegalArgumentException e) {
      return Nothing;
    }
  }
}
