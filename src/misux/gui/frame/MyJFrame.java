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
package misux.gui.frame;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import misux.div.Images;

/**
 * This class implements sets default configurations for a frame.
 * 
 * @author DSIW
 * 
 */
public class MyJFrame extends JFrame
{

  /**
   * 
   */
  private static final long serialVersionUID = 7422186832905813390L;
  private ActionListener    forceClose;
  private ActionListener    saveAndClose;
  private JPanel            content          = new JPanel();


  /**
   * Creates a new frame with an empty title.
   */
  public MyJFrame()
  {
    this("");
  }


  /**
   * Creates a new frame with a specified title and content. The size is
   * 500x400.
   * 
   * @param title
   *          Title
   */
  public MyJFrame(final String title)
  {
    super(title);
    setLookAndFeel();
    setFrameIcon();
    setPreferredSize(new Dimension(1000, 430));
    setMinimumSize(getPreferredSize());
    // setLocation(getMiddlePosition());
    setLocationByPlatform(true);
    forceClose = new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        disposeFrame();
      }
    };
    this.add(content);
  }


  /**
   * Sets the frame non visible and dispose it.
   */
  public void disposeFrame ()
  {
    setVisible(false);
    super.dispose();
  }


  /**
   * @return the content
   */
  public JPanel getContent ()
  {
    return content;
  }


  /**
   * @return the forceClose
   */
  public ActionListener getForceClose ()
  {
    return forceClose;
  }


  /**
   * Sets the frame to the middle of the screen.
   * 
   * @return startpoint of the window
   */
  public Point getMiddlePosition ()
  {
    final GraphicsEnvironment env = GraphicsEnvironment
        .getLocalGraphicsEnvironment();
    final GraphicsDevice gd = env.getDefaultScreenDevice();
    final DisplayMode dm = gd.getDisplayMode();
    final int newX = (dm.getWidth() - this.getWidth()) / 2;
    final int newY = (dm.getHeight() - this.getHeight()) / 2;
    return new Point(newX, newY);
  }


  /**
   * 
   * Gets the location of the frame, so that this frame is in the middle of the
   * parent frame.
   * 
   * @param parentJFrame
   *          parent frame
   * @return startpoint of the frame (top-left)
   */
  public Point getMiddlePosition (final JFrame parentJFrame)
  {
    // position from parent frame
    final Point parentLocation = parentJFrame.getLocationOnScreen();
    // position from this
    final int thisX = (parentJFrame.getWidth() - this.getWidth()) / 2
        + (int) parentLocation.getX();
    final int thisY = (parentJFrame.getHeight() - this.getHeight()) / 2
        + (int) parentLocation.getY();
    return new Point(thisX, thisY);
  }


  /**
   * @return the saveAndClose
   */
  public ActionListener getSaveAndClose ()
  {
    return saveAndClose;
  }


  /**
   * Initialise frame: close program if window is closed.
   */
  public void initFrame ()
  {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setSize(1000, 700);
    setVisible(true);
  }


  /**
   * Refreshs the frame.
   */
  public void refresh ()
  {
    this.validate();
    content.revalidate();
    this.repaint();
  }


  /**
   * @param content
   *          the content to set
   */
  public void setContent (final JPanel content)
  {
    this.content = content;
  }


  /**
   * @param forceClose
   *          the forceClose to set
   */
  public void setForceClose (final ActionListener forceClose)
  {
    this.forceClose = forceClose;
  }


  private void setFrameIcon ()
  {
    try {
      setIconImage(new Images("frame.png").getImage());
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
  }


  private void setLookAndFeel ()
  {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (final UnsupportedLookAndFeelException e) {
      try {
        UIManager.setLookAndFeel(UIManager
            .getCrossPlatformLookAndFeelClassName());
      }
      catch (final Exception e1) {
        e1.printStackTrace();
      }
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * @param saveAndClose
   *          the saveAndClose to set
   */
  public void setSaveAndClose (final ActionListener saveAndClose)
  {
    this.saveAndClose = saveAndClose;
  }

}
