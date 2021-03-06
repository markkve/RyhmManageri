package ryhma.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ryhma.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.23 20:00:21 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class RyhmaTest {



  // Generated by ComTest BEGIN
  /** testToString26 */
  @Test
  public void testToString26() {    // Ryhma: 26
    Ryhma ryhma = new Ryhma(); 
    ryhma.parse("   1  |   Ryhma 1  |"); 
    assertEquals("From: Ryhma line: 29", "1|Ryhma 1|", ryhma.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse46 */
  @Test
  public void testParse46() {    // Ryhma: 46
    Ryhma ryhma = new Ryhma(); 
    ryhma.parse("   1  |   Ryhma 1  |"); 
    assertEquals("From: Ryhma line: 49", 1, ryhma.getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetNimi68 */
  @Test
  public void testGetNimi68() {    // Ryhma: 68
    Ryhma aku = new Ryhma(); 
    aku.rekisteroi(); 
    aku.taytaRyhmaTiedoilla(); 
    assertEquals("From: Ryhma line: 72", "Ryhma 2", aku.getNimi()); 
  } // Generated by ComTest END
}