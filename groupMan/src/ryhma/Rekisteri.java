package ryhma;

import java.io.File;
import java.util.List;

/**
 * Rekisteri-luokka:
 * 
 * Rekisteriä ei testata erikseen koska samat testit on muissa luokissa.
 * 
 * Katsoo, että jasen-luokka ja työtunnit-luokka osaavat pitää yhteyttä toisiinsa
 * Lukee ja kirjoittaa ryhmän tiedostoon, kun sitä tarvitaan
 * Kysyjä luokka. Pistää vaan saamansa komennot eteenpäin. Oikeaan osoitteeseen.
 * 
 * testejä pääohjelmassa, sekä lisää metodeilla.
 * 
 * Puuttuu työtuntien poistaminen
 * 
 * @author Veikko M, Roope T
 * @version 22.04.2020
 * 
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 *
 *  private Rekisteri rekisteri;
 *  private Ryhma ryhma1;
 *  private Ryhma ryhma2;
 *  private int jid1;
 *  private int jid2;
 *  
 *  public void alustaRyhma() {
 *    rekisteri = new Rekisteri();
 *    ryhma1 = new Ryhma(); ryhma1.taytaRyhmaTiedoilla(); ryhma1.rekisteroi();
 *    ryhma2 = new Ryhma(); ryhma2.taytaRyhmaTiedoilla(); ryhma2.rekisteroi();
 *    jid1 = ryhma1.getTunnusNro();
 *    jid2 = ryhma2.getTunnusNro();
 *    try {
 *    rekisteri.lisaa(ryhma1);
 *    rekisteri.lisaa(ryhma2);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
 */
public class Rekisteri {
    // Rekisterissä on 3 attribuuttia.
    // Attribuutti1: Ryhmät tyyppinen luokka jonka olioviitteen nimi voi olla ryhmat
    Ryhmat ryhmat = new Ryhmat();
    Jasenet jasenet = new Jasenet();
    Tyotunnit tunnit = new Tyotunnit();
    
  
    
    /**
     * Oletusmuodostaja?
     */
    public Rekisteri() {
        // ei tarvita mitään tähän.
    }
    
    
    /**
     * @param jasen eli uusi jasen
     * @throws SailoException jos liikaa
     * 
     * tultiin controllerista. siirrytään jasenet luokkaan lisaa metodiin.
     * parametrina viedään luotu jasen.
     * 
     * Testattu muualla.
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    

    /**
     * Lis'tään uusi ryhma reksiteriin
     * @param ryhma lisättävä
     * @throws SailoException jos ei mahu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * var rekisteri = new Rekisteri();
     * Ryhma ry1 = new Ryhma(), ry2 = new Ryhma();
     * ry1.rekisteroi(); ry2.rekisteroi();
     * rekisteri.getRyhmia() === 0;
     * rekisteri.lisaa(ry1); rekisteri.getRyhmia() === 1;
     * rekisteri.lisaa(ry2); rekisteri.getRyhmia() === 2;
     * rekisteri.lisaa(ry1); rekisteri.getRyhmia() === 3;
     * rekisteri.annaRyhma(0) === ry1;
     * rekisteri.annaRyhma(1) === ry2;
     * rekisteri.annaRyhma(2) === ry1;
     * </pre>
     */
    public void lisaa(Ryhma ryhma) throws SailoException {
        
        ryhmat.lisaa(ryhma);
        
    }
    
    
    /**
     * @param tuntipaketti / tyotunnithlo eli kutsuu Tyotunnit luokan lisaa metodia parametrilla tietyn henkilön tuntipaketti
     * @throws SailoException jos liikaa
     */
    public void lisaa(TyotunnitHlo tuntipaketti) throws SailoException {
        tunnit.lisaa(tuntipaketti);
    }
    
    
    /**
     * @param jasen viite lisattavaan. omistajaksi tietorakenne
     * @throws SailoException exception
     
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {

        jasenet.korvaaTaiLisaa(jasen);
    }
    */
    
    
    /**
     * Poistetaan valittu jäsen
     * @param jasen poistettava jasen
     */
    public void poista(Jasen jasen) {
        if (jasen == null) return;
        this.jasenet.poista(jasen);
                
    }
    
    
    /**
     * Poistetaan valittu ryhmä jäsenineen
     * @param ryhma poistettava jasen
     * @return Poistettujen ryhmien lukumäärä
     */
    public int poista(Ryhma ryhma) {
        if (ryhma == null) return 0;
        int lukumaara = this.ryhmat.poista(ryhma.getTunnusNro());
        this.jasenet.poistaRyhmalaiset(ryhma.getTunnusNro());
        return lukumaara;
                
    }
    
    
    /**
     * @return palauttaa ryhmien lukumäärän.
     * 
     * tarvitaan chooserille for looppiin hae metodissa controllerissa
     */
    public int getRyhmia() {
        return ryhmat.getLkm();
    }

    
    /**
     * @return palauttaa jäsenten lukumäärän.
     * 
     * tarvitaan chooserille for looppiin hae metodissa controllerissa
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    
    /**
     * @param ryhma valittu ryhmä joka kertoo mistä kotoisin olevat jäsenet näytetään
     * @return tietyn ryhman jasenet Jasenet luokan aliohjelmalla annaJasenet
     * parametrina ryhman tunnusnumero. Ei toimi olennolla tarvitaan int.
     * Tuo tunnusnumero haetaan ryhmä luokasta. Se on jo olemassa ja se syntyi kun luotiin ko ryhmä.
     */
    public List<Jasen> annaJasenet(Ryhma ryhma) {
        return this.jasenet.annaJasenet(ryhma.getTunnusNro());
    }
    
    
    /**
     * @param jasen eli käsittelyssä oleva jäsen. 
     * @return viitteen pakettiin
     * 
     * Tultiin GUIcontrollerista kun haluttiin tulostaa juuri tämän jäsenen työtunnit
     * 
     * kutsutaan tunnit luokan annaTunnit aliohjelmaa parametrilla (Jäsenen tunnusnumero, joka Haetaan jäsen luokasta.)
     * Näin koska annaTunnit ei tarvitse koko olentoa vain sen tunnusnumero atrribuutin.
     * 
     * halutaan kaikki jäsenelle luodut tunnit
     * 
     * palautetaan viite jäsenen tunteihin takaisin controlleriin.
     */
    public List<TyotunnitHlo> annaTunnit(Jasen jasen) {
        return this.tunnit.annaTunnit(jasen.getTunnusNro());
    }
    

    /**
     * Palauttaa i:n ryhman 
     * @param i monesko ryhma
     * @return viite i:teen ryhmaan
     * @throws IndexOutOfBoundsException, jos i menee väärin
     * 
     * tarvitsee parametrina int luvun. Sama luku välitetään ryhmät luokkaan anna metodiin.
     */
    public Ryhma annaRyhma(int i) throws IndexOutOfBoundsException {
        return ryhmat.anna(i);
    }
        
    
    /**
     * tallennetaan!
     * @throws SailoException jos jokin menee vikaan
     */
    public void tallenna() throws SailoException {
        String virhe = "";

        try {
            jasenet.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }

        
        try {
            tunnit.tallenna();
            } catch (SailoException e) {
            e.printStackTrace();
        }
        
        try {
            ryhmat.tallenna();
            } catch (SailoException e) {
            e.printStackTrace();
            }
        
        if (virhe.length() > 0) {
            throw new SailoException(virhe);
        }
    }
    

    /**
     * luetaan käyttöliittymän tarvitsemat tiedot tiedostosta
     * @param nimi teidoston nimi
     * @throws SailoException ongelma
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  String hakemisto = "testiryhma";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/ryhmat.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  rekisteri = new Rekisteri(); // tiedostoja ei ole, tulee poikkeus 
     *  rekisteri.lueTiedostosta(hakemisto); #THROWS SailoException
     *  alustaRyhma();
     *  rekisteri.setTiedosto(hakemisto); // nimi annettava koska uusi poisti sen
     *  rekisteri.tallenna(); 
     *  rekisteri = new Rekisteri();
     *  rekisteri.lueTiedostosta(hakemisto);
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        ryhmat = new Ryhmat();
        jasenet = new Jasenet();
        tunnit = new Tyotunnit();
        setTiedosto(nimi);
        ryhmat.lueTiedostosta();
        jasenet.lueTiedostosta();
        tunnit.lueTiedostosta();
    }
    
    
    /**
     * Setter tiedostojen nimille
     * @param nimi tiedoston nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        ryhmat.setTiedostonPerusnimi(hakemistonNimi + "ryhmat");
        jasenet.setTiedostonPerusnimi(hakemistonNimi + "jasenet");
        tunnit.setTiedostonPerusnimi(hakemistonNimi + "tunnit");
    }


    /**
     * Testi-pääohjelma rekisterille
     * Testataan:
     *      Uuden rekisterin luonti
     *      Uusien ryhmien luonti
     *          Ryhmien rekisteröinti ja täyttäminen
     *          Ryhmien lisääminen
     *          Ryhmän tunnusnumeron haku
     *      Uuden jäsenen luominen ryhmän tunnusnumerolla
     *          Jäsenen reksiteröinti
     *          Jäsnen täyttö
     *          Jäsenen lisääminen
     *          Jäsenen tunnusnumeron haku
     *      Uusien työtuntien luonti jäsenelle
     *          rekisteröinti
     *          täyttö
     *          lisääminen
     *      
     *      Ryhmien läpikäynti
     *          Löytyneen ryhmän jäsenten läpikäynti
     *              Löytyneen jäsenen tuntien läpikäynti
     *      Tulostus.
     *      
     * @param args ei käytössä
     */
    public static void main (String[] args) {
        var rekisteri = new Rekisteri();
        
        var ry1 = new Ryhma();
        var ry2 = new Ryhma();
        
        ry1.rekisteroi();
        ry1.taytaRyhmaTiedoilla();
        
        ry2.rekisteroi();
        ry2.taytaRyhmaTiedoilla();
        
        try {
             rekisteri.lisaa(ry1);
             rekisteri.lisaa(ry2);
             
        } catch (SailoException e) {
            System.out.println(e.getMessage());
         }
        
        int ryhmanNumero1 = ry1.getTunnusNro();
        int ryhmanNumero2 = ry2.getTunnusNro();
        
        
        var aku = new Jasen(ryhmanNumero1);
        aku.rekisteroi();
        aku.taytaAkuTiedoilla(ryhmanNumero1);
        try {
            rekisteri.lisaa(aku);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        int jasenenNumero1 = aku.getTunnusNro();
        var testitunnit = new TyotunnitHlo(jasenenNumero1); // selvitetty controllerissa jäsenen numerosta.
        testitunnit.rekisteroi();
        testitunnit.vastaaTavoiteTyotunnit(jasenenNumero1);
        testitunnit.vastaaTosiTyotunnit(jasenenNumero1);
        try {
            rekisteri.lisaa(testitunnit);
        } catch (SailoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        
        
        var aku2 = new Jasen(ryhmanNumero2);
        aku2.rekisteroi();
        aku2.taytaAkuTiedoilla(ryhmanNumero2);
        try {
            rekisteri.lisaa(aku2);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        System.out.println("=========== Rekisterin testi ============");
        
        for (int i = 0; i < rekisteri.getRyhmia(); i++) {
            Ryhma ryhma = rekisteri.annaRyhma(i);
           //Jasen jasen = rekisteri.annaJasen(i);
            System.out.println("\n" + "Ryhma paikassa: " + i);
            ryhma.tulosta(System.out);
            
            
            
            List<Jasen> loytyneet = rekisteri.annaJasenet(ryhma);
            //List<TyotunnitHlo> loytynyt = rekisteri.annaTunnit(jasen);
            // nyt ylös kun vielä muistan, eli teehty sisäkkäiset for loopit joista ensimmäisellä haetaan jasenet joiden indeksi on sama kuin ryhman indeksi.
            // tulostetaan löytynyt jasen ja tarkastetaan toisella for loopilla onko tälle jäsenelle olemassa tuntipakettia. (Pakettia jonka jasenen numero
            // on sama kuin nyt tarkastelussa oleva jasen.) Jos on olemassa "loytynyt" tulostetaan tämän tiedot jasenen tietojen alle.
            System.out.println();
            System.out.println("Jäsenet: ");
            for (Jasen jasen : loytyneet) {
                jasen.tulosta(System.out);
                
                List<TyotunnitHlo> loytynyt = rekisteri.annaTunnit(jasen);
                for (TyotunnitHlo tunnit : loytynyt) {     
                    System.out.println("");
                    tunnit.tulosta(System.out);
                }
                
            }
        }   
    }



}
