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
package misux.music.pl;

import java.util.LinkedList;
import java.util.List;

import misux.music.Encoding;
import misux.music.track.Track;
import misux.music.track.TrackImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * @author DSIW
 * 
 */
public class PlaylistTest
{

  /**
   * Tests the method add
   * 
   * @author DSIW
   */
  @Test
  public void testAdd ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    Assert.assertTrue(pl.getLength() == 2);
  }


  /**
   * Tests the method cleanUpIndex
   * 
   * @author DSIW
   */
  @Test
  public void testCleanUpIndex ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(4, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(24, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "no", Encoding.MP3));
    pl.add(new TrackImpl(20, "jojo", "alb", "no", Encoding.MP3));
    pl.cleanUpIndex();
    System.out.println(pl);
    final Playlist res = new PlaylistImpl("test");
    res.add(new TrackImpl(1, "third", "alb", "no", Encoding.MP3));
    res.add(new TrackImpl(2, "first", "alb", "interp", Encoding.MP3));
    res.add(new TrackImpl(3, "jojo", "alb", "no", Encoding.MP3));
    res.add(new TrackImpl(4, "sec", "alb", "interp", Encoding.MP3));
    Assert.assertTrue(pl.equals(res));
  }


  /**
   * Tests the method contains
   * 
   * @author DSIW
   */
  @Test
  public void testContains ()
  {
    final Track cont = new TrackImpl(3, "third", "alb", "no", Encoding.MP3);
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    Assert.assertTrue(!pl.contains(cont));
    pl.add(cont);
    Assert.assertTrue(pl.contains(cont));
  }


  /**
   * Tests the method copy
   * 
   * @author DSIW
   */
  @Test
  public void testCopy ()
  {
    System.out.println("testCopy");
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    System.out.println(pl);
    final Playlist mancopy = new PlaylistImpl("test");
    mancopy.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    mancopy.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    mancopy.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    System.out.println(mancopy);
    Assert.assertTrue(pl.copy().equals(mancopy));
  }


  /**
   * Tests the method equals
   * 
   * @author DSIW
   */
  @Test
  public void testEquals ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(4, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(24, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "no", Encoding.MP3));
    pl.add(new TrackImpl(20, "jojo", "alb", "no", Encoding.MP3));
    final Playlist fals = new PlaylistImpl("test");
    fals.add(new TrackImpl(1, "third", "alb", "no", Encoding.MP3));
    fals.add(new TrackImpl(2, "first", "alb", "interp", Encoding.MP3));
    fals.add(new TrackImpl(3, "jojo", "alb", "no", Encoding.MP3));
    fals.add(new TrackImpl(4, "sec", "alb", "interp", Encoding.MP3));
    Assert.assertTrue(!pl.equals(fals));
    final Playlist ok = pl.copy();
    Assert.assertTrue(pl.equals(ok));
  }


  /**
   * Tests the method getAlbums
   * 
   * @author DSIW
   */
  @Test
  public void testGetAlbums ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "labum", "tester", Encoding.MP3));
    System.out.println(pl.getAlbums());
    final List<String> res = new LinkedList<String>();
    res.add("labum");
    res.add("alb");
    System.out.println(res);
    Assert.assertTrue(pl.getAlbums().containsAll(res));
  }


  /**
   * Tests the method getInterprets
   * 
   * @author DSIW
   */
  @Test
  public void testGetInterprets ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "tester", Encoding.MP3));
    System.out.println(pl.getInterprets());
    final List<String> res = new LinkedList<String>();
    res.add("tester");
    res.add("interp");
    System.out.println(res);
    Assert.assertTrue(pl.getInterprets().containsAll(res));
  }


  /**
   * Tests the method getRandomized
   * 
   * @author DSIW
   */
  @Test
  public void testGetRandomized ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "no", Encoding.MP3));
    final Playlist randomized = pl.getRandomized();
    System.out.println(randomized);
    Assert.assertTrue(!pl.equals(randomized));
  }


  /**
   * Tests the method isEmpty
   * 
   * @author DSIW
   */
  @Test
  public void testIsEmpty ()
  {
    final Playlist pl = new PlaylistImpl("test");
    Assert.assertTrue(pl.isEmpty());
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    Assert.assertTrue(!pl.isEmpty());
    pl.remove(0);
    Assert.assertTrue(pl.isEmpty());
  }


  /**
   * Tests the method moveTrackDown
   * 
   * @author DSIW
   */
  @Test
  public void testMoveTrackDown ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.moveTrackDown(0);
    System.out.println("testMoveTrackDown");
    System.out.println(pl);
    Assert.assertTrue(pl.getTrack(0).getTitle().equals("sec")
        && pl.getTrack(1).getTitle().equals("first")
        && pl.getTrack(2).getTitle().equals("third"));
  }


  /**
   * Tests the method moveTrackUp
   * 
   * @author DSIW
   */
  @Test
  public void testMoveTrackUp ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.moveTrackUp(2);
    System.out.println("testMoveTrackUp");
    System.out.println(pl);
    Assert.assertTrue(pl.getTrack(0).getTitle().equals("first")
        && pl.getTrack(1).getTitle().equals("third")
        && pl.getTrack(2).getTitle().equals("sec"));
  }


  /**
   * Tests the method moveTrackUp
   * 
   * @author DSIW
   */
  @Test
  public void testMoveTrackUpFirst ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.moveTrackUp(1);
    System.out.println(pl);
    Assert.assertTrue(pl.getTrack(0).getIndex() == 1);
  }


  /**
   * Tests the method next
   * 
   * @author DSIW
   */
  @Test
  public void testNext ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.next();
    Assert.assertTrue(pl.getActualTrack().getIndex() == 1);
    pl.next();
    Assert.assertTrue(pl.getActualTrack().getIndex() == 2);
    pl.next();
    Assert.assertTrue(pl.getActualTrack().getIndex() == 1);
  }


  /**
   * Tests the method prev
   * 
   * @author DSIW
   */
  @Test
  public void testPrev ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    for (int i = 0; i < pl.getLength() - 1; i++) {
      pl.next();
    }
    System.out.println(pl.getActualTrack());
    Assert.assertTrue(pl.getActualTrack().getIndex() == 3);
    pl.prev();
    Assert.assertTrue(pl.getActualTrack().getIndex() == 2);
    pl.prev();
    Assert.assertTrue(pl.getActualTrack().getIndex() == 1);
    pl.prev();
    Assert.assertTrue(pl.getActualTrack().getIndex() == 3);
  }


  /**
   * Tests the method remove
   * 
   * @author DSIW
   */
  @Test
  public void testRemove ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.remove(1);
    Assert.assertTrue(pl.getLength() == 1
        && pl.getTrack(0).getTitle().equalsIgnoreCase("first"));
  }


  /**
   * Tests the method search
   * 
   * @author DSIW
   */
  @Test
  public void testSearch ()
  {
    Assert.fail("Not yet implemented"); // TODO test search
  }


  /**
   * Tests the method searchAlbum
   * 
   * @author DSIW
   */
  @Test
  public void testSearchAlbum ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "labum", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "labum", "tester", Encoding.MP3));
    final Playlist searched = pl.searchAlbum("alb");
    System.out.println(searched);
    Assert.assertTrue(searched.getLength() == 2);
  }


  /**
   * Tests the method searchInterpret
   * 
   * @author DSIW
   */
  @Test
  public void testSearchInterpret ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "no", Encoding.MP3));
    final Playlist searched = pl.searchInterpret("interp");
    System.out.println(searched);
    Assert.assertTrue(searched.getLength() == 2
        && searched.getTrack(0).getTitle().equalsIgnoreCase("first"));
  }


  /**
   * Tests the method searchTitle
   * 
   * @author DSIW
   */
  @Test
  public void testSearchTitle ()
  {
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    final Playlist searched = pl.searchTitle("first");
    Assert.assertTrue(searched.getLength() == 1
        && searched.getTrack(0).getTitle().equalsIgnoreCase("first"));
  }


  /**
   * Tests the method sort
   * 
   * @author DSIW
   */
  @Test
  public void testSort ()
  {
    System.out.println("testSort");
    final Playlist pl = new PlaylistImpl("test");
    pl.add(new TrackImpl(1, "first", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(4, "fourth", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(3, "third", "alb", "interp", Encoding.MP3));
    pl.add(new TrackImpl(2, "sec", "alb", "interp", Encoding.MP3));
    System.out.println(pl);
    pl.sort();
    System.out.println(pl);
    boolean ok = true;
    for (int t = 0; t < pl.getLength(); t++) {
      final Track track = pl.getTrack(t);
      if (track.getIndex() != t + 1) {
        ok = false;
      }
    }
    Assert.assertTrue(ok);
  }
}
