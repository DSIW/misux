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
package misux.music.license;

import java.io.IOException;

import misux.div.Images;

/**
 * This class represents the license Copyright. There are different
 * informations.
 * 
 * @author DSIW
 * 
 */
public class Copyright extends License
{

  /**
   * Creates the license with default data.
   */
  public Copyright()
  {
    name = "Copyright";
    link = "http://www.copyright.com/";
    try {
      logo = new Images("copyright.png").getImage();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    date = "";
    version = "";
  }

}
