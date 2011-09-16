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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

/**
 * This class contains a few static default methods for IO.
 * 
 * @author DSIW
 */
public class FileHandler
{
  /**
   * Gets a String of a file.
   * 
   * @param file
   *          File to get a String from
   * @return String of a file
   * @throws IOException
   *           thrown if IOException occured
   */
  public static String readFile (final File file) throws IOException
  {
    final Reader reader = new FileReader(file);
    final String str = FileHandler.readFile(reader);
    reader.close();
    return str;
  }


  /**
   * Gets a String of a file.
   * 
   * @param reader
   *          Reader to get a String from
   * @return String of a file
   * @throws IOException
   *           thrown if IOException occured
   */
  public static String readFile (final Reader reader) throws IOException
  {
    final StringBuffer buffer = new StringBuffer();
    final char[] readArray = new char[1024];
    int bytes;
    while (true) {
      bytes = reader.read(readArray);
      if (bytes == -1) {
        break;
      }
      for (int i = 0; i < bytes; i++) {
        buffer.append(readArray[i]);
      }
    }
    return buffer.toString();
  }


  /**
   * Gets a String of a file.
   * 
   * @param str
   *          path to a file
   * @return String of a file
   * @throws IOException
   *           thrown if IOException occured
   */
  public static String readFile (final String str) throws IOException
  {
    final File file = new File(str);
    return FileHandler.readFile(file);
  }


  /**
   * Gets a String with the content of a webpage.
   * 
   * @param in
   *          InputStream of a webpage
   * @return String with the content of a webpage
   * @throws IOException
   *           thrown if IOException occured
   */
  public static String readPage (final InputStream in) throws IOException
  {
    final StringBuffer buffer = new StringBuffer();
    final byte[] readArray = new byte[1024];
    int bytes;
    while (true) {
      bytes = in.read(readArray);
      if (bytes == -1) {
        break;
      }
      for (int i = 0; i < bytes; i++) {
        buffer.append((char) readArray[i]);
      }
    }
    return buffer.toString();
  }


  /**
   * Gets a String with the content of a webpage.
   * 
   * @param str
   *          path to a webpage
   * @return String with the content of a webpage
   * @throws IOException
   *           thrown if IOException occured
   */
  public static String readPage (final String str) throws IOException
  {
    final URL url = new URL(str);
    return FileHandler.readPage(url);
  }


  /**
   * Gets a String with the content of a webpage.
   * 
   * @param url
   *          URL of a webpage
   * @return String with the content of a webpage
   * @throws IOException
   *           thrown if IOException occured
   */
  public static String readPage (final URL url) throws IOException
  {
    final InputStream in = url.openConnection().getInputStream();
    final String str = FileHandler.readPage(in);
    in.close();
    return str;
  }


  /**
   * Saves a binary file.
   * 
   * @param input
   *          File to read from
   * @param output
   *          File to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeBinaryFile (final File input, final File output)
      throws IOException
  {
    final InputStream in = new FileInputStream(input);
    final OutputStream out = new FileOutputStream(output);
    FileHandler.writeBinaryFile(in, out);
    in.close();
    out.close();
  }


  /**
   * Saves a binary file.
   * 
   * @param in
   *          InputStream to read from
   * @param out
   *          OutputStream to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeBinaryFile (final InputStream in,
      final OutputStream out) throws IOException
  {
    int bytes;
    final byte[] inputArray = new byte[1024];
    while (true) {
      bytes = in.read(inputArray);
      if (bytes == -1) {
        break;
      }
      out.write(inputArray, 0, bytes);
    }
    out.flush();
  }


  /**
   * Saves a binary file.
   * 
   * @param input
   *          path to a file to read from
   * @param output
   *          path to a file to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeBinaryFile (final String input, final String output)
      throws IOException
  {
    final File inputFile = new File(input);
    final File outputFile = new File(output);
    FileHandler.writeBinaryFile(inputFile, outputFile);
  }


  /**
   * Saves a binary webpage.
   * 
   * @param in
   *          InputStream to read from
   * @param out
   *          OutputStream to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeBinaryPage (final InputStream in,
      final OutputStream out) throws IOException
  {
    FileHandler.writeBinaryFile(in, out);
  }


  /**
   * Saves a binary webpage.
   * 
   * @param str
   *          path to a webpage to read from
   * @param out
   *          OutputStream to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeBinaryPage (final String str, final OutputStream out)
      throws IOException
  {
    final URL url = new URL(str);
    FileHandler.writeBinaryPage(url, out);
  }


  /**
   * Saves a binary webpage.
   * 
   * @param url
   *          URL to read from
   * @param out
   *          OutputStream to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeBinaryPage (final URL url, final OutputStream out)
      throws IOException
  {
    final InputStream in = url.openConnection().getInputStream();
    FileHandler.writeBinaryPage(in, out);
    in.close();
  }


  /**
   * Saves a file in another file.
   * 
   * @param input
   *          file to read from
   * @param output
   *          file to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeFile (final File input, final File output)
      throws IOException
  {
    final Reader reader = new FileReader(input);
    final Writer writer = new FileWriter(output);
    FileHandler.writeFile(reader, writer);
    reader.close();
    writer.close();
  }


  /**
   * Saves a file in another file.
   * 
   * @param reader
   *          Reader to read from
   * @param writer
   *          Wrier to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeFile (final Reader reader, final Writer writer)
      throws IOException
  {
    int bytes;
    final char[] readArray = new char[1024];
    while (true) {
      bytes = reader.read(readArray);
      if (bytes == -1) {
        break;
      }
      writer.write(readArray, 0, bytes);
    }
    writer.flush();
  }


  /**
   * Saves a file in another file.
   * 
   * @param input
   *          path to a file to read from
   * @param output
   *          path to a file to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writeFile (final String input, final String output)
      throws IOException
  {
    final File inputFile = new File(input);
    final File outputFile = new File(output);
    FileHandler.writeFile(inputFile, outputFile);
  }


  /**
   * Saves the content of a webpage in a file.
   * 
   * @param reader
   *          Reader to read from
   * @param writer
   *          Writer to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writePage (final Reader reader, final Writer writer)
      throws IOException
  {
    FileHandler.writeFile(reader, writer);
  }


  /**
   * Saves the content of a webpage in a file.
   * 
   * @param str
   *          path to a webpage to read from
   * @param writer
   *          Writer to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writePage (final String str, final Writer writer)
      throws IOException
  {
    final URL url = new URL(str);
    FileHandler.writePage(url, writer);
  }


  /**
   * Saves the content of a webpage in a file.
   * 
   * @param url
   *          webpage to read from
   * @param writer
   *          Writer to write in
   * @throws IOException
   *           thrown if IOException occured
   */
  public static void writePage (final URL url, final Writer writer)
      throws IOException
  {
    final Reader reader = new InputStreamReader(url.openConnection()
        .getInputStream());
    FileHandler.writePage(reader, writer);
    reader.close();
  }


  /**
   * Writes a String in the specified file.
   * 
   * @param str
   *          String to write
   * @param file
   *          file
   * @throws IOException
   * @author DSIW
   */
  public static void writeString (final String str, final File file)
      throws IOException
  {
    try {
      file.getParentFile().mkdirs();
    }
    catch (final NullPointerException e) {}
    file.createNewFile();
    final Writer writer = new FileWriter(file);
    FileHandler.writeString(str, writer);
    writer.close();
  }


  /**
   * Writes a String in the specified file.
   * 
   * @param str
   *          String to write
   * @param file
   *          file
   * @throws IOException
   * @author DSIW
   */
  public static void writeString (final String str, final String file)
      throws IOException
  {
    final File file1 = new File(file);
    FileHandler.writeString(str, file1);
  }


  /**
   * Writes a String in the specified writer.
   * 
   * @param str
   *          String to write
   * @param writer
   *          writer
   * @throws IOException
   * @author DSIW
   */
  public static void writeString (final String str, final Writer writer)
      throws IOException
  {
    writer.write(str);
    writer.flush();
  }
}
