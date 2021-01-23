package ryhma;

import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Jasenet - luokka: jäsenistö joka osaa mm. lisätä uuden jäsenen ja selata sisältöään.
 *
 * @author Veikko M, Roope T
 * @version 1 
 */
public class Jasenet implements Iterable<Jasen>{
    // private final Collection<Jasen> yksilot = new ArrayList<Jasen>(); collection on rajapinta en täysin tajunnut joten laitoin 
    // vaan rehellisesti array list.
    
    private List<Jasen> alkiot = new ArrayList<Jasen>();
    private String tiedostonPerusnimi = "";
    private String kokoNimi = "";


    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
        // Attribuuttien oma alustus riittää
    }

    
    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa jäsenen omistukseensa.
     * @param jasen lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * 
     * tultiin rekisteristä. tutkitaan montako jasenta on jo olemassa. Jos tämän jasenen tunnus numero (jasen luokka) on yli 15 tulee exception
     * lisataan jasen alkioihin Eli: private List<Jasen> alkiot = new ArrayList<Jasen>(); listaan.
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * jasenet.getLkm() === 0;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 1;
     * jasenet.lisaa(aku2); jasenet.getLkm() === 2;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 3;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 4;
     * jasenet.lisaa(aku1); jasenet.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Jasen jasen) throws SailoException {
        //if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        // TODO: laita tila kasvamaan
        // alkiot[lkm] = jasen;
        // lkm++;
        
        // if  (jasen.getTunnusNro() > 15) throw new SailoException("Liikaa alkioita");
        //this.alkiot.remove(jasen); // poistaJasen tulevaisuudessa. Param jäsenen id.
        this.alkiot.add(jasen);
    }
  

    /**
     * Tallentaa jäsenistön tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        
        // throw new SailoException("Ei osata vielä tallentaa tiedostoa " + tiedostonNimi);
        // File tied = new File("rekisteri/jasenet.dat");
        File tied = new File(getTiedostonNimi());
        
        try ( PrintStream fo = new PrintStream(new FileOutputStream(tied.getCanonicalPath())) ) {
            //fo.println(getKokoNimi());
            for (Jasen jasen : this) {
                fo.println(jasen.toString());
            }   
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + tied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + tied.getName() + " kirjoittamisessa ongelmia");
        }
 
    }

    
    /**
     * Lukee ryhmat tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * kun luetaan tiedostosta ensimmäinen jäsen ei rekisteröidy. Ja näin ollen homma menee sekaisin koska ongelma toistuu aina kun tiedostoa luetaan.
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Jasenet jasenet = new Jasenet();
     * Jasen roope = new Jasen(), veikko = new Jasen();
     * roope.taytaAkuTiedoilla(0);
     * veikko.taytaAkuTiedoilla(0);
     * String hakemisto = "testi";
     * String tiedNimi = hakemisto+"/nimet";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * jasenet.lueTiedostosta(tiedNimi); #THROWS SailoException
     * jasenet.lisaa(roope);
     * jasenet.lisaa(veikko);
     * jasenet.tallenna();
     * jasenet = new Jasenet();
     * jasenet.lueTiedostosta(tiedNimi);
     * Iterator<Jasen> i = jasenet.iterator();
     * i.next();
     * i.next();
     * i.hasNext() === false;
     * </pre>
      */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusnimi(hakemisto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) { 
            
            
            String rivi;
            
            
            
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Jasen jasen = new Jasen();
                jasen.parse(rivi);
                lisaa(jasen);
                jasen.rekisteroi();
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
      * @return Ryhman nimi merkkijonona
      */
     public String getKokoNimi() {
         return kokoNimi;
     }
     
     
     /**
      * @return Tiedoston kokonimi jota käytetään tallennukseen
      */
     public String getTiedostonPerusnimi() {
         return tiedostonPerusnimi;
     }
     
     
     /**
      * Asettaa tiedoston perusnimen.
      * @param nimi tallenustiedoston perusnimi
      */
     public void setTiedostonPerusnimi(String nimi) {
         tiedostonPerusnimi = nimi;
     }
     
     
     /**
      * @return Tallennustiedoston nimi, jota käytetään tallennukseen
      */
     public String getTiedostonNimi() {
         return getTiedostonPerusnimi() + ".dat";
     }
     
     
     /**
      * @return Palauttaa varakopiotiedoston nimen
      */
     public String getBakNimi() {
         return tiedostonPerusnimi + ".bak";
     }
     
     
     /**
      * Iteraattori kaikkien jäsenten läpikäymiseen
      * @example
      * <pre name="test">
      *  #PACKAGEIMPORT
      *  #import java.util.*;
      *  Jasenet jasenet = new Jasenet();
      *  Jasen jasen1 = new Jasen();
      *  Jasen jasen2 = new Jasen();
      *  jasenet.lisaa(jasen1);
      *  jasenet.lisaa(jasen2);
      *  Iterator<Jasen> iter = jasenet.iterator();
      *  iter.next() === jasen1;
      *  iter.next() === jasen2;
      * </pre>
      */    
     @Override
     public Iterator<Jasen> iterator() {
         return alkiot.iterator();
     }


     /**
     * Iteroidaan jäsenet läpi
     * Haetaan tietyn ryhmän kaikki jäsenet.
     * @param tunnusnro tunnusnumerolla haettava jasen
     * @return palauttaa löydetyt jäsenet listassa
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Jasenet jasenet = new Jasenet();
     * Jasen jasen1 = new Jasen(1);
     * Jasen jasen2 = new Jasen(2);
     * Jasen jasen3 = new Jasen(2);
     * jasenet.lisaa(jasen1);
     * jasenet.lisaa(jasen2);
     * jasenet.lisaa(jasen3);
     * List<Jasen> Loytyneet;
     * Loytyneet = jasenet.annaJasenet(3);
     * Loytyneet.size() === 0;
     * Loytyneet = jasenet.annaJasenet(1);
     * Loytyneet.size() === 1;
     * Loytyneet = jasenet.annaJasenet(2);
     * Loytyneet.size() === 2;
     * </pre>
     */
    public List<Jasen> annaJasenet(int tunnusnro) {
         List<Jasen> loydetyt = new ArrayList<Jasen>();
         for (Jasen jasen : alkiot)
             if ( jasen.getRyhmanNro() == tunnusnro ) loydetyt.add(jasen);
         return loydetyt;
     }
    

    /**
     * metodi jolla muokataan jäsentä. 
     * 
     * @param jasen käsiteltävä jäsen
     * @throws SailoException sf
     
    public void korvaaTaiLisaa(Jasen jasen) throws SailoException {
        int id = jasen.getTunnusNro();
        for (Jasen jas : alkiot) {
            if ( jas.getTunnusNro() == id ) {
                jas = jasen;
                //muutettu = true;
                return;
            }
        }
        lisaa(jasen);
    }
    */
    
    
    /**
     * Poistetaan jäsen
     * @param jasen poistettava jasen
     * @return True jos poisto onnistui
     * @example
     * <pre name="test">
     *  Jasenet jasenet = new Jasenet();
     *  Jasen jasen1 = new Jasen(1);
     *  Jasen jasen2 = new Jasen(2);
     *  Jasen jasen3 = new Jasen(2);
     *  jasenet.lisaa(jasen1);
     *  jasenet.lisaa(jasen2);
     *  jasenet.poista(jasen2) === true;
     *  jasenet.getLkm() === 1;
     *  jasenet.poista(jasen3) === false;
     *  jasenet.getLkm() === 1;
     * </pre>
     */
    public boolean poista(Jasen jasen) {
        boolean onnistuiko = this.alkiot.remove(jasen);
        return onnistuiko;
        
    }
    
    
    /**
     * @param tunnusnro tuotu ryhmän tunnusnumero
     * @return poistettujen jasenten lukumäärä
     * @example
     * <pre name="test">
     *  Jasenet jasenet = new Jasenet();
     *  Jasen jasen1 = new Jasen(1);
     *  Jasen jasen2 = new Jasen(2);
     *  Jasen jasen3 = new Jasen(2);
     *  jasenet.lisaa(jasen1);
     *  jasenet.lisaa(jasen2);
     *  jasenet.lisaa(jasen3);
     *  jasenet.poistaRyhmalaiset(2) === 2;
     *  jasenet.getLkm() === 1;
     * </pre>
     */
    public int poistaRyhmalaiset(int tunnusnro) {
        int lkm = 0;
        for (Iterator<Jasen> iter = this.alkiot.iterator(); iter.hasNext();) {
            Jasen jasen = iter.next();
            if (jasen.getRyhmanNro() == tunnusnro) { iter.remove(); lkm++; }
        }
        return lkm;
    }
    
     
    /**
     * Palauttaa ryhmäläisten lukumäärän. ei tarvista.
     * @return jäsenten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
        
    
    /**
     * Testipääohjelma jäsenistölle
     * Testataan:
     *      Uuden jäseneistön luonti
     *      Uuden jäsenen luonti
     *      Jäsenen täyttäminen ja rekisteröinti
     *      Jäsenen lisääminen
     *      Ryhmän jäsenten hakeminen
     *      
     * @param args ei käytössä
     * 
     * Olispa jasenet luokka (olio) jonne voisin luoda nyt ne aku ankat. 
     * Osataan luoda jäsen nyt koska tehtiin se juuri jasen luokassa.
     */
    public static void main(String args[]) {
        Jasenet jasenet = new Jasenet();
        
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        Jasen jasen3 = new Jasen();
         
          jasen1.taytaAkuTiedoilla(1); // täytäAkuTiedoilla saa parametrina ryhmän numeron
          jasen1.rekisteroi();
          jasen2.taytaAkuTiedoilla(2);
          jasen3.taytaAkuTiedoilla(1);
         
          try {
            jasenet.lisaa(jasen1);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          try {
            jasenet.lisaa(jasen2);
            jasenet.lisaa(jasen3);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
          System.out.println("============= Jäsenet testi =================");
          List<Jasen> jasenet2 = jasenet.annaJasenet(1); // Lista Jasen tyypin olioista, johon haetaan ryhmän 1 jasenet.
                      // for each : tyypin jasen olento jäsenet2 listassa tulostetaan. testi ei ehtoa. ;
          for (Jasen jasen : jasenet2) {
              System.out.println(jasen.getRyhmanNro() + " ");
              jasen.tulosta(System.out);
          }         
      }



    

    /**
     * Vanhentunutta koodia omaksi verrattavaksi.
     * 
     * @param ryhmanNro rekisteristä tullut parametri. halutaan juuri sen ryhman jasenet
     * @return lista viitteistä löydettyihin jaseniin
     * 
     * eli luodaan uusi lista Jasen olioista --> loydetyt.
     * Lisätään listaan for each : tyypin Jasen olento alkioista jonka ryhmän numero on sama kuin parametrina tuotu
     * ryhmän numero.
     */
    /*
    public List<Jasen> annaJasenet(int ryhmanNro) {
         List<Jasen> loydetyt = new ArrayList<Jasen>();
         for (Jasen jasen : this.alkiot) {
             if (jasen.getRyhmanNro() == ryhmanNro) loydetyt.add(jasen);
          }
          return loydetyt;
     }
     
    
    /**
     * tämä toimi taulukon kanssa enää ei samalla tavoin
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko jäsenen viite halutaan
     * @return viite jäseneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     
    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    */


  }       