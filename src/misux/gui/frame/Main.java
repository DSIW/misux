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
package misux.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import misux.div.DisableScreensaver;
import misux.gui.panel.PlayerPanel;
import misux.gui.panel.PlaylistList;
import misux.gui.panel.PlaylistListPanel;
import misux.gui.panel.Search;
import misux.gui.table.TrackTable;
import misux.gui.table.TrackTableModel;
import misux.music.pl.MainPlaylist;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistImpl;
import misux.music.player.Player;
import misux.music.player.PlayerImpl;
import misux.pref.Preferences;

/**
 * Main window.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class Main extends MyJFrame
{

  /**
   * Window adapter for the dispose method.
   * 
   * @author DSIW
   * 
   */
  public final class Window extends WindowAdapter
  {
    private final JFrame f;


    private Window(final JFrame parent)
    {
      f = parent;
    }


    @Override
    public void windowClosing (final WindowEvent e)
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
  }

  private Player                   player;
  private Search                   search;
  private TrackTable               table;
  private TrackTableModel          model;
  private Playlist                 playlist;
  private List<Playlist>           playlists;
  private final DisableScreensaver disableSreensaver;


  /**
   * Creates a new frame.
   */
  public Main()
  {
    disableSreensaver = new DisableScreensaver();
    disableSreensaver.start();
    String programTitle = "program";
    try {
      programTitle = Preferences.get().getOption("program.name")
          .getStringValue();
    }
    catch (final Exception e) {}
    setTitle(programTitle);
    init();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initFrame();
  }


  private void init ()
  {
    try {
      playlist = MainPlaylist.get();
    }
    catch (final Exception e) {
      playlist = new PlaylistImpl("NEW");
    }
    try {
      player = new PlayerImpl(playlist);
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    playlists = new ArrayList<Playlist>();
    playlists.add(playlist);
    search = new Search();

    model = new TrackTableModel(player);
    table = new TrackTable(player, model);
    new MainMenu(this, playlist);

    final JPanel all = new JPanel(new BorderLayout());

    final JPanel searchPanel = new JPanel(new BorderLayout());
    searchPanel.add(search, BorderLayout.EAST);

    final JPanel up = new JPanel(new BorderLayout());
    // up.add(searchPanel, BorderLayout.NORTH);
    final PlayerPanel playPanel = new PlayerPanel(player, model);
    // System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
    up.add(playPanel, BorderLayout.CENTER);
    setLayout(new BorderLayout());
    all.add(up, BorderLayout.NORTH);
    all.add(new JScrollPane(table), BorderLayout.CENTER);

    final JPanel coverPanel = new JPanel(new BorderLayout());
    final PlaylistList plist = new PlaylistList(playlists);
    final PlaylistListPanel playlistPanel = new PlaylistListPanel(plist);
    coverPanel.add(playlistPanel, BorderLayout.CENTER);
    coverPanel.add(playPanel.getCoverPanel(), BorderLayout.SOUTH);
    all.add(coverPanel, BorderLayout.WEST);

    all.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 5));
    add(all);
    addWindowListener(new Window(this));
  }
}
