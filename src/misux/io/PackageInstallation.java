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
package misux.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import misux.div.exceptions.ExecutionException;

/**
 * This class implements methods to check all needed packages and commands.
 * These could be installed.
 * 
 * @author DSIW
 * 
 */
public class PackageInstallation
{
  private List<String>       packages;
  private final List<String> commands;
  private int                counter;


  /**
   * This creates a new object with the specified packages or commands.
   * 
   * @param arePackage
   *          true, then the arguments will set to packages, in opposite to
   *          commands.
   * @param args
   *          packages or command which will add
   */
  public PackageInstallation(final boolean arePackage, final String... args)
  {
    packages = new ArrayList<String>();
    commands = new ArrayList<String>();
    counter = 0;
    if (arePackage) {
      setPackages(args);
    } else {
      setCommands(args);
    }
  }


  private boolean areAllPackagesInstalled () throws InterruptedException,
      IOException, ExecutionException
  {
    final List<String> newPkgs = new ArrayList<String>(packages);
    for (final String pkg : packages) {
      if (!isPackageInstalled(pkg)) {
        packages = newPkgs;
        return false;
      } else {
        newPkgs.remove(pkg);
      }
    }
    packages = newPkgs;
    return true;
  }


  private void convertCommandsToPackets ()
  {
    for (final String cmd : commands) {
      packages.add(convertedCommand(cmd));
      if (convertedCommand(cmd) != null) {
        commands.remove(cmd);
      }
    }
  }


  private String convertedCommand (final String cmd)
  {
    if (cmd.equals("abcde")) {
      return cmd;
    }
    if (cmd.equals("cd-discid")) {
      return cmd;
    }
    return null;
  }


  /**
   * @return the commands
   */
  public List<String> getCommands ()
  {
    return commands;
  }


  /**
   * @return the pakets
   */
  public List<String> getPakets ()
  {
    return packages;
  }


  /**
   * Installs all not installed packages from an <code>xterm</code>.
   * 
   * @throws Exception
   * @author DSIW
   */
  public void installAll () throws Exception
  {
    convertCommandsToPackets();
    if (commands.size() > 0) {
      throw new Exception("Commands exist.");
    }
    if (packages.size() <= 0) {
      return;
    }

    String packagesToInstall = "";
    for (final String paket : packages) {
      if (isPackageInstalled(paket)) {
        continue;// packages.remove(paket);
      }
      packagesToInstall += " " + paket;
    }
    if (packagesToInstall.length() == 0) {
      return;
    }

    final String command = "xterm";
    final Run terminal = new Run(command, "-e", "sudo apt-get install"
        + packagesToInstall);// +"; le_exec");
    terminal.exec();
    counter++;

    if (areAllPackagesInstalled()) {
      return;
    } else if (counter < 3) {
      installAll();
    } else {
      throw new Exception("Package isn't installed.");
    }
  }


  private boolean isPackageInstalled (final String packageName)
      throws InterruptedException, IOException, ExecutionException
  {
    final Run dpkg = new Run("dpkg-query", "-W",
        "-f='${Status} ${Version}\\n'", packageName);
    dpkg.exec();
    final String output = dpkg.getOutput();
    if (output.contains("deinstall")) {
      return false;
    }
    if (output.contains("unknown")) {
      return false;
    }
    if (output.contains("install")) {
      return true;
    }
    return false;
  }


  /**
   * @param commands
   *          the commands to set
   */
  public void setCommands (final String... commands)
  {
    for (final String string : commands) {
      this.commands.add(string);
    }
  }


  /**
   * @param packages
   *          the packages to set
   */
  public void setPackages (final String... packages)
  {
    for (final String string : packages) {
      this.packages.add(string);
    }
  }
}
