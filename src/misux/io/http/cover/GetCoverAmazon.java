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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import misux.div.exceptions.NoCoverFoundException;
import misux.io.http.HTTP;

/**
 * Search and download covers from amazon.de
 * 
 * @author DSIW
 * 
 */
public class GetCoverAmazon extends GetCover
{
  private String                  interpret;
  private String                  album;
  private List<PictureExtensions> imageExts        = new LinkedList<PictureExtensions>();
  private final String            PATTERN          = "class=\"productImage\" alt=\"Produkt-Information\"";
  private final String            PATTERN_NORESULT = "keine Treffer";
  private final char[]            SRC              = { 'c', 'r', 's' };
  private int                     maxCovers;
  private int                     coverSize;
  private final String            azUrl            = "http://www.amazon.de/s/url=search-alias%3Dpopular&field-keywords=";
  private String                  url;


  public String getInterpret ()
  {
    return interpret;
  }


  public void setInterpret (String interpret)
  {
    this.interpret = interpret.trim().toLowerCase();
  }


  public String getAlbum ()
  {
    return album;
  }


  public void setAlbum (String album)
  {
    this.album = album.trim().toLowerCase();
  }


  @Override
  public void setMaxCover (int max)
  {
    this.maxCovers = max;
  }


  @Override
  public int getMaxCover ()
  {
    return maxCovers;
  }


  /**
   * @return the url
   */
  public String getUrl ()
  {
    return url;
  }



  /**
   * @return cover size as integer
   */
  public int getCoverSize ()
  {
    return coverSize;
  }


  /**
   * Sets cover size. If the parameter less than 0, then the size will be 200.
   * 
   * @param coverSize
   *          > 0
   */
  public void setCoverSize (int coverSize)
  {
    if (coverSize <= 0) {
      this.coverSize = 200;
    }
    this.coverSize = coverSize;
  }


  /**
   * Creates a new GetCover with a cover size of 200 pixel and search for one
   * cover.
   * 
   * @param interpret
   *          interpret name for search
   * @param album
   *          album name for search
   */
  public GetCoverAmazon(String interpret, String album)
  {
    this(interpret, album, 200, 1);
  }


  /**
   * Creates GetCover with parameters
   * 
   * @param interpret
   *          interpret name for search
   * @param album
   *          album name for search
   * @param size
   *          cover size
   * @param maxCovers
   *          maximum count of covers for search.
   */
  public GetCoverAmazon(String interpret, String album, int size, int maxCovers)
  {
    setInterpret(interpret);
    setAlbum(album);
    setCoverSize(size);
    this.maxCovers = maxCovers;
    this.url = azUrl + convertSearchToURL();
  }


  /*
   * Converts the interpret and album for search. Alle whitespaces will be
   * replaced of "+". ("%22" is the character for """.)
   */
  protected String convertSearchToURL ()
  {
    if (!(interpret.isEmpty() && album.isEmpty())) {
      String conv;
      conv = "%22" + interpret.replace(' ', '+') + "%22";
      conv += "+%22" + album.replace(' ', '+') + "%22";
      return conv;
    }
    return null;
  }


  private boolean foundCover (String content)
  {
    if (content.indexOf(PATTERN_NORESULT) > 0) {
      return false;
    } else {
      return true;
    }
  }


  /*
   * Search PATTERN
   */
  private int indexOfImgClass (String content)
  {
    return content.indexOf(PATTERN) + PATTERN.length();
  }


  /*
   * Search "src" backwards, because the PATTERN is after the "src" in amazon's
   * sourcecode.
   */
  private int getIndexOfSrc (String content, int start)
  {
    while (start > 0) {
      if (content.charAt(start - 0) == SRC[0]
          && content.charAt(start - 1) == SRC[1]
          && content.charAt(start - 2) == SRC[2]) {
        return start - 2;
      }
      start--;
    }
    return -1;
  }


  /*
   * Tests URL for a image type. If there are no type, then the result will be
   * "-1". It adds the found image type to the list, if it's in the enum
   * 'PictureExtension'.
   */
  private int indexOfPictureExtension (String url) throws Exception
  {
    PictureExtensions[] pics = PictureExtensions.values();
    for (int i = 0; i < pics.length; i++) {
      url = url.toUpperCase();
      String ext = pics[i].toString().toUpperCase();
      if (url.indexOf(ext.toString()) > 0) {
        imageExts.add(PictureExtensions.value(ext));
        return url.indexOf(ext) + ext.length();
      }
    }
    throw new Exception("No picture type found");
  }


  /*
   * Extracts the image link from the content.
   */
  private String getPictureSrc (String content) throws Exception
  {
    if (indexOfImgClass(content) < 0
        || getIndexOfSrc(content, indexOfImgClass(content)) < 0) return "";
    String sub = content.substring(
        getIndexOfSrc(content, indexOfImgClass(content)),
        indexOfImgClass(content));
    String bildURL = sub.substring(sub.indexOf("http"),
        indexOfPictureExtension(sub));
    return bildURL;
  }


  /*
   * Exctracts many covers from the content. If a image url will be found, then
   * the search will go on, after this location.
   */
  private List<String> getPictureSrcs (String content) throws Exception
  {
    final int ANZ_RESULT = maxCovers;
    List<String> paths = new LinkedList<String>();
    String tmp = content;
    for (int i = 0; i < ANZ_RESULT; i++) {
      if (!getPictureSrc(tmp).equals("")) {
        paths.add(getPictureSrc(tmp));
      }
      int index = indexOfImgClass(tmp);
      tmp = tmp.substring(index);
    }
    return paths;
  }


  /*
   * Amazon.de scales pictures with their servers, if you change "AA115" (115
   * pixels) to "SS#". "#" is replaced by a number, that will be the height and
   * width of the image. If you use too big values, amazon.de will be add a
   * white border to the image.
   */
  private List<String> convertPathsToBigCovers (List<String> paths)
  {
    for (int i = 0; i < paths.size(); i++) {
      String conv = "SS" + getCoverSize();
      paths.set(i, paths.get(i).replaceAll("AA115", conv));
    }
    return paths;
  }


  /*
   * The picture url will be extract and the link will be changed to the whished
   * size. The found covers will be download and add to the list.
   */
  @Override
  public List<Cover> readCovers () throws NoCoverFoundException
  {
    List<Cover> covers = new LinkedList<Cover>();
    String content = null;
    try {
      content = HTTP.readContent(url, HTTP.FIREFOX);
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    if (content == null || !foundCover(content)) {
      throw new NoCoverFoundException();
    }

    List<String> paths = null;
    try {
      paths = convertPathsToBigCovers(getPictureSrcs(content));

      for (int i = 0; i < paths.size() && i < 5; i++) {
        // lade Image von path
        ImageProducer imgProd = (ImageProducer) new URL(paths.get(i)).openConnection()
            .getContent();
        Image img = Toolkit.getDefaultToolkit().createImage(imgProd);

        // erzeuge Cover
        PictureExtensions ext = imageExts.get(i);
        String link = paths.get(i);
        Cover c = new Cover(img, getInterpret(), getAlbum(), ext, link);
        covers.add(c);
      }
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return covers;
  }


  @Override
  public Cover readCover ()
  {
    List<Cover> covers = readCovers();
    if (covers == null || covers.size() <= 0) return null;
    return covers.get(0);
  }
}
