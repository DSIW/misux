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
package misux.div.exceptions;

/**
 * This exception will thrown, if no lyric was found.
 * 
 * @author DSIW
 * 
 */
public class NoLyricFoundException extends RuntimeException
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates an Exception with the message 'The can't be found.'
   */
  public NoLyricFoundException()
  {
    super("The lyric can't be found");
  }
}
