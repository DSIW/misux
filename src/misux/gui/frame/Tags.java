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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import misux.div.Images;
import misux.gui.IntTextField;
import misux.gui.Messages;
import misux.io.http.cover.Cover;
import misux.music.Encoding;
import misux.music.Genre;
import misux.music.license.Copyleft;
import misux.music.license.Copyright;
import misux.music.license.CreativeCommons;
import misux.music.license.FreeMusic;
import misux.music.license.License;
import misux.music.pl.MainPlaylist;
import misux.music.track.Track;

/**
 * This is a dialoge to change tags and information of a track. You also can
 * select a cover for this track.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class Tags extends JDialog
{
  private JButton         cancelButton;
  private JButton         chooseOffline;
  private JButton         chooseOnline;
  private JComboBox       genre;
  private JComboBox       license;
  private JComboBox       rating;
  private JLabel          TITLE;
  private JLabel          PATH;
  private JTextField      pathToMusic;
  private JLabel          ENCODING;
  private JLabel          enc;
  private JLabel          INTERPRET;
  private JLabel          ALBUM;
  private JLabel          GENRE;
  private JLabel          YEAR;
  private JLabel          LICENSE;
  private JLabel          RATING;
  private JLabel          COVER;
  private JLabel          coverImage;
  private JTextField      title;
  private JTextField      interpret;
  private JTextField      album;
  private IntTextField    year;
  private JButton         okButton;
  private final Frame     parent;

  // private Playlist pl;
  Track                   t;
  private final License[] licenses;

  private int             returnStatus = Tags.RET_CANCEL;
  /** A return status code - returned if Cancel button has been pressed */
  public static final int RET_CANCEL   = 0;
  /** A return status code - returned if OK button has been pressed */
  public static final int RET_OK       = 1;


  /**
   * Creates a new dialoge.
   * 
   * @param parent
   *          parent of the dialoge
   * @param t
   *          track, which will be changed.
   */
  public Tags(final Frame parent, final Track t)
  {
    super(parent, true);
    try {
      setIconImage(new Images("frame.png").getImage());
    }
    catch (final IOException e) {}
    this.parent = parent;
    licenses = new License[] { new Copyright(), new Copyleft(),
        new CreativeCommons(), new FreeMusic() };
    // this.pl = pl;
    this.t = t;
    initComponents();
  }


  private void cancelButtonActionPerformed (final ActionEvent evt)
  {
    doClose(Tags.RET_CANCEL);
  }


  private void chooseOfflineActionPerformed (final ActionEvent e)
  {
    final CoverChooser cs = new CoverChooser(this);
    if (cs.getCoverPath() != null) {
      t.setPathToCover(cs.getCoverPath());
      loadCover(null);
    }
  }


  private void chooseOnlineActionPerformed (final ActionEvent e)
  {
    final CoverSwitch cs = new CoverSwitch(t, parent, 10);
    if (cs.getSelectedCover() != null) {
      // pl.getActualTrack().setPathToCover(cs.getSelectedCoverPath());
      loadCover(cs.getSelectedCover());
    }
  }


  /** Closes the dialog */
  private void closeDialog (final WindowEvent evt)
  {
    doClose(Tags.RET_CANCEL);
  }


  private void doClose (final int retStatus)
  {
    returnStatus = retStatus;
    setVisible(false);
    dispose();
  }


  /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
  public int getReturnStatus ()
  {
    return returnStatus;
  }


  private Genre getSelectedGenre ()
  {
    final String selectedGenre = (String) genre.getModel().getSelectedItem();
    return Genre.value(selectedGenre);
  }


  private License getSelectedLicense ()
  {
    final String selectedLicense = ((License) license.getModel()
        .getSelectedItem()).getName();
    return License.parseName(selectedLicense);
  }


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  private void initComponents ()
  {
    okButton = new JButton();
    cancelButton = new JButton();
    TITLE = new JLabel();
    INTERPRET = new JLabel();
    title = new JTextField();
    interpret = new JTextField();
    album = new JTextField();
    year = new IntTextField(4);
    ALBUM = new JLabel();
    GENRE = new JLabel();
    YEAR = new JLabel();
    LICENSE = new JLabel();
    RATING = new JLabel();
    COVER = new JLabel();
    genre = new JComboBox();
    chooseOffline = new JButton();
    chooseOnline = new JButton();
    coverImage = new JLabel();
    license = new JComboBox();
    rating = new JComboBox();
    PATH = new JLabel();
    pathToMusic = new JTextField();
    ENCODING = new JLabel();
    enc = new JLabel();

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing (final WindowEvent evt)
      {
        closeDialog(evt);
      }
    });

    okButton.setText("Save");
    okButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        saveButtonActionPerformed(evt);
      }
    });

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        cancelButtonActionPerformed(evt);
      }
    });

    // Track t = pl.getActualTrack();
    TITLE.setText("Title");
    INTERPRET.setText("Interpret");
    title.setText(t.getTitle());
    interpret.setText(t.getInterpret());
    album.setText(t.getAlbum());
    year.setText(t.getYear() == 0 ? "" : t.getYear() + "");
    ALBUM.setText("Album");
    GENRE.setText("Genre");
    YEAR.setText("Year");
    LICENSE.setText("License");
    RATING.setText("Rating");
    COVER.setText("Cover");

    final Genre[] genres = Genre.values();
    final String[] strGenres = new String[genres.length];
    for (int g = 0; g < strGenres.length; g++) {
      strGenres[g] = genres[g].name();
    }
    Arrays.sort(strGenres);
    genre.setModel(new DefaultComboBoxModel(strGenres));
    genre.getModel().setSelectedItem(t.getGenre().name());

    coverImage.setHorizontalAlignment(SwingConstants.CENTER);
    coverImage.setPreferredSize(new Dimension(210, 210));
    loadCover(null);

    chooseOffline.setText("Offline...");
    chooseOffline.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        chooseOfflineActionPerformed(e);
      }
    });

    chooseOnline.setText("Online...");
    chooseOnline.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        chooseOnlineActionPerformed(e);
      }
    });

    license.setModel(new DefaultComboBoxModel(licenses));
    license.getModel().setSelectedItem(t.getLicense());

    rating.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "2", "3",
        "4", "5" }));
    rating.getModel().setSelectedItem(t.getRating() + "");
    PATH.setText("Path");

    pathToMusic.setEditable(false);
    pathToMusic.setText(t.getPathToMusic());
    final Dimension pathSize = new Dimension(480, pathToMusic.getHeight());
    pathToMusic.setPreferredSize(pathSize);

    ENCODING.setText("Encoding");
    enc.setText(t.getEnc().toString());

    final GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout
        .setHorizontalGroup(layout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(TITLE).addComponent(INTERPRET)
                            .addComponent(ALBUM).addComponent(GENRE)
                            .addComponent(YEAR).addComponent(COVER)
                            .addComponent(LICENSE).addComponent(RATING)
                            .addComponent(PATH).addComponent(ENCODING))
                    .addGap(74, 74, 74)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                GroupLayout.Alignment.LEADING)
                                            .addComponent(enc)
                                            .addComponent(pathToMusic)
                                            .addComponent(license, 0, 211,
                                                Short.MAX_VALUE)
                                            .addComponent(rating, 0, 211,
                                                Short.MAX_VALUE))
                                    .addContainerGap())
                            .addGroup(
                                GroupLayout.Alignment.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                GroupLayout.Alignment.TRAILING)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        okButton,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        67,
                                                        GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(cancelButton))
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        coverImage,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        122, Short.MAX_VALUE)
                                                    .addPreferredGap(
                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                            .addComponent(
                                                                chooseOnline,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                            .addComponent(
                                                                chooseOffline,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)))
                                            .addComponent(title,
                                                GroupLayout.Alignment.LEADING,
                                                GroupLayout.DEFAULT_SIZE, 211,
                                                Short.MAX_VALUE)
                                            .addComponent(interpret,
                                                GroupLayout.Alignment.LEADING,
                                                GroupLayout.DEFAULT_SIZE, 211,
                                                Short.MAX_VALUE)
                                            .addComponent(album,
                                                GroupLayout.Alignment.LEADING,
                                                GroupLayout.DEFAULT_SIZE, 211,
                                                Short.MAX_VALUE)
                                            .addComponent(genre,
                                                GroupLayout.Alignment.LEADING,
                                                0, 211, Short.MAX_VALUE)
                                            .addComponent(year,
                                                GroupLayout.Alignment.LEADING,
                                                GroupLayout.DEFAULT_SIZE, 211,
                                                Short.MAX_VALUE))
                                    .addContainerGap()))));

    layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { cancelButton,
        okButton });

    layout
        .setVerticalGroup(layout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(TITLE)
                            .addComponent(title, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(INTERPRET)
                            .addComponent(interpret,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(album, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE).addComponent(ALBUM))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(GENRE)
                            .addComponent(genre, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(year, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE).addComponent(YEAR))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(LICENSE)
                            .addComponent(license, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(RATING)
                            .addComponent(rating, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(COVER)
                            .addComponent(coverImage,
                                coverImage.getPreferredSize().width,
                                coverImage.getPreferredSize().height,
                                GroupLayout.PREFERRED_SIZE)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(chooseOffline)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(chooseOnline)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(PATH).addComponent(pathToMusic))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(ENCODING).addComponent(enc))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton).addComponent(okButton))
                    .addContainerGap()));

    setPreferredSize(new Dimension(650, 640));
    pack();
    setVisible(true);
  }


  private void loadCover (final Cover cover)
  {
    // Track t = pl.getActualTrack();
    if (t.getPathToCover() != null && !t.getPathToCover().isEmpty()
        && new File(t.getPathToCover()).canRead()) {
      if (cover != null) {
        coverImage.setIcon(Images.toImageIcon(cover.getImage()));
      } else {
        coverImage.setIcon(new ImageIcon(t.getPathToCover()));
      }
      coverImage.setText("");
    } else if (t.getEnc() != Encoding.NO) {
      coverImage.setIcon(Images.loadLocalIcon(t.getEnc().toString()
          .toLowerCase()
          + ".png"));
      coverImage.setText("");
      t.setPathToCover("");
    } else {
      coverImage.setIcon(null);
      coverImage.setText("[COVER]");
      t.setPathToCover("");
    }
  }


  private void saveButtonActionPerformed (final ActionEvent evt)
  {
    short error = 0;
    try {
      t.setTitle(title.getText());
      t.setAlbum(album.getText());
      t.setInterpret(interpret.getText());
      t.setYear(Integer.parseInt("0" + year.getText()));
      t.setLicense(getSelectedLicense());
      t.setGenre(getSelectedGenre());
      t.setRating(Integer
          .parseInt((String) rating.getModel().getSelectedItem()));
    }
    catch (final Exception e) {
      error = 1;
    }
    try {
      if (error == 0) {
        MainPlaylist.write();
      }
    }
    catch (final Exception e) {
      error = 1;
    }
    if (error == 0) {
      // pl.firePlaylistChanged();
      doClose(Tags.RET_OK);
    } else {
      Messages.showErrorMessage(this, "It occurres an error.");
    }
  }
}
