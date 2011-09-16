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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import misux.div.Images;
import misux.gui.IntTextField;
import misux.gui.Messages;
import misux.io.http.cover.Cover;
import misux.io.http.cover.GetCover;
import misux.io.http.cover.GetCoverAmazon;
import misux.io.http.cover.GetCoverGoogle;
import misux.music.track.Track;
import misux.pref.Preferences;

/**
 * This is a dialoge to switch between downloaded covers. The user can select
 * one.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class CoverSwitch extends JDialog
{
  private JButton                        prevButton;
  private JButton                        nextButton;
  private JButton                        cancelButton;
  private JButton                        saveButton;
  private JComboBox                      sources;
  private JLabel                         coverImage;
  private JLabel                         SIZE;
  private JLabel                         PX;
  private JTextField                     coverName;
  private JLabel                         SOURCE;
  private JLabel                         NAME;
  private IntTextField                   size;
  private JMenuBar                       menu;
  private JMenuItem                      save;
  private JMenuItem                      cancel;
  private JMenuItem                      refresh;

  private final String                   TITLE;
  private int                            covernr;
  private int                            maxCovers;
  private int                            sourcenr;
  private List<Cover>                    covers;
  private SwingWorker<List<Cover>, Void> getCoverWorker;
  Track                                  t;
  private Cover                          selectedCover;
  private final int                      maxCountCovers = 10;


  /**
   * Creates a new dialoge.
   * 
   * @param t
   *          track information for the search and cover
   * @param parent
   *          parent of the dialoge
   * @param countCovers
   *          maximum count of images for search
   */
  public CoverSwitch(final Track t, final Frame parent, final int countCovers)
  {
    super(parent, true);
    try {
      setIconImage(new Images("frame.png").getImage());
    }
    catch (final IOException e) {}
    // this.pl = pl;
    this.t = t;
    TITLE = "Cover download";
    maxCovers = countCovers;
    initComponents();
    sourcesActionPerformed(null);
    setVisible(true);
  }


  private void activateComp ()
  {
    coverImage.setText(null);
    saveButton.setEnabled(true);
    save.setEnabled(true);
    refresh.setEnabled(true);
    size.setEnabled(true);
    nextButton.setEnabled(true);
    prevButton.setEnabled(true);
    comprefresh();
  }


  private void cancelActionPerformed (final ActionEvent evt)
  {
    selectedCover = null;
    if (getCoverWorker != null) {
      getCoverWorker.cancel(true);
    }
    this.dispose();
    this.setVisible(false);
  }


  private void comprefresh ()
  {
    setPanelCover();
    setCovername();
    setTitle();
    if (covers == null || covers.size() == 0) {
      return;
    }
    if (covernr + 1 >= maxCovers) {
      nextButton.setEnabled(false);
    } else {
      nextButton.setEnabled(true);
    }
    if (covernr <= 0) {
      prevButton.setEnabled(false);
    } else {
      prevButton.setEnabled(true);
    }
  }


  private void createMenu ()
  {
    menu = new JMenuBar();

    final JMenu file = new JMenu("Cover");
    refresh = createMenuItem("Refresh", "Refresh the cover",
        new ActionListener()
        {
          @Override
          public void actionPerformed (final ActionEvent e)
          {
            sourcesActionPerformed(null);
          }
        });
    file.add(refresh);

    save = createMenuItem("Save", "Writes the cover into filesystem",
        new ActionListener()
        {
          @Override
          public void actionPerformed (final ActionEvent e)
          {
            saveActionPerformed(null);
          }
        });
    file.add(save);

    cancel = createMenuItem("Cancel", "Close window without saving",
        new ActionListener()
        {
          @Override
          public void actionPerformed (final ActionEvent e)
          {
            cancelActionPerformed(null);
          }
        });
    file.add(cancel);

    menu.add(file);
  }


  private JMenuItem createMenuItem (final String name, final String tooltip,
      final ActionListener l)
  {
    final JMenuItem mi = new JMenuItem();
    mi.setText(name);
    mi.setToolTipText(tooltip);
    mi.addActionListener(l);
    return mi;
  }


  private void deactivateComp ()
  {
    coverImage.setIcon(null);
    coverImage.setText("Please wait...");
    saveButton.setEnabled(false);
    save.setEnabled(false);
    refresh.setEnabled(false);
    size.setEnabled(false);
    nextButton.setEnabled(false);
    prevButton.setEnabled(false);

  }


  private String[] getPrefSources ()
  {
    final String[] srcs = new String[2];
    try {
      srcs[0] = Preferences.get().getOption("io.http.cover.source.az")
          .getStringValue();
      srcs[1] = Preferences.get().getOption("io.http.cover.source.gg")
          .getStringValue();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    return srcs;
  }


  /**
   * @return the cover, which is selected
   * @author DSIW
   */
  public Cover getSelectedCover ()
  {
    return selectedCover;
  }


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  private void initComponents ()
  {
    coverImage = new JLabel();
    SIZE = new JLabel();

    int defaultCoversize = 200;
    try {
      defaultCoversize = Preferences.get().getOption("io.http.cover.size")
          .getIntegerValue();
    }
    catch (final Exception e1) {}
    size = new IntTextField(defaultCoversize, 3);

    PX = new JLabel();
    prevButton = new JButton();
    nextButton = new JButton();
    coverName = new JTextField();
    SOURCE = new JLabel();
    sources = new JComboBox();
    NAME = new JLabel();
    cancelButton = new JButton();
    saveButton = new JButton();

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    coverImage.setOpaque(true);
    coverImage.setHorizontalAlignment(SwingConstants.CENTER);
    coverImage.setToolTipText("Cover");
    coverImage.setText("Please wait...");
    coverImage.setBackground(Color.WHITE);
    coverImage.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK),
        BorderFactory.createLineBorder(Color.WHITE, 5)));
    coverImage.setPreferredSize(new Dimension(206, 206));

    SIZE.setText("Downloadsize (w x w):");

    size.setHorizontalAlignment(SwingConstants.RIGHT);
    // size.getDocument().addDocumentListener(new DocumentListener() {});
    size.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained (final FocusEvent evt)
      {
        size.selectAll();
      }
    });
    size.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed (final KeyEvent e)
      {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          sizeActionPerformed(null);
        }
      }
    });

    PX.setText("px");

    prevButton.setIcon(Images.loadLocalIcon("arrow_left.png"));
    prevButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        prevCoverActionPerformed(evt);
      }
    });

    nextButton.setIcon(Images.loadLocalIcon("arrow_right.png"));
    nextButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        nextCoverActionPerformed(evt);
      }
    });

    coverName.setEditable(false);
    coverName.setText("interpret_album.ext");
    coverName.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained (final FocusEvent evt)
      {
        size.selectAll();
      }
    });

    SOURCE.setText("Source:");

    sources.setModel(new DefaultComboBoxModel(getPrefSources()));
    readPrefSourceNr();
    sources.setSelectedIndex(sourcenr);
    sources.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        sourcesActionPerformed(evt);
      }
    });

    NAME.setText("Name:");

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        cancelActionPerformed(evt);
      }
    });

    saveButton.setText("Save");
    saveButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        saveActionPerformed(evt);
      }
    });

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
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(coverImage,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                GroupLayout.Alignment.LEADING)
                                            .addComponent(SIZE)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(prevButton)
                                                    .addPreferredGap(
                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(nextButton))
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        size,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        63,
                                                        GroupLayout.PREFERRED_SIZE)
                                                    .addGap(3, 3, 3)
                                                    .addComponent(PX))))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                GroupLayout.Alignment.LEADING)
                                            .addComponent(SOURCE)
                                            .addComponent(NAME))
                                    .addGap(18, 18, 18)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                GroupLayout.Alignment.LEADING)
                                            .addComponent(coverName,
                                                GroupLayout.Alignment.TRAILING,
                                                GroupLayout.DEFAULT_SIZE, 302,
                                                Short.MAX_VALUE)
                                            .addComponent(sources, 0, 302,
                                                Short.MAX_VALUE)))
                            .addGroup(
                                GroupLayout.Alignment.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .addComponent(saveButton)
                                    .addPreferredGap(
                                        LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cancelButton,
                                        GroupLayout.PREFERRED_SIZE, 68,
                                        GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()));

    layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { cancelButton,
        saveButton });

    layout.setVerticalGroup(layout.createParallelGroup(
        GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(
                        layout
                            .createSequentialGroup()
                            .addComponent(SIZE)
                            .addPreferredGap(
                                LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(
                                layout
                                    .createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                    .addComponent(size,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PX))
                            .addGap(35, 35, 35)
                            .addGroup(
                                layout
                                    .createParallelGroup(
                                        GroupLayout.Alignment.LEADING)
                                    .addComponent(nextButton)
                                    .addComponent(prevButton)))
                    .addComponent(coverImage, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(coverName).addComponent(NAME))
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(SOURCE)
                    .addComponent(sources, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15,
                Short.MAX_VALUE)
            .addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton).addComponent(saveButton))
            .addContainerGap()));

    createMenu();
    this.setJMenuBar(menu);
    pack();
  }


  private void nextCover ()
  {
    if (covernr + 1 < maxCovers) {
      covernr++;
      comprefresh();
    }
  }


  private void nextCoverActionPerformed (final ActionEvent evt)
  {
    nextCover();
  }


  private void prevCover ()
  {
    if (covernr > 0) {
      covernr--;
      comprefresh();
    }
  }


  private void prevCoverActionPerformed (final ActionEvent evt)
  {
    prevCover();
  }


  private void readCovers () throws Exception
  {
    // load covers
    // GetCoverFromAll gca = new GetCoverFromAll(t.getInterpret(), t.getAlbum(),
    // 200, maxCountCovers);
    // gca.setSourcenr(sourcenr);

    GetCover gc = null;
    // Track t = pl.getActualTrack();
    // set covers
    if (sourcenr == 0) {
      gc = new GetCoverAmazon(t.getInterpret(), t.getAlbum(), size.getValue(),
          maxCovers);
    } else if (sourcenr == 1) {
      gc = new GetCoverGoogle(t.getInterpret(), t.getAlbum(), maxCovers);
    }
    if (!gc.noFound()) {
      covers = gc.getCovers();
    } else {
      if (sourcenr == 0) {
        sourcenr = 1;
      } else {
        sourcenr = 0;
      }
      comprefresh();
      return;
    }
    // covers = gca.getCovers();
    if (covers == null || covers.size() == 0) {
      throw new Exception("No cover loaded.");
    }

    // sourcenr = gca.getSourcenr();

    // resize
    if (sourcenr == 1) { // Google
      for (int c = 0; c < covers.size(); c++) {
        covers.get(c).resize(200); // size is fix
        System.out.println(covers.get(c));
      }
    }

    maxCovers = Math.min(maxCountCovers, covers.size());
    setTitle();
  }


  private void readPrefSourceNr ()
  {
    try {
      sourcenr = Preferences.get().getOption("io.http.cover.source")
          .getIntegerValue();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }


  private void resetCoverNr ()
  {
    covernr = 0;
    comprefresh();
  }


  private void saveActionPerformed (final ActionEvent evt)
  {
    final String pathToAlbum = new File(t.getPathToMusic()).getParent();
    if (selectedCover != null) {
      // write into filesystem
      selectedCover.setPath(pathToAlbum);
      try {
        if (!selectedCover.isWriteble()) {
          throw new Exception();
        }
        selectedCover.write();
      }
      catch (final Exception e) {
        Messages.showErrorMessage(this, "The cover can't  be written!");
      }
    }
    t.setCover(selectedCover);
    t.setCover(selectedCover);
    this.dispose();
    this.setVisible(false);
  }


  private void setCovername ()
  {
    if (covers == null || covers.size() == 0) {
      coverName.setText("no-cover.ext");
      return;
    }
    coverName.setText(covers.get(covernr).getFileName());
  }


  private void setPanelCover ()
  {
    try {
      if (covers != null && covers.size() > 0) {
        selectedCover = covers.get(covernr);
        System.out.println(selectedCover);
        // i = new ImageIcon(selectedCover.getImage());
        final Image img = selectedCover.getImage();

        // resize
        final int maxSize = 200;
        if (Images.getHeight(img) > maxSize || Images.getWidth(img) > maxSize) {
          final ImageIcon resized = Images.toImageIcon(Images.resize(img,
              maxSize));
          coverImage.setIcon(resized);
        } else {
          coverImage.setIcon(Images.toImageIcon(img));
        }
        // if (i == null) {
        // coverImage.setText("Please wait...");
        // } else {
        coverImage.setText(null);
        // }
        coverImage.setToolTipText(selectedCover.getLink());
      } else {
        coverImage.setText("No cover found!");
      }
    }
    catch (final NullPointerException e) {
      e.printStackTrace();
    }
    coverImage.revalidate();
  }


  private void setTitle ()
  {
    if (covers == null || covers.size() == 0) {
      setTitle(TITLE);
    } else {
      setTitle(TITLE + " " + (covernr + 1) + "/" + maxCovers);
    }
  }


  private void sizeActionPerformed (final ActionEvent evt)
  {
    // if (covers != null) {
    // try {
    // Thread.sleep(2000);
    // }
    // catch (InterruptedException e) {}
    // }
    if (!size.isValid()) {
      return;
    }
    sourcesActionPerformed(evt);
  }


  private void sourcesActionPerformed (final ActionEvent evt)
  {
    // if (covers == null || sourcenr == sources.getSelectedIndex()) {
    // return;
    // }

    if (getCoverWorker != null) {
      getCoverWorker.cancel(true);
    }
    getCoverWorker = new SwingWorker<List<Cover>, Void>()
    {
      @Override
      protected List<Cover> doInBackground () throws Exception
      {
        deactivateComp();
        try {
          Preferences.get().setOption("io.http.cover.source",
              sources.getSelectedIndex());
          sourcenr = sources.getSelectedIndex();
        }
        catch (final Exception e) {
          e.printStackTrace();
        }
        readCovers();
        return covers;
      };


      @Override
      protected void done ()
      {
        resetCoverNr();
        if (covers != null && covers.size() >= 0) {
          activateComp();
        }
        if (sourcenr == 1) { // 1 == Google.de
          size.setEnabled(false);
        } else {
          size.setEnabled(true);
        }
        comprefresh();
      }
    };
    getCoverWorker.execute();
  }
}
