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

/**
 * This class implements methods to convert illegal characters to legal
 * characters
 * 
 * @author DSIW
 * 
 */
public class Convert
{
  /**
   * Converts "&", "&quot;", "&lt;", "&gt;" to the code representation.
   * 
   * @param s
   *          String to convert
   * @return converted String
   * @author DSIW
   */
  public static String forXML (final String s)
  {
    String out = s;
    out = out.replaceAll("&", "&amp;");
    out = out.replaceAll("\"", "&quot;");
    out = out.replaceAll("<", "&lt;");
    out = out.replaceAll(">", "&gt;");
    return out;
  }


  /**
   * This is the opposite of <code>forXML()</code>
   * 
   * @see Convert
   * @param s
   *          String to convert
   * @return converted String
   * @author DSIW
   */
  public static String fromXML (final String s)
  {
    String out = s;
    out = out.replaceAll("&amp;", "&");
    out = out.replaceAll("&quot;", "\"");
    out = out.replaceAll("&lt;", "<");
    out = out.replaceAll("&gt;", ">");
    return out;
  }


  // /**
  // * Converts a String and search for illegal characters to convert these.
  // *
  // * @param s
  // * String
  // * @return a legal String
  // */
  // public static String toMusic (String s)
  // {
  // String out = s;
  // out = out.replaceAll("[^A-Za-z0-9üöäÜÖÄ.-]+", "_");
  // return out;
  // }

  // public static String noSpecialChars (final String s)
  // throws SpecialKeyException
  // {
  // if (s == null || s.isEmpty()) {
  // return "";
  // }
  // final String out = s;
  // final String[] specials = { "\uFFF0", "\uFFF1", "\uFFF2", "\uFFF3",
  // "\uFFF4", "\uFFF5", "\uFFF6", "\uFFF7", "\uFFF8", "\uFFF9", "\uFFFA",
  // "\uFFFB", "\uFFFC", "\uFFFD", "\uFFFE", "\uFFFF" };
  // for (final String special : specials) {
  // if (out.contains(special)) {
  // throw new SpecialKeyException();
  // }
  // }
  // return out;
  // }

  /**
   * Converts a String and search for illegal characters to convert these.
   * 
   * @param s
   *          String to convert
   * @return a legal String
   */
  public static String toIO (final String s)
  {
    if (s == null || s.isEmpty()) {
      return "";
    }
    String out = s;
    out = out.toLowerCase();
    out = out.replaceAll("ü", "ue");
    out = out.replaceAll("ö", "oe");
    out = out.replaceAll("ä", "ae");
    out = out.replaceAll("ß", "ss");
    out = out.replaceAll("[^a-z0-9.-]+", "_");
    out = out.replaceAll("_$", "");
    // replace all no alphanumeric characters as group with underline.
    // Run sed = new Run("echo");
    // ArrayList<String> args = new ArrayList<String>();
    // args.add("\""+s+"\"");
    // args.add("|");
    // args.add("sed");
    // String regex = "s/'[^A-Za-z0-9.-üöäÜÖÄ]\\+'/\\_/g";
    // args.add(regex);
    // args.add("|");
    // args.add("tr");
    // args.add("\"[A-Z]\"");
    // args.add("\"[a-z]\"");
    // sed.setArgs(args);
    // try {
    // sed.exec();
    // }
    // catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // catch (IOException e) {
    // e.printStackTrace();
    // }
    // catch (ExecutionException e) {
    // e.printStackTrace();
    // }
    // return sed.getOutput();
    // delete the last underline, if it's the end character of the String.
    return out;
  }
}
