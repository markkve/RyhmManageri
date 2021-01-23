package ryhma.test;
// Generated by ComTest BEGIN
import java.io.File;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import ryhma.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 21:14:32 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class JasenetTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa51 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa51() throws SailoException {    // Jasenet: 51
    Jasenet jasenet = new Jasenet(); 
    Jasen aku1 = new Jasen(), aku2 = new Jasen(); 
    assertEquals("From: Jasenet line: 55", 0, jasenet.getLkm()); 
    jasenet.lisaa(aku1); assertEquals("From: Jasenet line: 56", 1, jasenet.getLkm()); 
    jasenet.lisaa(aku2); assertEquals("From: Jasenet line: 57", 2, jasenet.getLkm()); 
    jasenet.lisaa(aku1); assertEquals("From: Jasenet line: 58", 3, jasenet.getLkm()); 
    jasenet.lisaa(aku1); assertEquals("From: Jasenet line: 59", 4, jasenet.getLkm()); 
    jasenet.lisaa(aku1); assertEquals("From: Jasenet line: 60", 5, jasenet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta107 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta107() throws SailoException {    // Jasenet: 107
    Jasenet jasenet = new Jasenet(); 
    Jasen roope = new Jasen(), veikko = new Jasen();
    veikko.rekisteroi();
    roope.taytaAkuTiedoilla(300); 
    veikko.taytaAkuTiedoilla(1); 
    String hakemisto = "testi"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    jasenet.lueTiedostosta(tiedNimi); 
    fail("Jasenet: 121 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    jasenet.lisaa(roope); 
    jasenet.lisaa(veikko); 
    jasenet.tallenna(); 
    jasenet = new Jasenet(); 
    jasenet.lueTiedostosta(tiedNimi); 
    Iterator<Jasen> i = jasenet.iterator(); 
    i.next(); 
    i.next(); 
    assertEquals("From: Jasenet line: 130", false, i.hasNext()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testIterator215 */
  @Test
  public void testIterator215() {    // Jasenet: 215
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(); 
    Jasen jasen2 = new Jasen(); 
    try {
        jasenet.lisaa(jasen1);
        jasenet.lisaa(jasen2); 
    } catch (SailoException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 

    Iterator<Jasen> iter = jasenet.iterator(); 
    assertEquals("From: Jasenet line: 224", jasen1, iter.next()); 
    assertEquals("From: Jasenet line: 225", jasen2, iter.next()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaJasenet240 */
  @Test
  public void testAnnaJasenet240() {    // Jasenet: 240
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(1); 
    Jasen jasen2 = new Jasen(2); 
    Jasen jasen3 = new Jasen(2); 
    try {
        jasenet.lisaa(jasen1);
        jasenet.lisaa(jasen2); 
        jasenet.lisaa(jasen3);
    } catch (SailoException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
 
    List<Jasen> Loytyneet; 
    Loytyneet = jasenet.annaJasenet(3); 
    assertEquals("From: Jasenet line: 251", 0, Loytyneet.size()); 
    Loytyneet = jasenet.annaJasenet(1); 
    assertEquals("From: Jasenet line: 253", 1, Loytyneet.size()); 
    Loytyneet = jasenet.annaJasenet(2); 
    assertEquals("From: Jasenet line: 255", 2, Loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista291 */
  @Test
  public void testPoista291() {    // Jasenet: 291
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(1); 
    Jasen jasen2 = new Jasen(2); 
    Jasen jasen3 = new Jasen(2); 
    try {
        jasenet.lisaa(jasen1);
        jasenet.lisaa(jasen2);
    } catch (SailoException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
 
    assertEquals("From: Jasenet line: 298", true, jasenet.poista(jasen2)); 
    assertEquals("From: Jasenet line: 299", 1, jasenet.getLkm()); 
    assertEquals("From: Jasenet line: 300", false, jasenet.poista(jasen3)); 
    assertEquals("From: Jasenet line: 301", 1, jasenet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoistaRyhmalaiset315 */
  @Test
  public void testPoistaRyhmalaiset315() {    // Jasenet: 315
    Jasenet jasenet = new Jasenet(); 
    Jasen jasen1 = new Jasen(1); 
    Jasen jasen2 = new Jasen(2); 
    Jasen jasen3 = new Jasen(2); 
    try {
        jasenet.lisaa(jasen1);
        jasenet.lisaa(jasen2); 
        jasenet.lisaa(jasen3); 
    } catch (SailoException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 

    assertEquals("From: Jasenet line: 323", 2, jasenet.poistaRyhmalaiset(2)); 
    assertEquals("From: Jasenet line: 324", 1, jasenet.getLkm()); 
  } // Generated by ComTest END
}