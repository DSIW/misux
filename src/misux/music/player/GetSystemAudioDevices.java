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
/**
 * 
 */
package misux.music.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import misux.div.exceptions.ExecutionException;
import misux.io.Run;

/**
 * This class implements methods to get the system audio devices.
 * 
 * @author DSIW
 * 
 */
public class GetSystemAudioDevices
{
  private String             content;
  private final AudioDevices adevices;


  /**
   * 
   */
  public GetSystemAudioDevices()
  {
    adevices = new AudioDevices();
    try {
      read();
    }
    catch (final InterruptedException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    catch (final ExecutionException e) {
      e.printStackTrace();
    }
  }


  private void filter ()
  {
    filterCardLines();

    final List<Integer> cardNrs = filterCardNrs();

    final List<String> facs = filterFactory();
    filterDevicePattern();

    final List<String> devs = filterDevices();

    final String[] names = new String[cardNrs.size()];
    for (int i = 0; i < names.length; i++) {
      String hwName = "";
      hwName += facs.get(i);
      hwName += " [";
      hwName += cardNrs.get(i);
      hwName += ", ";
      hwName += devs.get(i);
      hwName += "]";
      if (i == 0) {
        adevices.add(new AudioDevice(AudioDeviceType.INTERNAL, hwName));
      } else {
        adevices.add(new AudioDevice(AudioDeviceType.EXTERNAL, hwName));
      }
    }
  }


  private void filterCardLines ()
  {
    final String PATTERN = "Karte ";
    final String PATTERN_2 = "Card ";

    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\n");
    for (final String line : lines) {
      if (line.startsWith(PATTERN)) {
        final int startIndex = line.indexOf(PATTERN) + PATTERN.length();
        sb.append(line.substring(startIndex));
        sb.append("\n");
      } else if (line.startsWith(PATTERN_2)) {
        final int startIndex = line.indexOf(PATTERN_2) + PATTERN_2.length();
        sb.append(line.substring(startIndex));
        sb.append("\n");
      }
    }
    content = sb.toString();
  }


  private List<Integer> filterCardNrs ()
  {
    final List<Integer> nrs = new ArrayList<Integer>();
    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\n");
    for (String line : lines) {
      final int index = line.indexOf(":");
      nrs.add(Integer.parseInt(line.substring(0, index)));
      line = line.substring(line.indexOf(":") + 1);
      sb.append(line + "\n");
    }
    content = sb.toString();
    return nrs;
  }


  private void filterDevicePattern ()
  {
    final String PATTERN = "Gerät ";
    final String PATTERN_2 = "Device ";

    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\n");
    for (final String line : lines) {
      if (line.contains(PATTERN)) {
        final int startIndex = line.indexOf(PATTERN) + PATTERN.length();
        sb.append(line.substring(startIndex));
        sb.append("\n");
      } else if (line.contains(PATTERN_2)) {
        final int startIndex = line.indexOf(PATTERN_2) + PATTERN_2.length();
        sb.append(line.substring(startIndex));
        sb.append("\n");
      }
    }
    content = sb.toString();
  }


  private List<String> filterDevices ()
  {
    final List<String> devs = new ArrayList<String>();
    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\n");
    for (String line : lines) {
      final int index = line.indexOf(":");
      devs.add(line.substring(0, index).trim());
      line = line.substring(index + 1);
      sb.append(line.trim() + "\n");
    }
    content = sb.toString();
    return devs;
  }


  private List<String> filterFactory ()
  {
    final List<String> factories = new ArrayList<String>();
    final StringBuilder sb = new StringBuilder();
    final String[] lines = content.split("\n");
    for (String line : lines) {
      final int index = line.indexOf("[");
      factories.add(line.substring(0, index).trim());
      line = line.substring(index + 1);
      sb.append(line.trim() + "\n");
    }
    content = sb.toString();
    return factories;
  }


  /**
   * @return system audio devices
   * @author DSIW
   */
  public AudioDevices getDevices ()
  {
    return adevices;
  }


  private void loadContent () throws InterruptedException, IOException,
      ExecutionException
  {
    final Run aplay = new Run("aplay", "-l");
    aplay.exec();
    content = aplay.getOutput();
  }


  /**
   * @return true, if no system audio was found
   * @author DSIW
   */
  public boolean noFound ()
  {
    if (adevices.size() > 0) {
      return true;
    }
    return false;
  }


  private void read () throws InterruptedException, IOException,
      ExecutionException
  {
    loadContent();
    filter();
  }
}
