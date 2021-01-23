package ryhma;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
//import java.util.NoSuchElementException;
//import java.util.Scanner;

// import java.util.Arrays;

/**
 * Ryhmat - luokka: Luokka ryhmille joka huolehtii ryhmistä. Osaa mm. lisätä uuden ryhmän ja käydä nämä läpi.
 * @author  Veikko M, Roope T
 * @version 22.04
 */
public class Ryhmat{
    

    private int lkm;
    private static final int MAX_RYHMIA   = 3;
    //private static int lkm = 0;
    //private String tiedostonNimi = "";
    private Ryhma ryhmat[] = new Ryhma[MAX_RYHMIA];
    private String kokoNimi = "";
    private String tiedostonPerusnimi = "";
    
    
    /**
     * Getter ryhmien lukumäärälle
     * 
     * @return neulaa heinäsuovasta
     * 
     * palautetaan ryhmien lukumäärä. 
     * 
     * chooserin alustus kun lisätään ryhmä. For loop.
     */
    public int getLkm() {
        return lkm;
    }

    
    /**
     * lisää uuden ryhmän tietorakenteeseen.
     * @param ryhma :viite lisättävään ryhmään. Tietorakenne muutuu omistajaksi. ELi ryhmä on nyt yksi Ryhmät luokan alkioista.
     * @throws SailoException poikkeus
     * @example
     * <pre name="test">
     * Ryhmat ryhmaat = new Ryhmat();
     * Ryhma ryhma1 = new Ryhma();
     * Ryhma ryhma2 = new Ryhma();
     * Ryhma ryhma3 = new Ryhma();
     * Ryhma ryhma4 = new Ryhma();
     * Ryhma ryhma5 = new Ryhma();
     * Ryhma ryhma6 = new Ryhma();
     * ryhmaat.lisaa(ryhma1);
     * ryhmaat.lisaa(ryhma2);
     * ryhmaat.lisaa(ryhma3);
     * ryhmaat.lisaa(ryhma4);
     * ryhmaat.lisaa(ryhma5);
     * ryhmaat.lisaa(ryhma6);
     * ryhmaat.getLkm() === 6; 
     * </pre>
     */
    public void lisaa(Ryhma ryhma) throws SailoException {
        // if (lkm >= ryhmat.length) throw new SailoException("Liikaa alkioita");
        // tilaa kasvatetaan ilmeisesti noin. Internet good.
         if (lkm >= ryhmat.length) {
             ryhmat = Arrays.copyOf(ryhmat, ryhmat.length + ryhmat.length * 2);
             }
        ryhmat[lkm] = ryhma;
        lkm++;
    }
    
    
    /**
     * Toteutetaan valitun ryhman poistaminen
     * @param tunnusnro poistettavan ryhmän tunnusnro
     * @return 1 jos poistettiin 0 jos ei löytynyt
     * @example
     * <pre name="test">
     *  Ryhmat ryhmat = new Ryhmat();
     *  Ryhma ryhma1 = new Ryhma();
     *  Ryhma ryhma2 = new Ryhma();
     *  Ryhma ryhma3 = new Ryhma();
     *  ryhma1.rekisteroi();
     *  ryhma2.rekisteroi();
     *  ryhma3.rekisteroi();
     *  int id1 = ryhma1.getTunnusNro();
     *  ryhmat.lisaa(ryhma1);
     *  ryhmat.lisaa(ryhma2);
     *  ryhmat.lisaa(ryhma3);
     *  ryhmat.poista(id1+1) === 1;
     *  ryhmat.getLkm() === 2;
     *  ryhmat.poista(id1) === 1;
     *  ryhmat.getLkm() === 1;
     *  ryhmat.poista(id1+3) === 0;
     *  ryhmat.getLkm() === 1;
     * </pre>
     */
    public int poista(int tunnusnro) {
        int indeksi = haeId(tunnusnro);
        if (indeksi < 0) return 0;
        this.lkm--;
        for (int i = indeksi; i < getLkm(); i++) {
            ryhmat[i] = ryhmat[i + 1];
        }
        this.ryhmat[getLkm()] = null;
        return 1;
    }
    
    
    
    /**
     * Haetaan ryhma sen id numeron perusteella
     * @param idnro tunnus jonka mukaan etsitään
     * @return Löytyneen ryhman indeksi tai -1 jos ei löydy
     */
    public int haeId(int idnro) {
        for (int i = 0; i < getLkm(); i++)
            if (idnro == this.ryhmat[i].getTunnusNro()) return i;
        return -1;
    }
    
    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko jäsenen viite halutaan
     * @return viite jäseneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     * 
     *   tarvitsee parametrina int luvun ja nyt palauttaa viitteen ryhmään sen int luvun alla.
     *   Meilä on ryhmat taulukko. Jossa on ryhmiä. Joten meillä on mahdollisuus katsoa näin onko 
     *   taulukon paikassa i ryhmä...
     */
    public Ryhma anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return ryhmat[i];
    }
    
    
    /**
     * Lukee ryhmat tiedostosta.  
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * Ryhmat ryhmat = new Ryhmat();
     * Ryhma testi1 = new Ryhma();
     * testi1.rekisteroi();  
     * testi1.taytaRyhmaTiedoilla();
     * String hakemisto = "testi";
     * ryhmat.lueTiedostosta(hakemisto); #THROWS SailoException
     * String tiedNimi = hakemisto+"/testiryhma";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * ryhmat.lueTiedostosta(tiedNimi); #THROWS SailoException
     * try {
     *      ryhmat.lisaa(testi1);
     * } catch (SailoException e) {
     *  // TODO Auto-generated catch block
     *  e.printStackTrace();
     *  } 
     * ryhmat.tallenna();
     * ryhmat = new Ryhmat();
     * ryhmat.lueTiedostosta(tiedNimi);
     * ryhmat.anna(0).toString() === testi1.toString();
     * </pre>
     */   
    
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusnimi(hakemisto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) { 
        //try ( Scanner fi = new Scanner(new FileInputStream(new File(getTiedostonNimi()))) {
            kokoNimi = fi.readLine(); 
            if (kokoNimi == null) throw new SailoException("rekisterin nimi puuttuu");
            String rivi = fi.readLine();
            
            if (rivi == null) throw new SailoException("Maksimikoko puuttuu");
            
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Ryhma ryhma = new Ryhma();
                ryhma.parse(rivi);
                lisaa(ryhma);
            }
           
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea"); 
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia" + e.getMessage());
        }

        //tiedostonNimi = hakemisto + "/nimet.dat";
        //throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
   
    
     /**
      * Luetaan aikaisemmin annetun nimisestä tiedostosta
      * @throws SailoException jos tulee ongelmia
      */
     public void lueTiedostosta() throws  SailoException {
         lueTiedostosta(getTiedostonPerusnimi());
     }
     
     
    /**
     * Tallentaa ryhmat tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        //File fbak =new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        //fbak.delete();
        //ftied.renameTo(fbak);
        
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied.getCanonicalPath()))) {
            fo.println(getKokoNimi());
            fo.println(ryhmat.length);
            for (int i = 0; i < getLkm(); i++) {
                Ryhma ryhma = anna(i);
                fo.println(ryhma.toString()); 
            }
            
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }        
    }
    
    
    
    /**
     * Jasenillä vastaavat
     * @return ryhmän kokonimi
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    /**
     * getter
     * @return tiedoston perusnimi
     */
    public String getTiedostonPerusnimi() {
        return tiedostonPerusnimi;
    }
    
    /**
     * setter
     * @param nimi tiedoston nimi
     */
    public void setTiedostonPerusnimi(String nimi) {
        tiedostonPerusnimi = nimi;
    }
    
    
    /**
     * getter
     * @return tiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusnimi() + ".dat";
    }
    
    
    /**
     * getter
     * @return tied bakup nimi
     */
    public String getBakNimi() {
        return tiedostonPerusnimi + ".bak";
    }
    
   
    /**
     * Alla ei varsinaisesti tärkeää toimintaa. Pohdin nimen etsimistä mutta tätä ei enää muutosten jälkeen tarvinnutkaan.
     * 
     * Etsii sen Ryhman, jolla on annettu nimi.
     * @param nimi etsittävän Ryhman nimi
     * @return Ryhman indeksi taulukossa tai -1 jos ei löydy
     * TESTI TOIMINUT AIKAISEMMASSA VERSIOSSA SAMALLA KOODILLA!
     * Muissa testeissä luodut tiedostot taitavat sekoittaa tämän samalla, minkä takia ei toimi.
     * @example
     * <pre name="test">
     *  Ryhmat ryhmat = new Ryhmat();
     *  Ryhma aku = new Ryhma();
     *  aku.rekisteroi();        
     *  aku.taytaRyhmaTiedoilla();
     *  ryhmat.lisaa(aku);
     *  ryhmat.etsi("Ryhma 1").toString() === "1|Ryhma 1|";
     * </pre>
     */
    public Ryhma etsi(String nimi) {
        for (int i = 0; i < lkm; i++)
            if ( palauta(i).oletko(nimi) ) return palauta(i);
        return null;
    }

    
    
    /**
     * @param i int arvo eli paikko josta taulukossa etsitään.
     * @return palautetaan viite ryhmään ryhmät[] taulukon paikassa i
     */
    public Ryhma palauta(int i) {
        return ryhmat[i];
    }
    
    
    /**
     * Testi - Pääohjelma ryhmille
     * Testataan:
     *      Uuden ryhmät olion luonti
     *      Uuden ryhmä olion luonti
     *          Rekisteröinti
     *          Täyttö
     *          Lisääminen ryhmiin
     *      Ryhmien lukumäärän haku
     *      Ryhmien läpikäynti
     *          Ryhmän indeksi numeron haku
     *          Ryhmän nimen haku
     *          Ryhmän etsiminen
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Ryhmat ryhmat = new Ryhmat();

        Ryhma aku = new Ryhma();
        aku.rekisteroi();
        aku.taytaRyhmaTiedoilla();
        Ryhma aku1 = new Ryhma();
        aku1.rekisteroi();
        aku1.taytaRyhmaTiedoilla();

        try {
            ryhmat.lisaa(aku);
            ryhmat.lisaa(aku1);

            System.out.println("============= Ryhmat testissä =================");
            
            // anna ryhmä jonka kohdalla ollaan parhaillaan. Ryhmät luokassa ryhmä alkiot ovat taulukossa.
            // otetaan for loopilla indeksin i kohdalla oleva ryhmä ja tulostetaan pari testiä.

            for (int i = 0; i < ryhmat.getLkm(); i++) {
                Ryhma ryhma = ryhmat.anna(i);
                System.out.println("Ryhman inro: " + i);
                System.out.println(ryhma.getNimi());
                ryhma.tulosta(System.out); // edelleen tunnus nro liitettynä nimen perään indentiteetiksi mutta eri muodossa
                System.out.println("\n");
                System.out.println(ryhmat.etsi("Ryhma 1"));
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}