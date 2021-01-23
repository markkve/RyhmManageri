/**
 * 
 */
package ryhma;

// import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
// import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * KESKEN
 * 
 * Tyotunnit luokka pitämään yllä henkilöiden tunteja. Hakee TyotunnitHlo luokan kautta oikeat tunnit oikealle jäsenelle.
 * kattava testipääohjelma
 * 
 * @author Veikko M, Roope T
 * @version 5 Mar 2020
 *
 */
public class Tyotunnit implements Iterable<TyotunnitHlo>{
    

    private List<TyotunnitHlo> alkiot = new ArrayList<TyotunnitHlo>();
    private String tiedostonPerusnimi = "";
    
    /**
     * alustaja
     */
    public Tyotunnit() {
        //
    }
    
    
    /**
     * Työtuntien lisäys
     * @param tuntipaketti työtunnit tälle tunnusnumerolle. 
     * samalle voidaan lisätä useita. Huonompi nimi oli vain tunnit.
     * @throws SailoException jos liikaa alkioita muistissa
     * 
     * lisaa metodi jolla lisätään tehty työtuntipaketti alkioihin. ELi TyötunnitHlo tyyppiseen listaan.
     * Testi on sama kuin jäsenillä plus testipääohjelmassa huomioitu
     */
    public void lisaa(TyotunnitHlo tuntipaketti) throws SailoException {
        // if (tuntipaketti.getTunnusNro() > 15) throw new SailoException ("Liikaa alkioita");
        
        this.alkiot.add(tuntipaketti);
    }
        
    
    /**
     * Tallentaa tyotunnit omaan tiedostoonsa
     * @throws SailoException jos jotain tapahtuu
     */
    public void tallenna() throws SailoException {
        File tied = new File(getTiedostonNimi());
        try ( PrintStream fo = new PrintStream(new FileOutputStream(tied.getCanonicalPath())) ) {
            for (TyotunnitHlo jtunnit : this.alkiot) {
                fo.println(jtunnit.toString());
            }   
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + tied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + tied.getName() + " kirjoittamisessa ongelmia");
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee ongelmia
     */
    public void lueTiedostosta() throws  SailoException {
        lueTiedostosta(getTiedostonPerusnimi());
    }
    
    
    /**
     * Tiedostosta lukeminen
     * @param hakemisto missä ollaan 
     * @throws SailoException jos jokin menee pieleen
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusnimi(hakemisto);
        try ( Scanner fi = new Scanner(new FileInputStream(getTiedostonNimi()))) {
           String rivi = "";
            while (fi.hasNext()) {
                rivi = fi.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                TyotunnitHlo tunnit = new TyotunnitHlo();
                tunnit.parse(rivi);
                lisaa(tunnit);
                tunnit.rekisteroi();
            }    
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea"); 
        }
    }
    
        
        /**
         * @return getter
         */
        public String getTiedostonNimi() {
            return getTiedostonPerusnimi() + ".dat";
        }
        
        
        /**
         * @return getter
         */
        public String getTiedostonPerusnimi() {
            return tiedostonPerusnimi;
        }
        
        
        /**
         * @param nimi tiedoston nimi
         */
        public void setTiedostonPerusnimi(String nimi) {
            tiedostonPerusnimi = nimi;
        }
        
        
        /**
         * Iteraattori tuntien läpikäyntiin
         * testit samat kuin jäsenellä
         */
        @Override
        public Iterator<TyotunnitHlo> iterator() {
            return alkiot.iterator();
        }


        /**
         * Haetaan tunnit jotka kuuluvat tietylle jäsenlle.
         * 
         * @param jasenenNro kenelle tunnit kuuluu
         * @return viite löydettyyn jäseneen
         * 
         * !Lista työtunnitHlo olioista. Eli mitä ohjelman pitää palauttaa. (Tietyn jäsenen työtunnit oliot)
         * 
         * uusi arraylist löydetetty/löydetyt ja nyt haetaan tähän listaan kaikki Tyypin ttyötunnitHlo jtunnit. alkiot viittaa ylös kaikkiin työtunteihin
         * haetaan ehtoa varten jäsenen numero työtunneille TyötunnitHlo luokasta.
         * Ja jos jtunnit.getJasenenNro on sama kuin parametrina tuotu jäsenen numero lisätään kyseinen jäsenentyötuntipaketti löydettyihin.
         * palautetaan löydetyt tunnit rekisteriin ja näytetään tesktikentässä.
         * 
         */
        public List<TyotunnitHlo> annaTunnit(int jasenenNro) {
            List <TyotunnitHlo> loydetty = new ArrayList<TyotunnitHlo>();
            for (TyotunnitHlo jtunnit : this.alkiot) {
                if (jtunnit.getJasenenNro() == jasenenNro)  //pitää tietää kenestä jäsenestä on kysymys muuten jokaiselle jäsenelle tulstuu kaikki tunnit
                    loydetty.add(jtunnit);
            }
            return loydetty;
        }

        
        
    /**
     * Testi - Pääohjelma työtunneille
     * Testataan:
     *      Uuden jäsenet olion luonti
     *      Uuden jäsenen luonti         
     *          täyttö (parametrilla(ryhmän numero))
     *          lisäys
     *      Uuden tunnit olion luonti
     *      Uuden TyotunnitHlo (tuntipaketti) olion luonti
     *          tuntipaketin täyttö tavoitetunneilla
     *           - toteutuneilla tunneilla
     *          paketin lisäys tunteihin
     *      
     *      Haetaan jäsenen 1 kaikki tunnit
     *      tulostetaan läytyneen tuntipaketin kohdalta sen numero ja jäsenen numero 
     *      tulostetaan tosi-tunnit ja tavoitetunnit
     *          
     * @param args ei kayt
     */
    public static void main(String[] args) {
        
        Jasenet jasenet = new Jasenet();
        
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
         
          jasen1.taytaAkuTiedoilla(1);
          jasen2.taytaAkuTiedoilla(2);
         
          try {
            jasenet.lisaa(jasen1);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Tyotunnit tunnit = new Tyotunnit();
        
        TyotunnitHlo tunnit1 = new TyotunnitHlo();
        tunnit1.vastaaTavoiteTyotunnit(1);
        tunnit1.vastaaTosiTyotunnit(1);
        
        try {
            tunnit.lisaa(tunnit1);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("============= Tunnit testi =================");
        //List<TyotunnitHlo> testilista = tunnit.annaTunnit(2); //ei toimi koska jasenta ei lisatty
        List<TyotunnitHlo> testilista = tunnit.annaTunnit(1);
        for (TyotunnitHlo hlotunnit : testilista) {
            // hlotunnit.rekisteroi();
            System.out.println("Tulostetaan tuntipaketin numero: " + hlotunnit.getTunnusNro() + " Ja jäsenen numero: " + hlotunnit.getJasenenNro() + " ");
            hlotunnit.tulosta(System.out);
            hlotunnit.tulostaT(System.out);
        }
        
        
    }

}
