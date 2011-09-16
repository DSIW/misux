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
import java.io.File;
import java.io.IOException;

import misux.div.Images;
import misux.io.Convert;

/**
 * This class implements different fields and methods. Fields are the name of
 * the cover, the image of the cover, the file extension (i.e. jpg, png) and the
 * hyperlink to the image.
 * 
 * @author DSIW
 * 
 */
public class Cover
{
  Image             image;
  String            interpret;
  String            album;
  PictureExtensions ext;
  String            link;
  String            path;


  /**
   * Creates a Cover.
   * 
   * @param image
   *          cover image
   * @param album
   *          name of the album
   * @param interpret
   *          name of the interpret
   * @param ext
   *          file extension
   * @param link
   *          hyperlink
   */
  public Cover(final Image image, final String interpret, final String album,
      final PictureExtensions ext, final String link)
  {
    this.image = image;
    this.interpret = interpret;
    this.album = album;
    this.ext = ext;
    this.link = link;
    path = "";
  }


  /**
   * Creates a new cover with no image, the extension "JPG" and no link.
   * 
   * @param interpret
   *          name of the interpret
   * @param album
   *          name of the album
   */
  public Cover(final String interpret, final String album)
  {
    this(null, interpret, album, PictureExtensions.JPG, "");
  }


  /**
   * @return true, if a cover exists. False in the opposite.
   * @author DSIW
   */
  public boolean exists ()
  {
    return new File(getAbsolutePath()).canRead();
  }


  /**
   * @return the absolute path to the image.
   * @author DSIW
   */
  public String getAbsolutePath ()
  {
    return path + File.separator + getFileName();
  }


  /**
   * @return the name of the album
   */
  public String getAlbum ()
  {
    return album;
  }


  /**
   * @return file extension or image type (jpg, png, etc.)
   */
  public PictureExtensions getExt ()
  {
    return ext;
  }


  /**
   * @return the file name with extension.
   * @author DSIW
   */
  public String getFileName ()
  {
    return getFullName() + "." + getExt().toString().toLowerCase();
  }


  private String getFullName ()
  {
    String name = Convert.toIO(interpret);
    name += "-";
    name += Convert.toIO(album);
    return name;
  }


  /**
   * @return cover image
   */
  public Image getImage ()
  {
    return image;
  }


  /**
   * @return the name of the interpret
   */
  public String getInterpret ()
  {
    return interpret;
  }


  /**
   * @return link
   */
  public String getLink ()
  {
    return link;
  }


  /**
   * @return the path. The cover will be written in this directory.
   */
  public String getPath ()
  {
    return path;
  }


  /**
   * Checks the image to write it. It checks, that a non-empty link and
   * non-empty extension is set. The width and height must be greater than 0.
   * 
   * @return true, if it's writeable
   */
  public boolean isWriteble ()
  {
    int width;
    int height;
    try {
      width = Images.getWidth(image);
      height = Images.getHeight(image);
    }
    catch (final NullPointerException e) {
      return false;
    }

    System.out.println(path);
    return ext != PictureExtensions.OTHER && height > 0 && width > 0
        && !interpret.isEmpty() && !album.isEmpty() && !path.isEmpty();
  }


  /**
   * Scales the image to a same height and width.
   * 
   * @param width
   *          Width and height of the new image.
   */
  public void resize (final int width)
  {
    image = Images.resize(getImage(), width);
  }


  /**
   * @param album
   *          the album to set
   */
  public void setAlbum (final String album)
  {
    this.album = album;
  }


  /**
   * Sets image type (jpg, png, etc.) and file extension
   * 
   * @param ext
   *          picture extension
   */
  public void setExt (final PictureExtensions ext)
  {
    this.ext = ext;
  }


  /**
   * Sets cover image
   * 
   * @param image
   *          image as Image
   */
  public void setImage (final Image image)
  {
    this.image = image;
  }


  /**
   * @param interpret
   *          the interpret to set
   */
  public void setInterpret (final String interpret)
  {
    this.interpret = interpret;
  }


  /**
   * Sets link
   * 
   * @param path
   *          link as String
   */
  public void setLink (final String path)
  {
    link = path;
  }


  /**
   * @param path
   *          the path to set
   */
  public void setPath (final String path)
  {
    this.path = path;
  }


  @Override
  public String toString ()
  {
    return image.getClass().getSimpleName() + " (H:" + Images.getHeight(image)
        + ", W:" + Images.getWidth(image) + "), " + getExt() + ", "
        + getFileName() + " :: " + getLink(); // getLink().substring(getLink().lastIndexOf('/')+1);
  }


  /**
   * Writes the image in the filesystem.
   * 
   * @throws IOException
   *           if the image can't be written
   */
  public void write () throws IOException
  {
    new Images(image).write(getPath(), getFullName(), ext.toString());
  }
}
