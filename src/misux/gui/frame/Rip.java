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

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import misux.div.Images;
import misux.div.exceptions.MusicIsRippedException;
import misux.div.exceptions.NoCDException;
import misux.gui.Messages;
import misux.music.Encoding;
import misux.music.pl.Playlist;
import misux.music.rip.CD;
import misux.music.rip.Ripper;
import misux.music.rip.RipperListener;
import misux.pref.Preferences;

/**
 * This is the frame for the rip process.
 * 
 * @author DSIW
 * @author DSIW
 */
@SuppressWarnings("serial")
public class Rip extends JFrame
{
  private JButton                     cancel;
  private JLabel                      status;
  private JLabel                      CD_ICON;
  private JMenu                       menu;
  private JMenuItem                   ripping;
  private JMenuItem                   close;
  private JMenuItem                   eject;
  private JMenuBar                    menuBar;
  private JButton                     rip;
  private JProgressBar                ripBar;
  private JLabel                      info;

  private Ripper                      ripper;
  private SwingWorker<Object, Object> ripWorker;
  private final Playlist              pl;


  /**
   * Creates a new frame.
   * 
   * @param pl
   *          old playlist. The ripped track will add to them.
   */
  public Rip(final Playlist pl)
  {
    super("misux the ripper");
    this.pl = pl;
    initRipper();
    addWindowListener();
    initComponents();
    try {
      setIconImage(new Images("frame.png").getImage());
    }
    catch (final IOException e) {}
  }


  private void addRipperListener ()
  {
    ripper.addRipperListener(new RipperListener()
    {

      @Override
      public void ended (final boolean rippingOK)
      {
        rippingEnded(rippingOK);
      }


      @Override
      public void started (final List<Integer> trackNumbers)
      {
        cancel.setEnabled(true);
        String tracks = "";
        if (!trackNumbers.isEmpty()) {
          if (trackNumbers.size() > 1) {
            tracks = "The tracks ";
          } else {
            tracks = "The track ";
          }
          tracks += trackNumbers.toString().substring(1,
              trackNumbers.toString().length() - 1)
              + " will rip.";
        } else {
          tracks = "No tacks will rip.";
        }
        status.setText(tracks + " Please wait...");
      }
    });
  }


  private void addWindowListener ()
  {
    this.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing (final WindowEvent e)
      {
        cancelActionPerformed(null);
      }
    });
  }


  private void cancelActionPerformed (final ActionEvent evt)
  {
    if (ripper.getState() == Ripper.RIPPING) {
      if (!Messages.showConfirmMessage(this,
          "Do you want really cancel, while ripping the CD?", "Cancel")) {
        return;
      }
      ripWorker.cancel(true);
      ripWorker = new SwingWorker<Object, Object>()
      {
        @Override
        protected Object doInBackground () throws Exception
        {
          try {
            ripper.fireCanceled();
          }
          catch (final Exception e) {
            String ripProgram = null;
            try {
              ripProgram = Preferences.get()
                  .getOption("io.music.rip.ripProgram.name").getStringValue();
              Messages
                  .showErrorMessage(
                      null,
                      "The rip program can't killed. Please try it. Start a terminal and insert into the following code \"killall "
                          + ripProgram + "\"");
            }
            catch (final Exception e1) {
              Messages
                  .showErrorMessage(
                      null,
                      "The rip program can't killed. Please try it. Start a terminal and insert into the following code \"killall <rip program>\"");
            }
          }
          return null;
        }
      };
      ripWorker.execute();
      cancel.setText("Close");
    } else {
      this.dispose();
      this.setVisible(false);
    }
  }


  private void ejectAcion ()
  {
    try {
      new CD().eject();
    }
    catch (final Exception e) {
      Messages.showErrorMessage(null, "CD can't be ejected.");
    }
  }


  private void initComponents ()
  {
    ripBar = new JProgressBar();
    ripBar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    cancel = new JButton();
    rip = new JButton();
    info = new JLabel();
    status = new JLabel();
    CD_ICON = new JLabel();
    menuBar = new JMenuBar();
    menu = new JMenu();
    ripping = new JMenuItem();
    close = new JMenuItem();
    eject = new JMenuItem();

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    cancel.setText("Close");
    cancel.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        cancelActionPerformed(evt);
      }
    });

    rip.setText("Start rip");
    rip.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        ripActionPerformed(evt);
      }
    });

    info.setHorizontalAlignment(SwingConstants.CENTER);
    info.setText("The music will be ripped to the music directory. A Cover will be downloaded after the rip process.");

    status.setText("Start the rip process!");

    CD_ICON.setIcon(Images.loadLocalIcon("cd.png"));

    menu.setText("File");

    ripping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
        InputEvent.CTRL_MASK));
    ripping.setText("Start rip");
    ripping.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        ripActionPerformed(evt);
      }
    });
    menu.add(ripping);

    eject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
        InputEvent.CTRL_MASK));
    eject.setText("Eject CD");
    eject.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        ejectAcion();
      }
    });
    menu.add(eject);

    close.setText("Close window");
    close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
        InputEvent.CTRL_MASK));
    close.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        cancelActionPerformed(evt);
      }
    });
    menu.add(close);

    menuBar.add(menu);

    setJMenuBar(menuBar);

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
                            .addComponent(ripBar, GroupLayout.DEFAULT_SIZE,
                                511, Short.MAX_VALUE)
                            .addComponent(info, GroupLayout.Alignment.TRAILING,
                                GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(CD_ICON,
                                        GroupLayout.PREFERRED_SIZE, 135,
                                        GroupLayout.PREFERRED_SIZE)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                GroupLayout.Alignment.LEADING)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addGap(237, 237, 237)
                                                    .addComponent(
                                                        rip,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                    .addPreferredGap(
                                                        LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(cancel))
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(
                                                        status,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        358, Short.MAX_VALUE)))))
                    .addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(
        GroupLayout.Alignment.LEADING)
        .addGroup(
            layout
                .createSequentialGroup()
                .addContainerGap()
                .addComponent(info, GroupLayout.DEFAULT_SIZE, 70,
                    Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ripBar, GroupLayout.PREFERRED_SIZE, 36,
                    GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(
                    layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(CD_ICON, GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(
                            GroupLayout.Alignment.TRAILING,
                            layout
                                .createParallelGroup(
                                    GroupLayout.Alignment.BASELINE)
                                .addComponent(cancel).addComponent(rip))
                        .addComponent(status, GroupLayout.PREFERRED_SIZE, 92,
                            GroupLayout.PREFERRED_SIZE)).addContainerGap()));

    pack();
    setVisible(true);
  }


  private void initRipper ()
  {
    int opt = 0;
    try {
      opt = Preferences.get().getOption("io.music.rip.format")
          .getIntegerValue();
    }
    catch (final Exception e) {}
    final Encoding enc = Encoding.values()[opt];
    ripper = new Ripper(enc);
    addRipperListener();
  }


  private void ripActionPerformed (final ActionEvent evt)
  {
    rip.setEnabled(false);
    ripping.setEnabled(false);
    status.setText("Initialize Ripper (Encoding: "
        + ripper.getEnc().toString().toLowerCase() + "). Please wait...");
    ripBar.setIndeterminate(true);
    cancel.setText("Cancel");
    cancel.setEnabled(false);
    initRipper();
    if (ripWorker != null) {
      ripWorker.cancel(true);
    }
    ripWorker = new SwingWorker<Object, Object>()
    {
      @Override
      protected Object doInBackground () throws Exception
      {
        try {
          ripper.rip();
        }
        catch (final NoCDException e) {
          Messages.showInfoMessage(null, "Please insert a CD!",
              "No CD is injected.");
        }
        catch (final MusicIsRippedException e) {
          rippingEnded(true);
          Messages.showInfoMessage(null, "The music is already ripped!",
              "Ripped");
        }
        catch (final Exception e) {
          if (!ripWorker.isCancelled()) {
            Messages.showErrorMessage(null,
                "An error occurred while ripping the CD.");
          }
        }
        return null;
      }


      @Override
      protected void done ()
      {
        try {
          pl.add(ripper.getPlaylist());
        }
        catch (final Exception e) {
          e.printStackTrace();
        }
        pl.firePlaylistChanged();
      };
    };
    ripWorker.execute();
  }


  private void rippingEnded (final boolean rippingOK)
  {
    ripBar.setIndeterminate(false);
    ripBar.setCursor(null); // no wait cursor
    if (rippingOK) {
      status
          .setText("The ripping process successfully ended. Now you can listen to your music.");
      ripBar.setValue(ripBar.getMaximum());
    } else {
      status.setText("The ripping process occured. Please try it again.");
      ripBar.setValue(ripBar.getMinimum());
    }
    rip.setEnabled(true);
    ripping.setEnabled(true);
    cancel.setText("Close");
    cancel.setEnabled(true);
  }

}
