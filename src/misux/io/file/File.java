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
package misux.io.file;

import java.io.IOException;
import java.util.ArrayList;

import misux.div.exceptions.ExecutionException;
import misux.io.Run;
import misux.pref.Preferences;

/**
 * This class implements methods to write a file into the filesystem. This file
 * has a name and content.
 * 
 * @author DSIW
 * 
 */
/**
 * @author DSIW
 * 
 */
public abstract class File
{
  private String  fileName;
  private String  content;
  private boolean overwrite;
  private boolean executable;


  /**
   * Creates a new File with the name and content.
   * 
   * @throws Exception
   */
  public File() throws Exception
  {
    executable = false;
    overwrite = false;
    setFileName(createFileName());
    setContent(createContent());
  }


  /**
   * @return the content of the file
   * @throws Exception
   * @author DSIW
   */
  public abstract String createContent () throws Exception;


  /**
   * @return the name of the file.
   * @throws Exception
   * @author DSIW
   */
  public abstract String createFileName () throws Exception;


  /**
   * @return the content
   */
  public String getContent ()
  {
    return content;
  }


  /**
   * @return the file name
   */
  public String getFileName ()
  {
    return fileName;
  }


  /**
   * @return the executable
   */
  public boolean isExecutable ()
  {
    return executable;
  }


  /**
   * @return the overwrite
   */
  public boolean isOverwrite ()
  {
    return overwrite;
  }


  /**
   * Writes the file with the name and the content into the filesystem. If the
   * file also exists it will be overwritten.
   * 
   * @throws Exception
   *           will thrown, if the file name or the content is empty.
   */
  public void make () throws Exception
  {
    if (getFileName() == null || getFileName().equals("")
        || getContent() == null || getContent().equals("")) {
      throw new IOException();
    }

    // Overwrite if necessary
    final java.io.File file = new java.io.File(getFileName());
    if (overwrite && file.exists()) {
      mvToTrash();
    } else if (file.exists()) {
      throw new Exception("File already exists.");
    }
    FileHandler.writeString(getContent(), file);

    if (executable) {
      makeExecutable();
    }
  }


  /**
   * Sets the file executable.
   * 
   * @throws InterruptedException
   * @throws IOException
   * @throws ExecutionException
   */
  private void makeExecutable () throws InterruptedException, IOException,
      ExecutionException
  {
    // Hack: can't set executable and read-only with internal java methods
    new Run("chmod", "u+x", getFileName()).exec();
    new Run("chmod", "-w", getFileName()).exec();
  }


  private void mvToTrash () throws Exception
  {
    // move to trash-folder (like remove)
    final ArrayList<String> args = new ArrayList<String>();
    args.add(getFileName());
    args.add(Preferences.get().getOption("io.user.trashdir").getStringValue()
        + java.io.File.separator);
    new Run("mv", args).exec();
  }


  /**
   * Sets the content of the file.
   * 
   * @param content
   *          content of the file
   */
  public void setContent (final String content)
  {
    this.content = content;
  }


  /**
   * @param executable
   *          the executable to set
   */
  public void setExecutable (final boolean executable)
  {
    this.executable = executable;
  }


  /**
   * Sets the name of the file.
   * 
   * @param fileName
   *          name of the file
   */
  public void setFileName (final String fileName)
  {
    this.fileName = fileName;
  }


  /**
   * @param overwrite
   *          the overwrite to set
   */
  public void setOverwrite (final boolean overwrite)
  {
    this.overwrite = overwrite;
  }

}
