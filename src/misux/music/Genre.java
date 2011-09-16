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
package misux.music;

/**
 * This enum lists many genres for the music.
 * 
 * @author DSIW
 * 
 */
public enum Genre
{
  // http://www.multimediasoft.com/amp3dj/help/index.html?amp3dj_00003e.htm
  ClassicRock, Country, Dance, Disco, Funk, Grunge, HipHop, Jazz, Metal, NewAge, Oldies, Other, Pop, RB, Rap, Reggae, Rock, Techno, Industrial, Alternative, Ska, DeathMetal, Pranks, Soundtrack, EuroTechno, Ambient, TripHop, Vocal, JazzFunk, Fusion, Trance, Classical, Instrumental, Acid, House, Game, SoundClip, Gospel, Noise, AlternativeRock, Bass, Soul, Punk, Space, Meditative, InstrumentalPop, InstrumentalRock, Ethnic, Gothic, Darkwave, TechnoIndustrial, Electronic, PopFolk, Eurodance, Dream, SouthernRock, Comedy, Cult, Gangsta, Top40, ChristianRap, PopFunk, Jungle, NativeUS, Cabaret, NewWave, Psychadelic, Rave, Showtunes, Trailer, LoFi, Tribal, AcidPunk, AcidJazz, Polka, Retro, Musical, RockRoll, HardRock, Folk, FolkRock, NationalFolk, Swing, FastFusion, Bebob, Latin, Revival, Celtic, Bluegrass, Avantgarde, GothicRock, ProgressiveRock, PsychedelicRock, SymphonicRock, SlowRock, BigBand, Chorus, EasyListening, Acoustic, Humour, Speech, Chanson, Opera, ChamberMusic, Sonata, Symphony, BootyBass, Primus, PornGroove, Satire, SlowJam, Club, Tango, Samba, Folklore, Ballad, PowerBallad, RhythmicSoul, Freestyle, Duet, PunkRock, DrumSolo, Acapella, EuroHouse, DanceHall, Goa, DrumBass, ClubHouse, Hardcore, Terror, Indie, BritPop, Negerpunk, PolskPunk, Beat, ChristianGangstaRap, HeavyMetal, BlackMetal, Crossover, ContemporaryChristian, ChristianRock, Merengue, Salsa, ThrashMetal, Anime, JPop, Synthpop, Unknown;

  /**
   * This is an other implementation of valueOf(). If the valueOf-method will
   * throw an IllegalArgumentException, the return type is <code>Unknown</code>.
   * 
   * @param genre
   *          to parsed String
   * @return Genre object
   * @author DSIW
   */
  public static Genre value (final String genre)
  {
    try {
      return Genre.valueOf(genre);
    }
    catch (final IllegalArgumentException e) {
      return Genre.Unknown;
    }
  }
}
