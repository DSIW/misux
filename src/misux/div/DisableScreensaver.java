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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

/**
 * This Class implements a method to disable the screensaver.
 * 
 * @author DSIW
 * 
 */
public class DisableScreensaver
{
  private final Timer timer;


  /**
   * Creates a new timer, which calls the disable screensaver method every ten
   * minutes.
   */
  public DisableScreensaver()
  {
    final int delay = 10 * 60 * 1000; /* 10 minutes */
    timer = new Timer(delay, new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        try {
          disableScreenSaver();
        }
        catch (final AWTException e1) {}
      }
    });
  }


  /**
   * Disables the screensaver
   * 
   * @throws AWTException
   */
  public void disableScreenSaver () throws AWTException
  {
    final Robot r = new Robot();
    // r.waitForIdle();
    r.keyPress(KeyEvent.VK_CONTROL);
    r.keyRelease(KeyEvent.VK_CONTROL);
  }


  /**
   * Starts the timer.
   * 
   * @author DSIW
   */
  public void start ()
  {
    timer.start();
  }


  /**
   * Stops the timer.
   * 
   * @author DSIW
   */
  public void stop ()
  {
    timer.stop();
  }
}
