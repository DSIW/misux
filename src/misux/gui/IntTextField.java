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
package misux.gui;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * This class implements methods for an integer text field. You can't insert no
 * integer values.
 * 
 * @author DSIW
 * 
 */
@SuppressWarnings("serial")
public class IntTextField extends JTextField
{
  class IntTextDocument extends PlainDocument
  {

    @Override
    public void insertString (final int offs, final String str,
        final AttributeSet a) throws BadLocationException
    {
      if (str == null) {
        return;
      }
      final String oldString = getText(0, getLength());
      final String newString = oldString.substring(0, offs) + str
          + oldString.substring(offs);
      try {
        Integer.parseInt(newString + "0");
        super.insertString(offs, str, a);
      }
      catch (final NumberFormatException e) {}
    }
  }


  /**
   * Creates a new integer text field with default value "0".
   * 
   * @param size
   *          size of the text field
   */
  public IntTextField(final int size)
  {
    this(0, size);
  }


  /**
   * Creates a new integer text field.
   * 
   * @param defval
   *          default value
   * @param size
   *          size of the text field
   */
  public IntTextField(final int defval, final int size)
  {
    super(size);
    setText("" + defval);
  }


  @Override
  protected Document createDefaultModel ()
  {
    return new IntTextDocument();
  }


  /**
   * @return the integer value of the text field
   * @author DSIW
   */
  public int getValue ()
  {
    try {
      return Integer.parseInt(getText());
    }
    catch (final NumberFormatException e) {
      return 0;
    }
  }


  @Override
  public boolean isValid ()
  {
    try {
      Integer.parseInt(getText());
      return true;
    }
    catch (final NumberFormatException e) {
      return false;
    }
    catch (final Exception e) {
      return true;
    }
  }

}