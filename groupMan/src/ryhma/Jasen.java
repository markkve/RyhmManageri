package ryhma;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Jasen - luokka: Ryhman jäsen. Huolehtii omista jäsenellekuuluvista tiedoistaan.
 * Jasen luokan crc kortin asiat
 * 
 * @author Veikko M, Roope T
 * @version 17 Feb 2020
 *
 */
public class Jasen {
    
    private int tunnusNro;
    private int ryhmanNro;
    private String nimi = "";
    private String ika;
    private String koulutus = "";
    private String tyokokemus = "";
    private String terveys = "";
    private String etnisyys = "";
    private String perhe = "";
    private String harrastus = "";
    private String asuinmuoto = "";
    private String vahvuus = "";
    private String heikkous = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     *  alustus
     */
    public Jasen () {
        
    }
    
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * työläs toteuttaa testi näin koska kun jäsen tallennetaan sen mukaan menee 
     * tunnus nro + ryhmän numero jne.
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse(" 1 | 1 | Ankka Aku | 13 |");
     *   jasen.toString().startsWith("1|1|Ankka Aku|13|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *   
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                ryhmanNro + "|" +
                nimi + "|" +
                ika + "|" +
                koulutus + "|" +
                tyokokemus + "|" +
                terveys + "|" +
                etnisyys + "|" +
                perhe + "|" +
                harrastus+ "|" +
                asuinmuoto + "|" +
                vahvuus + "|" +
                heikkous;
    }

    
    /**
     * Parse metodi joka hakee jäsenen tidot tiedostosta
     * 
     * @param rivi yksi rivi tiedostosta
     * 
     * @example
     * <pre name="test">
     *   Jasen jasen = new Jasen();
     *   jasen.parse(" 1 | 1 | Ankka Aku | 13 |");
     *   jasen.toString().startsWith("1|1|Ankka Aku|13|") === true;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        ryhmanNro = Mjonot.erota(sb, '|', ryhmanNro);
        nimi = Mjonot.erota(sb, '|', nimi);
        ika = Mjonot.erota(sb, '|', ika);
        koulutus = Mjonot.erota(sb, '|', koulutus);
        tyokokemus = Mjonot.erota(sb, '|', tyokokemus);
        terveys = Mjonot.erota(sb, '|', terveys);
        etnisyys = Mjonot.erota(sb, '|', etnisyys);
        perhe = Mjonot.erota(sb, '|', perhe);
        harrastus = Mjonot.erota(sb, '|', harrastus);
        asuinmuoto = Mjonot.erota(sb, '|', asuinmuoto);
        vahvuus = Mjonot.erota(sb, '|', vahvuus);
        heikkous = Mjonot.erota(sb, '|', heikkous);
    }

    
    /**
     * @param ryhmanNumero ryhman id numero
     * alustetaan tietyn ryhmän jäsen.
     * valmiiksi muodostaja joka kertoo että olet tämän ryhmän jäsen
     */
    public Jasen (int ryhmanNumero) {
        this.ryhmanNro = ryhmanNumero;
    }

   
    @Override
    public boolean equals(Object jasen) {
        if ( jasen == null ) return false;
        return this.toString().equals(jasen.toString());
    }


    @Override
    public int hashCode() {
        return tunnusNro;
    }

         
     /**
     * Haetaan ryhman numero
     * @return ryhman id numero
     */
    public int getRyhmanNro() {
         return this.ryhmanNro;
     }
     
 
    /**
     * Tulosta metodi jäsenelle
     * Ei enää käyttöä joten ei testiä
     * @param out jäsenen tiedot
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + "  " + nimi);
        out.println("  " + ika + "  " + koulutus + "  " + tyokokemus + "  " + terveys);
        out.println("  " + etnisyys + "  " + perhe + "  " + harrastus + "  " +  asuinmuoto + "  " + vahvuus + "  "  + heikkous);
    }
    
    
    /**
     * @param os mihin tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta (new PrintStream(os));
    }
    
    /**
     * @param out jäsenen tiedot
     */
    public void tulostaNt(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + "  " + nimi);
    }
    
    /**
     * @param os mihin tulostetaan
     */
    public void tulostaNt(OutputStream os) {
        tulosta (new PrintStream(os));
    }
    
    
    /**
     * Alustus tyyppinen metodi joka antaa pohja/ohje tietoja uudelle jäsenelle.
     * 
     * @param ryhmanNumero ryhman id johon jasen kuuluu
     */
    public void taytaAkuTiedoilla(int ryhmanNumero) {
        // r numero on parametri siksi noin.
        // Muut tiedot täytetään tässä valmiiksi esimerkkinä.
        this.ryhmanNro = ryhmanNumero;
        nimi = "Uusi Jasen " + rand(1000, 9999); //oli aiemmin ankka aku en muista vääristääkö jotain testiä.
        ika = "000";
        koulutus = " ";
        tyokokemus = " ";
        terveys = " ";
        etnisyys = " ";
        perhe = " ";
        harrastus = " ";
        asuinmuoto = " ";
        vahvuus = " ";
        heikkous = " ";
    }
    
    
    /**
     * Otetaan satunnainen luku ja lisätään jäsenen nimeen jotta voidaan identifioida.
     * @param ala raja
     * @param yla raja
     * @return satunnainen luku valilta [ala,yla]
     * </pre>
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int) Math.round(n);
    }
    
    
    /**
     * Haetaan tunnusnumero.
     * @return tunnusnumero (int arvo jäsenelle)
     * 
     * Jäsen saa tunnusnumeron kun se rekisteröidään
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    
    /**
     * Aseteaan tunnusnumero.
     * @param nr asetettava tunnus numero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro;
    }
    
    
    /**
     * Jasenen rekisteröinti ja 
     * testit jäsenen rekisteröinnille.
     * 
     * @return numero
     * 
     * @example
     * <pre name="test">
     * Jasen aku1 = new Jasen();
     * Jasen aku2 = new Jasen();
     * aku1.getTunnusNro() === 0;
     * aku1.rekisteroi();
     * aku1.getTunnusNro() === 1;
     * aku2.rekisteroi();
     * aku2.rekisteroi();
     * aku2.getTunnusNro() === 2;
     * </pre>
     */
    public int rekisteroi() {
        //if (this.tunnusNro != 0) return this.tunnusNro; //eli ei rekisteroida samaa hahmoa 2 kertaa
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * getteri nimelle
     * @return palauttaa jäsenen nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * getteri iälle
     * @return jasenen ika
     */
    public String getIka() {
        return ika;
    }
    
    
    /**
     * @return jäsenen koulutus
     */
    public String getKoulutus() {
        return koulutus;
    }

    
    /**
     * @return jasenen terveys
     */
    public String getTerveys() {
        return terveys;
    }
    
    
    /**
     * @return tyokokemus 
     */
    public String getTyokokemus() {
        return tyokokemus;
    }

    
    /**
     * @return etnisyys
     */
    public String getAlkupera() {
        return etnisyys;
    }


    /**
     * @return perhe
     */
    public String getPerhe() {
        return perhe;
    }
    
    
    /**
     * @return harrastus
     */
    public String getHarrastus() {
        return harrastus;
    }

    
    /**
     * @return asuinmuoto
     */
    public String getAsuinmuoto() {
        return asuinmuoto;
    }

    
    /**
     * @return vahvuus
     */
    public String getVahvuus() {
        return vahvuus;
    }

    
    /**
     * @return heikkous
     */
    public String getHeikkous() {
        return heikkous;
    }
    
    
    /**
     * setter nimelle
     * @param s jäsenelle laitettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        nimi = s;
        return null;
    }
    
    /**
     * setter iälle
     * @param s jäsenelle laitettava ikä
     * @return virheilmoitus, null jos ok
     */
    public String setIka(String s) {
        if (s.matches("^[a-z]+$") ) return "Numeroita";
        ika = s;
        return null;
    }

    
    /**
     * setter terveydelle
     * @param s terveys
     * @return terveys
     */
    public String setTerveys(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        terveys = s;
        return null;
    }
    
    
    /**
     * @param s jäsenelle laitettava koulutus
     * @return koulutus
     */
    public String setKoulutus(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        koulutus = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava työkokemus
     * @return kokemus
     */
    public String setKokemus(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        tyokokemus = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava etninen alkuperä
     * @return alkuperä
     */
    public String setAlkupera(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        etnisyys = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava perhe, esim. vaimo ja kaksi lasta
     * @return perhe
     */
    public String setPerhe(String s) {
        perhe = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava asuinmuoto
     * @return asuinmuoto
     */
    public String setAsuinmuoto(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        asuinmuoto = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava(t) harrastus/harrastukset
     * @return harrastukset
     */
    public String setHarrastus(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        harrastus = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava vahvuus, esim. ahkera
     * @return vahvuus
     */
    public String setVahvuus(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        vahvuus = s;
        return null;
    }

    /**
     * @param s jäsenelle laitettava heikkous, esim. huolimaton
     * @return heikkous
     */
    public String setHeikkous(String s) {
        if ( s.matches("[0-9*]") ) return "Kirjaimia";
        heikkous = s;
        return null;
    }
    
    /**
     * metodi jäsenen kloonausta ja näyttöä indeksoidusti varten. 
     * Ei toteutettu näin kuitenkaan koska on vain niin paljon mitä Yksi ihmenen jaksaa tehdä näissä olosuhteissa. 
    @Override
    public Jasen clone() throws CloneNotSupportedException {
        Jasen uusi;
        uusi = (Jasen) super.clone();
        return uusi;
    }*/


    /**
     * Testi-pääohjelma Jasenelle
     * 
     * testataan:
     *      uuden jäsenen luonti
     *      rekisteröinti
     *      tiedoilla täyttäminen
     *      uusien tavoitetuntien tekeminen ja lisääminen
     *      tuntien tunnusnumero ja jäsenen tunnusnumero tuntien näkökulmasta
     *      
     * @param args ei kayt
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        Jasen aku = new Jasen();
        Jasen aku2 = new Jasen();
        System.out.println(aku.toString());
        
        
        
        aku.rekisteroi();
        aku2.rekisteroi();
       
        //alla valitan nollasta mutta se on ika koska se alustuu 0. Muut on "".
        aku.tulosta(System.out);    // mikä hitto on tuo 0 kun tulostaa... en saa päähän missään ei olla annettu millekkään arvoa 0
        aku.taytaAkuTiedoilla(1);   // en copynny kun teen luennon vaan mukana Vesa älä yritä syyllisttää siellä.
        aku.tulosta(System.out);    // tbh en tajua miksi laitoin aku:n jasen tai yksilö olis ollu parempi.
        System.out.println("\n");   // toisaalta jasenta on niin persseesti kaikkialla jo että meniskö sekaisin.
        aku.tulostaNt(System.out);
        System.out.println("\n");
        aku2.tulosta(System.out);
        aku2.taytaAkuTiedoilla(2); // tässä oli vastaaAkuAnkka
        aku2.tulosta(System.out);
        
       System.out.println("\n");
        
        TyotunnitHlo testunnit = new TyotunnitHlo();
        testunnit.rekisteroi();
        testunnit.vastaaTavoiteTyotunnit(aku.getTunnusNro());
        testunnit.tulosta(System.out);
        System.out.println(testunnit.getTunnusNro() + " " + testunnit.getJasenenNro());
    }


}
