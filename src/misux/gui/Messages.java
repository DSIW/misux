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
package misux.gui;

import java.awt.Component;
import java.awt.Image;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import misux.div.Images;

/**
 * This class implements static methods to create different message dialogues.
 * 
 * @author DSIW
 * 
 */
public class Messages
{
  private static Icon loadIcon (final String fileName)
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
   * Shows a confirm dialoge with a yes- and no-button.
   * 
   * @param parent
   *          parent of this dialoge
   * @param message
   *          text, which will be displayed
   * @param title
   *          title of this dialog
   * @return true, if the yes-button was clicked
   * @author DSIW
   */
  public static boolean showConfirmMessage (final Component parent,
      final String message, final String title)
  {
    final int i = JOptionPane.showConfirmDialog(parent, message, title,
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
        Messages.loadIcon("question.png"));
    return i == JOptionPane.YES_OPTION ? true : false;
  }


  /**
   * Shows an error dialoge with the title "Error".
   * 
   * @param parent
   *          parent of this dialoge
   * @param message
   *          text, which will be displayed
   * @author DSIW
   */
  public static void showErrorMessage (final Component parent,
      final String message)
  {
    Messages.showErrorMessage(parent, message, "Error");
  }


  /**
   * Shows an error dialoge.
   * 
   * @param parent
   *          parent of this dialoge
   * @param message
   *          text, which will be displayed
   * @param title
   *          title of this dialog
   * @author DSIW
   */
  public static void showErrorMessage (final Component parent,
      final String message, final String title)
  {
    JOptionPane.showMessageDialog(parent, message, title,
        JOptionPane.ERROR_MESSAGE, Messages.loadIcon("error.png"));
  }


  /**
   * Shows an exit dialoge.
   * 
   * @param parent
   *          parent of this dialoge
   * @return true, if the yes-button was clicked
   * @author DSIW
   */
  public static boolean showExitMessage (final Component parent)
  {
    final int i = JOptionPane.showConfirmDialog(parent,
        "Do you really want to quit the program?", "Quit",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
        Messages.loadIcon("exit.png"));
    return i == JOptionPane.YES_OPTION ? true : false;
  }


  /**
   * Shows an info dialoge.
   * 
   * @param parent
   *          parent of this dialoge
   * @param message
   *          text, which will be displayed
   * @author DSIW
   */
  public static void showInfoMessage (final Component parent,
      final String message)
  {
    Messages.showInfoMessage(parent, message, "Info");
  }


  /**
   * Shows an info dialoge.
   * 
   * @param parent
   *          parent of this dialoge
   * @param message
   *          text, which will be displayed
   * @param title
   *          title of the dialoge
   * @author DSIW
   */
  public static void showInfoMessage (final Component parent,
      final String message, final String title)
  {
    JOptionPane.showMessageDialog(parent, message, title,
        JOptionPane.INFORMATION_MESSAGE, Messages.loadIcon("info.png"));
  }


  /**
   * Shows a warning dialoge.
   * 
   * @param parent
   *          parent of this dialoge
   * @param message
   *          text, which will be displayed
   * @param title
   *          title of the dialoge
   * @author DSIW
   */
  public static void showWarningMessage (final Component parent,
      final String message, final String title)
  {
    JOptionPane.showMessageDialog(parent, message, title,
        JOptionPane.WARNING_MESSAGE, Messages.loadIcon("warning.png"));
  }
}
