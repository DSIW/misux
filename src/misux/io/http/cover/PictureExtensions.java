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
package misux.io.http.cover;

/**
 * All allowed picture extension are listed here.
 * 
 * @author DSIW
 * 
 */
public enum PictureExtensions
{
  /**
   * image type JPEG
   */
  JPEG,
  /**
   * image type PNG
   */
  PNG,
  /**
   * image type JPEG
   */
  JPG,
  /**
   * image type GIF
   */
  GIF,
  /**
   * image type BMP
   */
  BMP,
  /**
   * If no type of the allowed types matched, then use this pseudo type.
   */
  OTHER;

  /**
   * This is an other implementation of valueOf(). If the valueOf-method will
   * throw an IllegalArgumentException, the return type is <code>Other</code>.
   * 
   * @param picExtension
   *          to parsed String
   * @return PictureExtension object
   * @author DSIW
   */
  public static PictureExtensions value (final String picExtension)
  {
    try {
      return PictureExtensions.valueOf(picExtension.toUpperCase());
    }
    catch (final IllegalArgumentException e) {
      return PictureExtensions.OTHER;
    }
  }
}
