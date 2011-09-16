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
misux - musicplayer (written in Java)
Copyright (C) 2011  DSIW <dsiw@privatdemail.net>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
package misux.io;

import java.io.IOException;

import misux.div.exceptions.ExecutionException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class RunTest
{

  /**
   * Tests method exec
   */
  @Test
  public void testExec ()
  {
    final Run run = new Run("ls", "-l");
    run.setDir("/home/max/");
    try {
      run.exec();
      System.out.println(run.getOutput().split("\n").length);
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
    Assert.assertTrue(run.wasCorrectRun());
  }


  /**
   * Tests method getCmd
   */
  @Test
  public void testGetCmd ()
  {
    final Run run = new Run("eject");
    Assert.assertTrue(run.getCmd().equalsIgnoreCase("eject"));
    try {
      run.exec();
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
  }


  /**
   * Tests method getOutput without output
   */
  @Test
  public void testGetOutput ()
  {
    final Run run = new Run("ls", "-lh");
    run.setDir("/home/max");
    try {
      run.exec();
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
    System.out.println(run.getOutput());
    Assert.assertTrue(!run.getOutput().equalsIgnoreCase(""));
  }


  /**
   * Tests method getOutput with output
   */
  @Test
  public void testGetOutput2 ()
  {
    final Run run = new Run("pwd");
    try {
      run.exec();
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
    Assert.assertTrue(run.getOutput().length() > 0);
  }


  /**
   * Tests method wasCorrectRun
   */
  @Test
  public void testWasCorrectRun ()
  {
    final Run run = new Run("pwd");
    try {
      run.exec();
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
    Assert.assertTrue(run.wasCorrectRun());
  }

  /**
   * Tests method execLocal
   */
  // @Test
  // public void testExecLocal ()
  // {
  // Run run = new Run("bin/eject");
  // try {
  // run.execLocal();
  // }
  // catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // catch (IOException e) {
  // e.printStackTrace();
  // }
  // assertTrue(run.wasCorrectRun());
  // }

}
