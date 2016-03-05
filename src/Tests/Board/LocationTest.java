package Board;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the Location class.
 * @author David Guan 2/2/16
 */
public class LocationTest {

    /**
     * Tests the overrrided equals of the Location class
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {
        Location first = new Location('a', 1);
        Location second = new Location('a', 1);
        assertEquals(first, second);
        second.setY(2);
        assertNotEquals(first, second);
    }
}