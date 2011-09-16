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
package misux.div.exceptions;

/**
 * This TypeIsNotEqualsException will thrown, if a special key was found.
 * 
 * @author DSIW
 * 
 */
public class SpecialKeyException extends Exception
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates the Exception with a message.
   */
  public SpecialKeyException()
  {
    super(
        "The tags from music contains special keys (for example \uFFFD). This won't be supported.");
  }
}