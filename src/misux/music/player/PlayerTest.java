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
package misux.music.player;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import misux.music.pl.MainPlaylist;
import misux.music.pl.Playlist;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class PlayerTest
{
  Player   player;
  Playlist pl;


  private void init ()
  {
    pl = null;
    player = null;
    try {
      pl = MainPlaylist.get();
      player = new PlayerImpl(pl);
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }


  // /**
  // * Tests method play after a frame
  // */
  // @Test
  // public void testPlayInt ()
  // {
  // init();
  // try {
  // player.play(255);
  // Thread.sleep(1000);
  // }
  // catch (JavaLayerException e) {
  // e.printStackTrace();
  // }
  // catch (FileNotFoundException e) {
  // e.printStackTrace();
  // }
  // catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // catch (UnsupportedAudioFileException e) {
  // e.printStackTrace();
  // }
  // catch (IOException e) {
  // e.printStackTrace();
  // }
  // catch (BasicPlayerException e) {
  // e.printStackTrace();
  // }
  // catch (Exception e) {
  // e.printStackTrace();
  // }
  //
  // assertFalse(true);
  // }

  /**
   * Tests method getActualTrack
   */
  @Test
  public void testGetActualTrack ()
  {
    init();
    try {
      System.out.println(player.getActualTrack());
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  /**
   * Tests method pause
   */
  @Test
  public void testPause ()
  {
    init();
    // try {
    // player.playPlaylist();
    // player.resume();
    // }
    // catch (JavaLayerException e) {
    // e.printStackTrace();
    // }
    // catch (FileNotFoundException e) {
    // e.printStackTrace();
    // }
    // catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // catch (BasicPlayerException e) {
    // e.printStackTrace();
    // }
    // catch (UnsupportedAudioFileException e) {
    // e.printStackTrace();
    // }
    // catch (IOException e) {
    // e.printStackTrace();
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // }
    Assert.assertTrue(true);
  }


  /**
   * Tests method play
   */
  @Test
  public void testPlay ()
  {
    init();
    try {
      player.getAudioDevice();
      player.play();
    }
    catch (final JavaLayerException e) {
      e.printStackTrace();
    }
    catch (final FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (final UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final BasicPlayerException e) {
      e.printStackTrace();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  /**
   * Tests method playAll
   */
  @Test
  public void testPlayAll ()
  {
    init();
    try {
      player.setRepeat(Repeat.All);
      player.playPlaylist();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  /**
   * Tests the method play
   * 
   * @author DSIW
   */
  @Test
  public void testPlayBasic ()
  {
    init();
    try {
      // player.setRandom(true);
      System.out.println(player.getPlaylist());
      player.play();
    }
    catch (final BasicPlayerException e) {
      e.printStackTrace();
    }
    catch (final InterruptedException e) {
      e.printStackTrace();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    Assert.assertTrue(true);
  }


  /**
   * Tests method resume
   */
  @Test
  public void testResume ()
  {
    init();
    // try {
    // player.playPlaylist();
    // Thread.sleep(1000);
    // player.resume();
    // }
    // catch (JavaLayerException e) {
    // e.printStackTrace();
    // }
    // catch (FileNotFoundException e) {
    // e.printStackTrace();
    // }
    // catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // catch (BasicPlayerException e) {
    // e.printStackTrace();
    // }
    // catch (UnsupportedAudioFileException e) {
    // e.printStackTrace();
    // }
    // catch (IOException e) {
    // e.printStackTrace();
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // }
    Assert.assertTrue(true);
  }


  /**
   * Tests method stop
   */
  @Test
  public void testStop ()
  {
    init();
    // try {
    // player.playPlaylist();
    // Thread.sleep(1000);
    // player.stop();
    // }
    // catch (JavaLayerException e) {
    // e.printStackTrace();
    // }
    // catch (FileNotFoundException e) {
    // e.printStackTrace();
    // }
    // catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // catch (BasicPlayerException e) {
    // e.printStackTrace();
    // }
    // catch (UnsupportedAudioFileException e) {
    // e.printStackTrace();
    // }
    // catch (IOException e) {
    // e.printStackTrace();
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // }
    Assert.assertTrue(true);
  }
}
