/**
 * Java Settlers - An online multiplayer version of the game Settlers of Catan
 * This file Copyright (C) 2020 Jeremy D Monin <jeremy@nand.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The maintainer of this program can be reached at jsettlers@nand.net
 **/

package soctest.game;

import soc.game.SOCDevCardConstants;
import soc.game.SOCGame;
import soc.game.SOCInventory;
import soc.game.SOCPlayer;
import soc.game.SOCResourceSet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A few tests for {@link SOCPlayer}.
 *
 * @see TestGame
 * @since 2.3.00
 */
public class TestPlayer
{

    @Test
    public void testDiscardRoundDown()
    {
        SOCPlayer pl = new SOCPlayer(2, new SOCGame("test"));
        pl.addRolledResources(new SOCResourceSet(1, 2, 2, 2, 1, 0));
        assertEquals(8, pl.getResources().getTotal());  // SOCResourceSet is mostly tested elsewhere
        assertEquals("discard should be half", 4, pl.getCountToDiscard());
        pl.addRolledResources(new SOCResourceSet(1, 0, 0, 0, 0, 0));
        assertEquals("discard should round down", 4, pl.getCountToDiscard());
    }

    @Test
    public void testInventoryDevCardsVP()
    {
        SOCPlayer pl = new SOCPlayer(2, new SOCGame("test"));
        assertEquals(0, pl.getTotalVP());
        SOCInventory i = pl.getInventory();
        assertFalse(pl.hasUnplayedDevCards());

        i.addDevCard(1, SOCInventory.NEW, SOCDevCardConstants.ROADS);
        assertTrue(pl.hasUnplayedDevCards());
        assertEquals(0, pl.getPublicVP());
        assertEquals(0, pl.getTotalVP());

        i.addDevCard(1, SOCInventory.NEW, SOCDevCardConstants.UNIV);
        assertEquals("VP cards should be hidden from public VP", 0, pl.getPublicVP());
        assertEquals("VP cards should count towards total VP", 1, pl.getTotalVP());
    }

}
