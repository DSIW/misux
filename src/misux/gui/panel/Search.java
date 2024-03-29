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
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/*
 * Search.java
 * 
 * Created on 13.07.2011, 10:41:32
 */

package misux.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import misux.div.Images;

/**
 * This is a panel to search in the track table.
 * 
 * @author DSIW
 */
@SuppressWarnings("serial")
public class Search extends JPanel
{
  private JButton    searchButton;
  private JTextField searchField;


  /**
   * Creates a new search panel.
   */
  public Search()
  {
    initComponents();
  }


  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  private void initComponents ()
  {
    searchButton = new JButton();
    searchField = new JTextField();

    searchButton.setIcon(Images.loadLocalIcon("thumb-search_ltl.png"));
    searchButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed (final ActionEvent evt)
      {
        searchFieldActionPerformed(evt);
      }
    });

    searchField.setText("Search...");
    searchField.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained (final FocusEvent evt)
      {
        searchField.selectAll();
      }
    });
    searchField.addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed (final KeyEvent e)
      {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          searchFieldActionPerformed(null);
        }
      }
    });

    final GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(
        GroupLayout.Alignment.LEADING).addGroup(
        layout
            .createSequentialGroup()
            .addComponent(searchField, GroupLayout.PREFERRED_SIZE, 179,
                GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 29,
                GroupLayout.PREFERRED_SIZE)));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(searchField, GroupLayout.DEFAULT_SIZE, 29,
            Short.MAX_VALUE)
        .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 29,
            GroupLayout.PREFERRED_SIZE));

  }


  private void searchFieldActionPerformed (final ActionEvent evt)
  {
    // TODO add your handling code here:
  }
}
