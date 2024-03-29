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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import misux.div.Images;
import misux.gui.Messages;
import misux.io.http.lyrics.GetLyric;
import misux.io.http.lyrics.Lyric;
import misux.music.track.Track;

/**
 * Displays the lyric of a track.
 * 
 * @author DSIW
 * 
 */
@SuppressWarnings("serial")
public class Lyrics extends JDialog
{
  private JButton           okButton;
  private JLabel            icon;
  private JScrollPane       scrollPane;
  private JTextArea         text;

  private final Track       t;
  private JFrame            parent;
  SwingWorker<Object, Void> sw;


  /**
   * Creates a new dialoge.
   * 
   * @param parent
   *          parent of the dialoge
   * @param t
   *          track, which lyric should be displayed
   */
  public Lyrics(final Frame parent, final Track t)
  {
    super(parent, true);
    this.t = t;
    initComponents();
    loadLyric();
    setVisible(true);
  }


  private void doInBackgroundForWorker ()
  {
    final GetLyric getLyric = new GetLyric(t.getTitle(), t.getInterpret(), 1);
    if (getLyric.noFound()) {
      Messages.showErrorMessage(parent, "No lyric was found", "No found");
      this.dispose();
      this.setVisible(false);
    } else {
      final String message = getLyric.getLyric().getContent();
      text.setText(message);
      text.setColumns(80);
      text.setLineWrap(true);
      // text.setWrapStyleWord(true);
      text.setEditable(false);
    }
  }


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  private void initComponents ()
  {

    okButton = new JButton();
    icon = new JLabel();
    icon.setIcon(Images.loadLocalIcon("lyric.png"));
    scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    text = new JTextArea();
    text.setText("Please wait...");

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Lyrics of " + t.getTitle() + " from " + t.getInterpret());

    okButton.setText("OK");
    okButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        okActionPreformed(evt);
      }
    });

    text.setColumns(20);
    text.setRows(5);
    scrollPane.setViewportView(text);

    final GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(
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
                            .addComponent(icon)
                            .addPreferredGap(
                                LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                                513, Short.MAX_VALUE))
                    .addComponent(okButton, GroupLayout.Alignment.TRAILING,
                        GroupLayout.PREFERRED_SIZE, 75,
                        GroupLayout.PREFERRED_SIZE)).addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(
        GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addContainerGap()
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(icon)
                    .addGroup(
                        layout
                            .createSequentialGroup()
                            .addComponent(scrollPane,
                                GroupLayout.PREFERRED_SIZE, 593,
                                GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(
                                LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(okButton)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    pack();
  }


  private void loadLyric ()
  {
    sw = new SwingWorker<Object, Void>()
    {
      @Override
      protected Lyric doInBackground () throws Exception
      {
        doInBackgroundForWorker();
        return null;
      };
    };
    sw.execute();
  }


  protected void okActionPreformed (final ActionEvent evt)
  {
    if (sw != null) {
      sw.cancel(true);
    }
    this.dispose();
    this.setVisible(false);
  }

}
