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
package misux.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import misux.div.exceptions.ExecutionException;

/**
 * This class implements methods to run scripts or other system programs or
 * commands or external programs.
 * 
 * @author DSIW
 * 
 */
public class Run
{
  private static String InputStreamToString (final InputStream o)
      throws IOException
  {
    final BufferedReader br = new BufferedReader(new InputStreamReader(o));
    final StringBuffer sb = new StringBuffer();
    String line;
    while ((line = br.readLine()) != null) {
      sb.append(line).append("\n");
    }
    return sb.toString();
  }

  private String            dir;
  private String            cmd;
  private String            output;
  private ArrayList<String> args;

  private boolean           correctRun;

  private boolean           check;


  /**
   * Creates a new object with an empty argument list
   * 
   * @param cmd
   *          command to execute
   */
  public Run(final String cmd)
  {
    this(cmd, new ArrayList<String>());
  }


  /**
   * Creates a new object with parameters
   * 
   * @param cmd
   *          command to execute
   * @param args
   *          arguments for the command
   */
  public Run(final String cmd, final ArrayList<String> args)
  {
    if (cmd.lastIndexOf(File.separator) >= 0) {
      final String[] ar = splitCommand(cmd);
      dir = Run.class
          .getResource(File.separator + "resources" + File.separator)
          .toString()
          + ar[0];
      this.cmd = ar[1];
    } else {
      this.cmd = cmd;
    }
    this.args = args;
    correctRun = false;
    output = null;
    check = false;
  }


  /**
   * Creates a new object with parameters
   * 
   * @param cmd
   *          command to execute
   * @param args
   *          arguments for the command
   */
  public Run(final String cmd, final String... args)
  {
    this(cmd);
    for (final String arg : args) {
      this.args.add(arg);
    }
  }


  Run(final String prg, final String cmd, final boolean check)
  {
    this(prg, cmd);
    this.check = check;
  }


  /**
   * Check, if the command/program is installed.
   * 
   * @return true, if installed
   * @author DSIW
   */
  public boolean checkCommand ()
  {
    final Run which = new Run("which", cmd, true);
    try {
      which.exec();
    }
    catch (final Exception e) {
      return false;
    }
    if (which.wasCorrectRun()) {
      return true;
    }
    return false;
  }


  private ProcessBuilder createProcessBuilder () throws ExecutionException
  {
    ProcessBuilder pb;
    if (!args.isEmpty()) {
      final ArrayList<String> command = new ArrayList<String>();
      command.add(cmd);
      command.addAll(args);
      pb = new ProcessBuilder(command);
    } else {
      pb = new ProcessBuilder(cmd);
    }
    if (!check) {
      if (!checkCommand()) {
        throw new ExecutionException("The command does not exist", "");
      }
    }
    if (dir != null) {
      pb.directory(new File(dir));
    }
    return pb;
  }


  /**
   * Executes an external binary (command/program) in the set directory.
   * 
   * @throws InterruptedException
   * @throws IOException
   * @throws ExecutionException
   */
  public void exec () throws InterruptedException, IOException,
      ExecutionException
  {
    final Process p = createProcessBuilder().start();
    p.waitFor();

    output = Run.InputStreamToString(p.getInputStream());
    correctRun = p.exitValue() == 0 ? true : false;
    if (!correctRun) {
      throw new ExecutionException(cmd + " ERRORSTREAM: '"
          + Run.InputStreamToString(p.getErrorStream()) + "'");
    }
  }


  /**
   * @return the arguments of the command
   */
  public ArrayList<String> getArgs ()
  {
    return args;
  }


  /**
   * @return the command
   */
  public String getCmd ()
  {
    return cmd;
  }


  /**
   * @return the directory
   */
  public String getDir ()
  {
    return dir;
  }


  /**
   * @return the output
   */
  public String getOutput ()
  {
    return output;
  }


  /**
   * @param args
   *          the arguments to set
   */
  public void setArgs (final ArrayList<String> args)
  {
    this.args = args;
  }


  /**
   * @param cmd
   *          the command to set
   */
  public void setCmd (final String cmd)
  {
    this.cmd = cmd;
  }


  /**
   * @param dir
   *          the direcotory to set
   */
  public void setDir (final String dir)
  {
    this.dir = dir;
  }


  private String[] splitCommand (final String cmd)
  {
    if (cmd.isEmpty()) {
      return new String[2];
    }
    final int splitIndex = cmd.lastIndexOf(File.separator);
    final String[] ar = new String[2];
    ar[0] = cmd.substring(0, splitIndex);
    ar[1] = cmd.substring(splitIndex + 1, cmd.length());
    return ar;
  }


  /**
   * @return true, if the program ended correct
   */
  public boolean wasCorrectRun ()
  {
    return correctRun;
  }
}
