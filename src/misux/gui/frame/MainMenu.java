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
package misux.gui.frame;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import misux.music.pl.MainPlaylist;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistListener;
import misux.pref.Preferences;

/**
 * Menu of the main window.
 * 
 * @author DSIW
 * 
 */
public class MainMenu extends JMenuBar
{
  private static final long serialVersionUID = -2514195470467599933L;

  private final JFrame      f;
  private final Playlist    pl;

  private JMenuItem         rip, tags, lyrics;


  /**
   * Creates a new menu.
   * 
   * @param f
   *          main window
   * @param pl
   *          playlist of the main window
   */
  public MainMenu(final JFrame f, final Playlist pl)
  {
    this.f = f;
    this.pl = pl;
    final JMenuBar menubar = new JMenuBar();
    menubar.add(createProgramMenu());
    menubar.add(createPreferencesMenu());
    menubar.add(createAboutMenu());
    f.setJMenuBar(menubar);
    refreshEnableComp();
    addPlaylistListener();
  }


  private void addPlaylistListener ()
  {
    pl.addPlaylistListener(new PlaylistListener()
    {
      @Override
      public void changed ()
      {
        refreshEnableComp();
      }
    });
  }


  private JMenu createAboutMenu ()
  {
    final JMenu ret = new JMenu("Help");
    ret.setMnemonic('H');
    JMenuItem mi;

    String programTitle = "";
    try {
      programTitle = Preferences.get().getOption("program.name")
          .getStringValue();
    }
    catch (final Exception e1) {
      e1.printStackTrace();
    }
    mi = new JMenuItem("About " + programTitle, 'A');
    mi.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        new Info(f);
      }
    });
    mi.setToolTipText("Open informations about this program.");
    ret.add(mi);
    return ret;
  }


  private JMenu createPreferencesMenu ()
  {
    final JMenu ret = new JMenu("Preferences");
    ret.setMnemonic('f');
    JMenuItem mi;

    // Preferences
    mi = new JMenuItem("Preferences", 'P');
    setCtrlAccelerator(mi, 'P');
    mi.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        new Pref(f);
      }
    });
    mi.setToolTipText("Open preferences window.");
    ret.add(mi);
    return ret;
  }


  private JMenu createProgramMenu ()
  {
    final JMenu ret = new JMenu("Program");
    ret.setMnemonic('P');

    JMenuItem mi;

    // Rip
    rip = new JMenuItem("Rip cd", 'R');
    setCtrlAccelerator(rip, 'R');
    rip.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        new Rip(pl);
      }
    });
    rip.setToolTipText("Open rip window.");
    ret.add(rip);

    // Tags
    tags = new JMenuItem("Edit tags", 't');
    setCtrlAccelerator(tags, 'T');
    tags.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        new Tags(f, pl.getActualTrack());
      }
    });
    tags.setToolTipText("Open tag window.");
    ret.add(tags);

    // Lyric
    lyrics = new JMenuItem("Show lyric", 't');
    lyrics.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        new Lyrics(f, pl.getActualTrack());
      }
    });
    lyrics.setToolTipText("Open tag window.");
    ret.add(lyrics);

    // separator
    ret.addSeparator();

    // Quit
    mi = new JMenuItem("Quit", 'Q');
    setCtrlAccelerator(mi, 'Q');
    mi.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        f.dispose();
        try {
          MainPlaylist.write();
        }
        catch (final Exception e1) {
          e1.printStackTrace();
        }
        System.exit(0);
      }
    });
    mi.setToolTipText("Beende Spiel.");
    ret.add(mi);
    return ret;
  }


  private void refreshEnableComp ()
  {
    if (pl.isEmpty()) {
      tags.setEnabled(false);
      lyrics.setEnabled(false);
    } else {
      tags.setEnabled(true);
      lyrics.setEnabled(true);
    }
  }


  private void setCtrlAccelerator (final JMenuItem mi, final char acc)
  {
    final KeyStroke ks = KeyStroke.getKeyStroke(acc, Event.CTRL_MASK);
    mi.setAccelerator(ks);
  }
}