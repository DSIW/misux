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
package misux.io.file;

import java.io.IOException;

import misux.pref.Preferences;

/**
 * This class implements methods to create the config file for the external
 * program 'abcde'
 * 
 * @author DSIW
 * 
 */
public class AbcdeConfig extends File
{

  /**
   * Creates a new config file
   * 
   * @throws Exception
   */
  public AbcdeConfig() throws Exception
  {
    super();
    setOverwrite(true);
  }


  @Override
  public String createContent () throws Exception
  {
    final String comment = "# This is the config file for abcde. It's genereted by misux.";

    Preferences.get().getOption("io.config.dir").getStringValue();
    final String musicDir = Preferences.get().getOption("io.user.musicdir")
        .getStringValue();
    final String pipe = " | ";
    final String echo = "echo \"$@\"";
    // to lower case characters
    final String tr = "tr \"[A-Z]\" \"[a-z]\"";
    // replace all characters these are not ".", "_", "a-z", "0-9" and "-" with
    // "_"
    final String sed = "sed 's/[^._a-z0-9-]\\+/_/g'";
    // replace all ä,ü,ö,ß with ae,ue,oe,ss and delete the "_" at last
    final String aou = "sed 's/ü/ue/g;s/ö/oe/g;s/ä/ae/g;s/ß/ss/g;s/_$//g'";
    final String regex = echo + pipe + tr + pipe + sed + pipe + aou;

    final String content = "CDDBPROTO=6\nMP3ENCODERSYNTAX=default\nOGGENCODERSYNTAX=default\nKEEPWAVS=n\nPADTRACKS=y\nINTERACTIVE=n\nCDDBURL=\"http://freedb.freedb.org/~cddb/cddb.cgi\"\nCDDISCID=cd-discid\nACTIONS=cddb,read,encode,tag,move,clean\nOUTPUTDIR="
        + musicDir
        // +
        // "\nOUTPUTFORMAT='${ARTISTFILE}/${ALBUMFILE}/${TRACKNUM}-${ARTISTFILE}-${ALBUMFILE}-${TRACKFILE}'\nmungefilename ()\n{\necho \"$@\" | sed s,:,\\ -,g | tr \\ / __ | tr -d \\'\\\"\\?\\[:cntrl:\\]\n}\n";
        + "\nOUTPUTFORMAT='${ARTISTFILE}/${ALBUMFILE}/${TRACKNUM}-${ARTISTFILE}-${ALBUMFILE}-${TRACKFILE}'\nmungefilename ()\n{\n"
        + regex + "\n}\n";
    return comment + "\n" + content;
  }


  @Override
  public String createFileName () throws Exception
  {
    final String tmp = Preferences.get().getOption("io.music.rip.abcde.config")
        .getStringValue();
    if (tmp == null || tmp.equals("")) {
      throw new IOException();
    }
    return tmp;
  }
}
