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
package misux.main;

import misux.gui.Messages;
import misux.gui.frame.Main;

/**
 * This class will start the program.
 * 
 * @author DSIW
 * 
 */
public class MainMisux
{
  /**
   * main method
   * 
   * @param args
   */
  public static void main (final String[] args)
  {
    try {
      new Init();
    }
    catch (final Exception e) {
      Messages.showErrorMessage(null,
          "Error while initialization of the program.");
    }
    new Main();
  }
}
