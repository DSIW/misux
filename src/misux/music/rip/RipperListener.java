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
package misux.music.rip;

import java.util.List;

/**
 * This interface implements methods, which called from the Ripper.
 * 
 * @author DSIW
 * 
 */
public interface RipperListener
{
  /**
   * This method will called, if the rip process ended.
   * 
   * @param rippingOK
   *          true, if the rip process was successful
   * @author DSIW
   */
  void ended (boolean rippingOK);


  /**
   * This method will called, if the rip process started.
   * 
   * @param trackNumbers
   *          track list, which will ripped.
   * @author DSIW
   */
  void started (List<Integer> trackNumbers);
}
