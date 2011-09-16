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
package misux.music;

/**
 * This enum lists encodings for the player and tracks.
 * 
 * @author DSIW
 * 
 */
public enum Encoding
{
  /**
   * Encoding OGG
   */
  OGG,
  /**
   * Encoding MP3
   */
  MP3,
  /**
   * No encoding
   */
  NO;

  /**
   * This is an other implementation of valueOf(). If the valueOf-method will
   * throw an IllegalArgumentException, the return type is <code>No</code>.
   * 
   * @param encoding
   *          to parsed String
   * @return Encoding object
   * @author DSIW
   */
  public static Encoding value (final String encoding)
  {
    try {
      return Encoding.valueOf(encoding);
    }
    catch (final IllegalArgumentException e) {
      return Encoding.NO;
    }
  }
}
