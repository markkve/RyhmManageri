package ryhma;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Ryhmä-luokka: Luodaan ryhmä, joka tietää mm. tunnusnumeronsa ja nimensä.
 * 
 * @author Veikko M, Roope T
 * @version 25 Feb 2020
 * 
 * Luodaan yksi ryhmä.
 *
 */
public class Ryhma {
    
    private int tunnusNro;
    private String nimi;

    private static int seuraavaNro = 1;
    
    /**
     * Palautetaan Ryhmien tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @example
     * <pre name="test">
     *  Ryhma ryhma = new Ryhma();
     *  ryhma.parse("   1  |   Ryhma 1  |");
     *  ryhma.toString() === "1|Ryhma 1|";
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" ;
                
    }

    
    /**
     * Parse metodi tiedostosta lukua varten
     * 
     * @param rivi yksi rivi tiedostosta
     * @example
     * <pre name="test">
     *  Ryhma ryhma = new Ryhma();
     *  ryhma.parse("   1  |   Ryhma 1  |");
     *  ryhma.getTunnusNro() === 1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));

        nimi = Mjonot.erota(sb, '|', nimi);

    }

     
    /**
     * Getter ryhman nimelle
     * 
     * @return palauttaa ryhmän nimen
     * 
     * @example
     * <pre name="test">
     * Ryhma aku = new Ryhma();
     * aku.rekisteroi();
     * aku.taytaRyhmaTiedoilla();
     * aku.getNimi() === "Ryhma 2";
     * 
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Ryhmän reksiteröinti
     * @return tunnusnumero ryhmalle
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Getter tunnusnumerolle
     * @return tunnusnumero  
     * luotiin rekisteröinnin yhteydessä (index)
     */
    public int  getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * @param nr setter ryhmän tunnusnumerolle
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Alustus tyypin metodi ryhmälle.
     * Lisätään identiteettiä
     */
    public void taytaRyhmaTiedoilla() {
        nimi = "Ryhma ";
        
        StringBuilder sb = new StringBuilder(nimi);
        sb.append(tunnusNro);
        
        nimi = sb.toString();
    }
   
    
    /**
     * Tutkitaan onko astian nimi etsitty
     * @param mika verrattava nimi
     * @return true jos on samat, false muuten
     * </pre>
     */
    public boolean oletko(String mika) {
        return getNimi().equalsIgnoreCase(mika);
    }

    
    /**
     * Tulostus metodi ryhmälle
     * @param out Tietovirta ohon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", this.tunnusNro) + "  " + this.nimi);
    }
    
     
    /**
     * oletus muodostaja ei ole oikeastaan tarpeen koska javassa jos luokkaan ei kirjotia yhtään muodostajaa
     * niin siellä on aina automaattisesti olemassa oletusmuodostaja.
     * liityen ryhmä olioihin = new Ryhma() ei muodostajia
     * Sanotaan että tarvitaan muodostaja jolla ei ole yhtään parametria.
     * Mutta sellainen on javassa siis valmiina. Jos ei kirjoita yhtään muodostajaa luokkaan,
     * oletusmuodostaja antaa arvon 0.
     */


    /**
     * Testi-pääohjelma Ryhmäluokalle
     * Testataan:
     *      Uuden ryhmän luonti
     *      Ryhmän reksiteröinti
     *      Tietojen asetus
     *      Nimen testaus / etsiminen. Oletko tämä ryhmä?
     *      
     * Testaan ryhmää/ryhmiä
     * Onnistuko rekisterointi ja ryhmän täyttö tiedoilla eli Nimellä tässä tapauksessa.
     * @param args ei käytössä
     */
    public static void main(String[] args) {

        // Ryhmä olio aku = uusi ryhmä.
        Ryhma aku = new Ryhma();
        Ryhma aku2 = new Ryhma();
        
        aku.rekisteroi();
        
        aku.taytaRyhmaTiedoilla();
        
        aku2.rekisteroi();
        
        aku2.taytaRyhmaTiedoilla();
        
        /*aku2.rekisteroi(); ei laiteta kun joutuisi muuten randomoimaan ryhman tun.
        
        aku2.taytaRyhmaTiedoilla();*/
        
        System.out.println("katsotaan onko ryhman nimi oikein");
        System.out.println(aku.oletko("Ryhma 1"));
        System.out.println(aku2.oletko("Ryhma 2"));
        //System.out.println(aku.oletko("ykkkkkk"));
        aku.tulosta(System.out);
        aku2.tulosta(System.out);
        
        // tulostaa periaatteessa tunnusnumeron kahteen otteeseen. Eri formaateissa vain.
        // Indentiteetin takia nimeen on appendoitu tunnusnro ryhmalle.

    }
   
}