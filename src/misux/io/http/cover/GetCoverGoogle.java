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
package misux.io.http.cover;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import misux.io.http.HTTP;

/**
 * Searches for covers at www.google.de
 * 
 * @author DSIW
 * @author DSIW
 */
public class GetCoverGoogle extends GetCover
{
  private String interpret;
  private String album;
  private int    maxCovers;


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getAlbum ()
  {
    if (album == null) return "";
    return album;
  }


  /**
   * Searches for covers at google.
   * 
   * @param searchstring
   *          searchstring in uri-syntax
   * @param numberOfCovers
   *          maximum covers to search for
   * @return list with cover-objects
   * @throws IOException
   * @author DSIW
   */
  private List<Cover> search (String searchstring, int numberOfCovers)
      throws IOException
  {
    String page = HTTP.readContent(getSearchString(), HTTP.LYX);
    List<String> tagList = new ArrayList<String>();
    int pos = 0;
    while (true)// alle links in taglist kopieren
    {
      pos = page.indexOf("href=", pos);
      if (pos == -1) {
        break;
      }
      String sub = page.substring(pos + 6, page.indexOf("\"", pos + 6));
      tagList.add(sub);
      pos += 5;// endlosschleife verhindern
    }
    List<Cover> coverList = new ArrayList<Cover>(numberOfCovers);
    for (int i = 0; i < tagList.size(); i++) {
      if (coverList.size() == numberOfCovers) {
        break;
      }
      coverList.addAll(filterPicExt(tagList, PictureExtensions.JPG));
      coverList.addAll(filterPicExt(tagList, PictureExtensions.PNG));
    }
    return coverList;
  }


  private List<Cover> filterPicExt (List<String> tagList, PictureExtensions ext)
  {
    String picExt = "." + ext.toString().toLowerCase();
    List<Cover> coverList = new ArrayList<Cover>(maxCovers);
    for (int i = 0; i < tagList.size(); i++) {
      try {
        if (coverList.size() == maxCovers)// n bilder laden
        {
          break;
        }
        if (tagList.get(i).contains(picExt.toLowerCase()))// wenn der aktueller
        // link ein jpg
        // bild ist
        {
          String adress = tagList.get(i).substring(
              tagList.get(i).indexOf("http"),
              tagList.get(i).indexOf(picExt.toLowerCase()) + picExt.length());// den
          // link
          // trimmen
          ImageProducer imgProd = (ImageProducer) new URL(adress)
              .openConnection().getContent();
          Image image = Toolkit.getDefaultToolkit().createImage(imgProd);

          Cover cover = new Cover(image, interpret, album, ext, adress);
          coverList.add(cover);
        }
      }
      catch (SocketException e) {
        // e.printStackTrace();
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      catch (UnknownHostException e) {
        e.printStackTrace();
      }
      catch (ClassCastException e) {
        System.err.println("ImageProducer can't created in GetCoverGoogle");
        // e.printStackTrace();
      }
      catch (MalformedURLException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    return coverList;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public Cover readCover ()
  {
    return readCovers().get(0);
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public List<Cover> readCovers ()
  {
    List<Cover> covers = null;
    try {
      covers = search(getSearchString(), getMaxCover());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    if (covers.size() == 0) return null;
    return covers;
  }


  /**
   * Constructor to create a new {@link GetCover} object.
   * 
   * @param interpret
   *          interpret of the album to search for
   * @param album
   *          album to search for
   * @param maxCovers
   *          maximum number of pictures to search for
   * @author DSIW
   */
  public GetCoverGoogle(String interpret, String album, int maxCovers)
  {
    this.interpret = interpret;
    this.album = album;
    this.maxCovers = maxCovers;
  }


  /**
   * Gets the searchstring. This methid returns the searchstring in uri-syntax
   * needed for the search() method.
   * 
   * @return searchstring needed for search(...)
   * @author DSIW
   */
  private String getSearchString ()
  {
    String searchstring = "http://www.google.de/images?q=%22";
    searchstring += stringToHTMLStuff(interpret);
    searchstring += "%22+%22";
    searchstring += stringToHTMLStuff(album);
    searchstring += "%22";
    return searchstring;
  }


  /**
   * Converts a string to uri-style
   * 
   * @param str
   *          String to convert
   * @return converted String
   * @author DSIW
   */
  private String stringToHTMLStuff (String str)
  {
    return str.replace(' ', '+');
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public String getInterpret ()
  {
    if (interpret == null) return "";
    return interpret;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public int getMaxCover ()
  {
    return maxCovers;
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setAlbum (String album)
  {
    this.album = album.toLowerCase().trim();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setInterpret (String interpret)
  {
    this.interpret = interpret.toLowerCase().trim();
  }


  /**
   * {@inheritDoc}
   * 
   * @author DSIW
   */
  @Override
  public void setMaxCover (int max)
  {
    if (max < 0)
      this.maxCovers = 0;
    else
      this.maxCovers = max;
  }
}
