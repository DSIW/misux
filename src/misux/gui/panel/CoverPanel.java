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
package misux.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import misux.div.Images;
import misux.io.http.cover.PictureExtensions;
import misux.music.Encoding;
import misux.music.pl.Playlist;
import misux.music.track.Track;
import misux.pref.Preferences;

/**
 * This is a panel, which show the cover of the playing track.
 * 
 * @author DSIW
 * 
 */
@SuppressWarnings("serial")
public class CoverPanel extends JPanel
{
  private final JLabel label = new JLabel();
  private Playlist     pl    = null;


  /**
   * Creates a new panel.
   */
  public CoverPanel()
  {
    this((Playlist) null);
  }


  /**
   * Creates a new panel.
   * 
   * @param pl
   *          playlist to get the playing track
   */
  public CoverPanel(final Playlist pl)
  {
    this.pl = pl;
    initLabel();
    refreshCover();
  }


  private void initLabel ()
  {
    final int BORDER = 6;
    this.setLayout(new BorderLayout());
    int coverSize = 0;
    try {
      coverSize = Preferences.get().getOption("io.http.cover.size")
          .getIntegerValue();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    label.setOpaque(true);
    label
        .setPreferredSize(new Dimension(coverSize + BORDER, coverSize + BORDER));

    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setBackground(Color.WHITE);
    label.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK),
        BorderFactory.createLineBorder(Color.WHITE, 5)));

    this.add(label, BorderLayout.CENTER);
  }


  private void loadCover ()
  {
    Track actualTrack = null;
    if (pl != null && !pl.isEmpty()) {
      actualTrack = pl.getActualTrack();
    }

    // no track loaded
    if (actualTrack == null) {
      try {
        int format = Preferences.get().getOption("io.music.rip.format")
            .getIntegerValue();
        if (format < 0 || format >= Encoding.values().length) {
          format = 0;
        }
        final Encoding enc = Encoding.values()[format];
        loadPNGIcon(enc.toString());
      }
      catch (final Exception e) {
        e.printStackTrace();
      }
      return;
    }

    // load cover
    final File cover = new File(actualTrack.getPathToCover());
    if (cover.canRead()) {
      label.setIcon(Images.toImageIcon(actualTrack.getCover().getImage()));
    } else {
      final String musicType = actualTrack.getEnc().toString();
      loadPNGIcon(musicType);
    }
    label.setToolTipText(actualTrack.getInterpret() + "_"
        + actualTrack.getAlbum());
  }


  private void loadPNGIcon (final String enc)
  {
    final String PNG = PictureExtensions.PNG.toString().toLowerCase();
    label.setIcon(Images.loadLocalIcon(enc.toLowerCase() + "." + PNG));
  }


  /**
   * Refreshs the cover. It will be loaded from the filesystem.
   * 
   * @author DSIW
   */
  public void refreshCover ()
  {
    loadCover();
    revalidate();
  }


  /**
   * Sets the playlist to get the playing track.
   * 
   * @param pl
   *          playlist
   * @author DSIW
   */
  public void setPlaylist (final Playlist pl)
  {
    this.pl = pl;
  }
}
