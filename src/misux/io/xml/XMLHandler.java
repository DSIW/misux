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
package misux.io.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import misux.io.Convert;
import misux.music.Encoding;
import misux.music.Genre;
import misux.music.license.Copyright;
import misux.music.license.License;
import misux.music.pl.Playlist;
import misux.music.pl.PlaylistImpl;
import misux.music.track.Track;
import misux.music.track.TrackImpl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class handles xml in- and output for playlists. It implements methods to
 * saving in a XML file and parsing from a XML file.
 * 
 * @author DSIW
 * @author DSIW
 */
public class XMLHandler
{
  private static String PLAYLIST_TAG          = "PLAYLIST";
  private static String PLAYLIST_NAME_TAG     = "PLAYLIST_NAME";
  private static String TRACK_TAG             = "TRACK";
  private static String TRACK_TITLE_TAG       = "TRACK_TITLE";
  private static String TRACK_INTERPRET_TAG   = "TRACK_INTERPRET";
  private static String TRACK_ALBUM_TAG       = "TRACK_ALBUM";
  private static String TRACK_INDEX_TAG       = "TRACK_INDEX";
  private static String TRACK_COUNT_TAG       = "TRACK_COUNT";
  private static String TRACK_GENRE_TAG       = "TRACK_GENRE";
  private static String TRACK_YEAR_TAG        = "TRACK_YEAR";
  private static String TRACK_RATING_TAG      = "TRACK_RATING";
  private static String TRACK_PATHTOMUSIC_TAG = "TRACK_PATHTOMUSIC";
  private static String TRACK_PATHTOCOVER_TAG = "TRACK_PATHTOCOVER";
  private static String TRACK_DURATION_TAG    = "TRACK_DURATION";
  private static String TRACK_CDINDEX_TAG     = "TRACK_CDINDEX";
  private static String TRACK_LICENSE_TAG     = "TRACK_LICENSE";
  private static String TRACK_ENC_TAG         = "TRACK_ENCODING";


  private static String addXMLHeader (final String s)
  {
    return "<?xml version=\"1.0\"?>\n" + s;
  }


  private static void appendTag (final StringBuffer xml, final String tag,
      final String content)
  {
    xml.append("    <" + tag + ">");
    xml.append(Convert.forXML(content));
    xml.append("</" + tag + ">\n");
  }


  /**
   * Reads a node and returns a list with each playlist contained in the node.
   * 
   * @param dom
   *          W3C Node
   * @return list with all playlists contained in the node
   * @author DSIW
   */
  private static List<Playlist> fromXML (final Node dom)
  {
    final List<Playlist> list = new ArrayList<Playlist>();
    if (dom.getNodeName().equals(XMLHandler.PLAYLIST_TAG)) {
      final Playlist playlist = new PlaylistImpl("XML-HANDLER");
      final NodeList childNodes = dom.getChildNodes();
      for (int i = 0; i < childNodes.getLength(); i++) {
        final Node node = childNodes.item(i);
        if (node.getNodeName().equals(XMLHandler.TRACK_TAG)) {
          playlist.add(XMLHandler.getTrackfromXML(node));
        }
        if (node.getNodeName().equals(XMLHandler.PLAYLIST_NAME_TAG)) {
          playlist.setName(XMLHandler.getTextContent(node).trim());
        }
      }
      list.add(playlist);
    } else {
      final NodeList nodeList = dom.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        final Node node = nodeList.item(i);
        list.addAll(XMLHandler.fromXML(node));
      }
    }
    return list;
  }


  /**
   * Gets a list with all playlists which are contained as xml in a file.
   * 
   * @param pathToXMLFile
   *          path to a xml file which contains playlists
   * @return list of playlists
   * @throws SAXException
   * @throws IOException
   * @throws ParserConfigurationException
   * @author DSIW
   */
  public static List<Playlist> fromXML (final String pathToXMLFile)
      throws SAXException, IOException, ParserConfigurationException
  {
    final File playlistFile = new File(pathToXMLFile);
    final Node dom = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .parse(playlistFile);
    return XMLHandler.fromXML(dom);
  }


  private static String getTextContent (final Node n)
  {
    return Convert.fromXML(n.getTextContent().trim());
  }


  /**
   * Gets a track from a node.
   * 
   * @param node
   *          node with xml information for a track
   * @return track
   * @author DSIW
   * @author DSIW
   */
  private static Track getTrackfromXML (final Node node)
  {
    final NodeList nl = node.getChildNodes();

    Genre genre = Genre.Other;
    String album = "";
    int count = 0;
    String interpret = "";
    int duration = 0;
    String pathToMusic = "";
    String pathToCover = "";
    int rating = 0;
    String title = "";
    int year = 1990;
    int index = 0;
    int cdIndex = 0;
    License license = new Copyright();
    Encoding enc = Encoding.NO;

    for (int j = 0; j < nl.getLength(); j++) {
      final Node n = nl.item(j);
      if (n.getNodeName().equals(XMLHandler.TRACK_TITLE_TAG)) {
        title = XMLHandler.getTextContent(n).trim();
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_ALBUM_TAG)) {
        album = XMLHandler.getTextContent(n).trim();
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_INTERPRET_TAG)) {
        interpret = XMLHandler.getTextContent(n).trim();
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_GENRE_TAG)) {
        genre = Genre.value(XMLHandler.getTextContent(n).trim());
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_PATHTOMUSIC_TAG)) {
        pathToMusic = XMLHandler.getTextContent(n).trim();
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_PATHTOCOVER_TAG)) {
        pathToCover = XMLHandler.getTextContent(n).trim();
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_COUNT_TAG)) {
        count = Integer.parseInt(XMLHandler.getTextContent(n).trim());
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_INDEX_TAG)) {
        index = Integer.parseInt(XMLHandler.getTextContent(n).trim());
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_RATING_TAG)) {
        rating = Integer.parseInt(XMLHandler.getTextContent(n).trim());
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_YEAR_TAG)) {
        year = Integer.parseInt(XMLHandler.getTextContent(n).trim());
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_DURATION_TAG)) {
        duration = Integer.parseInt(XMLHandler.getTextContent(n));
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_CDINDEX_TAG)) {
        cdIndex = Integer.parseInt(XMLHandler.getTextContent(n));
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_ENC_TAG)) {
        enc = Encoding.value(XMLHandler.getTextContent(n));
      }
      if (n.getNodeName().equals(XMLHandler.TRACK_LICENSE_TAG)) {
        final String textNode = XMLHandler.getTextContent(n);
        license = License.parseName(textNode);
      }
    }
    return new TrackImpl(index, cdIndex, title, album, interpret, duration,
        year, genre, rating, pathToMusic, pathToCover, enc, license, count);
  }


  // private static String rmXMLHeader (final String s)
  // {
  // return s.substring(s.indexOf('\n'));
  // }

  /**
   * Converts a playlist to xml.
   * 
   * @param playlist
   *          playlist to convert
   * @return playlist as xml string
   * @author DSIW
   */
  public static String toXML (final Playlist playlist)
  {
    final StringBuffer xml = new StringBuffer();
    xml.append("<" + XMLHandler.PLAYLIST_TAG + ">\n");
    xml.append("<" + XMLHandler.PLAYLIST_NAME_TAG + ">");
    xml.append(playlist.getName());
    xml.append("</" + XMLHandler.PLAYLIST_NAME_TAG + ">\n");
    for (int i = 0; i < playlist.getLength(); i++) {
      final Track track = playlist.getTrack(i);
      xml.append(XMLHandler.toXML(track));
    }
    xml.append("</" + XMLHandler.PLAYLIST_TAG + ">\n");
    return XMLHandler.addXMLHeader(xml.toString());
  }


  /**
   * Converts a track to xml.
   * 
   * @param track
   *          track to convert
   * @return track as xml string
   * @author DSIW
   * @author DSIW
   */
  private static String toXML (final Track track)
  {
    if (!track.isWriteable()) {
      return "";
    }
    final StringBuffer xml = new StringBuffer();
    xml.append("<" + XMLHandler.TRACK_TAG + ">\n");
    XMLHandler.appendTag(xml, XMLHandler.TRACK_TITLE_TAG, track.getTitle());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_ALBUM_TAG, track.getAlbum());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_INTERPRET_TAG,
        track.getInterpret());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_GENRE_TAG,
        track.getGenreToString());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_PATHTOMUSIC_TAG,
        track.getPathToMusic());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_PATHTOCOVER_TAG,
        track.getPathToCover());
    XMLHandler
        .appendTag(xml, XMLHandler.TRACK_COUNT_TAG, track.getCount() + "");
    XMLHandler
        .appendTag(xml, XMLHandler.TRACK_INDEX_TAG, track.getIndex() + "");
    XMLHandler.appendTag(xml, XMLHandler.TRACK_CDINDEX_TAG, track.getCdIndex()
        + "");
    XMLHandler.appendTag(xml, XMLHandler.TRACK_RATING_TAG, track.getRating()
        + "");
    XMLHandler.appendTag(xml, XMLHandler.TRACK_DURATION_TAG,
        track.getDuration() + "");
    XMLHandler.appendTag(xml, XMLHandler.TRACK_ENC_TAG, track.getEnc()
        .toString());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_LICENSE_TAG, track.getLicense()
        .getName());
    XMLHandler.appendTag(xml, XMLHandler.TRACK_YEAR_TAG, track.getYear() + "");
    xml.append("</" + XMLHandler.TRACK_TAG + ">\n");
    return xml.toString();
  }
}
