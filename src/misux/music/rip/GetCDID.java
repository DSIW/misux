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
package misux.music.rip;

import java.io.IOException;

import misux.div.exceptions.ExecutionException;
import misux.io.Run;
import misux.pref.Preferences;

/**
 * Ths class reads and saves the readed CDDBID.
 * 
 * @author DSIW
 * 
 */
public class GetCDID
{
  private String id;
  private String output;

  private String fileName;


  /**
   * Creates a new object and read the name of the CDDBID-get program from the
   * preferences.
   */
  public GetCDID()
  {
    try {
      fileName = Preferences.get().getOption("program.cddiscid")
          .getStringValue();
    }
    catch (final Exception e1) {}
  }


  private String filterID ()
  {
    final int white = output.indexOf(' ');
    if (white < 0) {
      return null;
    }
    return output.substring(0, white);
  }


  /**
   * @return CDDBID as String
   * @throws ExecutionException
   * @throws IOException
   * @throws InterruptedException
   */
  public String getID () throws InterruptedException, IOException,
      ExecutionException
  {
    if (id == null) {
      read();
    }
    return id;
  }


  /**
   * Reads the CDDBID from the CD.
   * 
   * @throws IOException
   * @throws InterruptedException
   * @throws ExecutionException
   */
  private void read () throws InterruptedException, IOException,
      ExecutionException
  {
    final Run run = new Run(fileName);
    run.exec();
    output = run.getOutput();
    id = filterID();
  }


  @Override
  public String toString ()
  {
    try {
      return getID().toString();
    }
    catch (final InterruptedException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final ExecutionException e) {
      e.printStackTrace();
    }
    return null;
  }

  // public void cpBinary () throws InterruptedException, IOException,
  // ExecutionException
  // {
  // String src = path + FILE_SEP + fileName;
  // String path = null;
  // try {
  // path = Preferences.get().getOption("io.config.dir").getStringValue()
  // + FILE_SEP;
  // }
  // catch (Exception e) {}
  // Run cp = new Run("cp", src, path);
  // System.out.println(src);
  // cp.exec();
  // }
}
