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
package misux.div;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class represents an image.
 * 
 * @author DSIW
 * 
 */
public class Images implements ImageObserver
{
  /**
   * @param image
   *          image
   * @return height of the image
   */
  public static int getHeight (final Image image)
  {
    return Images.toImageIcon(image).getIconHeight();
  }


  /**
   * @param icon
   *          ImageIcon
   * @return height of ImageIcon
   */
  public static int getHeight (final ImageIcon icon)
  {
    return icon.getIconHeight();
  }


  /**
   * @param image
   *          image
   * @return width of the image
   */
  public static int getWidth (final Image image)
  {
    return Images.toImageIcon(image).getIconWidth();
  }


  /**
   * @param icon
   *          ImageIcon
   * @return width of ImageIcon
   */
  public static int getWidth (final ImageIcon icon)
  {
    return icon.getIconWidth();
  }


  /**
   * Loads an icon from the class path.
   * 
   * @param fileName
   *          picture name (not absolute path)
   * @return icon
   * @author DSIW
   */
  public static Icon loadLocalIcon (final String fileName)
  {
    Image image = null;
    try {
      image = new Images(fileName).getImage();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    final Icon icon = Images.toImageIcon(image);
    return icon;
  }


  /**
   * Resizes an image.
   * 
   * @param img
   *          image to resize
   * @param width
   *          width and height of the resized image
   * @return resized image
   * @author DSIW
   */
  public static Image resize (final Image img, final int width)
  {
    return Images.resize(img, width, width);
  }


  /**
   * Resizes an image.
   * 
   * @param img
   *          image to resize
   * @param width
   *          width of the resized image
   * @param height
   *          height of the resized image
   * @return resized image
   * @author DSIW
   */
  public static Image resize (final Image img, final int width, final int height)
  {
    final int PREFERRED_WIDTH = width;
    final int PREFERRED_HEIGHT = height;
    ImageIcon icon = new ImageIcon(img);
    if (icon.getIconWidth() > PREFERRED_WIDTH) {
      icon = new ImageIcon(icon.getImage().getScaledInstance(PREFERRED_WIDTH,
          -1, Image.SCALE_DEFAULT));
      if (icon.getIconHeight() > PREFERRED_HEIGHT) {
        icon = new ImageIcon(icon.getImage().getScaledInstance(-1,
            PREFERRED_HEIGHT, Image.SCALE_DEFAULT));
      }
    }
    return icon.getImage();
  }


  /**
   * Converts an ImageIcon to an image
   * 
   * @param icon
   *          ImageIcon to convert
   * @return converted ImageIcon
   */
  public static Image toImage (final ImageIcon icon)
  {
    return icon.getImage();
  }


  /**
   * Converts the image to an ImageIcon
   * 
   * @param image
   *          image to convert
   * @return converted image
   */
  public static ImageIcon toImageIcon (final Image image)
  {
    return new ImageIcon(image);
  }

  private Image image;


  /**
   * Creates a new image of the specified image
   * 
   * @param image
   */
  public Images(final Image image)
  {
    this.image = image;
  }


  /**
   * Creates a new image and reads an image from a file
   * 
   * @param fileName
   *          absolute path to the image file
   * @throws IOException
   */
  public Images(final String fileName) throws IOException
  {
    setImage(readImage(fileName));
  }


  /**
   * @return the image
   */
  public Image getImage ()
  {
    return image;
  }


  @Override
  public boolean imageUpdate (final Image img, final int infoflags,
      final int x, final int y, final int width, final int height)
  {
    return true;
  }


  /**
   * Reads an image from a file in the class path.
   * 
   * @param fileName
   *          name of the file (not absolute)
   * @return image
   * @throws IOException
   */
  public Image readImage (final String fileName) throws IOException
  {
    final String sep = File.separator;
    return ImageIO.read(getClass().getClassLoader().getResource(
        "resources" + sep + "imgs" + sep + fileName));
  }


  /**
   * @param image
   *          the image to set
   */
  public void setImage (final Image image)
  {
    this.image = image;
  }


  // private static int[] calcPictureSize (Image img, int maxSize)
  // {
  // int width = Images.getWidth(img);
  // int height = Images.getHeight(img);
  // double scaledWidth = width;
  // double scaledHeight = height;
  // if (width >= height) {
  // scaledWidth = (maxSize / width) * width;
  // scaledHeight = (maxSize / width) * height;
  // } else {
  // scaledWidth = (maxSize / height) * width;
  // scaledHeight = (maxSize / height) * height;
  // }
  // return new int[] { (int) scaledWidth, (int) scaledHeight };
  // }

  /**
   * Writes an image into the path with the specified file name and the image
   * extension.
   * 
   * @param path
   *          path
   * @param fileName
   *          file name of the written image
   * @param ext
   *          extension of the file name
   * @throws IOException
   * @author DSIW
   */
  public void write (final String path, final String fileName, final String ext)
      throws IOException
  {
    final BufferedImage bimage = new BufferedImage(Images.getWidth(image),
        Images.getHeight(image), BufferedImage.TYPE_INT_RGB);
    final Graphics2D graphics2d = bimage.createGraphics();
    graphics2d.drawImage(image, 0, 0, this);
    ImageIO.write(bimage, ext.toLowerCase(), new File(path + File.separator
        + fileName + "." + ext.toLowerCase()));
  }
}
