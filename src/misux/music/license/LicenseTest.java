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
 * misux - musicplayer (written in Java)
Copyright (C) 2011  DSIW <dsiw@privatdemail.net>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
misux - musicplayer (written in Java)
Copyright (C) 2011  DSIW <dsiw@privatdemail.net>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
package misux.music.license;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class
 * 
 * @author DSIW
 * 
 */
public class LicenseTest
{
  /**
   * Tests the method equals
   * 
   * @author DSIW
   */
  @Test
  public void testEqualsObject ()
  {
    License lic = new Copyright();
    Assert.assertTrue(lic.equals(new Copyright()));
    Assert.assertTrue(!lic.equals(new Copyleft()));
    Assert.assertTrue(!lic.equals(new CreativeCommons()));
    Assert.assertTrue(!lic.equals(new FreeMusic()));

    lic = new Copyleft();
    Assert.assertTrue(!lic.equals(new Copyright()));
    Assert.assertTrue(lic.equals(new Copyleft()));
    Assert.assertTrue(!lic.equals(new CreativeCommons()));
    Assert.assertTrue(!lic.equals(new FreeMusic()));

    lic = new CreativeCommons();
    Assert.assertTrue(!lic.equals(new Copyright()));
    Assert.assertTrue(!lic.equals(new Copyleft()));
    Assert.assertTrue(lic.equals(new CreativeCommons()));
    Assert.assertTrue(!lic.equals(new FreeMusic()));

    lic = new FreeMusic();
    Assert.assertTrue(!lic.equals(new Copyright()));
    Assert.assertTrue(!lic.equals(new Copyleft()));
    Assert.assertTrue(!lic.equals(new CreativeCommons()));
    Assert.assertTrue(lic.equals(new FreeMusic()));
  }
}
