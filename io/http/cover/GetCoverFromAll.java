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

/**
 * This class implements methods to get covers from all sources. If one source
 * didn't find a cover, the next source will be used.
 * 
 * @author DSIW
 * 
 */
public class GetCoverFromAll extends GetCover
{
  private String    interpret;
  private String    album;
  private int       maxCovers;
  private final int size;
  private int       sourcenr;


  /**
   * Creates a new object with parameters.
   * 
   * @param interpret
   *          name of the interpret
   * @param album
   *          name of the album
   * @param size
   *          size of the image
   * @param maxCovers
   *          maximum count of all found covers
   */
  public GetCoverFromAll(final String interpret, final String album,
      final int size, final int maxCovers)
  {
    this.interpret = interpret;
    this.album = album;
    this.maxCovers = maxCovers;
    this.size = size;
  }


  @Override
  public String getAlbum ()
  {
    return album;
  }


  @Override
  public String getInterpret ()
  {
    return interpret;
  }


  @Override
  public int getMaxCover ()
  {
    return maxCovers;
  }


  /**
   * @return the sourcenr (0 = Amazon.de | 1 = Google.de)
   */
  public int getSourcenr ()
  {
    return sourcenr;
  }


  @Override
  protected Cover readCover ()
  {
    return readCovers().get(0);
  }


  @Override
  protected List<Cover> readCovers ()
  {
    GetCover gc = null;
    if (sourcenr == 0) {
      gc = new GetCoverAmazon(interpret, album, size, maxCovers);
      if (gc.noFound()) {
        sourcenr = 1;
        return readCovers();
      } else {
        return gc.getCovers();
      }
    } else {
      gc = new GetCoverGoogle(interpret, album, maxCovers);
      return gc.getCovers();
    }
  }


  @Override
  public void setAlbum (final String album)
  {
    this.album = album;
  }


  @Override
  public void setInterpret (final String interpret)
  {
    this.interpret = interpret;
  }


  @Override
  public void setMaxCover (final int max)
  {
    maxCovers = max;
  }


  /**
   * @param sourcenr
   *          the sourcenr to set (0 = Amazon.de | 1 = Google.de)
   */
  public void setSourcenr (final int sourcenr)
  {
    this.sourcenr = sourcenr;
  }

}
