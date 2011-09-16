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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import misux.div.Images;
import misux.div.Time;
import misux.gui.Messages;
import misux.gui.table.TrackTableModel;
import misux.music.player.Player;
import misux.music.player.PlayerListener;
import misux.music.player.Repeat;
import misux.music.player.Volume;

/**
 * Creates a new panel to play a track. It has the following components: buttons
 * for playing, skipping, stopping, shuffleing, repeating and muting. A music
 * bar and different labels shows information about the playing track. A cover
 * panel shows the cover from it, too.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class PlayerPanel extends JPanel
{
  private JButton               prevButton;
  private JButton               playButton;
  private JButton               nextButton;
  private JButton               stopButton;
  private JButton               shuffleButton;
  private JButton               repeatButton;
  private JButton               umuteButton;
  private JLabel                duration;
  private JLabel                TIME_OF;
  private JLabel                actTime;
  private JLabel                interpret;
  private JLabel                FROM;
  private JLabel                title;
  private JProgressBar          musicBar;
  private JSlider               volSlider;

  private int                   miliTime = 0;
  private final Timer           timer;

  private final Player          player;
  private final TrackTableModel model;
  private CoverPanel            coverPanel;


  /**
   * Creates a new player panel.
   * 
   * @param player
   *          player
   * @param model
   *          track table model
   */
  public PlayerPanel(final Player player, final TrackTableModel model)
  {
    this.player = player;
    this.model = model;
    try {
      coverPanel = new CoverPanel(player.getPlaylist());
    }
    catch (final Exception e1) {
      coverPanel = null;
    }
    try {
      initComponents();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    timer = new Timer(1000, new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent e)
      {
        refreshTime();
      }
    });
    addPlayerListener();
  }


  private void addPlayerListener ()
  {
    player.addPlayerListener(new PlayerListener()
    {
      @Override
      public void opened ()
      {
        try {
          interpret.setText(player.getPlaylist().getActualTrack()
              .getInterpret());
          title.setText(player.getPlaylist().getActualTrack().getTitle());

          try {
            playButton.setIcon(Images.toImageIcon(new Images(
                "thumb-media_pause.png").getImage()));
            playButton.setToolTipText("Pause music");
          }
          catch (final IOException e1) {}
        }
        catch (final Exception e) {
          interpret.setText(null);
          title.setText(null);
        }
        loadDuration();
        // playButtonActionPerformed(null);
        initTime();
        coverPanel.refreshCover();
      }


      @Override
      public void playlistChanged ()
      {
        model.fireTableDataChanged();
      }


      @Override
      public void songFinished ()
      {
        model.fireTableDataChanged();
      }
    });
  }


  /**
   * @return cover panel
   * @author DSIW
   */
  public CoverPanel getCoverPanel ()
  {
    return coverPanel;
  }


  private void initComponents () throws IOException
  {
    prevButton = new JButton();
    playButton = new JButton();
    nextButton = new JButton();
    stopButton = new JButton();
    musicBar = new JProgressBar();
    interpret = new JLabel();
    FROM = new JLabel();
    title = new JLabel();
    shuffleButton = new JButton();
    repeatButton = new JButton();
    actTime = new JLabel();
    TIME_OF = new JLabel();
    duration = new JLabel();
    umuteButton = new JButton();
    volSlider = new JSlider();

    prevButton.setIcon(Images
        .toImageIcon(new Images("thumb-media_previous.png").getImage()));
    prevButton.setToolTipText("Previous track");
    prevButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        prevButtonActionPerformed(evt);
      }
    });

    playButton.setIcon(Images.toImageIcon(new Images("thumb-media_play.png")
        .getImage()));
    playButton.setToolTipText("Play selected track");
    playButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        playButtonActionPerformed(evt);
      }
    });

    nextButton.setIcon(Images.toImageIcon(new Images("thumb-media_next.png")
        .getImage()));
    nextButton.setToolTipText("Next track");
    nextButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        nextButtonActionPerformed(evt);
      }
    });

    stopButton.setIcon(Images.toImageIcon(new Images("thumb-media_stop.png")
        .getImage()));
    stopButton.setToolTipText("Stop playing");
    stopButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        stopButtonActionPerformed(evt);
      }
    });

    musicBar.setToolTipText("Progress of the actually played music");
    try {
      musicBar.setMaximum(player.getActualTrack().getDuration());
    }
    catch (final Exception e) {
      musicBar.setMaximum(100000);
    }
    musicBar.setMinimum(0);
    musicBar.setStringPainted(false);

    interpret.setText("");
    interpret.setToolTipText("Interpret of the selected track");
    interpret.setFont(new Font(interpret.getFont().getName(), Font.BOLD,
        (int) (interpret.getFont().getSize() * 1.2)));

    FROM.setText("from");

    title.setText("");
    // title.setMaximumSize(new Dimension(, title.getHeight()));
    title.setToolTipText("Title of the selected track");
    title.setFont(new Font(title.getFont().getName(), Font.BOLD, (int) (title
        .getFont().getSize() * 1.2)));

    shuffleButton.setIcon(Images.toImageIcon(new Images(
        "thumb-media_shuffle_deact.png").getImage()));
    shuffleButton.setToolTipText("Shuffle the playlist");
    shuffleButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        shuffleButtonActionPerformed(evt);
      }
    });

    repeatButton.setIcon(Images
        .toImageIcon(new Images("thumb-media_repeat.png").getImage()));
    repeatButton.setToolTipText("Repeat nothing");
    repeatButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        repeatButtonActionPerformed(evt);
      }
    });

    actTime.setText("00:00");
    actTime.setToolTipText("Actual time of the selected track");

    TIME_OF.setHorizontalAlignment(SwingConstants.CENTER);
    TIME_OF.setText("/");

    duration.setText("00:00");
    duration.setToolTipText("Total time of the selected track");

    umuteButton.setIcon(Images.toImageIcon(new Images(
        "thumb-media_volume_0-ltl.png").getImage()));
    umuteButton.setToolTipText("Mute/Unmute");
    umuteButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        umuteButtonActionPerformed(evt);
      }
    });

    volSlider.setOrientation(SwingConstants.VERTICAL);
    volSlider.setMaximum(Volume.VOL_MAX);
    volSlider.setMinimum(Volume.VOL_MIN);
    volSlider.setToolTipText("Set the volume");
    volSlider.setValue(player.getVolume());
    volSlider.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseReleased (final MouseEvent arg0)
      {
        volChanged(arg0);
      }
    });

    volSlider.addChangeListener(new ChangeListener()
    {
      @Override
      public void stateChanged (final ChangeEvent e)
      {
        volChanged(e);
      }
    });

    final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout
        .setHorizontalGroup(layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(prevButton)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(playButton)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(stopButton)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nextButton)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(shuffleButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(repeatButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(title,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        328, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(FROM)
                                    .addGap(18, 18, 18)
                                    .addComponent(interpret,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        202,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(actTime)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TIME_OF)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(duration))
                            .addComponent(musicBar,
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 934,
                                Short.MAX_VALUE))
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                false)
                            .addComponent(umuteButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .addComponent(volSlider,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)).addContainerGap()));
    layout
        .setVerticalGroup(layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING,
                                false)
                            .addComponent(nextButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                Short.MAX_VALUE)
                            .addComponent(stopButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .addComponent(playButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                Short.MAX_VALUE)
                            .addComponent(prevButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                Short.MAX_VALUE)
                            .addComponent(shuffleButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 50,
                                Short.MAX_VALUE)
                            .addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                layout
                                    .createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.BASELINE,
                                        false)
                                    .addComponent(FROM,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        52,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(title,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        52,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGap(42, 42, 42)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(duration)
                                            .addComponent(TIME_OF)
                                            .addComponent(actTime)))
                            .addComponent(interpret,
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 52,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(repeatButton,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE))
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(musicBar,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26))
            .addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addComponent(umuteButton,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(volSlider,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 74,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()));
  }


  private void initTime ()
  {
    miliTime = 0;
    timer.start();
    loadDuration();
    try {
      musicBar.setMaximum(player.getActualTrack().getDuration());
    }
    catch (final Exception e) {
      musicBar.setMaximum(100000);
    }
    refreshTime();
  }


  private void loadDuration ()
  {
    try {
      duration.setText(player.getActualTrack().getDurationToString());
    }
    catch (final Exception e) {
      duration.setText("00:00");
    }
  }


  private void nextButtonActionPerformed (final ActionEvent evt)
  {
    if (player.isPlaying()) {
      timer.stop();
      try {
        player.playNext();
      }
      catch (final Exception e) {
        Messages.showErrorMessage(this, "Music can't be skipped",
            "Error while managing music");
      }
      initTime();
    }
  }


  private void pauseAction ()
  {
    try {
      player.pause();
      timer.stop();
      playButton.setIcon(Images.toImageIcon(new Images("thumb-media_play.png")
          .getImage()));
      playButton.setToolTipText("Resume music");
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final BasicPlayerException e) {
      e.printStackTrace();
    }
  }


  private void playAction ()
  {
    try {
      playButton.setIcon(Images.toImageIcon(new Images("thumb-media_pause.png")
          .getImage()));
      playButton.setToolTipText("Pause music");
    }
    catch (final IOException e1) {}
    try {
      player.playPlaylist();
    }
    catch (final FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (final JavaLayerException e) {
      e.printStackTrace();
    }
    catch (final BasicPlayerException e) {
      e.printStackTrace();
    }
    catch (final UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }


  private void playButtonActionPerformed (final ActionEvent evt)
  {
    if (player.isPlaying()) {
      pauseAction();
    } else if (player.isStopped() || player.getState() == BasicPlayer.UNKNOWN) {
      playAction();
    } else if (player.isPaused()) {
      resumeAction();
    }
  }


  private void prevButtonActionPerformed (final ActionEvent evt)
  {
    if (player.isPlaying()) {
      timer.stop();
      try {
        player.playPrev();
      }
      catch (final Exception e) {
        Messages.showErrorMessage(this, "Music can't be skipped",
            "Error while managing music");
      }
      initTime();
    }
  }


  private void refreshTime ()
  {
    if (miliTime <= musicBar.getMaximum()) {
      actTime.setText(Time.MiliSecondsToString(miliTime));
      musicBar.setValue(miliTime);
      miliTime += 1000;
    }
  }


  private void repeatButtonActionPerformed (final ActionEvent evt)
  {
    switch (player.getRepeat()) {
      case Nothing:
        repeatButton.setToolTipText("Repeat track");
        try {
          repeatButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_repeat_1new.png").getImage()));
        }
        catch (final IOException e) {}
        player.setRepeat(Repeat.Track);
        break;
      case Track:
        repeatButton.setToolTipText("Repeat all");
        try {
          repeatButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_repeat_anew.png").getImage()));
        }
        catch (final IOException e) {}
        player.setRepeat(Repeat.All);
        break;
      case All:
      default:
        repeatButton.setToolTipText("Repeat nothing");
        try {
          repeatButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_repeat.png").getImage()));
        }
        catch (final IOException e) {}
        player.setRepeat(Repeat.Nothing);
        break;
    }
  }


  private void resumeAction ()
  {
    try {
      player.resume();
      timer.start();
      playButton.setIcon(Images.toImageIcon(new Images("thumb-media_pause.png")
          .getImage()));
      playButton.setToolTipText("Pause music");
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final Exception e) {
      Messages.showErrorMessage(this, "Music can't be played",
          "Error while managing music");
    }
  }


  private void shuffleButtonActionPerformed (final ActionEvent evt)
  {
    final SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>()
    {
      @Override
      protected Void doInBackground () throws Exception
      {
        if (player.isRandomized()) {
          player.setRandom(false);
          shuffleButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_shuffle_deact.png").getImage()));
        } else {
          player.setRandom(true);
          shuffleButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_shuffle.png").getImage()));
        }
        return null;
      }
    };
    sw.execute();
  }


  private void stopButtonActionPerformed (final ActionEvent evt)
  {
    pauseAction(); // HACK
    try {
      player.stop();
      playButton.setIcon(Images.toImageIcon(new Images("thumb-media_play.png")
          .getImage()));
      playButton.setToolTipText("Pause music");
    }
    catch (final Exception e) {
      Messages.showErrorMessage(this, "Music can't be stopped",
          "Error while managing music");
    }
    timer.stop();
    miliTime = 0;
    refreshTime();
  }


  private void umuteButtonActionPerformed (final ActionEvent evt)
  {
    try {
      player.toggleMuteVolume();
    }
    catch (final Exception e) {}
    if (player.getVolume() == Volume.VOL_MIN) {
      try {
        umuteButton.setIcon(Images.toImageIcon(new Images(
            "thumb-media_volume_3.png").getImage()));
        umuteButton.setToolTipText("Music is muted.");
      }
      catch (final IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        umuteButton.setIcon(Images.toImageIcon(new Images(
            "thumb-media_volume_0-ltl.png").getImage()));
        umuteButton.setToolTipText("Music is not muted.");
      }
      catch (final IOException e) {
        e.printStackTrace();
      }
    }
    volSlider.setValue(player.getVolume());
    volChanged(null);
  }


  private void volChanged (final Object obj)
  {
    if (obj instanceof MouseEvent) {
      if (!(volSlider.getValue() == 0 && player.getVolume() == Volume.VOL_MIN || volSlider
          .getValue() > 0 && player.getVolume() > Volume.VOL_MIN)) {
        umuteButtonActionPerformed(null);
      }
    } else if (obj == null || obj instanceof ChangeEvent) {
      try {
        player.setVolume(volSlider.getValue());
      }
      catch (final Exception e) {}
      if (volSlider.getValue() == 0) {
        try {
          umuteButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_volume_3.png").getImage()));
          umuteButton.setToolTipText("Music is muted.");
        }
        catch (final IOException e) {}
      } else {
        try {
          umuteButton.setIcon(Images.toImageIcon(new Images(
              "thumb-media_volume_0-ltl.png").getImage()));
          umuteButton.setToolTipText("Music is not muted.");
        }
        catch (final IOException e) {}
      }
    }
  }
}
