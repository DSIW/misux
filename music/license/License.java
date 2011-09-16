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
package misux.music.license;

import java.awt.Image;

/**
 * This interface implements methods for a license for the music. There
 * currently are different licenses.
 * 
 * @author DSIW
 * 
 */
public abstract class License
{
  /**
   * This method will parse the name of the license to the object, which will
   * habe the same name. If no license is equals, the result is null.
   * 
   * @param name
   *          name of the license
   * @return license object
   * @author DSIW
   */
  public static License parseName (final String name)
  {
    License newLicense = null;

    License tmp_license = new Copyleft();
    if (tmp_license.getName().equalsIgnoreCase(name)) {
      newLicense = tmp_license;
    }
    tmp_license = new Copyright();
    if (tmp_license.getName().equalsIgnoreCase(name)) {
      newLicense = tmp_license;
    }
    tmp_license = new CreativeCommons();
    if (tmp_license.getName().equalsIgnoreCase(name)) {
      newLicense = tmp_license;
    }
    tmp_license = new FreeMusic();
    if (tmp_license.getName().equalsIgnoreCase(name)) {
      newLicense = tmp_license;
    }
    return newLicense;
  }

  protected String name;
  protected String link;
  protected Image  logo;
  protected String date;

  protected String version;


  @Override
  public boolean equals (final Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof License)) {
      return false;
    }
    final License other = (License) obj;
    if (date == null) {
      if (other.date != null) {
        return false;
      }
    } else if (!date.equals(other.date)) {
      return false;
    }
    if (link == null) {
      if (other.link != null) {
        return false;
      }
    } else if (!link.equals(other.link)) {
      return false;
    }
    // if (logo == null) {
    // if (other.logo != null) {
    // return false;
    // }
    // } else if (!logo.equals(other.logo)) {
    // return false;
    // }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (version == null) {
      if (other.version != null) {
        return false;
      }
    } else if (!version.equals(other.version)) {
      return false;
    }
    return true;
  }


  /**
   * @return date
   */
  public String getDate ()
  {
    return date;
  }


  /**
   * @return hyperlink for more informations
   */
  public String getLink ()
  {
    return link;
  }


  /**
   * @return logo (picture) of the license
   */
  public Image getLogo ()
  {
    return logo;
  }


  /**
   * @return name of the license
   */
  public String getName ()
  {
    return name;
  }


  /**
   * @return version
   */
  public String getVersion ()
  {
    return version;
  }


  @Override
  public String toString ()
  {
    return getName();
  }
}
