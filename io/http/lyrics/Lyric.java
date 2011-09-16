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

/**
 * This class will represent a lyric object.
 * 
 * @author DSIW
 * 
 */
public class Lyric
{

  private String content;
  private String interpret;
  private String title;


  /**
   * @return content of the lyric
   */
  public String getContent ()
  {
    return content;
  }


  /**
   * Sets the content
   * 
   * @param content
   *          the lyric
   */
  public void setContent (String content)
  {
    this.content = content;
  }


  /**
   * @return the interpret of the lyric
   */
  public String getInterpret ()
  {
    return interpret;
  }


  /**
   * Sets the interpret
   * 
   * @param interpret
   *          of the lyric
   */
  public void setInterpret (String interpret)
  {
    this.interpret = interpret;
  }


  /**
   * @return the title of the song
   */
  public String getTitle ()
  {
    return title;
  }


  /**
   * Sets the title
   * 
   * @param title
   *          of the song
   */
  public void setTitle (String title)
  {
    this.title = title;
  }


  /**
   * Creates a new lyric with parameters
   * 
   * @param content
   *          the lyric
   * @param interpret
   *          interpret of the lyric
   * @param title
   *          title of the song
   */
  public Lyric(String content, String interpret, String title)
  {
    super();
    this.content = content;
    this.interpret = interpret;
    this.title = title;
  }


  /**
   * Creates a new lyric with an empty interpret and song title
   * 
   * @param content
   *          the lyric
   */
  public Lyric(String content)
  {
    this(content, "", "");
  }

}
