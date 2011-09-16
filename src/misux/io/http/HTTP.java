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
package misux.io.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class implements methods for reading the content from a website.
 * 
 * @author DSIW
 * 
 */
public class HTTP
{
  /**
   * This is the Uset Agent for the browser "lyx".
   */
  public final static String LYX     = "Lynx/2.8.4rel.1 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/0.9.6c";
  /**
   * This is the Uset Agent for the browser "firefox".
   */
  public final static String FIREFOX = "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.10) Gecko/2009042316 Firefox/3.0.10";


  /**
   * Reads the content
   * 
   * @param url
   *          URL of the content as String
   * @param userAgent
   *          the user agent to read the website
   * @return the readed content
   * @throws MalformedURLException
   * @throws IOException
   * @author DSIW
   */
  public static String readContent (final String url, final String userAgent)
      throws MalformedURLException, IOException
  {
    return HTTP.readContent(new URL(url), userAgent);
  }


  /**
   * Reads the content
   * 
   * @param url
   *          URL of the content as URL
   * @param userAgent
   *          the user agent to read the website
   * @throws IOException
   * @return the readed content
   * @author DSIW
   */
  public static String readContent (final URL url, final String userAgent)
      throws IOException
  {
    final URLConnection connection = url.openConnection();
    connection.addRequestProperty("User-Agent", userAgent);
    final String encoding = connection.getContentEncoding();
    final InputStream input = new BufferedInputStream(
        connection.getInputStream());

    Reader leser;
    if (encoding == null) {
      leser = new InputStreamReader(input, "iso-8859-1");
    } else {
      leser = new InputStreamReader(input, encoding);
    }

    final StringWriter contentW = new StringWriter();
    for (int i = leser.read(); i >= 0; i = leser.read()) {
      final char c = (char) i;
      contentW.write(c);
    }
    return contentW.toString();
  }
}
