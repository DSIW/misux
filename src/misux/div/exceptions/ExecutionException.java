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
package misux.div.exceptions;

/**
 * This exception will thrown, if an external command or program will execute
 * from the class Run
 * 
 * @author DSIW
 * 
 */
public class ExecutionException extends Exception
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates an ExecutionException with the message 'The execution failed.'
   */
  public ExecutionException()
  {
    super("The execution failed.");
  }


  /**
   * Creates an ExecutionException with the message 'The execution of failed.
   * $lt;message$gt;'
   * 
   * @param command
   *          command
   */
  public ExecutionException(final String command)
  {
    this("", command);
  }


  /**
   * Creates an ExecutionException with the message 'The execution of
   * &lt;command&gt; failed. $lt;message$gt;'
   * 
   * @param message
   *          message
   * @param cmd
   *          command
   */
  public ExecutionException(final String message, final String cmd)
  {
    super("The executation of " + cmd + " failed. " + message);
  }
}
