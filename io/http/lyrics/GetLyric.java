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
package misux.io.http.lyrics;

import java.io.IOException;
import java.net.URL;

import misux.div.exceptions.NoLyricFoundException;
import misux.io.Convert;
import misux.io.http.HTTP;

/**
 * This class implements method to download the lyric of a song
 * 
 * @author DSIW
 * 
 */
public class GetLyric
{
  private String       title, interpret;
  private URL          url;
  private int          numberResult     = 1;
  private String       content;
  private String       HTTP_CONTENT;

  private final String pattern          = "<div id=\"lyric_space\">";
  private final String endPattern       = "</div>";
  private final String pattern_noResult = "The URI you submitted has disallowed characters";
  private final String website          = "http://www.lyrics.com/";

  private Lyric        lyric;


  /**
   * Creates GetLyric with parameters
   * 
   * @param title
   *          title of the song
   * @param interpret
   *          name of the interpret
   * @param numberRes
   *          count of the results
   */
  public GetLyric(final String title, final String interpret,
      final int numberRes)
  {
    setTitle(title);
    setInterpret(interpret);
    setNumberResult(numberRes);
    try {
      setHTTPContent(readContent());
      content = HTTP_CONTENT;
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
  }


  private String convertSearchToURL ()
  {
    if (!(title.isEmpty() && interpret.isEmpty())) {
      String conv;
      conv = Convert.toIO(title.replace(' ', '-')).replaceAll("_", "");
      conv += "-lyrics-";
      conv += Convert.toIO(interpret.replace(' ', '-')).replaceAll("_", "");
      conv += ".html";
      return conv;
    }
    return null;
  }


  /**
   * @return interpret of the lyric
   */
  public String getInterpret ()
  {
    return interpret;
  }


  /**
   * @return the lyric
   * @throws NoLyricFoundException
   *           if no lyric was found
   */
  public Lyric getLyric () throws NoLyricFoundException
  {
    readLyric();
    if (lyric == null) {
      throw new NoLyricFoundException();
    }
    return lyric;
  }


  /**
   * @return the count of results
   */
  public int getNumberResult ()
  {
    return numberResult;
  }


  /**
   * @return title of the song
   */
  public String getTitle ()
  {
    return title;
  }


  private int indexOfEndPattern (final int indexStartPattern)
  {
    final String endPattern = this.endPattern;
    final int i = content.indexOf(endPattern, indexStartPattern);
    if (i < 0 || i < indexStartPattern) {
      return -1;
    }
    return i;
  }


  private int indexOfPattern ()
  {
    final String PATTERN = pattern;
    return content.indexOf(PATTERN) + PATTERN.length();
  }


  /**
   * @return true, if no lyric was found. False in the opposite.
   * @author DSIW
   */
  public boolean noFound ()
  {
    readLyric();
    if (lyric == null || noResult()) {
      return true;
    } else {
      return false;
    }
  }


  private boolean noResult ()
  {
    final String PATTERN = pattern_noResult;
    final int index = HTTP_CONTENT.indexOf(PATTERN);
    return index >= 0;
  }


  private String readContent () throws IOException
  {
    url = new URL(website + convertSearchToURL());
    return HTTP.readContent(url, HTTP.FIREFOX);
  }


  /**
   * Sets the lyric of the song. If the search will fail, it will return null
   */
  public void readLyric ()
  {
    if (noResult()) {
      content = null;
    }
    if (indexOfPattern() < 0) {
      content = "";
    }
    String c = content.substring(indexOfPattern(),
        indexOfEndPattern(indexOfPattern()));
    c = c.replaceAll("---*", "");
    c = c.replaceAll("<br />", "");
    c = rmComment(c);
    c = rmLink(c);
    c = rmSpecialTag(c);
    c = rmLeerzeilen(c);
    c = trimRows(c);
    if (c.contains("W3C") || c.contains("XHTML") || c.contains("html")
        || c.contains("are currently unavailable")) {
      lyric = null;
    } else {
      lyric = new Lyric(c, interpret, title);
    }
  }


  private String rmComment (final String l)
  {
    return l.replaceAll("<!--(.|\\s)*?-->", "");
  }


  private String rmLeerzeilen (String l)
  {
    l = l.replaceAll("\r\n", "");
    // return l.replaceAll("\n\n", "");
    return l;
  }


  private String rmLink (final String l)
  {
    return l.replaceAll("<a(.|\\s)*?</a>", "");
  }


  private String rmSpecialTag (String l)
  {
    l = l.replaceAll("<!(.|\\\\s)*?>", "");
    l = l.replaceAll("<div[:print:]*>", "");
    return l.replaceAll("&#8217;", "'");
  }


  /**
   * Set HTTP content
   * 
   * @param in
   *          content as String
   */
  public void setHTTPContent (final String in)
  {
    if (HTTP_CONTENT == null) {
      HTTP_CONTENT = in;
    }
  }


  /**
   * Sets the trimmed and lower cased name of the interpret
   * 
   * @param interpret
   *          name
   */
  public void setInterpret (final String interpret)
  {
    this.interpret = interpret.trim().toLowerCase();
  }


  /**
   * @param numberResult
   *          the count of results to set
   */
  public void setNumberResult (final int numberResult)
  {
    this.numberResult = numberResult;
  }


  /**
   * Sets the trimmed and lower cased title of a song
   * 
   * @param title
   *          title of song
   */
  public void setTitle (final String title)
  {
    this.title = title.trim().toLowerCase();
  }


  private String trimRows (final String l)
  {
    final String[] splitted = l.split("\\n");
    final StringBuilder sb = new StringBuilder();
    for (final String element : splitted) {
      String s = element;
      s = s.replaceAll("[\\t]", "");
      sb.append(s.trim() + "\n");
    }
    return sb.toString();
  }
}
