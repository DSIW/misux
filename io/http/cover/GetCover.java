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

import java.util.List;

import misux.div.exceptions.NoCoverFoundException;

/**
 * This abstract class implements methods to download and save covers. The name
 * of the interpret and album are needed. It can download one cover or more.
 * 
 * @author DSIW
 * 
 */
public abstract class GetCover
{
  private List<Cover> covers = null;


  /**
   * @return album name for search
   */
  public abstract String getAlbum ();


  /**
   * @return the first found cover
   * @throws NoCoverFoundException
   * @author DSIW
   */
  public Cover getCover () throws NoCoverFoundException
  {
    return getCovers().get(0);
  }


  /**
   * @return a list of all found covers
   * @throws NoCoverFoundException
   * @author DSIW
   */
  public List<Cover> getCovers () throws NoCoverFoundException
  {
    if (covers == null) {
      covers = readCovers();
    }
    return covers;
  }


  /**
   * @return name of the interpret as String
   */
  public abstract String getInterpret ();


  /**
   * @return maximum count for search
   */
  public abstract int getMaxCover ();


  /**
   * @return true, if no cover was found. If one cover was found, then it
   *         returns false.
   */
  public boolean noFound ()
  {
    List<Cover> covers = null;
    try {
      covers = getCovers();
    }
    catch (final NoCoverFoundException e) {
      return true;
    }
    catch (final Exception e) {
      return true;
    }
    if (covers.get(0) == null || covers == null || covers.size() == 0) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return the first found cover. If no cover was found, then returns null.
   */
  protected abstract Cover readCover ();


  /**
   * @return cover list, that't length is maximal of the searched covers. If no
   *         cover was found, then returns null.
   */
  protected abstract List<Cover> readCovers ();


  /**
   * Sets the name of the album for search
   * 
   * @param album
   *          name for search
   */
  public abstract void setAlbum (String album);


  /**
   * Sets the interpret for search
   * 
   * @param interpret
   */
  public abstract void setInterpret (String interpret);


  /**
   * Sets the maximum count for search
   * 
   * @param max
   *          maximum count
   */
  public abstract void setMaxCover (int max);
}
